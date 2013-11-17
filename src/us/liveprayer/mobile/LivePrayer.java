package us.liveprayer.mobile;

import us.liveprayer.mobile.objects.Prayer;
import us.liveprayer.mobile.tools.API;
import android.app.Application;

public class LivePrayer extends Application {
	
	private API api;
	private Prayer prayer;
	public static final String url = "http://localhost:8732/service/json/";
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public synchronized LivePrayer setPrayer(Prayer prayer) {
		this.prayer = prayer;
		return this;
	}
	
	public synchronized LivePrayer setAPI(API api) {
		this.api = api;
		return this;
	}
	
	public synchronized Prayer getPrayer() {
		return this.prayer;
	}
	
	public synchronized API getAPI() {
		return this.api;
	}
}