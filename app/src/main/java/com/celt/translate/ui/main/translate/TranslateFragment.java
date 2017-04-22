package com.celt.translate.ui.main.translate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    private LinearLayout leftView, rightView;
    private TextView foo, bar;
    private int viewHeight;
    private boolean noSwap = true;
    private static int ANIMATION_DURATION = 300;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        leftView = (LinearLayout) view.findViewById(R.id.left_view);
        rightView = (LinearLayout) view.findViewById(R.id.right_view);
        foo = (TextView) view.findViewById(R.id.textView);
        bar = (TextView) view.findViewById(R.id.textView2);

        ViewTreeObserver viewTreeObserver = foo.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int width = displayMetrics.widthPixels;

                    foo.getViewTreeObserver().addOnGlobalLayoutListener(this);
                    viewHeight = width - (foo.getWidth() + bar.getWidth()) + foo.getWidth();
                    foo.getLayoutParams();
                }
            });
        }


        view.findViewById(R.id.switchTranslate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noSwap) {
                    TranslateAnimation ta1 = new TranslateAnimation(0, viewHeight, 0, 0);
                    ta1.setDuration(ANIMATION_DURATION);
                    ta1.setFillAfter(true);
                    leftView.startAnimation(ta1);
                    leftView.bringToFront();

                    TranslateAnimation ta2 = new TranslateAnimation(0, -viewHeight, 0, 0);
                    ta2.setDuration(ANIMATION_DURATION);
                    ta2.setFillAfter(true);
                    rightView.startAnimation(ta2);
                    rightView.bringToFront();

                    noSwap = false;
                } else {
                    TranslateAnimation ta1 = new TranslateAnimation(viewHeight, 0, 0, 0);
                    ta1.setDuration(ANIMATION_DURATION);
                    ta1.setFillAfter(true);
                    leftView.startAnimation(ta1);
                    leftView.bringToFront();

                    TranslateAnimation ta2 = new TranslateAnimation(-viewHeight, 0, 0, 0);
                    ta2.setDuration(ANIMATION_DURATION);
                    ta2.setFillAfter(true);
                    rightView.startAnimation(ta2);
                    rightView.bringToFront();

                    noSwap = true;
                }
            }
        });
    }
}
