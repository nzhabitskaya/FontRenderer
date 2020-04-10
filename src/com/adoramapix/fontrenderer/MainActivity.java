package com.adoramapix.fontrenderer;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.adoramapix.fontrenderer.cache.CacheManager;
import com.adoramapix.fontrenderer.font.TtfReader;
import com.adoramapix.fontrenderer.ui.CustomTextView;

public class MainActivity extends Activity {
	public static final int LINE_WIDTH = 1725;
	public static final int MULTILINE_WIDTH = 1725;

	public static final String[] FONTS = { "airplanesn.ttf", "chopinn.otf", "jellykan.ttf", "lavanderian.otf", "learningn.ttf",
			"mystn.ttf", "onestarrynightn.ttf", "scriptn.ttf", "verdana.ttf", "viven.ttf" };
	public static final int[] BACKGROUND_LEFT = { R.drawable.airplanesn1, R.drawable.chopin1, R.drawable.jellyka1, R.drawable.lavanderia1,
			R.drawable.learning_curve1, R.drawable.mystic1, R.drawable.onescarynight1, R.drawable.script331, R.drawable.verdana1,
			R.drawable.vive1 };
	public static final int[] BACKGROUND_RIGHT = { R.drawable.airplanesn2, R.drawable.chopin2, R.drawable.jellyka2, R.drawable.lavanderia2,
			R.drawable.learning_curve2, R.drawable.mystic2, R.drawable.onescarynight2, R.drawable.script332, R.drawable.verdana2,
			R.drawable.vive2 };
	public static final int[][] TEXT_BOXES = { { 183, 400 }, { 183, 400 }, { 183, 400 }, { 143, 272 }, { 183, 400 }, { 183, 400 },
			{ 183, 400 }, { 150, 282 }, { 139, 400 }, { 183, 321 } };
	
	/**
	 * Cache typeFaces.
	 */
	public static final Map<String, Typeface> sTypefaceCache = new HashMap<String, Typeface>();

	private ImageView imageBackgroundLeft;
	private ImageView imageBackgroundRight;

	private CustomTextView textline1;
	private CustomTextView textline2;
	private CustomTextView textline3;
	private CustomTextView textMultiline1;
	private CustomTextView textMultiline2;
	private CustomTextView textMultiline3;

	private TtfReader ttfReader;
	private CacheManager cache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cache = new CacheManager(getApplicationContext());
		
		initFields();
		setLabels();
		setAlighments();
		
		changeFont(0);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_airplanes:
			changeFont(0);
			item.setChecked(true);
			return true;
		case R.id.action_chopin:
			changeFont(1);
			item.setChecked(true);
			return true;
		case R.id.action_jellyka:
			changeFont(2);
			item.setChecked(true);
			return true;
		case R.id.action_lavanderia:
			changeFont(3);
			item.setChecked(true);
			return true;
		case R.id.action_learning:
			changeFont(4);
			item.setChecked(true);
			return true;
		case R.id.action_mystic:
			changeFont(5);
			item.setChecked(true);
			return true;
		case R.id.action_onescarynight:
			changeFont(6);
			item.setChecked(true);
			return true;
		case R.id.action_script33:
			changeFont(7);
			item.setChecked(true);
			return true;
		case R.id.action_verdana:
			changeFont(8);
			item.setChecked(true);
			return true;
		case R.id.action_vive:
			changeFont(9);
			item.setChecked(true);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void changeFont(int index) {
		Typeface currentFont = getTypeface(getAssets(), FONTS[index]);
		
		imageBackgroundLeft.setImageResource(BACKGROUND_LEFT[index]);
		//imageBackgroundRight.setImageResource(BACKGROUND_RIGHT[index]);

		setLineHeights(TEXT_BOXES[index][0], TEXT_BOXES[index][1]);
		updateFontMetrics(FONTS[index]);
		
		refreshTextFields(currentFont);
	}

	private void updateFontMetrics(String fontName) {
		ttfReader = TtfReader.getInstance(this);
		ttfReader.readFontMetrics(fontName);
	}

	private void initFields() {
		imageBackgroundLeft = (ImageView) findViewById(R.id.image_bg_left);
		//imageBackgroundRight = (ImageView) findViewById(R.id.image_bg_right);

		textline1 = new CustomTextView(getApplicationContext(), 75, 75, LINE_WIDTH, 183);
		textline2 = new CustomTextView(getApplicationContext(), 75, 250, LINE_WIDTH, 183);
		textline3 = new CustomTextView(getApplicationContext(), 75, 425, LINE_WIDTH, 183);
		textMultiline1 = new CustomTextView(getApplicationContext(), 75, 768, MULTILINE_WIDTH, 500);
		textMultiline2 = new CustomTextView(getApplicationContext(), 75, 1247, MULTILINE_WIDTH, 500);
		textMultiline3 = new CustomTextView(getApplicationContext(), 1800, 75, MULTILINE_WIDTH, 500);

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
		layout.addView(textline1, LINE_WIDTH, 183);
		layout.addView(textline2, LINE_WIDTH, 183);
		layout.addView(textline3, LINE_WIDTH, 183);
		layout.addView(textMultiline1, MULTILINE_WIDTH, 500);
		layout.addView(textMultiline2, MULTILINE_WIDTH, 500);
		layout.addView(textMultiline3, MULTILINE_WIDTH, 500);
	}
	
	private void setLabels() {
		textline1.setText(getString(R.string.label1));
		textline2.setText(getString(R.string.label2));
		textline3.setText(getString(R.string.label3));
		textMultiline1.setText(getString(R.string.multiline1));
		textMultiline2.setText(getString(R.string.multiline2));
		textMultiline3.setText(getString(R.string.multiline3));
	}

	private void setLineHeights(int lineHeight, int multilineHeight) {
		textline1.setLineHeight(lineHeight);
		textline2.setLineHeight(lineHeight);
		textline3.setLineHeight(lineHeight);
		textMultiline1.setLineHeight(multilineHeight);
		textMultiline2.setLineHeight(multilineHeight);
		textMultiline3.setLineHeight(multilineHeight);
	}

	private void setAlighments() {
		textline1.setAlignment(CustomTextView.ALIGN_NORMAL);
		textline2.setAlignment(CustomTextView.ALIGN_CENTER);
		textline3.setAlignment(CustomTextView.ALIGN_OPPOSITE);
		textMultiline1.setAlignment(CustomTextView.ALIGN_NORMAL);
		textMultiline2.setAlignment(CustomTextView.ALIGN_CENTER);
		textMultiline3.setAlignment(CustomTextView.ALIGN_OPPOSITE);
	}

	private void refreshTextFields(Typeface tf) {
		textline1.refresh(tf);
		textline2.refresh(tf);
		textline3.refresh(tf);
		textMultiline1.refresh(tf);
		textMultiline2.refresh(tf);
		textMultiline3.refresh(tf);
	}
	
	public Typeface getTypeface(AssetManager mgr, String fileName) {
		return cache.getFontFromCache(fileName);
	}
}
