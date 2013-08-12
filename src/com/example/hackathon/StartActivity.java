package com.example.hackathon;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import de.arvidg.exampleactionbartabs.R;

public class StartActivity extends Activity {
	public static Context appContext;
	
//	ViewPager mViewPager;
//	CollectionPagerAdapter mCollectionPagerAdapter;

	SessionManager session;
	protected SharedMenu sharedMenu;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//this!
        appContext = getApplicationContext();	
        //ActionBar l
        ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        setUpActionBar(actionbar);
        
        //Shared pref stuff
        session = new SessionManager(getApplicationContext());
        sharedMenu = new SharedMenu();

        // Trying to implement swipe here
//        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        mViewPager.setAdapter(mCollectionPagerAdapter);
        
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);		// don't know why main doesn't work anymore
        return true;
    }


    public void setUpActionBar(ActionBar actionbar){
         
         ActionBar.Tab PeopleTab = actionbar.newTab().setText("People");
         ActionBar.Tab EventsTab = actionbar.newTab().setText("Events");
//         ActionBar.Tab SkillsTab = actionbar.newTab().setText("Skills");
               
         PeopleTab.setTabListener(new MyTabsListener( new AFragment()) );
         EventsTab.setTabListener(new MyTabsListener( new BFragment()) );
//         SkillsTab.setTabListener(new MyTabsListener( new CFragment()) );
         
         actionbar.addTab(PeopleTab);
         actionbar.addTab(EventsTab);
//         actionbar.addTab(SkillsTab);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		    if(sharedMenu.onOptionsItemSelected(item, this) == false) {
		      // handle local menu items here or leave blank
				Intent i = new Intent(StartActivity.this, PeopleDetail.class);
				Bundle b = null;
				Log.d("SharedMenu", "user id in shared menu is: " + session.getUserDetails().get("id"));
				b.putInt("userId", Integer.parseInt(session.getUserDetails().get("id")));
//				i.putExtra("userId", session.getUserDetails().get("id"));
				i.putExtras(b);
				startActivity(i);

		    }
		    return super.onOptionsItemSelected(item);
		  
	}
	 
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
    
}



class MyTabsListener implements ActionBar.TabListener {
	public Fragment fragment;
	
	public MyTabsListener(Fragment fragment) {
		this.fragment = fragment;
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Toast.makeText(StartActivity.appContext, "Reselected!", Toast.LENGTH_LONG).show();
		System.out.println("inside onTabReselected!!!");
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.fragment_container, fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
	}
	
}