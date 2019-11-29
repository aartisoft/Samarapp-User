package com.smartloan.smtrick.samarapp_user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Fragment_ViewCatalogs extends Fragment {

    private RecyclerView catalogRecycler;
    private DatabaseReference mdataRef;
    private List<String> catalogList;
    private List<String> catalogList1;
    private ProgressBar catalogprogress;

    // int[] animationList = {R.anim.layout_animation_up_to_down};
    int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcatalog, container, false);

        // getActivity().getActionBar().setTitle("Products");
        catalogprogress = (ProgressBar) view.findViewById(R.id.catalog_progress);
        catalogRecycler = (RecyclerView) view.findViewById(R.id.catalog_recycle);
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
                catalogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                catalogRecycler.setAdapter(catalogAdapter);
                catalogprogress.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
