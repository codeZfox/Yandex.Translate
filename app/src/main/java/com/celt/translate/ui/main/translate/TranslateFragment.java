package com.celt.translate.ui.main.translate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.celt.translate.R;
import com.celt.translate.business.models.Lang;
import com.celt.translate.ui.base.SimpleTextWatcher;
import com.celt.translate.ui.selectlang.SelectLangActivity;

import java.util.List;

import static android.app.Activity.RESULT_OK;

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

    private TextView textViewLangFrom, textViewLangTo;
    TextView translatedText;

    private int viewHeight;
    private EditText editText;
    private boolean isSwap = false;
    private static int ANIMATION_DURATION = 300;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = (EditText) view.findViewById(R.id.editText);
        translatedText = (TextView) view.findViewById(R.id.textView_translated);

        textViewLangFrom = (TextView) view.findViewById(R.id.textView_lang_from);
        textViewLangTo = (TextView) view.findViewById(R.id.textView_lang_to);

        getTextViewLangFrom().setOnClickListener(v -> presenter.clickTextViewLangFrom());
        getTextViewLangTo().setOnClickListener(v -> presenter.clickTextViewLangTo());

        int width = getWidthScreen();

        ViewTreeObserver viewTreeObserver = textViewLangFrom.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    textViewLangFrom.getViewTreeObserver().addOnGlobalLayoutListener(this);
                    viewHeight = width - (textViewLangFrom.getWidth() + textViewLangTo.getWidth()) + textViewLangFrom.getWidth();
                    textViewLangFrom.getLayoutParams();
                }
            });
        }


        view.findViewById(R.id.switchTranslate).setOnClickListener(v -> {
            if (!isSwap) {
                TranslateAnimation ta1 = new TranslateAnimation(0, viewHeight, 0, 0);
                ta1.setDuration(ANIMATION_DURATION);
                ta1.setFillAfter(true);
                textViewLangFrom.startAnimation(ta1);
                textViewLangFrom.bringToFront();

                TranslateAnimation ta2 = new TranslateAnimation(0, -viewHeight, 0, 0);
                ta2.setDuration(ANIMATION_DURATION);
                ta2.setFillAfter(true);
                textViewLangTo.startAnimation(ta2);
                textViewLangTo.bringToFront();

                isSwap = true;

                presenter.swapLang();
            } else {
                TranslateAnimation ta1 = new TranslateAnimation(viewHeight, 0, 0, 0);
                ta1.setDuration(ANIMATION_DURATION);
                ta1.setFillAfter(true);
                textViewLangFrom.startAnimation(ta1);
                textViewLangFrom.bringToFront();

                TranslateAnimation ta2 = new TranslateAnimation(-viewHeight, 0, 0, 0);
                ta2.setDuration(ANIMATION_DURATION);
                ta2.setFillAfter(true);
                textViewLangTo.startAnimation(ta2);
                textViewLangTo.bringToFront();

                isSwap = false;
                presenter.swapLang();
            }
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Toast.makeText(getContext(), getTextViewLangFrom().getText().toString(), Toast.LENGTH_SHORT).show();
                presenter.translate(v.getText().toString());
            }
            return true;
        });

        editText.addTextChangedListener(new SimpleTextWatcher() {
                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                presenter.translate(s.toString());
                                            }
                                        }
        );
    }

    private TextView getTextViewLangFrom() {
        return isSwap ? textViewLangTo : textViewLangFrom;
    }

    private TextView getTextViewLangTo() {
        return isSwap ? textViewLangFrom : textViewLangTo;
    }

    private int getWidthScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    @Override
    public void setLangFrom(Lang lang) {
        getTextViewLangFrom().setText(lang.getUi());
    }

    @Override
    public void setLangTo(Lang lang) {
        getTextViewLangTo().setText(lang.getUi());
    }

    @Override
    public void showTranslate(List<String> list) {
        StringBuilder builder = new StringBuilder();

        for (String item : list) {
            builder.append(item);
            builder.append("\t");
        }

        translatedText.setText(builder.toString());
    }

    @Override
    public void showTranslate(String text) {
        translatedText.setText("");
    }


    private static final int REQUEST_CODE_LANG_FROM = 0;
    private static final int REQUEST_CODE_LANG_TO = 1;

    @Override
    public void openSelectLangFromScreen(Lang lang) {
        startActivityForResult(SelectLangActivity.newIntent(getContext(), lang), REQUEST_CODE_LANG_FROM);
    }

    @Override
    public void openSelectLangToScreen(Lang lang) {
        startActivityForResult(SelectLangActivity.newIntent(getContext(), lang), REQUEST_CODE_LANG_TO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_LANG_FROM: {
                    presenter.setLangFrom(data.getParcelableExtra(Lang.NAME));
                    break;
                }
                case REQUEST_CODE_LANG_TO: {
                    presenter.setLangTo(data.getParcelableExtra(Lang.NAME));
                    break;
                }
            }
        }
    }
}
