package com.nexzenstudent;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;

import adapter.TopstudentAdapter;

public class TopStudentActivity extends CommonAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listview = (ListView)findViewById(R.id.listview);
        TopstudentAdapter adapter = new TopstudentAdapter(this, new JSONArray());
        listview.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopStudentActivity.this, EnquiryActivity.class);
                startActivity(intent);
            }
        });
    }

}
