package com.adoramapix.fontrenderer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import com.adoramapix.fontrenderer.FontRendererApp;

public class CustomTextView extends View {
	public static final String TAG = CustomTextView.class.getName();
	
	public static final int ALIGN_NORMAL = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_OPPOSITE = 2;

	private Context mContext;
	private String mText;
	private Path mBorder;
	private int mAlignment;
	private int mWidth;
	private int mHeight;

	private String mFontName;
	private Typeface mTypeface;
	private float mFontSize;
	private float mAscender;
	private float mDescender;
	private float mPadding;
	private float mMultilineSpacing;

	private TextPaint mPaint;
	private FontMetrics mFontMetrics;
	private float mTextAscent;

	public CustomTextView(Context context, int posX, int posY, int width, int height) {
		super(context);
		mContext = context;
		mWidth = width;
		mHeight = height;

		setX(posX);
		setY(posY - FontRendererApp.PAGE_MARGIN_TOP);

		mPaint = new TextPaint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawRect(canvas);
		
		canvas.save();	 

		float xPos = 0;
		float yPos = mPadding + mAscender;
		drawMultiLineText(mText, xPos, yPos, mPaint, canvas);

		canvas.restore();
	}

	private void drawRect(Canvas canvas) {
		mBorder = new Path();
		mBorder.addRect(0, FontRendererApp.PAGE_MARGIN_TOP, mWidth, mHeight + FontRendererApp.PAGE_MARGIN_TOP, Path.Direction.CCW);
		canvas.drawPath(mBorder, mPaint);
	}
	
	private void drawLineText(String line, float x, float y, TextPaint paint, Canvas canvas) {		
		float xPos = calcAlignment(canvas, line);
		for (int i = 1; i <= line.length(); i++) {
			char ch1 = line.charAt(i - 1);
			
			canvas.drawText(String.valueOf(ch1), x + xPos, FontRendererApp.PAGE_MARGIN_TOP + y, paint);

			if(i < line.length()) { 
				char ch2 = line.charAt(i);
				float xKerning = ((FontRendererApp) mContext).getWinKerning(ch1, ch2);
				xPos += xKerning;
			}
			
			float[] charWidth = new float[1];
			mPaint.getTextWidths(line, i - 1, i, charWidth);  
			xPos += charWidth[0];
		}
	}

	/**
	 * Draw multiline text, calculate lines width in text box.
	 * @param str
	 * @param x
	 * @param y
	 * @param paint
	 * @param canvas
	 */
	private void drawMultiLineText(String text, float x, float y, TextPaint paint, Canvas canvas) {
		String[] lines = text.split(" ");
		int linesCount = 0;
		StringBuilder drawableLine = new StringBuilder();
		String cacheLine = "";
		for (int i = 0; i < lines.length; i++) {
			drawableLine.append(lines[i]);
			if( (getLineWidth(drawableLine.toString()) + mPadding * 2) >= mWidth) {
				drawLineText(cacheLine.substring(0, cacheLine.length() - 1), x, y + mMultilineSpacing * linesCount, paint, canvas);
				drawableLine = new StringBuilder(lines[i]).append(" ");
				linesCount ++;
			} else {
				cacheLine = drawableLine.append(" ").toString();
			}
		}
		drawLineText(cacheLine.substring(0, cacheLine.length() - 1), x, y + mMultilineSpacing * linesCount, paint, canvas);
	}
	
	/**
	 * Calculate text alignment in text box with padding.
	 * @param canvas
	 * @param line
	 * @return
	 */
	private float calcAlignment(Canvas canvas, String line){
		float textWidth = getLineWidth(line);		
		
		switch(mAlignment){
			case ALIGN_NORMAL:
				return mPadding;
			case ALIGN_CENTER:
				return (mWidth - textWidth) / 2;
			case ALIGN_OPPOSITE:
				return mWidth - textWidth - mPadding;
			default:
				return 0;
		}
	}

	public void setLineHeight(int height) {
		mHeight = height;
	}

	public void setAlignment(int alignment) {
		mAlignment = alignment;
	}
	
	public void setText(String text){
		mText = text;
	}

	/**
	 * Read and set font metrics.
	 */
	public void readMetricsFromModel(Typeface tf) {
		FontRendererApp app = (FontRendererApp)mContext;
		mAscender = app.getWinAscender();
		mDescender = app.getWinDescender();
		mPadding = app.getWinPadding();
		mFontSize = app.getWinFontSize();
		mFontName = app.getFontName();
		mMultilineSpacing = app.getWinMultilineSpacing();
		mTypeface = tf;
		mPaint.setTypeface(mTypeface);
		mPaint.setTextSize(mFontSize);
		
		mFontMetrics = mPaint.getFontMetrics();
		mTextAscent = mFontMetrics.ascent;
	}
	
	public void refresh(Typeface tf) {
		readMetricsFromModel(tf);
		invalidate();
	}
	
	/**
	 * Get line width. It depends of glyph width and kerning!!!
	 * @param line
	 * @return
	 */
	private float getLineWidth(String line) {
		float lineWidth = 0;
		for (int i = 1; i <= line.length(); i++) {
			
			char ch1 = line.charAt(i - 1);
			float[] charWidth = new float[1];
			mPaint.getTextWidths(line, i - 1, i, charWidth);  // get fist char width

			if(i < line.length()) { 
				char ch2 = line.charAt(i);
				float xKerning = ((FontRendererApp) mContext).getWinKerning(ch1, ch2);
				lineWidth += xKerning;  // calculate kerning for first pair of chars
			}
			
			lineWidth += charWidth[0];  // increase line width with first char width + kerning
		}
		return lineWidth;
	}
}
