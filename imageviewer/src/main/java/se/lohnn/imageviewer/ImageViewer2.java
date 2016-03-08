package se.lohnn.imageviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import se.lohnn.imageviewer.image.ImageLoader;
import se.lohnn.imageviewer.views.ImageViewerView;

public class ImageViewer2 extends AppCompatActivity {
    public static final String IMAGE_PATH = "ImagePath";

    private ImageViewerView imageViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String imageLocation = intent.getStringExtra(IMAGE_PATH);

        imageViewer = new ImageViewerView(this);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Bitmap image = ImageLoader.INSTANCE.loadImage(imageLocation, metrics.widthPixels, metrics.heightPixels);
        imageViewer.setImageBitmap(image);
        //TODO: Error handling here (check if file exists, is an etc.)

        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            //TODO: Setup toolbar
            toolbar.setTitle("HEJSAN");
            toolbar.hide();
        }

        setContentView(imageViewer);

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
