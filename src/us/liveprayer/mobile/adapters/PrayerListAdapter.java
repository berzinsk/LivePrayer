package us.liveprayer.mobile.adapters;

import java.util.List;

import us.liveprayer.mobile.objects.Prayer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import us.liveprayer.mobile.R;


public class PrayerListAdapter extends BaseAdapter {
	
	Activity activity;
	List<Prayer> prayers;
	Context context;
	
	public PrayerListAdapter(Activity activity, Context context, List<Prayer> prayers) {
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
	public View getView(int index, View view, ViewGroup viewgroup) {
		Tag tag;
		LayoutInflater inflater = activity.getLayoutInflater();
		
		if (view == null) {
			tag = new Tag();
			view = inflater.inflate(R.layout.prayer_row, null);
			tag.prayer = (TextView) view.findViewById(R.id.row_prayer);
			view.setTag(tag);
		} else {
			tag = (Tag) view.getTag();
		}
		
		tag.prayer.setText(prayers.get(index).getText());
		
		return view;
	}
	
	private static class Tag {
		TextView prayer;
	}
}