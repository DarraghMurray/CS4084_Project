package ie.ul.cs4084project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 1000;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1001;
    public static final int ERROR_DIALOG_REQUEST = 1002;
    private boolean mLocationPermissionGranted = false;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onHomeClicked(View view){

        MainFeed newFragment = new MainFeed();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
    }

    public void onCreatePostClicked(View view) {
        CreatePost newFragment = new CreatePost();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, newFragment);
        ft.commit();
    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    //private void getLocationPermission() {
     //   if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
      //          android.Manifest.permission.ACCESS_FINE_LOCATION)
       //         == PackageManager.PERMISSION_GRANTED) {
       //     mLocationPermissionGranted = true;
     //       getItemPage();
      //  } else {
       //     ActivityCompat.requestPermissions(this,
       //             new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
      //              PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
      //  }
   // }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //@Override
   // public void onRequestPermissionsResult(int requestCode,
     //                                      @NonNull String permissions[],
      //                                     @NonNull int[] grantResults) {
     //   mLocationPermissionGranted = false;
      //  switch (requestCode) {
        //    case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
          //      // If request is cancelled, the result arrays are empty.
           //     if (grantResults.length > 0
          //              && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          //          mLocationPermissionGranted = true;
        //        }
       //     }
      //  }
   // }

   // @Override
   // protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     //   super.onActivityResult(requestCode, resultCode, data);
    //    Log.d(TAG, "onActivityResult: called.");
    //    switch (requestCode) {
    //        case PERMISSIONS_REQUEST_ENABLE_GPS: {
    //            if(mLocationPermissionGranted){
    //                getItem();
    //            }
    //            else{
    //                getLocationPermission();
    //            }
     //       }
   //     }

   // }





}