package com.nhncorp.student.sawonjungfinder.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
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

	// thread ��� ���� ����
	private Thread mUiThread;
	final Handler mHandler = new Handler();

	private DbOpenHelper mDbOpenHelper;

	private Boolean isCreated = false;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mDbOpenHelper = new DbOpenHelper(getApplicationContext());
		mDbOpenHelper.open();

		getData();
		System.out.println("======================getData()1================");

		if (Constants.ALARM_STATE.equals("1")
				&& Constants.PERIPHERAL_ONOFF.equals("0")) {
			mDbOpenHelper.updateColumn(1, Constants.DEVICE_NAME,
					Constants.DEVICE_ADDRESS, Constants.ALARM_STATE,
					Constants.DEVICE_STATE, "1");
			setCentralManager();
			System.out
					.println("=====================start alarm service========="
							+ Constants.DEVICE_NAME + "===================");
		} else if (Constants.DEVICE_STATE.equals("1")
				&& Constants.PERIPHERAL_ONOFF.equals("0")) {
			mDbOpenHelper.updateColumn(1, Constants.DEVICE_NAME,
					Constants.DEVICE_ADDRESS, Constants.ALARM_STATE,
					Constants.DEVICE_STATE, "1");
			setCentralManager();
			System.out
					.println("=====================start device service========="
							+ Constants.DEVICE_NAME + "===================");
		}
		isCreated = true;
		mDbOpenHelper.close();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (!isCreated) {
			mDbOpenHelper = new DbOpenHelper(getApplicationContext());
			mDbOpenHelper.open();

			super.onStartCommand(intent, flags, startId);

			getData();
			System.out
					.println("======================getData()2================");

			if (Constants.ALARM_STATE.equals("1")) {
				mDbOpenHelper.updateColumn(1, Constants.DEVICE_NAME,
						Constants.DEVICE_ADDRESS, Constants.ALARM_STATE,
						Constants.DEVICE_STATE, "1");
				setCentralManager();
				System.out
						.println("=====================start alarm service========="
								+ Constants.DEVICE_NAME + "===================");
			} else if (Constants.DEVICE_STATE.equals("1")) {
				mDbOpenHelper.updateColumn(1, Constants.DEVICE_NAME,
						Constants.DEVICE_ADDRESS, Constants.ALARM_STATE,
						Constants.DEVICE_STATE, "1");
				setCentralManager();
				System.out
						.println("=====================start device service========="
								+ Constants.DEVICE_NAME + "===================");
			}
			mDbOpenHelper.close();
			
			return START_STICKY;

		}
		isCreated = false;
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		mDbOpenHelper = new DbOpenHelper(getApplicationContext());
		mDbOpenHelper.open();
		getData();
		System.out.println("=================stop service====================");
		super.onDestroy();
		if (Constants.PERIPHERAL_ONOFF.equals("1")) {
			centralManager.stopScanning();
			mDbOpenHelper.updateColumn(1, Constants.DEVICE_NAME,
					Constants.DEVICE_ADDRESS, Constants.ALARM_STATE,
					Constants.DEVICE_STATE, "0");
		}
		mDbOpenHelper.close();
	}

	private void setCentralManager() {
		centralManager = CentralManager.getInstance();
		centralManager.init(getApplicationContext());
		centralManager.setPeripheralScanListener(new PeripheralScanListener() {
			@Override
			public void onPeripheralScan(Central central,
					final Peripheral peripheral) {
				if (Constants.DEVICE_ADDRESS.equals(peripheral.getBDAddress())) {
					runOnUiThread(new Runnable() {
						public void run() {

							setNotification(peripheral.getDistance());
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
		// push notification
		if (distance > 20) { // distance ���� ������ �ʿ���
			Intent intent = new Intent(
					"com.nhncorp.student.sawonjungfinder.service");
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, 0);

			notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notification = new Notification.Builder(getApplicationContext())
					.setContentTitle("������� �־������ϴ�.")
					.setContentText("������� ��ġ�� Ȯ���Ͻʽÿ�")
					.setSmallIcon(R.drawable.main_icon)
					.setTicker("������� ������ ��ʴϱ�?").setAutoCancel(true)
					.setVibrate(new long[] { 1000, 1000 })
					.setContentIntent(pendingIntent).build();
			System.out.println("push===============================");
			notificationManager.notify(0, notification);

			// vibrate setting
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(1000);
		} else {
		}
	}

	public final void runOnUiThread(Runnable action) {
		if (Thread.currentThread() != mUiThread) {
			mHandler.post(action);
		} else {
			action.run();
		}
	}

	private void getData() {

		Cursor mCursor = mDbOpenHelper.getAll();
		// ��� row�� �޾ƿ�
		mCursor.moveToFirst();
		// �޾ƿ� row�� attribute ���� variable�� ����
		Constants.DEVICE_NAME = mCursor.getString(mCursor
				.getColumnIndex("name"));
		Constants.DEVICE_ADDRESS = mCursor.getString(mCursor
				.getColumnIndex("macaddress"));
		Constants.ALARM_STATE = mCursor.getString(mCursor
				.getColumnIndex("alarmstate"));
		Constants.DEVICE_STATE = mCursor.getString(mCursor
				.getColumnIndex("devicestate"));
		Constants.PERIPHERAL_ONOFF = mCursor.getString(mCursor
				.getColumnIndex("peripheralonoff"));
	}

}
