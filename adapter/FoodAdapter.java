package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitlifefitnesstracker.R;
import entities.Food;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final Context context;
    private List<Food> foodList;
    private final OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Food food);
    }

    public FoodAdapter(Context context, List<Food> foodList, OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.foodList = foodList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.itemNameTextView.setText(food.getItemName());
        holder.caloriesTextView.setText(String.valueOf(food.getCalories()));
        holder.deleteFoodItem.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(food));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setFoodList(List<Food> newFoodList) {
        this.foodList = newFoodList;
        notifyDataSetChanged();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView caloriesTextView;
        ImageView deleteFoodItem;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            caloriesTextView = itemView.findViewById(R.id.caloriesTextView);
            deleteFoodItem = itemView.findViewById(R.id.deleteFoodItem);
        }
    }
}
