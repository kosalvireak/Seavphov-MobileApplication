package kh.edu.rupp.seavphov.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationBarView
import kh.edu.rupp.seavphov.R
import kh.edu.rupp.seavphov.databinding.ActivityMainBinding
import kh.edu.rupp.seavphov.model.LoginResponse

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
        // Hide button and top nav for login and sign-up
        hideOrShowNavigationListener()

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

    private fun hideOrShowNavigationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.signUpFragment -> {
                    hideBottomNavigation()
                    hideTopNavigation()
                }

                else -> {
                    showTopNavigation()
                    showBottomNavigation()
                }
            }
        }

    }

    private fun handleOnNavigationItemsSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> navController.navigate(R.id.homeFragment)
            R.id.menu_category -> navController.navigate(R.id.categoryFragment)
            R.id.menu_add_book -> checkPermissionPage(R.id.addBookFragment)
            R.id.menu_notification -> navController.navigate(R.id.notificationFragment)
            else -> checkAndProcessProfilePage()
        }
        return true
    }

    private fun checkPermissionPage(resId: Int) {
        if (isUserLoggedIn()) {
            navController.navigate(resId)
        } else {
            navController.navigate(R.id.noPermissionFragment)
        }
    }

    private fun checkAndProcessProfilePage() {
        if (isUserLoggedIn()) {
            navController.navigate(R.id.profileFragment)
            showTopNavigation()
            showBottomNavigation()
        } else {
            navController.navigate(R.id.loginFragment)
        }
    }


    fun saveLoginState(context: Context, isLoggedIn: Boolean, loginResponse: LoginResponse) {
        Log.d("Seavphov", "saveLoginState isLoggedIn $isLoggedIn loginResponse $loginResponse")
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Save the login state and authentication token
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.putString("gmail", loginResponse.gmail)
        editor.putString("name", loginResponse.name)
        editor.putString("imgUrl", loginResponse.imgUrl)
        editor.putString("token", loginResponse.token)
        editor.apply() // Save changes
        checkAndProcessProfilePage()
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = baseContext.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        Log.d("Seavphov", "isUserLoggedIn $isLoggedIn")
        return isLoggedIn;
    }

    private fun getAuthToken(): String? {
        val sharedPreferences = baseContext.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

    fun getUserInfo(): LoginResponse {
        val sharedPreferences = baseContext.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        return LoginResponse(
            sharedPreferences.getString("gmail", null),
            sharedPreferences.getString("name", null),
            sharedPreferences.getString("imgUrl", null),
            getAuthToken()
        )
    }

    fun clearLoginState() {
        val sharedPreferences = baseContext.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        Log.d("Seavphov", "clearLoginState")
        // Clear login state and authentication token
        editor.clear()
        editor.apply() // Save changes
        checkAndProcessProfilePage()
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