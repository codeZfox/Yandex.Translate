package com.celt.translate.ui.main.translate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.celt.translate.R;
import com.celt.translate.business.models.Lang;
import com.celt.translate.data.models.dictionary.Def;
import com.celt.translate.ui.base.AbsFragment;
import com.celt.translate.ui.base.SimpleTextWatcher;
import com.celt.translate.ui.selectlang.SelectLangActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static android.app.Activity.RESULT_OK;
import static com.celt.translate.ui.base.KeyboardUtils.hideSoftKeyboard;
import static com.celt.translate.ui.base.KeyboardUtils.setupUI;

public class TranslateFragment extends AbsFragment implements TranslateView {

    @InjectPresenter
    TranslatePresenter presenter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_translate, container, false);
    }

    private TextView textViewLangFrom, textViewLangTo;
    private EditText editText;
    private TextView translatedText;
    private ImageView bookMaker;
    private View btnShare;

    private ImageView btnPlayTextSource;
    private ImageView btnPlayTextTarget;
    private View btnClearText;

    private int position;
    private boolean isSwap = false;
    private static int ANIMATION_DURATION = 300;

    private DictionaryAdapter adapter = new DictionaryAdapter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickLister(item -> presenter.setTextSource(item));
        adapter.setOnLongClickLister(item -> presenter.copyText(getContext(), item));


        editText = (EditText) view.findViewById(R.id.editText);
        translatedText = (TextView) view.findViewById(R.id.textView_translated);
        translatedText.setOnClickListener(v -> {
            presenter.copySourceText(getContext());
        });

        btnClearText = view.findViewById(R.id.btnClearText);
        btnClearText.setOnClickListener(v -> {
            presenter.setTextSource("");
        });


        btnPlayTextSource = (ImageView) view.findViewById(R.id.btnPlayText);
        btnPlayTextSource.setOnClickListener(v -> presenter.playSourceText());

        btnPlayTextTarget = (ImageView) view.findViewById(R.id.btnPlayTextTarget);
        btnPlayTextTarget.setOnClickListener(v -> presenter.playTargetText());

        bookMaker = ((ImageView) getView().findViewById(R.id.btnBookMaker));
        bookMaker.setOnClickListener(v -> presenter.bookmaker());
        btnShare = getView().findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> shareText(translatedText.getText().toString()));

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
                    position = width - (textViewLangFrom.getWidth() + textViewLangTo.getWidth()) + textViewLangFrom.getWidth();
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
                presenter.translate(editText.getText().toString(), true);
                hideSoftKeyboard(getActivity());
            }
            return true;
        });

        editText.addTextChangedListener(new SimpleTextWatcher() {
                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                String text = s.toString();
                                                presenter.translate(text, false);
                                            }
                                        }
        );

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                presenter.translate(editText.getText().toString(), true);
            }
        });

        setupUI(getActivity(), view);
    }

    @Override
    public void showBtnSource(boolean isShow) {
        int visibility = isShow ? View.VISIBLE : View.GONE;
        btnClearText.setVisibility(visibility);
        btnPlayTextSource.setVisibility(visibility);
    }

    @Override
    public void showBtnTarget(boolean isShow) {
        int visibility = isShow ? View.VISIBLE : View.GONE;
        bookMaker.setVisibility(visibility);
        btnPlayTextTarget.setVisibility(visibility);
        btnShare.setVisibility(visibility);
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
    public void setLangSource(Lang lang) {
        getTextViewLangFrom().setText(lang.getUi());
    }

    @Override
    public void setLangTarget(Lang lang) {
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

    @Override
    public void setSourceText(String text) {
        editText.setText(text);
    }

    @Override
    public void showDictionaryInfo(List<Def> items) {
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideDictionaryInfo() {
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    private static final int REQUEST_CODE_LANG_FROM = 0;
    private static final int REQUEST_CODE_LANG_TO = 1;

    @Override
    public void openSelectLangFromScreen(Lang lang, Queue<Lang> list) {
        startActivityForResult(SelectLangActivity.newIntent(getContext(), lang, new ArrayList<>(list)), REQUEST_CODE_LANG_FROM);
    }

    @Override
    public void openSelectLangToScreen(Lang lang, Queue<Lang> list) {
        startActivityForResult(SelectLangActivity.newIntent(getContext(), lang, new ArrayList<>(list)), REQUEST_CODE_LANG_TO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_LANG_FROM: {
                    presenter.setLangSource(data.getParcelableExtra(Lang.NAME));
                    break;
                }
                case REQUEST_CODE_LANG_TO: {
                    presenter.setLangTarget(data.getParcelableExtra(Lang.NAME));
                    break;
                }
            }
        }
    }

    private RotateAnimation anim;

    @Override
    public void showAnimationPlayTextSource() {
        startAnim(btnPlayTextSource);
    }

    @Override
    public void showAnimationPlayTextTarget() {
        startAnim(btnPlayTextTarget);
    }

    @Override
    public void hideAnimationPlayTextSource(boolean isPlay) {
        if (anim != null) {
            anim.cancel();
        }
        enablePlayBtnTextSource(isPlay);
    }

    @Override
    public void hideAnimationPlayTextTarget(boolean isPlay) {
        if (anim != null) {
            anim.cancel();
        }
        enablePlayBtnTextTarget(isPlay);
    }

    private void startAnim(ImageView imageView) {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_spinner));
        if (anim == null) {
            int pivotX = (imageView.getWidth() / 2);
            int pivotY = (imageView.getHeight() / 2);
            anim = new RotateAnimation(0f, 350f, pivotX, pivotY);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(700);
        }
        imageView.startAnimation(anim);
    }

    @Override
    public void enablePlayBtnTextSource(boolean isPlay) {
        btnPlayTextSource.setImageDrawable(getResources().getDrawable(isPlay ? R.drawable.ic_sound_active : R.drawable.ic_sound_disabled));
    }

    @Override
    public void enablePlayBtnTextTarget(boolean isPlay) {
        btnPlayTextTarget.setImageDrawable(getResources().getDrawable(isPlay ? R.drawable.ic_sound_active : R.drawable.ic_sound_disabled));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBookMaker(boolean isFavorite) {
        bookMaker.setColorFilter(getResources().getColor(getColorMark(isFavorite)));
    }

    private int getColorMark(boolean item) {
        return item ? R.color.colorAccent : R.color.colorGray;
    }

    @Override
    public void update() {
        presenter.update();
    }

    private void shareText(String text) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sharingIntent, null));
    }
}
