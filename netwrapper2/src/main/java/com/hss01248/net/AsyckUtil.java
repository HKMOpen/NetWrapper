package com.hss01248.net;

import com.litesuits.android.async.AsyncTask;
import com.litesuits.android.async.Log;

/**
 * Created by Administrator on 2016/9/20.
 */
public class AsyckUtil {

    public static void doAsyc(){
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Log.i("One AsyncTask execute ");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        task.execute();
    }
}
