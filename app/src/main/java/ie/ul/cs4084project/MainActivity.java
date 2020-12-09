package ie.ul.cs4084project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LocationListener {

    //text input from the layout
    private TextInputLayout textInputSearch;
    //spinner for categories;
    private Spinner categorySpinner;

    //location manager to get location
    protected LocationManager locationManager;
    //latitude and longitude are updated during app life cycle
    protected double latitude, longitude;

    /**
     * onCreate creates the Activity and sets content view to acivity_main.
     * it also initializes UI elements and provides the category spinner on item selected listener
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, this);

        textInputSearch = findViewById(R.id.textInputSearch);
        categorySpinner = findViewById(R.id.categorySpinner);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!categorySpinner.getSelectedItem().toString().equals("-select a category-")) {
                    FindItem newFragment = new FindItem();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("Category", categorySpinner.getSelectedItem().toString());
                    newFragment.setArguments(args);
                    ft.replace(R.id.fragment, newFragment);
                    ft.commit();
                } else {
                    MainFeed newFragment = new MainFeed();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, newFragment);
                    ft.commit();
                    ft.addToBackStack("MainFeed");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * inflates menu actionbar
     *
     * @param menu Menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * button listener for menu items
     *
     * @param menuItem MenuItem used to access menu items/buttons
     * @return outcome from call to super class method returns true or false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        } else if (menuItem.getItemId() == R.id.signOut) {
            signOut();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /**
     * method for signing out when menu item signout pressed
     */
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }

    /**
     * method for returning to previous fragment on menu back arrow pressed.
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * gets the latitude
     *
     * @return latitude Double
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * gets the longitude
     *
     * @return longitude Double
     */
    public double getLongitude() {
        return longitude;
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
        ft.addToBackStack("MainFeed");
    }

    /**
     * onClick method for createPost button
     * takes user to the fragment createPost to add a new item to the store on clicking btnCreatePost.
     *
     * @param view View takes in the view as a parameter
     */
    public void onCreatePostClicked(View view) {
        CreatePost newFragment = new CreatePost();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
        ft.addToBackStack("CreatePost");
    }

    /**
     * onClick method for message button
     * takes user to the MessageScreen fragment where they can send emails to other users on clicking btnMessage
     * @param view View takes in the view as a parameter
     */
    public void onMessageClicked(View view) {
        MessageScreen newFragment = new MessageScreen();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
        ft.addToBackStack("Message");
    }

    /**
     * onClick method for search button
     * takes user to FindItem fragment to load the search items and category if one is chosen on clicking btnSearch.
     * @param view View takes in the view as a parameter
     */
    public void onSearchClicked(View view) {
        if (!(textInputSearch.getEditText().getText().toString().equals(""))) {
            FindItem newFragment = new FindItem();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString("searchTerm", textInputSearch.getEditText().getText().toString());
            newFragment.setArguments(args);
            ft.replace(R.id.fragment, newFragment);
            ft.commit();
            ft.addToBackStack("FindItem");
        } else {
            MainFeed newFragment = new MainFeed();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, newFragment);
            ft.commit();
            ft.addToBackStack("MainFeed");
        }
    }

    /**
     * onClick method for profile button
     * takes user to Profile fragment on clicking the button btnProfile
     *
     * @param view View takes in the view as a parameter
     */
    public void onProfileClicked(View view) {
        Profile newFragment = new Profile();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
        ft.addToBackStack("Profile");
    }

    /**
     * called on results of an activity started for result
     *
     * @param requestCode int
     * @param resultCode  int
     * @param data        Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * called when user location changes
     *
     * @param location Location new user location
     */
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("COORDS", location.getLatitude() + "," + location.getLongitude());
    }
}