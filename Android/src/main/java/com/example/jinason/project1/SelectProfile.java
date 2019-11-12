package com.example.jinason.project1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SelectProfile extends AppCompatActivity {

    private final int GALLERY_CODE=1112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_profile);

        findViewById(R.id.default_profile).setOnClickListener(new ApplyPic());
        findViewById(R.id.profile1).setOnClickListener(new ApplyPic());
        findViewById(R.id.profile3).setOnClickListener(new ApplyPic());
        findViewById(R.id.profile4).setOnClickListener(new ApplyPic());
        findViewById(R.id.profile5).setOnClickListener(new ApplyPic());
        findViewById(R.id.profile6).setOnClickListener(new ApplyPic());

        findViewById(R.id.add_button).setOnClickListener(new AddPic());

    }

    class ApplyPic implements View.OnClickListener {
        public void onClick(View v) {
            int key = 0;
            int get = 0;
            String tmp= "";

            Intent get_intent = getIntent();
            get = get_intent.getIntExtra("page",0);


            //when the previous page is the AddStudent
            if (get == 1) {
                    Intent intent = new Intent(SelectProfile.this, AddStudent.class);
                    tmp = getResources().getResourceEntryName(v.getId());
                    intent.putExtra("value", tmp);
                    startActivity(intent);
            }

            //when the previous page is the EditStudent
            else if (get == 2){
                    key = get_intent.getIntExtra("key", 0);
                    Intent intent = new Intent(SelectProfile.this, EditStudent.class);
                    tmp = getResources().getResourceEntryName(v.getId());
                    intent.putExtra("value", tmp);
                    intent.putExtra("key",key);
                    startActivity(intent);
            }
        }
    }

    class AddPic implements View.OnClickListener{
        public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE);
        }
    }

}
