package com.example.majinxin1.emptylistview.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by MAJINXIN1 on 2016/10/27.
 */

public class BitmapUtils {
    public static String compressPicture(String srcPath, String desPath,String fileName) {
        boolean isPNG = false;
        String suffix = null;
        String desfileFullPath = null;
        File file = new File(srcPath);
        String name = file.getName();
        if (name.contains(".")) {
            int index = name.lastIndexOf(".");
             suffix= name.substring(index).toLowerCase();
            if (suffix.contains("png")) {
                isPNG = true;
            }else {
                isPNG =false;
            }
            Log.i("file_info", "filename=" + file.getName());
            Log.i("file_info", "string suffix=" + suffix);
        }
        desfileFullPath = desPath + "/" + fileName + suffix;

        FileOutputStream fos = null;
        BitmapFactory.Options op = new BitmapFactory.Options();

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
        op.inJustDecodeBounds = false;

        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;
        Log.i("picture_info", "w = " + w + "   h = " + h);

        float hh = 1024f;//
        float ww = 1024f;//
        // 最长宽度或高度1024
        float be = 2.0f;
       /* if (w > h && w > ww) {
            be = (float) (w / ww);
        } else if (w < h && h > hh) {
            be = (float) (h / hh);
        }
        if (be <= 0) {
            be = 1.0f;
        }*/
        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, op);
        /*boolean temp = true;
        if(temp) return;*/

        int desWidth = (int) (w / be);
        int desHeight = (int) (h / be);
        bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
        try {
            fos = new FileOutputStream(desfileFullPath);
            if (bitmap != null) {
                if (isPNG) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                }else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return desfileFullPath;
    }


}
