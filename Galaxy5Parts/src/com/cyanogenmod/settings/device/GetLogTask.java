package com.cyanogenmod.settings.device;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.Preference;
import android.widget.Toast;
import com.cyanogenmod.settings.device.R;

final class GetLogTask implements Preference.OnPreferenceClickListener {

	Context mContext;

	GetLogTask(Context context) {
		mContext = context;
	}

	public final boolean onPreferenceClick(final Preference preference) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(mContext.getText(R.string.bug_report_gen));
		dialog.setMessage(mContext.getText(R.string.bug_report_dialog));
		dialog.setPositiveButton(mContext.getText(R.string.continew), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				generateLog.execute();
			}
		});
		dialog.setNegativeButton(mContext.getText(R.string.cancel), null);
		dialog.show();

		return false;

	}

	private AsyncTask<String, Integer, String> generateLog = new AsyncTask<String, Integer, String>() {
		
		ProgressDialog mProgressDialog;

		@Override
		protected void onPreExecute() {
			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setMessage(mContext.getText(R.string.generating_bug_report));
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				return mContext.getText(R.string.sd_not_mounted).toString();
			}

			try {

				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec("su");
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						process.getOutputStream());
				
				String date = new SimpleDateFormat("yyyyMMdd-HH.mm.ss")
						.format(Calendar.getInstance().getTime());

				String dumpFileName = "bugreport_" + date + ".log";

				String dumpFilePath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/" + dumpFileName;
				
				outputStreamWriter.write("dumpstate > " + dumpFilePath + "\n");

				outputStreamWriter.write("bzip2 -9 " + dumpFilePath + "\n");

				outputStreamWriter.flush();
				outputStreamWriter.close();
				process.waitFor();
				process.destroy();

				return mContext.getText(R.string.bug_report_success)
						+ " SD card > " + dumpFileName + ".bz2";

			} catch (Exception e) {
				return mContext.getText(R.string.bug_report_failed).toString();
			}
		}

		@Override
		protected void onPostExecute(String result) {

			mProgressDialog.cancel();

			Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();

		}
	};

}
