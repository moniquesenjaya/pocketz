package com.uniquez.pocketz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    String selectedItemText;
    Date convertDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Reference to buttons and texts views
        ImageView mBack = findViewById(R.id.backArrow);
        EditText itemName = findViewById(R.id.itemName);
        EditText itemQty = findViewById(R.id.itemQty);
        EditText expiryDate = findViewById(R.id.expiryDate);
        EditText storageDetails = findViewById(R.id.storageDetails);
        Button addButton = findViewById(R.id.addButton);
        final Spinner spinner = (Spinner) findViewById(R.id.categoryList);

        // Initializing a String Array for drop down list (spinner)
        String[] category = new String[]{
                "Select a category...*",
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
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    selectedItemText = (String) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v){

                ItemModel itemModel = null;

                try{
                    //check if exp date is avail
                    if(expiryDate.getText() == null){
                        itemModel = new ItemModel(itemName.getText().toString(), Integer.parseInt(itemQty.getText().toString()), selectedItemText, storageDetails.getText().toString());
                    }else{
                        //try to convert the date
                        try {
                            convertDate = new SimpleDateFormat("yyyy/MM/dd").parse(expiryDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        itemModel = new ItemModel(itemName.getText().toString(), Integer.parseInt(itemQty.getText().toString()), convertDate , selectedItemText, storageDetails.getText().toString());
                    }
                } catch (Exception e){
                    Toast.makeText(AddActivity.this, "Error in making item", Toast.LENGTH_SHORT).show();
                }

                try{
                    DatabaseHelper databaseHelper = new DatabaseHelper(AddActivity.this);
                    boolean success = databaseHelper.addOne(itemModel);

                    if (success){
                        Toast.makeText(AddActivity.this, "Data added to the database", Toast.LENGTH_SHORT).show();
                        Intent addIntent = new Intent(AddActivity.this, HomeActivity.class);
                        startActivity(addIntent);
                    }
                }catch(Exception e){
                    Toast.makeText(AddActivity.this, "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}