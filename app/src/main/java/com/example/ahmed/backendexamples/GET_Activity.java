package com.example.ahmed.backendexamples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GET_Activity extends AppCompatActivity {
    String url = "http://192.168.1.2/api1/sum/" ;
    TextView textView ;
    RequestQueue requestQueue ;
    EditText e1 ,e2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_);
        textView = (TextView) findViewById(R.id.textView);
        e1 = (EditText) findViewById(R.id.editText1);
        e2 = (EditText) findViewById(R.id.editText2);

        requestQueue = Volley.newRequestQueue(GET_Activity.this) ;
    }
    public void goToPostActivity(View v){
        Intent intent = new Intent(GET_Activity.this,POST_AND_GET.class) ;
        startActivity(intent);
    }
    public void callSum(View view){
        String n1 = e1.getText().toString() ;
        String n2 = e2.getText().toString() ;
        url+="?n1="+n1+"&n2="+n2 ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response) ;
                    int x = jsonObject.getInt("data") ;
                    textView.setText(String.valueOf(x));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;
        requestQueue.add(stringRequest) ;
    }

}
