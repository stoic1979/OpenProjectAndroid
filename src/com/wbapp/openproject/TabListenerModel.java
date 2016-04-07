package com.wbapp.openproject;




import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;

@SuppressWarnings("deprecation")
public class TabListenerModel implements TabListener{
	private Fragment fragment;
		     
		    // The contructor.
		    public TabListenerModel(Fragment fragment) {
		        this.fragment = fragment;
		    }
		 
		    // When a tab is tapped, the FragmentTransaction replaces
		    // the content of our main layout with the specified fragment;
	    // that's why we declared an id for the main layout.
		    public void onTabSelected(Tab tab, FragmentTransaction ft) {
		        ft.replace(R.id.ly_tab, fragment);
		    }
		 
		    // When a tab is unselected, we have to hide it from the user's view.
		    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		        ft.remove(fragment);
		    }
		 
		    // Nothing special here. Fragments already did the job.
		    public void onTabReselected(Tab tab, FragmentTransaction ft) {
		         
		    }

}
