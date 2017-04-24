package com.celt.translate.ui.selectlang.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.celt.translate.R;
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate;

import java.util.List;

public class TitleDelegate extends AbsListItemAdapterDelegate<LangTitle, LangSelectable, TitleDelegate.ViewHolder> {
    @Override
    protected boolean isForViewType(@NonNull LangSelectable item, @NonNull List<LangSelectable> items, int position) {
        return item instanceof LangTitle;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_lang_title, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull LangTitle item, @NonNull ViewHolder viewHolder, @NonNull List<Object> payloads) {
        viewHolder.textView.setText(item.getTitle());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
        }
    }
}