package fr.enseirb.cocktailapp.ui.search
import android.app.Activity
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
class CocktailAdapter(private var cocktailList: CocktailData, private val activity: FragmentActivity?) : RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {
    private val client = OkHttpClient()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_design, parent, false)

        return CocktailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cocktailList.size()
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        var cocktail = cocktailList[position]

        holder.textView.text = cocktail.strDrink
        val imageUrl = cocktail.strDrinkThumb
        Picasso.get().load(imageUrl).into(holder.imageView)
    }

    fun updateData(newText: String?) {
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
                    val cocktailData = Gson().fromJson(responseBody, CocktailData::class.java)
                    cocktailList = cocktailData
                    activity?.runOnUiThread{
                        notifyDataSetChanged()
                    }
                }
            })
    }

    fun updateCocktailList(cocktailData: CocktailData?){
        if(cocktailData != null){
            cocktailList = cocktailData
            activity?.runOnUiThread{
                notifyDataSetChanged()
            }
        }
    }

    class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.findViewById(R.id.textView)
        val imageView : ImageView = itemView.findViewById(R.id.imageId)
    }

}
