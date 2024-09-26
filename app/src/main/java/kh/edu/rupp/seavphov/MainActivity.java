package kh.edu.rupp.seavphov;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kh.edu.rupp.seavphov.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}