package sg.com.kaplan.first_game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class PauseDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pause_dialog);

       // Button closebtn = (Button) findViewById(R.id.btn_finish_dialog);


//        closebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    PauseDialog.this.finish();
//
//            }
//        });
    }


}
