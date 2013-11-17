package us.liveprayer.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import us.liveprayer.mobile.adapters.PublicPrayerListAdapter;
import us.liveprayer.mobile.asynctasks.AddPrayerAsync;
import us.liveprayer.mobile.asynctasks.GetPrayer;
import us.liveprayer.mobile.asynctasks.GetPublicList;
import us.liveprayer.mobile.asynctasks.GetPublicPrayerList;
import us.liveprayer.mobile.objects.Prayer;
import us.liveprayer.mobile.tools.API;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class PrayerList extends Activity {
	
	public API api;
	ImageView add_prayer;
	
	public AsyncTask<Void, Void, Void> getPrayerList;
	public AsyncTask<Void, Void, Void> getPublicPrayerList;
	public AsyncTask<Void, Void, Void> getPrayer;
	public AsyncTask<Void, Void, Void> createPrayer;
	public List<Prayer> prayers = new ArrayList<Prayer>();
	public List<Prayer> publicPrayers = new ArrayList<Prayer>();
	public Prayer prayer;
	public ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			api = new API(this, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setContentView(R.layout.prayer_list);
		
		add_prayer = (ImageView) findViewById(R.id.add_prayer);
		add_prayer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				progressDialog = ProgressDialog.show(PrayerList.this, null, "Loading...", true);
				progressDialog.show();
				createPrayer = new AddPrayerAsync(PrayerList.this).execute();
			}
		});
		
		getPublicPrayerList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prayer_list, menu);
		return true;
	}
	
	public void getPublicPrayerList() {
		
		final Handler handler = new Handler();
	    Timer timer = new Timer();
	    TimerTask doAsynchronousTask = new TimerTask() {       
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {       
	                    try {
	                    	getPublicPrayerList = new GetPublicPrayerList(PrayerList.this).execute();
	                    	getPrayerList.execute();
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            });
	        }
	    };
	    timer.schedule(doAsynchronousTask, 0, 50000);
	}
	
	public void updatePublicPrayers(List<Prayer> prayers) {
		if (prayers == null) {
			findViewById(R.id.all_prayers).setVisibility(View.GONE);
		} else {
			findViewById(R.id.all_prayers).setVisibility(View.VISIBLE);
		}
		
		ListView publicPrayers = (ListView) findViewById(R.id.all_prayers);
		publicPrayers.setAdapter(new PublicPrayerListAdapter(this, getApplicationContext(), prayers));
		
		publicPrayers.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				choosePrayer(position);
			}
		});
	}
	
	public void choosePrayer(int position) {
		((LivePrayer) getApplication()).setPrayer(publicPrayers.get(position));
		startActivity(new Intent(PrayerList.this, PrayerView.class));
	}
	
	public void updateUi(Prayer prayer) {
		Log.d("", prayer.getText());
	}
	
	public void updateUi() {
		getPublicPrayerList = new GetPublicPrayerList(PrayerList.this).execute();
	}

}
