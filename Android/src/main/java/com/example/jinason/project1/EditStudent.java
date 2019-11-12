package com.example.jinason.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class EditStudent extends AppCompatActivity {
    private DataBaseManager myManager;
    private DataBaseManager3 myManager3;
    private TextView response, student_id, age_tv, exam_tv;
    private EditText fn, ln, cs, ad;
    private RadioGroup rg;
    private RadioButton female_radiobtn, male_radiobtn;
    private NumberPicker np;
    private ImageView profile_img;
    private String profile_address, spinner_text;
    private boolean selected_image_flag = false;
    private int key = 0, old_key = 0;
    ListView listView, listView2;
    private CustomAdapter adapter, adapter2;
    private TextView present;
    long present_long;
    String present_string;
    ArrayList earlier_exam_arraylist, later_exam_arraylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_student);

        myManager = new DataBaseManager(this);
        response = (TextView) findViewById(R.id.response);
        student_id = (TextView) findViewById(R.id.student_id);
        fn = (EditText) findViewById(R.id.first_name);
        ln = (EditText) findViewById(R.id.last_name);
        cs = (EditText) findViewById(R.id.course);
        ad = (EditText) findViewById(R.id.address);
        rg = (RadioGroup) findViewById(R.id.gender_group);
        female_radiobtn = (RadioButton) findViewById(R.id.female_radiobtn);
        male_radiobtn = (RadioButton) findViewById(R.id.male_radiobtn);
        age_tv = (TextView) findViewById(R.id.age_tv);
        np = (NumberPicker) findViewById(R.id.picker);
        profile_img = (ImageView) findViewById(R.id.profile);
        listView = (ListView) findViewById(R.id.exam_listView);
        listView2 = (ListView) findViewById(R.id.exam_listView2);

        getPicIntent();
        setSpinner();
        setNumberpicker();
        findViewById(R.id.profile).setOnClickListener(new ChangeImg());
        findViewById(R.id.map_button).setOnClickListener(new GotoMap());
        findViewById(R.id.view_exam_btn).setOnClickListener(new ClickViewExam());
        findViewById(R.id.add_exam_btn).setOnClickListener(new ClickAddExam());
        findViewById(R.id.delete_exam_button).setOnClickListener(new ClickDeleteExam());
        findViewById(R.id.edit_button).setOnClickListener(new EditData());
        findViewById(R.id.delete_button).setOnClickListener(new DeleteData());
        findViewById(R.id.home).setOnClickListener(new GotoHome());

    }

    public void setSpinner() {
        ArrayList<Integer> key_list;
        key_list = myManager.retrieveKey();

        final Spinner dropdown = findViewById(R.id.spinner);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, key_list);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (selected_image_flag == true) {   //if user wants to change the image (old_key : student who wants to change - using intent)
                    key = old_key;
                    selected_image_flag = false;
                } else {                             //if user didn't select a image (key : student who user selected in spinner)
                    key = (Integer) parent.getItemAtPosition(position);
                    profile_address = myManager.findElement(key, 7);
                }

                student_id.setText(Integer.toString(key));
                fn.setText(myManager.findElement(key, 1));
                ln.setText(myManager.findElement(key, 2));
                cs.setText(myManager.findElement(key, 4));
                age_tv.setText(Integer.toString(myManager.findAge(key)));
                ad.setText(myManager.findElement(key, 6));

                profile_img.setImageURI(Uri.parse("android.resource://com.example.jinason.project1/drawable/" + profile_address));

                //exam list is hidden
                listView.setVisibility(View.GONE);
                listView2.setVisibility(View.GONE);

                if (myManager.findElement(key, 3).equals("Female")) {
                    rg.check(female_radiobtn.getId());
                } else if (myManager.findElement(key, 3).equals("Male")) {
                    rg.check(male_radiobtn.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getPicIntent() {
        Intent intent = getIntent();
        profile_address = intent.getStringExtra("value");
        old_key = intent.getIntExtra("key", 0);

        if (profile_address != null) {       //when user selects image
            selected_image_flag = true;
        }
    }

    public void setNumberpicker() {
        np.setMinValue(0);
        np.setMaxValue(100);
        np.setWrapSelectorWheel(true);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                age_tv.setText(Integer.toString(newVal));
            }
        });
    }


    class ClickViewExam implements View.OnClickListener {
        public void onClick(View v) {

            myManager3 = new DataBaseManager3(EditStudent.this);
            present = (TextView) findViewById(R.id.present);
            listView = (ListView) findViewById(R.id.exam_listView);
            listView2 = (ListView) findViewById(R.id.exam_listView2);
            listView.setVisibility(View.VISIBLE);
            listView2.setVisibility(View.VISIBLE);


            present_long = System.currentTimeMillis();
            Date present_date = new Date(present_long);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            present_string = sdf.format(present_date);
            present.setText("current time : " + present_string);


            //list view for uncompleted exam
            earlier_exam_arraylist = myManager3.findEarlier_withNonChecked(present_date, key);
            adapter = new CustomAdapter(earlier_exam_arraylist, getApplicationContext());
            listView.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(listView);

            //list view for completed exam
            later_exam_arraylist = myManager3.findLater_withNonChecked(present_date, key);
            adapter2 = new CustomAdapter(later_exam_arraylist, getApplicationContext());
            listView2.setAdapter(adapter2);
            Utility.setListViewHeightBasedOnChildren(listView2);


            //when users click checkbox
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {

                    MyDataModel dataModel = (MyDataModel) earlier_exam_arraylist.get(position);
                    dataModel.checked = !dataModel.checked;
                    adapter.notifyDataSetChanged();
                    Toast.makeText(EditStudent.this, dataModel.name + ": " + dataModel.checked, Toast.LENGTH_SHORT).show();
                }
            });

            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {

                    MyDataModel dataModel = (MyDataModel) later_exam_arraylist.get(position);
                    dataModel.checked = !dataModel.checked;
                    adapter2.notifyDataSetChanged();
                    Toast.makeText(EditStudent.this, dataModel.name + ": " + dataModel.checked, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    class ClickAddExam implements View.OnClickListener {
        public void onClick(View v) {
            try {
                Intent intent = new Intent(EditStudent.this, AddExam.class);
                intent.putExtra("key", Integer.parseInt(student_id.getText().toString()));
                System.out.print("\n in ClickAddExam_stu_id : "+ Integer.parseInt(student_id.getText().toString()) +"\n");
                startActivity(intent);

            } catch (Exception e) {
                Toast.makeText(EditStudent.this, "Input student id first", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }

    class ClickDeleteExam implements View.OnClickListener {
        public void onClick(View v) {
            int size =  earlier_exam_arraylist.size();
            for (int i = 0; i < size; i++) {
                System.out.print("\n i : "+i+"\n");
                MyDataModel dataModel = (MyDataModel) earlier_exam_arraylist.get(i);
                if (dataModel.checked == true) {
                    myManager3.delete(dataModel.name);
                    earlier_exam_arraylist.remove(dataModel.name);
                    response.setText("Data is deleted successfully.");
                }
            }

            size = later_exam_arraylist.size();
            for (int i = 0; i < size; i++) {
                System.out.print("\n i : "+i+"\n");
                MyDataModel dataModel = (MyDataModel) later_exam_arraylist.get(i);
                if (dataModel.checked == true) {
                    myManager3.delete(dataModel.name);
                    later_exam_arraylist.remove(dataModel.name);
                    response.setText("Data is deleted successfully.");
                }
            }
        }
    }

    class ChangeImg implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(EditStudent.this, SelectProfile.class);
            intent.putExtra("page", 2);
            intent.putExtra("key", key);
            startActivity(intent);
        }
    }


    class EditData implements View.OnClickListener {
        public void onClick(View v) {
            int gender_id = rg.getCheckedRadioButtonId();
            RadioButton gender_rb = (RadioButton) findViewById(gender_id);

            if (myManager.update(Integer.parseInt(student_id.getText().toString()),
                    fn.getText().toString(),
                    ln.getText().toString(),
                    gender_rb.getText().toString(),
                    cs.getText().toString(),
                    Integer.parseInt(age_tv.getText().toString()),
                    ad.getText().toString(),
                    profile_address,
                    null/*exam_tv.getText().toString()*/
            )) {
                response.setText("Data is changed successfully.");
            } else {
                response.setText("Data is NOT changed.");
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(ad.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    class DeleteData implements View.OnClickListener {
        public void onClick(View v) {
            if (myManager.delete(Integer.parseInt(student_id.getText().toString()))) {
                response.setText("Data is deleted successfully.");
                setSpinner();
            } else {
                response.setText("Data is NOT deleted.");
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(ad.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    class GotoHome implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(EditStudent.this, MainActivity.class);
            startActivity(intent);
        }
    }

    class GotoMap implements View.OnClickListener {
        public void onClick(View v) {
            final Geocoder geocoder = new Geocoder(EditStudent.this);

            List<Address> list = null;

            String str = ad.getText().toString();
            try {
                list = geocoder.getFromLocationName
                        (str, // spot name
                                10); // how many you read
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "error");
            }

            if (list != null) {
                if (list.size() == 0) {
                    Toast.makeText(EditStudent.this, "no result", Toast.LENGTH_SHORT).show();
                } else {
                    // giving intent to address
                    Address addr = list.get(0);
                    double lat = addr.getLatitude();
                    double lon = addr.getLongitude();

                    String sss = String.format("geo:%f,%f", lat, lon);

                    Intent intent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(sss));
                    startActivity(intent);
                }
            }
        }
    }
}