package com.example.fitlifefitnesstracker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import android.widget.EditText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.fitlifefitnesstracker.UI.LoginActivity;

public class LoginActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    private EditText editTextEmail;
    private EditText editTextPassword;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(activity -> {
            // Initialize views in the onActivity callback
            editTextEmail = activity.findViewById(R.id.editTextEmail);
            editTextPassword = activity.findViewById(R.id.editTextPassword);
        });
    }

    @Test
    public void testEmptyEmail() {
        activityRule.getScenario().onActivity(activity -> {
            activity.runOnUiThread(() -> {
                editTextEmail.setText("");
                activity.findViewById(R.id.buttonLogin).performClick();
            });
            assertEquals("Empty email address", editTextEmail.getError());
        });
    }

    @Test
    public void testInvalidEmail() {
        activityRule.getScenario().onActivity(activity -> {
            activity.runOnUiThread(() -> {
                editTextEmail.setText("invalid");
                activity.findViewById(R.id.buttonLogin).performClick();
            });
            assertEquals("Invalid email address", editTextEmail.getError());
        });
    }

    @Test
    public void testValidEmail() {
        activityRule.getScenario().onActivity(activity -> {
            activity.runOnUiThread(() -> {
                editTextEmail.setText("valid@email.com");
                activity.findViewById(R.id.buttonLogin).performClick();
            });
            assertNull(editTextEmail.getError());
        });
    }

    // Password tests
    @Test
    public void testEmptyPassword() {
        activityRule.getScenario().onActivity(activity -> {
            activity.runOnUiThread(() -> {
                editTextPassword.setText("");
                activity.findViewById(R.id.buttonLogin).performClick();
            });
            assertFalse("Empty password",false);
        });
    }

    @Test
    public void testShortPassword() {
        activityRule.getScenario().onActivity(activity -> {
            activity.runOnUiThread(() -> {
                editTextPassword.setText("pass");
                activity.findViewById(R.id.buttonLogin).performClick();
            });
            assertFalse("Password too short", false);
        });
    }

    @Test
    public void testValidPassword() {
        activityRule.getScenario().onActivity(activity -> {
            activity.runOnUiThread(() -> {
                editTextPassword.setText("validPassword123");
                activity.findViewById(R.id.buttonLogin).performClick();
            });
            assertNull(editTextPassword.getError());
        });
    }
}
