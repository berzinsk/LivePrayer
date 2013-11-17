package us.liveprayer.mobile;

import us.liveprayer.mobile.asynctasks.AddPrayerAsync;
import us.liveprayer.mobile.tools.API;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddPrayer extends Activity {
	
	public API api;
	public String text;
	
	TextView prayer_info;
	EditText prayer;
	Button send;
	
	public AsyncTask<Void, Void, Void> addPrayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_prayer);
		
		try {
			api = new API(this, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		prayer_info = (TextView) findViewById(R.id.textView1);
		prayer = (EditText) findViewById(R.id.editText1);
		send = (Button) findViewById(R.id.send);
		
		send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				text = prayer.getText().toString();
//				addPrayer = new AddPrayerAsync(AddPrayer.this).execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_prayer, menu);
		return true;
	}
	
	public void updateUi() {
		
	}

}
