package ie.ul.cs4084project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout textInputSearch;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputSearch = findViewById(R.id.textInputSearch);
        categorySpinner = findViewById(R.id.categorySpinner);
    }

    public void onHomeClicked(View view) {

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

    public void onMessageClicked(View view) {

    }

    public void onSearchClicked(View view) {
        if(!categorySpinner.getSelectedItem().toString().equals("-select a category-")) {
            FindItem newFragment = new FindItem();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString("Category", categorySpinner.getSelectedItem().toString());
            newFragment.setArguments(args);
            ft.replace(R.id.fragment, newFragment);
            ft.commit();
        }
    }


}