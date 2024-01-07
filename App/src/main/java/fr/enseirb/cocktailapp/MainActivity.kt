package fr.enseirb.cocktailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import fr.enseirb.cocktailapp.databinding.ActivityMainBinding
import fr.enseirb.cocktailapp.ui.categories.CategoriesFragment
import fr.enseirb.cocktailapp.ui.favorites.FavoritesFragment
import fr.enseirb.cocktailapp.ui.ingredients.IngredientsFragment
import fr.enseirb.cocktailapp.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = findViewById<ImageButton>(R.id.button)

        button.setOnClickListener {

            replaceFragment(SearchFragment())
        }
      
        replaceFragment(SearchFragment())


        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Home -> replaceFragment(SearchFragment())
                R.id.Ingredients -> replaceFragment(IngredientsFragment())
                R.id.Categories -> replaceFragment(CategoriesFragment())
                R.id.Favorites -> replaceFragment(FavoritesFragment())
                else -> {}
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


}