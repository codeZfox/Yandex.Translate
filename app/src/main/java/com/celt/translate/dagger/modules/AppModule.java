package com.celt.translate.dagger.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context appContext;

    public AppModule(@NonNull Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    @NonNull
    public Context getAppContext() {
        return appContext;
    }

}