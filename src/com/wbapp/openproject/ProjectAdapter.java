package com.wbapp.openproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.wb.db.DatabaseOperations;
import com.wb.model.Project;
import com.wb.model.Tasks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


//setting adapter to client list
	public class ProjectAdapter extends BaseAdapter {

		Context context;
		LayoutInflater inflater;

		ArrayList<Project> object = new ArrayList<Project>();

		public ProjectAdapter(Context c, ArrayList<Project> object) {
			this.context = c;
			this.object = object;
		}

		@Override
		public int getCount() {
			Log.d("Size", "Project Values "+object.size());
			return object.size();
		}

		@Override
		public Object getItem(int position) {
			return object.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ArrayList<Tasks>   stepobject;
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_row, null);

			long Id    = object.get(position).getId();

			TextView projectTitle    = (TextView) convertView.findViewById(R.id.txtTitle);
			TextView txttodo    = (TextView) convertView.findViewById(R.id.txttodo);
			TextView txtdoing    = (TextView) convertView.findViewById(R.id.txtdoing);
			TextView txtdone    = (TextView) convertView.findViewById(R.id.txtdone);
			TextView txtdate    = (TextView) convertView.findViewById(R.id.txtdate);

			Log.d("Task Value", "Task Here "+ object.get(position).getTitle());
			
			projectTitle.setText(object.get(position).getTitle());
			
			txttodo.setText(""+DatabaseOperations.getInstance().getTaskTdoCountByClient(Id));
			txtdoing.setText(""+DatabaseOperations.getInstance().getTaskDoingCountByClient(Id));
			txtdone.setText(""+DatabaseOperations.getInstance().getTaskDoneCountByClient(Id));
			txtdate.setText("" +getDate(object.get(position).getDate(), position));
			
			
			return convertView;
		}
		//
		private String getDate(long timeStamp , int position){

		    try{
		        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		        Date netDate = (new Date(timeStamp * 1000));
		        return sdf.format(netDate);
		    }
		    catch(Exception ex){
		        return "xx";
		    }
		}
	}
