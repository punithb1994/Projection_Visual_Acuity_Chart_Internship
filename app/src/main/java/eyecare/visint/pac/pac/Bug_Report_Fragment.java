package eyecare.visint.pac.pac;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Bug_Report_Fragment extends Fragment
{
    private Button send;
    private EditText emailbody;
    private int i, j;
    private TextView tv;
    private ImageView image1;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.bug_report_fragment, container, false);
        emailbody = (EditText) v.findViewById(R.id.body);
        send = (Button) v.findViewById(R.id.send);
        image1 = (ImageView) v.findViewById(R.id.imageViewbugreporting);
        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendEmail();
            }
        });
        tv = (TextView) v.findViewById(R.id.displaytextview);
        emailbody.addTextChangedListener(new TextWatcher()  //Textchange listener to display no of characters left to enter
        {
            public void afterTextChanged(Editable s)
            {
                i++;
                j = 100 - s.length();
                tv.setText(j + " characters left");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String k = emailbody.getText().toString();
                if (k.length() == 0)
                {
                    tv.setVisibility(View.INVISIBLE);
                } else
                {
                    tv.setVisibility(View.VISIBLE);
                }
                image1.setImageResource(R.drawable.ic_report_bugs);
            }
        });
        return v;
    }

    public void sendEmail() //Function to send the mail
    {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String k = emailbody.getText().toString();
        if (!k.isEmpty())
        {
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"visintcomplog@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug Report");
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailbody.getText().toString());
            getActivity().startActivity(Intent.createChooser(emailIntent, "Bug report sent"));
            getActivity().finish();
        } else
        {
            image1.setImageResource(R.drawable.ic_red_bug_report);
            Snackbar snackbar = Snackbar.make(v.findViewById(R.id.LinearLayout01), "Bug report field cannot be blank", Snackbar.LENGTH_SHORT);
            View view = snackbar.getView();
            TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            txtv.setGravity(Gravity.CENTER_HORIZONTAL);
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN)
            txtv.setTextAlignment(view.TEXT_ALIGNMENT_CENTER);
            txtv.setTextColor(Color.RED);
            snackbar.show();
        }
    }
}