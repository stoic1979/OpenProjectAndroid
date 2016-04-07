package com.wbapp.openproject;

import java.util.ArrayList;

import com.revmob.RevMob;
import com.wb.db.DatabaseOperations;
import com.wb.model.Tasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class FragmentTodo extends Fragment implements OnClickListener,
		OnItemClickListener {
	public Context mContext;
	private View rootView;
	private ListView listTask;
	private EditText edttask;
	private Button cancel;
	private Button save;
	private Toolbar toolbar;
	private ImageView taskadd;
	ArrayList<Tasks> taskTdoModel;
	TaskListAdapter taskAdapter;
	private int STATUS;
	private ImageView addproject;
	private RevMob revmob;
	public FragmentTodo(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_main, container, false);

		listTask 		= (ListView) rootView.findViewById(R.id.listproject);
		toolbar 		= (Toolbar) rootView.findViewById(R.id.toolbar);
		taskadd   		= (ImageView) rootView.findViewById(R.id.imgaddtask);
		addproject  	= (ImageView) rootView.findViewById(R.id.addproject);
		STATUS = 1;

		taskTdoModel =  DatabaseOperations.getInstance()
				.getAllTasksDoingByProjectId(Config.PROJECT_ID, STATUS);
		Log.d("taskTdoModel", "+++++++++Size of taskTdoModel++++++++++++"
				+ taskTdoModel.size());
		if (taskTdoModel.size() == 0)
			addproject.setVisibility(View.VISIBLE);
		else
			addproject.setVisibility(View.INVISIBLE);
		
		toolbar.setVisibility(View.GONE);
		taskadd.setVisibility(View.VISIBLE);
		taskadd.setOnClickListener(this);

		listTask.setOnItemClickListener(this);
		// Starting RevMob session
	     revmob = RevMob.start(getActivity());   
		return rootView;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imgaddtask:
			Config.OKANE(getActivity(), revmob);
			showTaskDialog(null);
			break;

		default:
			break;
		}
	}

	public void showTaskDialog(final Tasks task) {
		final Dialog dBox = new Dialog(getActivity());

		dBox.setContentView(R.layout.add_project);

		dBox.setTitle(Html.fromHtml("<font color='#009688'>Add Task</font>"));

		edttask = (EditText) dBox.findViewById(R.id.edttask);
		cancel = (Button) dBox.findViewById(R.id.btncancel);
		save = (Button) dBox.findViewById(R.id.btnsave);
		if (task != null) {

			edttask.setText(task.getTitle());

		}

		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dBox.cancel();
			}
		});

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (edttask.getText().toString().equals("")
						|| edttask.getText().toString().isEmpty()) {
					edttask.setError("Enter title of service");
				} else {
					revmob.showFullscreen(getActivity());
					Tasks data = new Tasks();
					data.setProjectId(Config.PROJECT_ID);
					data.setTitle(edttask.getText().toString());
					data.setTask(STATUS);

					if (task != null) {
						data.setId(task.getId());
						DatabaseOperations.getInstance().UpdateTask(data);
						setTaskData();
					} else {
						DatabaseOperations.getInstance().insertTask(data);
						setTaskData();
					}
					dBox.dismiss();
				}
			}
		});
		dBox.show();
	}

	// set list adapter
	public void setTaskData() {

		taskTdoModel = DatabaseOperations.getInstance().getAllTasksDoingByProjectId(Config.PROJECT_ID, STATUS);

		taskAdapter = new TaskListAdapter(getActivity(), taskTdoModel);
		taskAdapter.notifyDataSetChanged();
		listTask.setAdapter(taskAdapter);
		
		if (taskTdoModel.size() == 0)
			addproject.setVisibility(View.VISIBLE);
		else 
			addproject.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (taskTdoModel.size() == 0)
			addproject.setVisibility(View.VISIBLE);
		else 
			addproject.setVisibility(View.INVISIBLE);
		
		setTaskData();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Config.OKANE(getActivity(), revmob);
		showOptionDialog(taskTdoModel.get(arg2), arg2);
	}

	// dialog for options
	public void showOptionDialog(final Tasks item, final int index) {
		final Dialog dBox = new Dialog(getActivity());

		dBox.setContentView(R.layout.listitemoptions);

		dBox.setTitle(Html
				.fromHtml("<font color='#009688'>Choose Action</font>"));

		Button rename = (Button) dBox.findViewById(R.id.btnlistrename);
		Button delete = (Button) dBox.findViewById(R.id.btnlistdelete);
		Button todo = (Button) dBox.findViewById(R.id.btntodo);
		Button doing = (Button) dBox.findViewById(R.id.btndoing);
		Button nothing = (Button) dBox.findViewById(R.id.btnlistnothing);

		nothing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dBox.cancel();
			}
		});

		todo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Config.OKANE(getActivity(), revmob);
				item.setTask(2);
				DatabaseOperations.getInstance().UpdateTask(item);
				setTaskData();
				dBox.cancel();
			}
		});
		doing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Config.OKANE(getActivity(), revmob);
				item.setTask(3);
				DatabaseOperations.getInstance().UpdateTask(item);

				setTaskData();
				dBox.cancel();

			}
		});

		rename.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Config.OKANE(getActivity(), revmob);
				showTaskDialog(item);
				dBox.cancel();
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final long ids = taskTdoModel.get(index).getId();
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder.setTitle("Are You Sure");
				alertDialogBuilder
						.setMessage("You Want To Delete!")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										DatabaseOperations.getInstance()
												.deleteTask(ids);
										// notifyDataSetChanged();
										dialog.cancel();
										setTaskData();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
				dBox.cancel();
			}

		});

		dBox.show();
	}
}// FragmentTodo
