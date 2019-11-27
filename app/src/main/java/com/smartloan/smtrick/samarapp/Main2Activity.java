package com.smartloan.smtrick.samarapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    SNavigationDrawer sNavigationDrawer;
    int color1=0;
    Class fragmentClass;
    public static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        Boolean per = isStoragePermissionGranted();
        if (per){
            //   Toast.makeText(this, "Storage Premission Granted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Storage Premission Required", Toast.LENGTH_SHORT).show();
        }

        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Add Product Names",R.color.Black));
        menuItems.add(new MenuItem("Upload Catalog Images",R.color.Black));
        menuItems.add(new MenuItem("View Catalogs",R.color.Black));
        menuItems.add(new MenuItem("Log Out",R.color.Black));
        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  Fragment_ViewCatalogs.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }


        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        color1 = R.color.red;
                        fragmentClass = Fragment_Add_Product_names.class;
                        break;
                    }
                    case 1:{
                        color1 = R.color.orange;
                        fragmentClass = Fragment_Add_Products.class;
//                        Intent intent = new Intent(Main2Activity.this,Upload_Image_Activity.class);
//                        startActivity(intent);
                        break;
                    }
                    case 2:{
                        color1 = R.color.green;
                        fragmentClass = Fragment_ViewCatalogs.class;
                        break;
                    }
                    case 3:{
                        color1 = R.color.blue;
//                        fragmentClass = MusicFragment.class;
                        break;
                    }

                }
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });
            }
        });

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
}
