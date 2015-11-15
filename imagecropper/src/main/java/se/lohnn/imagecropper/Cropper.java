package se.lohnn.imagecropper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import se.lohnn.imagecropper.image.ImageLoader;
import se.lohnn.imagecropper.views.CropperView;

public class Cropper extends AppCompatActivity {
    public static final String IMAGE_PATH = "ImagePath";
    public static final String CROPPING_DIMENSIONS = "CroppingDimensions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String imageLocation = intent.getStringExtra(IMAGE_PATH);
        CropperView cropperView = new CropperView(this);
        cropperView.setImageBitmap(ImageLoader.loadImage(imageLocation));
        //TODO: Error handling here (check if file exists, is an etc.)


        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            //TODO: Setup toolbar
            toolbar.setTitle("HEJSAN");
        }

        setContentView(cropperView);

        /*
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
         */
        /*
         Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
        */
    }
}
