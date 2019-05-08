package com.smartloan.smtrick.samarapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by akshayejh on 19/12/17.
 */

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ViewHolder>{

  //  public List<String> fileNameList;
    public List<Uri> fileDoneList;
    Context mContext;

    public UploadListAdapter(Context fileNameList, List<Uri> fileDoneList){

        this.mContext = fileNameList;
        this.fileDoneList = fileDoneList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       // String fileName = fileNameList.get(position);
       // holder.fileNameView.setText(fileName);

        Uri fileDone = fileDoneList.get(position);

        Glide.with(mContext).load(fileDone).placeholder(R.drawable.loading).into(holder.fileDoneView);

//        if(fileDone.equals("uploading")){
//
//            holder.fileDoneView.setImageResource(R.mipmap.progress);
//
//        } else {
//
//            holder.fileDoneView.setImageResource(R.mipmap.checked);
//
//        }

    }

    @Override
    public int getItemCount() {
        return fileDoneList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView fileNameView;
        public ImageView fileDoneView;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

//            fileNameView = (TextView) mView.findViewById(R.id.upload_filename);
            fileDoneView = (ImageView) mView.findViewById(R.id.upload_icon);


        }

    }

}
