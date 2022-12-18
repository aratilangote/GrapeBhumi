package com.capstone.grapediseaseapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class BottomFragmentWeather extends Fragment {

    EditText edInput;
    TextView tvResult, tvResult0, tvResult1, tvResult2, link;


    public BottomFragmentWeather() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_weather, null);

        // Init
        edInput = view.findViewById(R.id.E2);
        tvResult = view.findViewById(R.id.t1);
        tvResult1 = view.findViewById(R.id.t2);
        tvResult2 = view.findViewById(R.id.t3);
        tvResult0 = view.findViewById(R.id.t11);
        link = view.findViewById(R.id.txtLink);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://downy-mildew-prediction.herokuapp.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        edInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(getContext(), "HIT", Toast.LENGTH_SHORT).show();
                String appId = "ed7e5b273e791728dfd58ae78e2a9044";
                String city = edInput.getText().toString().trim();

                RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
                @SuppressLint("SetTextI18n") JsonObjectRequest request =
                        new JsonObjectRequest("https://api.openweathermap.org/data/2.5/weather?q=" + city +
                                "&appid=" + appId, response -> {
                            try {
                                StringBuilder data = new StringBuilder();
                                StringBuilder data1 = new StringBuilder();
                                StringBuilder data2 = new StringBuilder();
                                StringBuilder data3 = new StringBuilder();
                                JSONObject object = response.getJSONObject("main");
                                JSONObject object1 = response.getJSONObject("wind");
                                JSONObject object2 = response.getJSONObject("clouds");
                                object.keys().forEachRemaining(key -> {
                                    try {
                                        Object value = object.get(key);
                                        if (key.equals("temp_min") || key.equals("temp_max") || key.equals("temp") || key.equals("feels_like")) {
                                            int s = (int) ((double) value - 273.15);
                                            data.append("\n").append(key).append(" : ").append(s).append(" °C");
                                        } else
                                            data.append("\n").append(key).append(" : ").append(value);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                });
                                object1.keys().forEachRemaining(key -> {
                                    try {
                                        Object value = object1.get(key);
                                        data1.append("\n").append(key).append(" : ").append(value);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                });
                                object2.keys().forEachRemaining(key -> {
                                    try {
                                        Object value = object2.get(key);
                                        data2.append("\n").append(value).append("%").append(" Cloud ");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                });
                                Log.d("-------->", data + "\n" + data1 + "\n" + data2);
                                tvResult.setText("No Data Found");
                                tvResult1.setText(data1.toString());
                                tvResult.setText(data.toString());
                                tvResult2.setText(data2.toString());
                                tvResult0.setText(data3.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> tvResult.setText("No Data Found"));
                requestQueue.add(request);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

}