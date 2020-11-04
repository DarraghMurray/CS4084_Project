package ie.ul.cs4084project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> myData;
    private LayoutInflater myInflater;
    private ItemClickListener myItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<String> data) {
        this.myInflater = LayoutInflater.from(context);
        this.myData = data;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = myInflater.inflate(R.layout.recyclerview_rows, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        String name = myData.get(position);
        holder.nameTxtView.setText(name);
        holder.descriptionTxtView.setText(name + ":");
        holder.pricetxtView.setText(name + "%");
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public String getItem(int position) {
        return myData.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView nameTxtView;
        TextView descriptionTxtView;
        TextView pricetxtView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            nameTxtView = itemView.findViewById(R.id.nameTxtView);
            descriptionTxtView = itemView.findViewById(R.id.descriptionTxtView);
            pricetxtView = itemView.findViewById(R.id.priceTxtView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myItemClickListener != null) {
                myItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.myItemClickListener = itemClickListener;
    }
}