package us.liveprayer.mobile.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import us.liveprayer.mobile.objects.Prayer;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

public class API {
	public String versiony;
	public Activity activity;
	private DefaultHttpClient httpclient;
	private HttpGet httpget;
	
	public API(Activity activity, String version) {
		this.activity = activity;
		this.versiony = version;
	}
	
	public List<Prayer> getPersonalPrayerList() {
		
		Prayer prayer;
		List<Prayer> prayers = new ArrayList<Prayer>();
		String url = "";
		
		for (int i = 0; i < 3; i++) {
			prayer = new Prayer(2457, "This is initial test to see if the list is actialy working and showing smthing.");
			prayers.add(prayer);
		}
		Log.d("prayers", String.valueOf(prayers));
		return prayers;
	}
	
	private JSONObject getJSONObject(String url) {
		
		String json = this.getData(url);
		
		try {
			return new JSONObject(json);
		} catch (Exception e) {
			return null;
		}
	}
	
	private String getData(String url) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			httpclient = new DefaultHttpClient(); 
			httpget = new HttpGet(url);
			
			HttpEntity entity = null;
			try {
				HttpResponse response = httpclient.execute(httpget);
				entity = response.getEntity();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (entity != null) {
				InputStream inputstream = null;
				inputstream = entity.getContent();
				
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
					String line = null;
					
					while ((line = reader.readLine()) != null) {
						stringBuilder.append(line + "\n");
					}
					
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					inputstream.close();
				}
				
				httpclient.getConnectionManager().shutdown();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String result = stringBuilder.toString().trim();
		return result;
	}
}