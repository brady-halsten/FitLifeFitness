package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Food;

@Dao
public interface FoodDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Food food);

    @Update
    void update (Food food);

    @Delete
    void delete (Food food);

    @Query("SELECT * FROM FOOD")
    List<Food> selectAllFood();
}
