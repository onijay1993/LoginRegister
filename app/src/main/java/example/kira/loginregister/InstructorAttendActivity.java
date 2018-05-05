package example.kira.loginregister;

import android.widget.AdapterView;
import android.widget.GridLayout.LayoutParams;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InstructorAttendActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String verificationCode = "xxaabb";
    Button back;
    Button current;
    String cID = "";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        back = findViewById(R.id.back);
        current = findViewById(R.id.Current);

        final Spinner spDate = findViewById(R.id.spDate);
        final TextView attend = findViewById(R.id.attend);
        Intent intent = getIntent();
        cID = intent.getStringExtra("cIDa");

        getDateBack();
        ArrayAdapter<String> ada=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,attendList);
        spDate.setAdapter(ada);
        spDate.setOnItemSelectedListener(this);


        current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                JSONObject jsonResponse = new JSONObject(json.getString("response"));
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    JSONObject jsonAID = new JSONObject(json.getString("aID"));
                                    convertJSONObjectToArrary(jsonAID);
                                    attend.setText("");
                                    for (int j = attendList.size() - 1; j >= 0; j--) {
                                        attend.append(attendList.get(j) + "\n");
                                    }
                                    System.out.print(attend);
                                    getDateBack();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(response);
                            }
                        }
                    };
                    InstructorAttendRequest instructorAttendRequest = new InstructorAttendRequest(cID, verificationCode, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(InstructorAttendActivity.this);
                    queue.add(instructorAttendRequest);
                }
            });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject jsonResponse = new JSONObject(json.getString("response"));
                            boolean success = jsonResponse.getBoolean("success");
                            date = "2018-04-16";
                            if (success) {
                                JSONObject jsonAID = new JSONObject(json.getString("aID"));
                                convertJSONObjectToArrary(jsonAID);
                                attend.setText("");
                                for (int j = attendList.size() - 1; j >= 0; j--) {
                                    attend.append(attendList.get(j) + "\n");
                                }
                                System.out.print(attend);
                                getDateBack();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(response);
                        }
                    }
                };
                InstructorGetSpRequest instructorGetSpRequest = new InstructorGetSpRequest(cID, date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(InstructorAttendActivity.this);
                queue.add(instructorGetSpRequest);
            }
        });
        }
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView myText=(TextView) view;
                System.out.println(i);
                date = myText.toString();
                Toast.makeText(adapterView.getContext(),myText.getText(), Toast.LENGTH_SHORT);

        }
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
        protected void convertJSONObjectToArrary (JSONObject aID){
            Iterator x = aID.keys();
            attendList.clear();
            try {
                while (x.hasNext()) {
                    String key = (String) x.next();
                    attendList.add(aID.get(key).toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        protected void getDateBack(){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONObject jsonResponse = new JSONObject(json.getString("response"));
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            JSONObject jsonAID = new JSONObject(json.getString("aID"));
                            convertJSONObjectToArrary(jsonAID);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(response);
                    }
                }
            };
            InstructorSpAttendRequest instructorSpAttendRequest = new InstructorSpAttendRequest(cID, responseListener);
            RequestQueue queue = Volley.newRequestQueue(InstructorAttendActivity.this);
            queue.add(instructorSpAttendRequest);
        }


        final ArrayList<String> attendList = new ArrayList<>();
        String date = "";
}

