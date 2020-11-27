package ie.ul.cs4084project;

import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
    TextView priceSign;

    public ItemHolder(@NonNull final View itemView) {
        super(itemView);

        nameTxtView = itemView.findViewById(R.id.nameTxtView);
        descriptionTxtView = itemView.findViewById(R.id.descriptionTxtView);
        priceTxtView = itemView.findViewById(R.id.priceTxtView);
        priceSign = itemView.findViewById(R.id.priceSign);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                ItemPage fragment = new ItemPage();
                Bundle args = new Bundle();
                args.putString("ItemName", nameTxtView.getText().toString());
                args.putString("ItemDescription", descriptionTxtView.getText().toString());
                args.putString("ItemSign", (String) priceSign.getText());
                args.putDouble("ItemPrice", Double.parseDouble(priceTxtView.getText().toString()));
                fragment.setArguments(args);
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, fragment);
                ft.commit();
            }
        });
    }

}
