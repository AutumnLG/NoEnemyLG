package com.taehee.autumnlgclient;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	
	private SectionView currentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.currentView = FragmentFactory.create(this, 1);
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit();
	}
	
	public void onSectionAttached(int number) {
	}
	
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(currentView.getTitle());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen() && getString(R.string.title_section1).equalsIgnoreCase(currentView.getTitle())) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			
			MenuItem item = menu.findItem(R.id.action_search);
			SearchView searchView = (SearchView) item.getActionView();
			searchView.setQueryHint("코드입력");
			searchView.setOnQueryTextListener(queryTextListener);
			
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			if (searchManager != null) {
				searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			}
			searchView.setIconifiedByDefault(true);
			return true;
		} else if (!mNavigationDrawerFragment.isDrawerOpen()) {
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	private OnQueryTextListener queryTextListener = new OnQueryTextListener() {
		
		@Override
		public boolean onQueryTextSubmit(String query) {
			return false;
		}
		
		@Override
		public boolean onQueryTextChange(String newText) {
			currentView.onSearch(newText);
			return false;
		}
	};
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}
		
		public PlaceholderFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			((MainActivity) getActivity()).currentView = FragmentFactory.create(getActivity(), getArguments().getInt(ARG_SECTION_NUMBER));
			return ((MainActivity) getActivity()).currentView.getView(inflater, container);
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			((MainActivity) getActivity()).currentView.onActivityCreated();
		}
		
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}
	
}
