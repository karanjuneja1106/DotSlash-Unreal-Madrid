package com.buddy.campus.campusbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PostAdActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Advertisements");
    EditText mTitleText,mDescText;
    Button mButton,mImgButton;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);
        mButton = findViewById(R.id.upload);
        mTitleText = findViewById(R.id.product_title);
        mDescText = findViewById(R.id.product_desc);
        mImgButton=findViewById(R.id.photo_sel);
        imageView=findViewById(R.id.viewImage);

        mImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mTitleText.getText().toString();
                Map<String,String> M = new HashMap<>();
                M.put("TITLE",str);
                if(str.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Title can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                str = mDescText.getText().toString();
                M.put("DESC",str);
                JSONObject json = new JSONObject(M);
                if(str.length()>0){
                    myRef.push().child("ad").setValue(json.toString());
                    Toast.makeText(getApplicationContext(),"Posted successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                        Toast.makeText(getApplicationContext(),"Description can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        AlertDialog.Builder builder = new AlertDialog.Builder(PostAdActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
