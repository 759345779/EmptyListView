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
    //进行图片的尺寸压缩
    /**
     *
     * @param srcPath 需要压缩图片的路径,如：/sdcard/srcfile/srcpicture.png
     * @param desPath 压缩后图片的保存文件夹得路径，如：/sdcard/file
     * @param fileName 压缩后文件保存name不包括路径，如：picture
     * @return 返回为压缩后图片的路径包括name和图片后缀，如：/sdcard/file/picture.png
     */
    public static String compressPicture(String srcPath, String desPath,String fileName) {
        boolean isPNG = false;
        String suffix = null;
        String desfileFullPath = null;
        File file = new File(srcPath);
        if (file != null && file.isFile()) {
            if (file.length() < 102400) return null;//小于100kb不做处理
        }else {
            return null;//文件不存在或者不是文件的情况下不做处理
        }
        String name = file.getName();
        if (name.contains(".")) {
            int index = name.lastIndexOf(".");
             suffix= name.substring(index).toLowerCase();
            if (suffix.contains("png")) {
                isPNG = true;
            }else {
                isPNG =false;
            }

        }
        desfileFullPath = desPath + "/" + file.getName();

        FileOutputStream fos = null;
        BitmapFactory.Options op = new BitmapFactory.Options();

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
        op.inJustDecodeBounds = false;

        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;

        if (w < 100 || h < 100) {//任何一条边的像素值小于100的情况下不做处理
            return null;
        }

        float be = 2.0f;//压缩比例参数

        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, op);

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
