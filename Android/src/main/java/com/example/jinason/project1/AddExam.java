package com.example.jinason.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

//The class which is for adding a exam record in exam database.
public class AddExam extends AppCompatActivity {

    private DataBaseManager3 myManager3;
    private EditText name, location;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private String date, time;
    Intent get_intent;

    int key = 0;        //student id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exam);

        //get student id information from EditStudent
        get_intent = getIntent();
        key = get_intent.getIntExtra("key",0);
        System.out.print("\n In AddExam_key : " + key + "\n");


        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        findViewById(R.id.add_button).setOnClickListener(new SaveButtonClick());
    }

    public void saveData(){
        myManager3 = new DataBaseManager3(AddExam.this);

        name = (EditText) findViewById(R.id.name);
        location = (EditText) findViewById(R.id.location);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        // The format is "dd/MM/yyyy mm:HH"
        date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
        time = timePicker.getHour() + ":" + timePicker.getMinute();
        try {
            myManager3.addRow(name.getText().toString(), date, time, location.getText().toString(), key);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goBackToEditStudent(){
            Intent intent = new Intent(AddExam.this, EditStudent.class);
            intent.putExtra("key",key);
            startActivity(intent);
    }

    class SaveButtonClick implements View.OnClickListener {
        public void onClick(View v) {
            saveData();
            goBackToEditStudent();
        }
    }
}
