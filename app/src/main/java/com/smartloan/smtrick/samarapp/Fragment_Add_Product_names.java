package com.smartloan.smtrick.samarapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Add_Product_names extends Fragment implements View.OnClickListener {

    private EditText flooreType;
    private EditText SubType;
    private Spinner spinner;

    private DatabaseReference mDatabaseRefMain;
    private DatabaseReference mDatabaseRefSub;

    private Button AddsubData;
    private Button AddmainData;
    private List<String> mainproductlist;
    private List<String> subproductlist;
    MainProducts mainProducts;
    private DatabaseReference mDatabaseRefcatalog;


    private EditText etcatalogname;
    private Button buttonaddcatalog;
    private List<String> catloglist;

    String mainproduct;

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
        mainproductlist = new ArrayList<>();
        subproductlist = new ArrayList<>();

        mDatabaseRefSub = FirebaseDatabase.getInstance().getReference("SubProducts");
        mDatabaseRefMain = FirebaseDatabase.getInstance().getReference("MainProducts");
        mDatabaseRefcatalog = FirebaseDatabase.getInstance().getReference("MainCatalogs");


        etcatalogname = (EditText) view.findViewById(R.id.edit_catelogname);
        buttonaddcatalog = (Button) view.findViewById(R.id.Add_catalogname);

        try {

            mDatabaseRefMain.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                        MainProducts mainProducts = mainproductSnapshot.getValue(MainProducts.class);


                        mainproductlist.add(mainProducts.getMainpro());


                    }
                    try {
                        ArrayAdapter<String> mainproadapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, mainproductlist);

                        mainproadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(mainproadapter);
                    }catch (Exception e){

                    }
                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError){

                    }
                });


            mDatabaseRefSub.addValueEventListener(new

                ValueEventListener() {
                    @Override
                    public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                        for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                            MainSubProducts mainsubproducts = mainproductSnapshot.getValue(MainSubProducts.class);


                            subproductlist.add(mainsubproducts.getSubproduct());

                        }

                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError){

                    }
                });


            AddsubData.setOnClickListener(this);
            AddmainData.setOnClickListener(this);
            buttonaddcatalog.setOnClickListener(this);


            } catch(Exception e){
            }


            return view;


        }


        @Override
        public void onClick (View v){

            try {

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

                        // progressBar.setVisibility(View.VISIBLE);
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

                    String floortype = etcatalogname.getText().toString();
                    if (TextUtils.isEmpty(floortype)) {
                        Toast.makeText(getContext(), "Enter Catalog Name!", Toast.LENGTH_SHORT).show();
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

                        MainCatalog mainProducts = new MainCatalog(floortype);
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


    }
