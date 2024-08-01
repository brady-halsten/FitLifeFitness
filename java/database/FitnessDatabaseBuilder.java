package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dao.ExerciseDAO;
import dao.FoodDAO;
import dao.UserDAO;
import dao.WorkoutDAO;
import entities.Exercise;
import entities.Food;
import entities.User;
import entities.Workout;

@Database(entities = {Exercise.class, Food.class, User.class, Workout.class}, version = 5, exportSchema = false)
public abstract class FitnessDatabaseBuilder extends RoomDatabase {

    public abstract ExerciseDAO exerciseDAO();
    public abstract FoodDAO foodDAO();
    public abstract UserDAO userDAO();
    public abstract WorkoutDAO workoutDAO();
    private static volatile FitnessDatabaseBuilder INSTANCE;

    public static FitnessDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (FitnessDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),FitnessDatabaseBuilder.class, "FitLifeDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
