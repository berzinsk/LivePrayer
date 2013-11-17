package us.liveprayer.mobile;

import us.liveprayer.mobile.asynctasks.GetPrayer;
import us.liveprayer.mobile.asynctasks.JoinPrayer;
import us.liveprayer.mobile.objects.Prayer;
import us.liveprayer.mobile.tools.API;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PrayerView extends Activity {
	private static final String TAG = "us.liveprayer.mobile";
	
	public API api;
	public Prayer prayer;
	public AsyncTask<Void, Void, Void> getPrayer;
	public AsyncTask<Void, Void, Void> joinPrayer;
	public String joinId;
	
	TextView prayer_content;
	TextView content;
	TextView title;
	Bitmap bitmap;
	public ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prayer_view);
		
		try {
			api = new API(this, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
		} catch (Exception e) {
			Log.d(TAG, "Error creating API");
		}
		
		prayer = ((LivePrayer) this.getApplicationContext()).getPrayer();
		
		getPrayer = new GetPrayer(this).execute();
		prayer_content = (TextView) findViewById(R.id.prayer_content);
		title = (TextView) findViewById(R.id.text_title);
		imageView = (ImageView) findViewById(R.id.imageView1);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prayer_view, menu);
		return true;
	}
	
	public void updateUi(final Prayer prayer) {
		prayer_content.setText(prayer.getText());
		title.setText(prayer.getTitle());
		joinId = String.valueOf(prayer.getId());
		
		joinPrayer = new JoinPrayer(this).execute();
		
		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					API api = new API();
					bitmap = api.getImage(prayer.getImageId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
				
			}
			
			@Override
			protected void onPostExecute(Void result) {
				imageView.setImageBitmap(getRoundedCornerBitmap(bitmap, 70));
			}
			
		};
		
		task.execute();
		
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
