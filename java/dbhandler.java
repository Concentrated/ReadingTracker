import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andy on 1/31/2016.
 */
public class dbhandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "gradetracker.db";
    private static final String TABLE_CLASS = "classes";
    private static final String CLASS_ID = "_cid";
    private static final String CLASS = "class";
    private static final String TABLE_STUDENT = "students";
    private static final String STUDENT_ID = "_sid";
    private static final String STUDENT_READINGLEVEL = "readinglevel";
    private static final String STUDENT = "student";
    private static final String TABLE_BOOK = "books";
    private static final String BOOK_ID = "_bid";
    private static final String BOOK_AUTHOR = "author";
    private static final String BOOK = "book";
    private static final String TABLE_READINGLOG = "readinglog";
    private static final String READINGLOG_ID = "_rid";
    private static final String READINGLOG_READED = "readed";
    private static final String READINGLOG_DATE = "date";

    public dbhandler(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " +
                TABLE_CLASS + "("
                + CLASS_ID + " INTEGER PRIMARY KEY," + CLASS
                + " TEXT" + ")";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS " +
                TABLE_STUDENT + "("
                + STUDENT_ID + " INTEGER PRIMARY KEY,"
                + CLASS_ID + " INTEGER," + STUDENT
                + " TEXT," + STUDENT_READINGLEVEL + " TEXT" + ")";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS " +
                TABLE_BOOK + "("
                + BOOK_ID + " INTEGER PRIMARY KEY,"
                + STUDENT_ID + " INTEGER,"
                + BOOK + " TEXT,"
                + BOOK_AUTHOR + " TEXT" + ")";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS " +
                TABLE_READINGLOG + "("
                + READINGLOG_ID + " INTEGER PRIMARY KEY,"
                + STUDENT_ID + " INTEGER,"
                + BOOK_ID + " INTEGER,"
                + READINGLOG_READED + " TEXT,"
                + READINGLOG_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_READINGLOG);
        onCreate(db);
    }
    public boolean alreadyExist(String name) {
        String query = "SELECT * FROM " + TABLE_CLASS
                + " WHERE " + CLASS + " = \"" + name + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return (cursor != null && cursor.getCount() > 0); //if the cursor is not null and there is at least on row
    }
    public boolean alreadyExistInClass(String cName, String sName) {
        String query = "SELECT * FROM " + TABLE_CLASS
                + " WHERE " + CLASS + " = \"" + cName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query, null);

        cursor1.moveToFirst();

        int cid = cursor1.getInt(0);
        cursor1.close();

        query = "SELECT * FROM " + TABLE_STUDENT
                + " WHERE " + CLASS_ID + " = \"" + cid + "\""
                + " AND " + STUDENT + " = \"" + sName + "\"";
        Cursor cursor2 = db.rawQuery(query, null);

        return (cursor2 != null && cursor2.getCount() > 0);
    }
    public void addClass(String grade) {
        ContentValues values = new ContentValues();
        values.put(CLASS, grade);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CLASS, null, values);
        db.close();
    }
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;

        String query = "SELECT * FROM " + TABLE_CLASS + " WHERE " + CLASS + " = \"" + student.getcName() + "\"";

        cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        ContentValues values = new ContentValues();

        values.put(STUDENT, student.getName());
        values.put(CLASS_ID, cursor.getInt(0));
        values.put(STUDENT_READINGLEVEL, "");

        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }
    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;

        String query = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + STUDENT + " = \"" + book.getOwner() + "\"";

        cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        ContentValues values = new ContentValues();

        values.put(STUDENT_ID, cursor.getInt(0));
        values.put(BOOK, book.getName());
        values.put(BOOK_AUTHOR, book.getAuthor());

        cursor.close();

        db.insert(TABLE_BOOK, null, values);
        db.close();
    }
    public void addReadingLog(String page) {

    }
    public void deleteClass(Student student) {

    }
    //Student need id
    public boolean deleteStudent(Student student) {

        String query = STUDENT_ID + " = " + student.getId();
        SQLiteDatabase db = this.getWritableDatabase();

        if(db.delete(TABLE_STUDENT, query, null) > 0) {
            return true;
        } else {
            return false;
        }
    }
    //querying with the actual student id as parameter
    public String getReadingLevel(String id) {
        String query = "SELECT * FROM " + TABLE_STUDENT
                        + " WHERE " + STUDENT_ID + " = \"" + id + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        return cursor.getString(3).equals("")?"Unknown":cursor.getString(3);
    }
    public ArrayList<String> getGradeList() {
        ArrayList<String> grades = null;

        String query = "SELECT * FROM " + TABLE_CLASS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            grades = new ArrayList<>();
            grades.add("");
            while(!cursor.isAfterLast()) {
                grades.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        return grades;
    }

    /**
     *
     * @param cName
     * @return Malfunctioning, need to fix.
     */
    public ArrayList<Student> getStudents(String cName) {
        int counter = 1;

        Student student;

        ArrayList<Student> students = new ArrayList<>();

        student = new Student("_ID", "NAME");

        students.add(student);

        String query = "SELECT * FROM " + TABLE_CLASS + " WHERE " + CLASS + "=\"" + cName + "\"";

        SQLiteDatabase db  = this.getWritableDatabase();

        Cursor cursor1 = db.rawQuery(query, null);

        System.out.println("Step 1 Completed");

        cursor1.moveToFirst();
        if(cursor1.moveToFirst()) {
            query = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + CLASS_ID + "=\"" + cursor1.getInt(0) + "\"";

            System.out.println("Step 2 Completed");

            cursor1.close();

            Cursor cursor2 = db.rawQuery(query, null);

            System.out.println("Step 3 Completed");

            cursor2.moveToFirst();

            if (cursor2.moveToFirst()) {
                while (!cursor2.isAfterLast()) {
                    student = new Student(""+cursor2.getInt(0), cursor2.getString(2));
                    students.add(student);
                    cursor2.moveToNext();
                }
            }
        System.out.println("Finished");
        cursor2.close();
        }
        cursor1.close();
        db.close();
        return students;
    }
    public ArrayList<Book> getBooks(Student student) {

        Book book;

        ArrayList<Book> books = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_BOOK + " WHERE " + STUDENT_ID + " = \"" + student.getId() + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                book = new Book(cursor.getString(2), cursor.getString(3), student.getName());
                books.add(book);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        return books;
    }
}
