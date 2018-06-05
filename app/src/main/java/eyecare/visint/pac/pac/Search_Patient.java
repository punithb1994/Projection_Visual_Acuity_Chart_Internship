package eyecare.visint.pac.pac;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Search_Patient extends AppCompatActivity
{
    int k;
    SearchView searchView;
    private Toolbar toolbar;
    private ListView list_order;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> Liste = new ArrayList<String>();
    private DBHelper db;
    Flag obj = new Flag();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_patient);
        setTitle("Select Patient");
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
        db = new DBHelper(this);
        k = obj.flag;
        if (k == 1)
        {
            Intent intent = new Intent(getApplicationContext(), Test.class);
            intent.putExtra("selected-item", obj.pid);
            finish();
        } else
        {
            list_order = (ListView) findViewById(R.id.listpatients);
            searchView = (SearchView) findViewById(R.id.searchpatient);
            searchView.setEnabled(false);
            Liste = new ArrayList<String>();
            Cursor c = db.getpatients();
            while (c.moveToNext())
            {
                Liste.add(c.getString(0));
                adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, Liste);
                list_order.setAdapter(adapter);
                searchView.setEnabled(true);
            }

            list_order.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, final View v, final int position, long arg3)
                {
                    String text = list_order.getItemAtPosition(position).toString();
                    obj.setflag(1);
                    obj.setpid(text);
                    Intent intent = new Intent(getApplicationContext(), Test.class);
                    intent.putExtra("selected-item", text);
                    startActivity(intent);
                    finish();
                }
            });

            SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
            {
                //@Override
                public boolean onQueryTextChange(String newText)
                {
                    try
                    {
                        adapter.getFilter().filter(newText);
                        System.out.println("On text chnge text: " + newText);
                        return true;
                    } catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query)
                {
                    // this is your adapter that will be filtered
                    adapter.getFilter().filter(query);
                    System.out.println("On query submit: " + query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(textChangeListener);
        }

        //Turn iconified to false:
        searchView.setIconified(false);
        //The above line will expand it to fit the area as well as throw up the keyboard

        //To remove the keyboard, but make sure you keep the expanded version:
        searchView.clearFocus();
     }

    @Override
    protected void onDestroy()
    {

        super.onDestroy();

    }
}