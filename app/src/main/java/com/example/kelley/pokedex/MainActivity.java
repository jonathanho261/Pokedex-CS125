package com.example.kelley.pokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "Pokedex-TAG";

    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    /** String to store api information*/
    private String apiCall = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        Button button_search = findViewById(R.id.button_search);
        Button button_lucky = findViewById(R.id.button_lucky);
        final EditText editText = findViewById(R.id.editText);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When we have the info from the API, pass it to the next activity
                // If it's null, pop up a toast that says that there is nothing there?
                // If we implement search by pokemon or number, have to tell whether the text is a string or an int
                String searchText = editText.getText().toString();
                startAPICall(searchText);
                Log.d(TAG, apiCall);
                Intent search = new Intent(v.getContext(), PokemonActivity.class);
                startActivity(search);
            }
        });

        button_lucky.setOnClickListener(new View.OnClickListener() {
            // If we use lucky, we should generate a random number and use a search by number function
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "This feature hasn't been implemented yet!",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });

    }

    void startAPICall(final String str) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://pokeapi.co/api/v2/pokemon/" + str + "/",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            apiCall = response.toString();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
