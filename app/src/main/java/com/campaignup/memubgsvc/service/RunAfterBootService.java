package com.campaignup.memubgsvc.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RunAfterBootService extends Service {

    private static final String TAG_BOOT_EXECUTE_SERVICE = "BOOT_BROADCAST_SERVICE";

    public RunAfterBootService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService onCreate() method.");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String message = "RunAfterBootService onStartCommand() method.";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService onStartCommand() method.");


        /*
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8080/test";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService onResponse() method.");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService onErrorResponse() method.");
            }
        }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put("guid", "386"); //Add the data you'd like to send to the server.
                    MyData.put("time", "4567898765");
                    return MyData;
                }
            };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);*/

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8080/test";

        JSONObject JSON = new JSONObject();
        try {
            JSON.put("guid", "4313");
            JSON.put("time", "5555555555");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, JSON, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService onResponse() method.");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService onErrorResponse() method.");

                    }
                });
        queue.add(jsonObjectRequest);

        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService request");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
