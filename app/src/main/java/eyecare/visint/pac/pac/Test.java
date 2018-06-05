package eyecare.visint.pac.pac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Test extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    private TextView jtv1, jtv2, jtv3, jtv4, jtv5, jtv6, jtv7, v1, v2, v3, v4, v5, v6, v7, jchar, jletter, pid, jpatient_id;
    private SeekBar jseekchar, jseekline;
    private RadioButton jrighteye, jlefteye;
    private Spinner jtype;
    private Double righteyevalue, lefteyevalue;
    private int progchar, progline, t = -1, reye = 0, leye = 0;
    private static String Allowed_Characters = "CDEFLNOPTZ";
    private DBHelper db;
    private FrameLayout main;
    private Button b, btnch;
    private Flag obj = new Flag();
    private int k;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.test);
        jpatient_id = (TextView) findViewById(R.id.TextviewPatientid);
        jtype = (Spinner) findViewById(R.id.spinner1);
        jtv1 = (TextView) findViewById(R.id.tv1);
        jtv2 = (TextView) findViewById(R.id.tv2);
        jtv3 = (TextView) findViewById(R.id.tv3);
        jtv4 = (TextView) findViewById(R.id.tv4);
        jtv5 = (TextView) findViewById(R.id.tv5);
        jtv6 = (TextView) findViewById(R.id.tv6);
        jtv7 = (TextView) findViewById(R.id.tv7);

        v1 = (TextView) findViewById(R.id.v1);
        v2 = (TextView) findViewById(R.id.v2);
        v3 = (TextView) findViewById(R.id.v3);
        v4 = (TextView) findViewById(R.id.v4);
        v5 = (TextView) findViewById(R.id.v5);
        v6 = (TextView) findViewById(R.id.v6);
        v7 = (TextView) findViewById(R.id.v7);

        v1.setVisibility(View.GONE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
        v4.setVisibility(View.GONE);
        v5.setVisibility(View.GONE);
        v6.setVisibility(View.GONE);
        v7.setVisibility(View.GONE);

        jrighteye = (RadioButton) findViewById(R.id.rdbrighteye);
        jlefteye = (RadioButton) findViewById(R.id.rdblefteye);

        jseekchar = (SeekBar) findViewById(R.id.sbchar);
        jletter = (TextView) findViewById(R.id.tvletter);
        jseekline = (SeekBar) findViewById(R.id.sbline);
        jchar = (TextView) findViewById(R.id.tvcharacter);

        db = new DBHelper(this);  //Initializing Database
        jrighteye.setOnCheckedChangeListener(this);
        jlefteye.setOnCheckedChangeListener(this);
        jtv1.setOnClickListener(this);
        jtv2.setOnClickListener(this);
        jtv3.setOnClickListener(this);
        jtv4.setOnClickListener(this);
        jtv5.setOnClickListener(this);
        jtv6.setOnClickListener(this);
        jtv7.setOnClickListener(this);

        main = (FrameLayout) findViewById(R.id.frame2);
        b = (Button) findViewById(R.id.btnsave);
        btnch = (Button) main.findViewById(R.id.btnchoosepatient);
        main.setOnClickListener(null);
        main.setVisibility(View.VISIBLE);
        main.bringToFront();
        pid = (TextView) findViewById(R.id.TextviewPatientid);
        k = obj.returnflag();
        String h = obj.returnpid();
        if (k == 1)
        {
            pid.setText(h);
        }

        SharedPreferences settingsPrefs = getSharedPreferences("MY_PREFS", 0);
        int sp1 = settingsPrefs.getInt("sharedspinner1", -1);//Chart setting Acuity Presentation
        int sp2 = settingsPrefs.getInt("sharedspinner2", -1);//Chart setting Snellan format
        int sp3 = settingsPrefs.getInt("sharedspinner3", -1);//Chart setting Chart Letter Set
        int sp4 = settingsPrefs.getInt("sharedspinner4", -1);//Chart setting Default Chart Letters per Line
        int sp5 = settingsPrefs.getInt("sharedspinner5", -1);//Chart setting Default Chart Number of lines
        int sp6 = settingsPrefs.getInt("sharedspinner6", -1);//Chart setting Default Chart Letter Seperation

        //********Retrieving height width and screen size from calibration*********
        /*String ht,wt,ss;
        ht=settingsPrefs.getString("sharededittextht", "NA");
        wt=settingsPrefs.getString("sharededittextwt", "NA");
        ss=settingsPrefs.getString("sharededittextss", "NA");
        Toast.makeText(getApplicationContext(),ht+wt+ss,Toast.LENGTH_SHORT).show();*/

        switch (sp3)
        {
            case 0:
                Allowed_Characters = "CDEFLNOPTZ";
                Typeface typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/snellen.ttf");
                alltextfont(typeFace1);
                break;
            case 1:
                Allowed_Characters = "CDRNSZEPXAT";
                Typeface typeFace2 = Typeface.createFromAsset(getAssets(), "fonts/sloan.ttf");
                alltextfont(typeFace2);
                break;
            case 2:
                Allowed_Characters = "C";
                Typeface typeFace3 = Typeface.createFromAsset(getAssets(), "fonts/sloan.ttf");
                alltextfont(typeFace3);
                break;
            case 3:
                Allowed_Characters = "EM3W";
                Typeface typeFace4 = Typeface.createFromAsset(getAssets(), "fonts/tumblinge.ttf");
                alltextfont(typeFace4);
                break;
        }

        switch (sp4)
        {

            case 4:
                progchar = 1;
                break;
            case 3:
                progchar = 2;
                break;
            case 2:
                progchar = 3;
                break;
            case 1:
                progchar = 4;
                break;
            case 0:
                progchar = 5;
                break;
        }

        switch (sp5)
        {
            case 6:
                progline = 1;
                break;
            case 5:
                progline = 2;
                break;
            case 4:
                progline = 3;
                break;
            case 3:
                progline = 4;
                break;
            case 2:
                progline = 5;
                break;
            case 1:
                progline = 6;
                break;
            case 0:
                progline = 7;
                break;
        }
        final SharedPreferences sharedPref = getSharedPreferences("MY_PREFS", 0);
        final int s = sharedPref.getInt("stack", -1);
        switch (s)
        {
            case 0:
                t = 4;
                break;
            case 1:
                t = 7;
                break;
            case 2:
                t = 10;
                break;
            case 3:
                t = 15;
                break;
            case 4:
                t = 20;
                break;
        }

        switch (t)
        {
            case 4:
                textsize(9);
                v1.setText("0.100");
                v2.setText("0.125");
                v3.setText("0.167");
                v4.setText("0.250");
                v5.setText("0.333");
                v6.setText("0.500");
                v7.setText("1.000");
                break;
            case 7:
                textsize(15);
                v1.setText("0.100");
                v2.setText("0.125");
                v3.setText("0.167");
                v4.setText("0.250");
                v5.setText("0.333");
                v6.setText("0.500");
                v7.setText("1.000");
                break;
            case 10:
                textsize(21);
                v1.setText("0.100");
                v2.setText("0.125");
                v3.setText("0.167");
                v4.setText("0.250");
                v5.setText("0.333");
                v6.setText("0.500");
                v7.setText("1.000");
                break;
            case 15:
                textsize(33);
                v1.setText("0.100");
                v2.setText("0.125");
                v3.setText("0.167");
                v4.setText("0.250");
                v5.setText("0.333");
                v6.setText("0.500");
                v7.setText("1.000");
                break;
            case 20:
                textsize(43);
                v1.setText("0.100");
                v2.setText("0.125");
                v3.setText("0.167");
                v4.setText("0.250");
                v5.setText("0.333");
                v6.setText("0.500");
                v7.setText("1.000");
                break;
        }

        //jtv1.setText(getRandomString(progchar, progline));
        randomtext(progchar, 1);
        jseekchar.setProgress(progchar);
        jseekline.setProgress(progline);
        switch (progline)
        {
            case 1:
                v4.setVisibility(View.VISIBLE);
                break;
            case 2:
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
                break;
            case 3:
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
                v5.setVisibility(View.VISIBLE);
                break;
            case 4:
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
                v5.setVisibility(View.VISIBLE);
                v6.setVisibility(View.VISIBLE);
                break;
            case 5:
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
                v5.setVisibility(View.VISIBLE);
                v6.setVisibility(View.VISIBLE);
                break;
            case 6:
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
                v5.setVisibility(View.VISIBLE);
                v6.setVisibility(View.VISIBLE);
                v7.setVisibility(View.VISIBLE);
                break;
            case 7:
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
                v5.setVisibility(View.VISIBLE);
                v6.setVisibility(View.VISIBLE);
                v7.setVisibility(View.VISIBLE);
                break;
        }
        jseekchar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                jchar.setVisibility(View.VISIBLE);
                progchar = progress;
                jchar.setText("Letters " + progchar);
                switch (progchar) {
                    case 0:
                        randomtext(0, 0);
                        break;
                    case 1:
                        randomtext(progchar, 1);
                        break;
                    case 2:
                        randomtext(progchar, 1);
                        break;
                    case 3:
                        randomtext(progchar, 1);
                        break;
                    case 4:
                        randomtext(progchar, 1);
                        break;
                    case 5:
                        randomtext(progchar, 1);
                        break;
                    case 6:
                        randomtext(progchar, 1);
                        break;
                    case 7:
                        randomtext(progchar, 1);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                jchar.setVisibility(View.INVISIBLE);
            }
        });
        jseekline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                jletter.setVisibility(View.VISIBLE);
                progline = progress;
                jletter.setText("Lines " + progline);
                switch (progline)
                {
                    case 0:
                        //if (progchar != 0)
                        //{
                            jtv1.setVisibility(View.GONE);
                            jtv2.setVisibility(View.GONE);
                            jtv3.setVisibility(View.GONE);
                            jtv4.setVisibility(View.GONE);
                            jtv5.setVisibility(View.GONE);
                            jtv6.setVisibility(View.GONE);
                            jtv7.setVisibility(View.GONE);
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.GONE);
                            v3.setVisibility(View.GONE);
                            v4.setVisibility(View.GONE);
                            v5.setVisibility(View.GONE);
                            v6.setVisibility(View.GONE);
                            v7.setVisibility(View.GONE);
                       // }
                        randomtext(0, 0);
                        break;
                    case 1:
                        //if (progchar != 0)
                        //{
                            jtv1.setVisibility(View.GONE);
                            jtv2.setVisibility(View.GONE);
                            jtv3.setVisibility(View.GONE);
                            jtv4.setVisibility(View.VISIBLE);
                            jtv5.setVisibility(View.GONE);
                            jtv6.setVisibility(View.GONE);
                            jtv7.setVisibility(View.GONE);
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.GONE);
                            v3.setVisibility(View.GONE);
                            v4.setVisibility(View.VISIBLE);
                            v5.setVisibility(View.GONE);
                            v6.setVisibility(View.GONE);
                            v7.setVisibility(View.GONE);
                        //}
                        randomtext(progchar, 1);
                        break;
                    case 2:
                       // if (progchar != 0)
                       // {
                            jtv1.setVisibility(View.GONE);
                            jtv2.setVisibility(View.GONE);
                            jtv3.setVisibility(View.VISIBLE);
                            jtv4.setVisibility(View.VISIBLE);
                            jtv5.setVisibility(View.GONE);
                            jtv6.setVisibility(View.GONE);
                            jtv7.setVisibility(View.GONE);
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.GONE);
                            v3.setVisibility(View.VISIBLE);
                            v4.setVisibility(View.VISIBLE);
                            v5.setVisibility(View.GONE);
                            v6.setVisibility(View.GONE);
                            v7.setVisibility(View.GONE);
                       // }
                        randomtext(progchar, 1);
                        break;
                    case 3:
                       // if (progchar != 0)
                       // {
                            jtv1.setVisibility(View.GONE);
                            jtv2.setVisibility(View.GONE);
                            jtv3.setVisibility(View.VISIBLE);
                            jtv4.setVisibility(View.VISIBLE);
                            jtv5.setVisibility(View.VISIBLE);
                            jtv6.setVisibility(View.GONE);
                            jtv7.setVisibility(View.GONE);
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.GONE);
                            v3.setVisibility(View.VISIBLE);
                            v4.setVisibility(View.VISIBLE);
                            v5.setVisibility(View.VISIBLE);
                            v6.setVisibility(View.GONE);
                            v7.setVisibility(View.GONE);
                        //}
                        randomtext(progchar, 1);
                        break;
                    case 4:
                       // if (progchar != 0)
                       // {
                            jtv1.setVisibility(View.GONE);
                            jtv2.setVisibility(View.GONE);
                            jtv3.setVisibility(View.VISIBLE);
                            jtv4.setVisibility(View.VISIBLE);
                            jtv5.setVisibility(View.VISIBLE);
                            jtv6.setVisibility(View.VISIBLE);
                            jtv7.setVisibility(View.GONE);
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.GONE);
                            v3.setVisibility(View.VISIBLE);
                            v4.setVisibility(View.VISIBLE);
                            v5.setVisibility(View.VISIBLE);
                            v6.setVisibility(View.VISIBLE);
                            v7.setVisibility(View.GONE);
                      //  }
                        randomtext(progchar, 1);
                        break;
                    case 5:
                        if (progchar != 0)
                       // {
                            jtv1.setVisibility(View.GONE);
                            jtv2.setVisibility(View.VISIBLE);
                            jtv3.setVisibility(View.VISIBLE);
                            jtv4.setVisibility(View.VISIBLE);
                            jtv5.setVisibility(View.VISIBLE);
                            jtv6.setVisibility(View.VISIBLE);
                            jtv7.setVisibility(View.GONE);
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.VISIBLE);
                            v3.setVisibility(View.VISIBLE);
                            v4.setVisibility(View.VISIBLE);
                            v5.setVisibility(View.VISIBLE);
                            v6.setVisibility(View.VISIBLE);
                            v7.setVisibility(View.GONE);
                        //}
                        randomtext(progchar, 1);
                        break;
                    case 6:
                       // if (progchar != 0)
                       // {
                            jtv1.setVisibility(View.GONE);
                            jtv2.setVisibility(View.VISIBLE);
                            jtv3.setVisibility(View.VISIBLE);
                            jtv4.setVisibility(View.VISIBLE);
                            jtv5.setVisibility(View.VISIBLE);
                            jtv6.setVisibility(View.VISIBLE);
                            jtv7.setVisibility(View.VISIBLE);
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.VISIBLE);
                            v3.setVisibility(View.VISIBLE);
                            v4.setVisibility(View.VISIBLE);
                            v5.setVisibility(View.VISIBLE);
                            v6.setVisibility(View.VISIBLE);
                            v7.setVisibility(View.VISIBLE);
                       // }
                        randomtext(progchar, 1);
                        break;
                    case 7:
                       // if (progchar != 0)
                       // {
                            jtv1.setVisibility(View.VISIBLE);
                            jtv2.setVisibility(View.VISIBLE);
                            jtv3.setVisibility(View.VISIBLE);
                            jtv4.setVisibility(View.VISIBLE);
                            jtv5.setVisibility(View.VISIBLE);
                            jtv6.setVisibility(View.VISIBLE);
                            jtv7.setVisibility(View.VISIBLE);
                            v1.setVisibility(View.VISIBLE);
                            v2.setVisibility(View.VISIBLE);
                            v3.setVisibility(View.VISIBLE);
                            v4.setVisibility(View.VISIBLE);
                            v5.setVisibility(View.VISIBLE);
                            v6.setVisibility(View.VISIBLE);
                            v7.setVisibility(View.VISIBLE);
                       // }
                        randomtext(progchar, 1);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                jletter.setVisibility(View.INVISIBLE);
            }
        });

        jtv1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String patient_id = jpatient_id.getText().toString();
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c.getTime());
                String type = jtype.getSelectedItem().toString();
                String s1 = v1.getText().toString();
                if (reye == 1)
                {
                    righteyevalue = Double.parseDouble(s1);
                } else if (leye == 1)
                {
                    lefteyevalue = Double.parseDouble(s1);
                }
                Cursor item = db.getresult(patient_id, type, date);
                item.moveToFirst();
                if (item.getCount() > 0)
                {
                    Double r = item.getDouble(3);
                    Double l = item.getDouble(4);
                    if (r > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                    else if (r > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                } else
                {
                    db.insertpatientresults(patient_id, date, type, righteyevalue, lefteyevalue);
                    inserted();
                }
            }
        });

        jtv2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String patient_id = jpatient_id.getText().toString();
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c.getTime());
                String type = jtype.getSelectedItem().toString();
                String s2 = v2.getText().toString();
                if (reye == 1)
                {
                    righteyevalue = Double.parseDouble(s2);
                } else if (leye == 1)
                {
                    lefteyevalue = Double.parseDouble(s2);
                }
                Cursor item = db.getresult(patient_id, type, date);
                item.moveToFirst();
                if (item.getCount() > 0)
                {
                    Double r = item.getDouble(3);
                    Double l = item.getDouble(4);
                    if (r > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                    else if (r > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                } else
                {
                    db.insertpatientresults(patient_id, date, type, righteyevalue, lefteyevalue);
                    inserted();
                }
            }
        });
        jtv3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String patient_id = jpatient_id.getText().toString();
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c.getTime());
                String type = jtype.getSelectedItem().toString();
                String s3 = v3.getText().toString();
                if (reye == 1)
                {
                    righteyevalue = Double.parseDouble(s3);
                } else if (leye == 1)
                {
                    lefteyevalue = Double.parseDouble(s3);
                }
                Cursor item = db.getresult(patient_id, type, date);
                item.moveToFirst();
                if (item.getCount() > 0)
                {
                    Double r = item.getDouble(3);
                    Double l = item.getDouble(4);
                    if (r > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                    else if (r > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                } else
                {
                    db.insertpatientresults(patient_id, date, type, righteyevalue, lefteyevalue);
                    inserted();
                }
            }
        });
        jtv4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String patient_id = jpatient_id.getText().toString();
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c.getTime());
                String type = jtype.getSelectedItem().toString();
                String s4 = v4.getText().toString();
                if (reye == 1)
                {
                    righteyevalue = Double.parseDouble(s4);
                } else if (leye == 1)
                {
                    lefteyevalue = Double.parseDouble(s4);
                }
                Cursor item = db.getresult(patient_id, type, date);
                item.moveToFirst();
                if (item.getCount() > 0)
                {
                    Double r = item.getDouble(3);
                    Double l = item.getDouble(4);
                    if (r > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                    else if (r > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                } else
                {
                    db.insertpatientresults(patient_id, date, type, righteyevalue, lefteyevalue);
                    inserted();
                }
            }
        });
        jtv5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String patient_id = jpatient_id.getText().toString();
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c.getTime());
                String type = jtype.getSelectedItem().toString();
                String s5 = v5.getText().toString();
                if (reye == 1)
                {
                    righteyevalue = Double.parseDouble(s5);
                } else if (leye == 1)
                {
                    lefteyevalue = Double.parseDouble(s5);
                }
                Cursor item = db.getresult(patient_id, type, date);
                item.moveToFirst();
                if (item.getCount() > 0)
                {
                    Double r = item.getDouble(3);
                    Double l = item.getDouble(4);
                    if (r > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                    else if (r > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                } else
                {
                    db.insertpatientresults(patient_id, date, type, righteyevalue, lefteyevalue);
                    inserted();
                }
            }
        });
        jtv6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String patient_id = jpatient_id.getText().toString();
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c.getTime());
                String type = jtype.getSelectedItem().toString();
                String s5 = v6.getText().toString();
                if (reye == 1)
                {
                    righteyevalue = Double.parseDouble(s5);
                } else if (leye == 1)
                {
                    lefteyevalue = Double.parseDouble(s5);
                }
                Cursor item = db.getresult(patient_id, type, date);
                item.moveToFirst();
                if (item.getCount() > 0)
                {
                    Double r = item.getDouble(3);
                    Double l = item.getDouble(4);
                    if (r > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                    else if (r > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                } else
                {
                    db.insertpatientresults(patient_id, date, type, righteyevalue, lefteyevalue);
                    inserted();
                }
            }
        });
        jtv7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String patient_id = jpatient_id.getText().toString();
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c.getTime());
                String type = jtype.getSelectedItem().toString();
                String s5 = v7.getText().toString();
                if (reye == 1)
                {
                    righteyevalue = Double.parseDouble(s5);
                } else if (leye == 1)
                {
                    lefteyevalue = Double.parseDouble(s5);
                }
                Cursor item = db.getresult(patient_id, type, date);
                item.moveToFirst();
                if (item.getCount() > 0)
                {
                    Double r = item.getDouble(3);
                    Double l = item.getDouble(4);
                    if (r > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                    else if (r > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && reye == 1)
                    {
                        db.updatepatientresults(patient_id, type, righteyevalue, l);
                        updated();
                    }
                    else if (l > 0.0 && leye == 1)
                    {
                        db.updatepatientresults(patient_id, type, r, lefteyevalue);
                        updated();
                    }
                } else
                {
                    db.insertpatientresults(patient_id, date, type, righteyevalue, lefteyevalue);
                    inserted();
                }
            }
        });
        b.setOnClickListener(this);
        btnch.setOnClickListener(this);
    }

    public void updated()
    {
        Toast.makeText(getApplicationContext(), "Result Updated", Toast.LENGTH_LONG).show();
    }

    public void inserted()
    {
        Toast.makeText(getApplicationContext(), "Result Inserted", Toast.LENGTH_LONG).show();
    }

    public void alltextfont(Typeface typeface)
    {
        jtv1.setTypeface((typeface));
        jtv2.setTypeface((typeface));
        jtv3.setTypeface((typeface));
        jtv4.setTypeface((typeface));
        jtv5.setTypeface((typeface));
        jtv6.setTypeface((typeface));
        jtv7.setTypeface((typeface));
    }

    public void textsize(int size)
    {
        switch (size)
        {
            case 9 :
                jtv1.setTextSize(size);
                jtv2.setTextSize(size - 1);
                jtv3.setTextSize(size - 2);
                jtv4.setTextSize(size - 3);
                jtv5.setTextSize(size - 4);
                jtv6.setTextSize(size - 5);
                jtv7.setTextSize(size - 6);
                break;
            case 15 :
                jtv1.setTextSize(size);
                jtv2.setTextSize(size - 2);
                jtv3.setTextSize(size - 4);
                jtv4.setTextSize(size - 6);
                jtv5.setTextSize(size - 8);
                jtv6.setTextSize(size - 10);
                jtv7.setTextSize(size - 12);
                break;
            case 21 :
                jtv1.setTextSize(size);
                jtv2.setTextSize(size - 3);
                jtv3.setTextSize(size - 6);
                jtv4.setTextSize(size - 9);
                jtv5.setTextSize(size - 12);
                jtv6.setTextSize(size - 15);
                jtv7.setTextSize(size - 18);
                break;
            case 33 :
                jtv1.setTextSize(size);
                jtv2.setTextSize(size - 4);
                jtv3.setTextSize(size - 8);
                jtv4.setTextSize(size - 12);
                jtv5.setTextSize(size - 16);
                jtv6.setTextSize(size - 20);
                jtv7.setTextSize(size - 24);
                break;
            case 43 :
                jtv1.setTextSize(size);
                jtv2.setTextSize(size - 5);
                jtv3.setTextSize(size - 10);
                jtv4.setTextSize(size - 15);
                jtv5.setTextSize(size - 20);
                jtv6.setTextSize(size - 25);
                jtv7.setTextSize(size - 30);
                break;
        }

        v1.setTextSize(10);
        v2.setTextSize(10);
        v3.setTextSize(10);
        v4.setTextSize(10);
        v5.setTextSize(10);
        v6.setTextSize(10);
        v7.setTextSize(10);
    }

    public void randomtext(int pc, int pl)
    {
        jtv1.setText(getRandomString(pc, pl));
        jtv2.setText(getRandomString(pc, pl));
        jtv3.setText(getRandomString(pc, pl));
        jtv4.setText(getRandomString(pc, pl));
        jtv5.setText(getRandomString(pc, pl));
        jtv6.setText(getRandomString(pc, pl));
        jtv7.setText(getRandomString(pc, pl));
    }

    private static String getRandomString(final int sizeofRandomStringChar, final int sizeofRandomStringLetter)
    {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeofRandomStringChar);
        for (int i = 1; i <= sizeofRandomStringLetter; i++)
        {
            for (int j = 1; j <= sizeofRandomStringChar; j++)
            {
                sb.append(Allowed_Characters.charAt(random.nextInt(Allowed_Characters.length())));
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN))
        {
            main.setVisibility(View.VISIBLE);
            main.bringToFront();
        } else if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (isChecked)
        {
            if (buttonView.getId() == R.id.rdbrighteye)
            {
                jlefteye.setChecked(false);
            }
            if (buttonView.getId() == R.id.rdblefteye)
            {
                jrighteye.setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnsave:
                if (jrighteye.isChecked())
                {
                    reye = 1;
                    leye = 0;
                }
                else
                {
                    reye = 0;
                    leye = 1;
                }
                main.setVisibility(View.GONE);
                break;
            case R.id.btnchoosepatient:
                finish();
                Flag obj = new Flag();
                obj.setflag(0);
                Intent i = new Intent(Test.this, Search_Patient.class);
                startActivity(i);
                break;
        }
    }

    public void onBackPressed()
    {
        finish();
    }
}