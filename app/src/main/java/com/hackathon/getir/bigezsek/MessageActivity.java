package com.hackathon.getir.bigezsek;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by taha on 25/03/17.
 */

public class MessageActivity extends Activity{
    ListView mesList;
    EditText mesText;
    Button sendBut;
    final MessageActivity m = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        mesList = (ListView) findViewById(R.id.mesList);
        mesText = (EditText) findViewById(R.id.mesText);
        sendBut = (Button) findViewById(R.id.sendBut);

        final String otherId = getIntent().getExtras().getString("otherId");
        Log.d("otherId",otherId);
        final String myId = "1";


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://bigezsek-backend.herokuapp.com/getMessagesbyUser?from="+myId+"&to="+otherId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        JSONArray array = null;
                        final ArrayList<Message> messages= new ArrayList<Message>();
                        try {
                            array = new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                Message message = gson.fromJson(array.getString(i), Message.class);
                                messages.add(message);
                                Log.d("message",message.from);
                            }
                            if(array.length()==0){
                                Toast.makeText(getApplicationContext(),"There is no message! Send one!",Toast.LENGTH_LONG).show();
                            }
                            else{
                                ChatAdapter c= new ChatAdapter(m,messages);
                                mesList.setAdapter(c);
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






        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mesText.getText().toString();
                mesText.setText(null);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                try {
                    message = URLEncoder.encode(message, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url ="http://bigezsek-backend.herokuapp.com/sendMessage?from=1&to="+otherId+"&message="+ message;
                Log.d("message",url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });


                queue.add(stringRequest);
            }
        });

    }
}
