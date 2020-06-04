package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase myDatabase;
    private ListAdapter myAdapter;
    private int amount=0;
    private EditText nameEdiText;
    private TextView amntTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShoppingListSQLHelper dbHelper = new ShoppingListSQLHelper(this);
        myDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new ListAdapter(this, getAllItems());
        recyclerView.setAdapter(myAdapter);


        //ItemTouchHelper - hjälper till att radera items via swipe motions
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }


            //Swipe:a höger på en vara i listan för att ta bort en vara
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());

            }
        }).attachToRecyclerView(recyclerView);
        //namn på vara
        nameEdiText = findViewById(R.id.nameTextView);
        //mängd varor i textvy
        amntTextView = findViewById(R.id.amntTextView);
        //knapp ökar värde
        Button incBtn = findViewById(R.id.incBtn);
        //knapp minskar värde
        Button decBtn = findViewById(R.id.decBtn);
        //knapp lägger till vara
        Button addBtn = findViewById(R.id.addBtn);
        //ökar satsen med 1
        incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });
        //minskar satsen med 1
        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });

        //lägger till vara
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        amount = 0;

    }

    //Ökar värde
    private void increment() {
        amount++;
        amntTextView.setText(String.valueOf(amount));
    }

    //Minskar värde
    private void decrement() {
        if (amount > 0) {
            amount--;
            amntTextView.setText(String.valueOf(amount));
        }
    }
    //Lägger till vara
    private void addItem() {
        if (nameEdiText.getText().toString().trim().length()==0 || amount == 0) {
            return;
        }

        String name = nameEdiText.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(ShoppingListContract.AddItemInput.NAME_COLUMN, name);
        cv.put(ShoppingListContract.AddItemInput.AMOUNT_COLUMN, amount);

        long rowInserted = myDatabase.insert(ShoppingListContract.AddItemInput.TABLE_NAME,null,cv);
        if(rowInserted != -1)
            Toast.makeText(this, "Ny rad tillagd, rad-id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        myAdapter.swapCursor(getAllItems());
        nameEdiText.getText().clear();
        amntTextView.setText(String.valueOf(0));
    }

    //Tar bort item från DB
    private void removeItem(long id) {
        myDatabase.delete(ShoppingListContract.AddItemInput.TABLE_NAME,
                ShoppingListContract.AddItemInput._ID + "=" + id, null);
        myAdapter.swapCursor(getAllItems());
    }
    //Hämtar alla varor från en DB-query
    private Cursor getAllItems() {
        return myDatabase.query(
                ShoppingListContract.AddItemInput.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ShoppingListContract.AddItemInput.TIMESTAMP_COLUMN + " DESC"
        );
    }

}
