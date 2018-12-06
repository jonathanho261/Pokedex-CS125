package com.example.kelley.pokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PokemonActivity extends AppCompatActivity {

    // TODO: add layout
    // TODO: Change title of page to name of pokemon?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        TextView name = findViewById(R.id.weight);


        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        try {
            JSONObject infoJSON = new JSONObject(info);
            name.setText(Double.toString(infoJSON.getInt("weight") * 0.1) + " kg");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        setTitle("Butterfree");
    }
}
