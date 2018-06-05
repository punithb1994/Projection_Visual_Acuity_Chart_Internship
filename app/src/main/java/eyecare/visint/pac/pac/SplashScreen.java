package eyecare.visint.pac.pac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class SplashScreen extends AppCompatActivity
{
    private TextView hTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        hTextView = (TextView) findViewById(R.id.text2);
        StartAnimations();
        hTextView.setEnabled(false);
        Thread t = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(1950);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                } finally
                {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        t.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }

    private void StartAnimations()
    {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);
        l2.clearAnimation();
        l2.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        hTextView.clearAnimation();
        hTextView.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        LinearLayout l3 = (LinearLayout) findViewById(R.id.l3);
        l3.setVisibility(View.VISIBLE);
        l3.clearAnimation();
        l3.startAnimation(anim);
    }
}