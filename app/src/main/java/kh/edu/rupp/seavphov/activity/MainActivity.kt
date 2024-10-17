package kh.edu.rupp.seavphov.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kh.edu.rupp.seavphov.R
import kh.edu.rupp.seavphov.databinding.ActivityMainBinding
import kh.edu.rupp.seavphov.fragment.AddBookFragment
import kh.edu.rupp.seavphov.fragment.HomeFragment
import kh.edu.rupp.seavphov.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        val homeFragment = HomeFragment()
        showFragment(homeFragment)
        binding!!.bottomNavigationView.setOnItemSelectedListener { menuItem -> handleOnNavigationItemsSelected(menuItem) }
    }

    private fun handleOnNavigationItemsSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_home) {
            showFragment(HomeFragment())
        } else if (item.itemId == R.id.menu_add_book) {
            showFragment(AddBookFragment())
        } else {
            showFragment(ProfileFragment())
        }
        return true
    }

    fun showFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding!!.lytFragment.id, fragment!!)
        fragmentTransaction.commit()
    }
}