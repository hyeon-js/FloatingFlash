package com.hyeonjs.floatingflash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView btn;
    private boolean isFlashOn = false;

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
                if (btn == null) {
                    createButton();
                } else {
                    WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                    if (btn != null) wm.removeView(btn);
                    btn = null;
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

    private void createButton() {
        try {
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            if (btn != null) wm.removeView(btn);

            final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    dip2px(48), dip2px(48),
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
            if (Build.VERSION.SDK_INT >= 26) {
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY; //2038
            }
            btn = new TextView(this);
            btn.setBackgroundResource(R.mipmap.ic_launcher);
            btn.setOnClickListener(v -> {
                try {
                    isFlashOn = !isFlashOn;
                    CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    cm.setTorchMode(cm.getCameraIdList()[0], isFlashOn);
                    if (isFlashOn) btn.setBackgroundResource(R.mipmap.flash_on);
                    else btn.setBackgroundResource(R.mipmap.ic_launcher);
                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            final boolean[] longClick = {false};
            btn.setOnTouchListener((v, ev) -> {
                if (longClick[0]) {
                    switch (ev.getAction()) {
                        case MotionEvent.ACTION_UP:
                            longClick[0] = false;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            params.x = (int) ev.getRawX() - dip2px(24);
                            params.y = (int) ev.getRawY() - dip2px(24);
                            params.gravity = Gravity.LEFT | Gravity.TOP;
                            wm.updateViewLayout(btn, params);
                            break;
                    }
                }
                return false;
            });
            btn.setOnLongClickListener(view -> {
                if (!longClick[0]) longClick[0] = true;
                return true;
            });
            wm.addView(btn, params);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

}