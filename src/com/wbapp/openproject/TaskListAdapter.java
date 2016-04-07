package com.wbapp.openproject;

import java.util.ArrayList;

import com.wb.model.Tasks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	ArrayList<Tasks> taskObject = new ArrayList<Tasks>();

	public TaskListAdapter(Context c, ArrayList<Tasks> object) {
		// super();
		this.context = c;
		this.taskObject = object;
	}

	@Override
	public int getCount() {
		return taskObject.size();
	}

	@Override
	public Object getItem(int position) {
		return taskObject.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.list_row, null);

		TextView titleTxt 	= (TextView) convertView.findViewById(R.id.txtTitle);
		LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.layshowtasks);
		TextView time 		= (TextView) convertView.findViewById(R.id.txtdate);
		
		time.setVisibility(View.INVISIBLE);
		layout.setVisibility(View.INVISIBLE);

		titleTxt.setText(taskObject.get(position).getTitle());
		
		return convertView;
	}
}//
