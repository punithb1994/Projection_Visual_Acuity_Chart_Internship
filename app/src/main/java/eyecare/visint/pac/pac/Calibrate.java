package eyecare.visint.pac.pac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Calibrate extends AppCompatActivity
{
    private Button btnnext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calibration_plus);
        btnnext = (Button) findViewById(R.id.bnext);
        btnnext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Calibration.class);
                startActivity(intent);
                finish();
            }
        });
    }
}