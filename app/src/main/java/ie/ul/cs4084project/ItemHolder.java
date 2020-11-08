package ie.ul.cs4084project;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolder extends RecyclerView.ViewHolder {

    TextView nameTxtView;
    TextView descriptionTxtView;
    TextView priceTxtView;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);

        nameTxtView = itemView.findViewById(R.id.nameTxtView);
        descriptionTxtView = itemView.findViewById(R.id.descriptionTxtView);
        priceTxtView = itemView.findViewById(R.id.priceTxtView);
    }
}
