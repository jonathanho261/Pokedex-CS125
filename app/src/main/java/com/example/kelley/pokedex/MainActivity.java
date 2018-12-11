package com.example.kelley.pokedex;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONArray;
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
                String searchText = editText.getText().toString().toLowerCase();
                if (searchText.trim().length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter something.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startAPICall(searchText);
                }
            }
        });

        button_lucky.setOnClickListener(new View.OnClickListener() {
            // If we use lucky, we should generate a random number and use a search by number function
            @Override
            public void onClick(View v) {
                int randomPokemon = (int) (Math.random()* 721 + 1);
                startAPICall(randomPokemon + "");
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
                            Intent search = new Intent(getBaseContext(), PokemonActivity.class);
                            Log.d("this is okay", response.toString());
                            search.putExtra("info", response.toString());
                            startActivity(search);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    if (str.equalsIgnoreCase("geoff") || str.equalsIgnoreCase("challen") || str.equalsIgnoreCase("geoffrey")) {
                        try {
                            JSONArray types = new JSONArray();
                            JSONObject normal = new JSONObject();
                            normal.put("type", new JSONObject()
                                    .put("name", "normal"));
                            JSONObject psychic = new JSONObject();
                            psychic.put("type", new JSONObject()
                                    .put("name", "psychic"));
                            types.put(normal);
                            types.put(psychic);
                            JSONArray stats = new JSONArray();
                            JSONObject speed = new JSONObject();
                            speed.put("base_stat", 119)
                                    .put("stat", new JSONObject()
                                            .put("name", "speed"));
                            JSONObject sd = new JSONObject();
                            sd.put("base_stat", 119)
                                    .put("stat", new JSONObject()
                                            .put("name", "special-defense"));
                            JSONObject sa = new JSONObject();
                            sa.put("base_stat", 119)
                                    .put("stat", new JSONObject()
                                            .put("name", "special-attack"));
                            JSONObject defense = new JSONObject();
                            defense.put("base_stat", 119)
                                    .put("stat", new JSONObject()
                                            .put("name", "defense"));
                            JSONObject attack = new JSONObject();
                            attack.put("base_stat", 119)
                                    .put("stat", new JSONObject()
                                            .put("name", "attack"));
                            JSONObject hp = new JSONObject();
                            hp.put("base_stat", 119)
                                    .put("stat", new JSONObject()
                                            .put("name", "hp"));
                            stats.put(speed);
                            stats.put(sd);
                            stats.put(sa);
                            stats.put(defense);
                            stats.put(attack);
                            stats.put(hp);
                            JSONObject challenInfo = new JSONObject()
                                    .put("id", "125")
                                    .put("name", "Geoffrey Challen")
                                    .put("height", "Tall")
                                    .put("weight", "???")
                                    .put("types", types)
                                    .put("stats", stats);
                            Intent search = new Intent(getBaseContext(), PokemonActivity.class);
                            Log.d("this is okay", challenInfo.toString());
                            search.putExtra("info", challenInfo.toString());
                            startActivity(search);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("POKEMON DOES NOT EXIST");
                        alertDialog.setMessage("Please try again. '" + str + "' cannot be found. You may want to check your spelling.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
