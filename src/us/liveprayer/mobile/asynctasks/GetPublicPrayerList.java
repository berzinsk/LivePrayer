package us.liveprayer.mobile.asynctasks;

import java.util.ArrayList;
import java.util.List;

import us.liveprayer.mobile.PrayerList;
import us.liveprayer.mobile.objects.Prayer;
import android.os.AsyncTask;
import android.util.Log;

public class GetPublicPrayerList extends AsyncTask<Void, Void, Void> {
	
	PrayerList view;
	List<Prayer> prayers = new ArrayList<Prayer>();
	
	public GetPublicPrayerList(PrayerList view) {
		this.view = view;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			view.publicPrayers = view.api.getPublicPrayerList();
		} catch (Exception e) {
			Log.d("us.liveprayer.mobile", "Error getting public prayer list");
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		view.updatePublicPrayers(view.publicPrayers);
		Log.d("Taggg", String.valueOf(view.publicPrayers));
	}
}