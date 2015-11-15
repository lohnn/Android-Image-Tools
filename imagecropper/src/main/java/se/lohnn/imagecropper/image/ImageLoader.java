package se.lohnn.imagecropper.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
    public static Bitmap loadImage(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }
}
