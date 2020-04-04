package com.burhanrashid52.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    ImageView imgCamera;
    ImageView imgGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCamera = findViewById(R.id.camera_button_image);
        imgCamera.setOnClickListener(this);

        imgGallery = findViewById(R.id.gallery_button_image);
        imgGallery.setOnClickListener(this);

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_button_image:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.gallery_button_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Intent intent = new Intent(MainActivity.this, EditImageActivity.class);
                    intent.putExtra("image", photo);
                    startActivity(intent);
             /*       mPhotoEditor.clearAllViews();
                    mPhotoEditorView.getSource().setImageBitmap(photo);*/
                    break;
                case PICK_REQUEST:
                    try {
                        Uri uri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        Intent galleryintent = new Intent(MainActivity.this, EditImageActivity.class);
                        galleryintent.putExtra("image", bitmap);
                        startActivity(galleryintent);
                     /*   mPhotoEditor.clearAllViews();
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);*/
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


}
