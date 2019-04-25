package com.smartloan.smtrick.samarapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smartloan.smtrick.samarapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.smartloan.smtrick.samarapp.Constants.DATABASE_PATH_UPLOADS;

public class Upload_Image_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PICK_IMAGE = 1002;
    private static final String YOUR_SERVER_KEY = "AAAACaJGpDg:APA91bGx2DKBqQdf8rhESuBr0ZF17u7hxWOtoEZktHNWMEta70bBG5Knpx7l43HNZg9_0TuJnWLmDaPLbQ5LelKB_HPTaMfb-L6PRqbjqgor4ssXVe6sVftEon7tIJrs3DVs7LIB56mp";
    private static final String FCM_TOKEN =  FirebaseInstanceId.getInstance().getToken(); ;
    Bitmap imageBitmap;
    String image;

    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    //view objects
    private Button buttonChoose;
    private Button buttonUpload;
    private EditText editTextName;
    private Spinner mainspinner;
    private ImageView imageView;
    private Spinner Subspinner;
    private EditText Idescription;
    private ProgressBar spinnerprogress;

    //uri to store file
    private Uri filePath;

    private List<String> mainproductList;
    private List<String> subproductList;

    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseMainProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_imageupload);


        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
       // editTextName = (EditText) findViewById(R.id.editText);
        mainspinner = (Spinner) findViewById(R.id.spinner_mainProduct);
        Subspinner = (Spinner) findViewById(R.id.spinner_Subproduct);
        Idescription = (EditText) findViewById(R.id.description);
        spinnerprogress = (ProgressBar) findViewById(R.id.spinner_progress);
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        mainproductList = new ArrayList<>();
        subproductList = new ArrayList<>();

       // Boolean per = isStoragePermissionGranted();
        spinnervalue();
        subspinnervalue();
            buttonChoose.setOnClickListener(this);
            buttonUpload.setOnClickListener(this);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            filePath = data.getData();
//            try {
//               bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
//               imageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//           // CropImage.activity(filePath).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(getActivity());
//        }

//        try {
//
//            if (requestCode != CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                if (resultCode == RESULT_OK) {
//                    img = result.getUri();
//                    Bitmap bitmap = null;
//
//                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), img);
//
//                    imageView.setImageURI(img);
//
//
//                }else if (resultCode == CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
//                    Exception error = result.getError();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case REQUEST_PICK_IMAGE:

//                        String path = "file:///storage/emulated/0/Download/Image-5312.jpg";
                        if (data.hasExtra("image_path")) {
                            Uri imagePath = Uri.parse(data.getStringExtra("image_path"));

                            String str = imagePath.toString();
                            String whatyouaresearching = str.substring(0, str.lastIndexOf("/"));
                            image = whatyouaresearching.substring(whatyouaresearching.lastIndexOf("/") + 1, whatyouaresearching.length());
                            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                            File file = new File(root, image);
                            filePath = Uri.fromFile(file);

                            setImage(filePath);
                        } else {
                            Toast.makeText(this, "no image", Toast.LENGTH_SHORT).show();
                        }

                        break;
                }
            }
        } else {

            System.out.println("Failed to load image");
        }

    }

//    public boolean isPermissionGranted() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return true;
//        }
//    }

    private void setImage(Uri imagePath) {

        imageView.setImageURI(imagePath);

    }

//    private Bitmap getImageFromStorage(String path) {
//
//        try {
//
//            File f = new File(path);
//            // First decode with inJustDecodeBounds=true to check dimensions
//            final BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = false;
//            // Calculate inSampleSize
//            options.inSampleSize = calculateInSampleSize(options, 512, 512);
//            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
//            return b;
//
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//        }
//        return null;
//    }

//    private int calculateInSampleSize(
//
//            BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//        if (height > reqHeight || width > reqWidth) {
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while ((halfHeight / inSampleSize) > reqHeight
//                    && (halfWidth / inSampleSize) > reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//        return inSampleSize;
//    }


    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(Upload_Image_Activity.this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            final StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadurl = uri.toString();

                                    String mainitem = mainspinner.getSelectedItem().toString();
                                    String subnitem = Subspinner.getSelectedItem().toString();
                                    Upload upload = new Upload(mainitem, subnitem,
                                            Idescription.getText().toString().trim(), editTextName.getText().toString().trim(), downloadurl,
                                            FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS).push().getKey());

                                    String uploadId = mDatabase.push().getKey();
                                    mDatabase.child(uploadId).setValue(upload);
                                  //  deleteFile();
                                    sendFCMPush();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
            Toast.makeText(this, "Please Select a file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonChoose) {

            // showFileChooser();
            pickImage();

        } else if (view == buttonUpload) {
            if (mainspinner.getSelectedItem().toString().trim().equals("")) {
                Toast.makeText(this, "Main Product required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Subspinner.getSelectedItem().toString().trim().equals("")) {
                Toast.makeText(this, "Sub Product required", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = editTextName.getText().toString().trim();
            String DESC = Idescription.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Enter Name!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(DESC)) {
                Toast.makeText(this, "Enter Description!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (imageView.getDrawable() == null) {
                Toast.makeText(this, "Image Required!", Toast.LENGTH_SHORT).show();
                return;
            }
            uploadFile();

            //  deleteFile();
        }
    }

    private void deleteFile() {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File file = new File(root, image);
        if (file.exists()) {
            deleteFile(image);
            Toast.makeText(this, "File deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File does not exists", Toast.LENGTH_SHORT).show();
        }
    }

    public void pickImage() {

        startActivityForResult(new Intent(this, ImagePickerActivity.class), REQUEST_PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        }
    }


    public void spinnervalue() {

        mDatabaseMainProduct = FirebaseDatabase.getInstance().getReference("MainProducts");
        mDatabaseMainProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot mainproduct2Snapshot : dataSnapshot.getChildren()) {

                    MainProducts mainProducts2 = mainproduct2Snapshot.getValue(MainProducts.class);
                    mainproductList.add(mainProducts2.getMainpro());
                }
                ArrayAdapter<String> mainadapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, mainproductList);
                mainadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mainspinner.setAdapter(mainadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void subspinnervalue() {

        mainspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerprogress.setVisibility(View.VISIBLE);
                String mainspinnerValue = mainspinner.getSelectedItem().toString();

                Query query = FirebaseDatabase.getInstance().getReference("SubProducts")
                        .orderByChild("mainproduct")
                        .equalTo(mainspinnerValue);

                query.addValueEventListener(valueEventListener);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                subproductList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MainSubProducts subproducts2 = snapshot.getValue(MainSubProducts.class);
                    subproductList.add(subproducts2.getSubproduct());
                }
                // subCatalogAdapter.notifyDataSetChanged();
            }

            ArrayAdapter<String> subadapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, subproductList);
            subadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Subspinner.setAdapter(subadapter);
            spinnerprogress.setVisibility(View.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }




    private void sendFCMPush() {

        final String SERVER_KEY = YOUR_SERVER_KEY;
        String msg = "New Product Added";
        String title = "Samar Floor's Notification";
        String token = FCM_TOKEN;

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();
            objData = new JSONObject();

            objData.put("body", msg);
            objData.put("title", title);
            objData.put("sound", "default");
            objData.put("icon", "icon_name"); //   icon_name
            objData.put("tag", token);
            objData.put("priority", "high");

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);

            obj.put("to", token);
            //obj.put("priority", "high");

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("return here>>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.FCM_PUSH_URL, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("True", response + "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("False", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

}
