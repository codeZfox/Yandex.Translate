package com.celt.translate.ui.selectlang.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.celt.translate.R;
import com.celt.translate.business.models.Lang;
import com.celt.translate.ui.base.OnClickItemListener;
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate;

import java.util.List;

public class LangAdapterDelegate extends AbsListItemAdapterDelegate<Lang, LangSelectable, LangAdapterDelegate.ViewHolder> {
    private OnClickItemListener<Lang> onClickLister;
    private Lang currantLang;

    public LangAdapterDelegate(OnClickItemListener<Lang> onClickLister, Lang currantLang) {
        this.onClickLister = onClickLister;
        this.currantLang = currantLang;
    }

    @Override
    protected boolean isForViewType(@NonNull LangSelectable item, @NonNull List<LangSelectable> items, int position) {
        return item instanceof Lang;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_lang, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull Lang item, @NonNull ViewHolder viewHolder, @NonNull List<Object> payloads) {

        viewHolder.check.setVisibility(currantLang.equals(item) ? View.VISIBLE : View.INVISIBLE);

        viewHolder.textView.setText(item.getUi());

        viewHolder.itemView.setOnClickListener(v -> onClickLister.onClick(item));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        View check;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
            check = itemView.findViewById(R.id.viewCheck);
        }
    }
}