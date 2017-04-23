package com.celt.translate.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.celt.translate.R;
import com.celt.translate.ui.base.AbsFragment;
import com.celt.translate.ui.base.ViewPagerAdapter;
import com.celt.translate.ui.main.localdata.TabsLocalDataFragment;
import com.celt.translate.ui.main.translate.TranslateFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView {


    @InjectPresenter
    MainPresenter presenter;

    private ViewPagerAdapter<AbsFragment> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_view_pager_with_top_tablayout);

        adapter = new ViewPagerAdapter<>(getSupportFragmentManager());

        adapter.addFragment(new TranslateFragment());
        adapter.addFragment(new TabsLocalDataFragment());
        adapter.addFragment(new TranslateFragment());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                (adapter.getItem(position)).update();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_translate);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_fav);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tab_settings);
    }
}
