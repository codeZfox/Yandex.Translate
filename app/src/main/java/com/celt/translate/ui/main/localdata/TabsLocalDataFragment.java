package com.celt.translate.ui.main.localdata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.celt.translate.R;
import com.celt.translate.ui.base.AbsFragment;
import com.celt.translate.ui.base.ViewPagerAdapter;

public class TabsLocalDataFragment extends AbsFragment {


    private ViewPagerAdapter<AbsFragment> adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_view_pager_with_bottom_tablayout, container, false);

        adapter = new ViewPagerAdapter<>(getChildFragmentManager());

        adapter.addFragment(HistoryFragment.newInstance(TypeLocalFragment.FAVORITES), getString(R.string.favorites));
        adapter.addFragment(HistoryFragment.newInstance(TypeLocalFragment.HISTORY), getString(R.string.history));

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                (adapter.getItem(position)).update();
            }
        });

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void update() {
        for (AbsFragment item: adapter.getItems()) {
            item.update();
        }
    }
}
