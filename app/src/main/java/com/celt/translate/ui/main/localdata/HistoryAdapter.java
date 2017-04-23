package com.celt.translate.ui.main.localdata;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.celt.translate.R;
import com.celt.translate.business.models.Translate;
import com.celt.translate.ui.base.OnClickItemListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Translate> items = new ArrayList<>();
    private OnClickItemListener<Translate> onClickLister;
    private OnClickItemListener<Translate> onLongClickLister;
    private OnClickItemListener<Translate> onClickListerMark;

    public void setItems(List<Translate> langs) {
        this.items = langs;
    }

    public void setOnClickLister(OnClickItemListener<Translate> onClickLister) {
        this.onClickLister = onClickLister;
    }

    public void setOnLongClickLister(OnClickItemListener<Translate> onLongClickLister) {
        this.onLongClickLister = onLongClickLister;
    }

    public void setOnClickListerMark(OnClickItemListener<Translate> onClickLister) {
        this.onClickListerMark = onClickLister;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textSource;
        TextView textTarget;
        TextView textLang;
        ImageView mark;

        public ViewHolder(View itemView) {
            super(itemView);
            textSource = (TextView) itemView.findViewById(R.id.textViewSource);
            textTarget = (TextView) itemView.findViewById(R.id.textViewTarget);
            textLang = (TextView) itemView.findViewById(R.id.textViewLang);
            mark = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_translate, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Translate item = items.get(position);
        holder.textSource.setText(item.source);
        holder.textTarget.setText(item.translation);
        holder.textLang.setText(item.textLang());
        holder.mark.setColorFilter(holder.itemView.getContext().getResources().getColor(getColorMark(item.isFavorite)));
        holder.mark.setOnClickListener(v -> onClickListerMark.onClick(item));
        holder.itemView.setOnClickListener(v -> onClickLister.onClick(item));
        holder.itemView.setOnLongClickListener(v -> {
            onLongClickLister.onClick(item);
            return true;
        });
    }

    private int getColorMark(boolean item) {
        return item ? R.color.colorAccent : R.color.colorGray;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
