package com.celt.translate.ui.main.localdata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.celt.translate.R;
import com.celt.translate.business.models.Translate;
import com.celt.translate.ui.base.AbsFragment;
import com.celt.translate.ui.base.SimpleTextWatcher;

import java.util.List;

import static com.celt.translate.ui.base.KeyboardUtils.hideSoftKeyboard;
import static com.celt.translate.ui.base.KeyboardUtils.setupUI;

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
    private TextView textViewPlaceHolder;

    private View btnClearText;
    private EditText editText;

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

        adapter.setOnLongClickLister(item -> presenter.longClick(item));

        adapter.setOnClickListerMark(item -> presenter.mark(item));

        imageViewPlaceHolder = (ImageView) view.findViewById(R.id.imageViewPlaceHolder);
        textViewPlaceHolder = (TextView) view.findViewById(R.id.textViewPlaceHolder);

        btnClearText = view.findViewById(R.id.btnClearText);
        btnClearText.setOnClickListener(v -> {
            editText.setText("");
            presenter.search("");
        });


        editText = (EditText) view.findViewById(R.id.editText);

        setupUI(getActivity(), view);

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.search(editText.getText().toString());
                hideSoftKeyboard(getActivity());
            }
            return true;
        });

        editText.addTextChangedListener(new SimpleTextWatcher() {
                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                presenter.search(s.toString());
                                            }
                                        }
        );

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                presenter.search(editText.getText().toString());
            }
        });
    }

    @Override
    public void showBtnClear(boolean isShow) {
        btnClearText.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showDeleteDialog(Translate item, boolean show) {
        if (show) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.delete_tr)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        presenter.delete(item);
                    })
                    .setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.cancel());
            builder.setOnDismissListener(dialog -> {
                presenter.cancelDeleteDialog();
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void setItems(List<Translate> langs) {
        adapter.setItems(langs);
    }

    @Override
    public void updateItems() {
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
