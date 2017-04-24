package com.celt.translate.ui.main.translate;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.celt.translate.R;
import com.celt.translate.data.models.dictionary.Def;
import com.celt.translate.data.models.dictionary.Ex;
import com.celt.translate.data.models.dictionary.Syn;
import com.celt.translate.data.models.dictionary.Tr;
import com.celt.translate.ui.base.FlowLayout;
import com.celt.translate.ui.base.OnClickItemListener;

import java.util.ArrayList;
import java.util.List;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {

    private List<Def> items = new ArrayList<>();
    private OnClickItemListener<String> onClickLister;
    private OnClickItemListener<String> onLongClickLister;

    public void setItems(List<Def> items) {
        this.items = items;
    }

    public void clear() {
        this.items.clear();
    }

    public void setOnClickLister(OnClickItemListener<String> onClickLister) {
        this.onClickLister = onClickLister;
    }

    public void setOnLongClickLister(OnClickItemListener<String> onLongClickLister) {
        this.onLongClickLister = onLongClickLister;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView ts;
        TextView pos;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            ts = (TextView) itemView.findViewById(R.id.ts);
            pos = (TextView) itemView.findViewById(R.id.pos);
            layout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_def, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Def item = items.get(position);
        if (position == 0) {
            holder.title.setText(item.getText());
            holder.title.setVisibility(View.VISIBLE);
            if (item.getTs() != null) {
                holder.ts.setText(item.getTs());
            }
            holder.ts.setVisibility(View.VISIBLE);
        } else {
            holder.title.setVisibility(View.GONE);
            holder.ts.setVisibility(View.GONE);
        }
        holder.pos.setText(item.getPos());
        holder.layout.removeAllViews();

        for (int i = 0; i < item.tr.size(); i++) {
            Tr tr = item.tr.get(i);


            LinearLayout linearLayout = new LinearLayout(holder.itemView.getContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(holder.itemView.getContext());
            LinearLayout.LayoutParams llp0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llp0.setMargins(0, dpToPx(holder, 6), dpToPx(holder, 6), 0); // llp.setMargins(left, top, right, bottom);
            textView.setLayoutParams(llp0);
            textView.setText(String.valueOf(i + 1));
            textView.setTextSize(COMPLEX_UNIT_DIP, 16);
            textView.setTextColor(holder.itemView.getResources().getColor(R.color.colorGray));

            linearLayout.addView(textView);

            FlowLayout flowLayout = new FlowLayout(holder.itemView.getContext());
            flowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            TextView view = getTextView1(holder, 8, tr.getText(), R.color.colorTr, false);
            view.setOnClickListener(v -> onClickLister.onClick(view.getText().toString()));
            view.setOnLongClickListener( v -> {
                        onLongClickLister.onClick(view.getText().toString());
                        return true;
                    }
            );
            flowLayout.addView(view);

            if (tr.getGen() != null) {
                flowLayout.addView(getTextView1(holder, 8, " " + tr.getGen(), R.color.colorGray, true));
            }

            if (tr.isSyn()) {
                for (Syn syn : tr.getSyn()) {
                    flowLayout.addView(getTextViewSep(holder));
                    TextView view1 = getTextView1(holder, 0, syn.getText(), R.color.colorTr, false);
                    view1.setOnClickListener(v -> onClickLister.onClick(view.getText().toString()));
                    view1.setOnLongClickListener( v -> {
                                onLongClickLister.onClick(view.getText().toString());
                                return true;
                            }
                    );
                    flowLayout.addView(view1);
                    if (syn.getGen() != null) {
                        flowLayout.addView(getTextView1(holder, 0, " " + syn.getGen(), R.color.colorGray, true));
                    }
                }
            }


            linearLayout.addView(flowLayout);
            holder.layout.addView(linearLayout);

            if (tr.isMean()) {
                TextView textView3 = new TextView(holder.itemView.getContext());
                LinearLayout.LayoutParams llp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp3.setMargins(dpToPx(holder, 14), 0, 0, 0); // llp.setMargins(left, top, right, bottom);
                textView3.setLayoutParams(llp3);

                textView3.setText(tr.getMean());
                textView3.setTextSize(COMPLEX_UNIT_DIP, 20);
                textView3.setTextColor(holder.itemView.getResources().getColor(R.color.colorMean));

                holder.layout.addView(textView3);
            }

            if (tr.isEx()) {
                for (Ex ex : tr.getEx()) {

                    TextView textView3 = new TextView(holder.itemView.getContext());
                    LinearLayout.LayoutParams llp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    llp3.setMargins(dpToPx(holder, 36), dpToPx(holder, 2), 0, dpToPx(holder, 2)); // llp.setMargins(left, top, right, bottom);
                    textView3.setLayoutParams(llp3);

                    textView3.setText(ex.getTr());
                    textView3.setTextSize(COMPLEX_UNIT_DIP, 18);
                    textView3.setTextColor(holder.itemView.getResources().getColor(R.color.colorTr));
                    textView3.setTypeface(null, Typeface.ITALIC);

                    holder.layout.addView(textView3);
                }
            }


        }

        if (position == items.size() - 1) {
            LinearLayout linearLayout = new LinearLayout(holder.itemView.getContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(holder, 36)));
            holder.layout.addView(linearLayout);
        }

    }

    private TextView getTextView1(ViewHolder holder, int left, String text, int color, boolean italic) {
        TextView textView = new TextView(holder.itemView.getContext());
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(dpToPx(holder, left), 0, 0, 0); // llp.setMargins(left, top, right, bottom);
        textView.setLayoutParams(llp);

        textView.setText(text);
        textView.setTextSize(COMPLEX_UNIT_DIP, 20);
        textView.setTextColor(holder.itemView.getResources().getColor(color));
        if (italic) {
            textView.setTypeface(null, Typeface.ITALIC);
        }
        return textView;
    }

    private TextView getTextViewSep(ViewHolder holder) {
        TextView textView = new TextView(holder.itemView.getContext());
        textView.setText(", ");
        textView.setTextSize(COMPLEX_UNIT_DIP, 20);
        textView.setTextColor(holder.itemView.getResources().getColor(R.color.colorTr));
        return textView;
    }

    private int dpToPx(ViewHolder holder, float dp) {
        return (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, dp, holder.itemView.getResources().getDisplayMetrics());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
