package com.tw.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private Intent serviceIntent = null;
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        findViewById(R.id.btnSend).setOnClickListener(this);

        serviceIntent = new Intent(this, AppService.class);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnBindService:
                bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
                break;

            case R.id.btnUnbindService:
                unbindService(this);
                break;

            case R.id.btnSend:
                try {
                    appServiceBinder.setData(etInput.getText().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            default:
        }
    }

    IAppServiceBinder appServiceBinder = null;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        appServiceBinder = IAppServiceBinder.Stub.asInterface(iBinder);
        try {
            String data = appServiceBinder.getData();
            System.out.println("get data from AppService:" + data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        appServiceBinder = null;
    }
}
