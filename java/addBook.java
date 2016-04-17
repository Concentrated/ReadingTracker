import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by andy on 3/11/2016.
 */
public class addBook extends ActionBarActivity {

    private EditText bookField;
    private EditText authorField;
    private String owner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_book_main);

        bookField = (EditText)findViewById(R.id.book_name);
        authorField = (EditText)findViewById(R.id.author_name);

        owner = StudentView.student_name;
    }

    public void addBook(View view) {
        String name = bookField.getText().toString();
        String author = authorField.getText().toString();

        Book book = new Book(name, author, owner);

        dbhandler db = new dbhandler(this, null, null);

        db.addBook(book);

        Intent intent = new Intent(this, StudentView.class);

        intent.putExtra(ClassView.EXTRA_STUDENT_NAME, StudentView.student_name);
        intent.putExtra(ClassView.EXTRA_STUDENT_ID, StudentView.student_id);

        startActivity(intent);
    }
}
