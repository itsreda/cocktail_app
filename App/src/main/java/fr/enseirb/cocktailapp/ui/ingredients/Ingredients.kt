package fr.enseirb.cocktailapp.ui.ingredients

data class IngredientsData(val drinks: List<Ingredients>){
    fun size(): Int {
        if (drinks!= null) return drinks.size
        return 0
    }
    operator fun get(position: Int): Ingredients {
        return drinks[position]
    }
}

data class Ingredients (
    val strIngredient1: String
        )
