package com.example.jinason.project1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class EditTodo extends AppCompatActivity {

    private DataBaseManager2 myManager;
    private TextView response, name;
    private EditText location;
    private Button editButton, deleteButton;
    private RadioGroup rg;
    private RadioButton cmp_radiobtn, not_radiobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_todo);

        findViewById(R.id.home).setOnClickListener(new GotoHome());


        myManager = new DataBaseManager2(this);
        name = (TextView) findViewById(R.id.name);
        response = (TextView) findViewById(R.id.response);
        editButton = (Button) findViewById(R.id.edit_button);
        deleteButton = (Button) findViewById(R.id.delete_button);

        location = (EditText) findViewById(R.id.location);
        rg = (RadioGroup) findViewById(R.id.cmp_group);
        cmp_radiobtn = (RadioButton) findViewById(R.id.cmp_radiobtn);
        not_radiobtn = (RadioButton) findViewById(R.id.not_radiobtn);

        setSpinner();

        //edit data
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cmp_id = rg.getCheckedRadioButtonId();
                RadioButton cmp_rb = (RadioButton) findViewById(cmp_id);

                if (myManager.update(name.getText().toString(), location.getText().toString(), cmp_rb.getText().toString())) {
                    response.setText("Data is changed successfully.");
                } else {
                    response.setText("Data is NOT changed.");
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(location.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        //delete data
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myManager.delete(name.getText().toString())) {
                    response.setText("Data is deleted successfully.");
                    setSpinner();
                } else {
                    response.setText("Data is NOT deleted.");
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(location.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    public void setSpinner(){
        ArrayList<String> key;
        key = myManager.retrieveKey();

        Spinner dropdown = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, key);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String key = parent.getItemAtPosition(position).toString();
                name.setText(key);
                location.setText(myManager.findElement(key, 1));

                if (myManager.findElement(key, 2).equals("Completed")) {
                    rg.check(cmp_radiobtn.getId());
                } else if (myManager.findElement(key, 2).equals("Not_Completed")) {
                    rg.check(not_radiobtn.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    class GotoHome implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(EditTodo.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
