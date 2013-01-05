package com.cyanogenmod.settings.device;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import com.cyanogenmod.settings.device.R;

public class DeviceSettings extends PreferenceActivity {

    public static Context mContext;

    public static String PROP_CACHESIZE = "persist.sys.read_ahead_kb";
    public static String PROP_COMPCACHE = "persist.service.zram";
    public static String PROP_COMPCACHE_RO = "ro.zram.default";
    public static String PROP_FAKE_DT = "persist.sys.fakedt";
    public static String PROP_FM_ATTN = "persist.sys.attn-fm";
    public static String PROP_HEADSET_ATTN = "persist.sys.attn-headset";
    public static String PROP_LMK = "persist.sys.dynlmk";
    public static String PROP_PROCESSLIMIT = "persist.sys.process_limit";
    public static String PROP_ROOTACCESS = "persist.sys.root_access";
    public static String PROP_SAMSUNG_EXTAMP_FILTER = "persist.sys.extamp-filter";
    public static String PROP_SPEAKER_ATTN = "persist.sys.attn-speaker";
    public static String PROP_SWAP = "persist.sys.swap";

    public static CharSequence[] attn = { "0 dB (disabled)", "1 dB",
            "2 dB", "3 dB", "4 dB", "5 dB", "6 dB", "7 dB", "8 dB", "9 dB" };
    public static CharSequence[] cachesize = { "128KB (default)", "256KB",
            "512KB", "1MB", "2MB", "4MB" };
    public static CharSequence[] processlimit = { "Standard limit (default)", "No background processes",
            "At most 1 processes", "At most 2 processes", "At most 3 processes", "At most 4 processes" };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        PreferenceScreen PrefScreen = getPreferenceManager()
                .createPreferenceScreen(this);

        PreferenceCategory AudioCat = new PreferenceCategory(this);
        AudioCat.setTitle(getText(R.string.audio_cat));
        PrefScreen.addPreference(AudioCat);

