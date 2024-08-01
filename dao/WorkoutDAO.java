package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Workout;

@Dao
public interface WorkoutDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Workout workout);

    @Update
    void update(Workout workout);

    @Delete
    void delete(Workout workout);

    @Query("SELECT * FROM WORKOUT ORDER BY workoutId ASC")
    List<Workout> selectAllWorkouts();

}
