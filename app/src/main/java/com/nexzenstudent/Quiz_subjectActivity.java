package com.nexzenstudent;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;

import adapter.Quiz_subject_rv_adapter;
import util.DatabaseHandler;

public class Quiz_subjectActivity extends CommonAppCompatActivity {

    private ListView lv_subject;
    Quiz_subject_rv_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_subject);

        lv_subject = (ListView) findViewById(R.id.lv_quiz_subject);

        loadSubjectData();

        lv_subject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DatabaseHandler db = new DatabaseHandler(Quiz_subjectActivity.this);
                db.clearAns();

                try {
                    String id = adapter.getItem(i).getString("subject_id");
                    String title = adapter.getItem(i).getString("subject_title");
                    String time = adapter.getItem(i).getString("quiz_time");
                    String total_qus = adapter.getItem(i).getString("total_qes");
                    String total_right_ans = adapter.getItem(i).getString("quiz_total_right_ans");

                    if (total_right_ans.equals("null")) {
                        showMessage(total_qus, time, title, id);
                    } else {
                        Intent ii = new Intent(Quiz_subjectActivity.this, Quiz_resultActivity.class);
                        ii.putExtra("id", id);
                        ii.putExtra("title", title);
                        startActivity(ii);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void showMessage(final String total_qus, final String time, final String title, final String id) {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(Quiz_subjectActivity.this);
        myAlertDialog.setCancelable(false);

        myAlertDialog.setTitle(getResources().getString(R.string.are_you_sure));
        myAlertDialog.setMessage(getResources().getString(R.string.total_questions) + total_qus +
                "\n" + getResources().getString(R.string.exam_time) + time);
        myAlertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                Intent ii = new Intent(Quiz_subjectActivity.this, Quiz_answerActivity.class);
                ii.putExtra("title", title);
                ii.putExtra("time", time);
                ii.putExtra("total_qus", total_qus);
                ii.putExtra("id", id);
                startActivity(ii);
            }
        });

        myAlertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = myAlertDialog.create();
        if (!dialog.isShowing()) {
            dialog.show();
        }

    }

    private void loadSubjectData() {
        adapter = new Quiz_subject_rv_adapter(this, new JSONArray());
        lv_subject.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadSubjectData();
    }
}
