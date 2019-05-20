package com.smartloan.smtrick.samarapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class MainCatalog_adapter extends RecyclerView.Adapter<MainCatalog_adapter.ViewHolder> {

    Context mContext;
    List<Upload> catalogList;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabase;
    String key2;

    public MainCatalog_adapter(Context applicationContext, List<Upload> uploads) {
        this.mContext = applicationContext;
        this.catalogList = uploads;

    }

    @NonNull
    @Override
    public MainCatalog_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogsublist, parent, false);
        MainCatalog_adapter.ViewHolder viewHolder = new MainCatalog_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainCatalog_adapter.ViewHolder holder, final int position) {

        final String subcatname = catalogList.get(position).getName();
        final String key = catalogList.get(position).getPoductId();
        holder.catalogSubname.setText(subcatname);

        if (position % 2 == 0) {
            holder.catalogSubname.setBackgroundResource(R.drawable.samarfloorred);
        } else {
            holder.catalogSubname.setBackgroundResource(R.drawable.samarfloorred);
        }

        holder.subcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do on click stuff
//                Toast.makeText(holder.catalogSubname.getContext(), sublist.get(position),
//                        Toast.LENGTH_SHORT).show();
                Intent subintent = new Intent(holder.catalogSubname.getContext(), ShowImagesActivity.class);
                subintent.putExtra("mianproduct", catalogList.get(position).getMainproduct());
                subintent.putExtra("subproduct", catalogList.get(position).getSubproduct());
                subintent.putExtra("catname", catalogList.get(position).getName());
                holder.catalogSubname.getContext().startActivity(subintent);
            }
        });


        holder.subcardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(holder.subcardView.getRootView().getContext());
                dialog.setContentView(R.layout.customdialogbox);

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text2);
                text.setText(catalogList.get(position).getName());

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

                Button dialogEditButton = (Button) dialog.findViewById(R.id.dialogButtonEDIT);

                dialogEditButton.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(holder.subcardView.getContext());
                        final EditText edittext = new EditText(holder.subcardView.getContext());

//                        alert.setTitle(Html.fromHtml("<font color='#d10101'>Edit Catalog Name</font>"));
                        TextView title = new TextView(holder.subcardView.getContext());
// You Can Customise your Title here
                        title.setText("Edit Catalog Name");
                        title.setPadding(10, 10, 10, 10);
                        title.setGravity(Gravity.CENTER);
                        title.setTextColor(Color.RED);
                        title.setTextSize(20);
                        alert.setCustomTitle(title);

                        edittext.setText(subcatname);

                        alert.setView(edittext);

                        alert.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                Query query4 = FirebaseDatabase.getInstance().getReference("NewImage")
                                        .orderByChild("name")
                                        .equalTo(subcatname);
                                Query query6 = FirebaseDatabase.getInstance().getReference("MainCatalogs")
                                        .orderByChild("maincat")
                                        .equalTo(subcatname);

                                query4.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {
                                            Upload upload = mainproductSnapshot.getValue(Upload.class);
                                            if (upload.getMainproduct().equalsIgnoreCase(catalogList.get(position).getMainproduct()) &&
                                                    upload.getSubproduct().equalsIgnoreCase(catalogList.get(position).getSubproduct())) {
                                                key2 = mainproductSnapshot.getKey();
                                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                                mDatabase.child("NewImage").child(key2).child("name").setValue(edittext.getText().toString());
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                query6.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {
                                            key2 = mainproductSnapshot.getKey();
                                            mDatabase = FirebaseDatabase.getInstance().getReference();
                                            mDatabase.child("MainCatalogs").child(key2).child("maincat").setValue(edittext.getText().toString());
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });

                        alert.setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog1, int whichButton) {
                                // what ever you want to do with No option.
                                dialog.cancel();
                            }
                        });

                        alert.show();

                    }
                });

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(holder.subcardView.getContext());

                        builder.setMessage("Do you want to delete the record")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        try {

//                                            String item1 = upload.getName().toString();
                                            mStorage = FirebaseStorage.getInstance();
                                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                            Query applesQuery1 = ref1.child("NewImage").orderByChild("poductId").equalTo(key);

                                            applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                        appleSnapshot.getRef().removeValue();

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Log.e(TAG, "onCancelled", databaseError.toException());
                                                }
                                            });

                                            StorageReference imageRef = mStorage.getReferenceFromUrl(catalogList.get(position).getUrl());
                                            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                                                }
                                            });

//                                            Toast.makeText(holder.imagecard.getContext(), "Delete Product Successfully", Toast.LENGTH_SHORT).show();
//                                            uploads.clear();

                                        } catch (Exception e) {
                                        }

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Delete Product");
                        alert.show();

                        dialog.dismiss();
                    }

                });

                dialog.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return catalogList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView catalogSubname;
        public CardView subcardView;

        public ViewHolder(View itemView) {
            super(itemView);

            catalogSubname = (TextView) itemView.findViewById(R.id.subcatalog_name);
            subcardView = (CardView) itemView.findViewById(R.id.newcard);
        }
    }
}
