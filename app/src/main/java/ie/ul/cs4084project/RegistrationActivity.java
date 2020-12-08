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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    //TextInputs from Layout
    private TextInputLayout usernameText, emailText, passwordText;
    //indicates registrtion is progressing
    private ProgressBar progressBar;

    private View rootLayout;

    //gets current Auth instance
    private FirebaseAuth mAuth;

    //pattern for checking password
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

    /**
     * onCreate method creates activity and sets content view to activity_log_in
     * It initializes mAuth to get FirebaseAuth instance
     * It initializes UI element declarations to their respective UI elements in the layout file
     * It sets the OnClickListener for the regBtn so it calls the method RegisterNewUser() on click
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        usernameText = findViewById(R.id.textInputUser);
        emailText = findViewById(R.id.textInputEmail);
        passwordText = findViewById(R.id.textInputPassword);
        //button for registering
        Button regBtn = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        rootLayout = findViewById(android.R.id.content);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    /**
     * Method places text from textInputLayouts into strings
     * It checks that these are valid by calling validateEmail, validatePassword and validateName with their respective text strings as parameters
     * It then calls the FirebaseAuth method createUserWithEmailAndPassword() with both strings as parameters to attempt Registration
     * It then updates users profiles with their chosen user name as the display name
     */
    private void registerNewUser() {

        final String name, email, password;
        name = usernameText.getEditText().getText().toString();
        email = emailText.getEditText().getText().toString();
        password = passwordText.getEditText().getText().toString();

        if (validateName(name) && validateEmail(email) && validatePassword(password)) {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(RegistrationActivity.this, LogIn.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        Snackbar.make(rootLayout, "Invalid Password too short!", Snackbar.LENGTH_LONG).show();
                    } else if (e instanceof FirebaseAuthUserCollisionException) {
                        Snackbar.make(rootLayout, "Registration Failed!", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(rootLayout, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });

            final FirebaseUser user = mAuth.getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("user", "User profile updated. " + user.getDisplayName());
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

    /**
     * Method checks that entered email was valid.
     * @param email String contains text of users input email.
     * @return True if valid, False if invalid.
     */
    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            emailText.setError("field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("please enter a valid email address");
            return false;
        } else {
            emailText.setError(null);
            return true;
        }
    }

    /**
     * Method checks that entered user name was valid.
     *
     * @param name String contains text of users input user name.
     * @return True if valid, False if invalid.
     */
    private boolean validateName(String name) {
        if (name.isEmpty()) {
            usernameText.setError("field can't be empty");
            return false;
        } else if (name.length() > 19) {
            usernameText.setError("username 20 characters or less");
            return false;
        } else {
            usernameText.setError(null);
            return true;
        }
    }
}