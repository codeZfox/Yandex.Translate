package com.celt.translate.ui.main.translate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.celt.translate.R;

public class TranslateFragment extends MvpAppCompatFragment implements TranslateView {

    @InjectPresenter
    TranslatePresenter presenter;

    @ProvidePresenter
    TranslatePresenter providePresenter() {
        return new TranslatePresenter(getContext());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_translate, container, false);
    }

}
