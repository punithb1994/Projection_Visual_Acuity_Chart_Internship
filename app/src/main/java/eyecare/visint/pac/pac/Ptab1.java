package eyecare.visint.pac.pac;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Ptab1 extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    public String a, gender, globalpatientid;
    private DBHelper db;
    private Button editBtn, updateBtn;
    private int mYear, mMonth, mDay;
    private View v;
    private TextInputLayout t2;
    private EditText vpatientid, vfirstname, vlastname, vdob, vmobilenumber, vaddress, vconsultant, vg;
    private RadioButton male, female;
    private LinearLayout l1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        db = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        db = new DBHelper(getContext());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        v = inflater.inflate(R.layout.ptab1, container, false);
        vpatientid = (EditText) v.findViewById(R.id.ViewingPatientID);
        vfirstname = (EditText) v.findViewById(R.id.ViewingPatientFirstname);
        vlastname = (EditText) v.findViewById(R.id.ViewingPatientLastName);
        vdob = (EditText) v.findViewById(R.id.ViewingDOB);
        vg = (EditText) v.findViewById(R.id.Viewinggender);
        vmobilenumber = (EditText) v.findViewById(R.id.ViewingMobileNumber);
        vaddress = (EditText) v.findViewById(R.id.ViewingAddress);
        vconsultant = (EditText) v.findViewById(R.id.ViewingConsultant);
        notediting(vpatientid);
        notediting(vfirstname);
        notediting(vlastname);
        notediting(vdob);
        notediting(vg);
        notediting(vmobilenumber);
        notediting(vaddress);
        notediting(vconsultant);
        l1 = (LinearLayout) v.findViewById(R.id.genderlinearlayout);
        l1.setVisibility(View.GONE);

        t2 = (TextInputLayout) v.findViewById(R.id.gentextlayout);
        male = (RadioButton) v.findViewById(R.id.rdbmale);
        female = (RadioButton) v.findViewById(R.id.rdbfem);
        //  edittextdisabled();
        editBtn = (Button) v.findViewById(R.id.EditButton);
        editBtn.setOnClickListener(this);
        updateBtn = (Button) v.findViewById(R.id.UpdateButton);
        updateBtn.setOnClickListener(this);
        updateBtn.setVisibility(View.GONE);
        male.setOnCheckedChangeListener(this);
        female.setOnCheckedChangeListener(this);

        cursordata();
        if (gender.equals("Male"))
        {
            male.setChecked(true);
        } else
        {
            female.setChecked(true);
        }
        return v;
    }

    public void cursordata()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String id = sharedPref.getString("sharedPID", "");
        Cursor c = db.getpatientonebyon(id);
        c.moveToFirst();
        if (c.getCount() > 0)
        {
            vpatientid.setText(c.getString(0));
            globalpatientid = c.getString(0);
            vfirstname.setText(c.getString(1));
            vlastname.setText(c.getString(c.getColumnIndex("lastname")));
            gender = c.getString(3);
            vg.setText(c.getString(3));
            vdob.setText(c.getString(4));
            vmobilenumber.setText(c.getString(c.getColumnIndex("mobilenumber")));
            vaddress.setText(c.getString(6));
            vconsultant.setText(c.getString(7));
        }
    }

    public void notediting(EditText a)
    {
        vdob.setOnClickListener(null);
        a.setFocusableInTouchMode(false);
        a.setFocusable(false);
        a.setCursorVisible(false);
        a.setClickable(false);
    }

    public void yesediting(EditText a)
    {
        vdob.setOnClickListener(this);
        a.setFocusableInTouchMode(true);
        a.setFocusable(true);
        a.setCursorVisible(true);
        a.setClickable(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (isChecked)
        {
            if (buttonView.getId() == R.id.rdbmale)
            {
                female.setChecked(false);
            }
            if (buttonView.getId() == R.id.rdbfem)
            {
                male.setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ViewingDOB:
                String datestr = vdob.getText().toString();
                if (!datestr.isEmpty())
                {
                    SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
                    try
                    {
                        Date dateobj = curFormater.parse(datestr);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dateobj);
                        mYear = calendar.get(Calendar.YEAR);
                        mMonth = calendar.get(Calendar.MONTH);
                        mDay = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                vdob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                } else
                {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                        {
                            vdob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            vdob.setError(null);
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
                break;
            case R.id.EditButton:
                yesediting(vpatientid);
                yesediting(vfirstname);
                yesediting(vlastname);
                yesediting(vdob);
                yesediting(vg);
                yesediting(vmobilenumber);
                yesediting(vaddress);
                yesediting(vconsultant);
                vdob.setFocusable(false);
                l1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.GONE);
                editBtn.setVisibility(View.GONE);
                updateBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.UpdateButton:
                String k = vpatientid.getText().toString();  //k is the value of patient id of that edittext...it might be modified or not also
                if (k.equals(globalpatientid))   //globalpatientid is previous value which came initially from the database
                {
                    updating(k);             //updating is a function to update the data
                } else
                {
                    Cursor c = db.getpatientonebyon(k);
                    c.moveToFirst();
                    if (c.getCount() > 0)
                    {
                        Toast.makeText(getContext(), "Exists", Toast.LENGTH_SHORT).show();
                        vpatientid.setSelectAllOnFocus(true);
                    } else
                    {
                        String oldpatientid = globalpatientid;
                        String newpatientid = vpatientid.getText().toString();
                        updatingnew(oldpatientid, newpatientid);
                    }
                }
        }
    }

    public void updatingnew(String old, String current)
    {
        String firstname = vfirstname.getText().toString();
        String lastname = vlastname.getText().toString();
        String dob = vdob.getText().toString();
        String mobilenumber = vmobilenumber.getText().toString();
        String address = vaddress.getText().toString();
        String consultant = vconsultant.getText().toString();
        String gender;
        if (male.isChecked())
        {
            gender = "Male";
            female.setChecked(false);
        } else
        {
            gender = "Female";
            male.setChecked(false);
        }
        if (vmobilenumber.getText().length() < 7)
        {
            vmobilenumber.setError("Enter valid mobile number");
            //break;
        }
        if (current.isEmpty())
        {
            vpatientid.setError("Enter Patient ID");
            //break;
        } else if (firstname.isEmpty())
        {
            vfirstname.setError("Enter First name");
            //break;
        } else if (lastname.isEmpty())
        {
            vlastname.setError("Enter Last name");
            // break;
        } else if (dob.isEmpty())
        {
            vdob.setVisibility(View.VISIBLE);
            vdob.setError("Select DOB");
            //break;
        } else if (address.isEmpty())
        {
            vaddress.setError("Enter address");
            // break;
        } else if (mobilenumber.isEmpty())
        {
            vmobilenumber.setError("Enter mobile number");
            /// break;
        } else if (consultant.isEmpty())
        {
            vconsultant.setError("Enter consultant name");
            // break;
        } else
        {
            db.newupdatePatient(old, current, firstname, lastname, gender, dob, mobilenumber, address, consultant);
            cursordata();
            afterclickingupdate();
        }
    }

    public void updating(String newpatientid)
    {
        String firstname = vfirstname.getText().toString();
        String lastname = vlastname.getText().toString();
        String dob = vdob.getText().toString();
        String mobilenumber = vmobilenumber.getText().toString();
        String address = vaddress.getText().toString();
        String consultant = vconsultant.getText().toString();
        String gender;
        if (male.isChecked())
        {
            gender = "Male";
            female.setChecked(false);
        } else
        {
            gender = "Female";
            male.setChecked(false);
        }
        if (vmobilenumber.getText().length() < 7)
        {
            vmobilenumber.setError("Enter valid mobile number");
            //break;
        }
        if (newpatientid.isEmpty())
        {
            vpatientid.setError("Enter Patient ID");
            //break;
        } else if (firstname.isEmpty())
        {
            vfirstname.setError("Enter First name");
            //break;
        } else if (lastname.isEmpty())
        {
            vlastname.setError("Enter Last name");
            // break;
        } else if (dob.isEmpty())
        {
            vdob.setVisibility(View.VISIBLE);
            vdob.setError("Select DOB");
            //break;
        } else if (address.isEmpty())
        {
            vaddress.setError("Enter address");
            // break;
        } else if (mobilenumber.isEmpty())
        {
            vmobilenumber.setError("Enter mobile number");
            /// break;
        } else if (consultant.isEmpty())
        {
            vconsultant.setError("Enter consultant name");
            // break;
        } else
        {
            db.updatePatient(newpatientid, firstname, lastname, gender, dob, mobilenumber, address, consultant);
            afterclickingupdate();
            cursordata();
        }
    }

    public void afterclickingupdate()
    {
        notediting(vpatientid);
        notediting(vfirstname);
        notediting(vlastname);
        notediting(vdob);
        notediting(vg);
        notediting(vmobilenumber);
        notediting(vaddress);
        notediting(vconsultant);
        l1.setVisibility(View.GONE);
        t2.setVisibility(View.VISIBLE);
        updateBtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.VISIBLE);
    }
}