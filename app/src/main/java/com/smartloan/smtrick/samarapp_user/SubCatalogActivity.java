package com.smartloan.smtrick.samarapp_user;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.samarapp_user.R;

import java.util.ArrayList;
import java.util.List;

public class SubCatalogActivity extends AppCompatActivity {

    private RecyclerView subcatalogRecycler;
    private List<String> subcatalogList;
    private List<String> subcatalogList1;
    private List<String> images;
    private SubCatalogAdapter subCatalogAdapter;
    private String name;
    private ProgressBar subCatalogProgress;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_catalog);

        subCatalogProgress = (ProgressBar) findViewById(R.id.Sub_progress);
        subCatalogProgress.setVisibility(View.VISIBLE);

        subcatalogRecycler = (RecyclerView) findViewById(R.id.subcatalog_recycle);
        subcatalogList = new ArrayList<>();
        subcatalogList1 = new ArrayList<>();

        Intent intent = getIntent();
        name = intent.getStringExtra("itemName");

        Drawable backArrow = getResources().getDrawable(R.drawable.vd_pathmorph_drawer_arrow);
        backArrow.setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>"+name+"</font>"));


        Query query3 = FirebaseDatabase.getInstance().getReference("SubProducts")
                .orderByChild("mainproduct")
                .equalTo(name);
        query3.addValueEventListener(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            subcatalogList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MainSubProducts subproducts1 = snapshot.getValue(MainSubProducts.class);
                    subcatalogList.add(subproducts1.getSubproduct());

                    int size = subcatalogList.size()-1;
                    subcatalogList1.clear();
                    for(int i=size;i>=0;i--){
                        subcatalogList1.add(subcatalogList.get(i));
                    }
                }
            }
            subCatalogAdapter = new SubCatalogAdapter(subcatalogList1, name);
            subcatalogRecycler.setHasFixedSize(true);
            subcatalogRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            subcatalogRecycler.setAdapter(subCatalogAdapter);
            subCatalogProgress.setVisibility(View.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
