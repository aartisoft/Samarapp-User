package com.smartloan.smtrick.samarapp_user;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {

    private List<String> list;


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
        final String catname = list.get(position);
        holder.catalogname.setText(catname);

//        if (position % 2 == 0) {
//            //holder.catalogname.setBackgroundColor(Color.parseColor("#4c4c4c"));
//            holder.cardLayout.setBackgroundResource(R.color.card_gray);
//        } else {
//            //holder.catalogname.setBackgroundColor(Color.parseColor("#ff6861"));
//            holder.cardLayout.setBackgroundResource(R.color.card_red);
//        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do on click stuff
                String item = list.get(position).toString();
                Intent intent = new Intent(holder.catalogname.getContext(), SubCatalogActivity.class);
                intent.putExtra("itemName", item);
                holder.catalogname.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView catalogname;
        public CardView cardView;
        public RelativeLayout cardLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            catalogname = (TextView) itemView.findViewById(R.id.catalog_name);
            cardView = (CardView) itemView.findViewById(R.id.card);
            cardLayout = (RelativeLayout) itemView.findViewById(R.id.card_layout);

        }
    }
}
