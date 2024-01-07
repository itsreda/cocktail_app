package fr.enseirb.cocktailapp.ui.search
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.enseirb.cocktailapp.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL


class SearchFragment : Fragment() {
    private lateinit var listView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: CocktailAdapter
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.recycler_view)
        searchView = view.findViewById(R.id.searchView)

        listView.layoutManager = LinearLayoutManager(activity)

        adapter = CocktailAdapter(CocktailData(emptyList()), activity)
        listView.adapter = adapter

        setAdapter()
        setupSearchView()
    }

    private fun setAdapter() {
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
                    adapter.updateCocktailList(cocktailData)


                }
            })
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                adapter.updateData(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.updateData(newText)

                return true
            }
        })
    }

}