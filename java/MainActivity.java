import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    private dbhandler db;
    private Spinner list;
    public final static String EXTRA_CLASS_NAME = "com.example.andy.readingtracker.CLASS_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (Spinner)findViewById(R.id.spinner);
        list.setOnItemSelectedListener(this);

        db = new dbhandler(this, null, null);

        List<String> grade = new ArrayList<>();

        if(db.getGradeList() == null) {
            grade.add("No Class");
        } else {
            grade = db.getGradeList();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, grade);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        list.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String cName = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        //check if empty class or No Class is selected
        if(cName != "" && cName != "No Class") {

            list.setSelection(0); //reset spinner selection

            Intent intent = new Intent(this, ClassView.class);

            intent.putExtra(EXTRA_CLASS_NAME, cName);
            startActivity(intent);
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void classAct(View view) {
        Intent new_intent = new Intent(this, addClass.class);

        startActivity(new_intent);
    }
}
