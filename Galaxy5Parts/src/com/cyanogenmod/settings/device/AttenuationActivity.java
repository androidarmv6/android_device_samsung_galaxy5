package com.cyanogenmod.settings.device;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class AttenuationActivity extends PreferenceActivity {

	public static boolean isAttenuationOn() {
		boolean bool = SystemProperties.get(Constants.PROP_FM_ATTN).equals("0")
				&& SystemProperties.get(Constants.PROP_HEADSET_ATTN).equals("0")
				&& SystemProperties.get(Constants.PROP_SPEAKER_ATTN).equals("0");
		return !bool;
	}

	public static void switchOnClick(boolean isChecked, SharedPreferences sharedPreferences) {
		if (isChecked) {
			int last_speaker_attn = sharedPreferences.getInt("last_speaker_attn", 0);
			int last_headset_attn = sharedPreferences.getInt("last_headset_attn", 0);
			int last_fm_attn = sharedPreferences.getInt("last_fm_attn", 0);
			SystemProperties.set(Constants.PROP_SPEAKER_ATTN, String.valueOf(last_speaker_attn));
			SystemProperties.set(Constants.PROP_HEADSET_ATTN, String.valueOf(last_headset_attn));
			SystemProperties.set(Constants.PROP_FM_ATTN, String.valueOf(last_fm_attn));
		} else {
			SystemProperties.set(Constants.PROP_SPEAKER_ATTN, "0");
			SystemProperties.set(Constants.PROP_HEADSET_ATTN, "0");
			SystemProperties.set(Constants.PROP_FM_ATTN, "0");
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			super.onBackPressed();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem menuItem = menu.add(getText(R.string.attenuation));

		menuItem.setActionView(R.layout.header_switch).setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);

		headerSwitch(menuItem.getActionView());

		return super.onCreateOptionsMenu(menu);
	}

	private void headerSwitch(final View view) {

		Switch s = (Switch) view.findViewById(R.id.header_switch);
		if (DeviceSettings.mPrefAttenuation != null) {
			s.setChecked(DeviceSettings.mPrefAttenuation.isChecked());
		} else {
			s.setChecked(isAttenuationOn());
		}
		s.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
				switchOnClick(arg1, sharedPreferences);
				if (DeviceSettings.mPrefAttenuation != null) {
					DeviceSettings.mPrefAttenuation.setChecked(arg1);
				}
				loadScreen(arg1);
			}
		});
	}

	private void loadScreen(boolean bool) {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		Resources resources = getResources();

		PreferenceScreen prefScreen = getPreferenceManager().createPreferenceScreen(this);
		prefScreen.setEnabled(bool);

		PreferenceCategory globalCat = new PreferenceCategory(this);
		globalCat.setTitle(getText(R.string.global));
		prefScreen.addPreference(globalCat);

		// ATTENUATION HEADSET
		Preference attnHeadset = new Preference(this);
		attnHeadset.setTitle(getText(R.string.attn_headset));
		int currentAttnH;
		int lastAttnH = sharedPreferences.getInt("last_headset_attn", 0);
		try {
			currentAttnH = Integer.parseInt(SystemProperties.get(Constants.PROP_HEADSET_ATTN));
		} catch (NumberFormatException e) {
			currentAttnH = 0;
		}
		attnHeadset.setSummary(getText(R.string.current_setting) + ": " + Constants.getAttn(resources)[bool ? currentAttnH : lastAttnH]);
		attnHeadset.setEnabled(true);
		attnHeadset.setOnPreferenceClickListener(new AttnHeadset(this, resources, sharedPreferences));
		globalCat.addPreference(attnHeadset);

		// ATTENUATION SPEAKER
		Preference attnSpeaker = new Preference(this);
		attnSpeaker.setTitle(getText(R.string.attn_speaker));
		int currentAttnS;
		int lastAttnS = sharedPreferences.getInt("last_speaker_attn", 0);
		try {
			currentAttnS = Integer.parseInt(SystemProperties.get(Constants.PROP_SPEAKER_ATTN));
		} catch (NumberFormatException e) {
			currentAttnS = 0;
		}
		attnSpeaker.setSummary(getText(R.string.current_setting) + ": " + Constants.getAttn(resources)[bool ? currentAttnS : lastAttnS]);
		attnSpeaker.setEnabled(true);
		attnSpeaker.setOnPreferenceClickListener(new AttnSpeaker(this, resources, sharedPreferences));
		globalCat.addPreference(attnSpeaker);


		// ATTENUATION FM
		PreferenceCategory fmCat = new PreferenceCategory(this);
		fmCat.setTitle(getText(R.string.fm_radio));
		prefScreen.addPreference(fmCat);
		
		Preference attnFM = new Preference(this);
		attnFM.setTitle(getText(R.string.attn_headset_speaker));
		int currentAttnFM;
		int lastAttnFM = sharedPreferences.getInt("last_fm_attn", 0);
		try {
			currentAttnFM = Integer.parseInt(SystemProperties.get(Constants.PROP_FM_ATTN));
		} catch (NumberFormatException e) {
			currentAttnFM = 0;
		}
		attnFM.setSummary(getText(R.string.current_setting) + ": " + Constants.getAttn(resources)[bool ? currentAttnFM : lastAttnFM]);
		attnFM.setEnabled(true);
		attnFM.setOnPreferenceClickListener(new AttnFM(this, resources, sharedPreferences));
		fmCat.addPreference(attnFM);
		
		setPreferenceScreen(prefScreen);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (DeviceSettings.mPrefAttenuation != null) {
			loadScreen(DeviceSettings.mPrefAttenuation.isChecked());
		} else {
			loadScreen(isAttenuationOn());
		}
	}

}
