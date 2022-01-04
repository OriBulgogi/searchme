package kr.ac.kumoh.s20160145.serchme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.telecom.Call
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
//import android.widget.Toast
//import com.android.volley.Response
import kr.ac.kumoh.s20160145.serchme.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class Login : AppCompatActivity() {
    private lateinit var view : ActivityLoginBinding
    lateinit var id: EditText
    lateinit var password: EditText
    lateinit var button: Button
    lateinit var btnRegister: TextView
    lateinit var user_info : LoginResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(view.root)
        //setContentView(R.layout.activity_login)

        id = findViewById(R.id.InputId)
        password = findViewById(R.id.InputPw)
        button = findViewById(R.id.SignIn)
        btnRegister = findViewById(R.id.SignUp)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://searchme1.cafe24.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SignService::class.java)

        fun successLogin() {
            val to_main = Intent(this, MainActivity::class.java)
            to_main.run {
                to_main.putExtra("ap", user_info)
                startActivity(to_main)
            }
        }

        button.setOnClickListener {
            val idStr = id.text.toString()
            val pwStr = password.text.toString()
            service.login(idStr, pwStr).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val result = response.body()
                    Log.d("로그인", "${result}")
                    if(result?.success == "true"){
                        user_info = result
                        successLogin()
                    }else{
                        Toast.makeText(applicationContext, "올바르지 않는 ID/PW", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("로그인", "${t.localizedMessage}")
                }
            })
        }


        btnRegister.setOnClickListener {
            Intent(this, registration::class.java).run {
                startActivity(this)
            }
        }
    }
}

interface SignService {
    @FormUrlEncoded
    @POST("Login.php")
    fun login(@Field("id") id:String, @Field("password") password:String) : Call<LoginResponse>
}