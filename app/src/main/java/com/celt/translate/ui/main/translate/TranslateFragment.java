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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.celt.translate.R;
import com.celt.translate.business.models.Lang;
import com.celt.translate.ui.base.AbsFragment;
import com.celt.translate.ui.base.SimpleTextWatcher;
import com.celt.translate.ui.selectlang.SelectLangActivity;

import static android.app.Activity.RESULT_OK;
import static com.celt.translate.ui.base.KeyboardUtils.setupUI;
import static com.celt.translate.ui.base.KeyboardUtils.showSoftKeyboard;

public class TranslateFragment extends AbsFragment implements TranslateView {

    @InjectPresenter
    TranslatePresenter presenter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_translate, container, false);
    }

    private TextView textViewLangFrom, textViewLangTo;
    private TextView translatedText;
    private ImageView btnPlayText;
    private View btnClearText;

    private int position;
    private EditText editText;
    private boolean isSwap = false;
    private static int ANIMATION_DURATION = 300;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = (EditText) view.findViewById(R.id.editText);
        translatedText = (TextView) view.findViewById(R.id.textView_translated);
        btnClearText = view.findViewById(R.id.btnClearText);
        btnClearText.setVisibility(View.INVISIBLE);
        btnClearText.setOnClickListener(v -> {
            editText.setText("");
//            presenter.translate("");
        });

        textViewLangFrom = (TextView) view.findViewById(R.id.textView_lang_from);
        textViewLangTo = (TextView) view.findViewById(R.id.textView_lang_to);

        editText.setHorizontallyScrolling(false);
        editText.setMinLines(4);
        editText.setMaxLines(6);

        getTextViewLangFrom().setOnClickListener(v -> presenter.clickTextViewLangFrom());
        getTextViewLangTo().setOnClickListener(v -> presenter.clickTextViewLangTo());

        int width = getWidthScreen();

        ViewTreeObserver viewTreeObserver = textViewLangFrom.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    textViewLangFrom.getViewTreeObserver().addOnGlobalLayoutListener(this);
                    position = width - (textViewLangFrom.getWidth() + textViewLangTo.getWidth()) + textViewLangFrom.getWidth();
                    textViewLangFrom.getLayoutParams();
                }
            });
        }


        view.findViewById(R.id.switchTranslate).setOnClickListener(v -> {
            if (!isSwap) {
                TranslateAnimation ta1 = new TranslateAnimation(0, position, 0, 0);
                ta1.setDuration(ANIMATION_DURATION);
                ta1.setFillAfter(true);
                textViewLangFrom.startAnimation(ta1);
                textViewLangFrom.bringToFront();

                TranslateAnimation ta2 = new TranslateAnimation(0, -position, 0, 0);
                ta2.setDuration(ANIMATION_DURATION);
                ta2.setFillAfter(true);
                textViewLangTo.startAnimation(ta2);
                textViewLangTo.bringToFront();

                isSwap = true;

                presenter.swapLang();
            } else {
                TranslateAnimation ta1 = new TranslateAnimation(position, 0, 0, 0);
                ta1.setDuration(ANIMATION_DURATION);
                ta1.setFillAfter(true);
                textViewLangFrom.startAnimation(ta1);
                textViewLangFrom.bringToFront();

                TranslateAnimation ta2 = new TranslateAnimation(-position, 0, 0, 0);
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
                presenter.translate(v.getText().toString());
            }
            return true;
        });

        editText.addTextChangedListener(new SimpleTextWatcher() {
                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                String text = s.toString();
                                                btnClearText.setVisibility(!text.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                                                presenter.translate(text);
                                            }
                                        }
        );

        btnPlayText = (ImageView) view.findViewById(R.id.btnPlayText);
        btnPlayText.setOnClickListener(v -> presenter.playText());
        setupUI(getActivity(), view);
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

//    @Override
//    public void showTranslate(List<String> list) {
//        StringBuilder builder = new StringBuilder();
//
//        for (String item : list) {
//            builder.append(item);
//            builder.append("\t");
//        }
//
//        translatedText.setText(builder.toString());
//    }

    @Override
    public void showTranslate(String text) {
        translatedText.setText(text);
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

            editText.requestFocus();
            showSoftKeyboard(getActivity());

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

    private RotateAnimation anim;

    @Override
    public void showAnimationPlayText(boolean isPlay) {
        btnPlayText.setImageResource(isPlay ? R.drawable.ic_spinner : R.drawable.ic_sound_active);
        if (isPlay) {
            if (anim == null) {
                int pivotX = (btnPlayText.getWidth() - btnPlayText.getPaddingLeft() - btnPlayText.getPaddingRight()) / 2;
                int pivotY = (btnPlayText.getHeight() - btnPlayText.getPaddingTop() - btnPlayText.getPaddingBottom()) / 2;
                anim = new RotateAnimation(0f, 350f, pivotX, pivotY);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(Animation.INFINITE);
                anim.setDuration(700);
            }
            btnPlayText.startAnimation(anim);
        } else {
            if (anim != null) {
                anim.cancel();
            }
        }
    }
}
