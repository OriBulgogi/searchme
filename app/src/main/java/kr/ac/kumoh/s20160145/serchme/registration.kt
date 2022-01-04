package kr.ac.kumoh.s20160145.serchme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.telecom.Call
import retrofit2.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.android.volley.Response
import retrofit2.Response
import kr.ac.kumoh.s20160145.serchme.databinding.ActivityRegistrationBinding
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


class registration : AppCompatActivity() {
    private lateinit var view: ActivityRegistrationBinding
    private val preferences = arrayOf("끓이기", "찌기", "굽기", "튀기기", "볶기", "기타")

    private lateinit var model: ListViewModel
    //private val preferenceAdapter = PreferenceAdapter()

    lateinit var id: EditText
    lateinit var password: EditText
    lateinit var password_confirm: EditText

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(view.root)
        //setContentView(R.layout.activity_registration)

//        model = ViewModelProvider(this).get(ListViewModel::class.java)
//
//        model.getList().observe(this, Observer<ArrayList<String>> {
//            preferenceAdapter.notifyDataSetChanged()
//        })
//
//        for (str in preferences) {
//            model.add(str)
//        }

//        view.listView1.apply{
//            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
//            itemAnimator = DefaultItemAnimator()
//            setHasFixedSize(true)
//            adapter = PreferenceAdapter()
//        }
//
//        view.listView1.setOnClickListener {
//            //클릭시 구현
//        }

        id = findViewById(R.id.InputId)
        password = findViewById(R.id.InputPw)
        password_confirm = findViewById(R.id.InputPwConfirm)

        button = findViewById(R.id.SignUp)

        var  ch1: CheckBox = findViewById(R.id.boil);
        var  ch2: CheckBox = findViewById(R.id.steame);
        var  ch3: CheckBox = findViewById(R.id.roast);
        var  ch4: CheckBox = findViewById(R.id.fry);
        var  ch5: CheckBox = findViewById(R.id.stirfry);
        var  ch6: CheckBox = findViewById(R.id.etc);

        var  ch7: CheckBox = findViewById(R.id.a_peanut);
        var  ch8: CheckBox = findViewById(R.id.a_welnut);
        var  ch9: CheckBox = findViewById(R.id.a_salmon);
        var  ch10: CheckBox = findViewById(R.id.a_shrimp);
        var  ch11: CheckBox = findViewById(R.id.a_wheat);
        var  ch12: CheckBox = findViewById(R.id.a_milk);
        var  ch13: CheckBox = findViewById(R.id.a_crusta);
        var  ch14: CheckBox = findViewById(R.id.a_peach);
        var  ch15: CheckBox = findViewById(R.id.a_lacquer);

        val retrofit = Retrofit.Builder()
            .baseUrl("http://searchme1.cafe24.com") // 주소는 본인의 서버 주소로 설정
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RegisterService::class.java)

        fun SuccessRegistration(){
            Intent(this, Login::class.java).run {
                startActivity(this)
            }
        }

        button.setOnClickListener {
            val idStr = id.text.toString()
            val pwStr = password.text.toString()
            val pwconfStr = password_confirm.text.toString()
            val p_cheked = mutableListOf<Int>()
            val a_cheked = mutableListOf<Int>()

            if(ch1.isChecked()){p_cheked.add(1) }else {p_cheked.add(0)}
            if(ch2.isChecked()){p_cheked.add(1) }else {p_cheked.add(0)}
            if(ch3.isChecked()){p_cheked.add(1) }else {p_cheked.add(0)}
            if(ch4.isChecked()){p_cheked.add(1) }else {p_cheked.add(0)}
            if(ch5.isChecked()){p_cheked.add(1) }else {p_cheked.add(0)}
            if(ch6.isChecked()){p_cheked.add(1) }else {p_cheked.add(0)}

            if(ch7.isChecked()){a_cheked.add(1) }else {a_cheked.add(0)}
            if(ch8.isChecked()){a_cheked.add(1) }else {a_cheked.add(0)}
            if(ch9.isChecked()){a_cheked.add(1) }else {a_cheked.add(0)}
            if(ch10.isChecked()){a_cheked.add(1) }else {a_cheked.add(0)}
            if(ch11.isChecked()){a_cheked.add(1) }else {a_cheked.add(0)}
            if(ch12.isChecked()){a_cheked.add(1) }else {a_cheked.add(0)}
            if(ch13.isChecked()){a_cheked.add(1) }else {a_cheked.add(0)}
            if(ch14.isChecked()){a_cheked.add(1) }else {a_cheked.add(0)}
            if(ch15.isChecked()){a_cheked.add(1) }else {a_cheked.add(0)}

            if(pwStr != pwconfStr){
                Toast.makeText(applicationContext, "confirm PW", Toast.LENGTH_SHORT).show()
            }else{
                service.register(idStr, pwStr, p_cheked, a_cheked).enqueue(object : Callback<RegistrationResponse> {
                    override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                        val result = response.body()
                        Log.d("로그인", "${result}")
                        SuccessRegistration()
                    }
                    override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                        Log.e("로그인", "${t.localizedMessage}")
                        Toast.makeText(applicationContext,"중복된 아이디입니다", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

    }

//    inner class PreferenceAdapter:RecyclerView.Adapter<PreferenceAdapter.ViewHolder>(){
//        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            val txPreference: TextView = itemView.findViewById(android.R.id.text1)
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
//            return ViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            holder.txPreference.text = model.getPreference(position)
//        }
//
//        override fun getItemCount()= model.getSize()

//    }
    
}

interface RegisterService {
    @FormUrlEncoded
    @POST("registration.php")
    fun register(@Field("id") id:String,
                 @Field("password") pw:String,
                 @Field("preference[]") prefer:List<Int>,
                 @Field("allergy[]") allergy:List<Int>
    ) : Call<RegistrationResponse>
}
