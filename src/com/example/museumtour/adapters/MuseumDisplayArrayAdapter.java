package com.example.museumtour.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.museumtour.R;
import com.example.museumtour.models.DisplayModel;

public class MuseumDisplayArrayAdapter extends ArrayAdapter<DisplayModel>{
	 
	private static class ViewHolder {
		TextView id;
		TextView url;
	}
	
	
	public MuseumDisplayArrayAdapter(Context context, ArrayList<DisplayModel> displays) {
		super(context, R.layout.museum_display_list_item, displays);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DisplayModel display = getItem(position);
		
		ViewHolder viewHolder;
		
		if(convertView == null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.museum_display_list_item, null);
			viewHolder.id = (TextView)convertView.findViewById(R.id.display_id);
			viewHolder.url = (TextView)convertView.findViewById(R.id.display_url);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.id.setText(display.getID());
		viewHolder.url.setText(display.getPictureURL());
		
		return convertView;
	}

}
