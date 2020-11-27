package ie.ul.cs4084project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

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

    public void onSearchClicked(View view) {
       /* FindItem newFragment = new FindItem();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString("searchTxt", textInputSearch.getEditText().getText().toString());
        args.putString("Category", categorySpinner.getSelectedItem().toString());
        newFragment.setArguments(args);

        ft.replace(R.id.fragment, newFragment);
        ft.commit(); */

    }

}