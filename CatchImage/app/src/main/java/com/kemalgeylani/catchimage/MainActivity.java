package com.kemalgeylani.catchimage;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.kemalgeylani.catchimage.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String gamerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void start(View view){

        gamerName = binding.gamerName.getText().toString();

        Intent intent = new Intent(MainActivity.this,CatchImageActivity.class);
        intent.putExtra("gamerName",gamerName);
        startActivity(intent);

    }

    public void scoreList(View view){

        Intent intentToScore = new Intent(MainActivity.this,ScoreTableActivity.class);
        intentToScore.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToScore);

    }
}