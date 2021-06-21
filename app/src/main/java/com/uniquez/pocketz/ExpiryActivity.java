package com.uniquez.pocketz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExpiryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView mBack;

    String finalDate;

    DatabaseHelper db;
    ArrayList<String> item_name;
    ArrayList<Integer> item_qty;
    ArrayList<String> item_exp;
    ArrayList<String> item_category;
    ArrayList<String> item_storage;

    CustomAdapter customAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry);

        mBack = findViewById(R.id.backArrow);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recyclerViewExpiry);

        db = new DatabaseHelper(ExpiryActivity.this);
        item_name = new ArrayList<>();
        item_qty = new ArrayList<>();
        item_exp = new ArrayList<>();
        item_category = new ArrayList<>();
        item_storage = new ArrayList<>();

        try {
            storeDataInArrays();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        customAdapter = new CustomAdapter(ExpiryActivity.this, item_name, item_qty, item_exp, item_category, item_storage);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpiryActivity.this));

        if (customAdapter.getItemCount() == 0){
            TextView instruct = findViewById(R.id.no_data);
            instruct.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void storeDataInArrays() throws ParseException {
        Cursor cursor = db.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Object date = dateFormat.format(calendar.getTime());
                if(cursor.getInt(1) != 0){
                    if (cursor.getString(2).equals("0")){
                        item_exp.add("0");
                    }else{
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
                        SimpleDateFormat formatterFinal = new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            Date expDate = formatter.parse(cursor.getString(2));
                            finalDate = formatterFinal.format(expDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    final DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    final LocalDate firstDate = LocalDate.parse(date.toString(), formatters);
                    final LocalDate secondDate = LocalDate.parse(finalDate, formatters);
                    final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
                    Log.e("Days between: ", String.valueOf(days));

                    if (Integer.parseInt(String.valueOf(days)) <= 7){
                        item_name.add(cursor.getString(0));
                        item_qty.add(cursor.getInt(1));
                        item_exp.add(finalDate);
                        item_category.add(cursor.getString(3));
                        item_storage.add(cursor.getString(4));
                    }
                }
            }
        }
        Log.e("databasecheck", item_name.toString());
    }
}