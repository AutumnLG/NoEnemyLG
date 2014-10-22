package com.taehee.autumnlgclient;

import com.taehee.autumnlgclient.view.ViewGoods;
import com.taehee.autumnlgclient.view.ViewIntroduce;
import com.taehee.autumnlgclient.view.ViewLocate;
import com.taehee.autumnlgclient.view.ViewBase;

import android.app.Activity;
import android.content.Context;

public class FragmentFactory {

	public static ViewBase create(Activity act, int num) {
		switch (num) {
		case 1:
			return new ViewGoods(act);
		case 2:
			return new ViewIntroduce(act);
		case 3:
			return new ViewLocate(act);
		default:
			return null;
		}
	}
}
