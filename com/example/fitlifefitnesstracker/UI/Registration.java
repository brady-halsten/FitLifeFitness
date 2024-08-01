package com.example.fitlifefitnesstracker.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fitlifefitnesstracker.R;
import java.util.Calendar;
import java.util.regex.Pattern;
import entities.User;
import database.Repository;

public class Registration extends AppCompatActivity {
    private CheckBox policy;
    private RadioGroup gender_group;
    private RadioButton gender_button;
    private TextView date, first_name, last_name, email, password, confirmPassword, date_of_birth;
    private Calendar c;
    private DatePickerDialog dpd;
    private Repository repository;

    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                    return "";
                }
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        gender_group = findViewById(R.id.genderGroup);
        date = findViewById(R.id.date_of_birth);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        date_of_birth = findViewById(R.id.date_of_birth);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        policy = findViewById(R.id.policy);

        repository = new Repository(getApplication());

        first_name.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(15)});
        last_name.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(15)});
        password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});

        date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText((month + 1) + dayOfMonth + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        Button b = findViewById(R.id.signUpButtonId);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    registerUser();
                }
            }
        });
    }

    private boolean validateInputs() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String datePattern = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";

        if (first_name.getText().toString().isEmpty()) {
            first_name.setError("Empty first name");
            return false;
        }
        if (last_name.getText().toString().isEmpty()) {
            last_name.setError("Empty last name");
            return false;
        }
        if (email.getText().toString().isEmpty()) {
            email.setError("Empty email address");
            return false;
        }
        if (!email.getText().toString().trim().matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        }
        if (date_of_birth.getText().toString().isEmpty()) {
            date_of_birth.setError("Select date of birth");
            return false;
        }
        if (!date_of_birth.getText().toString().trim().matches(datePattern)) {
            date_of_birth.setError("Date format should be 'mm/dd/yyyy'");
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            password.setError("Empty password");
            return false;
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("Passwords do not match");
            return false;
        }
        if (!policy.isChecked()) {
            policy.setError("Policy must be accepted");
            return false;
        }

        return true;
    }

    private void registerUser() {
        int selected_gender = gender_group.getCheckedRadioButtonId();
        gender_button = findViewById(selected_gender);

        User user = new User(
                first_name.getText().toString(),
                last_name.getText().toString(),
                gender_button.getText().toString(),
                email.getText().toString(),
                date_of_birth.getText().toString(),
                password.getText().toString(),
                Calendar.getInstance().getTime().toString()
        );

        // Add the user to the database
        repository.insert(user);

        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();

        // Clear the fields after successful registration
        first_name.setText("");
        last_name.setText("");
        email.setText("");
        date_of_birth.setText("");
        password.setText("");
        confirmPassword.setText("");
        policy.setChecked(false);
        policy.setError(null);

        // Navigate to the login activity
        Intent myIntent = new Intent(Registration.this, LoginActivity.class);
        startActivity(myIntent);
    }
}
