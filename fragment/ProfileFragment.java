package fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitlifefitnesstracker.R;
import com.example.fitlifefitnesstracker.UI.LoginActivity;

import dao.UserDAO;
import database.FitnessDatabaseBuilder;
import entities.User;

public class ProfileFragment extends Fragment {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private RadioGroup genderGroup;
    private UserDAO userDAO;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        firstNameEditText = view.findViewById(R.id.editFirstNameEditText);
        lastNameEditText = view.findViewById(R.id.editLastNameEditText);
        emailEditText = view.findViewById(R.id.editEmailEditText);
        passwordEditText = view.findViewById(R.id.editPasswordEditText);
        confirmPasswordEditText = view.findViewById(R.id.editPasswordConfirmEditText);
        genderGroup = view.findViewById(R.id.editGenderGroup);
        Button saveChangesBtn = view.findViewById(R.id.saveChangesBtn);
        TextView logoutTextView = view.findViewById(R.id.logout);

        // Initialize database and DAO
        FitnessDatabaseBuilder db = FitnessDatabaseBuilder.getDatabase(getContext());
        userDAO = db.userDAO();

        // Fetch current user (example: fetching by email or ID, you might need to adapt this part)
        new FetchUserTask().execute("user@example.com"); // Adjust the email parameter

        saveChangesBtn.setOnClickListener(v -> saveChanges());

        logoutTextView.setOnClickListener(v -> logout());

        return view;
    }

    private void saveChanges() {
        if (currentUser != null) {
            // Update user object with new data
            currentUser.setFirstName(firstNameEditText.getText().toString());
            currentUser.setLastName(lastNameEditText.getText().toString());
            currentUser.setEmail(emailEditText.getText().toString());
            currentUser.setGender(getSelectedGender());
            // Password update logic (if necessary)
            String newPassword = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            if (!newPassword.isEmpty() && newPassword.equals(confirmPassword)) {
                currentUser.setPassword(newPassword);
            }

            // Update user in database
            new UpdateUserTask().execute(currentUser);
            Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error: User not found", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSelectedGender() {
        int selectedId = genderGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioEditMaleButton) {
            return "Male";
        } else if (selectedId == R.id.radioEditFemaleButton) {
            return "Female";
        } else if (selectedId == R.id.radioEditOtherButton) {
            return "Other";
        } else if (selectedId == R.id.radioEditUndeclaredButton) {
            return "Rather Not Say";
        } else {
            return "Undeclared";
        }
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
    }

    private class FetchUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... emails) {
            return userDAO.getUserByEmail(emails[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            currentUser = user;
            if (currentUser != null) {
                // Populate the fields with user data
                firstNameEditText.setText(currentUser.getFirstName());
                lastNameEditText.setText(currentUser.getLastName());
                emailEditText.setText(currentUser.getEmail());
                // Set gender radio button
                String gender = currentUser.getGender();
                if (gender.equals("Male")) {
                    genderGroup.check(R.id.radioEditMaleButton);
                } else if (gender.equals("Female")) {
                    genderGroup.check(R.id.radioEditFemaleButton);
                } else if (gender.equals("Other")) {
                    genderGroup.check(R.id.radioEditOtherButton);
                } else {
                    genderGroup.check(R.id.radioEditUndeclaredButton);
                }
            } else {
                Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UpdateUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            userDAO.update(users[0]);
            return null;
        }
    }
}
