package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.User;

@Dao
public interface UserDAO {
    // Correctly define the method to get a user by email
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users ORDER BY user_Id ASC")
    List<User> selectAllUsers();

    @Query(value = "SELECT * FROM users WHERE user_Id = :user_Id LIMIT 1")
    User getUserByID(int user_Id);


}
