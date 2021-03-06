package com.taehee.autumnlgclient.view;

import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.taehee.autumnlgclient.R;
import com.taehee.autumnlgclient.R.id;
import com.taehee.autumnlgclient.R.layout;
import com.taehee.autumnlgclient.R.string;

public class ViewLocate extends ViewBase {

	// 37.603721, 126.958805

	private GoogleMap googleMap;

	public ViewLocate(Activity act) {
		super(act);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_section3;
	}

	@Override
	public void onActivityCreated() {
		this.googleMap = ((MapFragment) act.getFragmentManager().findFragmentById(R.id.map)).getMap();
		this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		this.googleMap.getUiSettings().setCompassEnabled(true);
		this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		this.googleMap.setMyLocationEnabled(true);
		this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.603721, 126.958805), 18));
		MarkerOptions mko = new MarkerOptions();
		mko.position(new LatLng(37.603721, 126.958805));
		mko.title("우리집");
		googleMap.addMarker(mko);
	}

	@Override
	public String getTitle() {
		return act.getString(R.string.title_section3);
	}
}
