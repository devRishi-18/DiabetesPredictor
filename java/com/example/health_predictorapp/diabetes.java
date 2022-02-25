package com.example.health_predictorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class diabetes extends AppCompatActivity {

private Button predict,back;
public EditText preg,glucose,BP,SkThick,insulin,bmi,dpf,age;
public TextView result;
String url = "https://diabetes-detector-app.herokuapp.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes);
        predict= findViewById(R.id.predict);
        back=findViewById(R.id.back);
        preg=findViewById(R.id.preg);
        glucose=findViewById(R.id.glucose);
        BP=findViewById(R.id.BP);
        SkThick=findViewById(R.id.SkThick);
        insulin=findViewById(R.id.insulin);
        bmi=findViewById(R.id.bmi);
        dpf=findViewById(R.id.dpf);
        age=findViewById(R.id.age);
        result=findViewById(R.id.result);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(diabetes.this,MainActivity.class);
                startActivity(intent);
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject= new JSONObject(response);
                                    String data = jsonObject.getString("outcome");
                                    if(data.equals("1"))
                                    {
                                        result.setText("You Have Diabetes");
                                    }
                                    else
                                    {
                                        result.setText("You Don't have Diabetes");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(diabetes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params= new HashMap<String,String>();
                        params.put("Pregnancies",preg.getText().toString());
                        params.put("Glucose",glucose.getText().toString());
                        params.put("BloodPressure",BP.getText().toString());
                        params.put("SkinThickness",SkThick.getText().toString());
                        params.put("Insulin",insulin.getText().toString());
                        params.put("BMI",bmi.getText().toString());
                        params.put("DiabetesPedigreeFunction",dpf.getText().toString());
                        params.put("Age",age.getText().toString());
                        return params;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(diabetes.this);
                queue.add(stringRequest);
            }
        });
    }
}