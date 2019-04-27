package com.smartloan.smtrick.samarapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainCatalog_Activity extends AppCompatActivity {

    RecyclerView mainCatalogRecycler;
    ProgressBar maincatalogprogress;

    private String subitem, mainitem;

    private List<Upload> uploads;

    private DatabaseReference mDatabase;

    MainCatalog_adapter adapter;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_catalog_);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainCatalogRecycler = (RecyclerView) findViewById(R.id.maincatalog_recycle);
        mainCatalogRecycler.setHasFixedSize(true);
        mainCatalogRecycler.setLayoutManager(new LinearLayoutManager(this));

        maincatalogprogress = (ProgressBar) findViewById(R.id.maincatalog_progress);

        Intent intent = getIntent();

        uploads = new ArrayList<>();

        mainitem = intent.getStringExtra("mianproduct");
        subitem = intent.getStringExtra("subproduct");

        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        Query query = FirebaseDatabase.getInstance().getReference("NewImage").orderByChild("subproduct").equalTo(subitem);

        query.addValueEventListener(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            uploads.clear();
            maincatalogprogress.setVisibility(View.INVISIBLE);
            //iterating through all the values in database
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Upload upload = postSnapshot.getValue(Upload.class);

                if (upload.getMainproduct().equalsIgnoreCase(mainitem) && upload.getSubproduct().equalsIgnoreCase(subitem)) {
                    uploads.add(upload);
                }
            }

            //creating adapter
            adapter = new MainCatalog_adapter(getApplicationContext(), uploads);

            //adding adapter to recyclerview
            mainCatalogRecycler.setAdapter(adapter);

            maincatalogprogress.setVisibility(View.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            maincatalogprogress.setVisibility(View.INVISIBLE);

        }
    };
}
