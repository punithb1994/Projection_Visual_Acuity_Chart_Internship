package eyecare.visint.pac.pac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Patient_Details_Tab extends AppCompatActivity
{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public String pid;
    private DBHelper db;
    private int a = 0;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientdetailstab);
        Intent intent = getIntent();
        // fetch value from key-value pair and make it visible on TextView.
        pid = intent.getStringExtra("pid");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("sharedPID", pid);
        editor.commit();
        db = new DBHelper(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        c = db.getresultonebyon(pid);
        if (c.getCount() == 0)
        {
            a = 0;
            setupViewPager(viewPager);
        } else
        {
            a = 1;
            setupViewPager(viewPager);
        }

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
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Ptab1(), "Patient Details");
        if (a == 1)
        {
            adapter.addFrag(new Ptab2(), "Results");
        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }
    }

    public void onBackPressed()
    {
        this.finish();
        Intent intent = new Intent(getApplicationContext(), ListPatientData.class);
        startActivity(intent);
    }
}