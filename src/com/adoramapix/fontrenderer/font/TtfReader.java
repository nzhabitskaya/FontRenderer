package com.adoramapix.fontrenderer.font;

import java.io.IOException;

import org.apache.fop.fonts.truetype.FontFileReader;
import org.apache.fop.fonts.truetype.TTFFile;

import android.content.Context;

import com.adoramapix.fontrenderer.FontRendererApp;

public class TtfReader {
	private static TtfReader kerning;
	private Context mContext;
	private TTFFile ttfFile;
	private FontFileReader reader;
	
	TtfReader(Context context){
		mContext = context;
		boolean useKerning = true;
        boolean useAdvanced = true;
        ttfFile = new TTFFile(useKerning, useAdvanced);
	}
	
	public static TtfReader getInstance(Context context) {
		if(kerning == null) {
			 kerning = new TtfReader(context);
		}
		return kerning;
	}
	
	public void readFontMetrics(String fontName) {
		try {
            reader = new FontFileReader(mContext.getCacheDir() + "/" + fontName);
            ttfFile.readFont(reader, null);
            saveFontMetrics(ttfFile, fontName);
            
        } catch (IOException ioe) {
            System.err.println("Problem reading font: " + ioe.toString());
            ioe.printStackTrace(System.err);
        }
	}
	
	private void saveFontMetrics(TTFFile ttfFile, String fontName) {
		FontRendererApp app = (FontRendererApp) mContext.getApplicationContext();
		app.setFontName(fontName);
		app.setAscender(ttfFile.getOs2Ascender());
		app.setDescender(ttfFile.getOs2Descender());
		app.setUnitsPerEm(ttfFile.getUpem());
		app.setKerningTable(ttfFile.getKerning());
		app.setCharHeight(ttfFile.getXHeight());
	}
}
