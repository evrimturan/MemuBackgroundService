package com.campaignup.memubgsvc.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class RunAfterBootService extends Service {

    private static final String TAG_BOOT_EXECUTE_SERVICE = "BOOT_BROADCAST_SERVICE";
    private String guid;
    private String time;
    private String androidID;
    private String token = "";
    private final String username = "evrimturan";
    private final String password = "123456";

    public RunAfterBootService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        guid = UUID.randomUUID().toString();
        androidID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService onCreate() method.");
        Log.d(TAG_BOOT_EXECUTE_SERVICE, "AndroidID " + androidID);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String message = "RunAfterBootService onStartCommand() method.";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService onStartCommand() method.");

        /*if(token == null) {
            authenticate();
        }
        else {
            test();
        }*/

        createRequest("test");


        return super.onStartCommand(intent, flags, startId);
    }

    public void test() {
        time = String.valueOf(Calendar.getInstance().getTimeInMillis());

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
        //String url ="http://10.0.2.2:8080/test";
        String url ="http://104.40.132.100:80/test";

        JSONObject JSON = new JSONObject();
        try {
            JSON.put("guid", androidID);
            JSON.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, JSON, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService test() onResponse() method.");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = error.networkResponse.statusCode;
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService test() onErrorResponse() method.");
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService test() onErrorResponse() method Error Netwoek Response Status Code: " + statusCode);
                        if(statusCode == 401) {
                           authenticate();
                        }
                    }
                }) {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);

        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService test() request");
    }

    public void authenticate() {

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://10.0.2.2:8080/authenticate";
        String url ="http://104.40.132.100:80/test";

        JSONObject JSON = new JSONObject();
        try {
            JSON.put("username", "evrimturan");
            JSON.put("password", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, JSON, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService authenticate() onResponse() method.");
                        try {
                            setToken(response.getString("token"));
                            test();
                        }
                        catch (JSONException e) {
                            System.out.println(e.getMessage());
                            System.out.println(e.getStackTrace());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService authenticate() onErrorResponse() method.");

                    }
                }) {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);

        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService authenticate() request");
    }

    public void setToken(String t) {
        token = "Bearer " + t;
    }

    public void createRequest(final String path) {
        time = String.valueOf(Calendar.getInstance().getTimeInMillis());

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://10.0.2.2:8080/" + path;
        String url ="http://104.40.132.100:80/" + path;

        JSONObject JSON = new JSONObject();
        try {
            if(path.equals("authenticate")) {
                JSON.put("username", username);
                JSON.put("password", password);
            }
            else if(path.equals("test")) {
                JSON.put("guid", androidID);
                JSON.put("time", time);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, JSON, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService createRequest() onResponse() method. " + path);
                        if(path.equals("authenticate")) {
                            try {
                                setToken(response.getString("token"));
                                Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService createRequest() onResponse() method Token: " + token);
                                createRequest("test");

                            }
                            catch (JSONException e) {
                                System.out.println(e.getMessage());
                                System.out.println(e.getStackTrace());
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService createRequest() onErrorResponse() method. " + path);
                        if(path.equals("test")) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService test() createRequest() onError() method Error Network Response Status Code: " + statusCode);
                            if(statusCode == 401) {
                                createRequest("authenticate");
                            }
                        }
                    }
                }) {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                if(path.equals("test")) {
                    headers.put("Authorization", token);
                }
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
        Log.d(TAG_BOOT_EXECUTE_SERVICE, "RunAfterBootService createRequest() finished " + path);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
