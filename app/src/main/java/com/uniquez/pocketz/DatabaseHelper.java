package com.uniquez.pocketz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseInterface{

    public static final String ITEM_TABLE = "ITEM_TABLE";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_ITEM_QTY = "ITEM_QTY";
    public static final String COLUMN_ITEM_EXP = "ITEM_EXP";
    public static final String COLUMN_ITEM_CATEGORY = "ITEM_CATEGORY";
    public static final String COLUMN_ITEM_STORAGE = "ITEM_STORAGE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "item.db", null, 1);
    }

    //first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ITEM_TABLE + " (" + COLUMN_ITEM_NAME + " TEXT, " + COLUMN_ITEM_QTY + " INT, " + COLUMN_ITEM_EXP + " TEXT, " + COLUMN_ITEM_CATEGORY + " TEXT, " + COLUMN_ITEM_STORAGE + " TEXT)";

        db.execSQL(createTableStatement);
    }

    //called when there is DB change
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean addOne (ItemModel itemModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, itemModel.getName());
        cv.put(COLUMN_ITEM_QTY, itemModel.getQty());
        try{
            cv.put(COLUMN_ITEM_EXP, itemModel.getExpDate().toString());
        }catch (Exception e){
            cv.put(COLUMN_ITEM_EXP, "0");
        }

        cv.put(COLUMN_ITEM_CATEGORY, itemModel.getCategory());
        cv.put(COLUMN_ITEM_STORAGE, itemModel.getStorage());

        long insert = db.insert(ITEM_TABLE, null, cv);

        if (insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public Cursor readAllData(){
        String query = "SELECT * FROM " + ITEM_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    boolean updateData(ItemModel item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_QTY, (item.getQty()-1));
        long result = db.update(ITEM_TABLE, cv, "ITEM_NAME=? AND ITEM_CATEGORY=? AND ITEM_STORAGE=?", new String[] {item.getName(), item.getCategory(), item.getStorage()});
        return result != -1;
    }
}
