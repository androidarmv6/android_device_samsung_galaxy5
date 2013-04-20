package com.cyanogenmod.settings.device;

import android.content.res.Resources;

public class Constants {

	public static final String PROP_CACHESIZE = "persist.sys.read_ahead_kb";
	public static final String PROP_COMPCACHE = "persist.service.zram";
	public static final String PROP_COMPCACHE_RO = "ro.zram.default";
	public static final String PROP_FAKE_DT = "persist.sys.fakedt";
	public static final String PROP_FM_ATTN = "persist.sys.attn-fm";
	public static final String PROP_HEADSET_ATTN = "persist.sys.attn-headset";
	public static final String PROP_SPEAKER_ATTN = "persist.sys.attn-speaker";
	public static final String PROP_LMK = "persist.sys.dynlmk";
	public static final String PROP_PROCESSLIMIT = "persist.sys.process_limit";
	public static final String PROP_ROOTACCESS = "persist.sys.root_access";
	public static final String PROP_SAMSUNG_EXTAMP_FILTER = "persist.sys.extamp-filter";
	public static final String PROP_SWAP = "persist.sys.swap";

	public static CharSequence[] getAttn(Resources resources) {

		CharSequence[] attn = resources.getStringArray(R.array.attenuation);

		return attn;
	}

	public static CharSequence[] getCacheSize(Resources resources) {

		CharSequence[] cacheSize = resources.getStringArray(R.array.cache_size);
		
		return cacheSize;
	}

	public static CharSequence[] getProcessLimit(Resources resources) {

		CharSequence[] processLimit = resources.getStringArray(R.array.background_limit);

		return processLimit;
	}

}
