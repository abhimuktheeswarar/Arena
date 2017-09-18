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

package msa.rehearsal.round1.subround1_1;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.processors.BehaviorProcessor;

/**
 * Created by Abhimuktheeswarar on 31-05-2017.
 */

public class SubRound1_1ViewModel {

    private BehaviorProcessor<String> behaviorProcessor = BehaviorProcessor.create();

    private String msg;

    Observable<String> getMsg() {
        return behaviorProcessor.map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String msg) throws Exception {
                return msg + "_" + msg.length();
            }
        }).toObservable();
    }

    void setMsg(String msg) {
        this.msg = msg;
        behaviorProcessor.onNext(msg);
    }


}
