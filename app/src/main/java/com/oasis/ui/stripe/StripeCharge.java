package com.oasis.ui.stripe;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class StripeCharge extends AsyncTask<String, Void, String> {
    String token;

    public StripeCharge(String token) {
        this.token = token;
    }

    @Override
    protected String doInBackground(String... params) {
        new Thread() {
            @Override
            public void run() {
                postData("name",token,""+"1");
            }
        }.start();
        return "Done";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("Result",s);
    }

    public void postData(String description, String token,String amount) {
        // Create a new HttpClient and Post Header
        try {
            URL url = new URL("[YOUR_SERVER_CHARGE_SCRIPT_URL]");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new NameValuePair("method", "charge"));
//            params.add(new NameValuePair("description", description));
//            params.add(new NameValuePair("source", token));
//            params.add(new NameValuePair("amount", amount));
//
//            OutputStream os = null;
//
//            os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getQuery(params));
//            writer.flush();
//            writer.close();
         //   os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}