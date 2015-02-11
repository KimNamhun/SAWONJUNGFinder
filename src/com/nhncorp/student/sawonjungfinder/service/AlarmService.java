package com.nhncorp.student.sawonjungfinder.service;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;

import com.nhncorp.student.sawonjungfinder.R;
import com.nhncorp.student.sawonjungfinder.constants.Constants;
import com.nhncorp.student.sawonjungfinder.database.DbOpenHelper;
import com.wizturn.sdk.central.Central;
import com.wizturn.sdk.central.CentralManager;
import com.wizturn.sdk.peripheral.Peripheral;
import com.wizturn.sdk.peripheral.PeripheralScanListener;

public class AlarmService extends Service {

	private CentralManager centralManager;

	// notification
	NotificationManager notificationManager;
	Notification notification;

	// thread 사용 위한 선언
	private Thread mUiThread;
	final Handler mHandler = new Handler();

	private DbOpenHelper mDbOpenHelper;

	ArrayList<Double> valueArr = new ArrayList<Double>();

	private int sumCount = 0;
	private int sum = 0;

	private int setAlarm = 0;

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
			mTimerHandler.sendEmptyMessageDelayed(0, Constants.NOTIFYSPEED);
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		setCentralManager();
		System.out.println("=====================start alarm service========="
				+ Constants.DEVICE_NAME + "===================");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		System.out.println("=================stop service====================");
		super.onDestroy();
		if (notificationManager != null) {
			notificationManager.cancel(0);
		}
		Constants.NOTIFYCOUNT = 5;
		centralManager.stopScanning();
	}

	private void setCentralManager() {
		getData();
		centralManager = CentralManager.getInstance();
		centralManager.init(getApplicationContext());
		centralManager.setPeripheralScanListener(new PeripheralScanListener() {
			@Override
			public void onPeripheralScan(Central central,
					final Peripheral peripheral) {

				if (Constants.NOTIFYCOUNT >= 5) {
					if (notificationManager != null) {
						notificationManager.cancel(0);
					
					}

					Constants.NOTIFYCOUNT = 0;
				}
				if (Constants.DEVICE_ADDRESS.equals(peripheral.getBDAddress())) {
					runOnUiThread(new Runnable() {
						public void run() {

							// 거리 수신값 최적화
							valueArr.add(peripheral.getDistance());
							sumCount = sumCount + 1;
							sum += peripheral.getDistance();
							if (sumCount == 10) {
								int avg = sum / 10;
								for (int i = 0; i < valueArr.size(); i++) {
									if (valueArr.get(i) > avg + 3
											|| valueArr.get(i) < avg - 3)
										valueArr.remove(i);
								}
								sum = 0;
								for (int i = 0; i < valueArr.size(); i++) {
									sum += valueArr.get(i);
								}
								avg = sum / valueArr.size(); // 환산된 avg

								sumCount = 5;
								sum = avg * 5;
								valueArr.clear();
								for (int i = 0; i < 5; i++) {
									valueArr.add((double) avg);
								}

								setNotification(avg);
								Constants.DISTANCE = avg;
							}

							System.out
									.println("notification================================");
						}

					});

				}
			}

		});
		centralManager.startScanning();
	}

	private void setNotification(double distance) {

		Intent intent = new Intent(
				"com.nhncorp.student.sawonjungfinder.service");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);

		if (distance < 9 && distance > 3) {
			setAlarm = 1;
		}

		if (distance > 18 && setAlarm == 1) { // distance 값의 조절이 필요함
			notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notification = new Notification.Builder(getApplicationContext())
					.setContentTitle("사원증이 멀어졌습니다.")
					.setContentText("사원증의 위치를 확인하십시오")
					.setSmallIcon(R.drawable.alarm_icon)
					.setTicker("사원증을 가지고 계십니까?").setAutoCancel(true)
					.setVibrate(new long[] { 1000, 1000 })
					.setContentIntent(pendingIntent).build();
			System.out.println("push===============================alarm");
			notificationManager.notify(1, notification);

			// vibrate setting
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(1000);
			setAlarm = 0;
		}

		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification = new Notification.Builder(getApplicationContext())
				.setContentTitle("사원증 찾기 동작중").setContentText("서비스가 동작 중입니다.")
				.setSmallIcon(R.drawable.main_icon).setAutoCancel(true)
				.setContentIntent(pendingIntent).build();
		System.out.println("push=============================== service");
		notificationManager.notify(0, notification);
		// push notification

	}

	public final void runOnUiThread(Runnable action) {
		if (Thread.currentThread() != mUiThread) {
			mHandler.post(action);

		} else {
			action.run();

		}
	}

	private void getData() {
		mDbOpenHelper = new DbOpenHelper(this);
		mDbOpenHelper.open();
		Cursor mCursor = mDbOpenHelper.getAll();
		// 모든 row를 받아옴
		mCursor.moveToFirst();
		System.out.println(mCursor.getString(mCursor.getColumnIndex("name")));// test
		System.out.println(mCursor.getString(mCursor
				.getColumnIndex("macaddress")));// test
		// 받아온 row의 attribute 값을 variable에 저장
		Constants.DEVICE_NAME = mCursor.getString(mCursor
				.getColumnIndex("name"));
		Constants.DEVICE_ADDRESS = mCursor.getString(mCursor
				.getColumnIndex("macaddress"));
		Constants.DEVICE_STATE = mCursor.getString(mCursor
				.getColumnIndex("devicestate"));
		Constants.LONGITUDE = mCursor.getString(mCursor
				.getColumnIndex("longitude"));
		Constants.LATITUDE = mCursor.getString(mCursor
				.getColumnIndex("latitude"));
		mDbOpenHelper.close();
	}
}
