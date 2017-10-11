package teammint.classroster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.R.attr.delay;

public class Main2Activity extends AppCompatActivity {
    private ImageView object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        object = (ImageView) findViewById(R.id.imageView);
        Animation FadeAnim= AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.slide_in_left);
        FadeAnim.setDuration(2000);
        object.startAnimation(FadeAnim);
        FadeAnim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                onOut();
            }
        });
    }
    public void onOut(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}


