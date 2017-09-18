/*
 * Copyright 2017, Abhi Muktheeswarar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package msa.rehearsal.round1.subround1_2;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.processors.BehaviorProcessor;
import msa.rehearsal.round1.subround1_2.datamodel.IDataModel;
import msa.rehearsal.round1.subround1_2.model.Language;
import msa.rehearsal.round1.subround1_2.schedulers.ISchedulerProvider;

/**
 * Created by Abhimuktheeswarar on 31-05-2017.
 */

public class SubRound1_2ViewModel {

    @NonNull
    private final IDataModel mDataModel;

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    @NonNull
    private final BehaviorProcessor<Language> mSelectedLanguage = BehaviorProcessor.create();

    SubRound1_2ViewModel(@NonNull IDataModel mDataModel, @NonNull ISchedulerProvider mSchedulerProvider) {
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
