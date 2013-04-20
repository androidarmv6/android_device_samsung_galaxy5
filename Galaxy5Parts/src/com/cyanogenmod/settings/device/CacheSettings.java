package com.cyanogenmod.settings.device;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.preference.Preference;
import com.cyanogenmod.settings.device.R;

final class CacheSize implements Preference.OnPreferenceClickListener {

	Context mContext;
	Resources mResources;
	int mSelected;

	CacheSize(Context context, Resources resources) {
		mContext = context;
		mResources = resources;
	}

    public final boolean onPreferenceClick(final Preference preference) {

		try {
			mSelected = Integer.parseInt(SystemProperties.get(Constants.PROP_CACHESIZE));
		} catch (NumberFormatException e) {
			mSelected = 0;
		}
		
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(mContext.getText(R.string.cache_size));
        dialog.setSingleChoiceItems(Constants.getCacheSize(mResources), mSelected, new OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        SystemProperties.set(Constants.PROP_CACHESIZE, ""
                                + arg1);
                        preference.setSummary(Constants.getCacheSize(mResources)[arg1].toString());

                        arg0.dismiss();

                    }
                });
        dialog.setNegativeButton(mContext.getText(R.string.cancel), null);
        dialog.show();
        return false;

    }
}
