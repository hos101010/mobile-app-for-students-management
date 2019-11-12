package com.example.jinason.project1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddTodo extends AppCompatActivity {

    private DataBaseManager2 myManager;
    private TextView response;
    private EditText name, location;
    private boolean recInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo);

        findViewById(R.id.home).setOnClickListener(new GotoHome());
        findViewById(R.id.add_button).setOnClickListener(new SaveData());
    }

    class SaveData implements View.OnClickListener {
        public void onClick(View v) {
            myManager = new DataBaseManager2(AddTodo.this);

            response = (TextView)findViewById(R.id.response);
            name = (EditText) findViewById(R.id.name);
            location = (EditText) findViewById(R.id.location);

            // student Id (unique for each student),first name, last name, gender, course study, age and address.
            recInserted = myManager.addRow(name.getText().toString(), location.getText().toString(), "Not_Completed");

            if (recInserted) {
                response.setText("The row in the todo table is inserted");
            } else {
                response.setText("Sorry, errors when inserting to DB");
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(location.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            name.setText("");
            location.setText("");
        }
    }

    class GotoHome implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(AddTodo.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
