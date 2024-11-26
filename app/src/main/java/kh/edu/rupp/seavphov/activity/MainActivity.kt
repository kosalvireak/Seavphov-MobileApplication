package kh.edu.rupp.seavphov.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kh.edu.rupp.seavphov.R
import kh.edu.rupp.seavphov.databinding.ActivityMainBinding
import kh.edu.rupp.seavphov.fragment.AddBookFragment
import kh.edu.rupp.seavphov.fragment.BookDetailFragment
import kh.edu.rupp.seavphov.fragment.CategoryFragment
import kh.edu.rupp.seavphov.fragment.HomeFragment
import kh.edu.rupp.seavphov.fragment.LoginFragment
import kh.edu.rupp.seavphov.fragment.NotificationFragment
import kh.edu.rupp.seavphov.model.BookDetail

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        showFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            menuItem -> handleOnNavigationItemsSelected(menuItem)
        }
    }

    //    Bottom Navigation
    private fun handleOnNavigationItemsSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> showFragment(HomeFragment())
            R.id.menu_category ->  showFragment(CategoryFragment())
            R.id.menu_add_book ->  showFragment(BookDetailFragment())
            R.id.menu_notification -> showFragment(NotificationFragment())
            else -> showFragment(LoginFragment())
        }
        return true
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.lytFragment.id, fragment)
        fragmentTransaction.commit()

//        supportFragmentManager.beginTransaction().apply {
//            replace(binding.lytFragment.id, fragment)
//            commit()
//        }
    }
}