package com.smartloan.smtrick.samarapp_user;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Add_Product_names extends Fragment implements View.OnClickListener {

    private EditText flooreType;
    private EditText SubType;
    private Spinner spinner;
    private Spinner MainCatalog;
    private Spinner SubCatalog;

    private DatabaseReference mDatabaseRefMain;
    private DatabaseReference mDatabaseRefSub;

    private Button AddsubData;
    private Button AddmainData;
    private List<String> mainproductlist;
    private List<String> subproductlist;
    private List<String> cataloglist;
    private List<String> cataloglist1;
    private List<String> subproductList;
    MainProducts mainProducts;
    private DatabaseReference mDatabaseRefcatalog;


    private EditText etcatalogname;
    private Button buttonaddcatalog;
    private List<String> catloglist;

    String mainproduct;
    private CatalogAdapter adapter;

    private ImageView dropmain, dropsub, dropcat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product_names, container, false);


        flooreType = (EditText) view.findViewById(R.id.Main_product);
        SubType = (EditText) view.findViewById(R.id.Sub_product);
        AddsubData = (Button) view.findViewById(R.id.Add_sub_product);
        AddmainData = (Button) view.findViewById(R.id.Add_Main_product);
        spinner = (Spinner) view.findViewById(R.id.mainspinner);
        MainCatalog = (Spinner) view.findViewById(R.id.mainspinnercatalog);
        SubCatalog = (Spinner) view.findViewById(R.id.subspinnercatalog);

        dropmain = (ImageView) view.findViewById(R.id.dropmainproduct);
        dropsub = (ImageView) view.findViewById(R.id.dropsubproduct);
        dropcat = (ImageView) view.findViewById(R.id.dropcatalog);

        mainproductlist = new ArrayList<>();
        subproductlist = new ArrayList<>();
        cataloglist = new ArrayList<>();
        cataloglist1 = new ArrayList<>();
        subproductList = new ArrayList<>();

        mDatabaseRefSub = FirebaseDatabase.getInstance().getReference("SubProducts");
        mDatabaseRefMain = FirebaseDatabase.getInstance().getReference("MainProducts");
        mDatabaseRefcatalog = FirebaseDatabase.getInstance().getReference("MainCatalogs");


        etcatalogname = (EditText) view.findViewById(R.id.edit_catelogname);
        buttonaddcatalog = (Button) view.findViewById(R.id.Add_catalogname);

        try {

            mDatabaseRefMain.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mainproductlist.clear();
                    for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                        MainProducts mainProducts = mainproductSnapshot.getValue(MainProducts.class);


                        mainproductlist.add(mainProducts.getMainpro());


                    }
                    try {
                        ArrayAdapter<String> mainproadapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, mainproductlist);

                        mainproadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(mainproadapter);

                        ArrayAdapter<String> mainprocatadapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, mainproductlist);

                        mainprocatadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        MainCatalog.setAdapter(mainprocatadapter);
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            mDatabaseRefSub.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    subproductlist.clear();
                    for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                        MainSubProducts mainsubproducts = mainproductSnapshot.getValue(MainSubProducts.class);


                        subproductlist.add(mainsubproducts.getSubproduct());

                    }

                    try {

//                        ArrayAdapter<String> mainprocatadapter = new ArrayAdapter<String>(getContext(),
//                                android.R.layout.simple_spinner_item, subproductlist);
//
//                        mainprocatadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        SubCatalog.setAdapter(mainprocatadapter);
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            mDatabaseRefcatalog.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cataloglist.clear();
                    for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                        MainCatalog mainsubproducts = mainproductSnapshot.getValue(MainCatalog.class);

                        cataloglist.add(mainsubproducts.getMaincat());

//                        String main = spinner.getSelectedItem().toString();
//                        String sub = SubCatalog.getSelectedItem().toString();
//                        if (mainsubproducts.getMainpro().equalsIgnoreCase(main) &&
//                                mainsubproducts.getSubpro().equalsIgnoreCase(sub)){
//                            cataloglist1.add(mainsubproducts.getMaincat());
//                        }


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            AddsubData.setOnClickListener(this);
            AddmainData.setOnClickListener(this);
            buttonaddcatalog.setOnClickListener(this);


        } catch (Exception e) {
        }

        catalogsubspinnervalue();

        dropmain.setOnClickListener(this);
        dropsub.setOnClickListener(this);
        dropcat.setOnClickListener(this);
        return view;


    }

    private void catalogsubspinnervalue() {

        MainCatalog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mainpro = MainCatalog.getSelectedItem().toString();

                Query query = FirebaseDatabase.getInstance().getReference("SubProducts")
                        .orderByChild("mainproduct")
                        .equalTo(mainpro);
                query.addValueEventListener(valueEventListener);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                subproductList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MainSubProducts subproducts2 = snapshot.getValue(MainSubProducts.class);
                    subproductList.add(subproducts2.getSubproduct());
                }
                // subCatalogAdapter.notifyDataSetChanged();
            }
            try {
                ArrayAdapter<String> subadapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, subproductList);
                subadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SubCatalog.setAdapter(subadapter);
            } catch (Exception e) {
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    public void onClick(View v) {

        try {
            if (v == dropmain) {

                PopupWindow popUp = popupWindowsort(v, mainproductlist);
                popUp.showAsDropDown(v, 0, 0);

            }
            if (v == dropsub) {

                PopupWindow popUp = popupWindowsort(v, subproductList);
                popUp.showAsDropDown(v, 0, 0);

            }
            if (v == dropcat) {



                getCatalog();
                PopupWindow popUp = popupWindowsort(v, cataloglist1);
                popUp.setHeight(380);
                popUp.showAsDropDown(v,0,-500);
            }
            if (v == AddmainData) {

                String floortype = flooreType.getText().toString();
                if (TextUtils.isEmpty(floortype)) {
                    Toast.makeText(getContext(), "Enter Main Type!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean productPresent = false;

                try {

                    for (int i = 0; i <= mainproductlist.size(); i++) {
                        String mainpro = (mainproductlist.get(i));


                        if (floortype.equals(mainpro)) {
                            productPresent = true;
                            break;
                        }

                    }
                } catch (Exception e) {
                }


                if (!productPresent) {

                    MainProducts mainProducts = new MainProducts(floortype);
                    String uploadId = mDatabaseRefSub.push().getKey();
                    mDatabaseRefMain.child(uploadId).setValue(mainProducts);

                    Toast.makeText(getContext(), "Product Added", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getContext(), "Product Name Allready Exist!", Toast.LENGTH_SHORT).show();
                }

            }

        } catch (Exception e) {
        }


        try {

            if (v == AddsubData) {

                String maintype = spinner.getSelectedItem().toString();
                String subtype = SubType.getText().toString();

                if (TextUtils.isEmpty(subtype)) {
                    Toast.makeText(getContext(), "Enter Sub Type!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spinner.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Select Main Product", Toast.LENGTH_SHORT).show();
                    return;
                }


                boolean productPresent = false;

                try {

                    for (int i = 0; i <= subproductlist.size(); i++) {
                        String subpro = (subproductlist.get(i));


                        if (subtype.equals(subpro)) {
                            productPresent = true;
                            break;
                        }

                    }
                } catch (Exception e) {
                }

                if (!productPresent) {

                    MainSubProducts products = new MainSubProducts(maintype, subtype);

                    String uploadId = mDatabaseRefSub.push().getKey();
                    mDatabaseRefSub.child(uploadId).setValue(products);

                    Toast.makeText(getContext(), "Product Added", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getContext(), "Product Name Allready Exist!", Toast.LENGTH_SHORT).show();
                }


            }
        } catch (Exception e) {
        }

        try {

            if (v == buttonaddcatalog) {

                String main = MainCatalog.getSelectedItem().toString();
                String sub = SubCatalog.getSelectedItem().toString();
                String floortype = etcatalogname.getText().toString();
                if (TextUtils.isEmpty(floortype)) {
                    Toast.makeText(getContext(), "Enter Catalog Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean productPresent = false;

                try {

                    for (int i = 0; i <= cataloglist.size(); i++) {
                        String mainpro = (cataloglist.get(i));


                        if (floortype.equalsIgnoreCase(mainpro)) {
                            productPresent = true;
                            break;
                        }

                    }
                } catch (Exception e) {
                }


                if (!productPresent) {

                    MainCatalog mainProducts = new MainCatalog(floortype, main, sub);
                    String uploadId = mDatabaseRefcatalog.push().getKey();
                    mDatabaseRefcatalog.child(uploadId).setValue(mainProducts);

                    Toast.makeText(getContext(), "Catalog Added", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getContext(), "Catalog Allready Exist!", Toast.LENGTH_SHORT).show();
                }

            }

        } catch (Exception e) {
        }


    }

    private void getCatalog() {
        mDatabaseRefcatalog.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cataloglist1.clear();
                for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                    MainCatalog mainsubproducts = mainproductSnapshot.getValue(MainCatalog.class);

                    String main = MainCatalog.getSelectedItem().toString();
                    String sub = SubCatalog.getSelectedItem().toString();
                    if (mainsubproducts.getMainpro().equalsIgnoreCase(main) &&
                            mainsubproducts.getSubpro().equalsIgnoreCase(sub)) {
                        cataloglist1.add(mainsubproducts.getMaincat());
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private PopupWindow popupWindowsort(View v, List catalog) {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, catalog);
        // the drop down list is a list view
        ListView listViewSort = new ListView(getContext());
        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter);

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // some other visual settings for popup window
        popupWindow.update();
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the listview as popup content
        popupWindow.setContentView(listViewSort);

        return popupWindow;
    }

}
