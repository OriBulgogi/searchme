package kr.ac.kumoh.s20160145.serchme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kr.ac.kumoh.s20160145.serchme.databinding.ActivityShowrecipesBinding

class showrecipes : AppCompatActivity() {
    private lateinit var view:ActivityShowrecipesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        view = ActivityShowrecipesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(view.root)
        //setContentView(R.layout.activity_showrecipes)
        val intentReselect = Intent(this, MainActivity::class.java)
        view.reselect.setOnClickListener {
            startActivity(intentReselect)
        }
    }
}