package com.buddy.campus.campusbuddy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static android.location.LocationManager.GPS_PROVIDER;

public class uploadFile extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseAuth auth;
    Button mSelDoc,mUploadDoc;
    TextView mFileName;
    EditText mFileTitle,mFileDesc;
    DatabaseReference myRef = database.getReference("Files");
    StorageReference storageRef = storage.getReferenceFromUrl("gs://campusbuddy-d4179.appspot.com");
    Uri mUri;
    LocationManager locationManager;
    private static final int FILE_SELECT_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        auth = FirebaseAuth.getInstance();
        mSelDoc = findViewById(R.id.sel_doc);
        mUploadDoc = findViewById(R.id.upload_doc);
        mFileTitle = findViewById(R.id.file_name);
        mFileDesc = findViewById(R.id.file_desc);
        mFileName = findViewById(R.id.file_id);
        mSelDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(GPS_PROVIDER, 5000, 5, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
        mUploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = UUID.randomUUID().toString();
                String user = auth.getCurrentUser().getEmail();
                String title = mFileTitle.getText().toString();
                String desc = mFileDesc.getText().toString();
                myRef.child(fileName).child("name").setValue(user);
                myRef.child(fileName).child("title").setValue(title);
                myRef.child(fileName).child("desc").setValue(desc);
                myRef.child(fileName).child("longitude").setValue(locationManager.getLastKnownLocation(GPS_PROVIDER).getLongitude());
                myRef.child(fileName).child("latitude").setValue(locationManager.getLastKnownLocation(GPS_PROVIDER).getLatitude());
                StorageReference mountainsRef = storageRef.child(fileName);
                UploadTask uploadTask = mountainsRef.putFile(mUri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getApplicationContext(),"File upload failed,Please try again",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"File upload Successful",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    mUri = data.getData();
                    // Get the path
                    String path;
                    path = getRealPathFromURI(getApplicationContext(),mUri);
                    mFileName.setText(path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getRealPathFromURI(Context mContext,Uri contentUri) {
        String filePath = "";
        String fileId = DocumentsContract.getDocumentId(contentUri);
        // Split at colon, use second item in the array
        String id = fileId.split(":")[1];
        String[] column = {MediaStore.Images.Media.DATA};
        String selector = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, selector, new String[]{id}, null);
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }
}