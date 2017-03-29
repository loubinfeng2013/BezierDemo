package com.loubinfeng.www.bezierdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handle2Bezier(View view) {
        startActivity(new Intent(this, Bezier2Activity.class));
    }

    public void handle3Bezier(View view) {
        startActivity(new Intent(this, Bezier3Activity.class));
    }

    public void handleDrawPad(View view) {
        startActivity(new Intent(this, DrawPadActivity.class));
    }

    public void handlePathMorph(View view){
        startActivity(new Intent(this,PathMorphActivity.class));
    }
}
