package com.kozmiksoftware.spinnerdatabase;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kozmiksoftware.spinnerdatabase.Database.SqliteDatabase;
import com.kozmiksoftware.spinnerdatabase.Model.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SqliteDatabase mDatabase;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mDatabase = new SqliteDatabase(this);
        List<Item> allItems = mDatabase.listItems();
        String[] itemNameList = new String[allItems.size()];

        for(int i = 0; i < allItems.size(); i++){
            itemNameList[i] = allItems.get(i).getName();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemNameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a new item
                addItemDialog();
            }
        });
    }

    private void addItemDialog() {
        LayoutInflater inflator = LayoutInflater.from(this);
        View subView = inflator.inflate(R.layout.add_item_layout, null);
        final EditText itemField = (EditText) subView.findViewById(R.id.enter_item);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add A New Item");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD ITEM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String item = itemField.getText().toString();
                if (TextUtils.isEmpty(item)) {
                    Toast.makeText(MainActivity.this, "Oops, Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    Item newItem = new Item(item);
                    mDatabase.addItem(newItem);
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "TAsk cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

}
