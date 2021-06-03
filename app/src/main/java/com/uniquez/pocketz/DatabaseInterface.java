package com.uniquez.pocketz;

import android.database.Cursor;

interface DatabaseInterface {
    boolean addOne(ItemModel itemModel);
    Cursor readAllData();

}
