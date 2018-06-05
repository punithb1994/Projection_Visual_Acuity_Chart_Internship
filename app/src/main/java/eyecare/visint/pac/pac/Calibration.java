package eyecare.visint.pac.pac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Calibration extends AppCompatActivity
{
    private Toolbar toolbar;
    private Spinner spinner1;
    private Button btncalibrate, btnsave;
    private EditText height, width, screen_size;
    private int val;
    private String ht, wt, screen;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibration);
        setTitle("Calibration");
        btncalibrate = (Button) findViewById(R.id.bcalibrate);
        btnsave = (Button) findViewById(R.id.bsave);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        height = (EditText) findViewById(R.id.Height);
        width = (EditText) findViewById(R.id.Width);
        screen_size = (EditText) findViewById(R.id.ms);
        final SharedPreferences sharedPref = getSharedPreferences("MY_PREFS", 0);

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

        int s = sharedPref.getInt("stack", -1);
        spinner1.setSelection(s);
        final SharedPreferences settingsPrefs = getSharedPreferences("MY_PREFS", 0);

        //********Retriving height width and screen size from calibrtion*********
        String h, w, size;
        h = settingsPrefs.getString("sharededittextht", "");
        w = settingsPrefs.getString("sharededittextwt", "");
        size = settingsPrefs.getString("sharededittextss", "");
        height.setText(h);
        width.setText(w);
        screen_size.setText(size);

        btncalibrate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Calibrate.class);
                startActivity(intent);
                finish();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final SharedPreferences settingsPrefs = getSharedPreferences("MY_PREFS", 0);
                final SharedPreferences.Editor editor = settingsPrefs.edit();
                ht = height.getText().toString();
                editor.putString("sharededittextht", ht);
                wt = width.getText().toString();
                editor.putString("sharededittextwt", wt);
                screen = screen_size.getText().toString();
                editor.putString("sharededittextss", screen);
                editor.commit();
                final SharedPreferences sharedPref = getSharedPreferences("MY_PREFS", 0);
                final SharedPreferences.Editor edit = sharedPref.edit();
                val = spinner1.getSelectedItemPosition();
                edit.putInt("stack", val);
                edit.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}