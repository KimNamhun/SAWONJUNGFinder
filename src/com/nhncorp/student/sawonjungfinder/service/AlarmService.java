package com.nhncorp.student.sawonjungfinder.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;

import com.nhncorp.student.sawonjungfinder.R;
import com.nhncorp.student.sawonjungfinder.constants.Constants;
import com.nhncorp.student.sawonjungfinder.database.DbGetSet;
import com.wizturn.sdk.central.Central;
import com.wizturn.sdk.central.CentralManager;
import com.wizturn.sdk.peripheral.Peripheral;
import com.wizturn.sdk.peripheral.PeripheralScanListener;

public class AlarmService extends Service implements LocationListener {

	private CentralManager centralManager;

	// notification
	private NotificationManager notificationManager;
	private Notification notification;
	private Notification notification2; // 사원증 감지중 알람

	// thread 사용 위한 선언
	private Thread mUiThread;
	private final Handler mHandler = new Handler();

	private int setAlarm = 0; // false
	private int setdistance = 0; // false

	// new algorithm variable
	ArrayList<Double> storedArr = new ArrayList<Double>();
	ArrayList<Double> dataArr = new ArrayList<Double>();
	private double sum = 0;
	SimpleDateFormat formatter = new SimpleDateFormat("ss", Locale.KOREA);
	private String scaleTime = "blank";
	private String currentTime = null;
	private static final int FREQUENCY = 10;

	private LocationManager locationManager;
	private String provider;

	private Location loc = null;
	private GpsLocationListener listener = null;

	private DbGetSet dbGetSet;

