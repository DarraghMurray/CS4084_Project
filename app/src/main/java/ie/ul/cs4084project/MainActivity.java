package ie.ul.cs4084project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.api.Api;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.snapshot.Index;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Spinner categorySpinner;

    protected boolean permission;

    /**
     * onCreate creates the Activity and sets content view to acivity_main
     * it also initializes textInputSearch and categorySpinner to their respective UI elements
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        categorySpinner = findViewById(R.id.categorySpinner);
    }

    /**
     * onClick method for home button
     * Takes the user to the main feed on clicking the button btnHome
     *
     * @param view takes in the view as a parameter
     */
    public void onHomeClicked(View view) {

        MainFeed newFragment = new MainFeed();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
    }

    /**
     * onClick method for createPost button
     * takes user to the fragment createPost to add a new item to the store on clicking btnCreatePost.
     *
     * @param view takes in the view as a parameter
     */
    public void onCreatePostClicked(View view) {
        CreatePost newFragment = new CreatePost();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
    }

    /**
     * onClick method for message button
     * takes user to the MessageScreen fragment where they can send emails to other users on clicking btnMessage
     *
     * @param view takes in the view as a parameter
     */
    public void onMessageClicked(View view) {
        MessageScreen newFragment = new MessageScreen();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
    }

    /**
     * onClick method for search button
     * takes user to FindItem fragment to load the search items and category if one is chosen on clicking btnSearch.
     *
     * @param view takes in the view as a parameter
     */
    public void onSearchClicked(View view) {
        if (!categorySpinner.getSelectedItem().toString().equals("-select a category-")) {
            FindItem newFragment = new FindItem();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString("Category", categorySpinner.getSelectedItem().toString());
            newFragment.setArguments(args);
            ft.replace(R.id.fragment, newFragment);
            ft.commit();
        }
    }

    /**
     * @param requestCode  int
     * @param permissions  String[]
     * @param grantResults int[]
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
            }
        }
    }

    /**
     * @param requestCode int
     * @param resultCode  int
     * @param data        Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}