package com.uniquez.pocketz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    //Reference to buttons and texts views
    ImageView mBack = findViewById(R.id.backArrow);
    EditText itemName = findViewById(R.id.itemName);
    EditText itemQty = findViewById(R.id.itemQty);
    EditText expiryDate = findViewById(R.id.expiryDate);
    EditText storageDetails = findViewById(R.id.storageDetails);
    Button addButton = findViewById(R.id.addButton);
    final Spinner spinner = (Spinner) findViewById(R.id.categoryList);
    String selectedItemText;

    // Initializing a String Array for drop down list (spinner)
    String[] category = new String[]{
            "Select a category...",
            "Milk",
            "Eggs",
            "Snacks",
            "Cheese",
            "Fish",
            "Chicken",
            "Beef",
            "Pork",
            "Drinks",
            "Vegetables",
            "Fruits",
            "Others"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Setting the back button on the top of the page
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });

        //Making Array List of category
        final List<String> categoryList = new ArrayList<>(Arrays.asList(category));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,categoryList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

       

    }
}