package com.liang.dl_weather;

import java.util.ArrayList;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.dl_weather.bean.WeatherDataBean;
import com.liang.dl_weather.bean.WeatherIndexBean;
import com.liang.dl_weather.utils.SPUtils;
import com.liang.dl_weather.utils.StringUtil;
import com.liang.dl_weather.utils.WeatherUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 锟斤拷权锟斤拷锟斤拷   锟斤拷锟斤拷锟斤拷锟斤拷系lygttpod@163.com 
 * 
 * 锟斤拷锟斤拷时锟斤拷 20150404 
 * 
 * @author allen 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
 * 
 */
public class MainActivity extends Activity {

	private String provider;
	private String locationString;
	// private TextView showLocaltionTV;

	private AsyncHttpClient asyncHttpClient;

	private PullToRefreshScrollView pullToRefreshScrollView;

	private ArrayList<WeatherIndexBean> weatherIndexBeans;
	private ArrayList<WeatherDataBean> weatherDataBeans;
	private static int UPDATA = 1;

	private LinearLayout layout_body, layout_today;
	private TextView date, weather, wind, temperature;

	private TextView date_one, weather_one, wind_one, temperature_one;

	private TextView date_two, weather_two, wind_two, temperature_two;

	private TextView date_three, weather_three, wind_three, temperature_three;
	private ImageView[] pic = new ImageView[3];
	private String[] params;
	private TextView textView_city_show;
	private String currentCity;

	private LocationClient locationClient = null;
	private LocationMode tempMode = LocationMode.Battery_Saving;
	private static final int UPDATE_TIME = 1000 * 60 * 60 * 8;
	private static int LOCATION_COUTNS = 0;
	private String UpdateTime = "";
	
	private RelativeLayout l ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		
		
