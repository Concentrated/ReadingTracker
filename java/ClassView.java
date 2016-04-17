package com.example.andy.readingtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by andy on 2/2/2016.
 */
public class ClassView extends ActionBarActivity {


    public final static String EXTRA_STUDENT_ID = "com.example.andy.readingtracker.STUDENT_NAME";
    public final static String EXTRA_STUDENT_NAME = "com.example.andy.readingtracker.STUDENT_ID";

    static String class_name;

    private ListView student_list;
    private dbhandler db;
    private ArrayList<HashMap<String, String>> list;
    private ArrayList<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_main);

        db = new dbhandler(this, null, null);

        Intent intent = getIntent();

        class_name = intent.getStringExtra(MainActivity.EXTRA_CLASS_NAME);

        student_list = (ListView)findViewById(R.id.listView);

        list = new ArrayList<HashMap<String, String>>();

        students = db.getStudents(class_name);

        HashMap<String, String> student;

        for(Student e : students) {
            student = new HashMap<>();
            student.put("_id", e.getId());
            student.put("name", e.getName());
            list.add(student);
        }
        //student.put("_id", "1");
        //student.put("name", "Ndy");
        /*
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("_id", "ID");
        header.put("name", "NAME");

        list.add(header);
        */
        StudentViewAdapter adapter = new StudentViewAdapter(this, list);
        student_list.setAdapter(adapter);
        //Student list listener
        student_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)             {
                //Change code below to add features when the student is clicked
                int pos = position + 1;
                HashMap<String, String> map = (HashMap)parent.getItemAtPosition(position);

                Toast.makeText(ClassView.this,map.get("name") +" Clicked", Toast.LENGTH_SHORT).show();

                startStudentView(map.get("_id"), map.get("name"));
            }
        });
    }
    //Called this function when New Student is clicked
    public void studentAct(View view) {
        Intent intent = new Intent(this, addStudent.class);

        startActivity(intent);
    }
    //Called this function when switching to StudentView activity
    public void startStudentView(String id, String name) {
        Intent intent = new Intent(this, StudentView.class);

        intent.putExtra(EXTRA_STUDENT_ID, id);
        intent.putExtra(EXTRA_STUDENT_NAME, name);

        startActivity(intent);
    }
}


class StudentViewAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView id;
    TextView name;
    public StudentViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = activity.getLayoutInflater();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.student_list, null);

            id = (TextView)convertView.findViewById(R.id.student_id);
            name = (TextView)convertView.findViewById(R.id.name);
        }

        HashMap<String, String> map = list.get(position);
        id.setText(map.get("_id"));
        name.setText(map.get("name"));

        return convertView;
    }
}
