package com.smartloan.smtrick.samarapp_user;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
//                Intent shared = new Intent(holder.imagecard.getContext(), sharedtransitionActivity.class);
//                shared.putExtra("url", upload.getUrl());
//                ActivityOptions options = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in, R.anim.slide_out);
//                holder.imagecard.getContext().startActivity(shared, options.toBundle());

                final Dialog dialog = new Dialog(holder.imagecard.getContext());
                dialog.setContentView(R.layout.customdialogboximagedisplay);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                final ImageView image = (ImageView) dialog.findViewById(R.id.floorimage);
                Glide.with(holder.imagecard.getContext())
                        .load(upload.getUrl())
                        .placeholder(R.drawable.loading)
                        .into(image);

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            }
        });

    }

    private void updateid(String poductId, Map<String, Object> updateLeedMap) {
        invoiceRepository = new InvoiceRepositoryImpl();
        invoiceRepository.updateLeed(poductId, updateLeedMap, new CallBack() {
            @Override
            public void onSuccess(Object object) {

                //  Toast.makeText(context,"updated",Toast.LENGTH_SHORT).show();
                Toast toast = Toast.makeText(context, "updated", Toast.LENGTH_SHORT);
                View view = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                toast.show();

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

        public EditText textViewdesc;
        public ImageView imageView;
        public CardView imagecard;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewdesc = (EditText) itemView.findViewById(R.id.textViewdescription);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imagecard = (CardView) itemView.findViewById(R.id.cardimage);
        }
    }
}