		SpotManager.getInstance(this).loadSpotAds();
		// 插屏出现动画效果，0:ANIM_NONE为无动画，1:ANIM_SIMPLE为简单动画效果，2:ANIM_ADVANCE为高级动画效果
		SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);
		// 设置插屏动画的横竖屏展示方式，如果设置了横屏，则在有广告资源的情况下会是优先使用横屏图。
		SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
		
		
		weatherDataBeans = new ArrayList<WeatherDataBean>();
		weatherIndexBeans = new ArrayList<WeatherIndexBean>();
		asyncHttpClient = new AsyncHttpClient();
		layout_today = (LinearLayout) findViewById(R.id.linearlayout_today);
		layout_body = (LinearLayout) findViewById(R.id.linearLayout_body);
		
		initWeatherBody();
		initLocation();
		initPullToRefreshScrollView();
		
		showBanner();
	}
	
	private void showBanner()
	{
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);

		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.linearLayout_ad);

		// 将广告条加入到布局中
		adLayout.addView(adView);
		
		if( Constants.x )
		{
			Constants.x = false;
			return;
		}
		
		SpotManager.getInstance(MainActivity.this).showSpotAds(MainActivity.this, new SpotDialogListener() {
			
			@Override
			public void onSpotClosed() {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "展示关闭", 1).show();
			}
			
			@Override
			public void onShowSuccess() {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "展示成功", 1).show();
			}
			
			@Override
			public void onShowFailed() {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "展示失败", 1).show();
			}
		});
	}
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (locationClient != null && locationClient.isStarted()) {
			locationClient.stop();
			locationClient = null;
		}
	}

	private void initWeatherBody() {
		textView_city_show = (TextView) findViewById(R.id.textView_city_show);

		date = (TextView) findViewById(R.id.today_date);
		weather = (TextView) findViewById(R.id.today_weather);
		wind = (TextView) findViewById(R.id.today_wind);
		temperature = (TextView) findViewById(R.id.today_temperature);

		date_one = (TextView) findViewById(R.id.textView_date_one);
		weather_one = (TextView) findViewById(R.id.textView_weather_one);
		wind_one = (TextView) findViewById(R.id.textView_wind_one);
		temperature_one = (TextView) findViewById(R.id.textView_temperature_one);
		pic[0] = (ImageView) findViewById(R.id.imageView_pic_one);

		date_two = (TextView) findViewById(R.id.textView_date_two);
		weather_two = (TextView) findViewById(R.id.textView_weather_two);
		wind_two = (TextView) findViewById(R.id.textView_wind_two);
		temperature_two = (TextView) findViewById(R.id.textView_temperature_two);
		pic[1] = (ImageView) findViewById(R.id.imageView_pic_two);

		date_three = (TextView) findViewById(R.id.textView_date_three);
		weather_three = (TextView) findViewById(R.id.textView_weather_three);
		wind_three = (TextView) findViewById(R.id.textView_wind_three);
		temperature_three = (TextView) findViewById(R.id.textView_temperature_three);
		pic[2] = (ImageView) findViewById(R.id.imageView_pic_three);
		for (int j = 0; j < 4; j++) {
			String dateString = (String) SPUtils.get(MainActivity.this, "date"
					+ j, "");
			String weatherString = (String) SPUtils.get(MainActivity.this,
					"weather" + j, "");
			String windString = (String) SPUtils.get(MainActivity.this, "wind"
					+ j, "");
			String temperatureString = (String) SPUtils.get(MainActivity.this,
					"temperature" + j, "");

			WeatherDataBean weatherDataBean = new WeatherDataBean(dateString,
					"", "", weatherString, windString, temperatureString);
			weatherDataBeans.add(weatherDataBean);
		}

		updataWeather();

	}

	private void updataWeather() {
		String currentCity = (String) SPUtils.get(MainActivity.this,
				"currentCity", "");
		textView_city_show.setText(currentCity);
		if (!weatherDataBeans.get(0).getDate().equals("")) {
			if (weatherDataBeans.get(0).getDate().length() > 9) {
				String dateString = StringUtil.Substring(weatherDataBeans
						.get(0).getDate());
				date.setText(dateString);

			} else {
				String dataString1 = StringUtil.Substring1(weatherDataBeans
						.get(0).getTemperature());

				date.setText(dataString1);
			}
		}

		weather.setText(weatherDataBeans.get(0).getWeather());
		wind.setText(weatherDataBeans.get(0).getWind());
		temperature.setText(weatherDataBeans.get(0).getTemperature());

		date_one.setText(weatherDataBeans.get(1).getDate());
		weather_one.setText(weatherDataBeans.get(1).getWeather());
		wind_one.setText(weatherDataBeans.get(1).getWind());
		temperature_one.setText(weatherDataBeans.get(1).getTemperature());

		date_two.setText(weatherDataBeans.get(2).getDate());
		weather_two.setText(weatherDataBeans.get(2).getWeather());
		wind_two.setText(weatherDataBeans.get(2).getWind());
		temperature_two.setText(weatherDataBeans.get(2).getTemperature());

		date_three.setText(weatherDataBeans.get(3).getDate());
		weather_three.setText(weatherDataBeans.get(3).getWeather());
		wind_three.setText(weatherDataBeans.get(3).getWind());
		temperature_three.setText(weatherDataBeans.get(3).getTemperature());
		for (int i = 0; i < weatherDataBeans.size(); i++) {
			SPUtils.put(MainActivity.this, "date" + i, weatherDataBeans.get(i)
					.getDate());
			SPUtils.put(MainActivity.this, "weather" + i,
					weatherDataBeans.get(i).getWeather());
			SPUtils.put(MainActivity.this, "wind" + i, weatherDataBeans.get(i)
					.getWind());
			SPUtils.put(MainActivity.this, "temperature" + i, weatherDataBeans
					.get(i).getTemperature());
		}

		for (int i = 0; i < 3; i++) {
			Log.d("allen",
					"i=" + i + "getWeather="
							+ weatherDataBeans.get(i + 1).getWeather());
			pic[i].setImageBitmap(WeatherUtil.getWeatherImg(MainActivity.this,
					weatherDataBeans.get(i + 1).getWeather()));
		}

	}

	private void initLocation() {

		locationClient = new LocationClient(this);
		// 锟斤拷锟矫讹拷位锟斤拷锟斤拷
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 锟斤拷锟矫讹拷位模式
		option.setOpenGps(false); // 锟角凤拷锟紾PS
		option.setCoorType("gcj02"); // 锟斤拷锟矫凤拷锟斤拷值锟斤拷锟斤拷锟斤拷锟斤拷锟酵★拷

		option.setProdName("LocationWeather"); // 锟斤拷锟矫诧拷品锟斤拷锟斤拷锟狡★拷强锟揭斤拷锟斤拷锟斤拷使锟斤拷锟皆讹拷锟斤拷牟锟狡凤拷锟斤拷锟斤拷疲锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷院锟轿拷锟斤拷峁╋拷锟斤拷锟叫ё既凤拷亩锟轿伙拷锟斤拷锟�
		option.setScanSpan(UPDATE_TIME); // 锟斤拷锟矫讹拷时锟斤拷位锟斤拷时锟斤拷锟斤拷锟斤拷锟斤拷位锟斤拷锟斤拷
		locationClient.setLocOption(option);

		// 注锟斤拷位锟矫硷拷锟斤拷锟斤拷
		locationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				// Receive Location
				StringBuffer sb = new StringBuffer(256);
				sb.append(location.getLongitude());
				sb.append(",");
				sb.append(location.getLatitude());

				Log.d("allen", sb.toString());
				if (!sb.toString().equals("")) {
					sendWeatherRequest(sb.toString());
					locationClient.stop();
				} else {
					Toast.makeText(MainActivity.this, "刷新中……", 1).show();
				}

			}
		});
		locationClient.start();
	}

	private void showLocation(Location location) {
		String currentPosition = "latitude is " + location.getLatitude() + "\n"
				+ "longitude is " + location.getLongitude();
		android.util.Log.d("allen", currentPosition);
		// showLocaltionTV.setText(currentPosition);
	}

	private void sendWeatherRequest(String location) {
		if (location != null) {
			// 锟斤拷示锟斤拷前锟借备锟斤拷位锟斤拷锟斤拷息

			String path = "http://api.map.baidu.com/telematics/v3/weather?location="
					+ location + "&output=json&ak=RUD7mk38fQdG0ZjcLCyigc2u";
			// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
			asyncHttpClient.get(path, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// TODO Auto-generated method stub
					//刷新成功提示
					Toast.makeText(MainActivity.this, "刷新成功",
							Toast.LENGTH_SHORT).show();
					
					showBanner();
					
					//UpdateTime = DateUtils.
					String jsonStr = new String(arg2);
					weatherDataBeans.clear();
					try {
						currentCity = WeatherJson(jsonStr);
						SPUtils.put(MainActivity.this, "currentCity",
								currentCity);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					handler.sendEmptyMessage(UPDATA);

					// showLocaltionTV.setText(jsonStr);

				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
					//刷新失败提示
					Toast.makeText(MainActivity.this, "刷新失败，\n请检查网络连接！",
							Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();

					pullToRefreshScrollView.onRefreshComplete();

				}
			});

		}
	}

	private void initPullToRefreshScrollView() {
		//String label = "";

		// �⼸��ˢ��Label������
		pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		// s.smoothScrollBy(0, 0);

		
		pullToRefreshScrollView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
		pullToRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
				"正在刷新");
		pullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("松手刷新");
		ScrollView scrollView = pullToRefreshScrollView.getRefreshableView();
		// scrollView.scrollTo(0, 0);
		scrollView.smoothScrollTo(0, 0);
		// pullToRefreshScrollView.scrollTo(0, 0);
		// �����������趨
		// pullToRefreshScrollView.setMode(Mode.PULL_FROM_START);

		pullToRefreshScrollView.setMode(Mode.PULL_FROM_START);
		pullToRefreshScrollView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						// TODO Auto-generated method stub
						
						//刷新时间
						String label = DateUtils.formatDateTime(getApplicationContext(),
								System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						
						pullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
								"上次更新于：" + label);
						
						initLocation();
					}
				});

	}

	public String WeatherJson(String jsonStr) throws Exception {
		String currentCity = "";

		JSONObject weatherJson = new JSONObject(jsonStr);
		String status = weatherJson.getString("status");
		if (status.equals("success")) {
			JSONArray results = weatherJson.getJSONArray("results");
			JSONObject obj = results.getJSONObject(0);
			currentCity = obj.getString("currentCity");
			String pm25 = obj.getString("pm25");

			System.out.println(currentCity + pm25);
			JSONArray indexArr = obj.getJSONArray("index");
			for (int i = 0; i < indexArr.length(); i++) {
				JSONObject index = indexArr.getJSONObject(i);
				WeatherIndexBean weatherIndexBean = new WeatherIndexBean(
						index.getString("title"), index.getString("zs"),
						index.getString("tipt"), index.getString("des"));
				weatherIndexBeans.add(weatherIndexBean);
				// System.out.println(index.getString("title"));
				// System.out.println(index.getString("zs"));
				// System.out.println(index.getString("tipt"));
				// System.out.println(index.getString("des"));
			}
			JSONArray weather_dataArr = obj.getJSONArray("weather_data");
			for (int i = 0; i < weather_dataArr.length(); i++) {
				JSONObject weather_data = weather_dataArr.getJSONObject(i);
				WeatherDataBean weatherDataBean = new WeatherDataBean(
						weather_data.getString("date"),
						weather_data.getString("dayPictureUrl"),
						weather_data.getString("nightPictureUrl"),
						weather_data.getString("weather"),
						weather_data.getString("wind"),
						weather_data.getString("temperature"));
				weatherDataBeans.add(weatherDataBean);

			}
		}
		return currentCity;

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				updataWeather();
				break;
			case 2:
				Log.d("allen",
						"weatherDataBeans.size()=" + weatherDataBeans.size());
				for (int i = 0; i < 3; i++) {
					Log.d("allen", "i=" + i + "getWeather="
							+ weatherDataBeans.get(i + 1).getWeather());
					pic[i].setImageBitmap(WeatherUtil.getWeatherImg(
							MainActivity.this, weatherDataBeans.get(i + 1)
									.getWeather()));
				}

				break;

			default:
				break;
			}
		};
	};

}
