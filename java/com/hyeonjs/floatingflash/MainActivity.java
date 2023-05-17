package com.hyeonjs.floatingflash;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView btn;

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
                if (btn != null) {

                } else {

                }
            }
        });
        layout.addView(flash);
        Button per = new Button(this);
        per.setText("다른 앱 위에 그리기 권한 설정");
        per.setOnClickListener(view -> {
            Uri uri = Uri.parse("package:" + getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri);
            startActivityForResult(intent, 5469);
        });
        layout.addView(per);
        Button git = new Button(this);
        git.setText("개발자 홈페이지");
        git.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://hyeonjs.com/");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });
        layout.addView(git);
        TextView cp = new TextView(this);
        cp.setText("© 2023 Hyeon.js, All rights reserved.");
        cp.setTextSize(13);
        cp.setGravity(Gravity.CENTER);
        int pad = dip2px(8);
        cp.setPadding(pad, pad, pad, pad);
        layout.addView(cp);
        pad = dip2px(16);
        layout.setPadding(pad, pad, pad, pad);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);
    }



    private int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

}