package com.celt.translate.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.celt.translate.R;
import com.celt.translate.ui.base.ViewPagerAdapter;
import com.celt.translate.ui.main.translate.TranslateFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView {


    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return new MainPresenter(this);
    }

    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new TranslateFragment());
        adapter.addFragment(new TranslateFragment());
        adapter.addFragment(new TranslateFragment());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_translate);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_fav);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tab_settings);
    }
}
