package ie.ul.cs4084project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout usernameText, emailText, passwordText;
    private Button regBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        usernameText = findViewById(R.id.textInputUser);
        emailText = findViewById(R.id.textInputEmail);
        passwordText = findViewById(R.id.textInputPassword);
        regBtn = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {

        String name, email, password;
        name = usernameText.getEditText().getText().toString();
        email = emailText.getEditText().getText().toString();
        password = passwordText.getEditText().getText().toString();

        if (validateName(name) && validateEmail(email) && validatePassword(password)) {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                                Intent intent = new Intent(RegistrationActivity.this, LogIn.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

            FirebaseUser user = mAuth.getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("user", "User profile updated.");
                            }
                        }
                    });

        }
    }

    private boolean validatePassword(String password) {
        if(password.isEmpty()) {
            passwordText.setError("field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            passwordText.setError("Password too weak");
            return false;
        } else {
            passwordText.setError(null);
            return true;
        }
    }

    private boolean validateEmail(String email) {
        if(email.isEmpty()) {
            emailText.setError("field can't be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("please enter a valid email address");
            return false;
        } else {
            emailText.setError(null);
            return true;
        }
    }

    private boolean validateName(String name) {
        if(name.isEmpty()) {
            usernameText.setError("field can't be empty");
            return false;
        } else if(name.length() > 19) {
            usernameText.setError("username 20 characters or less");
            return false;
        } else {
            usernameText.setError(null);
            return true;
        }
    }
}