package ie.ul.cs4084project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_RE_LOG = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button updateEmailBtn;
    private Button updatePasswordBtn;

    TextInputLayout textInputNewEmail;
    TextInputLayout textInputNewPass;

    Button reLogBtn;

    View rootLayout;

    private FirebaseUser currentUser;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputNewEmail = view.findViewById(R.id.textInputNewEmail);
        textInputNewPass = view.findViewById(R.id.textInputNewPass);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        rootLayout = getActivity().findViewById(android.R.id.content);
        reLogBtn = view.findViewById(R.id.reLogBtn);
        updateEmailBtn = view.findViewById(R.id.updateEmailBtn);
        updatePasswordBtn = view.findViewById(R.id.updatePasswordBtn);

        updateEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmail();
            }
        });
        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
        reLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLog();
            }
        });
    }

    private void updateEmail() {
        String email = textInputNewEmail.getEditText().getText().toString();
        currentUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(rootLayout, "Email update succeeded", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthRecentLoginRequiredException) {
                    Toast.makeText(getContext(), "Must Have Logged-In recently", Toast.LENGTH_LONG).show();
                    reLog();
                } else if (e instanceof FirebaseAuthEmailException) {
                    Snackbar.make(rootLayout, "Reset Email Failed to Send", Snackbar.LENGTH_LONG).show();
                } else if (e instanceof FirebaseAuthUserCollisionException) {
                    Snackbar.make(rootLayout, "Email update Failed", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(rootLayout, "Email update Failed", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updatePassword() {
        String password = textInputNewPass.getEditText().getText().toString();
        currentUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(rootLayout, "Password update succeeded", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthRecentLoginRequiredException) {
                    Toast.makeText(getContext(), "Must have logged-In recently", Toast.LENGTH_LONG).show();
                    reLog();
                } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Snackbar.make(rootLayout, "Password Too Weak", Snackbar.LENGTH_LONG).show();
                } else if (e instanceof FirebaseAuthEmailException) {
                    Snackbar.make(rootLayout, "Reset Email Failed to Send", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(rootLayout, "Password Update Failed", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void reLog() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers).build(), RC_RE_LOG);
    }
}