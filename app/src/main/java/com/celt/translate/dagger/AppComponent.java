package com.celt.translate.dagger;


import com.celt.translate.business.translate.TranslateInteractorImpl;
import com.celt.translate.dagger.modules.InteractorModule;
import com.celt.translate.dagger.modules.RepositoriesModule;
import com.celt.translate.ui.main.translate.TranslatePresenter;
import com.celt.translate.ui.selectlang.SelectLangPresenter;
import dagger.Component;

import javax.inject.Singleton;


@Component(modules = {InteractorModule.class, RepositoriesModule.class})
@Singleton
public interface AppComponent {

    void inject(TranslatePresenter translateInteractor);

    void inject(SelectLangPresenter translateInteractor);

    void inject(TranslateInteractorImpl translateInteractor);

}