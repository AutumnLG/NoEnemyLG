package com.taehee.autumnlgclient;

import com.taehee.autumnlgclient.model.ModelGoods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActGoodsDetail extends Activity {

	public static String EXTRA_MODELGOODS = "EXTRA_MODELGOODS_ActGoodsDetail";
	
	public static Intent getIntent(Context context, ModelGoods modelGoods) {
		Intent intent = new Intent(context, ActGoodsDetail.class);
		intent.putExtra(EXTRA_MODELGOODS, modelGoods);
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		ModelGoods modelGoods = (ModelGoods) getIntent().getSerializableExtra(EXTRA_MODELGOODS);
		
		((TextView)findViewById(R.id.activity_detail_tv_code)).setText(modelGoods.code);
		((TextView)findViewById(R.id.activity_detail_tv_name)).setText(modelGoods.name);
		((TextView)findViewById(R.id.activity_detail_tv_price)).setText(modelGoods.price);
		
	}
}
