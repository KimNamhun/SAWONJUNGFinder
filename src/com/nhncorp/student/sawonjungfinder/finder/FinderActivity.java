package com.nhncorp.student.sawonjungfinder.finder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhncorp.student.sawonjungfinder.R;
import com.nhncorp.student.sawonjungfinder.R.drawable;
import com.nhncorp.student.sawonjungfinder.constants.Constants;

public class FinderActivity extends Activity {

	private ImageView distanceImg;
	private Thread myThread = null;
	private boolean runThread = true;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if (myThread != null && myThread.isAlive()) {
					runThread = false;
				}
				this.finish();

			}
		}
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_finder);
		init();

	}

	private void init() {
		confirmService();
		getView();

	}

	private void confirmService() {
		if (Constants.DEVICE_STATE.equals("1")) {
		} else if (Constants.DEVICE_STATE.equals("0")) {
			Toast.makeText(this, "서비스가 꺼져있습니다.", Toast.LENGTH_LONG).show();
		}

	}

	private void getView() {
		distanceImg = (ImageView) findViewById(R.id.distanceImg);

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			updateThread();
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		myThread = new Thread(new Runnable() {
			public void run() {
				while (runThread) {
					try {
						handler.sendMessage(handler.obtainMessage());
						Thread.sleep(100);
					} catch (Throwable t) {
					}
				}
			}
		});
		if (Constants.DEVICE_STATE.equals("1")) {
			myThread.start();
		}
	}

	private void updateThread() {
		System.out.println("Distance ***************************************"
				+ Constants.DISTANCE);
		System.out
				.println("notifycount ***************************************"
						+ Constants.NOTIFYCOUNT);
		if (Constants.NOTIFYCOUNT >= 4) {
			distanceImg.setBackgroundResource(drawable.wifi_icon_0);
		} else if (Constants.DISTANCE >= 0 && Constants.DISTANCE < 9) {
			distanceImg.setBackgroundResource(drawable.wifi_icon_3);
		} else if (Constants.DISTANCE >= 0 && Constants.DISTANCE >= 11
				&& Constants.DISTANCE < 17) {
			distanceImg.setBackgroundResource(drawable.wifi_icon_2);
		} else if (Constants.DISTANCE >= 0 && Constants.DISTANCE > 19) {
			distanceImg.setBackgroundResource(drawable.wifi_icon_1);
		}

	}
}
