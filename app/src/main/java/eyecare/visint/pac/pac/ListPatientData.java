package eyecare.visint.pac.pac;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class ListPatientData extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback
{
    int r = 0;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayList<User> users;
    private List<User> filteredModelList;
    private LinearLayoutManager layoutManager;
    private UsersAdapter adapter;
    private static final String TAG = "VisintHealthcare";
    public static String PACKAGE_NAME;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private FrameLayout Framevis, fm1, fm2, fm3;
    private TextView tvfab1, tvfab2, tvfab3;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_patient_data);

        recyclerView = (RecyclerView) findViewById(R.id.usersList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        db = new DBHelper(this);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Patients");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        isStoragePermissionGranted();
        PACKAGE_NAME = getApplicationContext().getPackageName();

        Framevis = (FrameLayout) findViewById(R.id.framelayoutbluring);
        tvfab1 = (TextView) findViewById(R.id.textViewfab1);
        tvfab2 = (TextView) findViewById(R.id.textViewfab2);
        tvfab3 = (TextView) findViewById(R.id.textViewfab3);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fm1 = (FrameLayout) findViewById(R.id.frameLayout1);
        fm2 = (FrameLayout) findViewById(R.id.frameLayout2);
        fm3 = (FrameLayout) findViewById(R.id.frameLayout3);

        Framevis.setEnabled(false);
        tvfab1.setEnabled(false);
        tvfab2.setEnabled(false);
        tvfab3.setEnabled(false);
        fab1.setEnabled(false);
        fab2.setEnabled(false);
        fab3.setEnabled(false);

        datatorecycler();
        //recycler view ONCLICK
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener()
        {
            User card;

            @Override
            public void onClick(View view, int position)
            {
            }

            @Override
            public void onLongClick(View view, int position)
            {
                if (r == 0)
                {
                    card = users.get(position);
                } else
                {
                    card = filteredModelList.get(position);
                }
                finish();
                String id = card.getId().toString();
                Intent intent = new Intent(getApplicationContext(), Patient_Details_Tab.class);
                intent.putExtra("pid", id);
                startActivity(intent);
            }
        }));

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
    }

    public void datatorecycler()
    {
        r = 0;
        users = db.getAllUsers();
        // Sorting
        Collections.sort(users, new Comparator<User>()
        {
            @Override
            public int compare(User user2, User user1)
            {
                return user1.id.compareTo(user2.id);
            }
        });
        adapter = new UsersAdapter(users);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (r == 0)
        {
            datatorecycler();  //code to refresh data
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_viewpatientdetailslistactivity, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener()
        {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item)
            {
                // Do something when collapsed
                adapter.animateTo(users);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item)
            {
                // Do something when expanded
                return true; // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        r = 1;
        if (newText.isEmpty())
        {
            datatorecycler();
        }
        final List<User> filteredModelList = filter(users, newText);
        adapter.animateTo(filteredModelList);
        recyclerView.scrollToPosition(0);
        setModels(users);
        return true;
    }

    public void setModels(List<User> models)
    {
        users = new ArrayList<>(models);
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return true;
    }

    private List<User> filter(List<User> models, String query)
    {
        query = query.toLowerCase();
        filteredModelList = new ArrayList<>();
        for (User model : models)
        {
            final String text = model.getFname().toLowerCase() + model.getId().toLowerCase() + model.getLname().toLowerCase();
            if (text.contains(query))
            {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.delete)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Deleting Entire Database");
            alertDialogBuilder.setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    dialog.cancel();
                }
            }).setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    db.deleteAllpatients();
                    db.close();
                    recyclerView.setAdapter(null);  //Clearing the listview after deleting the database
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isStoragePermissionGranted()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            } else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else
        {
            return true;
        }
    }

    @Override
    public void onClick(View v)
    {
        try
        {
            int id = v.getId();
            switch (id)
            {
                case R.id.fab:
                    animateFAB();
                    Framevis.setOnClickListener(this);
                    fm1.setOnClickListener(this);
                    fm2.setOnClickListener(this);
                    fm3.setOnClickListener(this);
                    fab1.setOnClickListener(this);
                    fab2.setOnClickListener(this);
                    fab3.setOnClickListener(this);
                    break;
                case R.id.fab1:
                    closefab();
                    importDB();
                    break;
                case R.id.frameLayout1:
                    closefab();
                    importDB();
                    break;
                case R.id.fab2:
                    closefab();
                    exportDB();
                    break;
                case R.id.frameLayout2:
                    closefab();
                    exportDB();
                    break;
                case R.id.fab3:
                    openaddpatient(); // calling addpatient activity
                    closefab();
                    break;
                case R.id.frameLayout3:
                    openaddpatient();
                    closefab();
                    break;
                case R.id.framelayoutbluring:
                    closefab();
            }
        } catch (Exception e)
        {
            displaysnackbar("Error");
        }
    }

    public void openaddpatient()   //Opening add patient activity with this function
    {
        Intent add_mem1 = new Intent(this, Add_Patient_Data.class);
        startActivity(add_mem1);
    }

    public void animateFAB()
    {
        if (isFabOpen)
        {
            Framevis.setClickable(false);
            closefab();
        } else
        {
            openfab();
        }
    }

    public void exportDB()
    {
        String SAMPLE_DB_NAME = db.getDatabaseName();
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + PACKAGE_NAME + "/databases/" + SAMPLE_DB_NAME;
        String backupDBPath = SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try
        {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            displaysnackbar("Database Exported");
            closefab();
        } catch (IOException e)
        {
            redsnackbar("Error Exporting");
        }
    }

    public void importDB()
    {
        String SAMPLE_DB_NAME = db.getDatabaseName();
        try
        {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite())
            {
                String currentDBPath = "/data/" + PACKAGE_NAME + "/databases/" + SAMPLE_DB_NAME;
                String backupDBPath = SAMPLE_DB_NAME;
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                datatorecycler();
                displaysnackbar("Database Imported");
            }
        } catch (Exception e)
        {
            redsnackbar("Error Importing");
        }
    }

    public void displaysnackbar(String str)
    {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.list_patient_relativelayout), str, Snackbar.LENGTH_SHORT);

        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN)
        txtv.setTextAlignment(view.TEXT_ALIGNMENT_CENTER);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
        snackbar.show();
    }

    public void redsnackbar(String str)
    {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.LinearLayout01), str, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN)
        txtv.setTextAlignment(view.TEXT_ALIGNMENT_CENTER);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
        txtv.setTextColor(Color.RED);
        snackbar.show();
    }

    //class for recyclerview touch event using gesture detector
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener)
        {
            Log.d(TAG, "constructor invoked");
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
            {
                @Override
                public boolean onSingleTapUp(MotionEvent e)
                {
                    Log.d(TAG, "onSingleTap " + e);
                    return true;
                }

                @SuppressWarnings("deprecation")
                public void onLongPress(MotionEvent e)
                {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null)
                    {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                    Log.d(TAG, "onLongPress " + e);
                }
            });
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
        {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e))
            {
                clickListener.onLongClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e)
        {
            Log.d(TAG, "constructor invoked");
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
        {
        }
    }

    public static interface ClickListener
    {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    //end of class
    public void openfab()
    {
        Framevis.setVisibility(View.VISIBLE);
        Framevis.setEnabled(true);
        tvfab1.setVisibility(View.VISIBLE);
        tvfab2.setVisibility(View.VISIBLE);
        tvfab3.setVisibility(View.VISIBLE);
        fab.startAnimation(rotate_forward);
        tvfab1.startAnimation(fab_open);
        tvfab2.startAnimation(fab_open);
        tvfab3.startAnimation(fab_open);
        fab1.startAnimation(fab_open);
        fab2.startAnimation(fab_open);
        fab3.startAnimation(fab_open);
        fab1.setClickable(true);
        fab2.setClickable(true);
        fab3.setClickable(true);
        isFabOpen = true;
        tvfab1.setEnabled(true);
        tvfab2.setEnabled(true);
        tvfab3.setEnabled(true);
        fab1.setEnabled(true);
        fab2.setEnabled(true);
        fab3.setEnabled(true);
    }

    public void closefab()
    {
        Framevis.setVisibility(View.GONE);
        Framevis.setEnabled(false);
        tvfab1.setVisibility(View.GONE);
        tvfab2.setVisibility(View.GONE);
        tvfab3.setVisibility(View.GONE);
        fab.startAnimation(rotate_backward);
        tvfab1.startAnimation(fab_close);
        tvfab2.startAnimation(fab_close);
        tvfab3.startAnimation(fab_close);
        fab1.startAnimation(fab_close);
        fab2.startAnimation(fab_close);
        fab3.startAnimation(fab_close);
        fab1.setClickable(false);
        fab2.setClickable(false);
        fab3.setClickable(false);
        isFabOpen = false;
        tvfab1.setEnabled(false);
        tvfab2.setEnabled(false);
        tvfab3.setEnabled(false);
        fab1.setEnabled(false);
        fab2.setEnabled(false);
        fab3.setEnabled(false);
        Framevis.setOnClickListener(null);
        fm1.setOnClickListener(null);
        fm2.setOnClickListener(null);
        fm3.setOnClickListener(null);
        fab1.setOnClickListener(null);
        fab2.setOnClickListener(null);
        fab3.setOnClickListener(null);
    }

    public void onBackPressed()
    {
        this.finish();
    }
}