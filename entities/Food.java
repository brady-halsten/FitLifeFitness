package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food")
public class Food {
    @PrimaryKey(autoGenerate = true)
    private int meal_Id;
    private int user_Id;
    private String mealLogDate;
    private String mealType;
    private String itemName;
    private int calories;
    private int proteinG;
    private int carbsG;
    private int fatG;

    public Food(int meal_Id, int user_Id, String mealLogDate, String mealType,
                String itemName, int calories, int proteinG, int carbsG, int fatG) {
        this.meal_Id = meal_Id;
        this.user_Id = user_Id;
        this.mealLogDate = mealLogDate;
        this.mealType = mealType;
        this.itemName = itemName;
        this.calories = calories;
        this.proteinG = proteinG;
        this.carbsG = carbsG;
        this.fatG = fatG;
    }

    public int getMeal_Id() {
        return meal_Id;
    }

    public void setMeal_Id(int meal_Id) {
        this.meal_Id = meal_Id;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getMealLogDate() {
        return mealLogDate;
    }

    public void setMealLogDate(String mealLogDate) {
        this.mealLogDate = mealLogDate;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProteinG() {
        return proteinG;
    }

    public void setProteinG(int proteinG) {
        this.proteinG = proteinG;
    }

    public int getCarbsG() {
        return carbsG;
    }

    public void setCarbsG(int carbsG) {
        this.carbsG = carbsG;
    }

    public int getFatG() {
        return fatG;
    }

    public void setFatG(int fatG) {
        this.fatG = fatG;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + itemName + '\'' +
                ", calories=" + calories +
                ", protein=" + proteinG +
                ", fat=" + fatG +
                ", carbs=" + carbsG +
                '}';
    }
}
