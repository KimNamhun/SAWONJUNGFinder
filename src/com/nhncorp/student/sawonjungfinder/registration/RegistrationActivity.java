package com.nhncorp.student.sawonjungfinder.registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhncorp.student.sawonjungfinder.MainActivity;
import com.nhncorp.student.sawonjungfinder.R;
import com.nhncorp.student.sawonjungfinder.database.DbGetSet;
import com.wizturn.sdk.central.Central;
import com.wizturn.sdk.central.CentralManager;
import com.wizturn.sdk.peripheral.Peripheral;
import com.wizturn.sdk.peripheral.PeripheralScanListener;

public class RegistrationActivity extends Activity {

	private CentralManager centralManager;
	private TextView cardWaitingText;
	private LinearLayout cardNamingLayout;
	private Button cardNamingBtn;
	private EditText cardNamingEdit;

	private boolean loadingState = false;
	private boolean registrationState = false;

	private Peripheral peripheralValue;

	private DbGetSet dbGetSet;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				centralManager.stopScanning();
				Intent intent = new Intent(RegistrationActivity.this,
						MainActivity.class);
				startActivity(intent);
				RegistrationActivity.this.finish();
			default:
			}
		}
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registration);
		init();
	}

	private void init() {
		getView();
		setCentralManager(this.getApplicationContext());
		dbGetSet = new DbGetSet(this);
		addListener();

	}

	private void addListener() {
		cardNamingBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dbGetSet.setRegistration(cardNamingEdit.getText().toString(),
						peripheralValue.getBDAddress());
				Intent intent = new Intent(RegistrationActivity.this,
						MainActivity.class);
				startActivity(intent);
				RegistrationActivity.this.finish();
			}
		});

	}

	private void getView() {
		cardWaitingText = (TextView) findViewById(R.id.cardWaitingText);
		cardNamingLayout = (LinearLayout) findViewById(R.id.cardNamingLayout);
		cardNamingBtn = (Button) findViewById(R.id.cardNamingBtn);
		cardNamingEdit = (EditText) findViewById(R.id.cardNamingEdit);

	}

	private void setCentralManager(Context context) {
		centralManager = CentralManager.getInstance();
		centralManager.init(context);
		centralManager.setPeripheralScanListener(new PeripheralScanListener() {
			@Override
			public void onPeripheralScan(Central central,
					final Peripheral peripheral) {

				System.out.println("onPeripheralScan() : peripheral : "
						+ peripheral); // /////////////////////////////////////////////////////

				runOnUiThread(new Runnable() {
					public void run() {
						if (peripheral.isIBeacon())
							setView(peripheral);
					}
				});

			}

		});
		centralManager.startScanning();
	}

	private void setView(Peripheral peripheral) {
		if (loadingState == false) {
			cardWaitingText.setText("비콘 카드를 단말기에 접근시켜 주세요... ");
			loadingState = true;
		} else {
			cardWaitingText.setText("비콘 카드를 단말기에 접근시켜 주세요...");
			loadingState = false;
		}
		if (peripheral.getDistance() < 0.03 && registrationState == false) {
			cardWaitingText.setTextSize(60);
			cardWaitingText.setText("삑 ");
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(500);
			peripheralValue = peripheral;
			registrationState = true;

		} else if (registrationState == true) {
			centralManager.stopScanning();
			registrationState = false;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cardWaitingText.setTextSize(20);
			cardWaitingText.setText("기기가 등록되었습니다 ");
			cardNamingLayout.setVisibility(View.VISIBLE);

		}

	}

}
