package com.smartloan.smtrick.samarapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainCatalog_adapter extends RecyclerView.Adapter<MainCatalog_adapter.ViewHolder> {

    Context mContext;
    List<Upload> catalogList;

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
