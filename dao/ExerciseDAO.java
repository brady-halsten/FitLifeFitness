package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Exercise;

@Dao
public interface ExerciseDAO {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("SELECT * FROM EXERCISES ORDER BY exerciseId ASC")
    List<Exercise> selectAllExercises();
}
