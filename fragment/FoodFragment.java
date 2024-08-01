package fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlifefitnesstracker.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ViewModel.SharedViewModel;
import adapter.FoodAdapter;
import database.Repository;
import entities.Food;

public class FoodFragment extends Fragment {
    private Repository repository;

    private TextView foodLogTitle, foodLogDate;
    private TextView foodLogTotalCalories, foodLogTotalProtein, foodLogTotalFat, foodLogTotalCarb;
    private EditText breakfastItemEditText, breakfastCaloriesEditText, breakfastProteinEditText, breakfastFatEditText, breakfastCarbsEditText;
    private EditText lunchItemEditText, lunchCaloriesEditText, lunchProteinEditText, lunchFatEditText, lunchCarbsEditText;
    private EditText dinnerItemEditText, dinnerCaloriesEditText, dinnerProteinEditText, dinnerFatEditText, dinnerCarbsEditText;
    private EditText snackItemEditText, snackCaloriesEditText, snackProteinEditText, snackFatEditText, snackCarbsEditText;
    private ImageView previousDayImageView, nextDayImageView;

    private Button addBreakfastButton, addLunchButton, addDinnerButton, addSnackButton;
    private RecyclerView breakfastRecyclerView, lunchRecyclerView, dinnerRecyclerView, snackRecyclerView;
    private List<Food> breakfastList;
    private List<Food> lunchList;
    private List<Food> dinnerList;
    private List<Food> snackList;
    private FoodAdapter breakfastAdapter;
    private FoodAdapter lunchAdapter;
    private FoodAdapter dinnerAdapter;
    private FoodAdapter snackAdapter;

    private int totalCalories = 0;
    private int totalProtein = 0;
    private int totalFat = 0;
    private int totalCarbs = 0;
    private LocalDate currentDate;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        repository = new Repository(requireActivity().getApplication());

        // Initialize the shared ViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Initialize UI components
        previousDayImageView = view.findViewById(R.id.previousDayImageView);
        nextDayImageView = view.findViewById(R.id.nextDayImageView);
        foodLogTitle = view.findViewById(R.id.foodLogTitle);
        foodLogDate = view.findViewById(R.id.foodLogDate);
        foodLogTotalCalories = view.findViewById(R.id.foodLogTotalCalories);
        foodLogTotalProtein = view.findViewById(R.id.foodLogTotalProtein);
        foodLogTotalFat = view.findViewById(R.id.foodLogTotalFat);
        foodLogTotalCarb = view.findViewById(R.id.foodLogTotalCarb);

        breakfastItemEditText = view.findViewById(R.id.breakfastItemEditText);
        breakfastCaloriesEditText = view.findViewById(R.id.breakfastCaloriesEditText);
        breakfastProteinEditText = view.findViewById(R.id.breakfastProteinEditText);
        breakfastFatEditText = view.findViewById(R.id.breakfastFatEditText);
        breakfastCarbsEditText = view.findViewById(R.id.breakfastCarbsEditText);
        addBreakfastButton = view.findViewById(R.id.addBreakfastButton);
        breakfastRecyclerView = view.findViewById(R.id.breakfastRecyclerView);

        lunchItemEditText = view.findViewById(R.id.lunchItemEditText);
        lunchCaloriesEditText = view.findViewById(R.id.lunchCaloriesEditText);
        lunchProteinEditText = view.findViewById(R.id.lunchProteinEditText);
        lunchFatEditText = view.findViewById(R.id.lunchFatEditText);
        lunchCarbsEditText = view.findViewById(R.id.lunchCarbsEditText);
        addLunchButton = view.findViewById(R.id.addLunchButton);
        lunchRecyclerView = view.findViewById(R.id.lunchRecyclerView);

