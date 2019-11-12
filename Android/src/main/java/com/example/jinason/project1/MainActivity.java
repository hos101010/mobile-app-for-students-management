package com.example.jinason.project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity {

    private LinearLayout student_layout, todo_layout;
    private TextView tv_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_home = (TextView)findViewById(R.id.tv_home);
        student_layout = (LinearLayout)findViewById(R.id.student_home);
        student_layout.setVisibility(GONE);
        todo_layout = (LinearLayout)findViewById(R.id.todo_home);
        todo_layout.setVisibility(GONE);

        galleryAddPic();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.student_menu) {
            tv_home.setVisibility(GONE);
            student_layout.setVisibility(View.VISIBLE);
            todo_layout.setVisibility(GONE);
            studentMenu();
        }
        else if (id == R.id.todo_menu){
            tv_home.setVisibility(GONE);
            student_layout.setVisibility(GONE);
            todo_layout.setVisibility(View.VISIBLE);
            todoMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(Environment.getExternalStorageDirectory() + "/Pictures");
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    public void studentMenu(){
        findViewById(R.id.add_stu_button).setOnClickListener(new GotoAddStu());
        findViewById(R.id.edit_stu_button).setOnClickListener(new GotoEditStu());
    }

    public void todoMenu(){
        findViewById(R.id.add_todo).setOnClickListener(new GotoAddTodo());
        findViewById(R.id.edit_todo).setOnClickListener(new GotoEditTodo());
        findViewById(R.id.view_todo).setOnClickListener(new GotoViewTodo());
    }

    class GotoAddStu implements OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddStudent.class);
            startActivity(intent);
        }
    }

    class GotoEditStu implements OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, EditStudent.class);
            startActivity(intent);
        }
    }

    class GotoAddTodo implements OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddTodo.class);
            startActivity(intent);
        }
    }

    class GotoEditTodo implements OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, EditTodo.class);
            startActivity(intent);
        }
    }

    class GotoViewTodo implements OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ViewTodo.class);
            startActivity(intent);
        }
    }
}
