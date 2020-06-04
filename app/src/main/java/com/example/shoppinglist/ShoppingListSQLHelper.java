package com.example.shoppinglist;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.shoppinglist.ShoppingListContract.*;
import androidx.annotation.Nullable;

public class ShoppingListSQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "groceryList.db";

    public static final int DATABASE_VERSION = 1;

    public ShoppingListSQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //Hjälper till att initsiera databasen
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SHOPPINGLIST_TABLE = "CREATE TABLE " +
                AddItemInput.TABLE_NAME + " (" +
                AddItemInput._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AddItemInput.NAME_COLUMN + " TEXT NOT NULL, " +
                AddItemInput.AMOUNT_COLUMN + " INTEGER NOT NULL, " +
                AddItemInput.TIMESTAMP_COLUMN + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        //Execute:ar databas
        db.execSQL(SQL_CREATE_SHOPPINGLIST_TABLE);
    }

    //Anropas när man konstruerar SQL med en nyare version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AddItemInput.TABLE_NAME);
        onCreate(db);
    }
}
