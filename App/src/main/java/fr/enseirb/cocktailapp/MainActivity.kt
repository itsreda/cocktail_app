package fr.enseirb.cocktailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import fr.enseirb.cocktailapp.databinding.ActivityMainBinding
import fr.enseirb.cocktailapp.ui.categories.CategoriesFragment
import fr.enseirb.cocktailapp.ui.home.HomeFragment
import fr.enseirb.cocktailapp.ui.ingredients.IngredientsFragment
import fr.enseirb.cocktailapp.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = findViewById<ImageButton>(R.id.button)

        button.setOnClickListener {

            replaceFragment(SearchFragment(),  R.string.search_title)
        }
      
        replaceFragment(HomeFragment(),  R.string.home_title)


        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Home -> replaceFragment(HomeFragment(), R.string.home_title)
                R.id.Ingredients -> replaceFragment(IngredientsFragment(), R.string.ingredients_title)
                R.id.Categories -> replaceFragment(CategoriesFragment(), R.string.categories_title)
                else -> {}
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment, title: Int){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        var titleView = findViewById<Toolbar>(R.id.topAppBar)
        this.title = getString(title)
        titleView.title = this.title

    }


}