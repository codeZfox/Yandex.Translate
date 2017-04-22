package com.celt.translate.dagger;

import android.content.Context;
import com.celt.translate.dagger.modules.InteractorModule;
import com.celt.translate.dagger.modules.RepositoriesModule;


public class Components {

    private static Components instance;

    private AppComponent appComponent;

    private Components(Context appContext) {
        appComponent = buildAppComponent(appContext);
    }

    public static void init(Context appContext) {
        instance = new Components(appContext);
    }

    private static Components getInstance() {
        if (instance == null) {
            throw new IllegalStateException("components must be init first");
        }
        return instance;
    }


    public static AppComponent getAppComponent() {
        return getInstance().appComponent;
    }

    private AppComponent buildAppComponent(Context appContext) {
        return DaggerAppComponent.builder()
                .interactorModule(new InteractorModule())
                .repositoriesModule(new RepositoriesModule())
                .build();
    }
}
