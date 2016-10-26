package com.example.majinxin1.emptylistview.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;

import com.example.majinxin1.emptylistview.R;

/**
 * @author huzhi
 * @description  模仿Iphone滑动开关按钮
 */
public class DragUISwitch extends UISwitch implements OnTouchListener,View.OnClickListener{
	
	private final String TAG = "UISwitch";

    private static final int TOUCH_MODE_IDLE = 0;
    private static final int TOUCH_MODE_DOWN = 1;   
    private static final int TOUCH_MODE_DRAGGING = 2;
    
    private float mTouchX;
    private float mTouchY;
	
	private int mTouchMode; 
	private int mTouchSlop;

    private boolean OnSlip = false;

    private float NowX;

    private Rect Btn_On, Btn_Off;

    private Bitmap bitmap_slip_btn;
    
    private int height;
    private int width;

	public DragUISwitch(Context context) {
		super(context);
		init();
	}

	public DragUISwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DragUISwitch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setSwitchOn(R.drawable.switch_on);
		setSwitchOff(R.drawable.switch_off);
		bitmap_slip_btn = UISwitch.bigCircle(BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_thumb));
		Btn_On = new Rect(0, 0, bitmap_slip_btn.getWidth(), bitmap_slip_btn.getHeight());
		Btn_Off = new Rect(bitmap_bg_on.getWidth() - bitmap_slip_btn.getWidth(), 0,
				bitmap_bg_on.getWidth(), bitmap_slip_btn.getHeight());
		
		ViewConfiguration config = ViewConfiguration.get(getContext());
		mTouchSlop = config.getScaledTouchSlop();
		
		setOnTouchListener(this);
		setOnClickListener(this);
		
		setLayoutSize();
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			switch (mTouchMode) {
			case TOUCH_MODE_IDLE:
				break;
			case TOUCH_MODE_DOWN:
				final float x = event.getX();
				final float y = event.getY();
				if (Math.abs(x - mTouchX) > mTouchSlop
						|| Math.abs(y - mTouchY) > mTouchSlop) {
					mTouchMode = TOUCH_MODE_DRAGGING;
					getParent().requestDisallowInterceptTouchEvent(true);
					return true; // 不需要让父类处理了
				}
				break;
			case TOUCH_MODE_DRAGGING:
				OnSlip = true;
				NowX = event.getX();
				invalidate();
				return true; // 不需要让父类处理了
			default:
				break;
			}
			break;
		case MotionEvent.ACTION_DOWN:
			final float x = event.getX();
			final float y = event.getY();
			if (isEnabled() && hitView(x, y)) {
				mTouchMode = TOUCH_MODE_DOWN;
				mTouchX = x;
				mTouchY = y;
			}
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			OnSlip = false;
			if (mTouchMode == TOUCH_MODE_DRAGGING) {
				boolean LastChoose = mChecked;
				if (event.getX() >= (bitmap_bg_on.getWidth() / 2)) {
					NowX = bitmap_bg_on.getWidth() - bitmap_slip_btn.getWidth() / 2;
					mChecked = true;
				}
				else {
					NowX = NowX - bitmap_slip_btn.getWidth() / 2;
					mChecked = false;
				}
				invalidate();
				if (mChgLsn != null && (LastChoose != mChecked))
					mChgLsn.onChanged(mChecked);
				return true; // 不需要让父类处理了
			}
			mTouchMode = TOUCH_MODE_IDLE;
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = resolveSizeAndState(width, widthMeasureSpec, 0);
        int heightSize = resolveSizeAndState(height, heightMeasureSpec, 0);
        setMeasuredDimension(widthSize, heightSize);
	}
	
	/*画打开背景的画笔，实现淡出淡入效果*/
	private Paint alphaPaint = new Paint();
	private final int MAX_ALPHA_SIZE = 255;
	
	Matrix matrix = new Matrix();
	Paint paint = new Paint();
	/*画关闭背景的RECT，实现放大缩小效果*/
	Rect scaleRect = new Rect();
	
	@Override
	protected void onDraw(Canvas canvas) {// 绘图函数
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		/**动画*/
		if(isAnimationRunning()){
			if(mChecked){
				//1. 选中背景的淡出
				int alpha = (int) (MAX_ALPHA_SIZE*animationFraction);
				alphaPaint.setAlpha(alpha);
				canvas.drawBitmap(bitmap_bg_on, matrix, alphaPaint);
				
				//2. 未选中背景消失
				Rect scaleRect = new Rect();
				scaleRect.left = (int) ((bitmap_bg_on.getWidth()/2)*animationFraction);
				scaleRect.right = (int) (bitmap_bg_on.getWidth()/2 +(bitmap_bg_on.getWidth()/2)*(1-animationFraction)) ;
				scaleRect.top = (int) (bitmap_bg_on.getHeight()/2 - bitmap_bg_on.getHeight()/2* (1-animationFraction));
				scaleRect.bottom = (int) (bitmap_bg_on.getHeight()/2 + bitmap_bg_on.getHeight()/2*(1-animationFraction));
				canvas.drawBitmap(bitmap_bg_off, null, scaleRect, paint);
				
				//3. 游标从左到右
				int SLIP_WIDTH = bitmap_bg_on.getWidth() - bitmap_slip_btn.getWidth();
				int x = (int) (SLIP_WIDTH*animationFraction);
				canvas.drawBitmap(bitmap_slip_btn, x, 0, paint);
			}else{ 
				//1. 选中背景的淡入
				int alpha = (int) (MAX_ALPHA_SIZE*(1-animationFraction));
				alphaPaint.setAlpha(alpha);
				canvas.drawBitmap(bitmap_bg_on, matrix, alphaPaint);
				
				//2. 未选中背景出现
				Rect scaleRect = new Rect();
				scaleRect.left = (int) ((bitmap_bg_on.getWidth()/2)*(1-animationFraction));
				scaleRect.right = (int) (bitmap_bg_on.getWidth()/2 +(bitmap_bg_on.getWidth()/2)*(animationFraction)) ;
				scaleRect.top = (int) (bitmap_bg_on.getHeight()/2 - bitmap_bg_on.getHeight()/2* (animationFraction));
				scaleRect.bottom = (int) (bitmap_bg_on.getHeight()/2 + bitmap_bg_on.getHeight()/2*(animationFraction));
				canvas.drawBitmap(bitmap_bg_off, null, scaleRect, paint);
				
				//1.  游标从右到左
				int SLIP_WIDTH = bitmap_bg_on.getWidth() - bitmap_slip_btn.getWidth();
				int x = (int) (SLIP_WIDTH*(1-animationFraction));
				canvas.drawBitmap(bitmap_slip_btn, x, 0, paint);
			}
			return;
		}
		
		/**非动画*/
		float x;
		{
			
			if (OnSlip){// 是否是在滑动状态,
				if (NowX >= bitmap_bg_on.getWidth())// 是否划出指定范围,不能让游标跑到外头,必须做这个判断
					x = bitmap_bg_on.getWidth() - bitmap_slip_btn.getWidth();// 减去游标1/2的长度...
				else
					x = NowX - bitmap_slip_btn.getWidth() / 2;
			} else {// 非滑动状态
				if (mChecked)// 根据现在的开关状态设置画游标的位置
					x = Btn_Off.left;
				else
					x = Btn_On.left;
			}
			if (x < 0)// 对游标位置进行异常判断...
				x = 0;
			else if (x > bitmap_bg_on.getWidth() - bitmap_slip_btn.getWidth())
				x = bitmap_bg_on.getWidth() - bitmap_slip_btn.getWidth();
			
			
			int size = (int) (bitmap_bg_on.getWidth() - bitmap_slip_btn.getWidth());
			if (OnSlip) {
				// 画出打开时的背景 算出淡入淡出比例
				int alpha = (int)(x*MAX_ALPHA_SIZE/size);
				alphaPaint.setAlpha(alpha);
				canvas.drawBitmap(bitmap_bg_on, matrix, alphaPaint);
				
				scaleRect.left = (int) ((bitmap_bg_on.getWidth()/2)*(x/size));
				scaleRect.right = (int) (bitmap_bg_on.getWidth()/2 +(bitmap_bg_on.getWidth()/2)*(1-x/size)) ;
				scaleRect.top = (int) (bitmap_bg_on.getHeight()/2 - bitmap_bg_on.getHeight()/2* (1-x/size));
				scaleRect.bottom = (int) (bitmap_bg_on.getHeight()/2 + bitmap_bg_on.getHeight()/2*(1-x/size));
				
				canvas.drawBitmap(bitmap_bg_off, null, scaleRect, paint);
				
			} else {
				if (mChecked){
					canvas.drawBitmap(bitmap_bg_on, matrix, paint);// 画出打开时的背景
				} else{
					canvas.drawBitmap(bitmap_bg_off, matrix, paint);// 画出关闭时的背景
				}
			}
			
			canvas.drawBitmap(bitmap_slip_btn, x, 0, paint);// 画出游标.
		}
	}

	@Override
	public void onClick(View v) {
		setChecked(!mChecked);
		startAnimation();
	}
	
	private boolean hitView(float x,float y){
		if(x>bitmap_bg_on.getWidth() || y>bitmap_bg_on.getHeight()){
			return false;
		}
		return true;
	}
	
	/**由switch_off这张图片来决定view的大小*/
	private void setLayoutSize(){
		Bitmap bm = big(BitmapFactory.decodeResource(getResources(),
		R.drawable.switch_on));
		height = bm.getHeight();
		width = bm.getWidth();
		bm.recycle();
	}

//	private boolean isAnimating = false;
	private final int ANIMATION_DURATION_MS = 300;
	private float animationFraction;
	private ValueAnimator switchAnimator; 
	
	
	public boolean isAnimationRunning(){
		if(switchAnimator!=null && switchAnimator.isRunning()){
			return true;
		}else{
			return false;
		}
	}
	
	/*点击UISWITCH后进行动画*/
	private void startAnimation(){
		if(isAnimationRunning())
			return;
		switchAnimator = ValueAnimator.ofFloat(100);
		switchAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float currentValue = (Float) animation.getAnimatedValue();
				animationFraction = currentValue/100;
				invalidate();
			}
		});
		switchAnimator.setDuration(ANIMATION_DURATION_MS).start();
	}

	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (bitmap_slip_btn != null) {
			bitmap_slip_btn.recycle();
			bitmap_slip_btn = null;
		}

		if (bitmap_bg_on != null) {
			bitmap_bg_on.recycle();
			bitmap_bg_on = null;
		}

		if (bitmap_bg_off != null) {
			bitmap_bg_off.recycle();
			bitmap_bg_off = null;
		}
	}
}
