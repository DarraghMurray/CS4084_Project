package ie.ul.cs4084project;


import android.Manifest;
import android.content.Intent;
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
import android.view.WindowManager;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

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

    private View rootLayout;

    //GoogleMap
    private GoogleMap mGoogleMap;
    View mview;

    //item for item page user is on
    private Item itemPageItem;
    private FirebaseFirestore firestoreInstance;

    private boolean userLocationPermission = true;
    private boolean itemHasLocation = true;

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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        userLatitude = ((MainActivity) getActivity()).getLatitude();
        userLongitude = ((MainActivity) getActivity()).getLongitude();

        userLocationPermission = userLatitude != 1000 || userLongitude != 1000;

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
        Button getDirections = view.findViewById(R.id.getDirections);
        final ImageView itemPageImage = view.findViewById(R.id.itemPageImage);
        rootLayout = getActivity().findViewById(android.R.id.content);
        firestoreInstance = FirebaseFirestore.getInstance();

        itemPageItem = getArguments().getParcelable("Item");
        itemHasLocation = itemPageItem.getLatitude() != 1000 || itemPageItem.getLongitude() != 1000;
        final String email = itemPageItem.getSellerContact();

        purchase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //"Item ID" is the auto-generated ID from firestore, was unable to retrieve
                DocumentSnapshot ref;
                firestoreInstance.collection("posts").document(itemPageItem.getId()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.getResult().exists()) {
                                    firestoreInstance.collection("posts").document(itemPageItem.getId()).delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    PurchaseScreen newFragment = new PurchaseScreen();
                                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                                    ft.replace(R.id.fragment, newFragment);
                                                    ft.commit();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(rootLayout, "Purchase Error", Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    Snackbar.make(rootLayout, "Item has been purchased by another user", Snackbar.LENGTH_LONG).show();
                                }
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

        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemHasLocation) {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)",
                            userLatitude, userLongitude, "You",
                            itemPageItem.getLatitude(), itemPageItem.getLongitude(), "Item");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
            }
        });

        itemTitle.setText(itemPageItem.getName());
        itemDescrip.setText(itemPageItem.getDescription());
        itemPricing.setText(Double.toString(itemPageItem.getPrice()));
        sellerUser.setText(itemPageItem.getSellerName());
        if (!(itemPageItem.getItemImage() == null)) {
            Glide.with(getContext()).load(Uri.parse(itemPageItem.getItemImage())).into(itemPageImage);
        } else {
            itemPageImage.setImageResource(R.drawable.no_image_available_icon);
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
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        if (userLocationPermission) {
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(userLatitude, userLongitude))).setTitle("YOU");
        }
        if (itemHasLocation) {
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(itemPageItem.getLatitude(), itemPageItem.getLongitude())).title("ITEM"));
            CameraPosition itemLocation = CameraPosition.builder().target(new LatLng(itemPageItem.getLatitude(), itemPageItem.getLongitude())).zoom(4).bearing(0).tilt(50).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(itemLocation));
        }
    }
}