package com.smartloan.smtrick.samarapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Multiple_images_Activity extends AppCompatActivity implements View.OnClickListener {

    Button Select;
    RecyclerView imagesRecyclerView;
    private static final int RESULT_LOAD_IMAGE = 1;

    private List<String> fileNameList;
    private List<Uri> fileDoneList;

    private UploadListAdapter uploadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_images_);

        Select = (Button) findViewById(R.id.imageSelect);
        imagesRecyclerView = (RecyclerView) findViewById(R.id.imageRecyclerView);
        fileDoneList  = new ArrayList<>();

        Select.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){

            if(data.getClipData() != null){

                int totalItemsSelected = data.getClipData().getItemCount();

                for(int i = 0; i < totalItemsSelected; i++){

                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    fileDoneList.add( data.getClipData().getItemAt(i).getUri());

                    String fileName = getFileName(fileUri);

//                    fileNameList.add(fileName);
                 //   fileDoneList.add("uploading");
//                    uploadListAdapter.notifyDataSetChanged();


//                    StorageReference fileToUpload = mStorage.child("Images").child(fileName);
//
//                    final int finalI = i;
//                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            fileDoneList.remove(finalI);
//                            fileDoneList.add(finalI, "done");
//
//                            uploadListAdapter.notifyDataSetChanged();
//
//                        }
//                    });

                }
                uploadListAdapter = new UploadListAdapter(getApplicationContext(),fileDoneList);
                imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
                imagesRecyclerView.setHasFixedSize(true);
                imagesRecyclerView.setAdapter(uploadListAdapter);

                //Toast.makeText(MainActivity.this, "Selected Multiple Files", Toast.LENGTH_SHORT).show();

            } else if (data.getData() != null){

                Toast.makeText(Multiple_images_Activity.this, "Selected Single File", Toast.LENGTH_SHORT).show();

            }

        }

    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
