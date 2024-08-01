package fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitlifefitnesstracker.R;
import ViewModel.SharedViewModel;

public class DashboardFragment extends Fragment {

    private TextView userNameTextView;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Initialize the shared ViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Initialize the views
        userNameTextView = view.findViewById(R.id.userNameTextView);

        // Set click listeners
        view.findViewById(R.id.yogaCard).setOnClickListener(this::openYogaRoutineFragment);
        view.findViewById(R.id.weightliftingCard).setOnClickListener(this::openWeightliftingRoutineFragment);
        view.findViewById(R.id.bodyweightCard).setOnClickListener(this::openBodyweightRoutineFragment);
        view.findViewById(R.id.cardioCard).setOnClickListener(this::openCardioRoutineFragment);

        return view;
    }

    public void openYogaRoutineFragment(View view) {
        replaceFragment(new YogaRoutineFragment());
    }

    public void openWeightliftingRoutineFragment(View view) {
        replaceFragment(new WeightliftingRoutineFragment());
    }

    public void openBodyweightRoutineFragment(View view) {
        replaceFragment(new BodyweightRoutineFragment());
    }

    public void openCardioRoutineFragment(View view) {
        replaceFragment(new CardioRoutineFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
