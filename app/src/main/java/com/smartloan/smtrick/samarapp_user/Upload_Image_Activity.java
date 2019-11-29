package com.smartloan.smtrick.samarapp_user;

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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Upload_Image_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PICK_IMAGE = 1002;
    private static final String YOUR_SERVER_KEY = "AAAACaJGpDg:APA91bGx2DKBqQdf8rhESuBr0ZF17u7hxWOtoEZktHNWMEta70bBG5Knpx7l43HNZg9_0TuJnWLmDaPLbQ5LelKB_HPTaMfb-L6PRqbjqgor4ssXVe6sVftEon7tIJrs3DVs7LIB56mp";
    private static final String FCM_TOKEN = FirebaseInstanceId.getInstance().getToken();
    ;
    Bitmap imageBitmap;
    String image;

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_CROP_IMAGE = 2342;

    private List<Uri> fileDoneList;
    private List<Uri> cropedfileDoneList;

    private UploadListAdapter uploadListAdapter;
    RecyclerView imagesRecyclerView, cropedimagesRecyclerView;
    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    //view objects
    private Button buttonChoose;
    private Button buttonUpload;
    //  private EditText editTextName;
    private Spinner mainspinner;
    private ImageView imageView;
    private Spinner Subspinner;
    //    private EditText Idescription;
    private ProgressBar spinnerprogress;

    private Spinner spinner_catlogname;
    private CardView imagecard;

    //uri to store file
    private Uri filePath;

    private List<String> mainproductList;
    private List<String> subproductList;
    private List<String> mainCatalogList;

    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseMainProduct;
    private DatabaseReference mDatabasecatalog;

    LeedRepository leedRepository;
    TextView cropedimages;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_imageupload);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        leedRepository = new LeedRepositoryImpl();

        imagesRecyclerView = (RecyclerView) findViewById(R.id.imageRecyclerView);
        cropedimagesRecyclerView = (RecyclerView) findViewById(R.id.cropedimageRecyclerView);
        fileDoneList = new ArrayList<>();
        cropedfileDoneList = new ArrayList<>();

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        mainspinner = (Spinner) findViewById(R.id.spinner_mainProduct);
        Subspinner = (Spinner) findViewById(R.id.spinner_Subproduct);
        spinner_catlogname = (Spinner) findViewById(R.id.spinner_catlogname);
        cropedimages = (TextView) findViewById(R.id.textcropedimages);

