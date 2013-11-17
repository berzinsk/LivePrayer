package us.liveprayer.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import us.liveprayer.mobile.adapters.PublicPrayerListAdapter;
import us.liveprayer.mobile.asynctasks.GetPublicList;
import us.liveprayer.mobile.objects.Prayer;
import us.liveprayer.mobile.tools.API;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class Authorization extends Activity {
	public API api;
	ImageView authorize;
	public List<Prayer> prayers = new ArrayList<Prayer>();
	public ProgressDialog progressDialog;
	
	public AsyncTask<Void, Void, Void> getPrayerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authorization);
		
		try {
			api = new API(this, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		authorize = (ImageView) findViewById(R.id.login_button);
		authorize.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Authorization.this, PrayerList.class));
			}
		});
		
		progressDialog = ProgressDialog.show(Authorization.this, null, "Loading...", true);
		progressDialog.show();
		
		getPrayerList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.authorization, menu);
		return true;
	}
	
	public void getPrayerList() {
		final Handler handler = new Handler();
	    Timer timer = new Timer();
	    TimerTask doAsynchronousTask = new TimerTask() {       
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {       
	                    try {
	                    	getPrayerList = new GetPublicList(Authorization.this);
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
	
	public void updateUi(List<Prayer> prayers) {
		if (prayers == null) {
			findViewById(R.id.authorization_list).setVisibility(View.GONE);
		} else {
			findViewById(R.id.authorization_list).setVisibility(View.VISIBLE);
		}
		ListView publicPrayers = (ListView) findViewById(R.id.authorization_list);
		publicPrayers.setAdapter(new PublicPrayerListAdapter(this, getApplicationContext(), prayers));
	}

}
