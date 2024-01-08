package fr.enseirb.cocktailapp.ui.categories

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.enseirb.cocktailapp.R
import fr.enseirb.cocktailapp.ui.search.CocktailAdapter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL

class CategoriesAdapter (private var categoriesList: CategoriesData, private val activity: FragmentActivity?) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private lateinit var mListener: CategoriesAdapter.OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_design, parent, false)

        return CategoriesViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return categoriesList.size()
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categoriesList[position]

        holder.textView.text = category.strCategory

        val imageName = getDrawableNameFromText(category.strCategory)
        val imageResourceId =
            activity?.resources?.getIdentifier(imageName, "drawable", activity.packageName)

        if (imageResourceId != null && imageResourceId != 0) {
            holder.imageView.setImageResource(imageResourceId)
        } else {
            holder.imageView.setImageResource(R.drawable.cocktail2)
        }

    }

    private fun getDrawableNameFromText(text: String): String {

        return text.toLowerCase().replace(" / ", "_").replace(" ", "_")
    }

    fun updateData(newText: String?, client: OkHttpClient) {
        Log.i("UpdateData", "Func called")

        val url = URL("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=$newText")
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
                    val cocktailData = Gson().fromJson(responseBody, CategoriesData::class.java)
                    categoriesList = cocktailData
                    activity?.runOnUiThread {
                        notifyDataSetChanged()
                    }
                }
            })
    }

    fun updateCategoriesList(categoriesData: CategoriesData?) {
        if (categoriesData != null) {
            categoriesList = categoriesData
            activity?.runOnUiThread {
                notifyDataSetChanged()
            }
        }
    }

    class CategoriesViewHolder(itemView: View, listener: CocktailAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.findViewById(R.id.textView)
        val imageView : ImageView = itemView.findViewById(R.id.imageId)

        init {

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

    interface OnItemClickListener : CocktailAdapter.OnItemClickListener {
        override fun onItemClick(position: Int)
    }

    fun setOnItemClicklistener(listener: OnItemClickListener){
        mListener = listener
    }

    fun getItemName(position: Int): String {
        return categoriesList[position].strCategory

    }

}
