package com.example.xdc.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ColorTrackTextView extends View {
	public final static String TAG = "com.example.newtest.ColorTrackTextView";

	public final static int DIRECTION_LEFT = 0;
	public final static int DIRECTION_RIGHT = 1;
	//	public final static int DEFAULT_TEXT_SIZE = sp2dp(30);

	private Paint mPaint;

	private String mText;
	private float mTextSize;
	private int mTextOriginalColor;
	private int mTextChangeColor;
	private int mDirection;
	private float mProgress;

	private int mTextWidth;
	private Rect mTextBound;
	private int mTextStartX;

	public ColorTrackTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
		mText = ta.getString(R.styleable.ColorTrackTextView_android_text);
		mTextOriginalColor = ta.getColor(R.styleable.ColorTrackTextView_text_original_color, Color.BLACK);
		mTextChangeColor = ta.getColor(R.styleable.ColorTrackTextView_text_change_color, Color.RED);
		mTextSize = ta.getDimension(R.styleable.ColorTrackTextView_android_textSize, 0);
		mDirection = ta.getInt(R.styleable.ColorTrackTextView_direction, DIRECTION_RIGHT);
		mProgress = ta.getInt(R.styleable.ColorTrackTextView_progress, 0);
		ta.recycle();

		mPaint = new Paint();
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextSize(mTextSize);

		measureText(mText);
	}

	private void measureText(String text) {
		mTextWidth = (int) mPaint.measureText(text);
		mTextBound = new Rect();
		mPaint.getTextBounds(text, 0, text.length(), mTextBound);
		//		mTextWidth = mTextBound.width();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
		int measureWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
		//		int textWidth = mTextBound.width()/2;		// 139
		int textWidth = mTextWidth/2;				// 145
		mTextStartX = measureWidth/2 - textWidth;
	}

	private int measureHeight(int measureSpec){  
		int mode = MeasureSpec.getMode(measureSpec);  
		int val = MeasureSpec.getSize(measureSpec);  
		int result = 0;  
		switch (mode)  
		{  
		case MeasureSpec.EXACTLY:  
			result = val;  
			break;  
		case MeasureSpec.AT_MOST:  
		case MeasureSpec.UNSPECIFIED:  
			result = mTextBound.height();  
			break;  
		}  
		result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;  
		return result + getPaddingTop() + getPaddingBottom();  
	}  

	private int measureWidth(int measureSpec){  
		int mode = MeasureSpec.getMode(measureSpec);  
		int val = MeasureSpec.getSize(measureSpec);  
		int result = 0;  
		switch (mode)  
		{  
		case MeasureSpec.EXACTLY:  
			result = val;  
			break;  
		case MeasureSpec.AT_MOST:  
		case MeasureSpec.UNSPECIFIED:  
			//			 result = mTextBound.width();  
			result = mTextWidth;  
			break;  
		}  
		result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;  
		return result + getPaddingLeft() + getPaddingRight();  
	} 

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if(mDirection == DIRECTION_RIGHT){
			drawOriginalRight(canvas);
			drawChangeRight(canvas);
		}else if(mDirection == DIRECTION_LEFT){
			drawOriginalLeft(canvas);
			drawChangeLeft(canvas);
		}
	}

	private void drawOriginalRight(Canvas canvas) {
		drawOriginalText(canvas, (int) (mTextStartX + mProgress * mTextWidth), mTextStartX + mTextWidth);
	}

	private void drawChangeRight(Canvas canvas) {
		drawChangeText(canvas, mTextStartX, (int) (mTextStartX + mProgress * mTextWidth));
	}

	private void drawOriginalLeft(Canvas canvas) {
		drawOriginalText(canvas, mTextStartX, (int) (mTextStartX + mProgress * mTextWidth));
	}

	private void drawChangeLeft(Canvas canvas) {
		drawChangeText(canvas, (int) (mTextStartX + mProgress * mTextWidth), mTextStartX + mTextWidth);
	}

	private void drawChangeText(Canvas canvas, int startX, int endX) {
		mPaint.setColor(mTextChangeColor);
		if(DIRECTION_LEFT == mDirection){
			mPaint.setAlpha(0 == mProgress? 255: (int) ((1 - mProgress) * 255));
		}else if(DIRECTION_RIGHT == mDirection){
			mPaint.setAlpha(0 == mProgress? 255: (int) (mProgress * 255));
		}
		canvas.save(Canvas.CLIP_SAVE_FLAG);
		canvas.clipRect(startX, 0, endX, getMeasuredHeight());
		canvas.drawText(mText, mTextStartX,
				getMeasuredHeight()/2 + mTextBound.height() / 2, mPaint);
		canvas.restore();
	}
	
	private void drawOriginalText(Canvas canvas, int startX, int endX) {
		mPaint.setColor(mTextOriginalColor);
		canvas.save(Canvas.CLIP_SAVE_FLAG);
		canvas.clipRect(startX, 0, endX, getMeasuredHeight());
		canvas.drawText(mText, mTextStartX,
				getMeasuredHeight()/2 + mTextBound.height() / 2, mPaint);
		canvas.restore();
	}

	public void setProgress(float progress){
		this.mProgress = progress;
		invalidate();
	}

	public void setDirection(int direction){
		this.mDirection = direction;
	}

}
