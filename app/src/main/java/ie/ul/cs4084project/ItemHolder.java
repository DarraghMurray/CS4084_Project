package ie.ul.cs4084project;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolder extends RecyclerView.ViewHolder  {

    TextView nameTxtView;
    TextView descriptionTxtView;
    TextView priceTxtView;
    TextView userNameTxtView;

    public ItemHolder(@NonNull final View itemView) {
        super(itemView);

        nameTxtView = itemView.findViewById(R.id.nameTxtView);
        descriptionTxtView = itemView.findViewById(R.id.descriptionTxtView);
        priceTxtView = itemView.findViewById(R.id.priceTxtView);
        userNameTxtView = itemView.findViewById(R.id.userNameTxtView);
    }

}
