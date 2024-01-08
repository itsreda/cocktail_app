package fr.enseirb.cocktailapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.enseirb.cocktailapp.R
import fr.enseirb.cocktailapp.ui.ingredients.IngredientsData
import fr.enseirb.cocktailapp.ui.search.CocktailData
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL

class HomeFragment : Fragment() {
    private val client = OkHttpClient()
    private lateinit var drink1: TextView
    private lateinit var drink2: TextView
    private lateinit var drink3: TextView

    private lateinit var ingredient1: TextView
    private lateinit var ingredient2: TextView
    private lateinit var ingredient3: TextView

    private lateinit var imgDrink1: ImageView
    private lateinit var imgDrink2: ImageView
    private lateinit var imgDrink3: ImageView

    private lateinit var imgIngredient1: ImageView
    private lateinit var imgIngredient2: ImageView
    private lateinit var imgIngredient3: ImageView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drink1= view.findViewById<View>(R.id.drink1).findViewById(R.id.categoryName)
        drink2= view.findViewById<View>(R.id.drink2).findViewById(R.id.categoryName)
        drink3= view.findViewById<View>(R.id.drink3).findViewById(R.id.categoryName)

        imgDrink1= view.findViewById<View>(R.id.drink1).findViewById(R.id.categoryImage)
        imgDrink2= view.findViewById<View>(R.id.drink2).findViewById(R.id.categoryImage)
        imgDrink3= view.findViewById<View>(R.id.drink3).findViewById(R.id.categoryImage)


        ingredient1= view.findViewById<View>(R.id.ingredient1).findViewById(R.id.categoryName)
        ingredient2= view.findViewById<View>(R.id.ingredient2).findViewById(R.id.categoryName)
        ingredient3= view.findViewById<View>(R.id.ingredient3).findViewById(R.id.categoryName)

        imgIngredient1= view.findViewById<View>(R.id.ingredient1).findViewById(R.id.categoryImage)
        imgIngredient2= view.findViewById<View>(R.id.ingredient2).findViewById(R.id.categoryImage)
        imgIngredient3= view.findViewById<View>(R.id.ingredient3).findViewById(R.id.categoryImage)

        setView()
    }

    private fun setView() {
        val url = URL("https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=Cocktail")
        val request = Request.Builder().url(url).build()
        client
            .newCall(request)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i("OKHTTP", "OnFailure: ${e.localizedMessage}")
                }
                override fun onResponse(call: Call, response: Response) {


                    val responseBody = response.body?.string()
                    val cocktailData = Gson().fromJson(responseBody, CocktailData::class.java)
                    activity?.runOnUiThread {
                        drink1.text = cocktailData[0].strDrink
                        drink2.text = cocktailData[1].strDrink
                        drink3.text = cocktailData[2].strDrink

                        val image1Url = cocktailData[0].strDrinkThumb
                        Picasso.get().load(image1Url).into(imgDrink1)
                        val image2Url = cocktailData[1].strDrinkThumb
                        Picasso.get().load(image2Url).into(imgDrink2)
                        val image3Url = cocktailData[2].strDrinkThumb
                        Picasso.get().load(image3Url).into(imgDrink3)
                    }
                }
            })


        val url2 = URL("https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list")
        val request2 = Request.Builder().url(url2).build()
        client
            .newCall(request2)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i("OKHTTP", "OnFailure: ${e.localizedMessage}")
                }
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    val ingredientsData = Gson().fromJson(responseBody, IngredientsData::class.java)

                    activity?.runOnUiThread {
                        ingredient1.text = ingredientsData[0].strIngredient1
                        ingredient2.text = ingredientsData[1].strIngredient1
                        ingredient3.text = ingredientsData[2].strIngredient1

                        val imageUrl1 =
                            "https://www.thecocktaildb.com/images/ingredients/${ingredientsData[0].strIngredient1}-Small.png"
                        Picasso.get().load(imageUrl1).into(imgIngredient1)
                        val imageUrl2 =
                            "https://www.thecocktaildb.com/images/ingredients/${ingredientsData[1].strIngredient1}-Small.png"
                        Picasso.get().load(imageUrl2).into(imgIngredient2)
                        val imageUrl3 =
                            "https://www.thecocktaildb.com/images/ingredients/${ingredientsData[2].strIngredient1}-Small.png"
                        Picasso.get().load(imageUrl3).into(imgIngredient3)

                    }


                }
            })
    }
}