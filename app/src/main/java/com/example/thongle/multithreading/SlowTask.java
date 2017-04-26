package com.example.thongle.multithreading;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by thongle on 26/04/2017.
 */

public class SlowTask extends AsyncTask<Void, Long, Void> {
    private ProgressDialog progressDialog_waiting;
    private Long starttime;
    private Context context;
    private TextView textView_status;

    public SlowTask(Context m_context, TextView txtv_status){
        this.context = m_context;
        this.textView_status = txtv_status;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog_waiting = new ProgressDialog(context);
        starttime = System.currentTimeMillis();
        textView_status.setText("Start time: " + starttime);
        progressDialog_waiting.setMessage(context.getString(R.string.pleasw_wait));
        progressDialog_waiting.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            for(Long i = 0L; i < 3L; i++){
                Thread.sleep(2000);
                publishProgress(i);
            }

        } catch (Exception e){}
        return null;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        textView_status.append("\nWorking..." + values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(progressDialog_waiting.isShowing())
            progressDialog_waiting.dismiss();
        textView_status.setText("\nEnd Time" + System.currentTimeMillis());
        textView_status.append("\nDone");
    }
}
