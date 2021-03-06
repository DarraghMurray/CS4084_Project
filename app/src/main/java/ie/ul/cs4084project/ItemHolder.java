package ie.ul.cs4084project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolder extends RecyclerView.ViewHolder {

    //declarations for all UI elements
    TextView nameTxtView;
    TextView descriptionTxtView;
    TextView priceTxtView;
    TextView userNameTxtView;
    ImageView itemHolderImage;

    /**
     * ItemHolder default ItemHolder constructor initializes all UI elements
     *
     * @param itemView final View
     */
    public ItemHolder(@NonNull final View itemView) {
        super(itemView);

        nameTxtView = itemView.findViewById(R.id.nameTxtView);
        descriptionTxtView = itemView.findViewById(R.id.descriptionTxtView);
        priceTxtView = itemView.findViewById(R.id.priceTxtView);
        userNameTxtView = itemView.findViewById(R.id.userNameTxtView);
        itemHolderImage = itemView.findViewById(R.id.itemHolderImage);
    }

}
