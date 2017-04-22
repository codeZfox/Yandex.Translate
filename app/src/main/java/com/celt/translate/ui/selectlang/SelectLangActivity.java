package com.celt.translate.ui.selectlang;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.celt.translate.R;
import com.celt.translate.business.models.Lang;

import java.util.List;

public class SelectLangActivity extends MvpAppCompatActivity implements SelectLang {

    @InjectPresenter
    SelectLangPresenter presenter;

    @ProvidePresenter
    SelectLangPresenter providePresenter() {
        return new SelectLangPresenter(this);
    }

    private SelectLangAdapter adapter = new SelectLangAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_select_lang);

        setTitle("Язык текста");
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickLister(item -> Toast.makeText(SelectLangActivity.this, item.getCode(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setLangs(List<Lang> langs) {
        adapter.setDict(langs);
        adapter.notifyDataSetChanged();
    }
}
