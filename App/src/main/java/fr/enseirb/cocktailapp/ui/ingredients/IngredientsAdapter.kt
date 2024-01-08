package fr.enseirb.cocktailapp.ui.ingredients

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
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

class IngredientsAdapter(private var IngredientsList: IngredientsData, private val activity: FragmentActivity?) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_design, parent, false)

        return IngredientsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return IngredientsList.size()
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = IngredientsList[position]

        holder.textView.text = ingredient.strIngredient1

        val imageUrl = "https://www.thecocktaildb.com/images/ingredients/${ingredient.strIngredient1}-Small.png"

        Picasso.get().load(imageUrl).into(holder.imageView)
    }

    fun updateData(newText: String?, client: OkHttpClient) {
        Log.i("UpdateData", "Func called")

        val url = URL("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=$newText")
        val request = Request.Builder().url(url).build()
        client
            .newCall(request)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i("OKHTTP", "OnFailure: ${e.localizedMessage}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.i("OKHTTP", "OnSuccess")

                    val responseBody = response.body?.string()
                    val cocktailData = Gson().fromJson(responseBody, IngredientsData::class.java)
                    IngredientsList = cocktailData
                    activity?.runOnUiThread{
                        notifyDataSetChanged()
                    }
                }
            })
    }

    fun updateIngredientsList(ingredientsData: IngredientsData?){
        if(ingredientsData != null){
            IngredientsList = ingredientsData
            activity?.runOnUiThread{
                notifyDataSetChanged()
            }
        }
    }

    class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.findViewById(R.id.textView)
        val imageView : ImageView = itemView.findViewById(R.id.imageId)
    }

}
