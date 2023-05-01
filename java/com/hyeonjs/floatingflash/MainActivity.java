package com.hyeonjs.floatingflash;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        Button flash = new Button(this);
        flash.setText("손전등 버튼 생성/삭제");
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        layout.addView(flash);
        Button git = new Button(this);
        git.setText("개발자 홈페이지");
        git.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://hyeonjs.com/");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });
        layout.addView(git);
        int pad = dip2px(16);
        layout.setPadding(pad, pad, pad, pad);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);
    }

    private int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

}