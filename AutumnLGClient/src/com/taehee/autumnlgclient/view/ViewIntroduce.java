package com.taehee.autumnlgclient.view;

import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taehee.autumnlgclient.R;
import com.taehee.autumnlgclient.model.ModelGem;
import com.taehee.autumnlgclient.model.ModelGemList;
import com.taehee.autumnlgclient.net.BrowserBase;
import com.taehee.autumnlgclient.net.BrowserData;
import com.taehee.autumnlgclient.net.RequestManager;
import com.taehee.autumnlgclient.net.onRequestListener;

public class ViewIntroduce extends ViewBase implements OnClickListener, onRequestListener {
	// 0 : 알코드, 1 : 컷, 2 : 알, 3 : 가로, 4 : 세로, 5 : 알값, 6 : 중량, 7 : 구입가, 8 : 변경
	// 1 : 필드제목, 2 ~ : 해당 데이터 시작
	// 모든 데이터는 스트링 타입

	public ViewIntroduce(Activity act) {
		super(act);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_section2;
	}

	@Override
	public void onActivityCreated() {
		act.findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public String getTitle() {
		return act.getString(R.string.title_section2);
	}

	@Override
	public void onClick(View v) {
		//클릭 막아둠..
		Toast.makeText(act, "클릭 막아둠..", Toast.LENGTH_LONG).show();
//		new ExcelAsyncTask().execute();
	}

	@Override
	public void onRequestStart(BrowserBase data) {
	}

	@Override
	public void onRequestSuccess(BrowserBase data) {
		Log.i("taehee", "onRequestSuccess");
	}

	@Override
	public void onRequestFail(BrowserBase data) {
		Log.i("taehee", "onRequestFail");
	}

	@Override
	public void onRequestFinish(BrowserBase data) {
		Log.i("taehee", "onRequestFinish");
	}

	@Override
	public void onRequestCancel(BrowserBase data) {
	}

	@Override
	public void onRequestProgress(BrowserBase data, int bytesWritten, int totalSize) {
	}

	private class ExcelAsyncTask extends AsyncTask<Void, Void, ModelGemList> {

		@Override
		protected ModelGemList doInBackground(Void... params) {
			ModelGemList modelGemList = new ModelGemList();
			try {
				modelGemList.gemItems = new ArrayList<ModelGem>();

				Workbook workbook = Workbook.getWorkbook(act.getAssets().open("testexcel.xls"));
				Sheet sheet = workbook.getSheet(2);
				for (int i = 2; i < sheet.getRows(); i++) {
					ModelGem modelGem = new ModelGem();
					modelGem.code = sheet.getCell(0, i).getContents();
					modelGem.cut = sheet.getCell(1, i).getContents();
					modelGem.gemType = sheet.getCell(2, i).getContents();
					modelGem.width = sheet.getCell(3, i).getContents();
					modelGem.height = sheet.getCell(4, i).getContents();
					modelGem.gemPrice = sheet.getCell(5, i).getContents();
					modelGem.weight = sheet.getCell(6, i).getContents();
					modelGem.purchasePrice = sheet.getCell(7, i).getContents();
					modelGem.changeDate = sheet.getCell(8, i).getContents();

					modelGemList.gemItems.add(modelGem);
				}

			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return modelGemList;
		}

		@Override
		protected void onPostExecute(ModelGemList result) {
			super.onPostExecute(result);
			String json;
			try {
				json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(result);
				BrowserData data = new BrowserData(act, "champlgserver", ViewIntroduce.this, true, false);
				RequestManager.Instance(act).startRequestEntity(act, data, json);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
