package com.uniquez.pocketz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList item_name, item_qty, item_exp, item_category, item_storage;

    CustomAdapter(Context context, ArrayList item_name, ArrayList item_qty, ArrayList item_exp, ArrayList item_category, ArrayList item_storage){
        this.context = context;
        this.item_name = item_name;
        this.item_qty = item_qty;
        this.item_exp = item_exp;
        this.item_category = item_category;
        this.item_storage = item_storage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item_name_display.setText(String.valueOf(item_name.get(position)));
        holder.item_qty_display.setText(String.valueOf(item_qty.get(position)));
        if (String.valueOf(item_exp.get(position)).equals("0")){
            holder.item_exp_display.setText("");
        }else{
            holder.item_exp_display.setText(String.valueOf(item_exp.get(position)));
        }
        holder.item_storage_display.setText(String.valueOf(item_storage.get(position)));
        holder.itemView.setTag(new ItemModel(String.valueOf(item_name.get(position)), (Integer) item_qty.get(position), String.valueOf(item_category.get(position)), String.valueOf(item_storage.get(position))));
    }

    @Override
    public int getItemCount() {
        return item_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView item_name_display, item_qty_display, item_exp_display, item_storage_display;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name_display = itemView.findViewById(R.id.itemNameDisplay);
            item_qty_display = itemView.findViewById(R.id.itemQtyDisplay);
            item_exp_display = itemView.findViewById(R.id.itemExpDisplay);
            item_storage_display = itemView.findViewById(R.id.itemStorageDisplay);

        }
    }

}
