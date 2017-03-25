package com.hackathon.getir.bigezsek;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taha on 25/03/17.
 */

public class Utils {

    private static final String TAG = "Utils";

    public static void loadProfiles(final Context context,final SwipePlaceHolderView mSwipeView){
        try{

            RequestQueue queue = Volley.newRequestQueue(context);
            String url ="http://bigezsek-backend.herokuapp.com/getUsers?place=ChIJuQfSbQbQyhQRo686317fLx4&date=26.3.2017&hour=aksam&id=2";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            JSONArray array = null;
                            try {
                                array = new JSONArray(response);
                                for(int i=0;i<array.length();i++){
                                    Profile profile = gson.fromJson(array.getString(i), Profile.class);
                                    mSwipeView.addView(new Card(context, profile, mSwipeView));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            queue.add(stringRequest);

        }catch (Exception e){
        }
    }

    private static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        InputStream is=null;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG,"path "+jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}