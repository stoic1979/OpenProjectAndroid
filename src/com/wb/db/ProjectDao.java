package com.wb.db;

import com.wb.model.Project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class ProjectDao extends AbstractDao<Project, Long> {

	public final static String TABLENAME = "ProjectRecord";

	public static class Properties {
		public final static Property Id = new Property(0, Long.class, "id",
				true, "ID");
		public final static Property Title = new Property(1, String.class,
				"title", false, "TITLE");
		public final static Property Date = new Property(2, Long.class, "date",
				false, "DATE");

	}

	;

	public ProjectDao(DaoConfig config) {
		super(config);
	}

	public ProjectDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
	}

	/**
	 * Creates the underlying database table.
	 */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {

		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'ProjectRecord' (" + //
				"'ID' INTEGER PRIMARY KEY ," + "'TITLE' TEXT NOT NULL ," + "'DATE' LONG );");
	}

	/**
	 * Drops the underlying database table.
	 */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "")
				+ "'ProjectRecord'";
		db.execSQL(sql);
	}

	@Override
	protected void bindValues(SQLiteStatement stmt, Project entity) {
		Long id = entity.getId();

		if (id != null) {
			stmt.bindLong(1, id);
		}

		String title = entity.getTitle();
		if (title != null) {
			stmt.bindString(2, title);
		}
		Long date = entity.getDate();

		if (date != null) {
			stmt.bindLong(3, date);
		}

	}

	@Override
	protected Long getKey(Project entity) {
		if (entity != null) {
			return entity.getId();
		} else {
			return null;
		}
	}

	@Override
	protected boolean isEntityUpdateable() {
		return true;
	}

	@Override
	protected Project readEntity(Cursor cursor, int offset) {
		Project entity = new Project(

				cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
				cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1),
				cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));

		return entity;
	}

	@Override
	protected void readEntity(Cursor cursor, Project entity, int offset) {
		entity.setId((cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0)));
		entity.setTitle(cursor.getString(offset + 1));
		entity.setDate(cursor.getLong(offset + 2));
	}

	@Override
	protected Long readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
	}

	@Override
	protected Long updateKeyAfterInsert(Project entity, long rowId) {
		entity.setId(rowId);
		return rowId;
	}

}// ProjectDao
