package com.uniquez.pocketz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceriesViewHolder> {
    private Context context;
    private ArrayList item_category, item_qty;

    GroceryAdapter(Context context, ArrayList item_category, ArrayList item_qty){
        this.context = context;
        this.item_qty = item_qty;
        this.item_category = item_category;
    }

    @NonNull
    @Override
    public GroceriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shopping_list_row, parent, false);
        return new GroceriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceriesViewHolder holder, int position) {
        Log.e("Listss", item_category.get(position).toString());
            holder.item_category_display.setText(item_category.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return item_category.size();
    }

    public class GroceriesViewHolder extends RecyclerView.ViewHolder{

        TextView item_category_display;

        public GroceriesViewHolder(@NonNull View itemView) {
            super(itemView);
            item_category_display = itemView.findViewById(R.id.itemCategoryDisplay);
        }
    }
}
