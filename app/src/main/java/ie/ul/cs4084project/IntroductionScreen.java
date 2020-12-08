package ie.ul.cs4084project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroductionScreen extends AppCompatActivity {

    /**
     * onCreate creates the activity and sets content view to activity_introduction_screen
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_screen);
    }

    /**
     * onClick method for the button signInButton
     * takes user to LogIn Activity on clicking sign-in
     * @param view takes the view as a parameter
     */
    public void onSignInPressed(View view) {
        Intent intent = new Intent(IntroductionScreen.this, LogIn.class);
        startActivity(intent);
    }

    /**
     * onClick method for the button createAccountButton
     * takes user to RegistrationActivity Activity on clicking create an account
     * @param view takes the view as a parameter
     */
    public void onCreateAccountPressed(View view) {
        Intent intent = new Intent(IntroductionScreen.this, RegistrationActivity.class);
        startActivity(intent);
    }
}