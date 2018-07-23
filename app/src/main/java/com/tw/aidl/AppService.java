package com.tw.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AppService extends Service {

    private String data = "123";

    public AppService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return new IAppServiceBinder.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {

            }

            @Override
            public void setData(String data) {
                System.out.println("AppService receive remote data:" + data);
            }

            @Override
            public String getData() throws RemoteException {
                return data;
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("service create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("service destroy");
    }
}
