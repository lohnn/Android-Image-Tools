package se.lohnn.imagecropper.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
public class CropperView extends ImageView {
    private Paint shadowedArea;
    private Paint cropFramePaint;
    private Rect visibleArea = new Rect();
    private float screenPercentage = 0.15f;
    private float cropperRatio = 1f;

    public CropperView(Context cropper) {
        super(cropper);
        init();
    }

    public void setRatio(int w, int h) {
        cropperRatio = (float) w / h;
    }

    private void init() {
        setBackgroundColor(0xff000000);

        shadowedArea = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowedArea.setColor(0x80000000);

        cropFramePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cropFramePaint.setColor(0x80eeeeee);
        cropFramePaint.setStrokeWidth(2.0f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float screenRatio = (float) getMeasuredWidth() / getMeasuredHeight();

        Log.d("Ratios", cropperRatio + " : " + screenRatio);

        int rectangleWidth;
        int rectangleHeight;

        //Screens ratio is wider than croppers ratio
        if (screenRatio > cropperRatio) {
            //Use height
            rectangleWidth = getMeasuredHeight() - Math.round((float) getMeasuredHeight() * screenPercentage);
            rectangleHeight = Math.round(rectangleWidth * cropperRatio);
        } else {
            //Use width
            rectangleHeight = getMeasuredWidth() - Math.round((float) getMeasuredWidth() * screenPercentage);
            rectangleWidth = Math.round(rectangleHeight * cropperRatio);
        }

        //Calculate visible rectangle edge positions
        int left = (getMeasuredWidth() / 2) - (rectangleWidth / 2);
        int right = (getMeasuredWidth() / 2) + (rectangleWidth / 2);
        int top = (getMeasuredHeight() / 2) - (rectangleHeight / 2);
        int bottom = (getMeasuredHeight() / 2) + (rectangleHeight / 2);

        visibleArea.set(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Top
        canvas.drawRect(0, 0, getMeasuredWidth(), visibleArea.top, shadowedArea);
        //Middle left
        canvas.drawRect(0, visibleArea.top, visibleArea.left, visibleArea.bottom, shadowedArea);
        //Middle right
        canvas.drawRect(visibleArea.right, visibleArea.top, getMeasuredWidth(), visibleArea.bottom, shadowedArea);
        //Bottom
        canvas.drawRect(0, visibleArea.bottom, getMeasuredWidth(), getMeasuredHeight(), shadowedArea);

        //Draw frame for crop
        canvas.drawLine(visibleArea.left, visibleArea.top, visibleArea.right, visibleArea.top, cropFramePaint);
        canvas.drawLine(visibleArea.right, visibleArea.top, visibleArea.right, visibleArea.bottom, cropFramePaint);
        canvas.drawLine(visibleArea.right, visibleArea.bottom, visibleArea.left, visibleArea.bottom, cropFramePaint);
        canvas.drawLine(visibleArea.left, visibleArea.bottom, visibleArea.left, visibleArea.top, cropFramePaint);
    }
}
