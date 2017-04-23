package com.celt.translate;

import android.app.Application;
import com.celt.translate.dagger.Components;
import ru.yandex.speechkit.SpeechKit;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Components.init(getApplicationContext());
        SpeechKit.getInstance().configure(getApplicationContext(), "f9f1e2f6-0a3c-463a-a8f0-2ff9b9816b73");

    }
}
