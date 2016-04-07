package com.wbapp.openproject;

import com.revmob.RevMob;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

@SuppressWarnings("deprecation")
public class MainTabActivity extends Activity {

	private Context mContext;
	ActionBar actionBar ;
	Long Projectid;
	public MainTabActivity() {
		// Empty constructor required for fragment subclasses
	}
	ActionBar.Tab tabtodo;
	ActionBar.Tab tabdoing;
	ActionBar.Tab tabdone;
	
	Fragment frgtodo;
	Fragment frgdoing;
	Fragment frgdone;
	private RevMob revmob;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.tasktab);
        mContext = this;
		actionBar =this.getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Bundle bundle = getIntent().getExtras();
		Long Projectid = bundle.getLong("message");
		
		Log.d("Project Id", "Project id "+Projectid);
		
		Config.PROJECT_ID = Projectid;
		
		frgtodo = new FragmentTodo(mContext);
		frgdoing = new FragmentDoing(mContext);
		frgdone = new FragmentDone(mContext);
		
		tabtodo = actionBar.newTab().setText("To-Do");
		tabdoing = actionBar.newTab().setText("Doing");
		tabdone = actionBar.newTab().setText("Done");
		
		tabtodo.setTabListener(new TabListenerModel(frgtodo));
		tabdoing.setTabListener(new TabListenerModel(frgdoing));
		tabdone.setTabListener(new TabListenerModel(frgdone));
		
		actionBar.addTab(tabtodo);
		actionBar.addTab(tabdoing);
		actionBar.addTab(tabdone);
		actionBar.setDisplayHomeAsUpEnabled(true);
		// Starting RevMob session
				revmob = RevMob.start(this);
       
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		/*if (id == R.id.action_settings) {
			return true;
		}*/

		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		Config.OKANE(this, revmob);
		revmob.showFullscreen(this);
	}
}
