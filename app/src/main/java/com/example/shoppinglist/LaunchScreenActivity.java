package com.example.shoppinglist;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);


        //Route:ar från Launchscreen till MainActivity-screen efter en delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LaunchScreenActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }//Visar launch-vyn i 2.5 sekunder
        },2500);

    }
}
