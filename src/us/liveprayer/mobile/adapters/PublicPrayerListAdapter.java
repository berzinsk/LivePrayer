package us.liveprayer.mobile.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import us.liveprayer.mobile.R;
import us.liveprayer.mobile.objects.Prayer;
import us.liveprayer.mobile.tools.API;

public class PublicPrayerListAdapter extends BaseAdapter {
	
	Activity activity;
	List<Prayer> prayers;
	Context context;
	public AsyncTask<Void, Void, Void> getImage;
	int userIndex;
	Bitmap bitmap;
	
	public PublicPrayerListAdapter(Activity activity, Context context, List<Prayer> prayers) {
		super();
		
		this.activity = activity;
		this.prayers = prayers;
		this.context = context;
	}

	@Override
	public int getCount() {
		return prayers.size();
	}

	@Override
	public Object getItem(int index) {
		return prayers.get(index);
	}

	@Override
	public long getItemId(int index) {
		return prayers.get(index).getId();
	}

	@Override
	public View getView(final int index, View view, ViewGroup viewgroup) {
		Tag tag;
		LayoutInflater inflater = activity.getLayoutInflater();
		
		if (view == null) {
			tag = new Tag();
			view = inflater.inflate(R.layout.prayer_row, null);
			tag.prayer = (TextView) view.findViewById(R.id.row_prayer);
			tag.title = (TextView) view.findViewById(R.id.row_title);
			
			final ImageView img = (ImageView) view.findViewById(R.id.image);
			AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					try {
						API api = new API();
						bitmap = api.getImage(prayers.get(index).getImageId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
					
				}
				
				@Override
				protected void onPostExecute(Void result) {
					img.setImageBitmap(getRoundedCornerBitmap(bitmap, 70));
				}
				
			};
			task.execute();
			
			view.setTag(tag);
		} else {
			tag = (Tag) view.getTag();
		}
		
		tag.prayer.setText(prayers.get(index).getText());
		tag.title.setText(prayers.get(index).getTitle());
		return view;
	}
	
	private static class Tag {
		public TextView id;
		TextView sample;
		TextView title;
		TextView prayer;
		ImageView img;
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