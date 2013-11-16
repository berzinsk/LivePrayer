package us.liveprayer.mobile.asynctasks;

import java.util.ArrayList;
import java.util.List;

import us.liveprayer.mobile.PrayerList;
import us.liveprayer.mobile.objects.Prayer;
import android.os.AsyncTask;

public class GetPrayerList extends AsyncTask<Void, Void, Void> {
	
	PrayerList view;
	boolean pullToRefresh = false;
	String data;
	List<Prayer> prayers = new ArrayList<Prayer>();
	
	public GetPrayerList(PrayerList view, boolean pullToRefresh) {
		this.view = view;
		this.pullToRefresh = pullToRefresh;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			view.prayers = view.api.getPersonalPrayerList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		view.updateUi(view.prayers);
	}
}