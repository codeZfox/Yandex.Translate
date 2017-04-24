package com.celt.translate.ui.main.localdata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.celt.translate.R;
import com.celt.translate.business.models.Translate;
import com.celt.translate.ui.base.AbsFragment;

import java.util.List;

public class HistoryFragment extends AbsFragment implements HistoryView {

    private static final String ARG_TYPE_FRAGMENT = "TypeLocalFragment";

    public static HistoryFragment newInstance(TypeLocalFragment type) {

        Bundle args = new Bundle();
        args.putString(ARG_TYPE_FRAGMENT, type.name());

        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    HistoryPresenter presenter;

    private ImageView imageViewPlaceHolder;
    private TextView  textViewPlaceHolder;

    @ProvidePresenter
    public HistoryPresenter providePresenter() {
        return new HistoryPresenter(TypeLocalFragment.valueOf(getArguments().getString(ARG_TYPE_FRAGMENT)));
    }

    private HistoryAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_history, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.update();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new HistoryAdapter();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnClickLister(item -> {

        });

        adapter.setOnLongClickLister(item -> presenter.delete(item));

        adapter.setOnClickListerMark(item -> presenter.mark(item));

        imageViewPlaceHolder = (ImageView) view.findViewById(R.id.imageViewPlaceHolder);
        textViewPlaceHolder = (TextView) view.findViewById(R.id.textViewPlaceHolder);
    }

    @Override
    public void setHistory(List<Translate> langs) {
        adapter.setItems(langs);
    }

    @Override
    public void updateHistory() {
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showPlaceHolder(TypeLocalFragment type, boolean isShow) {
        switch (type) {
            case HISTORY: {
                imageViewPlaceHolder.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
                textViewPlaceHolder.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
                if (isShow) {
                    imageViewPlaceHolder.setImageResource(R.drawable.ic_no_history);
                    textViewPlaceHolder.setText(getString(R.string.no_translations_in_history));
                }
                break;
            }
            case FAVORITES: {
                imageViewPlaceHolder.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
                textViewPlaceHolder.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
                if (isShow) {
                    imageViewPlaceHolder.setImageResource(R.drawable.ic_error_face);
                    textViewPlaceHolder.setText(getString(R.string.no_translations_in_fav));
                }
                break;
            }
        }
    }

    @Override
    public void update() {
        if (presenter != null) {
            presenter.update();
        }
    }
}
