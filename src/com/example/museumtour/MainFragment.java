package com.example.museumtour;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.museumtour.helperui.MuseumDisplayArrayAdapter;
import com.example.museumtour.model.CSVReader;
import com.example.museumtour.model.DisplayModel;

public class MainFragment extends Fragment{
	private static final String TAG = "MainFragment";
	private String[] line;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = (View)inflater.inflate(R.layout.main_fragment, container, false);
		ArrayList<DisplayModel> displayModels = getDisplayData();
		MuseumDisplayArrayAdapter adapter = new MuseumDisplayArrayAdapter(getActivity(), displayModels);
		ListView lstMain = (ListView)v.findViewById(R.id.lst_main);
		lstMain.setAdapter(adapter);
		return v;
	}
	
	private ArrayList<DisplayModel> getDisplayData(){
		ArrayList<DisplayModel> displayModels = new ArrayList<DisplayModel>();
		CSVReader reader = new CSVReader(getImagesInputStream());
		try{
			while((line = reader.readNext()) != null){
				displayModels.add(new DisplayModel(line));
			}
			reader.close();
		}catch(Exception ex){
			Log.e(TAG, "Could not open reader");
		}
		
		return displayModels;
	}
	
	private InputStreamReader getImagesInputStream(){
		try {
			return new InputStreamReader(getActivity().getAssets().open("images.csv"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
