/**
 * $Date: 2014$
 * Copyright (C) 2014-2015 Diligent Applications Pvt. Ltd. All Rights Reserved
 * 
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 
 * Contact: Mohit Guleria <mohit@locateup.com>
 *
 * @version 1.13 
 * 
 * The Class DaoMaster create all Tables
 * 
 * @author Ramandeep
 */

package com.wb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

public class DaoMaster extends AbstractDaoMaster {

	public static String TAG = "DaoMaster";

	public static final int SCHEMA_VERSION = 1000;

	/** Creates underlying database table using DAOs. */
	public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
		ProjectDao.createTable(db, ifNotExists);
		TaskDao.createTable(db, ifNotExists);
	}

	/** Drops underlying database table using DAOs. */
	public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
		ProjectDao.dropTable(db, ifExists);
		TaskDao.dropTable(db, ifExists);

	}

	public static abstract class OpenHelper extends SQLiteOpenHelper {

		public OpenHelper(Context context, String name, CursorFactory factory) {
			super(context, name, factory, SCHEMA_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i("", "Creating tables for schema version " + SCHEMA_VERSION);
			createAllTables(db, false);
		}
	}

	/** WARNING: Drops all table on Upgrade! Use only during development. */
	public static class DevOpenHelper extends OpenHelper {
		public DevOpenHelper(Context context, String name, CursorFactory factory) {
			super(context, name, factory);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i("", "Upgrading schema from version " + oldVersion + " to "
					+ newVersion + " by dropping all tables");
			dropAllTables(db, true);
			onCreate(db);
		}
	}

	public DaoMaster(SQLiteDatabase db) {
		super(db, SCHEMA_VERSION);
		registerDaoClass(ProjectDao.class);
		registerDaoClass(TaskDao.class);

	}

	@Override
	public DaoSession newSession() {
		return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
	}

	@Override
	public DaoSession newSession(IdentityScopeType type) {
		return new DaoSession(db, type, daoConfigMap);
	}
}//DaoMaster
