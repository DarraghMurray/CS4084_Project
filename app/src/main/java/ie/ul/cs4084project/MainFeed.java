package ie.ul.cs4084project;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFeed extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirestoreRecyclerAdapter adapter;

    public MainFeed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFeed.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFeed newInstance(String param1, String param2) {
        MainFeed fragment = new MainFeed();
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

    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_feed, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView itemFeed = view.findViewById(R.id.itemFeed);
        itemFeed.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection("posts").orderBy("timeStamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Item> options = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Item, ItemHolder>(options) {

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

            @Override
            public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_rows, parent, false);

                return new ItemHolder(view);
            }
        };

        itemFeed.setAdapter(adapter);
    }
}