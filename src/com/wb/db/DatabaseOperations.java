package com.wb.db;

import java.util.ArrayList;
import java.util.List;
import com.wb.db.ProjectDao.Properties;
import com.wb.model.Project;
import com.wb.model.Tasks;

import android.database.sqlite.SQLiteDatabase;

public class DatabaseOperations {

	private static DatabaseOperations instance;
	private SQLiteDatabase sQLiteDatabase;

	ProjectDao projectdao;
	//TaskDao taskdao;

	public static synchronized DatabaseOperations getInstance() {

		if (instance == null) {
			instance = new DatabaseOperations();
		}

		return instance;
	}

	public DaoSession getSession() {
		sQLiteDatabase = DatabaseManager.getInstance().openDatabase();
		DaoMaster daoMaster = new DaoMaster(sQLiteDatabase);
		return daoMaster.newSession();
	}

	public void closeSession() {
		DatabaseManager.getInstance().closeDatabase();
	}

	public void insert(Project entity) {
		getSession().getprojectDao().insert(entity);
		closeSession();
	}

	public void insertTask(Tasks entity) {
		getSession().getTaskDao().insert(entity);
		closeSession();
	}

	public void deleteProject(Long key) {
		getSession().getprojectDao().deleteByKey(key);
		closeSession();
	}

	public void deleteTask(Long key) {
		getSession().getTaskDao().deleteByKey(key);
		closeSession();
		getAllDataFromDB();
	}

	public List<Project> getDataById(long key) {

		projectdao = getSession().getprojectDao();

		List<Project> infoArray = projectdao.queryBuilder().where(Properties.Id.like("%" + key + "%")).list();

		return infoArray;
	}

	public void UpdateData(Project entity) {
		getSession().getprojectDao().update(entity);
		closeSession();
		getAllDataFromDB();
	}

	public void UpdateTask(Tasks entity) {
		getSession().getTaskDao().update(entity);
		closeSession();
		getAllDataFromDB();
	}

	public ArrayList<Project> getAllDataFromDB() {

		ArrayList<Project> array = new ArrayList<Project>();
		projectdao = getSession().getprojectDao();

		List<Project> projectArray = projectdao.queryBuilder().list();

		for (int i = 0; i < projectArray.size(); i++) {
			Project data = new Project();
			data.setId(projectArray.get(i).getId());
			data.setTitle(projectArray.get(i).getTitle());
			data.setDate(projectArray.get(i).getDate());
			array.add(data);
		}

		closeSession();
		return array;

	}

	public ArrayList<Tasks> getAllTasksFromDB() {
		TaskDao stepDao = getSession().getTaskDao();

		ArrayList<Tasks> taskList = new ArrayList<Tasks>();

		List<Tasks> stepArray = stepDao.queryBuilder().list();

		for (int i = 0; i < stepArray.size(); i++) {
			Tasks data = new Tasks();
			data.setId(stepArray.get(i).getId());
			data.setProjectId(stepArray.get(i).getProjectId());
			data.setTitle(stepArray.get(i).getTitle());
			data.setTask(stepArray.get(i).getTask());
			taskList.add(data);
		}

		closeSession();
		return taskList;

	}

	// getting the total task done by client id
	public int getTaskDoneCountByClient(Long clientId) {

		int count = 0;

		TaskDao stepDao = getSession().getTaskDao();

		List<Tasks> stepArray = stepDao.queryBuilder()
				.where(TaskDao.Properties.ProjectId.like("%" + clientId + "%"))
				.list();

		for (int i = 0; i < stepArray.size(); i++) {

			if (stepArray.get(i).getTask() == 3)
				count++;

		}
		closeSession();
		return count;
	}
	// getting the total task under process by client id
		public int getTaskDoingCountByClient(Long clientId) {

			int count = 0;

			TaskDao stepDao = getSession().getTaskDao();

			List<Tasks> stepArray = stepDao.queryBuilder()
					.where(TaskDao.Properties.ProjectId.like("%" + clientId + "%"))
					.list();

			for (int i = 0; i < stepArray.size(); i++) {

				if (stepArray.get(i).getTask() == 2)
					count++;

			}
			closeSession();
			return count;
		}
		// getting the total task under process by client id
				public int getTaskTdoCountByClient(Long clientId) {

					int count = 0;

					TaskDao stepDao = getSession().getTaskDao();

					List<Tasks> stepArray = stepDao.queryBuilder()
							.where(TaskDao.Properties.ProjectId.like("%" + clientId + "%"))
							.list();

					for (int i = 0; i < stepArray.size(); i++) {

						if (stepArray.get(i).getTask() == 1)
							count++;

					}
					closeSession();
					return count;
				}

	// getting all tasks by client id
	public ArrayList<Tasks> getAllTasksByProjectId(Long projectId) {

		TaskDao stepDao = getSession().getTaskDao();

		ArrayList<Tasks> stepList = new ArrayList<Tasks>();

		List<Tasks> stepArray = stepDao.queryBuilder()
				.where(TaskDao.Properties.ProjectId.like("%" + projectId + "%"))
				.list();

		for (int i = 0; i < stepArray.size(); i++) {
			Tasks task = new Tasks();

			task.setId(stepArray.get(i).getId());
			task.setProjectId(stepArray.get(i).getProjectId());
			task.setTitle(stepArray.get(i).getTitle());
			task.setTask(stepArray.get(i).getTask());
			stepList.add(task);
		}
		closeSession();
		return stepList;
	}
	
	// getting all tasks by client id
		public ArrayList<Tasks> getAllTasksDoingByProjectId(Long projectId , int taskstatus) {

			TaskDao stepDao = getSession().getTaskDao();

			ArrayList<Tasks> stepList = new ArrayList<Tasks>();

			List<Tasks> stepArray = stepDao.queryBuilder()
					.where(TaskDao.Properties.ProjectId.like("%" + projectId + "%"), TaskDao.Properties.TaskStatus.like("%" + taskstatus + "%"))
					.list();

			for (int i = 0; i < stepArray.size(); i++) {
				Tasks task = new Tasks();

				task.setId(stepArray.get(i).getId());
				task.setProjectId(stepArray.get(i).getProjectId());
				task.setTitle(stepArray.get(i).getTitle());
				task.setTask(stepArray.get(i).getTask());
				stepList.add(task);
			}
			closeSession();
			return stepList;
		}

}// DatabaseOperations
