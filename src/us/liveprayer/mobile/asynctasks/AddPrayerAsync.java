package us.liveprayer.mobile.asynctasks;

import us.liveprayer.mobile.PrayerList;
import us.liveprayer.mobile.objects.Prayer;
import android.os.AsyncTask;
import android.util.Log;

public class AddPrayerAsync extends AsyncTask<Void, Void, Void> {
	
	PrayerList view;
	Prayer prayer;
	
	public AddPrayerAsync(PrayerList view) {
		this.view = view;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			Log.d("Strada", "strada");
			view.api.createPrayer();
		} catch (Exception e) {
			Log.d("us.liveprayer.mobile", "Error getting public prayer list");
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		view.progressDialog.dismiss();
		view.updateUi();
	}
}