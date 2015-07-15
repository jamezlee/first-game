package sg.com.kaplan.first_game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;


public class GameOver extends ActionBarActivity {


    //private static final int PREFERENCE_MODE_PRIVATE = 0;
    SharedPreferences sharedPref;
    Animation zoomin;

    //String Scores;
    TextView High_scores;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        getSupportActionBar().hide();


        ImageButton Mainmenu = (ImageButton) findViewById(R.id.Mainbutton);
        ImageButton replaygame = (ImageButton) findViewById(R.id.Replay);
        zoomin = AnimationUtils.loadAnimation(this, R.anim.playjumpbtn);

        replaygame.startAnimation(zoomin);
        Mainmenu.startAnimation(zoomin);

        //Retrieve Score
        High_scores = (TextView) findViewById(R.id.HighScore);
        sharedPref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        High_scores.setText(" " + sharedPref.getString("HighScore", " 0 ") + "");

        replaygame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this,StartGame.class);
                startActivity(intent);
                GameOver.this.finish();


            }
        });


        Mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GameOver.this,MainActivity.class);
                startActivity(intent);
                GameOver.this.finish();

            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
