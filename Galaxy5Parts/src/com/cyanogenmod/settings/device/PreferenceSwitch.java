package com.cyanogenmod.settings.device;

import android.content.Context;
import android.preference.Preference;
import android.view.View;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PreferenceSwitch extends Preference {

	private boolean mChecked;
	private OnCheckedChangeListener onCheckedChangeListener;

	public PreferenceSwitch(Context context) {
		super(context);
		setWidgetLayoutResource(R.layout.preference_widget_switch);
	}

	public boolean isChecked() {
		return mChecked;
	}

	public void setChecked(boolean bool) {
		mChecked = bool;
		notifyChanged();
	}

	public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
		onCheckedChangeListener = listener;
	}

	@Override
	public void onBindView(final View view) {
		super.onBindView(view);

		Switch s = (Switch) view.findViewById(R.id.header_switch);
		s.setChecked(mChecked);
		if (onCheckedChangeListener != null) {
			s.setOnCheckedChangeListener(onCheckedChangeListener);
		}
	}
	
}
