package com.nhncorp.student.sawonjungfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
			Toast.makeText(this, "��������� �۵� �ǰ� �ֽ��ϴ�.", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "�ش� �ܸ��� ��������� �������� �ʽ��ϴ�.", Toast.LENGTH_LONG)
					.show();
		}
		dbGetSet = new DbGetSet(MainActivity.this);
		deviceConfirm(1); // nothing
		deviceNameText.setText(dbGetSet.getName());
		addListener();
	}

	private void deviceConfirm(int sel) { // 1: nothing 2: dev

		if (sel == 2) { // dev��ư�� ���� ��� devicestate�� ���� ����

			if (dbGetSet.getDeviceState().equals("0")) {
				dbGetSet.setDeviceState("1");
				devOnOffBtn.setImageResource(R.drawable.main_powerorange);
				showSettingsAlert();
				setService(1); // start
			} else if (dbGetSet.getDeviceState().equals("1")) {
				dbGetSet.setDeviceState("0");
				devOnOffBtn.setImageResource(R.drawable.main_powerblack);
				setService(0); // stop
			}
		} else if (sel == 1) { // �ƹ��ϵ� ���� ����
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
							"��⸦ ����Ϸ��� ������ ���� ������ �մϴ�.", Toast.LENGTH_LONG)
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

					View layout = inflater.inflate(R.layout.help_message,
							(ViewGroup) findViewById(R.id.helpMsg));
					AlertDialog.Builder aDialog = new AlertDialog.Builder(
							MainActivity.this);

					aDialog.setTitle("����"); // Ÿ��Ʋ�� ����
					aDialog.setView(layout);

					// �׳� �ݱ��ư�� ���� �κ�
					aDialog.setNegativeButton("�ݱ�",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					// �˾�â ����
					AlertDialog ad = aDialog.create();
					ad.show();// ������!
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

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle("�˶���� ���ÿϷ�");
		alertDialog
				.setMessage("�߰������� �αױ�� ����� ���ؼ��� ��ġ�� Ȱ��ȭ�ؾ��մϴ�\n��ġ�� Ȱ��ȭ �Ͻðڽ��ϱ�?");

		// OK �� ������ �Ǹ� ����â���� �̵��մϴ�.
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						MainActivity.this.startActivity(intent);
					}
				});
		// Cancel�ϸ� ���� �մϴ�.
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.show();
	}
}
