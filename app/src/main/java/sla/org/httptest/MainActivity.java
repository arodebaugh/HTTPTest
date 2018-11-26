package sla.org.httptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.*;

public class MainActivity extends AppCompatActivity {
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView getAPI = findViewById(R.id.getAPI);

        controller = new Controller(getAPI);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.pi.delivery/v1/pi?numberOfDigits=25";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject obj = new JSONObject(response);
                            String content = obj.getString("content");
                            getAPI.setText("Response is: " + content);
                        } catch (JSONException error) {
                            getAPI.setText("That didn't work!");
                            error.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getAPI.setText("That didn't work!");
                error.printStackTrace();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
