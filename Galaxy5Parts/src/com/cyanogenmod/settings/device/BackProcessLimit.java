package com.cyanogenmod.settings.device;

import android.app.ActivityManagerNative;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.preference.Preference;
import android.util.Log;
import android.widget.Toast;

import com.cyanogenmod.settings.device.R;

final class BackProcessLimit implements Preference.OnPreferenceClickListener {

	Context mContext;
	Resources mResources;
	int mSelected;

	BackProcessLimit(Context context, Resources resources) {
		mContext = context;
		mResources = resources;
	}

	public final boolean onPreferenceClick(final Preference preference) {
		
		try {
			mSelected = ActivityManagerNative.getDefault().getProcessLimit();
		} catch (RemoteException e) {
			mSelected = -1;
		}

		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(mContext.getText(R.string.back_process_limit));
		dialog.setSingleChoiceItems(Constants.getProcessLimit(mResources),
						(mSelected + 1), new OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								
								mSelected = arg1 - 1;

								try {
									ActivityManagerNative.getDefault().setProcessLimit(mSelected);
								} catch (RemoteException e) {
									Toast.makeText(mContext, "Error: Cannot set Process Limit", Toast.LENGTH_SHORT).show();
									Log.e("Galaxy5Parts", e.toString());
									arg0.dismiss();
									return;
								}
								
								SystemProperties.set(Constants.PROP_PROCESSLIMIT, "" + mSelected);

								preference.setSummary(Constants.getProcessLimit(mResources)[arg1].toString());

								arg0.dismiss();

							}
						});
		dialog.setNegativeButton(mContext.getText(R.string.cancel), null);
		dialog.show();
		return false;

	}
}
