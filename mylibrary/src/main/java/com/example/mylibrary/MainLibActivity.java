package com.example.mylibrary;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ninetripods.sydialoglib.IDialog;
import com.ninetripods.sydialoglib.SYDialog;

public class MainLibActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlib);
        Button bt_dialog=findViewById(R.id.bt_dialog);
        bt_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SYDialog.Builder(MainLibActivity.this)
                        .setCancelableOutSide(true)
                        .setCancelable(true)
                        .setGravity(Gravity.CENTER)
                        .setWindowBackgroundP(0.5f)
                        .setScreenWidthP(1f)
                        .setDialogView(R.layout.dialog_test)
                        .setAnimStyle(R.style.PracticeModeAnimation)
                        .setBuildChildListener(new IDialog.OnBuildListener() {
                            @Override
                            public void onBuildChildView(IDialog dialog, View view, int layoutRes) {
                                TextView tv_close=view.findViewById(R.id.tv_close);
                                tv_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                            }

                        }).show();
            }
        });
    }
}