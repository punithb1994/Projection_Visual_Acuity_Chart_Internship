package eyecare.visint.pac.pac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Chart_Settings extends AppCompatActivity
{
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_settings);
        setTitle("Chart Settings");
        Toolbar toolbar;
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner6 = (Spinner) findViewById(R.id.spinner6);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });


        int sp1, sp2, sp3, sp4, sp5, sp6;
        SharedPreferences settingsPrefs = getSharedPreferences("MY_PREFS", 0);
        sp1 = settingsPrefs.getInt("sharedspinner1", -1);
        spinner1.setSelection(sp1);
        sp2 = settingsPrefs.getInt("sharedspinner2", -1);
        spinner2.setSelection(sp2);
        sp3 = settingsPrefs.getInt("sharedspinner3", -1);
        spinner3.setSelection(sp3);
        sp4 = settingsPrefs.getInt("sharedspinner4", -1);
        spinner4.setSelection(sp4);
        sp5 = settingsPrefs.getInt("sharedspinner5", -1);
        spinner5.setSelection(sp5);
        sp6 = settingsPrefs.getInt("sharedspinner6", -1);
        spinner6.setSelection(sp6);
    }

    public void onBackPressed()
    {
        int spr1, spr2, spr3, spr4, spr5, spr6;
        final SharedPreferences settingsPrefs = getSharedPreferences("MY_PREFS", 0);
        final SharedPreferences.Editor editor = settingsPrefs.edit();
        spr1 = spinner1.getSelectedItemPosition();
        editor.putInt("sharedspinner1", spr1);
        spr2 = spinner2.getSelectedItemPosition();
        editor.putInt("sharedspinner2", spr2);
        spr3 = spinner3.getSelectedItemPosition();
        editor.putInt("sharedspinner3", spr3);
        spr4 = spinner4.getSelectedItemPosition();
        editor.putInt("sharedspinner4", spr4);
        spr5 = spinner5.getSelectedItemPosition();
        editor.putInt("sharedspinner5", spr5);
        spr6 = spinner6.getSelectedItemPosition();
        editor.putInt("sharedspinner6", spr6);
        editor.commit();
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}