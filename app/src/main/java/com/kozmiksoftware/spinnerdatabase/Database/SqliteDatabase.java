package com.kozmiksoftware.spinnerdatabase.Database;

import android.app.TaskStackBuilder;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kozmiksoftware.spinnerdatabase.Model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 3/1/2017.
 */

public class SqliteDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "food";
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ITEMNAME = "itemname";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_ITEMNAME + " TEXT" + ")";
        Log.v("SHAKEY", "CREATE_TABLE_QUERYSTRING: " + CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public List<Item> listItems(){
        String sql = "select * from " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> storeItems = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                storeItems.add(new Item(id, name));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return storeItems;
    }

    public void addItem(Item item){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEMNAME, item.getName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ITEMS,null,values);
    }
}
