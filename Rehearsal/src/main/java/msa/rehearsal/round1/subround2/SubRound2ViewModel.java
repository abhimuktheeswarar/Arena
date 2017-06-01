package msa.rehearsal.round1.subround2;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.processors.BehaviorProcessor;
import msa.rehearsal.R;
import msa.rehearsal.round1.subround2.datamodel.IDataModel;
import msa.rehearsal.round1.subround2.model.Language;
import msa.rehearsal.round1.subround2.schedulers.ISchedulerProvider;

/**
 * Created by Abhimuktheeswarar on 31-05-2017.
 */

public class SubRound2ViewModel {

    @NonNull
    private final IDataModel mDataModel;

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    @NonNull
    private final BehaviorProcessor<Language> mSelectedLanguage = BehaviorProcessor.create();

    SubRound2ViewModel(@NonNull IDataModel mDataModel, @NonNull ISchedulerProvider mSchedulerProvider) {
        this.mDataModel = mDataModel;
        this.mSchedulerProvider = mSchedulerProvider;
    }

    Observable<String> getGreeting() {
        return mSelectedLanguage.observeOn(mSchedulerProvider.computation()).subscribeOn(mSchedulerProvider.ui()).map(new Function<Language, Language.LanguageCode>() {
            @Override
            public Language.LanguageCode apply(@io.reactivex.annotations.NonNull Language language) throws Exception {
                return language.getCode();
            }
        }).flatMap(new Function<Language.LanguageCode, Publisher<? extends String>>() {
            @Override
            public Publisher<? extends String> apply(@io.reactivex.annotations.NonNull Language.LanguageCode languageCode) throws Exception {
                return mDataModel.getGreetingByLanguageCode(languageCode);
            }
        }).toObservable();
    }

    Observable<List<Language>> getSupportedLanguages() {
        return mDataModel.getSupportedLanguages();
    }

    public void languageSelected(@NonNull final Language language) {
        mSelectedLanguage.onNext(language);
    }


}
