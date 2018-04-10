package com.example.ahmed.backendexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class POST_AND_GET extends AppCompatActivity {
    RequestQueue requestQueue ;
    final  String url = "http://192.168.1.2/api1/users/";
    EditText editText1 ,editText2 ;
    RecyclerView recyclerView ;
    UserRecyclerAdapter adapter ;
    ArrayList<User> userArrayList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__and__get);
        requestQueue = Volley.newRequestQueue(POST_AND_GET.this);

        userArrayList = new ArrayList<>() ;

        editText1 = (EditText) findViewById(R.id.firstNameEditText) ;
        editText2 = (EditText) findViewById(R.id.lastNameEditText) ;

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView) ;
        adapter = new UserRecyclerAdapter(userArrayList) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(POST_AND_GET.this) ;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);


    }
    public void addUsers(View v){
        final String firstName , lastName ;
        firstName = editText1.getText().toString().trim() ;
        lastName = editText2.getText().toString().trim() ;
        if(firstName.isEmpty()||lastName.isEmpty()) return;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("POST_Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;
                            String status_message = jsonObject.getString("status_message") ;
                            if(status_message.equals("ok")){
                                Toast.makeText(POST_AND_GET.this,"User Added Successfully",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(POST_AND_GET.this,"Error Adding User",Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(POST_AND_GET.this,"Error Adding User",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                        Toast.makeText(POST_AND_GET.this,"Connection Error",Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("firstName", firstName);
                params.put("lastName", lastName);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }



    public void refreshUsers(View v){

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("POST_Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;
                            JSONArray jsonArray = jsonObject.getJSONArray("data") ;
                            for(int i=0 ;i<jsonArray.length() ;i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String firstName = object.getString("firstName") ;
                                String lastName = object.getString("lastName") ;
                                String id = object.getString("id") ;
                                User user = new User(firstName,lastName,id) ;
                                userArrayList.add(user);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(POST_AND_GET.this,"Error Getting User",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                        Toast.makeText(POST_AND_GET.this,"Connection Error",Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(postRequest);
    }
}
