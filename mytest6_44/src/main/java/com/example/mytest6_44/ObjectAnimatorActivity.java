package com.example.mytest6_44;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/6/29.
 */
public class ObjectAnimatorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        LinearLayout container = (LinearLayout)findViewById(R.id.container);
        container.addView(new MyAnimationView(this));
    }
    public class MyAnimationView extends View{
        public MyAnimationView(Context context){
            super(context);
            ObjectAnimator colorAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(ObjectAnimatorActivity.this,R.animator.color_anim);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setTarget(this);
            colorAnim.start();

        }
    }
}
