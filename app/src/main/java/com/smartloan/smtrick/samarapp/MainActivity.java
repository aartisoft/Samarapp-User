package com.smartloan.smtrick.samarapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity
        implements OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {


    private NavigationView navigationView;
    private Fragment selectedFragement;
    private FirebaseAuth firebaseAuth;

    private String uid;
    String uName;
    private FirebaseUser Fuser;
    private DatabaseReference databaseReference;

    private TextView userEmail;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // NOTE : Just remove the fab button
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Boolean per = isStoragePermissionGranted();
        if (per){
         //   Toast.makeText(this, "Storage Premission Granted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Storage Premission Required", Toast.LENGTH_SHORT).show();
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //NOTE:  Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.products);
        View headerview = navigationView.getHeaderView(0);
        username = (TextView) headerview.findViewById(R.id.username);
        userEmail = (TextView) headerview.findViewById(R.id.useremail);

        FirebaseMessaging.getInstance().subscribeToTopic("Products");

        getCurrentuserdetails();

        //NOTE:  Open fragment1 initially.
        selectedFragement = new Fragment_ViewCatalogs();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, selectedFragement);
        ft.commit();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //   Log.v(TAG,"Permission is granted");
                return true;
            } else {

                // Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }else {
                    return false;
                }
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //  Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    private void getCurrentuserdetails() {

        try {
            firebaseAuth = FirebaseAuth.getInstance();

            Fuser = firebaseAuth.getCurrentUser();
            uid = Fuser.getUid();
        //    uid = Fuser.getDisplayName();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot usersnapshot : dataSnapshot.getChildren()) {
                        String acctname = dataSnapshot.child("name").getValue(String.class);
                        String acctemail = dataSnapshot.child("email").getValue(String.class);
                        username.setText(acctname);
                        userEmail.setText(acctemail);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //NOTE: creating fragment object
        Fragment fragment = null;
        if (id == R.id.products) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Fragment_Add_Product_names()).commit();
        } else if (id == R.id.upload) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                   new Fragment_UploadImage()).commit();
//            Intent intent = new Intent(MainActivity.this, Upload_Image_Activity.class);
//            startActivity(intent);
        } else if (id == R.id.view_catalogs) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,
                    new Fragment_ViewCatalogs()).commit();
        } else if (id == R.id.logout) {

            // clearDataWithSignOut();
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

//            Intent i = new Intent(MainActivity.this,Multiple_images_Activity.class);
//            startActivity(i);

        }

        //NOTE: Fragment changing code
        selectedFragement = fragment;
        if (fragment != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();

        }

        //NOTE:  Closing the drawer after selecting
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Ya you can also globalize this variable :P
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onFragmentInteraction(String title) {

        // NOTE:  Code to replace the toolbar title based current visible fragment
        getSupportActionBar().setTitle(title);
    }


    @Override
    public void changeFragement(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }
    }
}
