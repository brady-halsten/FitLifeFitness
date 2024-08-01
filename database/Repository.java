package database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dao.ExerciseDAO;
import dao.FoodDAO;
import dao.UserDAO;
import dao.WorkoutDAO;
import entities.Exercise;
import entities.Food;
import entities.User;
import entities.Workout;

public class Repository {

    private ExerciseDAO mExerciseDAO;
    private FoodDAO mFoodDAO;
    private UserDAO mUserDAO;
    private WorkoutDAO mWorkoutDAO;

    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        FitnessDatabaseBuilder db = FitnessDatabaseBuilder.getDatabase(application);
        mExerciseDAO = db.exerciseDAO();
        mFoodDAO = db.foodDAO();
        mUserDAO = db.userDAO();
        mWorkoutDAO = db.workoutDAO();
    }
    public User getUserByID(int id) {
        Future<User> future = databaseExecutor.submit(() -> mUserDAO.getUserByID(id));
        try {
            return future.get();  // Waits for the result
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByEmail(String email) {
        Future<User> future = databaseExecutor.submit(() -> mUserDAO.getUserByEmail(email));
        try {
            return future.get();  // Waits for the result
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Food> getAllFood() {
        return execute(mFoodDAO::selectAllFood);
    }

    public List<Exercise> selectAllExercises() {
        return execute(mExerciseDAO::selectAllExercises);
    }

    public List<Food> selectAllFood() {
        return execute(mFoodDAO::selectAllFood);
    }

    public List<User> selectAllUsers() {
        return execute(mUserDAO::selectAllUsers);
    }

    public List<Workout> selectAllWorkouts() {
        return execute(mWorkoutDAO::selectAllWorkouts);
    }

    public void insert(Object entity) {
        databaseExecutor.execute(() -> {
            if (entity instanceof Exercise) {
                mExerciseDAO.insert((Exercise) entity);
            } else if (entity instanceof Food) {
                mFoodDAO.insert((Food) entity);
            } else if (entity instanceof User) {
                mUserDAO.insert((User) entity);
            } else if (entity instanceof Workout) {
                mWorkoutDAO.insert((Workout) entity);
            }
        });
    }

    public void update(Object entity) {
        databaseExecutor.execute(() -> {
            if (entity instanceof Exercise) {
                mExerciseDAO.update((Exercise) entity);
            } else if (entity instanceof Food) {
                mFoodDAO.update((Food) entity);
            } else if (entity instanceof User) {
                mUserDAO.update((User) entity);
            } else if (entity instanceof Workout) {
                mWorkoutDAO.update((Workout) entity);
            }
        });
    }

    public void delete(Object entity) {
        databaseExecutor.execute(() -> {
            if (entity instanceof Exercise) {
                mExerciseDAO.delete((Exercise) entity);
            } else if (entity instanceof Food) {
                mFoodDAO.delete((Food) entity);
            } else if (entity instanceof User) {
                mUserDAO.delete((User) entity);
            } else if (entity instanceof Workout) {
                mWorkoutDAO.delete((Workout) entity);
            }
        });
    }

    private <T> List<T> execute(Callable<List<T>> task) {
        Future<List<T>> future = databaseExecutor.submit(task);
        try {
            return future.get(); // Waits for the result
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