//        Idescription = (EditText) findViewById(R.id.description);
        spinnerprogress = (ProgressBar) findViewById(R.id.spinner_progress);
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        mainproductList = new ArrayList<>();
        subproductList = new ArrayList<>();
        mainCatalogList = new ArrayList<>();

        spinnervalue();
        subspinnervalue();

        int a = 0;
        try {

             a = uploadListAdapter.getItemCount();
        }catch (Exception e){}
        if (a == 0) {
            cropedimages.setText("");
        }else {
//            cropedimages.setText("CROPED IMAGES");
        }

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonChoose) {

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

//            String name = editTextName.getText().toString().trim();
//            String DESC = Idescription.getText().toString().trim();

//            if (TextUtils.isEmpty(name)) {
//                Toast.makeText(this, "Enter Name!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (TextUtils.isEmpty(DESC)) {
//                Toast.makeText(this, "Enter Description!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (imageView.getDrawable() == null) {
//                Toast.makeText(this, "Image Required!", Toast.LENGTH_SHORT).show();
//                return;
//            }
            uploadFile();

        }
    }

    public void pickImage() {

//        startActivityForResult(new Intent(this, ImagePickerActivity.class), REQUEST_PICK_IMAGE);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == RESULT_OK) {
//            if (data != null) {
//                switch (requestCode) {
//                    case REQUEST_PICK_IMAGE:
//
////                        String path = "file:///storage/emulated/0/Download/Image-5312.jpg";
//                        if (data.hasExtra("image_path")) {
//                            Uri imagePath = Uri.parse(data.getStringExtra("image_path"));
//
//                            String str = imagePath.toString();
//                            String whatyouaresearching = str.substring(0, str.lastIndexOf("/"));
//                            image = whatyouaresearching.substring(whatyouaresearching.lastIndexOf("/") + 1, whatyouaresearching.length());
//                            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//                            File file = new File(root, image);
//                            filePath = Uri.fromFile(file);
//
//                            setImage(filePath);
//                        } else {
//                            Toast.makeText(this, "no image", Toast.LENGTH_SHORT).show();
//                        }
//
//                        break;
//                }
//            }
//        } else {
//
//            System.out.println("Failed to load image");
//        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if (data.getClipData() != null) {

                int totalItemsSelected = data.getClipData().getItemCount();

                for (int i = 0; i < totalItemsSelected; i++) {

                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    fileDoneList.add(data.getClipData().getItemAt(i).getUri());

                    //String fileName = getFileName(fileUri);
                }
                uploadListAdapter = new UploadListAdapter(Upload_Image_Activity.this, fileDoneList);
                imagesRecyclerView.setLayoutManager(new LinearLayoutManager(Upload_Image_Activity.this, LinearLayoutManager.HORIZONTAL, true));
                imagesRecyclerView.setHasFixedSize(true);
                imagesRecyclerView.setAdapter(uploadListAdapter);

            } else if (data.getData() != null) {

                Uri image = data.getData();
                fileDoneList.add(image);

                uploadListAdapter = new UploadListAdapter(Upload_Image_Activity.this, fileDoneList);
                imagesRecyclerView.setLayoutManager(new LinearLayoutManager(Upload_Image_Activity.this, LinearLayoutManager.HORIZONTAL, true));
                imagesRecyclerView.setHasFixedSize(true);
                imagesRecyclerView.setAdapter(uploadListAdapter);

                Toast.makeText(getApplicationContext(), "Selected Single File", Toast.LENGTH_SHORT).show();

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
            String search = whatyouaresearching1.substring(1, whatyouaresearching1.length());
            int i = search.indexOf("/");
            String main = search.substring(0, i) + "/" + search.substring(i, search.length());
            Uri u = Uri.parse(main);

            int pos = fileDoneList.indexOf(u);

            fileDoneList.remove(pos);
//            fileDoneList.add(filePath);
            cropedfileDoneList.add(filePath);

            uploadListAdapter = new UploadListAdapter(Upload_Image_Activity.this, fileDoneList);
            imagesRecyclerView.setLayoutManager(new LinearLayoutManager(Upload_Image_Activity.this, LinearLayoutManager.HORIZONTAL, true));
            imagesRecyclerView.setHasFixedSize(true);
            imagesRecyclerView.setAdapter(uploadListAdapter);

            cropedimages.setText("CROPED IMAGES");

            uploadListAdapter = new UploadListAdapter(Upload_Image_Activity.this, cropedfileDoneList);
            cropedimagesRecyclerView.setLayoutManager(new LinearLayoutManager(Upload_Image_Activity.this, LinearLayoutManager.HORIZONTAL, true));
            cropedimagesRecyclerView.setHasFixedSize(true);
            cropedimagesRecyclerView.setAdapter(uploadListAdapter);

        }
    }

