package com.nhncorp.student.sawonjungfinder.finder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhncorp.student.sawonjungfinder.R;
import com.nhncorp.student.sawonjungfinder.R.drawable;
import com.nhncorp.student.sawonjungfinder.constants.Constants;
import com.nhncorp.student.sawonjungfinder.database.DbGetSet;
import com.nhncorp.student.sawonjungfinder.map.MapActivity;

public class FinderActivity extends Activity {

	private ImageView distanceImg;
	private ImageView distanceData;
	private ImageView distanceMessage;
	private ImageButton mapBtn;

	private Thread myThread = null;
	private boolean runThread = true;

	int countVibrator = 0;

	private DbGetSet dbGetSet;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if (myThread != null && myThread.isAlive()) {
					runThread = false;
				}
				this.finish();
			default:
			}
		}
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_finder);
		dbGetSet = new DbGetSet(this);
		init();
	}

	private void init() {
		confirmService();
		getView();
		addListener();
	}

	private void addListener() {
		mapBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FinderActivity.this,
						MapActivity.class);
				startActivity(intent);
			}
		});

	}

	private void confirmService() {
		if (dbGetSet.getDeviceState().equals("1")) {
		} else if (dbGetSet.getDeviceState().equals("0")) {
			Toast.makeText(this, "서비스가 꺼져있습니다.", Toast.LENGTH_LONG).show();
		}
	}

	private void getView() {

		distanceData = (ImageView) findViewById(R.id.distanceData);
		distanceMessage = (ImageView) findViewById(R.id.distanceMessage);
		distanceImg = (ImageView) findViewById(R.id.distanceImg);
		mapBtn = (ImageButton) findViewById(R.id.mapBtn);

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
		if (dbGetSet.getDeviceState().equals("1")) {
			myThread.start();
		}
	}

	private void updateThread() {

		// 거리는 1m단위로 제공
		if (Constants.DISTANCE <= 30) {
			String sDistance = "finder_m" + Constants.DISTANCE;
			int distanceId = this.getResources().getIdentifier(sDistance,
					"drawable", this.getPackageName());
			distanceData.setBackgroundResource(distanceId);
		} else {
			distanceData.setBackgroundResource(drawable.finder_m40);
		}

		// 2m 이하일 때 메세지 표시, 진동 발생
		if (Constants.DISTANCE <= 2) {
			distanceMessage.setBackgroundResource(drawable.finder_message);
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			if (countVibrator == 0) {
				vibrator.vibrate(200);
				countVibrator++;
			}
		} else {
			distanceMessage.setBackgroundResource(drawable.finder_message2);
			countVibrator = 0;
		}

		// 배경 이미지를 거리에 따라 보여줌
		if (Constants.DISTANCE == 0) {
			distanceImg.setBackgroundResource(drawable.finder_distance0);
		} else if (Constants.DISTANCE == 1) {
			distanceImg.setBackgroundResource(drawable.finder_distance1);
		} else if (Constants.DISTANCE == 2) {
			distanceImg.setBackgroundResource(drawable.finder_distance2);
		} else if (Constants.DISTANCE == 3) {
			distanceImg.setBackgroundResource(drawable.finder_distance3);
		} else if (Constants.DISTANCE == 4) {
			distanceImg.setBackgroundResource(drawable.finder_distance4);
		} else if (Constants.DISTANCE == 5) {
			distanceImg.setBackgroundResource(drawable.finder_distance5);
		} else if (Constants.DISTANCE == 6) {
			distanceImg.setBackgroundResource(drawable.finder_distance6);
		} else if (Constants.DISTANCE == 7) {
			distanceImg.setBackgroundResource(drawable.finder_distance7);
		} else if (Constants.DISTANCE == 8) {
			distanceImg.setBackgroundResource(drawable.finder_distance8);
		} else if (Constants.DISTANCE == 9) {
			distanceImg.setBackgroundResource(drawable.finder_distance9);
		} else if (Constants.DISTANCE > 9 && Constants.DISTANCE <= 12) {
			distanceImg.setBackgroundResource(drawable.finder_distance12);
		} else if (Constants.DISTANCE > 12 && Constants.DISTANCE <= 15) {
			distanceImg.setBackgroundResource(drawable.finder_distance15);
		} else if (Constants.DISTANCE > 15 && Constants.DISTANCE <= 18) {
			distanceImg.setBackgroundResource(drawable.finder_distance18);
		} else if (Constants.DISTANCE > 18 && Constants.DISTANCE <= 21) {
			distanceImg.setBackgroundResource(drawable.finder_distance21);
		} else if (Constants.DISTANCE > 21 && Constants.DISTANCE <= 24) {
			distanceImg.setBackgroundResource(drawable.finder_distance24);
		} else if (Constants.DISTANCE > 24 && Constants.DISTANCE <= 27) {
			distanceImg.setBackgroundResource(drawable.finder_distance27);
		} else if (Constants.DISTANCE > 27 && Constants.DISTANCE <= 30) {
			distanceImg.setBackgroundResource(drawable.finder_distance30);
		} else if (Constants.DISTANCE > 30) {
			distanceImg.setBackgroundResource(drawable.finder_distance31);
		}

		// 신호가 없는 경우
		if (Constants.NOTIFYCOUNT >= 3) {
			distanceImg.setBackgroundResource(drawable.finder_distance31);
			distanceMessage.setBackgroundResource(drawable.finder_message3);
			distanceData.setBackgroundResource(drawable.finder_m40);
		}
	}
}
