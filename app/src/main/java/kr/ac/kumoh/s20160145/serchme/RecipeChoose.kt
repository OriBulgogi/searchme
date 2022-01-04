package kr.ac.kumoh.s20160145.serchme

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kr.ac.kumoh.s20160145.serchme.databinding.ActivityRecipeChooseBinding
import kr.ac.kumoh.s20160145.serchme.databinding.ActivityShowRcpDetailBinding
import org.json.JSONObject
import java.util.ArrayList
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import android.content.Intent
import androidx.core.content.ContextCompat


class RecipeChoose() : AppCompatActivity() {
    private var ingr_list: ArrayList<String>? = null
    private lateinit var view: ActivityRecipeChooseBinding
    private lateinit var model: RecipeViewModel
    private val mAdapter = RCPAdapter()
    lateinit var ap_data: LoginResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityRecipeChooseBinding.inflate(layoutInflater)
        setContentView(view.root)
        ingr_list = intent.getStringArrayListExtra("ingredient")
        ap_data = intent.getSerializableExtra("ap_list") as LoginResponse
        Log.d("연결후 넘어온 재료 확인", ingr_list.toString())

        model = ViewModelProvider(this).get(RecipeViewModel::class.java)
        //if (ingr_list != null) {}
        model.get_ingredients(ingr_list!!)
        model.get_ap(ap_data!!)
        model.get_json(getJsonData()!!)



        view.ListRCP.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }

        model.list.observe(this,
            Observer<ArrayList<RecipeViewModel.rcp_checksum>> {
                mAdapter.notifyDataSetChanged()
            })

        //model.make_list()
    }

    private fun getJsonData(): JsonData? {
        val assetManager = resources.assets
        var result: JsonData? = null
        try {
            val inputStream = assetManager.open("menu_json.json")


            val reader = inputStream.bufferedReader()
            val gson = Gson()
            result = gson.fromJson(reader, JsonData::class.java)

        } catch (e:IOException) {
            e.printStackTrace()
            Log.d("불러오기", "에러")
        }
        Log.d("결과", result.toString())
        return result
    }


    inner class RCPAdapter: RecyclerView.Adapter<RCPAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val MenuName: Button = itemView.findViewById(R.id.menu_name)
            val CollectNum: TextView = itemView.findViewById(R.id.collect_num)
            val SelectedIngredients: TextView = itemView.findViewById(R.id.selected_ingredients)
            val NeededIngredients: TextView = itemView.findViewById(R.id.needed_ingredients)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.rcp_recycler_ex, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) { // 메뉴별 조리법 순서대로 불러오기
            holder.MenuName.text = model.first_filter.get(position).rcp_name
            holder.CollectNum.text = "일치하는 식재료 수: " + model.first_filter.get(position).rcp_have.toString()
            holder.SelectedIngredients.text = "일치하는 식재료: " + model.first_filter.get(position).have_ingr.toString()
            holder.NeededIngredients.text = "필요한 식재료: " + model.first_filter.get(position).no_ingr.toString()
            holder.MenuName.setOnClickListener {
                val intent = Intent(holder.itemView?.context, LinkJson::class.java)
                intent.putExtra("RCPNAME", model.first_filter.get(position).rcp_name)
                ContextCompat.startActivity(holder.itemView.context, intent, null)

                Log.d("추출확인", model.first_filter.get(position).rcp_name)
            }

        }

        override fun getItemCount() = model.getSize()
    }
}