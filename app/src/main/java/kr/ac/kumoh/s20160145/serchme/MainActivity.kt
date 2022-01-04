package kr.ac.kumoh.s20160145.serchme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.kumoh.s20160145.serchme.databinding.ActivityMainBinding
import kr.ac.kumoh.s20160145.serchme.databinding.ActivityShowrecipesBinding
import java.io.File
import java.io.FileReader
import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
//    private var allergy: String? = null
//    var alle: MutableMap<String, Int> = mutableMapOf("a_peanut" to 1, "a_welnut" to 0, "a_salmon" to 0, "a_shrimp" to 0, "a_wheat" to 0,
//        "a_milk" to 0, "a_crusta" to 0, "a_peach" to 0, "a_lacquer" to 0) // 알러지 넘어왔을때 저장용
    lateinit var ap_data: LoginResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        view = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(view.root)

//        allergy = intent.getStringExtra("preference") // 로그인화면에서 다음화면으로 넘어올때 알러지 받아오는 용도
//        Log.d("알러지조사", allergy!!)
//        val pref_list = allergy!!.split(", ")
//        for(i in 0..8) {
//            val temp_parse = pref_list[i].split("=")
//            alle.put(temp_parse[0], temp_parse[1].toInt())
//            Log.d("알러지파싱", temp_parse[0] + "  " + temp_parse[1] + i.toString())
//        }
//        Log.d("맵적용확인", alle.toString()) // 알러지 관련파트 끝 알러지 넘어온것 확인용

        ap_data = intent.getSerializableExtra("ap") as LoginResponse
        Log.d("로그인시 넘어온 알러지: 땅콩", ap_data.a_peanut.toString())
        Log.d("로그인시 넘어온 알러지: 호두", ap_data.a_welnut.toString())
        Log.d("로그인시 넘어온 알러지: 연어", ap_data.a_salmon.toString())
        Log.d("로그인시 넘어온 알러지: 새우", ap_data.a_shrimp.toString())
        Log.d("로그인시 넘어온 알러지: 밀가루", ap_data.a_wheat.toString())
        Log.d("로그인시 넘어온 알러지: 우유", ap_data.a_milk.toString())
        Log.d("로그인시 넘어온 선호도: 끓이기", ap_data.p_boil.toString())
        Log.d("로그인시 넘어온 선호도: 튀기기", ap_data.p_fry.toString())


        val Ingredient = resources.getStringArray(R.array.INGREDIENT)
        var selectedIngredient = arrayListOf<String>()


        val ingredient_Adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Ingredient)
        val selectedIngredient_Adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedIngredient)

        view.ingredientList.adapter = ingredient_Adapter
        view.selectedIngredientList.adapter = selectedIngredient_Adapter

        view.Search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        view.ingredientList.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        view.ingredientList.setOnItemClickListener{ parent, view, position, id ->
            Toast.makeText(this, Ingredient.get(position) + "를 클릭하셨습니다.", Toast.LENGTH_SHORT).show()
            selectedIngredient.add(Ingredient.get(position))
            selectedIngredient_Adapter.notifyDataSetChanged()
        }
        val RecipeIntent = Intent(this, RecipeChoose::class.java)
        view.toRcpChoose.setOnClickListener {
            RecipeIntent.putExtra("ingredient", selectedIngredient)
            RecipeIntent.putExtra("ap_list", ap_data)
            startActivity(RecipeIntent)
        }
    }
    private fun search(Text:String){
        if(Text.length == 0) {

        }
    }
}