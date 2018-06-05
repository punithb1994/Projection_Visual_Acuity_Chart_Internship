package eyecare.visint.pac.pac;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private int ishomeopen = 1;
    private int fragmentopened = 0;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private Flag obj = new Flag();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        obj.setflag(0);

        // calibration setting value initialization to 20 //
        final SharedPreferences sharedPref = getSharedPreferences("MY_PREFS", 0);
        int s = sharedPref.getInt("stack", -1);
        if (s == -1)
        {
            final SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("stack", 4);
            editor.commit();
        }
        //end of calibration

        openhome();
        hamburgerdisplayed();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void hamburgerdisplayed()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();
        if (id == R.id.nav_home)
        {
            openhome();
        } else if (id == R.id.nav_reportbugs)
        {
            fragmentopened = 1;
            ishomeopen = 0;
            fragment = new Bug_Report_Fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            setTitle("Report Bugs");
        } else if (id == R.id.nav_about)
        {
            fragmentopened = 1;
            ishomeopen = 0;
            fragment = new About_Fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            setTitle("About");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openhome()
    {
        fragmentopened = 0;
        ishomeopen = 1;
        Fragment fragment = null;
        fragment = new Home_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        setTitle("PAC");
    }

    public void onBackPressed()
    {
        if (ishomeopen != 1)
        {
            openhome();
        } else
        {
            finish();
        }
    }
}