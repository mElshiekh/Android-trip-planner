package com.example.delllaptop.projone;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,UpcomingFrag.OnFragmentInteractionListener, HistoryFrag.OnFragmentInteractionListener, FullHistFrag.OnFragmentInteractionListener{
    UpcomingFrag upcomingFrag;
    HistoryFrag historyFrag;
    FragmentManager fM;
    int flag = 0;
//    Boolean refresh;

    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private FirebaseUser fuser;

    SharedPreferences saving;
    SharedPreferences.Editor edit;
    public static final String saveData="NewData";
    public static final String newLogin="NewLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new CheckPermissions(Welcome.this).checkInternet()) {
                    Intent intent1 = new Intent(Welcome.this, AddTripActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Welcome.this.startActivity(intent1);
                }
            }
        });
        if(savedInstanceState!=null)
        flag= savedInstanceState.getInt("flag");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        saving = getApplicationContext().getSharedPreferences(saveData, 0);
//        refresh = saving.getBoolean("refresh",true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 9999);
            }
        }
        ///////////////
        db = FirebaseDatabase.getInstance().getReference();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        Log.i("TAG", "onCreate: "+fuser);
        mAuth = FirebaseAuth.getInstance();
        //////////////

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(flag==0){
            upcomingFrag = new UpcomingFrag();
            fM = getFragmentManager();
            FragmentTransaction fT = fM.beginTransaction();
            fT.replace(R.id.mainFrag, upcomingFrag, "upcoming");
            fT.commit();
            Log.i("da5al","ahoh");
            setTitle("Upcoming trips");
//            saving = getApplicationContext().getSharedPreferences(saveData, 0);
//            edit = saving.edit();
//            edit.putBoolean("refresh",false);
//            saving=getSharedPreferences(saveData,0);
//            String user = saving.getString("user", "Not registered");
//            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//            TextView email  = (TextView) navigationView.findViewById(R.id.Navigationemail);
//            email.setText(user);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("da5al",flag+"");
        outState.putInt("flag",flag);
        Log.i("da5al",flag+"");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
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

        if (id == R.id.upcoming) {

            upcomingFrag = new UpcomingFrag();
            fM = getFragmentManager();
            FragmentTransaction fT = fM.beginTransaction();
            fT.replace(R.id.mainFrag,upcomingFrag,"upcoming");
            flag = 0;
            Log.i("da5al",flag+"");
            fT.commit();
            setTitle("Upcoming trips");
        } else if (id == R.id.history) {
            historyFrag = new HistoryFrag();
            fM = getFragmentManager();
            FragmentTransaction fT = fM.beginTransaction();
            fT.replace(R.id.mainFrag,historyFrag,"hist");
            flag = 1;
            Log.i("da5al",flag+"");
            fT.commit();
            setTitle("History");

        }else if (id == R.id.syncDownload) {
            saving = getApplicationContext().getSharedPreferences(saveData, 0);
            String user = saving.getString("user", "null");
            String userid = saving.getString("userid", "null");
            SyncDownload syncDownload = new SyncDownload(Welcome.this,mAuth,db,fuser);
            syncDownload.execute();

        } else if (id == R.id.logout) {
            Intent intent = new Intent(Welcome.this,Login.class);
            saving = getApplicationContext().getSharedPreferences(saveData, 0);
            edit = saving.edit();
            edit.putBoolean(newLogin,false);
            edit.commit();
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
