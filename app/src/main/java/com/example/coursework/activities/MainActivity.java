package com.example.coursework.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;


import android.os.Bundle;
import com.example.coursework.R;
import com.example.coursework.database.HikeDatabase;
import com.example.coursework.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new MainFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home){
                replaceFragment(new MainFragment());
            } else if (item.getItemId() == R.id.navigation_add) {
                replaceFragment(new AddFragment());
            } else if (item.getItemId() == R.id.navigation_search) {
                replaceFragment(new SearchFragment());
            }
            return true;
        });



    }
    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_main,fragment);
        fragmentTransaction.commit();
    }

}
