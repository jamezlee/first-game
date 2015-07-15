package sg.com.kaplan.first_game;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;


public class StartGame extends ActionBarActivity {


    private int count = 0;
    private int life;
    private int secondlife;
    private String userChoice;
    private String comChoice;


    private long timeLeft=4000;
    private CountDownTimer timer;
    private boolean isPlaying = false;
    //private MalibuCountDownTimer countDownTimer;


    SharedPreferences sharedPref;

    TextView timercounter;
    TextView scoretxt;
    ImageView gamestatus;

    ProgressBar UserLife;
    ProgressBar ComLifes;

    ImageView gamerightside;

    ImageView SecondCom;

    AnimationDrawable UserMain;
    AnimationDrawable Enemies1;

    Animation animatefadeout;
    Animation animatefadein;

    MediaPlayer mainBgsound;
    MediaPlayer hitBgsound;
    MediaPlayer Stonesound;
    MediaPlayer scissorsound;
    MediaPlayer papersound;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        getSupportActionBar().hide();
        //this.requestWindowFeature (Window.FEATURE_NO_TITLE);



        animatefadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        animatefadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        ImageView MainUser = (ImageView) findViewById(R.id.maincharacter);
        mainBgsound = MediaPlayer.create(this,R.raw.theother);
        hitBgsound = MediaPlayer.create(this, R.raw.ouch);
        Stonesound = MediaPlayer.create(this, R.raw.stonethrown);
        scissorsound = MediaPlayer.create(this, R.raw.cutting);
        papersound = MediaPlayer.create(this, R.raw.paper);


        UserMain = (AnimationDrawable) MainUser.getBackground();


        scoretxt = (TextView) findViewById(R.id.score);
        timercounter = (TextView) findViewById(R.id.viewtimer);

        UserLife =(ProgressBar) findViewById(R.id.UserLive);
        ComLifes = (ProgressBar) findViewById(R.id.ComLife);

        ComLifes.setRotation(180);

        ImageButton scissorbtn = (ImageButton) findViewById(R.id.MainScissor);
        ImageButton paperbtn = (ImageButton) findViewById(R.id.MainPaper);
        ImageButton rockbtn = (ImageButton) findViewById(R.id.MainStone);
        ImageButton PauseBtn =(ImageButton) findViewById(R.id.btnPause);


        gamestatus = (ImageView) findViewById(R.id.status);
        gamerightside = (ImageView) findViewById(R.id.gameright);

        Gametimer();

        badpeople();
        radchoice();





        isPlaying = true;
        mainBgsound.setLooping(true);
        mainBgsound.start();

        scissorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoice = "scissor";
                play();

            }
        });

        paperbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoice ="paper";
                play();
            }
        });

        rockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoice ="rock";
                play();


            }
        });

        PauseBtn.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(StartGame.this);

                GamePause();

                dialog.setContentView(R.layout.activity_pause_dialog);
                dialog.setTitle("Pause Game");
                dialog.setCancelable(false);
                dialog.show();

                Button closebtn = (Button)dialog.findViewById(R.id.btn_finish_dialog);
                closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                GameResume();

                    }
                });

            }
        });


    }

    private void badpeople(){
        // Generates a random charter.
        Random rad=new Random();
        int rads = rad.nextInt(3);


        SecondCom = (ImageView) findViewById(R.id.comchar);

       switch (rads){
            case 0:
                SecondCom.setBackgroundResource(R.drawable.secchar);
                Enemies1 = (AnimationDrawable) SecondCom.getBackground();
                break;
            case 1:
                SecondCom.setBackgroundResource(R.drawable.bad2char);
                Enemies1 = (AnimationDrawable) SecondCom.getBackground();
                break;
           case 2:
               SecondCom.setBackgroundResource(R.drawable.bad3char);
               Enemies1 = (AnimationDrawable) SecondCom.getBackground();
               break;
        }

    }

    private  void radchoice(){

        // Generates a random play.
        int rand = ((int) (Math.random() * 10)) % 3;

        // Sets the right image according to random selection.
        switch (rand) {
            case 0:
                comChoice = "rock";
                gamerightside.setImageResource(R.drawable.stoneright);
                gamerightside.startAnimation(animatefadein);
                break;
            case 1:
                comChoice = "paper";
                gamerightside.setImageResource(R.drawable.paperright);
                gamerightside.startAnimation(animatefadein);
                break;
            case 2:
                comChoice = "scissor";
                gamerightside.setImageResource(R.drawable.scissorright);
                gamerightside.startAnimation(animatefadein);
                break;
        }



    }


    private void play(){

        if(userChoice==comChoice) {

            gamestatus.setImageResource(R.drawable.drawbanner);
            Reset();

        }
        else if(userChoice == "rock" && comChoice =="scissor")
        {

            gamestatus.setImageResource(R.drawable.winbanner);
            Stonesound.start();
            comlife();
            Reset();
        }
        else if(userChoice == "paper" && comChoice == "rock") {
            gamestatus.setImageResource(R.drawable.winbanner);
            papersound.start();
            comlife();
            Reset();


        }
        else if(userChoice == "scissor" && comChoice =="paper") {
            gamestatus.setImageResource(R.drawable.winbanner);
            scissorsound.start();
            comlife();
            Reset();
        }
        else if(comChoice == "scissor" && userChoice =="paper"){
            scissorsound.start();
            gamestatus.setImageResource(R.drawable.losebanner);
            UserMain.run();
            userlife();
            //Reset();

        }
        else if(comChoice == "paper" && userChoice =="rock") {

            papersound.start();
            gamestatus.setImageResource(R.drawable.losebanner);
            UserMain.run();
            userlife();
        }
        else if(comChoice == "rock" && userChoice =="scissor") {
            Stonesound.start();
            gamestatus.setImageResource(R.drawable.losebanner);
            UserMain.run();
            userlife();
        }


        else {

            UserMain.run();
            userlife();
        }




    }

    public void Reset(){
        comChoice = " ";
        userChoice = " ";
        gamestatus.startAnimation(animatefadeout);
        isPlaying = false;
        timer.cancel();
        timeLeft = 4000;
        Gametimer();

        radchoice();
    }
    public void comlife()
    {
        // setting for computer life
        Enemies1.run();
        ComLifes.incrementProgressBy(-20);
        secondlife=ComLifes.getProgress();
        hitBgsound.start();
        if (secondlife==0){
            hitBgsound.start();
            scoretxt.setText("" + (++count) + "");
            ComLifes.setProgress(100);
          //  countDownTimer.cancel();
           // timer.cancel();
            badpeople();

        }
        else


             Reset();



    }

    public void userlife()
    {

        //setting for user life
        sharedPref = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPref.edit();

        hitBgsound.start();

        UserLife.incrementProgressBy(-20);
        life=UserLife.getProgress();


        if (life == 0){
            //store the score
            editor.putString("HighScore", scoretxt.getText().toString());
            editor.commit();
            gamestatus.setImageResource(R.drawable.gameover);
         //   countDownTimer.cancel();
            timer.cancel();
            Intent intent = new Intent(StartGame.this, GameOver.class);
            startActivity(intent);
            mainBgsound.stop();
            StartGame.this.finish();


        }
        else {

            Reset();

        }



    }




    public void Gametimer(){

        if (isPlaying == false) {
            timer = new CountDownTimer(timeLeft, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft = millisUntilFinished;
                    timercounter.setText(millisUntilFinished / 1000 + "s");
                }

                @Override
                public void onFinish() {
                    isPlaying = false;
                    play();
                }
            };
            timer.start();
            isPlaying = true;
        } else {
            timer.cancel();
            isPlaying = false;
        }



    }
    public void GamePause(){

      //  isPlaying = true;
        timer.cancel();
        mainBgsound.pause();
        Gametimer();

    }
    public void GameResume(){


        Gametimer();
        mainBgsound.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        isPlaying = false;
        Gametimer();
        mainBgsound.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        mainBgsound.pause();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_game, menu);
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
