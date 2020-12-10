package ie.ul.cs4084project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1000;
    //text input from the layout
    private TextInputLayout textInputSearch;
    //spinner for categories;
    private Spinner categorySpinner;

    protected LocationManager locationManager;
    protected double latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    protected boolean permission;

    /**
     * onCreate creates the Activity and sets content view to acivity_main.
     * it also initializes UI elements.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkLocationPermission();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

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

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }

    //Both navigation bar back press and title bar back press will trigger this method
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public double getLatitude() {
        return latitude;
    }

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
     * @param view takes in the view as a parameter
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
     * @param view takes in the view as a parameter
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
     * @param view takes in the view as a parameter
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

    public void onProfileClicked(View view) {
        Profile newFragment = new Profile();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
        ft.addToBackStack("Profile");
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this).setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                        , MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * method called when permissions are requested
     *
     * @param requestCode  int code of the request made
     * @param permissions  String[] permissions required
     * @param grantResults int[] whether permission is granted for each permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Request location updates:
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    } else {
                        latitude = 1000;
                        longitude = 1000;
                    }
                } else {
                }
                return;
            }

        }
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

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }
}