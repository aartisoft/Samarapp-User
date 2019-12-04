package com.smartloan.smtrick.samarapp_user;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCatalogActivity extends AppCompatActivity {

    private RecyclerView catalogRecycler;
    private DatabaseReference mdataRef;
    private List<String> catalogList;
    private List<String> catalogList1;
    private ProgressBar catalogprogress;

    // int[] animationList = {R.anim.layout_animation_up_to_down};
    int i = 0;
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;

    private int revealX;
    private int revealY;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_viewcatalog);

        final Intent intent = getIntent();
        rootLayout = findViewById(R.id.activity_show_images);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }

        Drawable backArrow = getResources().getDrawable(R.drawable.vd_pathmorph_drawer_arrow);
        backArrow.setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>"+"Catalogs"+"</font>"));

        // getActivity().getActionBar().setTitle("Products");
        catalogprogress = (ProgressBar) findViewById(R.id.catalog_progress);
        catalogRecycler = (RecyclerView) findViewById(R.id.catalog_recycle);
        catalogList = new ArrayList<>();
        catalogList1 = new ArrayList<>();

        catalogprogress.setVisibility(View.VISIBLE);
        mdataRef = FirebaseDatabase.getInstance().getReference("MainProducts");
        mdataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                catalogList.clear();
                for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                    MainProducts mainProducts = mainproductSnapshot.getValue(MainProducts.class);
                    catalogList.add(mainProducts.getMainpro());

                    int size = catalogList.size() - 1;
                    catalogList1.clear();
                    for (int i = size; i >= 0; i--) {
                        catalogList1.add(catalogList.get(i));
                    }
                }
                CatalogAdapter catalogAdapter = new CatalogAdapter(catalogList1);
                catalogRecycler.setHasFixedSize(true);
                catalogRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                catalogRecycler.setAdapter(catalogAdapter);
                catalogprogress.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

}
