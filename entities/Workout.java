package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity(tableName = "workout")

public class Workout {
    @PrimaryKey(autoGenerate = true)
    int workoutId;
    int user_Id;
    String workoutName;
    String workoutType;
    int workoutDuration;

    public Workout(int workoutId, int user_Id, String workoutName, String workoutType, int workoutDuration) {
        this.workoutId = workoutId;
        this.user_Id = user_Id;
        this.workoutName = workoutName;
        this.workoutType = workoutType;
        this.workoutDuration = workoutDuration;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public int getWorkoutDuration() {
        return workoutDuration;
    }

    public void setWorkoutDuration(int workoutDuration) {
        this.workoutDuration = workoutDuration;
    }


}
