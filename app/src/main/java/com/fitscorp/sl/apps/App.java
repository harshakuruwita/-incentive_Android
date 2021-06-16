package com.fitscorp.sl.apps;

import android.app.Application;
import android.content.Context;
import androidx.room.Room;

import com.facebook.stetho.Stetho;
import com.fitscorp.sl.apps.di.DaggerMyComponent;
import com.fitscorp.sl.apps.di.MainModule;
import com.fitscorp.sl.apps.di.MyComponent;


import static com.fitscorp.sl.apps.common.URLStringsKt.BASE_URL;


public class App extends Application {
    private MyComponent appComponent;

    private static com.fitscorp.sl.apps.App instance;

    public static com.fitscorp.sl.apps.App getInstance() {
        return instance;
    }

    public static Context context;

   // public static AppDatabase roomDB = null;



    @Override
    public void onCreate() {
        super.onCreate();

        App.context = getApplicationContext();


        Stetho.initializeWithDefaults(this);
        initApplication();

        appComponent = DaggerMyComponent.builder().mainModule(new MainModule(this, BASE_URL))
                .build();

        appComponent.inject(this);

//        roomDB = Room.databaseBuilder(this, AppDatabase.class,
//                "ayubolifemasterdb").build();

    }

    public static Context getAppContext() {
        return App.context;
    }
    private void initApplication() {
        instance = this;
    }

//    public static AppDatabase getRoomDB() {
//        return roomDB;
//    }


    public MyComponent getAppComponent() {
        return appComponent;
    }





}


