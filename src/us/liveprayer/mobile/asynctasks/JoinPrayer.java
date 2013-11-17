package us.liveprayer.mobile.asynctasks;

import us.liveprayer.mobile.PrayerView;
import us.liveprayer.mobile.objects.Prayer;
import android.os.AsyncTask;
import android.util.Log;

public class JoinPrayer extends AsyncTask<Void, Void, Void> {
	
	PrayerView view;
	Prayer prayer;
	
	public JoinPrayer(PrayerView view) {
		this.view = view;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			view.api.joinPrayer(String.valueOf(view.joinId));
		} catch (Exception e) {
			Log.d("us.liveprayer.mobile", "Error getting public prayer list");
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		return;
	}
}