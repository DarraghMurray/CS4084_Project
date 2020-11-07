package ie.ul.cs4084project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Map<String, Object>> myData;
    private LayoutInflater myInflater;
    private ItemClickListener myItemClickListener;

    public MyRecyclerViewAdapter(Context context, ArrayList<Map<String, Object>> data) {
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
        Map<String,Object> item = myData.get(position);
        String name = (String) item.get("ItemName");
        String description = (String) item.get("ItemDescription");
        String price = (String) item.get("ItemPrice");

        holder.nameTxtView.setText(name);
        holder.descriptionTxtView.setText(description);
        holder.pricetxtView.setText(price);
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public Map<String, Object> getItem(int position) {
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
