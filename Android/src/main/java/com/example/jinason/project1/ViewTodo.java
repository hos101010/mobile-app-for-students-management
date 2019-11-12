package com.example.jinason.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class ViewTodo extends AppCompatActivity {

    private DataBaseManager2 mydManager;
    private ListView cmp_list, not_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_todo);

        findViewById(R.id.home).setOnClickListener(new GotoHome());


        mydManager = new DataBaseManager2(this);
        mydManager.openReadable();

        cmp_list = (ListView)findViewById(R.id.cmp_list);
        ArrayList<String> tableContent = mydManager.retrieveRow("Completed");
        ArrayAdapter<String> arrayAdpt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, tableContent);
        cmp_list.setAdapter(arrayAdpt);

        not_list = (ListView)findViewById(R.id.not_list);
        tableContent = mydManager.retrieveRow("Not_Completed");
        arrayAdpt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, tableContent);
        not_list.setAdapter(arrayAdpt);

    }

    class GotoHome implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(ViewTodo.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
