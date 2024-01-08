package fr.enseirb.cocktailapp.ui.categories


data class CategoriesData(val drinks: List<Category>){
    fun size(): Int {
        if (drinks!= null) return drinks.size
        return 0
    }
    operator fun get(position: Int): Category {
        return drinks[position]
    }
}
data class Category (
    val strCategory: String)
