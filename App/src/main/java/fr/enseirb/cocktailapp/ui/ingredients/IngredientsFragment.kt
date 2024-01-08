package fr.enseirb.cocktailapp.ui.ingredients

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.enseirb.cocktailapp.R
import fr.enseirb.cocktailapp.ui.IngredientActivity
import fr.enseirb.cocktailapp.ui.RecipeActivity
import fr.enseirb.cocktailapp.ui.search.CocktailAdapter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL


class IngredientsFragment : Fragment() {
    private lateinit var listView: RecyclerView
    private lateinit var adapter: IngredientsAdapter
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.recycler_view)

        listView.layoutManager = LinearLayoutManager(activity)

        adapter = IngredientsAdapter(IngredientsData(emptyList()), activity)
        listView.adapter = adapter
        adapter.setOnItemClicklistener(object : IngredientsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {


                val intent = Intent(activity, IngredientActivity::class.java)
                intent.putExtra("ingredientName", adapter.getItemName(position))
                startActivity(intent)

            }
        })

        setAdapter()
    }

    private fun setAdapter() {
        val url = URL("https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list")
        val request = Request.Builder().url(url).build()
        client
            .newCall(request)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i("OKHTTP", "OnFailure: ${e.localizedMessage}")
                }
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    val ingredientsData = Gson().fromJson(responseBody, IngredientsData::class.java)
                    adapter.updateIngredientsList(ingredientsData)

                }
            })
    }

}