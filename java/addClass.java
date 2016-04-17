import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by andy on 2/2/2016.
 */
public class addClass extends ActionBarActivity {

    EditText class_name;
    dbhandler db;
    @Override
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_class_main);

        class_name = (EditText)findViewById(R.id.class_name);
        db = new dbhandler(this, null, null);
    }
    public void addClass(View view) {
        String cName = class_name.getText().toString();

        if(!db.alreadyExist(cName)) {
            db.addClass(cName);

            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra(MainActivity.EXTRA_CLASS_NAME, cName);
            startActivity(intent);
        } else {
            class_name.setText("");
            Toast.makeText(getApplicationContext(), "Class \"" + cName + "\" is already created", Toast.LENGTH_LONG).show();
        }
    }
}
