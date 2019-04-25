package com.smartloan.smtrick.samarapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.smartloan.smtrick.samarapp.R;

import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {

    private Context context;
    private List<String> list;
    String item;
    private FirebaseStorage mStorage;

    public CatalogAdapter(List<String> catalogList) {
        list = catalogList;
    }


    @Override
    public CatalogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cataloglist, parent, false);
        CatalogAdapter.ViewHolder viewHolder = new CatalogAdapter.ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CatalogAdapter.ViewHolder holder, final int position) {
        String catname = list.get(position);
        holder.catalogname.setText(catname);

        if (position % 2 == 0) {
            //holder.catalogname.setBackgroundColor(Color.parseColor("#4c4c4c"));
            holder.catalogname.setBackgroundResource(R.drawable.samarfloorgray);
        } else {
            //holder.catalogname.setBackgroundColor(Color.parseColor("#ff6861"));
            holder.catalogname.setBackgroundResource(R.drawable.samarfloorgray);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do on click stuff
               // Toast.makeText(holder.catalogname.getContext(), list.get(position), Toast.LENGTH_SHORT).show();
                String item = list.get(position).toString();
                Intent intent = new Intent(holder.catalogname.getContext(), SubCatalogActivity.class);
                intent.putExtra("itemName", item);
                holder.catalogname.getContext().startActivity(intent);

            }
        });


        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                try {

                // custom dialog
                final Dialog dialog1 = new Dialog(holder.cardView.getRootView().getContext());
                dialog1.setContentView(R.layout.customdialogbox);
                //dialog.setTitle("Title...");
                String item = list.get(position).toString();
                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog1.findViewById(R.id.text2);
                text.setText(item);


                Button dialogButton = (Button) dialog1.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(holder.cardView.getContext());

                        builder.setMessage("Do you want to delete the Product")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                        String item1 = list.get(position).toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("MainProducts").orderByChild("mainpro").equalTo(item1);


                        try {

                           applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {
                                   for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

//                                        try {
//                                            //////////2/////
//                                            String item2 = list.get(position).toString();
//                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//                                            Query applesQuery = ref.child("SubProducts").orderByChild("mainproduct").equalTo(item2);
//
//                                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
//
//                                                        ///////3//////
//                                                        try {
//
//                                                            String item3 = list.get(position).toString();
//                                                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
//                                                            Query applesQuery1 = ref1.child("NewImage").orderByChild("mainproduct").equalTo(item3);
//
//                                                            applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                @Override
//                                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
//
//                                                                        Upload upload = appleSnapshot.getValue(Upload.class);
//                                                                        try {
//                                                                            mStorage = FirebaseStorage.getInstance();
//                                                                            StorageReference imageRef = mStorage.getReferenceFromUrl(upload.getUrl());
//                                                                            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                                @Override
//                                                                                public void onSuccess(Void aVoid) {
//                                                                                    //   Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
//
//                                                                                }
//                                                                            });
//                                                                        }catch (Exception e){
//
//                                                                        }
//                                                                        appleSnapshot.getRef().removeValue();
//
//                                                                    }
//                                                                }
//
//                                                                @Override
//                                                                public void onCancelled(DatabaseError databaseError) {
//                                                                    Log.e(TAG, "onCancelled", databaseError.toException());
//                                                                }
//                                                            });
//
//                                                            appleSnapshot.getRef().removeValue();
//
//                                                        } catch (Exception e) {
//                                                            Toast.makeText(holder.cardView.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//                                                    Log.e(TAG, "onCancelled", databaseError.toException());
//                                                }
//                                            });
//
//                                            appleSnapshot.getRef().removeValue();
//                                            Toast.makeText(holder.catalogname.getContext(), "Delete Product Successfully", Toast.LENGTH_SHORT).show();
//                                            list.clear();
//
//                                        } catch (Exception e) {
//                                        }
                                       appleSnapshot.getRef().removeValue();
                                       Toast.makeText(holder.catalogname.getContext(), "Delete Product Successfully", Toast.LENGTH_SHORT).show();
                                       list.clear();
                                 }


                               }

                               @Override
                               public void onCancelled(DatabaseError databaseError) {
                                    Log.e(TAG, "onCancelled", databaseError.toException());
                               }
                           });

                       }catch (Exception e){}

                        dialog.dismiss();

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

                        Button btnPositive = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button btnNegative = alert.getButton(AlertDialog.BUTTON_NEGATIVE);

                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
                        layoutParams.weight = 10;
                        btnPositive.setLayoutParams(layoutParams);
                        btnNegative.setLayoutParams(layoutParams);


                    }
                });

                dialog1.show();


                }catch (Exception e){}

                return true;

            }
        });

//        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//               // AlertDialog.Builder alertbox = new AlertDialog.Builder(getParent().getApplicationContext());
//                AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.cardView.getRootView().getContext());
//                alertbox.setMessage(list.get(position));
//                alertbox.setTitle("Delete Catalog");
//              // alertbox.setIcon(R.drawable.album1);
//
//                alertbox.setNeutralButton("DELETE",
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface arg0,
//                                                int arg1) {
//                                String item = list.get(position).toString();
//                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//                                Query applesQuery = ref.child("MainProducts").orderByChild("mainpro").equalTo(item);
//
//
//                                try {
//
//
//                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//
//
//                                            //////////2/////
//                                            String item = list.get(position).toString();
//                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//                                            Query applesQuery = ref.child("SubProducts").orderByChild("mainproduct").equalTo(item);
//
//                                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//
//                                                        ///////3//////
//
//
//
//
//                                                        String item1 = list.get(position).toString();
//                                                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
//                                                        Query applesQuery1 = ref1.child("NewImage").orderByChild("mainproduct").equalTo(item1);
//
//                                                        applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                            @Override
//                                                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                                                                    appleSnapshot.getRef().removeValue();
//
//                                                                }
//                                                            }
//
//                                                            @Override
//                                                            public void onCancelled(DatabaseError databaseError) {
//                                                                Log.e(TAG, "onCancelled", databaseError.toException());
//                                                            }
//                                                        });
//
//                                                        appleSnapshot.getRef().removeValue();
//
//                                                        }
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//                                                    Log.e(TAG, "onCancelled", databaseError.toException());
//                                                }
//                                            });
//
//                                             appleSnapshot.getRef().removeValue();
//                                           Toast.makeText(holder.catalogname.getContext(),"Delete Product Successfully", Toast.LENGTH_SHORT).show();
//
//
//
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//                                        Log.e(TAG, "onCancelled", databaseError.toException());
//                                    }
//                                });
//
//                                }catch (Exception e){}
//                            }
//
//                                });
//
//
//
//                alertbox.show();
//
//
//                return true;// returning true instead of false, works for me
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView catalogname;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            catalogname = (TextView) itemView.findViewById(R.id.catalog_name);
            cardView = (CardView) itemView.findViewById(R.id.card);
        }
    }
}