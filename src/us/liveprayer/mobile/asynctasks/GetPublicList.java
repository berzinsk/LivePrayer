package us.liveprayer.mobile.asynctasks;

import java.util.ArrayList;
import java.util.List;

import us.liveprayer.mobile.Authorization;
import us.liveprayer.mobile.objects.Prayer;
import android.os.AsyncTask;
import android.util.Log;

public class GetPublicList extends AsyncTask<Void, Void, Void> {
	
	Authorization view;
	List<Prayer> prayers = new ArrayList<Prayer>();
	
	public GetPublicList(Authorization view) {
		this.view = view;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			view.prayers = view.api.getPrayerList();
		} catch (Exception e) {
			Log.d("us.liveprayer.mobile", "Error getting public prayer list");
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		view.updateUi(view.prayers);
		view.progressDialog.dismiss();
	}
}