        // EXTAMP FILTER
        final CheckBoxPreference Extamp = new CheckBoxPreference(this);
        Extamp.setTitle(getText(R.string.extamp));
        Extamp.setSummaryOn(getText(R.string.extamp_sum_on));
        Extamp.setSummaryOff(getText(R.string.extamp_sum_off));
        String isExtampON = Command.getprop(PROP_SAMSUNG_EXTAMP_FILTER);
        if (isExtampON.equals("1")) {
            Extamp.setChecked(true);
        } else {
            Extamp.setChecked(false);
        }
        Extamp.setEnabled(true);
        Extamp.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference arg0) {
                if (Extamp.isChecked()) {
                    SystemProperties.set(PROP_SAMSUNG_EXTAMP_FILTER, "1");
                } else {
                    SystemProperties.set(PROP_SAMSUNG_EXTAMP_FILTER, "0");

                }

                return false;
            }
        });
        AudioCat.addPreference(Extamp);

        // ATTENUATION FM
        Preference AttnFM = new Preference(this);
        AttnFM.setTitle(getText(R.string.attn_fm));
        String currentAttnFM = Command.getprop(PROP_FM_ATTN);
        
        int currentAttnFMi = 0;
        if (currentAttnFM.length() == 1) {
            currentAttnFMi = Integer.parseInt(currentAttnFM);
        }
        AttnFM.setSummary(getText(R.string.current_setting) + " " + attn[currentAttnFMi]);

        AttnFM.setEnabled(true);
        AttnFM.setOnPreferenceClickListener(new AttnFM(this));
        AudioCat.addPreference(AttnFM);

        PreferenceCategory Memory = new PreferenceCategory(this);
        Memory.setTitle(getText(R.string.memory_man));
        PrefScreen.addPreference(Memory);

        // ATTENUATION HEADSET
        Preference AttnH = new Preference(this);
        AttnH.setTitle(getText(R.string.attn_headset));
        String currentAttnH = Command.getprop(PROP_HEADSET_ATTN);
        if (currentAttnH.equals("0")) {
            currentAttnH = getText(R.string.disabled).toString();
        }
        int currentAttnHi = 0;
        if (currentAttnH.length() == 1) {
            currentAttnHi = Integer.parseInt(currentAttnH);
        }
        AttnH.setSummary(getText(R.string.current_setting) + " " + attn[currentAttnHi]);
        AttnH.setEnabled(true);
        AttnH.setOnPreferenceClickListener(new AttnHeadset(this));
        AudioCat.addPreference(AttnH);

        // ATTENUATION SPEAKER
        Preference AttnS = new Preference(this);
        AttnS.setTitle(getText(R.string.attn_speaker));
        String currentAttnS = Command.getprop(PROP_SPEAKER_ATTN);
        int currentAttnSi = 0;
        if (currentAttnS.length() == 1) {
            currentAttnSi = Integer.parseInt(currentAttnS);
        }
        AttnS.setSummary(getText(R.string.current_setting) + " " + attn[currentAttnSi]);
        AttnS.setEnabled(true);
        AttnS.setOnPreferenceClickListener(new AttnSpeaker(this));
        AudioCat.addPreference(AttnS);

        // BACKGROUND PROCESS LIMIT
        Preference BackProcessLimit = new Preference(this);
        BackProcessLimit.setTitle(getText(R.string.back_process_limit));
        String currentBackProcessLimit = Command.getprop(PROP_PROCESSLIMIT);
        
        int currentBackProcessLimiti = -1;
        if (currentBackProcessLimit.length() > 0) {
            currentBackProcessLimiti = (Integer.parseInt(currentBackProcessLimit));
        }
        BackProcessLimit.setSummary(getText(R.string.current_setting) + " " + processlimit[currentBackProcessLimiti+1]);

        BackProcessLimit.setEnabled(true);
        BackProcessLimit.setOnPreferenceClickListener(new BackProcessLimit(this));
        Memory.addPreference(BackProcessLimit);

        // DYNAMIC LMK
        final CheckBoxPreference LMK = new CheckBoxPreference(this);
        LMK.setTitle(getText(R.string.lmk));
        LMK.setSummary(getText(R.string.lmk_sum));
        String isLMKON = Command.getprop(PROP_LMK);
        if (isLMKON.equals("1")) {
            LMK.setChecked(true);
        } else {
            LMK.setChecked(false);
        }
        LMK.setEnabled(true);
        LMK.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference arg0) {
                if (LMK.isChecked()) {
                    SystemProperties.set(PROP_LMK, "1");
                } else {
                    SystemProperties.set(PROP_LMK, "0");

                }

                return false;
            }
        });
        Memory.addPreference(LMK);

        // CACHE SIZE
        Preference CacheSize = new Preference(this);
        CacheSize.setTitle(getText(R.string.cache_size));
        String currentCacheSize = Command.getprop(PROP_CACHESIZE);
        int currentCacheSizei = 0;
        if (currentCacheSize.length() > 0) {
            currentCacheSizei = Integer.parseInt(currentCacheSize);
        }
        CacheSize.setSummary(getText(R.string.current_setting) + " " + cachesize[currentCacheSizei]);
        CacheSize.setEnabled(true);
        CacheSize.setOnPreferenceClickListener(new CacheSize(this));
        Memory.addPreference(CacheSize);

        // SWAP
        final CheckBoxPreference Swap = new CheckBoxPreference(this);
        Swap.setTitle("Swap");
        Swap.setSummary(getText(R.string.swap_sum));
        String isSwapON = Command.getprop(PROP_SWAP);
        if (isSwapON.equals("1")) {
            Swap.setChecked(true);
        } else {
            Swap.setChecked(false);
        }

        String isCompcacheON = Command.getprop(PROP_COMPCACHE); // persist

        if (isCompcacheON.equals("")) {
            isCompcacheON = Command.getprop(PROP_COMPCACHE_RO); // use read-only prop instead
        }

        if (isCompcacheON.equals("0") || isCompcacheON.equals("")) {
            Swap.setEnabled(true);
        } else {
            Swap.setEnabled(false);
            Swap.setSummary(getText(R.string.swap_cce));

        }

        Swap.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference arg0) {
                if (Swap.isChecked()) {
                    SystemProperties.set(PROP_SWAP, "1");
                } else {
                    SystemProperties.set(PROP_SWAP, "0");

                }

                return false;
            }
        });
        Memory.addPreference(Swap);

        PreferenceCategory ScreenCat = new PreferenceCategory(this);
        ScreenCat.setTitle(getText(R.string.screen_cat));
        PrefScreen.addPreference(ScreenCat);

        // FAKE DUALTOUCH
        final CheckBoxPreference DT = new CheckBoxPreference(this);
        DT.setTitle(getText(R.string.fake_dt));
        DT.setSummary(getText(R.string.fake_dt_sum));
        String isDTON = Command.getprop(PROP_FAKE_DT);
        if (isDTON.equals("1")) {
            DT.setChecked(true);
        } else {
            DT.setChecked(false);
        }
        DT.setEnabled(true);
        DT.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference arg0) {
                if (DT.isChecked()) {
                    SystemProperties.set(PROP_FAKE_DT, "1");
                } else {
                    SystemProperties.set(PROP_FAKE_DT, "0");
                }

                return false;
            }
        });
        ScreenCat.addPreference(DT);

        PreferenceCategory LogCat = new PreferenceCategory(this);
        LogCat.setTitle(getText(R.string.log_cat));
        PrefScreen.addPreference(LogCat);
        
        Preference Dump = new Preference(this);
        Dump.setTitle(getText(R.string.gen_bug_report));
        Dump.setSummary(getText(R.string.gen_bug_report_summary));
        String isLogCatAllowed = Command.getprop(PROP_ROOTACCESS);
        if (isLogCatAllowed.equals("3")) {
            Dump.setEnabled(true);
        } else {
            Dump.setEnabled(false);
            Dump.setSummary(getText(R.string.rootaccess_required));
        }
        Dump.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference arg0) {
                new GetLogTask().execute();
                return false;
            }
        });
        LogCat.addPreference(Dump);
        
        setPreferenceScreen(PrefScreen);

    }
}
