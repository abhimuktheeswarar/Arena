package msa.rehearsal.round1.subround2.datamodel;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import msa.rehearsal.round1.subround2.model.Language;

public class DataModel implements IDataModel {

    @NonNull
    @Override
    public Observable<List<Language>> getSupportedLanguages() {
        return Observable.fromCallable(this::getLanguages);
    }

    @NonNull
    private List<Language> getLanguages() {
        return Arrays
                .asList(new Language("English", Language.LanguageCode.EN),
                        new Language("German", Language.LanguageCode.DE),
                        new Language("Slovakian", Language.LanguageCode.HR));
    }

    @NonNull
    @Override
    public Flowable<String> getGreetingByLanguageCode(@NonNull final Language.LanguageCode code) {
        switch (code) {
            case DE:
                return Flowable.just("Guten Tag!");
            case EN:
                return Flowable.just("Hello!");
            case HR:
                return Flowable.just("Zdravo!");
            default:
                return Flowable.empty();
        }
    }
}
