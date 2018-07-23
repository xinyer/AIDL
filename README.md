- AIDL/app/src/main/aidl/IAppServiceBinder.aidl

```
    void setData(String data);

    String getData();

```

- rebuild project

- AppService.java

```

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

```

- MainActivity.java

bindService

```
Intent serviceIntent = new Intent(this, AppService.class);
bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);

```

MainActivity implements ServiceConnection 

getData
```
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
```

setData
```
try {
    appServiceBinder.setData(etInput.getText().toString());
} catch (RemoteException e) {
    e.printStackTrace();
}
```

