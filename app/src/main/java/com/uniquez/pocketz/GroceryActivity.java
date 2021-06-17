package com.uniquez.pocketz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GroceryActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    DatabaseHelper db;
    ArrayList<Integer> item_qty;
    ArrayList<String> item_category;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        recyclerView = findViewById(R.id.recyclerViewGroceries);
        ImageView mBack = findViewById(R.id.backArrow);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });

        db = new DatabaseHelper(GroceryActivity.this);
        item_qty = new ArrayList<>();
        item_category = new ArrayList<>();


        try {
            storeDataInArrays();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e("Listss", item_category.toString());
        GroceryAdapter groceryAdapter = new GroceryAdapter(GroceryActivity.this, item_category, item_qty);
        recyclerView.setAdapter(groceryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(GroceryActivity.this));
        if (groceryAdapter.getItemCount() == 0){
            TextView instruct = findViewById(R.id.instruction);
            instruct.setVisibility(View.VISIBLE);
        }
    }

    void storeDataInArrays() throws ParseException {
        Cursor cursor = db.readGroceryList();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            Log.e("Listss", "have data");
            Log.e("Listss", cursor.toString());
            while (cursor.moveToNext()){
                if (cursor.getInt(1) == 0){
                    item_category.add(cursor.getString(0));
                    item_qty.add(cursor.getInt(1));
                }
            }
        }
        Log.e("Listss", item_category.toString());
        Log.e("Listss", item_qty.toString());
    }
}