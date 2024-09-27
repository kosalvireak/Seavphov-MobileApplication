package kh.edu.rupp.seavphov.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;

import kh.edu.rupp.seavphov.R;
import kh.edu.rupp.seavphov.databinding.ActivityMainBinding;
import kh.edu.rupp.seavphov.fragment.AddBookFragment;
import kh.edu.rupp.seavphov.fragment.HomeFragment;
import kh.edu.rupp.seavphov.fragment.ProfileFragment;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HomeFragment homeFragment = new HomeFragment();
        showFragment(homeFragment);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return handleOnNavigationItemsSelected(menuItem);
            }
        });
    }

    private boolean handleOnNavigationItemsSelected(MenuItem item){
        if (item.getItemId() == R.id.menu_home){
            showFragment(new HomeFragment());
        } else if (item.getItemId() == R.id.menu_add_book){
            showFragment(new AddBookFragment());
        } else {
            showFragment(new ProfileFragment());
        }
        return true;
    }

    public void showFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(binding.lytFragment.getId(),fragment);

        fragmentTransaction.commit();
    }
}