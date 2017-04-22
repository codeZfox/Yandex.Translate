package com.celt.translate.business.translate;

import com.celt.translate.business.models.Lang;
import com.celt.translate.data.models.TranslateResponse;
import com.celt.translate.data.repositories.translate.TranslateRepository;
import com.celt.translate.data.repositories.translate.TranslateRepositoryImpl;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TranslateInteractorImpl implements TranslateInteractor {

    private TranslateRepository repository = new TranslateRepositoryImpl();

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
