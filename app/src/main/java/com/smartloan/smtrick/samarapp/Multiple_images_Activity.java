package com.smartloan.smtrick.samarapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Multiple_images_Activity extends AppCompatActivity implements View.OnClickListener {

    Button Select;
    RecyclerView imagesRecyclerView;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_CROP_IMAGE = 2342;
    String image;
    private Uri filePath;

    String image1;
    private Uri filePath1;

    private List<String> fileNameList;
    private List<Uri> fileDoneList;

    private UploadListAdapter uploadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_images_);

        Select = (Button) findViewById(R.id.imageSelect);
        imagesRecyclerView = (RecyclerView) findViewById(R.id.imageRecyclerView);
        fileDoneList = new ArrayList<>();

        Select.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if (data.getClipData() != null) {

                int totalItemsSelected = data.getClipData().getItemCount();

                for (int i = 0; i < totalItemsSelected; i++) {

                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    fileDoneList.add(data.getClipData().getItemAt(i).getUri());

                    String fileName = getFileName(fileUri);

                }
                uploadListAdapter = new UploadListAdapter(getApplicationContext(), fileDoneList);
                imagesRecyclerView.setLayoutManager(new LinearLayoutManager(Multiple_images_Activity.this, LinearLayoutManager.HORIZONTAL, true));
                imagesRecyclerView.setHasFixedSize(true);
                imagesRecyclerView.setAdapter(uploadListAdapter);

            } else if (data.getData() != null) {

                Toast.makeText(Multiple_images_Activity.this, "Selected Single File", Toast.LENGTH_SHORT).show();

            }

        } else if (requestCode == REQUEST_CROP_IMAGE) {
            System.out.println("Image crop success :" + data.getStringExtra(CropImageActivity.CROPPED_IMAGE_PATH));
            String imagePath = new File(data.getStringExtra(CropImageActivity.CROPPED_IMAGE_PATH), "image.jpg").getAbsolutePath();
            String original = new File(data.getStringExtra(CropImageActivity.ORIGINAL_IMAGE_PATH), "image.jpg").getAbsolutePath();

            //   Uri imagePath1 = Uri.parse(data.getStringExtra("image_path"));
            String str = imagePath.toString();
            String whatyouaresearching = str.substring(0, str.lastIndexOf("/"));
            image = whatyouaresearching.substring(whatyouaresearching.lastIndexOf("/") + 1, whatyouaresearching.length());
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            File file = new File(root, image);
            filePath = Uri.fromFile(file);


            String str1 = original.toString();
            String whatyouaresearching1 = str1.substring(0, str1.lastIndexOf("/"));
            String search = whatyouaresearching1.substring(1,whatyouaresearching1.length());
            int i = search.indexOf("/");
            String main = search.substring(0, i) + "/" + search.substring(i, search.length());
            Uri u = Uri.parse(main);

            int pos = fileDoneList.indexOf(u);

            fileDoneList.remove(pos);
            fileDoneList.add(filePath);

            uploadListAdapter = new UploadListAdapter(getApplicationContext(), fileDoneList);
            imagesRecyclerView.setLayoutManager(new LinearLayoutManager(Multiple_images_Activity.this, LinearLayoutManager.HORIZONTAL, true));
            imagesRecyclerView.setHasFixedSize(true);
            imagesRecyclerView.setAdapter(uploadListAdapter);
//            Intent result = new Intent();
//            result.putExtra("image_path", imagePath);
//            setResult(Activity.RESULT_OK, result);
//            finish();
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
