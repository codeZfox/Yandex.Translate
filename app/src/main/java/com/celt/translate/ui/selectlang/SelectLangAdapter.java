package com.celt.translate.ui.selectlang;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.celt.translate.R;
import com.celt.translate.business.models.Lang;
import com.celt.translate.ui.base.OnClickItemListener;

import java.util.ArrayList;
import java.util.List;

public class SelectLangAdapter extends RecyclerView.Adapter<SelectLangAdapter.ViewHolder> {

    private List<Lang> items = new ArrayList<>();
    private OnClickItemListener<Lang> onClickLister;

    public void setDict(List<Lang> langs) {
        this.items = langs;
//        Collections.sort(items);
    }

    public void setOnClickLister(OnClickItemListener<Lang> onClickLister) {
        this.onClickLister = onClickLister;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_lang, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lang item = items.get(position);
        holder.textView.setText(item.getUi());
        holder.itemView.setOnClickListener(v -> onClickLister.onClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
