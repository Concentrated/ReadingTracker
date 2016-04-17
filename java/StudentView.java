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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by andy on 3/10/2016.
 */
public class StudentView extends ActionBarActivity {


    static String student_id;
    static String student_name;
    private ListView book_list;
    private TextView nameView;
    private TextView classView;
    private TextView readingLevelView;
    private TextView bookCount;
    private dbhandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);

        db = new dbhandler(this, null, null);

        Intent intent = getIntent();

        nameView = (TextView)findViewById(R.id.nameView);
        classView = (TextView)findViewById(R.id.classView);
        readingLevelView = (TextView)findViewById(R.id.rLevelView);
        bookCount = (TextView)findViewById(R.id.bookCount);

        student_id = getIntent().getStringExtra(ClassView.EXTRA_STUDENT_ID);
        student_name = getIntent().getStringExtra(ClassView.EXTRA_STUDENT_NAME);

        nameView.setText(student_name);
        classView.setText(ClassView.class_name);
        readingLevelView.setText(db.getReadingLevel(student_id));


        //Book list config
        Student student = new Student(student_id, student_name);
        ArrayList<HashMap<String, String>> list;
        ArrayList<Book> books;

        book_list = (ListView)findViewById(R.id.listView);

        list = new ArrayList<HashMap<String, String>>();

        books = db.getBooks(student);

        bookCount.setText("Numbers of Books "+books.size());

        HashMap<String, String> book;

        for(Book b : books) {
            book = new HashMap<>();
            book.put("name", b.getName());
            book.put("author", b.getAuthor());
            list.add(book);
        }

        BookViewAdapter adapter = new BookViewAdapter(this, list);

        book_list.setAdapter(adapter);

        book_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                //Change code below to add features when the student is clicked
                int pos = position + 1;
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);

                Toast.makeText(StudentView.this, map.get("name") + " Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteStudent(View view) {
        Student student = new Student(student_id);
        db.deleteStudent(student);

        Intent intent = new Intent(this, ClassView.class);

        startActivity(intent);
    }

    public void bookAct(View view) {
        Intent intent = new Intent(this, addBook.class);

        intent.putExtra(ClassView.EXTRA_STUDENT_NAME, student_name);

        startActivity(intent);
    }
}

class BookViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView author;
    TextView name;
    public BookViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.book_list, null);

            name = (TextView)convertView.findViewById(R.id.book);
            author = (TextView)convertView.findViewById(R.id.author);
        }

        HashMap<String, String> map = list.get(position);

        name.setText(map.get("name"));
        author.setText("by "+map.get("author"));

        return convertView;
    }
}