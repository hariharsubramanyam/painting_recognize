package com.example.museumtour.fragments;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

import pl.itraff.TestApi.ItraffApi.ItraffApi;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.museumtour.R;
import com.example.museumtour.adapters.MuseumDisplayArrayAdapter;
import com.example.museumtour.helpers.APIKeys;
import com.example.museumtour.models.DisplayModel;
import com.example.museumtour.models.MuseumDisplays;

public class MainFragment extends Fragment{
	private static final String TAG = "MainFragment";
		
	ListView lstMain;
	Button btnTakePicture;
	ImageView imgPicture;
	TextView txtDescription;
	MuseumDisplays museumDisplays;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = (View)inflater.inflate(R.layout.main_fragment, container, false);
		String data = getDisplayData();
		try{
			JSONObject json = new JSONObject(data);
			museumDisplays = new MuseumDisplays(json);
			MuseumDisplayArrayAdapter adapter = new MuseumDisplayArrayAdapter(getActivity(), museumDisplays.toList());
			txtDescription = (TextView)v.findViewById(R.id.txt_description);
			imgPicture = (ImageView)v.findViewById(R.id.img_picture);
			btnTakePicture = (Button)v.findViewById(R.id.btn_takePicture);
			btnTakePicture.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					takePictureIntent.putExtra(MediaStore.EXTRA_FULL_SCREEN, true);
					takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, true);
					takePictureIntent.putExtra(MediaStore.EXTRA_SHOW_ACTION_ICONS, false);
					startActivityForResult(takePictureIntent, 1234);
				}
			});
			lstMain = (ListView)v.findViewById(R.id.lst_main);
			lstMain.setAdapter(adapter);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return v;
	}
	
	private String getDisplayData(){
		String json = null;
		try{
			InputStream is = getActivity().getAssets().open("displays.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		}catch(IOException ex){
			ex.printStackTrace();
			return null;
		}
		return json;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "requestCode: " + requestCode + " and resultCode: " + resultCode);
		if(resultCode == Activity.RESULT_OK){
			if(data != null){
				Bundle bundle = data.getExtras();
				if(bundle != null){
					Bitmap image = (Bitmap) bundle.get("data");
					if(image != null){
						if(ItraffApi.isOnline(getActivity())){
							ItraffApi api = new ItraffApi(APIKeys.API_ID, APIKeys.API_KEY, TAG, true);
							ByteArrayOutputStream stream = new ByteArrayOutputStream();
							image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
							byte[] pictureData = stream.toByteArray();
							api.sendPhoto(pictureData, itraffApiHandler, true);
						}
					}
				}
			}
		}
	}
	
	private void onReceiveImageResponse(String response){
		try{
			DisplayModel model = this.museumDisplays.getDisplayModelFromAPIResponse(new JSONObject(response));
			if(model != null){
				txtDescription.setText(model.getDescription());
				new DownloadImageTask(this.imgPicture).execute(model.getPictureURL());
			}else{
				txtDescription.setText("Could not find model");
			}
		}catch(Exception ex){
			txtDescription.setText("Error when trying to parse data");
			ex.printStackTrace();
		}
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
	@SuppressLint("HandlerLeak")
	private Handler itraffApiHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			Bundle data = msg.getData();
			if(data != null){
				Integer status = data.getInt(ItraffApi.STATUS, -1);
				String response = data.getString(ItraffApi.RESPONSE);
				if(status.intValue() == 0){
					onReceiveImageResponse(response);
				}
			}
		};
	};
	
}
