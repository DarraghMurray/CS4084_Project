package ie.ul.cs4084project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemPage extends Fragment implements OnMapReadyCallback {

    public MapView mMapView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView itemTitle;
    private TextView itemDescrip;
    private TextView itemPricing;
    private TextView itemSign;
    private Button purchase;
    GoogleMap mGoogleMap;
    View mview;

    public ItemPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_item_page, container, false);
        return mview;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemTitle = view.findViewById(R.id.itemTitle);
        itemDescrip = view.findViewById(R.id.itemDescrip);
        itemPricing = view.findViewById(R.id.itemPricing);
        itemSign = view.findViewById(R.id.itemSign);

        purchase = view.findViewById(R.id.btnPurchase);

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseScreen newFragment = new PurchaseScreen();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, newFragment);
                ft.commit();
            }
        });


        itemTitle.setText(getArguments().getString("ItemName"));
        itemDescrip.setText(getArguments().getString("ItemDescription"));
        itemPricing.setText(Double.toString(getArguments().getDouble("ItemPrice")));
        itemSign.setText("€");


        mMapView = (MapView) mview.findViewById(R.id.map);
        if (mMapView != null) {
        mMapView = (MapView) mview.findViewById(R.id.user_list_map);
        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(52.673872, -8.575679)).title("Limerick"));
        CameraPosition Limerick = CameraPosition.builder().target(new LatLng(52.673872, -8.575679)).zoom(16).bearing(0).tilt(50).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Limerick));
    }
}