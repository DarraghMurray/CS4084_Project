package ie.ul.cs4084project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFeed extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MyRecyclerViewAdapter adapter;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<Map<String, Object>> items = new ArrayList<>();

        db.collection("posts").orderBy("TimeStamp", Query.Direction.ASCENDING)
                .limitToLast(20).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    int i = 0;
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        items.add(document.getData());
                    }
                }else {
                    Log.w("tag", "Error getting documents",  task.getException());
                }
            }
        });

        itemFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), items);
        adapter.setClickListener(this);
        itemFeed.setAdapter(adapter);
    }

    public void onItemClick(View view, int position) {
        Toast.makeText( getActivity(),"you clicked " + adapter.getItem(position) + " on row number"
                + position, Toast.LENGTH_SHORT).show();
    }


}