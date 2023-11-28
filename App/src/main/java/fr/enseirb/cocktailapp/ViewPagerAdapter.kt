import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.appcompat.app.AppCompatActivity

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private val TAB_COUNT = 3

    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchFragment() // Replace with your actual SearchFragment
            1 -> CategoriesFragment() // Replace with your actual CategoriesFragment
            2 -> IngredientsFragment() // Replace with your actual IngredientsFragment
            else -> Fragment() // Fallback for any other position
        }
    }
}
