package com.nhncorp.student.sawonjungfinder.finder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhncorp.student.sawonjungfinder.R;
import com.nhncorp.student.sawonjungfinder.R.drawable;
import com.nhncorp.student.sawonjungfinder.constants.Constants;

public class FinderActivity extends Activity {

	private ImageView distanceImg3;
	private ImageView distanceImg4;
	private ImageView distanceImg5;
	private ImageView distanceImg6;
	private ImageView distanceImg7;
	private ImageView distanceImg8;
	private TextView distanceImg9;
	private ImageView distanceImg10;
	private ImageView distanceImg11;
	private ImageView distanceImg12;
	private ImageView distanceImg13;
	private ImageView distanceImg14;
	private ImageView distanceImg15;
	private ImageView distanceImg16;

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
		distanceImg3 = (ImageView) findViewById(R.id.distanceImg3);
		distanceImg4 = (ImageView) findViewById(R.id.distanceImg4);
		distanceImg5 = (ImageView) findViewById(R.id.distanceImg5);
		distanceImg6 = (ImageView) findViewById(R.id.distanceImg6);
		distanceImg7 = (ImageView) findViewById(R.id.distanceImg7);
		distanceImg8 = (ImageView) findViewById(R.id.distanceImg8);
		distanceImg9 = (TextView) findViewById(R.id.distanceImg9);
		distanceImg10 = (ImageView) findViewById(R.id.distanceImg10);
		distanceImg11 = (ImageView) findViewById(R.id.distanceImg11);
		distanceImg12 = (ImageView) findViewById(R.id.distanceImg12);
		distanceImg13 = (ImageView) findViewById(R.id.distanceImg13);
		distanceImg14 = (ImageView) findViewById(R.id.distanceImg14);
		distanceImg15 = (ImageView) findViewById(R.id.distanceImg15);

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
		//distanceImg9.setText(Constants.DISTANCE + " M");
		if (Constants.DISTANCE >= 0 && Constants.DISTANCE <= 3) {
			distanceImg3.setBackgroundResource(drawable.img03);
			distanceImg4.setBackgroundResource(drawable.img04);
			distanceImg5.setBackgroundResource(drawable.img05);
			distanceImg6.setBackgroundResource(drawable.img06);
			distanceImg7.setBackgroundResource(drawable.img07);
			distanceImg8.setBackgroundResource(drawable.img08);
			distanceImg9.setBackgroundResource(drawable.img09);
			distanceImg10.setBackgroundResource(drawable.img10);
			distanceImg11.setBackgroundResource(drawable.img11);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);

		} else if (Constants.DISTANCE > 3 && Constants.DISTANCE <= 6) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img04);
			distanceImg5.setBackgroundResource(drawable.img05);
			distanceImg6.setBackgroundResource(drawable.img06);
			distanceImg7.setBackgroundResource(drawable.img07);
			distanceImg8.setBackgroundResource(drawable.img08);
			distanceImg9.setBackgroundResource(drawable.img09);
			distanceImg10.setBackgroundResource(drawable.img10);
			distanceImg11.setBackgroundResource(drawable.img11);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 6 && Constants.DISTANCE <= 9) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img05);
			distanceImg6.setBackgroundResource(drawable.img06);
			distanceImg7.setBackgroundResource(drawable.img07);
			distanceImg8.setBackgroundResource(drawable.img08);
			distanceImg9.setBackgroundResource(drawable.img09);
			distanceImg10.setBackgroundResource(drawable.img10);
			distanceImg11.setBackgroundResource(drawable.img11);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 9 && Constants.DISTANCE <= 12) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img06);
			distanceImg7.setBackgroundResource(drawable.img07);
			distanceImg8.setBackgroundResource(drawable.img08);
			distanceImg9.setBackgroundResource(drawable.img09);
			distanceImg10.setBackgroundResource(drawable.img10);
			distanceImg11.setBackgroundResource(drawable.img11);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 12 && Constants.DISTANCE <= 14) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img07);
			distanceImg8.setBackgroundResource(drawable.img08);
			distanceImg9.setBackgroundResource(drawable.img09);
			distanceImg10.setBackgroundResource(drawable.img10);
			distanceImg11.setBackgroundResource(drawable.img11);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 14 && Constants.DISTANCE <= 16) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img02);
			distanceImg8.setBackgroundResource(drawable.img08);
			distanceImg9.setBackgroundResource(drawable.img09);
			distanceImg10.setBackgroundResource(drawable.img10);
			distanceImg11.setBackgroundResource(drawable.img11);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 16 && Constants.DISTANCE <= 18) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img02);
			distanceImg8.setBackgroundResource(drawable.img02);
			distanceImg9.setBackgroundResource(drawable.img09);
			distanceImg10.setBackgroundResource(drawable.img10);
			distanceImg11.setBackgroundResource(drawable.img11);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 18 && Constants.DISTANCE <= 19) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img02);
			distanceImg8.setBackgroundResource(drawable.img02);
			distanceImg9.setBackgroundResource(drawable.img02);
			distanceImg10.setBackgroundResource(drawable.img10);
			distanceImg11.setBackgroundResource(drawable.img11);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 19 && Constants.DISTANCE <= 20) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img02);
			distanceImg8.setBackgroundResource(drawable.img02);
			distanceImg9.setBackgroundResource(drawable.img02);
			distanceImg10.setBackgroundResource(drawable.img02);
			distanceImg11.setBackgroundResource(drawable.img11);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 20 && Constants.DISTANCE <= 21) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img02);
			distanceImg8.setBackgroundResource(drawable.img02);
			distanceImg9.setBackgroundResource(drawable.img02);
			distanceImg10.setBackgroundResource(drawable.img02);
			distanceImg11.setBackgroundResource(drawable.img02);
			distanceImg12.setBackgroundResource(drawable.img12);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 21 && Constants.DISTANCE <= 22) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img02);
			distanceImg8.setBackgroundResource(drawable.img02);
			distanceImg9.setBackgroundResource(drawable.img02);
			distanceImg10.setBackgroundResource(drawable.img02);
			distanceImg11.setBackgroundResource(drawable.img02);
			distanceImg12.setBackgroundResource(drawable.img02);
			distanceImg13.setBackgroundResource(drawable.img13);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 22 && Constants.DISTANCE <= 23) {

			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img02);
			distanceImg8.setBackgroundResource(drawable.img02);
			distanceImg9.setBackgroundResource(drawable.img02);
			distanceImg10.setBackgroundResource(drawable.img02);
			distanceImg11.setBackgroundResource(drawable.img02);
			distanceImg12.setBackgroundResource(drawable.img02);
			distanceImg13.setBackgroundResource(drawable.img02);
			distanceImg14.setBackgroundResource(drawable.img14);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 23 && Constants.DISTANCE <= 24) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img02);
			distanceImg8.setBackgroundResource(drawable.img02);
			distanceImg9.setBackgroundResource(drawable.img02);
			distanceImg10.setBackgroundResource(drawable.img02);
			distanceImg11.setBackgroundResource(drawable.img02);
			distanceImg12.setBackgroundResource(drawable.img02);
			distanceImg13.setBackgroundResource(drawable.img02);
			distanceImg14.setBackgroundResource(drawable.img02);
			distanceImg15.setBackgroundResource(drawable.img15);
		} else if (Constants.DISTANCE > 24) {
			distanceImg3.setBackgroundResource(drawable.img02);
			distanceImg4.setBackgroundResource(drawable.img02);
			distanceImg5.setBackgroundResource(drawable.img02);
			distanceImg6.setBackgroundResource(drawable.img02);
			distanceImg7.setBackgroundResource(drawable.img02);
			distanceImg8.setBackgroundResource(drawable.img02);
			distanceImg9.setBackgroundResource(drawable.img02);
			distanceImg10.setBackgroundResource(drawable.img02);
			distanceImg11.setBackgroundResource(drawable.img02);
			distanceImg12.setBackgroundResource(drawable.img02);
			distanceImg13.setBackgroundResource(drawable.img02);
			distanceImg14.setBackgroundResource(drawable.img02);
			distanceImg15.setBackgroundResource(drawable.img02);
		}

	}
}