        dinnerItemEditText = view.findViewById(R.id.dinnerItemEditText);
        dinnerCaloriesEditText = view.findViewById(R.id.dinnerCaloriesEditText);
        dinnerProteinEditText = view.findViewById(R.id.dinnerProteinEditText);
        dinnerFatEditText = view.findViewById(R.id.dinnerFatEditText);
        dinnerCarbsEditText = view.findViewById(R.id.dinnerCarbsEditText);
        addDinnerButton = view.findViewById(R.id.addDinnerButton);
        dinnerRecyclerView = view.findViewById(R.id.dinnerRecyclerView);

        snackItemEditText = view.findViewById(R.id.snackItemEditText);
        snackCaloriesEditText = view.findViewById(R.id.snackCaloriesEditText);
        snackProteinEditText = view.findViewById(R.id.snackProteinEditText);
        snackFatEditText = view.findViewById(R.id.snackFatEditText);
        snackCarbsEditText = view.findViewById(R.id.snackCarbsEditText);
        addSnackButton = view.findViewById(R.id.addSnacksButton);
        snackRecyclerView = view.findViewById(R.id.snackRecyclerView);

        // Initialize the current date
        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd, MMMM yyyy");
        foodLogDate.setText(currentDate.format(formatter));

        // Initialize lists and adapters
        breakfastList = new ArrayList<>();
        lunchList = new ArrayList<>();
        dinnerList = new ArrayList<>();
        snackList = new ArrayList<>();

        breakfastAdapter = new FoodAdapter(getContext(), breakfastList, this::deleteFoodItem);
        lunchAdapter = new FoodAdapter(getContext(), lunchList, this::deleteFoodItem);
        dinnerAdapter = new FoodAdapter(getContext(), dinnerList, this::deleteFoodItem);
        snackAdapter = new FoodAdapter(getContext(), snackList, this::deleteFoodItem);

        initializeRecyclerView(breakfastRecyclerView, breakfastAdapter);
        initializeRecyclerView(lunchRecyclerView, lunchAdapter);
        initializeRecyclerView(dinnerRecyclerView, dinnerAdapter);
        initializeRecyclerView(snackRecyclerView, snackAdapter);

        // Set up button click listeners
        addBreakfastButton.setOnClickListener(v -> addBreakfastItem());
        addLunchButton.setOnClickListener(v -> addLunchItem());
        addDinnerButton.setOnClickListener(v -> addDinnerItem());
        addSnackButton.setOnClickListener(v -> addSnackItem());

        // Set up image view click listeners
        previousDayImageView.setOnClickListener(v -> changeDate(-1));
        nextDayImageView.setOnClickListener(v -> changeDate(1));


