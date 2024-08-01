package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlifefitnesstracker.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AvailableExercisesAdapter;
import adapter.SelectedExercisesAdapter;
import database.Repository;
import entities.Workout;

public class ExerciseFragment extends Fragment {

    private static final String ARG_USER_ID = "userId";
    private int userId;

    public static ExerciseFragment newInstance(int userId) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView workoutTitle, selectExercisesTitle, selectedExercisesTitle;
    private EditText editTextWorkoutName, editTextWorkoutDuration;
    private Spinner spinnerWorkoutType;
    private Button buttonAddWorkout, buttonCreateNewExercise, buttonAddSelectedExercises;
    private RecyclerView recyclerViewAvailableExercises, recyclerViewSelectedExercises;

    private List<String> availableExercises = new ArrayList<>();
    private List<String> selectedExercises = new ArrayList<>();
    private AvailableExercisesAdapter availableExercisesAdapter;
    private SelectedExercisesAdapter selectedExercisesAdapter;
    private Repository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        // Retrieve userId from arguments
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
        }

        repository = new Repository(requireActivity().getApplication());

        // Initialize the views
        workoutTitle = view.findViewById(R.id.workoutTitle);
        selectExercisesTitle = view.findViewById(R.id.selectExercisesTitle);
        selectedExercisesTitle = view.findViewById(R.id.selectedExercisesTitle);
        editTextWorkoutName = view.findViewById(R.id.editTextWorkoutName);
        spinnerWorkoutType = view.findViewById(R.id.spinnerWorkoutType);
        editTextWorkoutDuration = view.findViewById(R.id.editTextWorkoutDuration);
        buttonAddWorkout = view.findViewById(R.id.buttonAddWorkout);
        buttonCreateNewExercise = view.findViewById(R.id.buttonCreateNewExercise);
        buttonAddSelectedExercises = view.findViewById(R.id.buttonAddSelectedExercises);
        recyclerViewAvailableExercises = view.findViewById(R.id.recyclerViewAvailableExercises);
        recyclerViewSelectedExercises = view.findViewById(R.id.recyclerViewSelectedExercises);

        // Setup RecyclerViews
        recyclerViewAvailableExercises.setLayoutManager(new LinearLayoutManager(requireContext()));
        availableExercisesAdapter = new AvailableExercisesAdapter(availableExercises, this::onExerciseClick);
        recyclerViewAvailableExercises.setAdapter(availableExercisesAdapter);

        recyclerViewSelectedExercises.setLayoutManager(new LinearLayoutManager(requireContext()));
        selectedExercisesAdapter = new SelectedExercisesAdapter(selectedExercises);
        recyclerViewSelectedExercises.setAdapter(selectedExercisesAdapter);

        // Create listeners
        buttonAddWorkout.setOnClickListener(v -> addWorkout());
        buttonCreateNewExercise.setOnClickListener(v -> showCreateExerciseLayout());
        buttonAddSelectedExercises.setOnClickListener(v -> addSelectedExercisesToWorkout());

        // Populate the Spinner with workout type options
        ArrayAdapter<CharSequence> workoutTypeAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.workout_type_array, android.R.layout.simple_spinner_item);
        workoutTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkoutType.setAdapter(workoutTypeAdapter);

        return view;
    }

    private void addWorkout() {
        String workoutName = editTextWorkoutName.getText().toString();
        String workoutType = spinnerWorkoutType.getSelectedItem().toString();
        String workoutDurationStr = editTextWorkoutDuration.getText().toString();

        if (workoutName.isEmpty() || workoutType.isEmpty() || workoutDurationStr.isEmpty() || selectedExercises.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields and select exercises", Toast.LENGTH_SHORT).show();
            return;
        }

        int workoutDuration;
        try {
            workoutDuration = Integer.parseInt(workoutDurationStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter a valid duration", Toast.LENGTH_SHORT).show();
            return;
        }

        Workout workout = new Workout(0, userId, workoutName, workoutType, workoutDuration);
        repository.insert(workout);

        // Clear input fields after adding workout
        editTextWorkoutName.setText("");
        spinnerWorkoutType.setSelection(0);
        editTextWorkoutDuration.setText("");
        selectedExercises.clear();
        selectedExercisesAdapter.notifyDataSetChanged();

        Toast.makeText(getContext(), "Workout Added!", Toast.LENGTH_SHORT).show();
    }

    private void showCreateExerciseLayout() {
        CreateExerciseDialogueFragment createExerciseDialog = new CreateExerciseDialogueFragment();
        createExerciseDialog.setTargetFragment(this, 0); // Set target fragment
        createExerciseDialog.show(getParentFragmentManager(), "createExercise");
    }

    private void addSelectedExercisesToWorkout() {
        // Logic to add selected exercises to workout (if needed)
        selectedExercisesAdapter.notifyDataSetChanged();
    }

    private void onExerciseClick(int position, boolean isChecked) {
        String exercise = availableExercises.get(position);
        if (isChecked) {
            if (!selectedExercises.contains(exercise)) {
                selectedExercises.add(exercise);
            }
        } else {
            selectedExercises.remove(exercise);
        }
        selectedExercisesAdapter.notifyDataSetChanged();
    }

    public void addNewExercise(String exerciseName) {
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            Toast.makeText(getContext(), "Exercise name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        availableExercises.add(exerciseName);
        availableExercisesAdapter.notifyDataSetChanged();
    }


}
