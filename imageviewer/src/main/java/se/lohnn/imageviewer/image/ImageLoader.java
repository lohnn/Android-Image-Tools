package se.lohnn.imageviewer.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

/**
 * Copyright (C) lohnn on 2015.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ImageLoader {
    private static String TAG = "ImageLoader";

    public static Bitmap loadImage(String path, int widthPixels, int heightPixels) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

//         Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, widthPixels, heightPixels);
//        options.inSampleSize = 1;
        //TODO: See if I can calculate a way to see if I need to scale image even though it will be blurry

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap scaleBitmapKeepRatio(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleValue;
        int scaleSize = (newWidth > newHeight) ? newWidth : newHeight;
        float imageScaleSize = (width > height) ? height : width;
        scaleValue = (float) scaleSize / imageScaleSize;
        Log.d(TAG, String.format("ScaleValue: %s : %s : %s", scaleValue, width, newWidth));

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleValue, scaleValue);
//        matrix.postScale(.78f, .78f);

//         "RECREATE" THE NEW BITMAP
        bm.recycle();
        return Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
