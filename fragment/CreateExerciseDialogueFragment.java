package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.fitlifefitnesstracker.R;

public class CreateExerciseDialogueFragment extends DialogFragment {

    private EditText editTextExerciseName;
    private Button buttonSaveExercise;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_exercise_dialogue, container, false);

        // Initialize the views
        editTextExerciseName = view.findViewById(R.id.editTextExerciseName);
        buttonSaveExercise = view.findViewById(R.id.buttonSaveExercise);

        // Set up the button click listener
        buttonSaveExercise.setOnClickListener(v -> {
            String exerciseName = editTextExerciseName.getText().toString();
            if (exerciseName.isEmpty()) {
                Toast.makeText(getContext(), "Please enter an exercise name", Toast.LENGTH_SHORT).show();
            } else {
                Fragment targetFragment = getTargetFragment();
                if (targetFragment instanceof ExerciseFragment) {
                    ExerciseFragment exerciseFragment = (ExerciseFragment) targetFragment;
                    exerciseFragment.addNewExercise(exerciseName);
                    dismiss(); // Close the dialog
                } else {
                    Toast.makeText(getContext(), "Unable to access ExerciseFragment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
