import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by andy on 3/8/2016.
 */
public class addStudent extends ActionBarActivity {

    EditText student_name;
    dbhandler db;
    String class_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_main);

        db = new dbhandler(this, null, null);

        student_name = (EditText)findViewById(R.id.student_name);

        class_name  = ClassView.class_name;
    }
    public void addStudent(View view) {
        String name = student_name.getText().toString();

        name = name.trim().replaceAll("\\s", "");

        if(name == null || name.equals("") || name.isEmpty()) {
            student_name.setText("");
            Toast.makeText(getApplicationContext(), "Student name should not be empty", Toast.LENGTH_LONG).show();
            return;
        }
        Student student = new Student();

        student.setcName(class_name);
        student.setName(name);

        if(!db.alreadyExistInClass(class_name, name)) {
            db.addStudent(student);

            Intent intent = new Intent(this, ClassView.class);

            intent.putExtra(MainActivity.EXTRA_CLASS_NAME, class_name);

            startActivity(intent);
        }else {
            student_name.setText("");
            Toast.makeText(getApplicationContext(), "Student by the name \"" + name + "\" already exist", Toast.LENGTH_LONG).show();
        }
    }
}
