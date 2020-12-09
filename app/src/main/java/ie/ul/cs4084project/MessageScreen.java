package ie.ul.cs4084project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int EMAIL_SENT = 10;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Button to send emails
    private Button sendEmail;
    //text inputs from the layout
    private TextInputLayout textInputTo, textInputSubject, textInputBody;

    /**
     * MessageScreen default constructor
     */
    public MessageScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 String Parameter 1.
     * @param param2 String Parameter 2.
     * @return A new instance of fragment MessageScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageScreen newInstance(String param1, String param2) {
        MessageScreen fragment = new MessageScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onCreate default fragment onCreate method
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

    }

    /**
     * onCreateView default fragment onCreateView method
     * @param inflater           LayoutInflator
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return returns inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_screen, container, false);
    }

    /**
     * initializes UI elements
     * adds seller email if sellerEmail was passed in a bundle
     * initializes sendEmail button and sets on click listener
     * on click retrieves email text and places it in intent and starts email app chooser with the intent
     * @param view               View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputTo = view.findViewById(R.id.textInputTo);
        textInputSubject = view.findViewById(R.id.textInputSubject);
        textInputBody = view.findViewById(R.id.textInputBody);

        if (getArguments() != null) {
            textInputTo.getEditText().setText(getArguments().getString("sellerEmail"));
        }

        sendEmail = view.findViewById(R.id.Send);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailsend = textInputTo.getEditText().getText().toString();
                String emailsubject = textInputSubject.getEditText().getText().toString();
                String emailbody = textInputBody.getEditText().getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailsend});
                intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
                intent.putExtra(Intent.EXTRA_TEXT, emailbody);
                intent.setType("message/rfc822");

                startActivityForResult(Intent.createChooser(intent, "Choose an Email client :"), 10);
            }
        });
    }

    /**
     * onActivityResult method responds to results of activity started for result
     * checks what request code was received
     * checks result code for success
     * if request is to send an email and it succeeds it clears textInputLayouts.
     *
     * @param requestCode int
     * @param resultCode  int
     * @param data        Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EMAIL_SENT) {
            textInputTo.getEditText().getText().clear();
            textInputSubject.getEditText().getText().clear();
            textInputBody.getEditText().getText().clear();
        }
    }
}