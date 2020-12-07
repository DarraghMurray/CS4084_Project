package ie.ul.cs4084project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

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
    public static final int REQUEST_LOCATION = 4;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputLayout textInputName;
    private TextInputLayout textInputDescription;
    private TextInputLayout textInputPrice;
    private Spinner categorySpinner;
    private ImageView pickedImage;
    private Button pickImage;
    private Uri imageUri;

    private int locationRequestCode = 1000;

    private double latitude;
    private double longitude;

    private Item item;
    private FirebaseStorage storage;
    private StorageReference imageRef;

    private static final int PICK_IMAGE = 100;

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
        storage = FirebaseStorage.getInstance();
        item = new Item();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ((MainActivity) getActivity()).permission = false;
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, locationRequestCode);
        } else {
            LocationFinder finder;
            finder = new LocationFinder(getContext());
            if (finder.canGetLocation()) {
                item.setLatitude(finder.getLatitude());
                item.setLongitude(finder.getLongitude());
                Log.d("DDDDDDDDDdd", finder.getLatitude() + " " + finder.getLongitude());
            } else {
                finder.showSettingsAlert();
            }
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
        categorySpinner = view.findViewById(R.id.categorySpinner);
        pickImage = view.findViewById(R.id.pickImage);
        pickedImage = view.findViewById(R.id.pickedImage);
        pickedImage.setDrawingCacheEnabled(true);
        pickedImage.buildDrawingCache();
        imageUri = null;

        Button Post = view.findViewById(R.id.Post);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference ref = db.collection("posts").document();

        imageRef = storage.getReference().child("images/" + ref.getId());

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateName() && validateDescription() && validatePrice() && validateCategory()) {
                    item.setName(textInputName.getEditText().getText().toString());
                    item.setDescription(textInputDescription.getEditText().getText().toString());
                    item.setPrice(Integer.parseInt(textInputPrice.getEditText().getText().toString()));
                    item.setCategory(categorySpinner.getSelectedItem().toString());
                    item.setSellerName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    item.setId(ref.getId());
                    if (imageUri == null) {
                        item.setItemImage(null);
                    } else {
                        item.setItemImage(imageUri.toString());
                    }
                    System.out.println(item.getId());
                    ref.set(item);

                    MainFeed newFragment = new MainFeed();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment, newFragment);
                    ft.commit();
                }
            }
        });

        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/");
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            pickedImage.setImageURI(data.getData());
            Bitmap bitmap = ((BitmapDrawable) pickedImage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();
            upload(imageData);
        }
    }

    private void upload(byte[] imageData) {
        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            }
        });
        Task<Uri> URITask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    imageUri = task.getResult();
                } else {

                }
            }
        });

    }

    private boolean validateCategory() {
        if (categorySpinner.getSelectedItem().toString().equals("-select a category-")) {
            Toast.makeText(getContext(), "Must select a category", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
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