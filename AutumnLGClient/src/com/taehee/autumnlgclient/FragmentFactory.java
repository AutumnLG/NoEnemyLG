package com.taehee.autumnlgclient;

import android.app.Activity;

import com.taehee.autumnlgclient.view.ViewBase;
import com.taehee.autumnlgclient.view.ViewGoods;
import com.taehee.autumnlgclient.view.ViewIntroduce;
import com.taehee.autumnlgclient.view.ViewLocate;

public class FragmentFactory {

	private static ViewGoods viewGoods;
	private static ViewIntroduce viewIntroduce;
	private static ViewLocate viewLocate;

	public static ViewBase create(Activity act, int num) {
		switch (num) {
		case 1:
			if (viewGoods == null) {
				viewGoods = new ViewGoods(act);
			}
			return viewGoods;
		case 2:
			if (viewIntroduce == null) {
				viewIntroduce = new ViewIntroduce(act);
			}
			return viewIntroduce;
		case 3:
			if (viewLocate == null) {
				viewLocate = new ViewLocate(act);
			}
			return viewLocate;
		default:
			return null;
		}
	}
}
