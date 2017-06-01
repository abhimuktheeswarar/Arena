package msa.rehearsal.round1.subround1_2.datamodel;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import msa.rehearsal.round1.subround1_2.model.Language;


public interface IDataModel {

    @NonNull
    Observable<List<Language>> getSupportedLanguages();

    @NonNull
    Flowable<String> getGreetingByLanguageCode(@NonNull final Language.LanguageCode code);
}
