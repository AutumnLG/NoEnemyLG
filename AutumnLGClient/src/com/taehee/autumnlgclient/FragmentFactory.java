package com.taehee.autumnlgclient;

import android.app.Activity;
import android.content.Context;

public class FragmentFactory {
	
	public static SectionView create(Activity act, int num) {
		switch (num) {
			case 1:
				return new Section1View(act);
			case 2:
				return new Section2View(act);
			case 3:
				return new Section3View(act);
			default:
				return null;
		}
	}
}
