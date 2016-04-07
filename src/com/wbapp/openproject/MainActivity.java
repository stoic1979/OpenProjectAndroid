package com.wbapp.openproject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.revmob.RevMob;
import com.revmob.RevMobTestingMode;
import com.revmob.ads.banner.RevMobBanner;
import com.wb.db.DaoMaster;
import com.wb.db.DatabaseManager;
import com.wb.db.DatabaseOperations;
import com.wb.db.DaoMaster.DevOpenHelper;
import com.wb.model.Project;
import com.wb.model.Tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private ListView list;
	private ImageView add;
	//private ImageView settings;
	private EditText task;
	private Button cancel;
	private Button save;
	private ImageView taskadd;
	private ImageView addproject;

	ArrayList<Project> objecProjectModel;
	ProjectAdapter adapter;
	long projectId;
	RevMob revmob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, " ", null);
		DatabaseManager.initializeInstance(helper);

		add = (ImageView) findViewById(R.id.imgadd);
		list = (ListView) findViewById(R.id.listproject);
		taskadd = (ImageView) findViewById(R.id.imgaddtask);
		addproject = (ImageView) findViewById(R.id.addproject);
		//settings = (ImageView) findViewById(R.id.imgsettings);
		
		taskadd.setVisibility(View.INVISIBLE);
		add.setOnClickListener(this);
		//settings.setOnClickListener(this);
		
		objecProjectModel = DatabaseOperations.getInstance().getAllDataFromDB();
		Log.d("objecProjectModel", "+++++++++Size of Project++++++++++++"
				+ objecProjectModel.size());
		if (objecProjectModel.size() == 0)
			addproject.setVisibility(View.VISIBLE);
		else
			addproject.setVisibility(View.INVISIBLE);
		onResume();
		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);
		registerForContextMenu(list);

		// Starting RevMob session
		revmob = RevMob.start(this);

		// ###### set value of DEBUG_MODE false while testing the application
		if (Config.DEBUG_MODE)
			revmob.setTestingMode(RevMobTestingMode.WITH_ADS);
		RevMobBanner banner = revmob.createBanner(this);
		ViewGroup view = (ViewGroup) findViewById(R.id.banner);
		view.addView(banner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_notification) {
			return true;
		}
		else if (id == R.id.action_rateus) {
			return true;
		}
		else if (id == R.id.action_invite) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

		Config.OKANE(this, revmob);
		switch (v.getId()) {
		case R.id.imgadd:

			showClientDialog(null);

			break;
	/*	case R.id.imgsettings:
			
			 openOptionsMenu();
			Toast.makeText(getApplicationContext(), "Settings Clicked", Toast.LENGTH_SHORT).show();*/
		default:
			break;
		}
	}

	// dialog to add new task
	public void showClientDialog(final Project item) {
		final Dialog dBox = new Dialog(MainActivity.this);

		dBox.setContentView(R.layout.add_project);

		dBox.setTitle(Html.fromHtml("<font color='#009688'>Add Project</font>"));

		task = (EditText) dBox.findViewById(R.id.edttask);
		save = (Button) dBox.findViewById(R.id.btnsave);
		cancel = (Button) dBox.findViewById(R.id.btncancel);

		task.setHint("Project");
		if (item != null) {
			task.setText(item.getTitle());
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

				if (task.getText().toString().equals("")
						|| task.getText().toString().isEmpty()) {
					task.setError("Enter Task");
				} else {

					Project data = new Project();

					data.setTitle(task.getText().toString());
					long startDate = System.currentTimeMillis() / 1000;
					Log.d("Date", "+++++++++Date++++++++++++" + startDate);
					data.setDate(startDate);

					if (item != null) {
						data.setId(item.getId());
						DatabaseOperations.getInstance().UpdateData(data);
						setListAdapter();
					} else {
						DatabaseOperations.getInstance().insert(data);
						setListAdapter();
					}

					dBox.cancel();
				}
			}
		});
		dBox.show();
	}

	// set list adapter
	public void setListAdapter() {

		objecProjectModel = DatabaseOperations.getInstance().getAllDataFromDB();

		adapter = new ProjectAdapter(MainActivity.this, objecProjectModel);
		adapter.notifyDataSetChanged();
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);

		if (objecProjectModel.size() == 0)
			addproject.setVisibility(View.VISIBLE);
		else
			addproject.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Config.OKANE(this, revmob);
		projectId = objecProjectModel.get(position).getId();
		Bundle bundle = new Bundle();

		bundle.putLong("message", projectId);
		bundle.putString("name", objecProjectModel.get(position).getTitle());

		Intent it = new Intent(MainActivity.this, MainTabActivity.class);
		it.putExtras(bundle);
		startActivity(it);

	}

	@Override
	public void onResume() {
		super.onResume();
		if (objecProjectModel.size() == 0)
			addproject.setVisibility(View.VISIBLE);
		else
			addproject.setVisibility(View.INVISIBLE);
		setListAdapter();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Config.OKANE(this, revmob);
		showOptionDialog(objecProjectModel.get(arg2), arg2);
		return true;
	}

	// dialog for options
	public void showOptionDialog(final Project item, final int index) {
		final Dialog dBox = new Dialog(MainActivity.this);

		dBox.setContentView(R.layout.menuoptions);

		dBox.setTitle(Html
				.fromHtml("<font color='#009688'>Choose Action</font>"));

		Button rename = (Button) dBox.findViewById(R.id.btnrename);
		Button delete = (Button) dBox.findViewById(R.id.btndelete);
		// Button email = (Button) dBox.findViewById(R.id.btnemail);
		Button nothing = (Button) dBox.findViewById(R.id.btnnothing);

		nothing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dBox.cancel();
			}
		});
		rename.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showClientDialog(item);
				dBox.cancel();
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final long ids = objecProjectModel.get(index).getId();
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						MainActivity.this);
				alertDialogBuilder.setTitle("Are You Sure");
				alertDialogBuilder
						.setMessage("You Want To Delete!")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										// if this button is clicked, close
										// current activity
										DatabaseOperations.getInstance()
												.deleteProject(ids);

										ArrayList<Tasks> alltask = DatabaseOperations
												.getInstance()
												.getAllTasksByProjectId(ids);

										if (alltask.size() != 0) {
											for (int i = 0; i < alltask.size(); i++) {
												DatabaseOperations
														.getInstance()
														.deleteTask(
																alltask.get(i)
																		.getId());
												// notifyDataSetChanged();
											}
										}
										dialog.cancel();
										setListAdapter();
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

	Date mDate = new Date(System.currentTimeMillis());
	SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
	// System.out.println(mDateFormat.format(mDate));
}
