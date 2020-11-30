package ie.ul.cs4084project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroductionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_screen);
    }

    public void onSignInPressed(View view) {
        Intent intent = new Intent(IntroductionScreen.this,LogIn.class);
        startActivity(intent);
    }

    public void onCreateAccountPressed(View view) {
        Intent intent = new Intent(IntroductionScreen.this,RegistrationActivity.class);
        startActivity(intent);
    }
}