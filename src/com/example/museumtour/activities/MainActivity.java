package com.example.museumtour.activities;

import com.example.museumtour.fragments.MainFragment;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new MainFragment();
	}

}
