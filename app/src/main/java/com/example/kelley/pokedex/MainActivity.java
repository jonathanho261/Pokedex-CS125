package com.example.kelley.pokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_search = findViewById(R.id.button_search);
        Button button_lucky = findViewById(R.id.button_lucky);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When we have the info from the API, pass it to the next activity
                // If it's null, pop up a toast that says that there is nothing there?
                // If we implement search by pokemon or number, have to tell whether the text is a string or an int
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
}
