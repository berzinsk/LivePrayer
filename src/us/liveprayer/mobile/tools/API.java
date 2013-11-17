package us.liveprayer.mobile.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import us.liveprayer.mobile.objects.Prayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class API {
	public String versiony;
	public Activity activity;
	private DefaultHttpClient httpclient;
	private HttpGet httpget;
	private HttpPost httppost;
	
	public API(Activity activity, String version) {
		this.activity = activity;
		this.versiony = version;
	}
	
	public API() {
		
	}
	
	public String joinPrayer(String userId) {
		
		String url = "http://www.liveprayer.us/LivePrayerService.JsonRestService.Service.svc/JoinPrayer?sessionId=android&prayerId="+userId;
		Log.d("joinPrayeeer", url);
		
		JSONObject data;
		try {
			data = getJSONObject(url);
			
			if (data == null) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void addPrayer(String text) {
		Log.d("text", text);
		
		String url = "";
		
		JSONObject data;
		try {
			data = postJSONObject(url);
			
			if (data == null) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createPrayer() {
		String url = "http://www.liveprayer.us/LivePrayerService.JsonRestService.Service.svc/CreatePrayer?sessionId=android";
		
		Log.d("URLL", url);
		
		JSONObject data;
		try {
			data = getJSONObject(url);
			
			if (data == null) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Prayer getPrayer(String userId) {
		
		Prayer prayer = new Prayer();
		String url = "http://www.liveprayer.us/LivePrayerService.JsonRestService.Service.svc/GetPrayer?sessionId=android&prayerId="+userId;
		Log.d("GetPrayer URL", url);
		
		JSONObject data;
		try {
			data = getJSONObject(url);
			
			if (data.has("id")) {
				prayer.setId(data.getLong("id"));
			}
			
			if (data.has("imageId")) {
				prayer.setImageId(data.getString("imageId"));
			}
			
			if (data.has("isAccepted")) {
				prayer.setIsAccepted(data.getBoolean("isAccepted"));
			}
			
			if (data.has("isEnabled")) {
				prayer.setIsEnded(data.getBoolean("isEnabled"));
			}
			
			if (data.has("isFinalCountdown")) {
				prayer.setIsFinalCountdown(data.getBoolean("isFinalCountdown"));
			}
			
			if (data.has("startTime")) {
				prayer.setStartTime(data.getString("startTime"));
			}
			
			if (data.has("text")) {
				prayer.setText(data.getString("text"));
			}
			
			if (data.has("title")) {
				prayer.setTitle(data.getString("title"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prayer;
	}
	
	public Bitmap getImage(String pictureId) {
		
		String imageUrl = "http://www.liveprayer.us/GetImage.aspx?imageId="+pictureId;
		Bitmap bitmap = null;
		try {
	        URL url = new URL(imageUrl);
	        HttpGet httpRequest = null;

	        httpRequest = new HttpGet(url.toURI());

	        HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response = (HttpResponse) httpclient
	                .execute(httpRequest);

	        HttpEntity entity = response.getEntity();
	        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
	        InputStream input = b_entity.getContent();

	        bitmap = BitmapFactory.decodeStream(input);
	        return bitmap;

	    } catch (Exception ex) {
	    	Log.d("Error", ex.toString());
	    	return null;
	    }
		
	}
	
	public Prayer getPrayer(String sessionId, String prayerId) {
		
		Prayer prayer = new Prayer();
		String url = "http://www.liveprayer.us/LivePrayerService.JsonRestService.Service.svc/GetPrayer?sessionId={" + sessionId + "}&prayerId={" + prayerId + "}";
		
		JSONObject data = null;
		try {
			data = getJSONObject(url);
			
			if (data.has("text")) {
				prayer.setText(data.getString("text"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prayer;
	}
	
	public List<Prayer> getPrayerList() {
		
		Prayer prayer;
		List<Prayer> prayers = new ArrayList<Prayer>();
		String url = "http://www.liveprayer.us/LivePrayerService.JsonRestService.Service.svc/GetPrayers?sessionId=10";
		
		JSONObject object = null;
		try {
			object = getJSONObject(url);
			
			JSONArray data = null;
			if (object.has("prayers")) {
				Log.d("IRRRR", "IRRRRR");
				data = object.getJSONArray("prayers");
			}
			
			for (int i = 0; i < data.length(); i++) {
				Log.d("prayeeeer", data.getJSONObject(i).getString("text"));
				prayer = new Prayer(data.getJSONObject(i).getLong("id"), data.getJSONObject(i).getBoolean("isMine"), data.getJSONObject(i).getString("imageId"), data.getJSONObject(i).getString("title"), data.getJSONObject(i).getString("text"),
									data.getJSONObject(i).getString("startTime"), data.getJSONObject(i).getBoolean("isFinalCountdown"), data.getJSONObject(i).getBoolean("isStarted"), data.getJSONObject(i).getInt("participantCount"), data.getJSONObject(i).getInt("progress"), data.getJSONObject(i).getBoolean("isEnded"), data.getJSONObject(i).getBoolean("isAccepted"));
				prayers.add(prayer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	private JSONObject postJSONObject(String url) {
		
		String json = this.postData(url);
		
		try {
			return new JSONObject(json);
		} catch (JSONException e) {
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
	
	public String postData(String url) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost();
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
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
					// TODO: handle exception
				} finally {
					inputstream.close();
				}
				
				httpclient.getConnectionManager().shutdown();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String result = stringBuilder.toString().trim();
		return result;
	}
}