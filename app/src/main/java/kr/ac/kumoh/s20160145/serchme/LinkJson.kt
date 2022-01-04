package kr.ac.kumoh.s20160145.serchme

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import kr.ac.kumoh.s20160145.serchme.databinding.ActivityShowRcpDetailBinding

class LinkJson() : AppCompatActivity() {
    private lateinit var view: ActivityShowRcpDetailBinding
    private lateinit var model: JsonViewModel
    private val mAdapter = JSAdapter()
    var rcp_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityShowRcpDetailBinding.inflate(layoutInflater)
        setContentView(view.root)

        model = ViewModelProvider(this).get(JsonViewModel::class.java)
        rcp_name = intent.getStringExtra("RCPNAME")
        if (rcp_name == null) {Log.d("이름전달안됨", "전달안됨")}
        if (rcp_name != null) {Log.d("이름전달잘됨", "전달잘됨")}
        //Log.d("이름전달확인", rcp_name)

        view.JSlist.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }

        model.list.observe(this,
            Observer<ArrayList<JsonViewModel.JS>> {
                mAdapter.notifyDataSetChanged()
            })
        if (rcp_name != null) {model.getRcpName(rcp_name)}

        model.requestJS()

    }

    inner class JSAdapter: RecyclerView.Adapter<JSAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val RcpImg: ImageView = itemView.findViewById(R.id.img_rcp)
            val RcpExp: TextView = itemView.findViewById(R.id.text_rcp)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_recycler_ex, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) { // 메뉴별 조리법 순서대로 불러오기
            Glide.with(this@LinkJson).load(model.getJS(position).rcp_img).into(holder.RcpImg)
            holder.RcpExp.text = model.getJS(position).explain
        }

        override fun getItemCount() = model.getSize()
    }

}