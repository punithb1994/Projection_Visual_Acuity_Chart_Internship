package eyecare.visint.pac.pac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class DBHelper extends SQLiteOpenHelper
{
    private static final String LOGCAT = null;
    /* Static Variables*/
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "patientdetailspac";
    private static final String TABLE_USER = "patientdetails_addpac";
    private static final String KEY_ID = "patient_id";
    private static final String KEY_FNAME = "firstname";
    private static final String KEY_LNAME = "lastname";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DOB = "dob";
    private static final String KEY_MOB = "mobilenumber";
    private static final String KEY_ADD = "address";
    private static final String KEY_CONSULTANT = "consultant";
    // result table
    private static final String TABLE_USER_RESULTS = "patient_resultpac";
    private static final String KEY_ID_RESULTS = "patient_id"; //Foreign key
    private static final String KEY_DATE = "date";
    private static final String KEY_TYPE = "type";
    private static final String KEY_RIGHT_EYE = "righteye";
    private static final String KEY_LEFT_VALUE = "lefteye";

    public static String CREATE_USER_TABLE1 = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + KEY_ID + " VARCHAR PRIMARY KEY, "
            + KEY_FNAME + " VARCHAR, "
            + KEY_LNAME + " VARCHAR, "
            + KEY_GENDER + " VARCHAR, "
            + KEY_DOB + " VARCHAR, "
            + KEY_MOB + " VARCHAR, "
            + KEY_ADD + " VARCHAR, "
            + KEY_CONSULTANT + " VARCHAR " + ")";

    public static String CREATE_USER_TABLE2 = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_RESULTS + "("
            + KEY_ID_RESULTS + " VARCHAR, "
            + KEY_DATE + " VARCHAR, "
            + KEY_TYPE + " VARCHAR, "
            + KEY_RIGHT_EYE + " DOUBLE, "
            + KEY_LEFT_VALUE + " DOUBLE " + ")";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOGCAT, "database Created");
        //deleting database from mobile
        // context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL(CREATE_USER_TABLE1);
            Log.d(LOGCAT, "Table 1 Created");
            db.execSQL(CREATE_USER_TABLE2);
            Log.d(LOGCAT, "Table2 Created");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version_old, int current_version)
    {
        try
        {
            String query1, query2;
            query1 = "DROP TABLE IF EXISTS " + TABLE_USER;
            query2 = "DROP TABLE IF EXISTS " + TABLE_USER_RESULTS;
            db.execSQL(query1);
            db.execSQL(query2);
            onCreate(db);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insertpatientdetails(User user) /*Function to insert patient details to database*/
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getId());
        values.put(KEY_FNAME, user.getFname());
        values.put(KEY_LNAME, user.getLname());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_DOB, user.getDob());
        values.put(KEY_MOB, user.getMobile());
        values.put(KEY_ADD, user.getAddress());
        values.put(KEY_CONSULTANT, user.getConsultant());
        database.insert(TABLE_USER, null, values);
    }

    // Result table insertion
    public void insertpatientresults(String patient_id, String date, String type, Double righteye, Double lefteye) /*Function to insert patient details to database*/
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_RESULTS, patient_id);
        values.put(KEY_DATE, date);
        values.put(KEY_TYPE, type);
        values.put(KEY_RIGHT_EYE, righteye);
        values.put(KEY_LEFT_VALUE, lefteye);
        database.insert(TABLE_USER_RESULTS, null, values);
    }

    public void updatePatient(String patient_id, String firstname, String lastname, String gender, String dob, String mobilenumber, String address, String consultant)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, patient_id);
        values.put(KEY_FNAME, firstname);
        values.put(KEY_LNAME, lastname);
        values.put(KEY_GENDER, gender);
        values.put(KEY_DOB, dob);
        values.put(KEY_MOB, mobilenumber);
        values.put(KEY_ADD, address);
        values.put(KEY_CONSULTANT, consultant);
        database.update(TABLE_USER, values, "patient_id='" + patient_id + "'", null);   /*Function to update patient details*/
    }

    // Result Update
    public void updatepatientresults(String patient_id, String type, Double righteye, Double lefteye) /*Function to update patient results to database*/
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_RESULTS, patient_id);
        //values.put(KEY_DATE, date);
        values.put(KEY_TYPE, type);
        values.put(KEY_RIGHT_EYE, righteye);
        values.put(KEY_LEFT_VALUE, lefteye);
        database.update(TABLE_USER_RESULTS, values, "patient_id='" + patient_id + "' and type='" + type + "'", null);
    }

    public void newupdatePatient(String oldpatient_id, String current, String firstname, String lastname, String gender, String dob, String mobilenumber, String address, String consultant)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, current);
        values.put(KEY_FNAME, firstname);
        values.put(KEY_LNAME, lastname);
        values.put(KEY_GENDER, gender);
        values.put(KEY_DOB, dob);
        values.put(KEY_MOB, mobilenumber);
        values.put(KEY_ADD, address);
        values.put(KEY_CONSULTANT, consultant);
        database.update(TABLE_USER, values, "patient_id='" + oldpatient_id + "'", null);    /*Function to update patient while changing patient id*/
    }

    public Cursor getpatients()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        return database.rawQuery(selectQuery, null);
    }

    public Cursor getpatientonebyon(String patient_id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + "  where patient_id='" + patient_id + "'";
        return database.rawQuery(selectQuery, null);
    }

    public Cursor getresultonebyon(String patient_id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER_RESULTS + " where patient_id='" + patient_id + "'";
        return database.rawQuery(selectQuery, null);
    }

    public Cursor getresult(String patient_id, String type, String date)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER_RESULTS + " where patient_id='" + patient_id + "' and type='" + type + "' and date='" + date + "'";
        return database.rawQuery(selectQuery, null);
    }

    public void deleteAllpatients()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_USER, null, null);
    }

    /*getAllUsers() will return the list of all users*/
    public ArrayList<User> getAllUsers()
    {
        ArrayList<User> usersList = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        /* looping through all rows and adding to list*/
        if (cursor.moveToFirst())
        {
            do
            {
                User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                usersList.add(user);
            }
            while (cursor.moveToNext());
        }
        return usersList;
    }

    /*getUsersCount() will give the total number of records in the table*/
    public int getUsersCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}