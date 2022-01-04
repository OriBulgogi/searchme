package kr.ac.kumoh.s20160145.serchme

import android.app.Application
import android.content.Context
import android.os.ParcelFileDescriptor.open
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.channels.DatagramChannel.open
//import java.util.ArrayList
import kotlin.collections.ArrayList

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    data class rcp_checksum(var rcp_name: String, var rcp_have: Int, var have_ingr: ArrayList<String>, var no_ingr: ArrayList<String>)

    var ingredient_list = ArrayList<String>() // 선택된 식재료 리스트
    var json_string: JsonData? = null
    var json_o: JSONObject? = null
    var temp = ""
    var temp_n0: Int = 0
    var temp_n1: Int = 0
    var yes_list = ArrayList<String>()
    var no_list = ArrayList<String>()
    var arg_list = ArrayList<String>() // 선택된 알러지 리스트
    var preference_list = ArrayList<String>() // 선택된 선호도 리스트

    val list = MutableLiveData<ArrayList<rcp_checksum>>()
    val rcp_position = ArrayList<rcp_checksum>() // 적합 레시피 번호와 맞는 갯수
    val first_filter = ArrayList<rcp_checksum>() // 적합 레시피 기준으로 목록화

    init {
        //arg_list.add("오이")
        //arg_list.add("새싹채소")
//        ingredient_list.add("광어")
//        ingredient_list.add("소금")
//        ingredient_list.add("후춧가루")
//        ingredient_list.add("밀가루")
//        ingredient_list.add("달걀")
//        ingredient_list.add("아몬드")
//        ingredient_list.add("올리브오일")
//        ingredient_list.add("플레인요거트")
        list.value = rcp_position
        //"광어","소금","후춧가루","밀가루","달걀","아몬드","호두","올리브오일","플레인요거트","꿀","레몬즙"
    }

    fun getRCP(i: Int) = rcp_position[i]

    fun getSize() = first_filter.size


    override fun onCleared() {
        super.onCleared()
    }

    fun get_ingredients(ingredient: ArrayList<String>) {
        for (i in 0 until ingredient.size) {
            ingredient_list.add(ingredient[i])
        }
        Log.d("레시피 뷰모델로 재료 정상적으로 넘어갔는지 확인", ingredient_list.toString())
    }
    fun get_ap(ap: LoginResponse) {
        if (ap.a_peanut == 1) { arg_list.add("땅콩") }
        if (ap.a_welnut == 1) { arg_list.add("호두") }
        if (ap.a_salmon == 1) { arg_list.add("연어") }
        if (ap.a_shrimp == 1) { arg_list.add("새우") }
        if (ap.a_wheat == 1) { arg_list.add("밀가루") }
        if (ap.a_milk == 1) { arg_list.add("우유") }
        if (ap.a_crusta == 1) { arg_list.add("갑각류") }
        if (ap.a_peach == 1) { arg_list.add("복숭아") }
        if (ap.a_lacquer == 1) { arg_list.add("옻") }

        if (ap.p_boil == 1) { preference_list.add("끓이기") }
        if (ap.p_fry == 1) { preference_list.add("튀기기") }
        if (ap.p_roast == 1) { preference_list.add("굽기") }
        if (ap.p_steaming == 1) { preference_list.add("찌기") }
        if (ap.p_stirfry == 1) { preference_list.add("볶기") }
        if (ap.p_etc == 1) { preference_list.add("기타") }
    }

    fun get_json(result: JsonData) { // 비교과정
        for (i in 0..997) {
            var dtl = result.get(i).Ingredients
            if (!arg_list.isEmpty()) {
                var temp_check = 0
                for (n in 0 until arg_list.size) {
                    if (dtl.contains(arg_list[n])) {
                        temp_check = 1
                        break
                    }
                }
                if (temp_check == 1) { continue }
            }
            temp_n0 = i
            for (j in 0 until ingredient_list.size) {
                for (k in 0 until dtl.size) {
                    if(dtl[k] == ingredient_list[j]) {
                        yes_list.add(dtl[k])
                        temp_n1 += 1
                        break
                    }
                }
            }
            for (j in 0 until dtl.size) {
                if (!yes_list.contains(dtl[j])) {
                    no_list.add(dtl[j])
                }
            }
            if (temp_n1 != 0) {
                var temp_name = result.get(i).Name
                rcp_position.add(rcp_checksum("", temp_n1, ArrayList<String>(), ArrayList<String>()))
                rcp_position[rcp_position.size - 1].rcp_name = temp_name
                rcp_position[rcp_position.size - 1].have_ingr.addAll(yes_list)
                rcp_position[rcp_position.size - 1].no_ingr.addAll(no_list)
            }
            temp_n0 = 0
            temp_n1 = 0
            yes_list.clear()
            no_list.clear()
        }
        make_list(rcp_position)
    }

    fun make_list(rcp_list: ArrayList<rcp_checksum>) { // 맞는 것들 선별
        for (i in 0 until rcp_list.size) {
            if (rcp_list.get(i).rcp_have != 0) {
                first_filter.add(rcp_list.get(i))
            }
        }
        first_filter.sortByDescending { it.rcp_have }
    }

    fun make_second_list(rcp_list: ArrayList<rcp_checksum>) {

    }
}