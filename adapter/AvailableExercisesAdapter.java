package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlifefitnesstracker.R;

import java.util.List;

public class AvailableExercisesAdapter extends RecyclerView.Adapter<AvailableExercisesAdapter.ExerciseViewHolder> {

    private List<String> exercises;
    private OnExerciseClickListener listener;

    public interface OnExerciseClickListener {
        void onExerciseClick(int position, boolean isChecked);
    }

    public AvailableExercisesAdapter(List<String> exercises, OnExerciseClickListener listener) {
        this.exercises = exercises;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        String exercise = exercises.get(position);
        holder.textViewExercise.setText(exercise);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onExerciseClick(position, isChecked));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewExercise;
        CheckBox checkBox;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewExercise = itemView.findViewById(R.id.textViewExercise);
            checkBox = itemView.findViewById(R.id.checkBoxExercise);
        }
    }
}
