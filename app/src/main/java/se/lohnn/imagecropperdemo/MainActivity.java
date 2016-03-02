package se.lohnn.imagecropperdemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import se.lohnn.imageviewer.ImageViewer;

public class MainActivity extends AppCompatActivity {

    private static final int CROP_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cropperStart = (Button) findViewById(R.id.buttonStartCropping);

        openImageViewer(this);
        /*cropperStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageViewer(v.getContext());
            }
        });*/
    }

    private void openImageViewer(Context context) {
        Intent cropperIntent = new Intent(context, ImageViewer.class);
        File cacheDir = getCacheDir();
        String tempImageFileName = "testImage.jpg";
        File tempImageFile = new File(cacheDir, tempImageFileName);
        //If demo app has not been run before
        //(temp image in cache folder does not exist)
        if (!tempImageFile.exists()) {
            //Load image from asset
            Bitmap tempImage = getBitmapFromAsset(getAssets(), tempImageFileName);
            //Save image to cache folder
            tempImageFile.getParentFile().mkdirs();
            saveImageToFile(tempImage, tempImageFile);
        }

        cropperIntent.putExtra(ImageViewer.IMAGE_PATH, tempImageFile.getAbsolutePath());
        startActivityForResult(cropperIntent, CROP_REQUEST);
    }

    /**
     * Reads an image from asset folder
     *
     * @param assetManager
     * @param fileName
     * @return
     */
    private static Bitmap getBitmapFromAsset(AssetManager assetManager, String fileName) {
        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Saves a Bitmap to file
     *
     * @param imageToSave
     * @param saveLocation
     * @return
     */
    private static boolean saveImageToFile(Bitmap imageToSave, File saveLocation) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(saveLocation);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
