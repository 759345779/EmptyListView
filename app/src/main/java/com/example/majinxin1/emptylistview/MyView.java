package com.example.majinxin1.emptylistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by MAJINXIN1 on 2016/10/14.
 */

public class MyView extends View {
    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作
    private int mWidth;
    private int mHeight;

    public MyView(Context context) {
        super(context);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init(context);
    }


    private void init(Context context) {
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.v6_bottom_btn_fwd_pressed, options);
        mMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//此方法在onMeasure之后   在onLayout之前执行
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i("view_info", "onDetachedFromWindow");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i("view_info", "onAttachedToWindow");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //画出坐标轴
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, redPaint);
        canvas.drawLine(0, mHeight/2, mWidth, mHeight/2, redPaint);
        canvas.translate(mWidth / 2, mHeight / 2);

        Paint paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setColor(Color.BLACK);       //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充

        Paint fillPaint = new Paint();
        fillPaint.setStrokeWidth(5f);
        fillPaint.setColor(Color.RED);       //设置画笔颜色
        fillPaint.setStyle(Paint.Style.STROKE);  //设置画笔模式为填充
//        paint.setStrokeWidth(100f);
//        canvas.translate(getWidth() / 2, getHeight() / 2);
        Path path = new Path();
//        path.lineTo(0, 200);
//        path.lineTo(200, 200);
//        path.lineTo(200,0);
        path.addRect(-200,-200, 200, 200, Path.Direction.CW);

        canvas.drawPath(path,paint);
//        canvas.drawRect(0,0,200,200,paint);

        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path

        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联
        float total_length = measure.getLength();
        float fourEach = total_length / 4;


// 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        measure.getSegment(fourEach, total_length, dst, true);


//        canvas.drawPath(dst, fillPaint);

        Path circlePath = new Path();
        circlePath.addCircle(0, 0, 200, Path.Direction.CW);
        canvas.drawPath(circlePath, fillPaint);
        PathMeasure measure2 = new PathMeasure(circlePath, false);     // 创建 PathMeasure

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }

//        measure2.getPosTan(measure2.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势 布局预览不支持此方法  运行起来才有效果

        mMatrix.reset();                                                        // 重置Matrix
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

//        canvas.drawPath(path, mDeafultPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, paint);                     // 绘制箭头
        invalidate();
    }
}
