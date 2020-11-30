package ie.ul.cs4084project;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

    private TextInputLayout textInputEmailSignIn, textInputPasswordSignIn;
    private Button loginBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        textInputEmailSignIn= findViewById(R.id.textInputEmailSignIn);
        textInputPasswordSignIn = findViewById(R.id.textInputPasswordSignIn);
        loginBtn = findViewById(R.id.logInBtn);
        progressBar = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount() {

        String email, password;
        email = textInputEmailSignIn.getEditText().getText().toString();
        password = textInputPasswordSignIn.getEditText().getText().toString();

        if(validateEmail(email) && validatePassword(password)) {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                                Intent intent = new Intent(LogIn.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    private boolean validatePassword(String password) {
        if(password.isEmpty()) {
            textInputPasswordSignIn.setError("field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            textInputPasswordSignIn.setError("Password too weak");
            return false;
        } else {
            textInputPasswordSignIn.setError(null);
            return true;
        }
    }

    private boolean validateEmail(String email) {
        if(email.isEmpty()) {
            textInputEmailSignIn.setError("field can't be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEmailSignIn.setError("please enter a valid email address");
            return false;
        } else {
            textInputEmailSignIn.setError(null);
            return true;
        }
    }

}