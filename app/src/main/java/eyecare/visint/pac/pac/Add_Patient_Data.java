package eyecare.visint.pac.pac;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Add_Patient_Data extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    private EditText varpatient_id, varpatientfirstname, varpatientlastname, varmobilenumber, varaddress, varconsultant, dobvar;
    private DBHelper db;
    private Button btnsave;
    private Toolbar toolbar;
    private Animation animBlink;
    private int mYear, mMonth, mDay,p=0;
    private RadioButton male, female;
    private String patientidentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patient);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha); //Setting Back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        setTitle("Add Patient Details");
        db = new DBHelper(this);  //Initializing Database
        male = (RadioButton) findViewById(R.id.radioButton1);
        female = (RadioButton) findViewById(R.id.radioButton2);
        ImageView image1 = (ImageView) findViewById(R.id.imageView5);
        image1.setImageResource(R.drawable.male);   //Setting Image before Radio Button
        ImageView image2 = (ImageView) findViewById(R.id.imageView6);
        image2.setImageResource(R.drawable.female); //Setting Image before Radio Button
        male.setOnCheckedChangeListener(this);
        female.setOnCheckedChangeListener(this);
        varpatient_id = (EditText) findViewById(R.id.AddeditTextPatientid);
        varpatientfirstname = (EditText) findViewById(R.id.AddeditTextFirstname);
        varpatientlastname = (EditText) findViewById(R.id.AddeditTextLastname);
        varmobilenumber = (EditText) findViewById(R.id.AddeditTextMobilenumber);
        varaddress = (EditText) findViewById(R.id.AddeditTextAddress);
        varconsultant = (EditText) findViewById(R.id.AddeditTextConsultant);
        dobvar = (EditText) findViewById(R.id.AddingDOBeditText);
        dobvar.setOnClickListener(this);
        btnsave = (Button) findViewById(R.id.AddbuttonSave);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/roboto_bold.ttf");  //Setting Font for Save Button
        btnsave.setTypeface(font);
        btnsave.setOnClickListener(this);
        varpatient_id.setFilters(new InputFilter[]{new InputFilter.AllCaps()}); //Validation for patientid letters to be capital
        dobvar.addTextChangedListener((new TextWatcher()    //To Display date selector calendar
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat cdate = new SimpleDateFormat("dd-MM-yyyy");
                String CurrentDateStr = cdate.format(cal.getTime());
                SimpleDateFormat cf = new SimpleDateFormat("dd-MM-yyyy");
                String datestr = dobvar.getText().toString();
                SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
                try
                {
                    Date dateobj = curFormater.parse(datestr);
                    Date currentDate = cf.parse(CurrentDateStr);
                    if (dateobj.after(currentDate))
                    {
                        dobvar.setText("");
                    }
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        }));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        ImageView image111 = (ImageView) findViewById(R.id.imageView5);
        ImageView image222 = (ImageView) findViewById(R.id.imageView6);
        if (isChecked)
        {
            image111.setImageResource(R.drawable.male);
            image222.setImageResource(R.drawable.female);
            if (buttonView.getId() == R.id.radioButton1)
            {
                image111.clearAnimation();
                image222.clearAnimation();
                male.clearAnimation();
                female.clearAnimation();
                female.setChecked(false);
            }
            if (buttonView.getId() == R.id.radioButton2)
            {
                image111.clearAnimation();
                image222.clearAnimation();
                male.clearAnimation();
                female.clearAnimation();
                male.setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        Cursor g = db.getpatients();
        ImageView image11 = (ImageView) findViewById(R.id.imageView5);
        ImageView image22 = (ImageView) findViewById(R.id.imageView6);
        switch (v.getId())
        {
            case R.id.AddbuttonSave:
                            p=0;
                final String patientid = varpatient_id.getText().toString(); //Saving the data to database after performing validation
                final String firstname = varpatientfirstname.getText().toString();
                final String lastname = varpatientlastname.getText().toString();
                final String mobilenumber = varmobilenumber.getText().toString();
                final String address = varaddress.getText().toString();
                final String consultant = varconsultant.getText().toString();
                final String dob = dobvar.getText().toString();
                String gender;

                while (g.moveToNext())
                {
                    patientidentifier = g.getString(0);
                    if (patientidentifier.equals(patientid))
                    {
                        // Toast.makeText(this, "Patient ID Already Exists", Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.addpatientrelativelayout), "Patient ID Already Exists", Snackbar.LENGTH_SHORT);
                        View view = snackbar.getView();
                        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
                            txtv.setTextAlignment(view.TEXT_ALIGNMENT_CENTER);
                        txtv.setTextColor(Color.RED);
                        snackbar.show();
                        p=1;
                    }
                    else
                        p=0;

                }
                if(p==0)
                {
                    if (male.isChecked())
                    {
                        gender = "Male";
                        female.setChecked(false);
                    } else
                    {
                        gender = "Female";
                        male.setChecked(false);
                    }
                    if (patientid.isEmpty())
                    {
                        varpatient_id.setError("Enter Patient ID");
                        break;
                    } else if (firstname.isEmpty())
                    {
                        varpatientfirstname.setError("Enter first name");
                        break;
                    } else if (lastname.isEmpty())
                    {
                        varpatientlastname.setError("Enter last name");
                        break;
                    } else if (!male.isChecked() && (!female.isChecked()))
                    {
                        image11.setImageResource(R.drawable.errormale);
                        image22.setImageResource(R.drawable.errorfemale);
                        animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                        image11.startAnimation(animBlink);
                        image22.startAnimation(animBlink);
                        male.startAnimation(animBlink);
                        female.startAnimation(animBlink);
                        break;
                    } else if (dob.isEmpty())
                    {
                        dobvar.setVisibility(View.VISIBLE);
                        dobvar.setError("Select DOB");
                        break;
                    } else if (address.isEmpty())
                    {
                        varaddress.setError("Enter address");
                        break;
                    } else if (mobilenumber.isEmpty() || varmobilenumber.getText().length() < 7)
                    {
                        varmobilenumber.setError("Enter valid mobile number");
                        break;
                    } else if (consultant.isEmpty())
                    {
                        varconsultant.setError("Enter consultant name");
                        break;
                    } else
                    {
                        db.insertpatientdetails(new User(patientid, firstname, lastname, gender, dob, mobilenumber, address, consultant));
                        Intent home_intent = new Intent(getApplicationContext(), ListPatientData.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(home_intent);    //Opening New class
                        finish();
                        Toast.makeText(this, "Patient Details Added", Toast.LENGTH_SHORT).show();   //Displaying operation successful message
                    }
                }
                break;
            case R.id.AddingDOBeditText:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        dobvar.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        dobvar.setError(null);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
}