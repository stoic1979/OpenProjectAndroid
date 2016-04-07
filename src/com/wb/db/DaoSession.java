
package com.wb.db;

import java.util.Map;

import com.wb.model.Project;
import com.wb.model.Tasks;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

public class DaoSession extends AbstractDaoSession {

	private final DaoConfig DaoConfigProjects;
	private final DaoConfig TaskDaoConfigProjects;

	private final ProjectDao projectDao;

	private final TaskDao taskDao;

	public DaoSession(SQLiteDatabase db, IdentityScopeType type,
			Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
		super(db);

		DaoConfigProjects = daoConfigMap.get(ProjectDao.class).clone();
		DaoConfigProjects.initIdentityScope(type);

		TaskDaoConfigProjects = daoConfigMap.get(TaskDao.class).clone();
		TaskDaoConfigProjects.initIdentityScope(type);

		projectDao = new ProjectDao(DaoConfigProjects, this);
		taskDao = new TaskDao(TaskDaoConfigProjects, this);

		registerDao(Project.class, projectDao);
		registerDao(Tasks.class, taskDao);

	}

	public void clear() {
		DaoConfigProjects.getIdentityScope().clear();
		TaskDaoConfigProjects.getIdentityScope().clear();
	}

	public ProjectDao getprojectDao() {
		return projectDao;
	}

	public TaskDao getTaskDao() {
		return taskDao;
	}
}//DaoSession
