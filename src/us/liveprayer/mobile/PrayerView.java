package us.liveprayer.mobile;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PrayerView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prayer_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prayer_view, menu);
		return true;
	}

}
