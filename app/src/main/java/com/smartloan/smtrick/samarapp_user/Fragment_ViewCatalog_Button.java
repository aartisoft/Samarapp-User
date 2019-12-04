package com.smartloan.smtrick.samarapp_user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.smartloan.smtrick.samarapp_user.View_Catalog_Activity.fragment;


public class Fragment_ViewCatalog_Button extends Fragment {

    private Button btnCreate;
    ViewPager viewPager;
    private int[] textureArrayWin = {
            R.drawable.naturalfloor3,
            R.drawable.naturalfloor,
            R.drawable.naturalfloor2,
    };
    private static int NUM_PAGES = 0;
    private List<Advertise> uploads;
    private List<Advertise> uploads1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_send_request, container, false);

        btnCreate = (Button) view.findViewById(R.id.btn);
        viewPager = view.findViewById(R.id.viewPager);

        uploads = new ArrayList<>();
        uploads1 = new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference("Advertise");

        query.addValueEventListener(valueEventListener);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 500, 4000);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentActivity(v);

            }
        });
        
        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            uploads.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Advertise upload = postSnapshot.getValue(Advertise.class);

                uploads.add(upload);
                int size = uploads.size() - 1;
                uploads1.clear();
                for (int i = size; i >= 0; i--) {
                    uploads1.add(uploads.get(i));
                }

            }
            NUM_PAGES = uploads1.size();
//            showDots();
            ImageAdapter adapter = new ImageAdapter(getContext(), uploads1);
            viewPager.setAdapter(adapter);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 500, 3000);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < NUM_PAGES - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(getContext(), ViewCatalogActivity.class);
        intent.putExtra(ViewCatalogActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(ViewCatalogActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(getContext(), intent, options.toBundle());
    }

}
