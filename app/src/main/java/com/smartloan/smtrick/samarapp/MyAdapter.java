package com.smartloan.smtrick.samarapp;

import android.app.ActivityOptions;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Upload> uploads;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabase;
    InvoiceRepository invoiceRepository;

    public MyAdapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_images, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Upload upload = uploads.get(position);

        //  holder.textViewName.setText(upload.getName());
        holder.textViewdesc.setText(upload.getDesc());

        Glide.with(context).load(upload.getUrl()).placeholder(R.drawable.loading).into(holder.imageView);

        holder.imagecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared = new Intent(holder.imagecard.getContext(), sharedtransitionActivity.class);
                shared.putExtra("url", upload.getUrl());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in, R.anim.slide_out);
                holder.imagecard.getContext().startActivity(shared, options.toBundle());


            }
        });

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = holder.textViewdesc.getText().toString();
                String mainpro = upload.getMainproduct();
                String subpro = upload.getSubproduct();
                String proid = upload.getPoductId();
                String name = upload.getName();

                upload.setDesc(desc);
                upload.setMainproduct(mainpro);
                upload.setSubproduct(subpro);
                upload.setPoductId(proid);
                upload.setName(name);
                updateid(upload.getPoductId(), upload.getUpdateLeedMap());

            }
        });

        holder.imagecard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // custom dialog
                final Dialog dialog = new Dialog(holder.imagecard.getRootView().getContext());
                dialog.setContentView(R.layout.customdialogbox);
                //dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text2);
                text.setText(upload.getName());


                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.imagecard.getContext());

                        builder.setMessage("Do you want to delete the record")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        ///////3//////
                                        try {

                                            String item1 = upload.getPoductId().toString();
                                            mStorage = FirebaseStorage.getInstance();
                                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                            Query applesQuery1 = ref1.child("NewImage").orderByChild("poductId").equalTo(item1);

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

                                            StorageReference imageRef = mStorage.getReferenceFromUrl(upload.getUrl());
                                            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            Toast.makeText(holder.imagecard.getContext(), "Delete Product Successfully", Toast.LENGTH_SHORT).show();
                                            uploads.clear();


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
                        alert.setTitle("Delete Product!");
                        alert.show();

                        dialog.dismiss();
                    }

                });

                dialog.show();


                return true;
            }
        });

    }

    private void updateid(String poductId, Map<String, Object> updateLeedMap) {
        invoiceRepository = new InvoiceRepositoryImpl();
        invoiceRepository.updateLeed(poductId, updateLeedMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {

                Toast.makeText(context,"updated",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Object object) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //  public TextView textViewName;
        public EditText textViewdesc;
        public ImageView imageView;
        public CardView imagecard;
        public Button Edit;


        public ViewHolder(View itemView) {
            super(itemView);

            //  textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewdesc = (EditText) itemView.findViewById(R.id.textViewdescription);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imagecard = (CardView) itemView.findViewById(R.id.cardimage);
            Edit = (Button) itemView.findViewById(R.id.Edit_desc);
        }
    }
}
