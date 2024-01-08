package fr.enseirb.cocktailapp.ui.search

data class CocktailData(val drinks: List<Cocktail>) {
    fun size(): Int {
        if (drinks!= null) return drinks.size
        return 0
    }
    operator fun get(position: Int): Cocktail {
        return drinks[position]
    }
}

data class Cocktail(
    val idDrink: Long,
    val strDrink: String,
    val strDrinkThumb: String,
    val strInstructions: String
)