package kh.edu.rupp.seavphov.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationBarView
import kh.edu.rupp.seavphov.R
import kh.edu.rupp.seavphov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView.labelVisibilityMode =
            NavigationBarView.LABEL_VISIBILITY_UNLABELED
        binding.bottomNavigationView.isItemHorizontalTranslationEnabled = false

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Start Fragment
        navController.navigate(R.id.homeFragment)

        // Handle on click
        binding.searchButton.setOnClickListener {
            navController.navigate(R.id.categoryFragment)
            binding.bottomNavigationView.selectedItemId = R.id.menu_category
        }

        binding.seavphovLogo.setOnClickListener {
            navController.navigate(R.id.homeFragment)
            selectHomeNavigation()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            handleOnNavigationItemsSelected(menuItem)
        }
    }

    private fun handleOnNavigationItemsSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> navController.navigate(R.id.homeFragment)
            R.id.menu_category -> navController.navigate(R.id.categoryFragment)
            R.id.menu_add_book -> navController.navigate(R.id.addBookFragment)
            R.id.menu_notification -> navController.navigate(R.id.notificationFragment)
            else -> navController.navigate(R.id.profileFragment)
        }
        return true
    }

    fun selectHomeNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.menu_home
    }


    fun hideTopNavigation() {
        binding.navbarContainer.visibility = View.GONE
    }

    fun showTopNavigation() {
        binding.navbarContainer.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }
}