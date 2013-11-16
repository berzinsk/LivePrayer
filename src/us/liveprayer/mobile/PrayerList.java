package us.liveprayer.mobile;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import us.liveprayer.mobile.adapters.PrayerListAdapter;
import us.liveprayer.mobile.adapters.PublicPrayerListAdapter;
import us.liveprayer.mobile.asynctasks.GetPrayerList;
import us.liveprayer.mobile.asynctasks.GetPublicPrayerList;
import us.liveprayer.mobile.objects.Prayer;
import us.liveprayer.mobile.tools.API;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PrayerList extends Activity {
	
	public API api;
	
	private PullToRefreshListView pullToRefreshView;
	public AsyncTask<Void, Void, Void> getPrayerList;
	public AsyncTask<Void, Void, Void> getPublicPrayerList;
	public List<Prayer> prayers = new ArrayList<Prayer>();
	public List<Prayer> publicPrayers = new ArrayList<Prayer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			api = new API(this, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setContentView(R.layout.prayer_list);
		
		getPrayerList();
		getPublicPrayerList();
		
		pullToRefreshView = (PullToRefreshListView) findViewById(R.id.list_list);
		pullToRefreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				getPrayerList();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prayer_list, menu);
		return true;
	}
	
	public void getPrayerList() {
		getPrayerList = new GetPrayerList(this, true).execute();
	}
	
	public void getPublicPrayerList() {
		getPublicPrayerList = new GetPublicPrayerList(this).execute();
	}
	
	public void updateUi(List<Prayer> prayers) {
		pullToRefreshView.onRefreshComplete();
		if (prayers == null) {
			findViewById(R.id.list_list).setVisibility(View.GONE);
		} else {
			findViewById(R.id.list_list).setVisibility(View.VISIBLE);
		}
		
		final PullToRefreshListView list = (PullToRefreshListView) findViewById(R.id.list_list);
		list.setAdapter(new PrayerListAdapter(this, getApplicationContext(), prayers));
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
		Prayer prayer = publicPrayers.get(position);
		((LivePrayer) getApplication()).setPrayer(publicPrayers.get(position));
		startActivity(new Intent(PrayerList.this, PrayerView.class));
	}

}
