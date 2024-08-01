package com.example.fitlifefitnesstracker.UI;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitlifefitnesstracker.R;
import com.example.fitlifefitnesstracker.databinding.ActivityInterfaceBinding;

import fragment.DashboardFragment;
import fragment.ExerciseFragment;
import fragment.FoodFragment;
import fragment.ProfileFragment;

public class Interface extends AppCompatActivity {

    ActivityInterfaceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInterfaceBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFragment(new DashboardFragment());

        binding.bottomNavBar.setOnItemSelectedListener(item -> {


            int itemId = item.getItemId();

            if (itemId == R.id.dashboard) {
                replaceFragment(new DashboardFragment());
            } else if (itemId == R.id.workout) {
                replaceFragment(new ExerciseFragment());
            } else if (itemId == R.id.food) {
                replaceFragment(new FoodFragment());
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }

            return true;
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}