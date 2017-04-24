package com.celt.translate.business.translate;

import com.celt.translate.business.models.Lang;
import com.celt.translate.business.models.Translate;
import com.celt.translate.dagger.Components;
import com.celt.translate.data.models.LookupResponse;
import com.celt.translate.data.repositories.database.DatabaseRepository;
import com.celt.translate.data.repositories.dictionary.DictionaryRepository;
import com.celt.translate.data.repositories.translate.TranslateRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TranslateInteractorImpl implements TranslateInteractor {

    @Inject
    public TranslateRepository repository;

    @Inject
    public DictionaryRepository dictionaryRepository;

    @Inject
    public DatabaseRepository databaseRepository;

    public TranslateInteractorImpl() {
        Components.getAppComponent().inject(this);
    }

    public Observable<List<Lang>> getLangs(String ui) {
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
    public Observable<Translate> translate(String text, Lang langFrom, Lang langTo, boolean save) {
        return databaseRepository.findTranslate(text, langFrom.getCode(), langTo.getCode())
                .flatMap(translateList -> {
                    if (!translateList.isEmpty()) {
                        Translate item = translateList.get(0);
                        databaseRepository.updateTranslate(item);
                        return Observable.just(item);
                    } else {
                        return repository.translate(text, getLangLang(langFrom, langTo))
                                .flatMap(response -> save ? saveTranslate(text, langFrom, langTo, response.getText().get(0))
                                        : Observable.just(new Translate(text, response.getText().get(0), langFrom, langTo)));
                    }
                });

    }

    private Observable<Translate> saveTranslate(String text, Lang langFrom, Lang langTo, String response) {
        return databaseRepository.saveTranslate(new Translate(text, response, langFrom, langTo));
    }

    private String getLangLang(Lang langFrom, Lang langTo) {
        return langFrom.getCode() + "-" + langTo.getCode();
    }

    public Observable<Translate> getHistory() {
        return databaseRepository.getHistory();
    }

    public Observable<Translate> getFavorites() {
        return databaseRepository.getFavorites();
    }

    public Completable mark(Translate item) {
        return databaseRepository.mark(item);
    }

    @Override
    public Single<LookupResponse> lookup(String text, Lang langFrom, Lang langTo, String ui) {
        return dictionaryRepository.lookup(text, getLangLang(langFrom, langTo), ui);
    }
}
