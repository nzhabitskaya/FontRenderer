package com.adoramapix.fontrenderer;

import java.util.Map;

import android.app.Application;

public class FontRendererApp extends Application {
	/**
	 * Calculate multiply value, to correlate typography paper size with android screen
	 */
	public static final float PIXEL_SIZE = 72;
	public static final float PAPER_SIZE = 300;
	public static final float MULT_VALUE = PAPER_SIZE / PIXEL_SIZE;

	/**
	 * Given values: font, page margin and box padding
	 */
	public static final float FONT_SIZE = 26;
	public static final int PAGE_MARGIN_TOP = 16;
	public static final float BOX_PADDING = 4;
	public static final float MULTILINE_SPACING = 1.2f;

	/**
	 * Current font metrics values: emSize, ascender, descender, table with
	 * kerning pairs
	 */
	private String fontName;
	private int unitsPerEm = 0;
	private float ascender = 0;
	private float descender = 0;
	private float charHeight = 0;
	private Map<Integer, Map<Integer, Integer>> kerningTable;

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getUnitsPerEm() {
		return unitsPerEm;
	}

	public void setUnitsPerEm(int unitsPerEm) {
		this.unitsPerEm = unitsPerEm;
	}

	public float getAscender() {
		return ascender;
	}

	public void setAscender(float ascender) {
		this.ascender = ascender;
	}

	public float getDescender() {
		return descender;
	}

	public void setDescender(float descender) {
		this.descender = descender;
	}

	public float getCharHeight() {
		return charHeight;
	}

	public void setCharHeight(float charHeight) {
		this.charHeight = charHeight;
	}

	public Map<Integer, Map<Integer, Integer>> getKerningTable() {
		return kerningTable;
	}

	public void setKerningTable(Map<Integer, Map<Integer, Integer>> kerningTable) {
		this.kerningTable = kerningTable;
	}
	
	public float getWinFontSize() {
		return MULT_VALUE * FONT_SIZE;
	}

	public float getWinAscender() {
		return MULT_VALUE * ascender * FONT_SIZE / unitsPerEm;
	}

	public float getWinDescender() {
		return MULT_VALUE * descender * FONT_SIZE / unitsPerEm;
	}
	
	public float getWinPadding() {
		return MULT_VALUE * BOX_PADDING;
	}
	
	public float getWinMultilineSpacing() {
		return getWinFontSize() * MULTILINE_SPACING;
	}

	/**
	 * Take kerning for 2 chars from kerning table
	 * 
	 * @param ch1
	 * @param ch2
	 * @return
	 */
	public int getKerning(char ch1, char ch2) {
		int firstCharPos = toUnicode(ch1);
		int secondCharPos = toUnicode(ch2);

		if (kerningTable.containsKey(firstCharPos)) {
			Map<Integer, Integer> firstKernTable = kerningTable.get(firstCharPos);
			if (firstKernTable.containsKey(secondCharPos)) {
				return firstKernTable.get(secondCharPos);
			}
		}
		return 0;
	}
	
	public float getWinKerning(char ch1, char ch2) {
		return MULT_VALUE * getKerning(ch1, ch2) * FONT_SIZE / unitsPerEm;
	}
	
	private int toUnicode(char ch) {
		int i = (int) ch;  
		return i;
	}
}