	private String macAddress;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mTimerHandler.sendEmptyMessage(0);
	}

	Handler mTimerHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Constants.NOTIFYCOUNT++;
			mTimerHandler.sendEmptyMessageDelayed(0, 1000);
			if (Constants.NOTIFYCOUNT > 3) {
				if (notificationManager != null) {
					notificationManager.cancel(0);
				}

			}
		}

	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		setCentralManager();
		dbGetSet = new DbGetSet(this);
		Intent intent2 = new Intent(
				"com.nhncorp.student.sawonjungfinder.service");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent2, 0);
		notification2 = new Notification.Builder(getApplicationContext())
				.setContentTitle("사원증 찾기 동작중").setContentText("서비스가 동작 중입니다.")
				.setSmallIcon(R.drawable.main_icon)
				.setContentIntent(pendingIntent).build();
		macAddress = dbGetSet.getMacAddress();
		System.out.println("=====================start alarm service=========");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		System.out.println("=================stop service====================");
		super.onDestroy();
		if (notificationManager != null) {
			notificationManager.cancel(0);
		}
		centralManager.stopScanning();
	}

	private void setCentralManager() {

		centralManager = CentralManager.getInstance();
		centralManager.init(getApplicationContext());
		centralManager.setPeripheralScanListener(new PeripheralScanListener() {
			@Override
			public void onPeripheralScan(Central central,
					final Peripheral peripheral) {
				if (macAddress.equals(peripheral.getBDAddress())) {
					runOnUiThread(new Runnable() {
						public void run() {

							// data를 받은 시간 저장
							currentTime = formatter.format(new Date());
							if (currentTime.equals(scaleTime)) {
								setEqualTime(peripheral);
							} else {
								setDifferentTime(peripheral);
								if (macAddress.equals(peripheral.getBDAddress())) {
									setNotification(Constants.DISTANCE);
								}
							}

						}
					});

				}
			}

		});
		centralManager.startScanning();
	}

	private void setEqualTime(Peripheral peripheral) {
		// UUID가 같으면 넣고 아니면 넣지 않는다.
		if (macAddress.equals(peripheral.getBDAddress())) {
			Constants.NOTIFYCOUNT = 0;
			if (storedArr.size() < FREQUENCY) {
				storedArr.add(0, peripheral.getDistance());
			} else {
				dataArr.add(0, peripheral.getDistance());
			}
		}
	}

	private void setDifferentTime(Peripheral peripheral) {
		// storedArr size check
		if (storedArr.size() == FREQUENCY) {
			// 10이면 caluclateDistance
			calculateDistance();
			// UUId가 같으면 저장
			if (macAddress.equals(peripheral.getBDAddress())) {
				Constants.NOTIFYCOUNT = 0;
				dataArr.add(0, peripheral.getDistance());
			}
		} else {
			// storedArr.size가 10이 아니면
			if (macAddress.equals(peripheral.getBDAddress())) {
				Constants.NOTIFYCOUNT = 0;
				storedArr.add(0, peripheral.getDistance());
			}
		}
		scaleTime = currentTime;
	}

	private void calculateDistance() {
		// dataArr.size check
		if (dataArr.size() != FREQUENCY) {
			// size 맞춘다
			if (dataArr.size() == 0) {
				dataArr = (ArrayList<Double>) storedArr.clone();
			} else {
				/*
				 * original code : for(int i = 0; i<FREQUENCY-dataArr.size();
				 * i++){ dataArr.add(0,storedArr.get(i)); } thread의 시간차때문에
				 * outOfIndexException 발생
				 */
				if (dataArr.size() == 1) {
					dataArr.add(storedArr.get(8));
					dataArr.add(storedArr.get(7));
					dataArr.add(storedArr.get(6));
					dataArr.add(storedArr.get(5));
					dataArr.add(storedArr.get(4));
					dataArr.add(storedArr.get(3));
					dataArr.add(storedArr.get(2));
					dataArr.add(storedArr.get(1));
					dataArr.add(storedArr.get(0));
				} else if (dataArr.size() == 2) {
					dataArr.add(storedArr.get(7));
					dataArr.add(storedArr.get(6));
					dataArr.add(storedArr.get(5));
					dataArr.add(storedArr.get(4));
					dataArr.add(storedArr.get(3));
					dataArr.add(storedArr.get(2));
					dataArr.add(storedArr.get(1));
					dataArr.add(storedArr.get(0));
				} else if (dataArr.size() == 3) {
					dataArr.add(storedArr.get(6));
					dataArr.add(storedArr.get(5));
					dataArr.add(storedArr.get(4));
					dataArr.add(storedArr.get(3));
					dataArr.add(storedArr.get(2));
					dataArr.add(storedArr.get(1));
					dataArr.add(storedArr.get(0));
				} else if (dataArr.size() == 4) {
					dataArr.add(storedArr.get(5));
					dataArr.add(storedArr.get(4));
					dataArr.add(storedArr.get(3));
					dataArr.add(storedArr.get(2));
					dataArr.add(storedArr.get(1));
					dataArr.add(storedArr.get(0));
				} else if (dataArr.size() == 5) {
					dataArr.add(storedArr.get(4));
					dataArr.add(storedArr.get(3));
					dataArr.add(storedArr.get(2));
					dataArr.add(storedArr.get(1));
					dataArr.add(storedArr.get(0));
				} else if (dataArr.size() == 6) {
					dataArr.add(storedArr.get(3));
					dataArr.add(storedArr.get(2));
					dataArr.add(storedArr.get(1));
					dataArr.add(storedArr.get(0));
				} else if (dataArr.size() == 7) {
					dataArr.add(storedArr.get(2));
					dataArr.add(storedArr.get(1));
					dataArr.add(storedArr.get(0));
				} else if (dataArr.size() == 8) {
					dataArr.add(storedArr.get(1));
					dataArr.add(storedArr.get(0));
				} else if (dataArr.size() == 9) {
					dataArr.add(storedArr.get(0));
				}
			}
		}
		if (dataArr.size() == 10) {
			// avg
			sum = dataArr.get(0) + dataArr.get(1) + dataArr.get(2)
					+ dataArr.get(3) + dataArr.get(4) + dataArr.get(5)
					+ dataArr.get(6) + dataArr.get(7) + dataArr.get(8)
					+ dataArr.get(9);
			Constants.DISTANCE = (int) Math.round(sum / FREQUENCY);
			// storedArr 갱신
			storedArr = (ArrayList<Double>) dataArr.clone();
		}
		// dataArr.clear
		dataArr.clear();
	}

	private void setNotification(double distance) {

		if (Constants.DISTANCE < 10 && Constants.DISTANCE > 3) {
			setAlarm = 1;
		}

		if (Constants.DISTANCE > 15 && setAlarm == 1) {
			loadGps();
			getgps();
		}

		if (setdistance == 1 && Constants.DISTANCE < 10
				&& Constants.DISTANCE > 3) {
			if (notificationManager != null) {
				notificationManager.cancel(1);
				setdistance = 0;
			}
		}

		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		System.out.println("push=============================== service");
		notificationManager.notify(0, notification2);
		// push notification

	}

	public final void runOnUiThread(Runnable action) {
		if (Thread.currentThread() != mUiThread) {
			mHandler.post(action);

		} else {
			action.run();

		}
	}

	public void getgps() {

		loc = getLocation();
		locationManager.removeUpdates(listener);

		if (loc == null) {
			setDistanceNullNotify();
		} else {
			setDistanceNotNullNotify();
			new Location(loc);
			dbGetSet.setLongitude(Double.toString(loc.getLongitude()));
			dbGetSet.setLatitude(Double.toString(loc.getLatitude()));
		}

	}

	public void setDistanceNullNotify() {
		Intent intent = new Intent(
				"com.nhncorp.student.sawonjungfinder.service");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification = new Notification.Builder(getApplicationContext())
				.setContentTitle("사원증이 멀어졌습니다.")
				.setContentText("WARNING! 위치 정보가 저장되지 않았습니다.")
				.setSmallIcon(R.drawable.alarm_icon)
				.setTicker("사원증을 가지고 계십니까?").setAutoCancel(true)
				.setVibrate(new long[] { 1000, 1000 })
				.setContentIntent(pendingIntent).build();
		System.out.println("push===============================alarm");
		notificationManager.notify(1, notification);
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);
		setAlarm = 0;
		setdistance = 1;

	}

	public void setDistanceNotNullNotify() {
		Intent intent = new Intent(
				"com.nhncorp.student.sawonjungfinder.service");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification = new Notification.Builder(getApplicationContext())
				.setContentTitle("사원증이 멀어졌습니다.")
				.setContentText("위치 정보가 저장되었습니다.")
				.setSmallIcon(R.drawable.alarm_icon)
				.setTicker("사원증을 가지고 계십니까?").setAutoCancel(true)
				.setVibrate(new long[] { 1000, 1000 })
				.setContentIntent(pendingIntent).build();
		System.out.println("push===============================alarm");
		notificationManager.notify(1, notification);
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);
		setAlarm = 0;
		setdistance = 1;
	}

	private Location getLocation() {

		Location location = locationManager.getLastKnownLocation(provider);

		if (location == null) {
			System.out
					.println("NETWORK+++++++++++++++++++++++++++++++++++++++++");

			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		}

		return location;
	}

	public void loadGps() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 정확도
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 전원 소비량
		criteria.setAltitudeRequired(false); // 고도, 높이 값을 얻어 올지를 결정
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false); // 속도
		criteria.setCostAllowed(true); // 위치 정보를 얻어 오는데 들어가는 금전적 비용
		provider = locationManager.getBestProvider(criteria, true);
		listener = new GpsLocationListener();
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			System.out.println("GPS+++++++++++++++++++++++++++++++++++++++++");
			locationManager.requestLocationUpdates(provider, 1000, 5, listener);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	private class GpsLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {

		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	}

}
