package com.liang.dl_weather.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.liang.dl_weather.R;

public class WeatherUtil {
	// ��|����|��|����|������|��������б���|���ѩ|С��|����|����|����|����|�ش���|��ѩ|Сѩ|��ѩ|��ѩ|��ѩ|��|����|ɳ����|С��ת����|
	// ����ת����|����ת����|����ת����|����ת�ش���|Сѩת��ѩ|��ѩת��ѩ|��ѩת��ѩ|����|��ɳ|ǿɳ����|��
	public static Bitmap getWeatherImg(Context context, String weather) {

		Resources res = context.getResources();
		Bitmap bmp = null;
		if (weather.equals("��")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d0);
		} else if (weather.equals("����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d1);
		} else if (weather.equals("��")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d2);
		} else if (weather.equals("����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d3);
		} else if (weather.equals("������")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d4);
		} else if (weather.equals("��������б���")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d5);
		} else if (weather.equals("���ѩ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d6);
		} else if (weather.equals("С��")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d7);
		} else if (weather.equals("����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d8);
		} else if (weather.equals("����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d9);
		} else if (weather.equals("����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d10);
		} else if (weather.equals("����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d11);
		} else if (weather.equals("�ش���")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d12);
		} else if (weather.equals("��ѩ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d13);
		} else if (weather.equals("Сѩ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d14);
		} else if (weather.equals("��ѩ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d15);
		} else if (weather.equals("��ѩ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d16);
		} else if (weather.equals("��ѩ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d17);
		} else if (weather.equals("��")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d18);
		} else if (weather.equals("����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d19);
		} else if (weather.equals("ɳ����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d20);
		} else if (weather.equals("С��ת����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d21);
		} else if (weather.equals("����ת����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d22);
		} else if (weather.equals("����ת����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d23);
		} else if (weather.equals("����ת����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d24);
		} else if (weather.equals("����ת�ش���")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d25);
		} else if (weather.equals("Сѩת��ѩ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d26);
		} else if (weather.equals("��ѩת��ѩ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d27);
		} else if (weather.equals("��ѩת��ѩ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d28);
		} else if (weather.equals("����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d29);
		} else if (weather.equals("��ɳ")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d30);
		} else if (weather.equals("ǿɳ����")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d31);
		} else if (weather.equals("��")) {
			bmp = BitmapFactory.decodeResource(res, R.drawable.d53);
		} else {
			 bmp = BitmapFactory.decodeResource(res, R.drawable.d1);
		}
		return bmp;

	}

}
