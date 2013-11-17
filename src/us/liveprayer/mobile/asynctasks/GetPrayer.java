package us.liveprayer.mobile.asynctasks;

import us.liveprayer.mobile.PrayerList;
import us.liveprayer.mobile.PrayerView;
import us.liveprayer.mobile.objects.Prayer;
import android.os.AsyncTask;
import android.util.Log;

public class GetPrayer extends AsyncTask<Void, Void, Void> {
	
	PrayerView view;
	Prayer prayer;
	
	public GetPrayer(PrayerView view) {
		prayer = new Prayer();
		this.view = view;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			this.prayer = view.api.getPrayer(String.valueOf(view.prayer.getId()));
		} catch (Exception e) {
			Log.d("us.liveprayer.mobile", "Error getting public prayer list");
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		view.updateUi(this.prayer);
	}
}