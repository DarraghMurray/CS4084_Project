package ie.ul.cs4084project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindItem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirestoreRecyclerAdapter adapter;

    /**
     * FindItem default constructor
     */
    public FindItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindItem.
     */
    // TODO: Rename and change types and number of parameters
    public static FindItem newInstance(String param1, String param2) {
        FindItem fragment = new FindItem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onCreate default fragment onCreate
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
    }

    /**
     * starts recycler adapter listening
     */
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /**
     * stops recycler adapter listening
     */
    public void onStop() {
        super.onStop();
        adapter.stopListening();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_item, container, false);
    }

    /**
     * onViewCreated initializes and sets up UI elements
     * retrieves Firestore instance and creates query to get items of the specified category and order them by time stamp descending
     * Firestore recycler is created using this query and previously setup adapter
     * ItemSearchFeed's adapter is set to this adapter
     *
     * @param view               View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView itemSearchFeed = view.findViewById(R.id.itemSearchFeed);
        itemSearchFeed.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query;
        if (getArguments().getString("Category") != null) {
            if (getArguments().getString("searchTerm") == null) {
                query = db.collection("posts").whereEqualTo("category", getArguments().getString("Category"))
                        .orderBy("timeStamp", Query.Direction.DESCENDING);
            } else {
                String searchTerm = getArguments().getString("searchTerm");
                query = db.collection("posts").whereEqualTo("category", getArguments().getString("Category"))
                        .orderBy("timeStamp", Query.Direction.DESCENDING).whereGreaterThanOrEqualTo("name", searchTerm)
                        .whereLessThanOrEqualTo("name", searchTerm + "uF7FF");
                ;
            }
        } else {
            String searchTerm = getArguments().getString("searchTerm");
            query = db.collection("posts").whereGreaterThanOrEqualTo("name", searchTerm)
                    .whereLessThanOrEqualTo("name", searchTerm + "uF7FF");
        }
        FirestoreRecyclerOptions<Item> options = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Item, ItemHolder>(options) {

            /**
             * onBindViewHolder sets text for ItemHolder text views
             * uses Glide to load the holders ImageView
             * sets itemView on click listener which changes to the item page and sends a parcel of the item
             *
             * @param holder   ItemHolder provides current Holder
             * @param position int provides position of Holder
             * @param item     Final Item provides current Item
             */
            @Override
            public void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull final Item item) {
                holder.nameTxtView.setText(item.getName());
                holder.descriptionTxtView.setText(item.getDescription());
                holder.priceTxtView.setText(Double.toString(item.getPrice()));
                if (!(item.getItemImage() == null)) {
                    Glide.with(getContext()).load(Uri.parse(item.getItemImage())).into(holder.itemHolderImage);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemPage fragment = new ItemPage();
                        Bundle args = new Bundle();
                        args.putParcelable("Item", item);
                        fragment.setArguments(args);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment, fragment);
                        ft.commit();
                    }
                });
            }

            /**
             * onCreateViewHolder default onCreateViewHolder method
             *
             * @param parent   ViewGroup
             * @param viewType int
             * @return new ItemHolder
             */
            @Override
            public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_rows, parent, false);

                return new ItemHolder(view);
            }
        };
        itemSearchFeed.setAdapter(adapter);
    }
}