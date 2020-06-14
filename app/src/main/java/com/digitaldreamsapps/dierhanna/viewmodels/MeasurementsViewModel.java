package com.digitaldreamsapps.dierhanna.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Config;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitaldreamsapps.dierhanna.models.Measurements;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MeasurementsViewModel extends AndroidViewModel {
    private JsonLiveData postsList;
    public MeasurementsViewModel(@NonNull Application application) {
        super(application);
        if(postsList==null)
            postsList=new JsonLiveData(application);
    }
    public MutableLiveData<List<Measurements>> getPostsList() {
        return  postsList;
    }
}
class JsonLiveData extends MutableLiveData<List<Measurements>> {
    private List<Measurements> measurements=new ArrayList<Measurements>();
    private final Context context;
    private int page=1;

    public JsonLiveData(Context context){
        this.context=context;
        LoadData();
    }
    private void LoadData() {

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, "https://api.weather.com/v2/pws/observations/current?stationId=IDEIRH2&format=json&units=m&apiKey=1286985dc2ef494f86985dc2efe94fcd", null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson Gson = gsonBuilder.create();
                        Log.d("response ", response.toString() + "Size: "+response.length());

                        try {
                            JSONArray jsonArray =response.getJSONArray("observations");

                            for (int i= 0 ; i < jsonArray.length() ; i ++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                Measurements measurement = Gson.fromJson(jsonObject.toString(),Measurements.class);
                                measurements.add(measurement);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        setValue(measurements);
                    }


                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                }
        ) ;
        requestQueue.add(getRequest);
    }

}