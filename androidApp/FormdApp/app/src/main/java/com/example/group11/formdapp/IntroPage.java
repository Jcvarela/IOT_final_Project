package com.example.group11.formdapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.group11.formdapp.Utilities.fields.FieldList;


public class IntroPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "class_IntroPage";

    // variable to keep track of last filled form
    private String latestFormFilled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_forms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragById( R.id.nav_all_forms);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Log.i(TAG, "onBackPress press");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "Create Manu onCreateOptionMenu Press");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_forms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       setFragById(id);
        return true;
    }

    public void setFragById(int id){
        if (id == R.id.nav_all_forms) {
            AllFormFrag allFormFrag = new AllFormFrag();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.forFragList, allFormFrag, allFormFrag.getTag()).commit();
        } else if (id == R.id.nav_latest_form) {
            LatestFormFrag latestFormFrag = new LatestFormFrag();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.forFragList, latestFormFrag, latestFormFrag.getTag()).commit();
        } else if (id == R.id.nav_fill_form) {
            Intent intent = new Intent(this, FieldList.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            SettingFrag settingFormFrag = new SettingFrag();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.forFragList, settingFormFrag, settingFormFrag.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
