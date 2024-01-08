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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL

class RecipeActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    private lateinit var cocktailName: TextView
    private lateinit var cocktailimg: ImageView
    private lateinit var ingredients: TextView
    private lateinit var instructions: TextView
    private lateinit var glass: TextView
    private lateinit var category: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        cocktailName = findViewById(R.id.cocktailName)
        cocktailimg = findViewById(R.id.cocktailImg)
        ingredients = findViewById(R.id.ingredientId)
        instructions = findViewById(R.id.instructions)
        glass = findViewById(R.id.glass)
        category = findViewById(R.id.drink_category)

        val bundle : Bundle? = intent.extras
        val idDrink = bundle!!.getLong("idDrink")

        setView(idDrink)
    }

    fun setView(id: Long) {
        val url = URL("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=$id")
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
                    val recipeData = Gson().fromJson(responseBody, RecipeData::class.java)

                    runOnUiThread {
                        cocktailName.text = recipeData[0].strDrink

                        val imageUrl = recipeData[0].strDrinkThumb
                        Picasso.get().load(imageUrl).into(cocktailimg)

                        var i = 1
                        var str = ""
                        var s = ""
                        while (true) {

                            val strIngredient =
                                recipeData[0].javaClass.getDeclaredField("strIngredient$i")
                                    .apply { isAccessible = true }.get(recipeData[0]) as String?
                            val sIngredient =
                                recipeData[0].javaClass.getDeclaredField("strMeasure$i")
                                    .apply { isAccessible = true }.get(recipeData[0]) as String?
                            if (strIngredient.isNullOrEmpty()) break
                            str = "$str $strIngredient ($sIngredient) \n"
                            i++
                        }
                        ingredients.text = str
                        instructions.text = recipeData[0].strInstructions
                        glass.text = recipeData[0].strGlass
                        category.text = recipeData[0].strCategory
                    }

                }
            })
    }
}