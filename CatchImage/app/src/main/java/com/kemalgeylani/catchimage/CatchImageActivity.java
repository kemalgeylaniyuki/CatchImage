package com.kemalgeylani.catchimage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kemalgeylani.catchimage.databinding.ActivityCatchImageBinding;

import java.util.Random;

public class CatchImageActivity extends AppCompatActivity {

    private ActivityCatchImageBinding binding;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;
    long gamerScore;
    AlertDialog.Builder alert;
    SQLiteDatabase sqLiteDatabase;
    String gamerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatchImageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //initialize

        imageArray = new ImageView[] {binding.imageView,binding.imageView2,binding.imageView3,binding.imageView4,binding.imageView5,binding.imageView6,binding.imageView7,binding.imageView8,binding.imageView9};

        hideImages();

        Intent intentString = getIntent();
        gamerName = intentString.getStringExtra("gamerName");

        gamerScore = 0;

        sqLiteDatabase = this.openOrCreateDatabase("Score",MODE_PRIVATE,null);

        new CountDownTimer(10000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                binding.timeText.setText("Time: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                binding.timeText.setText("Time Off");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                alert = new AlertDialog.Builder(CatchImageActivity.this);

                alert.setTitle("Restart?");
                alert.setMessage("Are you sure to restart game?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //restart

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CatchImageActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();

                        try {

                            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS score(id INTEGER PRIMARY KEY, gamerName VARCHAR, gamerScore INTEGER)");
                            String sqlString = "INSERT INTO score(gamerName,gamerScore) VALUES(?,?)";
                            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sqlString);
                            sqLiteStatement.bindString(1,gamerName);
                            sqLiteStatement.bindLong(2,gamerScore);
                            sqLiteStatement.execute();

                        } catch (Exception e){
                            e.printStackTrace();
                        }

                        Intent intentToMainActivity = new Intent(CatchImageActivity.this,MainActivity.class);
                        intentToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentToMainActivity);

                    }
                });

                alert.show();

            }
        }.start();

    }

    public void increaseScore (View view) {

        gamerScore++;
        //gamerScore = gamerScore + 1;

        binding.scoreText.setText("Score: " + gamerScore);


    }

    public void hideImages() {

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {

                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,500);

            }
        };

        handler.post(runnable);
    }


}