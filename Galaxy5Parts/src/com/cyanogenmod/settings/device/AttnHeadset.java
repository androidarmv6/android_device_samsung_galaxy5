package com.cyanogenmod.settings.device;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.preference.Preference;
import com.cyanogenmod.settings.device.R;

final class AttnHeadset implements Preference.OnPreferenceClickListener {

	Context mContext;
	Resources mResources;
	int mSelected;
	SharedPreferences mSharedPreferences;

	AttnHeadset(Context context, Resources resources,
			SharedPreferences sharedPreferences) {
		mContext = context;
		mResources = resources;
		mSharedPreferences = sharedPreferences;
	}

	public final boolean onPreferenceClick(final Preference preference) {

		try {
			mSelected = Integer.parseInt(SystemProperties
					.get(Constants.PROP_HEADSET_ATTN));
		} catch (NumberFormatException e) {
			mSelected = 0;
		}

		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(mContext.getText(R.string.attn_headset));
		dialog.setSingleChoiceItems(Constants.getAttn(mResources), mSelected,
				new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {

						SystemProperties.set(Constants.PROP_HEADSET_ATTN, ""
								+ arg1);
						preference.setSummary(mContext
								.getText(R.string.current_setting)
								+ ": "
								+ Constants.getAttn(mResources)[arg1]
										.toString());
						mSharedPreferences.edit()
								.putInt("last_headset_attn", arg1).commit();
						arg0.dismiss();

					}
				});
		dialog.setNegativeButton(mContext.getText(R.string.cancel), null);
		dialog.show();
		return false;

	}
}
