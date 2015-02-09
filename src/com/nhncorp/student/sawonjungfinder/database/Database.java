package com.nhncorp.student.sawonjungfinder.database;

import android.provider.BaseColumns;

public class Database {
	public static final class CreateDB implements BaseColumns {
		public static final String NAME = "name";
		public static final String ID = "id";
		public static final String MACADDRESS = "macaddress";
		public static final String DEVICESTATE = "devicestate";
		public static final String LONGITUDE = "longitude";
		public static final String LATITUDE = "latitude";
		public static final String _TABLENAME = "beaconfinder";
		public static final String _CREATE = "create table " + _TABLENAME + "("
				+ ID + " integer primary key, " + NAME + " text not null , "
				+ MACADDRESS + " text not null , " + DEVICESTATE
				+ " text not null, " + LONGITUDE + " text not null, "
				+ LATITUDE + " text not null );";
		public static final String _INSERT = "insert into " + _TABLENAME
				+ " VALUES "
				+ "(1, '기기를 등록해 주세요', '기기를 등록해 주세요', '0', '0', '0');";
	}
}
