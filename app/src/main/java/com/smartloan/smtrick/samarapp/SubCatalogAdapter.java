package com.smartloan.smtrick.samarapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smartloan.smtrick.samarapp.R;

import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class SubCatalogAdapter extends RecyclerView.Adapter<SubCatalogAdapter.ViewHolder> {

    private Context context;
    private List<String> sublist;
    String mainproductname;
    private FirebaseStorage mStorage;

    public SubCatalogAdapter(List<String> catalogList, String mainproductname) {
        this.mainproductname = mainproductname;
        sublist = catalogList;
    }

    @Override
    public SubCatalogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogsublist, parent, false);
        SubCatalogAdapter.ViewHolder viewHolder = new SubCatalogAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubCatalogAdapter.ViewHolder holder, final int position) {
        final String subcatname = sublist.get(position);
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
                Intent subintent = new Intent(holder.catalogSubname.getContext(), MainCatalog_Activity.class);
                subintent.putExtra("mianproduct", mainproductname);
                subintent.putExtra("subproduct", subcatname);
                holder.catalogSubname.getContext().startActivity(subintent);
            }
        });


        holder.subcardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // custom dialog
                final Dialog dialog1 = new Dialog(holder.subcardView.getRootView().getContext());
                dialog1.setContentView(R.layout.customdialogbox);
                //dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog1.findViewById(R.id.text2);
                text.setText(subcatname);


                Button dialogButton = (Button) dialog1.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.catalogSubname.getContext());

                        builder.setMessage("Do you want to delete the product")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        try {
                                            //////////2/////
                                            String item = sublist.get(position).toString();
                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                            Query applesQuery = ref.child("SubProducts").orderByChild("subproduct").equalTo(item);

                                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                        ///////3//////
                                                        try {

                                                            final String item1 = sublist.get(position).toString();
                                                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                                            Query applesQuery1 = ref1.child("NewImage").orderByChild("subproduct").equalTo(item1);

                                                            applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {

                                                                        Query query = FirebaseDatabase.getInstance().getReference("NewImage").orderByChild("subproduct").equalTo(item1);
                                                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    Upload upload = appleSnapshot.getValue(Upload.class);
                                                                                    try {
                                                                                        mStorage = FirebaseStorage.getInstance();
                                                                                        StorageReference imageRef = mStorage.getReferenceFromUrl(upload.getUrl());
                                                                                        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void aVoid) {
                                                                                                //   Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();

                                                                                            }
                                                                                        });
                                                                                    } catch (Exception e) {

                                                                                    }
                                                                                }

                                                                            }

                                                                            @Override
                                                                            public void onCancelled
                                                                                    (@NonNull DatabaseError
                                                                                             databaseError) {

                                                                            }
                                                                        });

                                                                        appleSnapshot.getRef().removeValue();

                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled
                                                                        (DatabaseError databaseError) {
                                                                    Log.e(TAG, "onCancelled", databaseError.toException());
                                                                }
                                                            });

                                                            appleSnapshot.getRef().removeValue();
                                                            Toast.makeText(holder.catalogSubname.getContext(), "Delete Product Successfully", Toast.LENGTH_SHORT).show();
                                                            dialog1.dismiss();
                                                            sublist.clear();

                                                        } catch (Exception e) {
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError
                                                                                databaseError) {
                                                    Log.e(TAG, "onCancelled", databaseError.toException());
                                                }
                                            });

                                        } catch (Exception e) {
                                        }

                                        dialog.dismiss();

                                    }
                                })
                                .

                                        setNegativeButton("No", new DialogInterface.OnClickListener() {
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

                        Button btnPositive = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button btnNegative = alert.getButton(AlertDialog.BUTTON_NEGATIVE);

                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
                        layoutParams.weight = 10;
                        btnPositive.setLayoutParams(layoutParams);
                        btnNegative.setLayoutParams(layoutParams);


                    }

                });


                dialog1.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return sublist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView catalogSubname;
        public CardView subcardView;

        public ViewHolder(View itemView) {
            super(itemView);

            catalogSubname = (TextView) itemView.findViewById(R.id.subcatalog_name);
            subcardView = (CardView) itemView.findViewById(R.id.newcard);
        }
    }
}