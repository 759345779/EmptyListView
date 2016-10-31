package com.example.majinxin1.emptylistview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;

import com.example.majinxin1.emptylistview.view.BitmapUtils;
import com.example.majinxin1.emptylistview.view.DragUISwitch;
import com.example.majinxin1.emptylistview.view.UISwitch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main2Activity extends AppCompatActivity {
//    private static String imagePath = "/storage/emulated/0/tencent/QQfile_recv/desk.png";
    private static String imagePath = "/storage/emulated/0/Download/wpsmail/abcd.jpg";
    private static String path_text = "/storage/emulated/0/majinxin/desk2.png";
    private static String path_text2 = "/storage/emulated/0/majinxin/desk3.png";
    private static String baseFile = null;
    private static String caheFile = "/majinxin";
    private static String caheFile2 = "/majinxin2";
    File file1;
    File file2;
    private Bitmap loadedImage;
    private Bitmap pressedImage;
    private Bitmap loadedImage2;
    ExecutorService executorService;
    private boolean fileCacheIsInit = false;
    private int nameCount = 0;
    DragUISwitch drag_switch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drag_switch = (DragUISwitch) findViewById(R.id.drag_switch);
        drag_switch.setOnChangedListener(new UISwitch.OnChangedListener() {
            @Override
            public void onChanged(boolean CheckState) {
                Log.i("drag_button", "drag_switch1=" + CheckState);
                Log.i("drag_button", "drag_switch2=" + drag_switch.isChecked());
            }
        });
        drag_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getIsCheckedState();
            }
        });
        executorService = Executors.newSingleThreadExecutor();
        File file = Environment.getExternalStorageDirectory();
        baseFile = file.getPath();
        file1 = new File(baseFile+caheFile);
        file2 = new File(baseFile+caheFile2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                fileCacheIsInit=initCacheFile();
                Log.i("file_info", "init finished=" + fileCacheIsInit);
            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        nameCount++;
                        String createBasePath = baseFile + caheFile ;
                        String crearedfile=BitmapUtils.compressPicture(imagePath, createBasePath,nameCount+"");
                        Log.i("file_info", "press finished ="+crearedfile);
                    }
                });
//                getFilePathInfo();
//                loadImage(imagePath);
//                loadImage2(path_text);
//                getStoragePath();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {//质量压缩图片
            @Override
            public void onClick(View v) {
                File file3 = new File("/storage/emulated/0/tencent/QQfile_recv/desk.png");
                Log.i("file_info", "filename ="+file3.getName());
//                pressedImage = pressImage();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileInfo();
            }
        });
    }

    private void getIsCheckedState() {
        Log.i("drag_button", "drag_switch=" + drag_switch.isChecked());
    }

    private void getFilePathInfo() {

        Log.i("file_info", "file1=" + file1.exists() + "file2=" + file2.exists());
//        if(!file2.exists()){
//            boolean mkdirIs=file2.mkdir();
//            Log.i("file_info", "mkdirIs=" + mkdirIs);
//        }

    }


    private void loadImage2(final String path_text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadedImage = getBitmap(path_text);

                Log.i("file_test", "bitmap2_info_witdth=" + loadedImage.getWidth() + " height=" + loadedImage.getHeight() + "  byteCount(MB)=" + getPrettySize(loadedImage.getByteCount()));
            }
        }).start();
    }

    private Bitmap pressImage() {
        if (loadedImage != null) {
            return compressImage(loadedImage,path_text);
        }else {
            return null;
        }
    }

    private void loadImage(final String imagePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadedImage = getBitmap(imagePath);

                Log.i("file_test", "bitmap_info_witdth=" + loadedImage.getWidth() + " height=" + loadedImage.getHeight() + "  byteCount(MB)=" + getPrettySize(loadedImage.getByteCount()));
            }
        }).start();

    }

    private void getStoragePath() {
        File file = Environment.getExternalStorageDirectory();
        Log.i("file_test", "path=" + file.getPath());
        File rootFile = Environment.getRootDirectory();
        Log.i("file_test", "rootpath=" + rootFile.getPath());
    }


    public Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    public static float getPrettySize(int bytes) {//将字节数量转换为MB
        float finalNum = (float) bytes / 1024 / 1024;
        return finalNum;
    }

    private Bitmap compressImage(Bitmap image, String outPath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 / 1024 > 1) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩

            baos.reset();//重置baos即清空baos

            options -= 10;//每次都减少10

            image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        // Generate compressed image file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPath);
            fos.write(baos.toByteArray());
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){

        } finally{
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public void getFileInfo() {
        File file1 = new File(imagePath);
        File file2 = new File(path_text);
        Log.i("file_test", "file1_info_totalspace1=" + file1.getTotalSpace());
        Log.i("file_test", "file1_info_totalspace2=" + file2.getTotalSpace());
    }

    private boolean initCacheFile() {//清空压缩图片的文件夹或者创建文件夹
        if(file1.exists()){
            if (file1.isDirectory()) {
                File[] files = file1.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件
                    files[i].delete();//将文件夹下面文件全部删除
                }
            }
            return true;
        }else {
            boolean mkdirIs=file1.mkdir();
            return  mkdirIs;
        }

    }


}
