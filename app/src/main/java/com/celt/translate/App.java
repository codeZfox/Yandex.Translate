package com.celt.translate;

import android.app.Application;
import com.celt.translate.dagger.Components;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Components.init(getApplicationContext());

    }
}
