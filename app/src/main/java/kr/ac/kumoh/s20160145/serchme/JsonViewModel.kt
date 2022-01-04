package kr.ac.kumoh.s20160145.serchme

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class JsonViewModel(application: Application) : AndroidViewModel(application) {
    data class JS (var rcp_img: String, var explain: String) // 조리 방법용
    data class rcp_main (var rcp_img: String, var rcp_name: String, var rcp_ingredient: String) // 레시피 기본 정보 (현재: 레시피 이름, 레시피 이름, 레시피 재료설명)
    var rcp_name: String? = null

    val list = MutableLiveData<ArrayList<JS>>()
    private val js = ArrayList<JS>()

    init {
        list.value = js
    }

    fun getJS(i: Int) = js[i]

    fun getSize() = js.size

    fun getRcpName(RCPNAME: String?) {
        if (RCPNAME != null) {rcp_name = RCPNAME}
    }

    companion object {
        const val QUEUE_TAG = "JSVolleyRequest"
    }

    private var queue: RequestQueue

    init {
        list.value = js
        queue = Volley.newRequestQueue(application)
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }

    fun requestJS() {
        rcp_name = rcp_name!!.replace(" ", "_")
        val url = "http://openapi.foodsafetykorea.go.kr/api/d0422c886fe84db9809b/COOKRCP01/json/1/1000/RCP_NM=" + rcp_name
        //val url = "http://openapi.foodsafetykorea.go.kr/api/d0422c886fe84db9809b/COOKRCP01/json/1/1"
        if (rcp_name != null) { Log.d("레시피 최종전달확인", rcp_name!!) }


        val request = JsonObjectRequest(Request.Method.GET, url, null,
            {
                js.clear()
                parseJson(it)
                list.value = js
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )
        request.tag = QUEUE_TAG
        queue.add(request)
    }

    private fun parseJson(items: JSONObject) {
        val item: JSONObject = items // 이줄포함 4줄 파싱용 기초설정
        val cookrcp = item.getJSONObject("COOKRCP01")
        val row = cookrcp.getJSONArray("row")
        val inner = row[0] as JSONObject
        val start_img = inner.getString("ATT_FILE_NO_MK") // 대표이미지
        var start_name = inner.getString("RCP_NM") // 요리 이름
        var start_ingredient = inner.getString("RCP_PARTS_DTLS") // 요리 재료
        //열량 , 탄수화물 , 단백질 , 지방 , 나트륨
        var start_eng = inner.getString("INFO_ENG") // 열량 정보
        var start_car = inner.getString("INFO_CAR") // 탄수화물 정보
        var start_pro = inner.getString("INFO_PRO") // 단백질 정보
        var start_fat = inner.getString("INFO_FAT") // 지방 정보
        var start_na = inner.getString("INFO_NA") // 나트륨 정보
        var info_set = "열량: " + start_eng + "\n" + "탄수화물: " + start_car + "\n" + "단백질: " + start_pro + "\n" + "지방: " + start_fat + "\n" + "나트륨: " + start_na + "\n"

        js.add(JS(start_img, start_name + "\n\n" + start_ingredient + "\n\n" + info_set))
        for (i: Int in 1..20) {
            if (i < 10) {
                if (inner.getString("MANUAL0" + i) == "") break // 내용없을시 중단
                val rcp_img = inner.getString("MANUAL_IMG0" + i) // 조리 방법 사진
                val explain = inner.getString("MANUAL0" + i) // 조리 설명
                js.add(JS(rcp_img, explain))
            }
            else {
                if (inner.getString("MANUAL" + i) == "") break // 내용없을시 중단
                val rcp_img = inner.getString("MANUAL_IMG" + i) // 조리 방법 사진
                val explain = inner.getString("MANUAL" + i) // 조리 설명
                js.add(JS(rcp_img, explain))
            }
        }
    }
}