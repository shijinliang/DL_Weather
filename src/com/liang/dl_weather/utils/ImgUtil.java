package com.liang.dl_weather.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class ImgUtil {

	/**
	 * 工具类，主要根据url读取图片返回流的方法
	 * 
	 * @author ymw
	 * 
	 */

	public static InputStream HandlerImgData(String url) {
		InputStream inStream = null;

		try {
			URL feedUrl = new URL(url);
			URLConnection conn = feedUrl.openConnection();
			conn.setConnectTimeout(10 * 1000);
			inStream = conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return inStream;
	}

	/** 直接返回Drawable对象设置图片 */
	public static Drawable loadImageFromNetwork(String imageUrl) {
		Drawable drawable = null;
		try {
			// 可以在这里通过文件名来判断，是否本地有此图片
			drawable = Drawable.createFromStream(
					new URL(imageUrl).openStream(), "image.jpg");
		} catch (IOException e) {
			Log.d("test", e.getMessage());
		}
		if (drawable == null) {
			Log.d("test", "null drawable");
		} else {
			Log.d("test", "not null drawable");
		}

		return drawable;
	}

	private static String sdcardPathName = Environment.getExternalStorageDirectory()
			+ "/allen_weather/";

	/**
	 * 保存文件
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 */
	public static void saveImage(Bitmap bm, String fileName) throws IOException {
		File dirFile = new File(sdcardPathName);

		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(sdcardPathName + fileName + ".png");

		new ByteArrayOutputStream();

		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
		bos.flush();
		bos.close();
	}

	public static Bitmap getImage(String fileName) {
		Bitmap bp = null;

		File mfile = new File(sdcardPathName + fileName + ".png");

		if (mfile.exists()) {
			bp = BitmapFactory.decodeFile(sdcardPathName + fileName + ".png");
		}

		return bp;
	}

}
