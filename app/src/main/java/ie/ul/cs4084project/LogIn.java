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

    //pattern for password checking
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

    //TextInputs from the layout
    private TextInputLayout textInputEmailSignIn, textInputPasswordSignIn;
    //button for log in
    private Button loginBtn;
    //indicates login is progressing
    private ProgressBar progressBar;

    //gets current Auth instance
    private FirebaseAuth mAuth;

    /**
     * onCreate method creates activity and sets content view to activity_log_in
     * It initializes mAuth to get FirebaseAuth instance
     * It initializes UI element declarations to their respective UI elements in the layout file
     * It sets the OnClickListener for the loginBtn so it calls the method loginUserAccount() on click
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        textInputEmailSignIn = findViewById(R.id.textInputEmailSignIn);
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

    /**
     * Method places text from textInputLayouts into strings
     * It checks that these are valid by calling validateEmail and validatePassword with their respective text strings as parameters
     * It then calls the FirebaseAuth method signInWithEmailAndPassword() with both strings as parameters to attempt sign-in
     */
    private void loginUserAccount() {

        String email, password;
        email = textInputEmailSignIn.getEditText().getText().toString();
        password = textInputPasswordSignIn.getEditText().getText().toString();

        if (validateEmail(email) && validatePassword(password)) {
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

    /**
     * Method checks that the entered password was valid.
     * @param password String contains text of users input password.
     * @return True if valid, False if invalid.
     */
    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
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

    /**
     * Method checks that entered email was valid.
     * @param email String contains text of users input email.
     * @return True if valid, False if invalid.
     */
    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            textInputEmailSignIn.setError("field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEmailSignIn.setError("please enter a valid email address");
            return false;
        } else {
            textInputEmailSignIn.setError(null);
            return true;
        }
    }

}