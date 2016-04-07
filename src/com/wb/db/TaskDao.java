package com.wb.db;

import com.wb.model.Tasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class TaskDao extends AbstractDao<Tasks, Long> {

	public static final String TABLENAME = "TaskRecord";

	public static class Properties {
		public final static Property Id = new Property(0, Long.class, "id",
				true, "_id");
		public final static Property ProjectId = new Property(1, Long.class,
				"projectid", false, "PROJECTID");
		public final static Property TaskTitle = new Property(2, String.class,
				"tasktitle", false, "TASKTITLE");
		public final static Property TaskStatus = new Property(3,
				Integer.class, "taskstatus", false, "TASKSTATUS");

	};

	public TaskDao(DaoConfig config) {
		super(config);
	}

	public TaskDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);

	}

	/** Creates the underlying database table. */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'TaskRecord' (" + //
				"'_id' INTEGER PRIMARY KEY ," + // 0: id
				"'PROJECTID' LONG NOT NULL ," + // 1: projectid
				"'TASKTITLE' TEXT," + // 2: tasktitle
				"'TASKSTATUS' INTEGER);"); // 5: taskstatus
	}

	/** Drops the underlying database table. */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "")
				+ "'TaskRecord'";
		db.execSQL(sql);
	}

	@Override
	protected void bindValues(SQLiteStatement stmt, Tasks entity) {
		Long id = entity.getId();
		if (id != null) {
			stmt.bindLong(1, id);
		}

		Long projectId = entity.getProjectId();
		if (projectId != null) {
			stmt.bindLong(2, projectId);
		}

		String title = entity.getTitle();
		if (title != null) {
			stmt.bindString(3, title);
		}

		int task = entity.getTask();
		stmt.bindLong(4, task);

	}

	@Override
	protected Long getKey(Tasks entity) {
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
	protected Tasks readEntity(Cursor cursor, int offset) {
		Tasks entity = new Tasks(
				//
				cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
				cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // projectId
				cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tasktitle
				cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3) // taskstatus
		);
		return entity;
	}

	@Override
	protected void readEntity(Cursor cursor, Tasks entity, int offset) {

		entity.setId(cursor.isNull(offset + 0) ? null : cursor
				.getLong(offset + 0));
		entity.setProjectId(cursor.getLong(offset + 1));
		entity.setTitle(cursor.isNull(offset + 2) ? null : cursor
				.getString(offset + 2));
		entity.setTask(cursor.isNull(offset + 3) ? null : cursor
				.getInt(offset + 3));
	}

	@Override
	protected Long readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
	}

	@Override
	protected Long updateKeyAfterInsert(Tasks entity, long rowId) {
		entity.setId(rowId);
		return rowId;
	}

}// TaskRecordDao
