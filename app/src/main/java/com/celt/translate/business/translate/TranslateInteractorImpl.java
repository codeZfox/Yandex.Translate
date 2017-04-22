package com.celt.translate.business.translate;

import com.celt.translate.business.models.Lang;
import com.celt.translate.dagger.Components;
import com.celt.translate.data.models.TranslateResponse;
import com.celt.translate.data.repositories.translate.TranslateRepository;
import io.reactivex.Single;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TranslateInteractorImpl implements TranslateInteractor {

    @Inject
    public TranslateRepository repository;

    public TranslateInteractorImpl() {
        Components.getAppComponent().inject(this);
    }

    public Single<List<Lang>> getLangs(String ui) {
        return repository.getLangs(ui)
                .map(i -> {
                    List<Lang> list = new ArrayList<>();
                    for (Map.Entry<String, String> entry : i.getLangs().entrySet()) {
                        list.add(new Lang(entry.getKey(), entry.getValue()));
                    }
                    Collections.sort(list);
                    return list;
                });
    }

    @Override
    public Single<TranslateResponse> translate(String text, Lang langFrom, Lang langTo) {
        return repository.translate(text, langFrom.getCode() + "-" + langTo.getCode());
    }

}