        return view;
    }

    private void changeDate(int days) {
        currentDate = currentDate.plusDays(days);
        updateDateDisplay();
        updateFoodLists(); // Update the food list based on the new date
    }

    private void updateDateDisplay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd, MMMM yyyy");
        foodLogDate.setText(currentDate.format(formatter));
    }

    private void addBreakfastItem() {
        addItem(breakfastItemEditText, breakfastCaloriesEditText, breakfastProteinEditText, breakfastFatEditText, breakfastCarbsEditText, breakfastList, breakfastAdapter, "Breakfast");
    }

    private void addLunchItem() {
        addItem(lunchItemEditText, lunchCaloriesEditText, lunchProteinEditText, lunchFatEditText, lunchCarbsEditText, lunchList, lunchAdapter, "Lunch");
    }

    private void addDinnerItem() {
        addItem(dinnerItemEditText, dinnerCaloriesEditText, dinnerProteinEditText, dinnerFatEditText, dinnerCarbsEditText, dinnerList, dinnerAdapter, "Dinner");
    }

    private void addSnackItem() {
        addItem(snackItemEditText, snackCaloriesEditText, snackProteinEditText, snackFatEditText, snackCarbsEditText, snackList, snackAdapter, "Snack");
    }

    private void addItem(EditText itemEditText, EditText caloriesEditText, EditText proteinEditText, EditText fatEditText, EditText carbsEditText, List<Food> foodList, FoodAdapter adapter, String mealType) {
        int user_id = getId();
        String mealLogDate = foodLogDate.getText().toString();
        String itemName = itemEditText.getText().toString();

        // Validate input to prevent crashes due to empty fields
        if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(caloriesEditText.getText()) ||
                TextUtils.isEmpty(proteinEditText.getText()) ||
                TextUtils.isEmpty(fatEditText.getText()) ||
                TextUtils.isEmpty(carbsEditText.getText())) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int calories = Integer.parseInt(caloriesEditText.getText().toString());
        int proteinG = Integer.parseInt(proteinEditText.getText().toString());
        int fatG = Integer.parseInt(fatEditText.getText().toString());
        int carbsG = Integer.parseInt(carbsEditText.getText().toString());

        Food foodItem = new Food(generateMealId(), user_id, mealLogDate, mealType, itemName, calories, proteinG, fatG, carbsG);
        foodList.add(foodItem);
        repository.insert(foodItem);
        adapter.notifyDataSetChanged(); // Notify the adapter of the data change
        updateTotalMacros();

        // Clear the fields after adding the item
        itemEditText.setText("");
        caloriesEditText.setText("");
        proteinEditText.setText("");
        fatEditText.setText("");
        carbsEditText.setText("");
    }


    private void updateTotalMacros() {
        totalCalories = 0;
        totalProtein = 0;
        totalFat = 0;
        totalCarbs = 0;

        for (Food food : breakfastList) {
            totalCalories += food.getCalories();
            totalProtein += food.getProteinG();
            totalFat += food.getFatG();
            totalCarbs += food.getCarbsG();
        }
        for (Food food : lunchList) {
            totalCalories += food.getCalories();
            totalProtein += food.getProteinG();
            totalFat += food.getFatG();
            totalCarbs += food.getCarbsG();
        }
        for (Food food : dinnerList) {
            totalCalories += food.getCalories();
            totalProtein += food.getProteinG();
            totalFat += food.getFatG();
            totalCarbs += food.getCarbsG();
        }
        for (Food food : snackList) {
            totalCalories += food.getCalories();
            totalProtein += food.getProteinG();
            totalFat += food.getFatG();
            totalCarbs += food.getCarbsG();
        }

        foodLogTotalCalories.setText(String.valueOf(totalCalories));
        foodLogTotalProtein.setText(String.valueOf(totalProtein));
        foodLogTotalFat.setText(String.valueOf(totalFat));
        foodLogTotalCarb.setText(String.valueOf(totalCarbs));

        sharedViewModel.setTotalCalories(totalCalories);

    }

    private void deleteFoodItem(Food food) {
        repository.delete(food); // Delete from database
        updateFoodLists(); // Update the UI
    }

    private void updateFoodLists() {
        List<Food> allFood = repository.getAllFood();

        breakfastList.clear();
        lunchList.clear();
        dinnerList.clear();
        snackList.clear();

        for (Food food : allFood) {
            if (food.getMealLogDate() .equals(foodLogDate.getText().toString())) {
                switch (food.getMealType()) {
                    case "Breakfast":
                        breakfastList.add(food);
                        break;
                    case "Lunch":
                        lunchList.add(food);
                        break;
                    case "Dinner":
                        dinnerList.add(food);
                        break;
                    case "Snack":
                        snackList.add(food);
                        break;
                }
            }
        }

        breakfastAdapter.notifyDataSetChanged();
        lunchAdapter.notifyDataSetChanged();
        dinnerAdapter.notifyDataSetChanged();
        snackAdapter.notifyDataSetChanged();

        updateTotalMacros();
    }

    private int generateMealId() {
        return (int) (Math.random() * 10000);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFoodLists();
        updateTotalMacros();
    }

    private void initializeRecyclerView(RecyclerView recyclerView, FoodAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

}
