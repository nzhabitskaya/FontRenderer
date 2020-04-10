package com.adoramapix.fontrenderer.ui;

import android.text.StaticLayout;
import android.text.TextPaint;

public class CustomStaticLayout extends StaticLayout{

	public CustomStaticLayout(CharSequence source, TextPaint paint, int width,
			Alignment align, float spacingmult, float spacingadd,
			boolean includepad) {
		super(source, paint, width, align, spacingmult, spacingadd, includepad);
	}
}