//    private void setImage(Uri imagePath) {
//
//        imageView.setImageURI(imagePath);
//
//    }


    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {
        //checking if file is available
        if (fileDoneList != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(Upload_Image_Activity.this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            for (int i = 0; i < fileDoneList.size(); i++) {

                //getting the storage reference
                final StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(fileDoneList.get(i)));

                //adding the file to reference
                sRef.putFile(fileDoneList.get(i))
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
                                        String key = mDatabase.push().getKey();

                                        String mainitem = mainspinner.getSelectedItem().toString();
                                        String subnitem = Subspinner.getSelectedItem().toString();
                                        String catalog = spinner_catlogname.getSelectedItem().toString();

                                        Upload upload = new Upload();
                                        upload.setMainproduct(mainitem);
                                        upload.setSubproduct(subnitem);
//                                        upload.setDesc( Idescription.getText().toString().trim());
                                        upload.setName(catalog);
                                        upload.setUrl(downloadurl);
                                        upload.setPoductId(key);

                                        leedRepository.createLeed(upload, new CallBack() {
                                            @Override
                                            public void onSuccess(Object object) {

                                            }

                                            @Override
                                            public void onError(Object object) {

                                            }
                                        });
//                                        Upload upload = new Upload(mainitem, subnitem,
//                                                Idescription.getText().toString().trim(), editTextName.getText().toString().trim(), downloadurl,
//                                                FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS).push().getKey());
//
//                                        String uploadId = mDatabase.push().getKey();
//                                        mDatabase.child(uploadId).setValue(upload);
//                                        //  deleteFile();
////                                    sendFCMPush();
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
            }
        } else {
            //display an error if no file is selected
            Toast.makeText(this, "Please Select a file", Toast.LENGTH_SHORT).show();
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
                ArrayAdapter<String> mainadapter = new ArrayAdapter<String>(getBaseContext(),
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
            } else {
                subproductList.clear();
            }
            if (!subproductList.isEmpty()) {
                ArrayAdapter<String> subadapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_spinner_item, subproductList);
                subadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Subspinner.setAdapter(subadapter);
            } else {
                subproductList.add("null");
                ArrayAdapter<String> subadapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_spinner_item, subproductList);
                subadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Subspinner.setAdapter(subadapter);
            }

            spinnerprogress.setVisibility(View.GONE);
            catalogspinnervalue();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void catalogspinnervalue() {

        final String main1 = mainspinner.getSelectedItem().toString();

        Subspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String sub = Subspinner.getSelectedItem().toString();

                mDatabasecatalog = FirebaseDatabase.getInstance().getReference("MainCatalogs");
                mDatabasecatalog.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mainCatalogList.clear();
                            for (DataSnapshot mainproduct2Snapshot : dataSnapshot.getChildren()) {

                                MainCatalog catalog = mainproduct2Snapshot.getValue(MainCatalog.class);
                                if (catalog.getMainpro().equalsIgnoreCase(main1) &&
                                        catalog.getSubpro().equalsIgnoreCase(sub)) {
                                    mainCatalogList.add(catalog.getMaincat());
                                }
                            }
                        } else {
                            mainCatalogList.clear();
                        }

                        if (!mainCatalogList.isEmpty()) {
                            ArrayAdapter<String> mainadapter = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_item, mainCatalogList);
                            mainadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_catlogname.setAdapter(mainadapter);
                        } else {
                            mainCatalogList.add("null");
                            ArrayAdapter<String> mainadapter = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_item, mainCatalogList);
                            mainadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_catlogname.setAdapter(mainadapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

//    private void sendFCMPush() {
//
//        final String SERVER_KEY = YOUR_SERVER_KEY;
//        String msg = "New Product Added";
//        String title = "Samar Floor's Notification";
//        String token = FCM_TOKEN;
//
//        JSONObject obj = null;
//        JSONObject objData = null;
//        JSONObject dataobjData = null;
//
//        try {
//            obj = new JSONObject();
//            objData = new JSONObject();
//
//            objData.put("body", msg);
//            objData.put("title", title);
//            objData.put("sound", "default");
//            objData.put("icon", "icon_name"); //   icon_name
//            objData.put("tag", token);
//            objData.put("priority", "high");
//
//            dataobjData = new JSONObject();
//            dataobjData.put("text", msg);
//            dataobjData.put("title", title);
//
//            obj.put("to", token);
//            //obj.put("priority", "high");
//
//            obj.put("notification", objData);
//            obj.put("data", dataobjData);
//            Log.e("return here>>", obj.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.FCM_PUSH_URL, obj,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e("True", response + "");
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("False", error + "");
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "key=" + SERVER_KEY);
//                params.put("Content-Type", "application/json");
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        int socketTimeout = 1000 * 60;// 60 seconds
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        jsObjRequest.setRetryPolicy(policy);
//        requestQueue.add(jsObjRequest);
//    }

}
