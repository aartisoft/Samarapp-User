package com.smartloan.smtrick.samarapp_user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

public class Crop_Selected_Activity extends AppCompatActivity {

    private static final int REQUEST_CROP_IMAGE = 2342;

    ImageView image;
    Uri uri1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop__selected_);
        image = (ImageView) findViewById(R.id.selectedImage);
        Intent i =getIntent();
        String uri = i.getStringExtra("url");
        uri1 = Uri.parse(uri);

        Glide.with(getApplicationContext()).load(uri1).placeholder(R.drawable.loading).into(image);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Image crop success :"+data.getStringExtra(CropImageActivity.CROPPED_IMAGE_PATH));
        String imagePath = new File(data.getStringExtra(CropImageActivity.CROPPED_IMAGE_PATH), "image.jpg").getAbsolutePath();
//        Intent result = new Intent();
//        result.putExtra("image_path", imagePath);
//        setResult(Activity.RESULT_OK, result);
//        finish();
        Glide.with(getApplicationContext()).load(imagePath).placeholder(R.drawable.loading).into(image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, CropImageActivity.class);
                intent.putExtra(CropImageActivity.EXTRA_IMAGE_URI, uri1.toString());
                startActivityForResult(intent, REQUEST_CROP_IMAGE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
