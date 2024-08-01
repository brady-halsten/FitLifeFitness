package ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> totalCalories = new MutableLiveData<>();

    public void setTotalCalories(int calories) {
        totalCalories.setValue(calories);
    }

    public LiveData<Integer> getTotalCalories() {
        return totalCalories;
    }
}