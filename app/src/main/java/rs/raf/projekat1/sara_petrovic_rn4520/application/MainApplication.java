package rs.raf.projekat1.sara_petrovic_rn4520.application;

import android.app.Application;

import timber.log.Timber;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
