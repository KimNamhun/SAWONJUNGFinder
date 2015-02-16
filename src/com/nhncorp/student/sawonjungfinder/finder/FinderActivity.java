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
import com.nhncorp.student.sawonjungfinder.map.MapActivity;

public class FinderActivity extends Activity {

	private ImageView distanceImg0;

	private ImageView distanceImg1;

	private ImageView distanceImg2;

	private ImageView distanceImg3;

	private ImageView distanceImg4;

	private ImageView distanceImg5;

	private ImageView distanceImg6;

	private ImageView distanceImg7;

	private ImageView distanceImg8;

	private ImageView distanceImg9;

	private ImageView distanceImg10;

	private ImageView distanceImg11;

	private ImageView distanceImg12;

	private ImageView distanceImg13;

	private ImageView distanceImg14;

	private ImageView distanceImg15;

	private ImageView distanceImg16;

	private ImageView distanceImg17;

	private ImageView distanceData;

	private ImageView distanceMessage;

	private ImageButton mapBtn;

	private Thread myThread = null;
	private boolean runThread = true;

	int countVibrator = 0;

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
		if (Constants.DEVICE_STATE.equals("1")) {
		} else if (Constants.DEVICE_STATE.equals("0")) {
			Toast.makeText(this, "서비스가 꺼져있습니다.", Toast.LENGTH_LONG).show();
		}

	}

	private void getView() {

		distanceData = (ImageView) findViewById(R.id.distanceData);

		distanceMessage = (ImageView) findViewById(R.id.distanceMessage);

		distanceImg0 = (ImageView) findViewById(R.id.distanceImg0);

		distanceImg1 = (ImageView) findViewById(R.id.distanceImg1);

		distanceImg2 = (ImageView) findViewById(R.id.distanceImg2);

		distanceImg3 = (ImageView) findViewById(R.id.distanceImg3);

		distanceImg4 = (ImageView) findViewById(R.id.distanceImg4);

		distanceImg5 = (ImageView) findViewById(R.id.distanceImg5);

		distanceImg6 = (ImageView) findViewById(R.id.distanceImg6);

		distanceImg7 = (ImageView) findViewById(R.id.distanceImg7);

		distanceImg8 = (ImageView) findViewById(R.id.distanceImg8);

		distanceImg9 = (ImageView) findViewById(R.id.distanceImg9);

		distanceImg10 = (ImageView) findViewById(R.id.distanceImg10);

		distanceImg11 = (ImageView) findViewById(R.id.distanceImg11);

		distanceImg12 = (ImageView) findViewById(R.id.distanceImg12);

		distanceImg13 = (ImageView) findViewById(R.id.distanceImg13);

		distanceImg14 = (ImageView) findViewById(R.id.distanceImg14);

		distanceImg15 = (ImageView) findViewById(R.id.distanceImg15);

		distanceImg16 = (ImageView) findViewById(R.id.distanceImg16);

		distanceImg17 = (ImageView) findViewById(R.id.distanceImg17);

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
		if (Constants.DEVICE_STATE.equals("1")) {
			myThread.start();
		}
	}

	private void updateThread() {
		

		// distanceImg9.setText(Constants.DISTANCE + " M");

		// 거리는 1m단위로 제공

		String sDistance = "m" + Constants.DISTANCE;

		int distanceId = this.getResources().getIdentifier(sDistance,

		"drawable", this.getPackageName());

		distanceData.setBackgroundResource(distanceId);

		if (Constants.DISTANCE <= 2) {

			// 2m 이하일 때 메세지 표시, 진동 발생

			distanceMessage.setBackgroundResource(drawable.message);

			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

			if (countVibrator == 0) {

				vibrator.vibrate(200);

				countVibrator++;

			}

		} else {
			distanceMessage.setBackgroundResource(drawable.message2);

			countVibrator = 0;

		}

		if (Constants.DISTANCE == 0) {

			distanceImg0.setBackgroundResource(drawable.img0);

			distanceImg1.setBackgroundResource(drawable.img1);

			distanceImg2.setBackgroundResource(drawable.img2);

			distanceImg3.setBackgroundResource(drawable.img3);

			distanceImg4.setBackgroundResource(drawable.img4);

			distanceImg5.setBackgroundResource(drawable.img5);

			distanceImg6.setBackgroundResource(drawable.img6);

			distanceImg7.setBackgroundResource(drawable.img7);

			distanceImg8.setBackgroundResource(drawable.img8);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE == 1) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img1);

			distanceImg2.setBackgroundResource(drawable.img2);

			distanceImg3.setBackgroundResource(drawable.img3);

			distanceImg4.setBackgroundResource(drawable.img4);

			distanceImg5.setBackgroundResource(drawable.img5);

			distanceImg6.setBackgroundResource(drawable.img6);

			distanceImg7.setBackgroundResource(drawable.img7);

			distanceImg8.setBackgroundResource(drawable.img8);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE == 2) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img2);

			distanceImg3.setBackgroundResource(drawable.img3);

			distanceImg4.setBackgroundResource(drawable.img4);

			distanceImg5.setBackgroundResource(drawable.img5);

			distanceImg6.setBackgroundResource(drawable.img6);

			distanceImg7.setBackgroundResource(drawable.img7);

			distanceImg8.setBackgroundResource(drawable.img8);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE == 3) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img3);

			distanceImg4.setBackgroundResource(drawable.img4);

			distanceImg5.setBackgroundResource(drawable.img5);

			distanceImg6.setBackgroundResource(drawable.img6);

			distanceImg7.setBackgroundResource(drawable.img7);

			distanceImg8.setBackgroundResource(drawable.img8);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE == 4) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img4);

			distanceImg5.setBackgroundResource(drawable.img5);

			distanceImg6.setBackgroundResource(drawable.img6);

			distanceImg7.setBackgroundResource(drawable.img7);

			distanceImg8.setBackgroundResource(drawable.img8);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE == 5) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img5);

			distanceImg6.setBackgroundResource(drawable.img6);

			distanceImg7.setBackgroundResource(drawable.img7);

			distanceImg8.setBackgroundResource(drawable.img8);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE == 6) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img6);

			distanceImg7.setBackgroundResource(drawable.img7);

			distanceImg8.setBackgroundResource(drawable.img8);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE == 7) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img7);

			distanceImg8.setBackgroundResource(drawable.img8);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE == 8) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img8);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE == 9) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img9);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE > 9 && Constants.DISTANCE <= 12) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img19);

			distanceImg10.setBackgroundResource(drawable.img10);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE > 12 && Constants.DISTANCE <= 15) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img19);

			distanceImg10.setBackgroundResource(drawable.img19);

			distanceImg11.setBackgroundResource(drawable.img11);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE > 15 && Constants.DISTANCE <= 18) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img19);

			distanceImg10.setBackgroundResource(drawable.img19);

			distanceImg11.setBackgroundResource(drawable.img19);

			distanceImg12.setBackgroundResource(drawable.img12);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE > 18 && Constants.DISTANCE <= 21) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img19);

			distanceImg10.setBackgroundResource(drawable.img19);

			distanceImg11.setBackgroundResource(drawable.img19);

			distanceImg12.setBackgroundResource(drawable.img19);

			distanceImg13.setBackgroundResource(drawable.img13);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE > 21 && Constants.DISTANCE <= 24) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img19);

			distanceImg10.setBackgroundResource(drawable.img19);

			distanceImg11.setBackgroundResource(drawable.img19);

			distanceImg12.setBackgroundResource(drawable.img19);

			distanceImg13.setBackgroundResource(drawable.img19);

			distanceImg14.setBackgroundResource(drawable.img14);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE > 24 && Constants.DISTANCE <= 27) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img19);

			distanceImg10.setBackgroundResource(drawable.img19);

			distanceImg11.setBackgroundResource(drawable.img19);

			distanceImg12.setBackgroundResource(drawable.img19);

			distanceImg13.setBackgroundResource(drawable.img19);

			distanceImg14.setBackgroundResource(drawable.img19);

			distanceImg15.setBackgroundResource(drawable.img15);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE > 27 && Constants.DISTANCE <= 30) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img19);

			distanceImg10.setBackgroundResource(drawable.img19);

			distanceImg11.setBackgroundResource(drawable.img19);

			distanceImg12.setBackgroundResource(drawable.img19);

			distanceImg13.setBackgroundResource(drawable.img19);

			distanceImg14.setBackgroundResource(drawable.img19);

			distanceImg15.setBackgroundResource(drawable.img19);

			distanceImg16.setBackgroundResource(drawable.img16);

			distanceImg17.setBackgroundResource(drawable.img18);

		} else if (Constants.DISTANCE > 30) {

			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img19);

			distanceImg10.setBackgroundResource(drawable.img19);

			distanceImg11.setBackgroundResource(drawable.img19);

			distanceImg12.setBackgroundResource(drawable.img19);

			distanceImg13.setBackgroundResource(drawable.img19);

			distanceImg14.setBackgroundResource(drawable.img19);

			distanceImg15.setBackgroundResource(drawable.img19);

			distanceImg16.setBackgroundResource(drawable.img19);

			distanceImg17.setBackgroundResource(drawable.img17);

		}

		if (Constants.NOTIFYCOUNT > 5) {

			System.out.println("????");
			distanceImg0.setBackgroundResource(drawable.img19);

			distanceImg1.setBackgroundResource(drawable.img19);

			distanceImg2.setBackgroundResource(drawable.img19);

			distanceImg3.setBackgroundResource(drawable.img19);

			distanceImg4.setBackgroundResource(drawable.img19);

			distanceImg5.setBackgroundResource(drawable.img19);

			distanceImg6.setBackgroundResource(drawable.img19);

			distanceImg7.setBackgroundResource(drawable.img19);

			distanceImg8.setBackgroundResource(drawable.img19);

			distanceImg9.setBackgroundResource(drawable.img19);

			distanceImg10.setBackgroundResource(drawable.img19);

			distanceImg11.setBackgroundResource(drawable.img19);

			distanceImg12.setBackgroundResource(drawable.img19);

			distanceImg13.setBackgroundResource(drawable.img19);

			distanceImg14.setBackgroundResource(drawable.img19);

			distanceImg15.setBackgroundResource(drawable.img19);

			distanceImg16.setBackgroundResource(drawable.img19);

			distanceImg17.setBackgroundResource(drawable.img17);

			distanceMessage.setBackgroundResource(drawable.message3);

			distanceData.setBackgroundResource(drawable.black);
		}
	}
}
