package com.adoramapix.fontrenderer.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class CacheManager {
	private static final long MAX_SIZE = 5242880L; // 5MB cache directory size
	private final String TAG = CacheManager.class.getName();
	private Context mContext;

	public CacheManager(Context context) {
		mContext = context;
	}

	public void cacheData(Context context, byte[] data, String name) throws IOException {

		File cacheDir = context.getCacheDir();
		long size = getDirSize(cacheDir);
		long newSize = data.length + size;

		if (newSize > MAX_SIZE) {
			cleanDir(cacheDir, newSize - MAX_SIZE);
		}

		File file = new File(cacheDir, name);
		FileOutputStream os = new FileOutputStream(file);
		try {
			os.write(data);
		} finally {
			os.flush();
			os.close();
		}
	}

	private static void cleanDir(File dir, long bytes) {

		long bytesDeleted = 0;
		File[] files = dir.listFiles();

		for (File file : files) {
			bytesDeleted += file.length();
			file.delete();

			if (bytesDeleted >= bytes) {
				break;
			}
		}
	}

	private static long getDirSize(File dir) {

		long size = 0;
		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isFile()) {
				size += file.length();
			}
		}

		return size;
	}

	/**
	 * Copy font from assets to cache directory.
	 * @param assetManager
	 * @param fromAssetPath
	 * @param toPath
	 * @return
	 */
	private void cacheFontFromAssets(String fileName, String toPath) {
		InputStream in = null;
		OutputStream out = null;		
		try {
			in = mContext.getAssets().open(fileName);
			new File(toPath).createNewFile();
			out = new FileOutputStream(toPath);
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
			Log.i(TAG, "File was saved to path: " + toPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Typeface getFontFromCache(String fileName) {
		String toPath = mContext.getCacheDir() + "/" + fileName;
		File file = new File(toPath);
		if(!file.exists())
			cacheFontFromAssets(fileName, toPath);
		return Typeface.createFromFile(toPath);
	}
	
	private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }
}