package ie.ul.cs4084project;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemPage extends Fragment implements OnMapReadyCallback {

    public MapView mMapView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //longitude and latitude of user
    private double userLatitude;
    private double userLongitude;

    //request code for location
    private int locationRequestCode = 1000;

    //GoogleMap
    private GoogleMap mGoogleMap;
    View mview;

    //item for item page user is on
    private Item itemPageItem;

    /**
     * ItemPage default constructor
     */
    public ItemPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemPage newInstance(String param1, String param2) {
        ItemPage fragment = new ItemPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * gets location permission
     * if permission is provided it retrieves the user location and sets their latitude and longitude
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ((MainActivity) getActivity()).permission = false;
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, locationRequestCode);
        } else {
            LocationFinder finder;
            finder = new LocationFinder(getContext());
            if (finder.canGetLocation()) {
                userLatitude = finder.getLatitude();
                userLongitude = finder.getLongitude();
                Log.d("DDDDDDDDDdd", finder.getLatitude() + " " + finder.getLongitude());
            } else {
                finder.showSettingsAlert();
            }
        }
    }

    /**
     * onCreateView default fragment onCreateView
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_item_page, container, false);
        return mview;
    }


    /**
     * initializes UI elements
     * gets itemPageItem from a parcel argument and seller contact email
     * Purchase button on click gets Firestore instance and deletes the item in database with the ID of this item
     * then transitions to purchase screen
     * Message Seller button on click transitions to message screen taking seller contact email as an argument
     * After the on clicks it sets up textView text, image in ImageView and a google map
     *
     * @param view               View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView itemTitle = view.findViewById(R.id.itemTitle);
        TextView itemDescrip = view.findViewById(R.id.itemDescrip);
        TextView itemPricing = view.findViewById(R.id.itemPricing);
        TextView sellerUser = view.findViewById(R.id.sellerUser);
        Button purchase = view.findViewById(R.id.btnPurchase);
        Button messageSeller = view.findViewById(R.id.btnMessageSeller);
        final ImageView itemPageImage = view.findViewById(R.id.itemPageImage);

        itemPageItem = getArguments().getParcelable("Item");
        final String email = itemPageItem.getSellerContact();

        purchase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //"Item ID" is the auto-generated ID from firestore, was unable to retrieve
                FirebaseFirestore.getInstance().collection("posts").document(itemPageItem.getId()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                PurchaseScreen newFragment = new PurchaseScreen();
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.fragment, newFragment);
                                ft.commit();
                                Log.d(TAG, "onSuccess: Deleted document");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: Failed to delete", e);
                            }
                        });
            }
        });

        messageSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageScreen newFragment = new MessageScreen();
                Bundle args = new Bundle();
                args.putString("sellerEmail", email);
                System.out.println(args.getString("sellerEmail"));
                newFragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, newFragment);
                ft.commit();
            }
        });


        itemTitle.setText(itemPageItem.getName());
        itemDescrip.setText(itemPageItem.getDescription());
        itemPricing.setText(Double.toString(itemPageItem.getPrice()));
        sellerUser.setText(itemPageItem.getSellerName());
        if (!(itemPageItem.getItemImage() == null)) {
            Glide.with(getContext()).load(Uri.parse(itemPageItem.getItemImage())).into(itemPageImage);
        }

        mMapView = (MapView) mview.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    /**
     * initializes map and sets up markers for the item and user location
     * moves camera to the item marker location
     *
     * @param googleMap GoogleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(userLatitude, userLongitude))).setTitle("YOU");
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(itemPageItem.getLatitude(), itemPageItem.getLongitude())).title("ITEM"));
        CameraPosition itemLocation = CameraPosition.builder().target(new LatLng(itemPageItem.getLatitude(), itemPageItem.getLongitude())).zoom(16).bearing(0).tilt(50).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(itemLocation));
    }
}