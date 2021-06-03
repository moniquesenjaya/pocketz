package com.uniquez.pocketz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button mButton;

    String finalDate;

    DatabaseHelper db;
    ArrayList<String> item_name;
    ArrayList<Integer> item_qty;
    ArrayList<String> item_exp;
    ArrayList<String> item_category;
    ArrayList<String> item_storage;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mButton = findViewById(R.id.addButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent addIntent = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(addIntent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        db = new DatabaseHelper(HomeActivity.this);
        item_name = new ArrayList<>();
        item_qty = new ArrayList<>();
        item_exp = new ArrayList<>();
        item_category = new ArrayList<>();
        item_storage = new ArrayList<>();

        Log.e("databasecheck", "outside try");

        try {
            Log.e("databasecheck", "does it");
            storeDataInArrays();
            Log.e("databasecheck", item_category.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        customAdapter = new CustomAdapter(HomeActivity.this, item_name, item_qty, item_exp, item_category, item_storage);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                updateItem((ItemModel) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);


    }

    void storeDataInArrays() throws ParseException {
        Cursor cursor = db.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){

                if(cursor.getInt(1) != 0){
                    if (cursor.getString(2).equals("0")){
                        item_exp.add("0");
                    }else{
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
                        SimpleDateFormat formatterFinal = new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            Date date = formatter.parse(cursor.getString(2));
                            Log.e("datecheck", date.toString());
                            finalDate = formatterFinal.format(date);
                            Log.e("datecheck", finalDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    item_name.add(cursor.getString(0));
                    item_qty.add(cursor.getInt(1));
                    item_exp.add(finalDate);
                    item_category.add(cursor.getString(3));
                    item_storage.add(cursor.getString(4));
                }
            }
        }
        Log.e("databasecheck", item_name.toString());
    }

    private void updateItem(ItemModel item){
        boolean result = db.updateData(item);
        if (result){
            Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
            recreate();
        }else{
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

    }
}