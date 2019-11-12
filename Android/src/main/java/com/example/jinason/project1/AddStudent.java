package com.example.jinason.project1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddStudent extends AppCompatActivity {

    private DataBaseManager myManager;
    private ImageView profile_img;
    private TextView response;
    private EditText fn, ln, cs, ad;
    private EditText id;
    private RadioGroup rg;
    private boolean recInserted;
    private String profile_address, spinner_text = "";
    private TextView age_tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        getPicIntent();
        setNumberPicker();

        findViewById(R.id.profile).setOnClickListener(new ChangeImg());
        findViewById(R.id.add_button).setOnClickListener(new SaveData());
        findViewById(R.id.home).setOnClickListener(new GotoHome());

    }

    public void getPicIntent(){
        profile_img = (ImageView) findViewById(R.id.profile);
        id = (EditText)findViewById(R.id.student_id);
        Intent intent = getIntent();
        profile_address = intent.getStringExtra("value");

        try {   //if user didn't select any picture yet, going to catch
            int temp = intent.getIntExtra("key",0);
            if (temp != 0)  id.setText(String.valueOf(temp));
        }catch (Exception e){}

        //if user didn't select any picture yet, it is just default image.
        if (profile_address == null || profile_address.length() == 0) {
            profile_img.setImageResource(R.drawable.default_profile);
            profile_address = "default_profile";
        } else {
            profile_img.setImageURI(Uri.parse("android.resource://com.example.jinason.project1/drawable/" + profile_address));
        }
    }

    public void setNumberPicker(){
        age_tv = (TextView) findViewById(R.id.age_tv);
        final NumberPicker np = (NumberPicker) findViewById(R.id.picker);
        //final int age;

        np.setMinValue(0);
        np.setMaxValue(100);
        np.setWrapSelectorWheel(true);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                age_tv.setText(Integer.toString(newVal));
            }
        });
    }


    class ChangeImg implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(AddStudent.this, SelectProfile.class);
            intent.putExtra("page", 1);
            startActivity(intent);
        }
    }


    class SaveData implements View.OnClickListener{
        public void onClick(View v){

            id = (EditText) findViewById(R.id.student_id);

            myManager = new DataBaseManager(AddStudent.this);

            response = (TextView)findViewById(R.id.response);
            profile_img = (ImageView)findViewById(R.id.profile);
            fn = (EditText) findViewById(R.id.first_name);
            ln = (EditText) findViewById(R.id.last_name);
            age_tv = (TextView) findViewById(R.id.age_tv);

            //gender
            rg = (RadioGroup)findViewById(R.id.gender_group);
            int gender_id = rg.getCheckedRadioButtonId();
            RadioButton gender_rb = (RadioButton) findViewById(gender_id);

            cs = (EditText) findViewById(R.id.course);
            ad = (EditText) findViewById(R.id.address);

            //addRow
            recInserted = myManager.addRow(Integer.parseInt(id.getText().toString()),
                    fn.getText().toString(),
                    ln.getText().toString(),
                    gender_rb.getText().toString(),
                    cs.getText().toString(),
                    Integer.parseInt(age_tv.getText().toString()),
                    ad.getText().toString(),
                    profile_address,
                    spinner_text
            );

            if (recInserted) {
                response.setText("The row in the students table is inserted");
            } else {
                response.setText("Sorry, errors when inserting to DB");
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(ad.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            profile_img.setImageResource(R.drawable.default_profile);
            id.setText("");
            fn.setText("");
            ln.setText("");
            cs.setText("");
            ad.setText("");
        }
    }

    class GotoHome implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(AddStudent.this, MainActivity.class);
            startActivity(intent);
        }
    }


}
