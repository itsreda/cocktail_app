package fr.enseirb.cocktailapp.ui

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.enseirb.cocktailapp.R
import fr.enseirb.cocktailapp.ui.search.CocktailData
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL

class CocktailActivity : AppCompatActivity() {
    private lateinit var cocktailName: TextView
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)

        cocktailName = findViewById(R.id.cocktailName)


        val bundle : Bundle? = intent.extras
        val category = bundle!!.getString("categoryName")

        setView(category)
    }


    fun setView(id: String?) {
        val url = URL("https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=$id")
        val request = Request.Builder().url(url).build()
        client
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i("OKHTTP", "OnFailure: ${e.localizedMessage}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.i("OKHTTP", "OnSuccess")

                    val responseBody = response.body?.string()
                    val recipeData = Gson().fromJson(responseBody, CocktailData::class.java)
                    var str = ""
                    runOnUiThread {
                        for(i in 0 until recipeData.size()){
                            str = str + "${recipeData[i].strDrink}\n"
                        }

                        cocktailName.text = str

                    }

                }
            })
    }
}