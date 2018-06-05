package eyecare.visint.pac.pac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Home_Fragment extends Fragment implements View.OnClickListener
{
    private Button Btncalibration, Btnchartsettings;
    private GridLayoutManager lLayout1; //To Display recyclerview in a grid format Layout is declared

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        Btncalibration = (Button) v.findViewById(R.id.button1);
        Btnchartsettings = (Button) v.findViewById(R.id.button2);

        List<ItemObjectHome> rowListItem1 = getAllItemList1();  //List is declared and data is initialized from getallitemlist() function
        lLayout1 = new GridLayoutManager(getContext(), 1);  //To set recyclerview in grid format Here you change the value from 1 to number of columns you want to display
        RecyclerView rView1 = (RecyclerView) v.findViewById(R.id.recycler_view1);
        rView1.setHasFixedSize(true);
        rView1.setLayoutManager(lLayout1);
        RecyclerViewAdapter_Home rcAdapter1 = new RecyclerViewAdapter_Home(getContext(), rowListItem1);
        rView1.setAdapter(rcAdapter1);

        Btncalibration.setOnClickListener(this);
        Btnchartsettings.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button1:
                Intent i1 = new Intent(getContext(), Calibrate.class);
                startActivity(i1);
                break;
            case R.id.button2:
                Intent i2 = new Intent(getContext(), Chart_Settings.class);
                startActivity(i2);
                break;
        }
    }

    private List<ItemObjectHome> getAllItemList1()  //Function to get data to a list
    {
        List<ItemObjectHome> allItems1 = new ArrayList<ItemObjectHome>();
        allItems1.add(new ItemObjectHome("PATIENT DETAILS"));
        allItems1.add(new ItemObjectHome("ACUITY TEST"));
        return allItems1;
    }
}