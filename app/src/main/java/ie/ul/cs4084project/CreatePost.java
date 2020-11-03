package ie.ul.cs4084project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePost extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputLayout textInputName;
    private TextInputLayout textInputDescription;
    private TextInputLayout textInputPrice;

    public CreatePost() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreatePost.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatePost newInstance(String param1, String param2) {
        CreatePost fragment = new CreatePost();
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
        return inflater.inflate(R.layout.fragment_create_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputName = view.findViewById(R.id.TextInputName);
        textInputDescription = view.findViewById(R.id.TextInputDescription);
        textInputPrice = view.findViewById(R.id.TextInputPrice);
    }

    // adds post data to database and exits CreatePost fragment
    // not sure on adding images yet
    public void onPostClicked(View view) {

        if(validateName() && validateDescription() && validatePrice()) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            CollectionReference posts = db.collection("Posts");
            Map<String,Object> currentPost = new HashMap<>();
            currentPost.put("ItemName", textInputName.getEditText().getText().toString());
            currentPost.put("ItemDescription", textInputDescription.getEditText().getText().toString());
            currentPost.put("ItemPrice", textInputPrice.getEditText().getText().toString());
            currentPost.put("TimeStamp", System.currentTimeMillis());
            posts.document().set(currentPost);
        }

    }

    //these three methods validate the post input
    private boolean validatePrice() {
        String userInput = textInputName.getEditText().getText().toString();

        if(userInput.isEmpty()) {
            textInputName.setError("field can't be empty");
            return false;
        } else {
            textInputName.setError(null);
            return true;
        }
    }

    private boolean validateDescription() {
        String userInput = textInputDescription.getEditText().getText().toString();

        if(userInput.isEmpty()) {
            textInputDescription.setError("field can't be empty");
            return false;
        } else {
            textInputDescription.setError(null);
            return true;
        }
    }

    private boolean validateName() {
        String userInput = textInputPrice.getEditText().getText().toString();

        if(userInput.isEmpty()) {
            textInputPrice.setError("field can't be empty");
            return false;
        } else {
            textInputPrice.setError(null);
            return true;
        }
    }
}