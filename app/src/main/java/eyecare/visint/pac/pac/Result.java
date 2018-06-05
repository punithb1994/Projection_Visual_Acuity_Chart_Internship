package eyecare.visint.pac.pac;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Result extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextView jdate, jtype, jreye, jleye;
    private DBHelper db;
    // ArrayList<String> Liste = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String date, type;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle("Result");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        // fetch value from key-value pair and make it visible on TextView.
        type = intent.getStringExtra("item");
        date = intent.getStringExtra("itemdate");

        jdate = (TextView) findViewById(R.id.tvdate);
        jtype = (TextView) findViewById(R.id.tvtype);
        jreye = (TextView) findViewById(R.id.tvright);
        jleye = (TextView) findViewById(R.id.tvleft);

        db = new DBHelper(getApplicationContext());
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String id = sharedPref.getString("sharedPID", "");
        // Liste = new ArrayList<String>();
        //  Cursor c = db.getresultonebyon(id);
        Cursor c = db.getresult(id, type, date);

        while (c.moveToNext())
        {
            // Liste.add(c.getString(1));
            //adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_item, Liste);
            jdate.setText(c.getString(1));
            jtype.setText(c.getString(2));
            jreye.setText(c.getString(3));
            jleye.setText(c.getString(4));
        }
    }
}