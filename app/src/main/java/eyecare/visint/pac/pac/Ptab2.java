package eyecare.visint.pac.pac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Ptab2 extends Fragment
{
    private DBHelper db;
    private ArrayList<String> Liste = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView jlistdate;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.ptab2, container, false);
        jlistdate = (ListView) v.findViewById(R.id.listViewDate);
        db = new DBHelper(getActivity());
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String id = sharedPref.getString("sharedPID", "");

        Liste = new ArrayList<String>();
        Cursor c = db.getresultonebyon(id);

        while (c.moveToNext())
        {
            Liste.add(c.getString(1) + "  " + c.getString(2));
            // Liste.add(c.getString(2));
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, Liste);
            jlistdate.setAdapter(adapter);
        }

        jlistdate.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, final View v, final int position, long arg3)
            {
                String text = jlistdate.getItemAtPosition(position).toString();
                String text1 = text.substring(text.lastIndexOf(' ') + 1).trim();
                String date = text.substring(0, text.indexOf(' '));
                Intent intent = new Intent(getActivity(), Result.class);
                intent.putExtra("item", text1);
                intent.putExtra("itemdate", date);
                startActivity(intent);
                //finish();
            }
        });
        return v;
    }
}