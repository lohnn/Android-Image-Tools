package se.lohnn.imageviewer.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

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
public class ImageViewerView extends ImageView {
    private Bitmap bitmap;
    private Rect imageDrawRect = new Rect();

    public ImageViewerView(Context cropper) {
        super(cropper);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("ImageViewerView", "In onMeasure!");

        Drawable drawable = getDrawable();
        if (drawable == null) return;

        float imageRatio = (float) drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
        float screenRatio = (float) getMeasuredWidth() / getMeasuredHeight();

        //Image ratio is wider than screen ratio

        if (imageRatio > screenRatio) {
            //Use height
            int screenHeight = getMeasuredHeight();
            int imageScaledWidth = drawable.getIntrinsicWidth() * screenHeight / drawable.getIntrinsicHeight();
            imageDrawRect.set(0, 0, imageScaledWidth, screenHeight);
        } else {
            //Use width
            int screenWidth = getMeasuredWidth();
            int imageScaledHeight = drawable.getIntrinsicHeight() * screenWidth / drawable.getIntrinsicWidth();
            imageDrawRect.set(0, 0, screenWidth, imageScaledHeight);
        }
        bitmap = ((BitmapDrawable) drawable).getBitmap();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (bitmap != null) bitmap.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        if (bitmap == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        canvas.drawBitmap(bitmap, null, imageDrawRect, null);

        //TODO: Add shading to show you can tilt phone to see more

//        Read more: http://www.androidhub4you.com/2014/10/android-custom-shape-imageview-rounded.html#ixzz40jOEeEBx
    }
}
