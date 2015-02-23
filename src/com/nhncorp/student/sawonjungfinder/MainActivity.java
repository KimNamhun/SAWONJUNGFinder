package com.nhncorp.student.sawonjungfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nhncorp.student.sawonjungfinder.bluetooth.BlueToothEnabler;
import com.nhncorp.student.sawonjungfinder.database.DbGetSet;
import com.nhncorp.student.sawonjungfinder.registration.RegistrationActivity;
import com.nhncorp.student.sawonjungfinder.service.AlarmService;

public class MainActivity extends Activity {

	private BlueToothEnabler bluetooth;

	private TextView deviceNameText;

	private ImageButton finderBtn;
	private ImageButton registrationBtn;
	private ImageButton devOnOffBtn;
	private ImageButton questionBtn;

	private DbGetSet dbGetSet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		getView();
		bluetooth = new BlueToothEnabler();
		boolean isBluetooth = bluetooth.enableBlueTooth();
		if (isBluetooth) {
			Toast.makeText(this, "블루투스가 작동 되고 있습니다.", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "해당 단말은 블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG)
					.show();
		}
		dbGetSet = new DbGetSet(MainActivity.this);
		deviceConfirm(1); // nothing
		deviceNameText.setText(dbGetSet.getName());
		addListener();
	}

	private void deviceConfirm(int sel) { // 1: nothing 2: dev

		if (sel == 2) { // dev버튼을 누른 경우 devicestate의 값을 변경

			if (dbGetSet.getDeviceState().equals("0")) {
				dbGetSet.setDeviceState("1");
				devOnOffBtn.setImageResource(R.drawable.main_powerorange);
				setService(1); // start
			} else if (dbGetSet.getDeviceState().equals("1")) {
				dbGetSet.setDeviceState("0");
				devOnOffBtn.setImageResource(R.drawable.main_powerblack);
				setService(0); // stop
			}
		} else if (sel == 1) { // 아무일도 하지 않음
			if (dbGetSet.getDeviceState().equals("0")) {
				devOnOffBtn.setImageResource(R.drawable.main_powerblack);
			} else if (dbGetSet.getDeviceState().equals("1")) {
				devOnOffBtn.setImageResource(R.drawable.main_powerorange);
			}

		}
	}

	private void addListener() {
		finderBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(MainActivity.this,
				// FinderActivity.class);
				// startActivity(intent);

			}
		});

		registrationBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dbGetSet.getDeviceState().equals("1")) {
					Toast.makeText(MainActivity.this,
							"기기를 등록하려면 전구의 불이 꺼져야 합니다.", Toast.LENGTH_LONG)
							.show();
				} else {
					Intent intent = new Intent(MainActivity.this,
							RegistrationActivity.class);
					MainActivity.this.finish();
					startActivity(intent);
				}
			}
		});
		devOnOffBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				deviceConfirm(2); // dev

			}
		});

		questionBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == questionBtn) {
					Context mContext = getApplicationContext();
					LayoutInflater inflater = (LayoutInflater) mContext
							.getSystemService(LAYOUT_INFLATER_SERVICE);

					// R.layout.dialog는 xml 파일명이고 R.id.popup은 보여줄 레이아웃 아이디
					View layout = inflater.inflate(R.layout.help_message,
							(ViewGroup) findViewById(R.id.helpMsg));
					AlertDialog.Builder aDialog = new AlertDialog.Builder(
							MainActivity.this);

					aDialog.setTitle("도움말"); // 타이틀바 제목
					aDialog.setView(layout); // dialog.xml 파일을 뷰로 셋팅

					// 그냥 닫기버튼을 위한 부분
					aDialog.setNegativeButton("닫기",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					// 팝업창 생성
					AlertDialog ad = aDialog.create();
					ad.show();// 보여줌!
				}
			}

		});

	}

	private void getView() {
		deviceNameText = (TextView) findViewById(R.id.deviceNameText);
		finderBtn = (ImageButton) findViewById(R.id.finderBtn);
		registrationBtn = (ImageButton) findViewById(R.id.registrationBtn);
		devOnOffBtn = (ImageButton) findViewById(R.id.devOnOffBtn);
		questionBtn = (ImageButton) findViewById(R.id.questionBtn);
	}

	private void setService(int i) {
		Intent intent = new Intent(this, AlarmService.class);
		if (i == 0) { // stop
			stopService(intent);
		} else if (i == 1) { // start
			startService(intent);
		}
	}
}
