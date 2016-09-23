package com.example.orange.sickoorange_customview;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button Success;
    private Button Fail;
    private SuccessView successView;
    private View myView;
    private AlertDialog dialog;
    private AlertDialog loadingDialog;
    private Button loadingview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = this.getLayoutInflater().inflate(R.layout.success_dialog, null);
        successView = (SuccessView) myView.findViewById(R.id.loadingView);
        dialog = new AlertDialog.Builder(this).setView(myView).
                setNegativeButton("OK", null).create();
        loadingDialog = new AlertDialog.Builder(this).setView(this.getLayoutInflater().
                inflate(R.layout.loadingview, null)).setNegativeButton("OK", null).create();

        Success = (Button) findViewById(R.id.success);
        Fail = (Button) findViewById(R.id.fail);
        loadingview = (Button) findViewById(R.id.myLoading);
        Success.setOnClickListener(this);
        Fail.setOnClickListener(this);
        loadingview.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.success:
                successView.setSucceed(true);
                successView.setStart(true);
                dialog.show();
                break;
            case R.id.fail:

                successView.setSucceed(false);
                successView.setStart(true);
                dialog.show();
                break;
            case R.id.myLoading:
                loadingDialog.show();

        }
    }
}
