package msa.rehearsal.round1;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.processors.BehaviorProcessor;

/**
 * Created by Abhimuktheeswarar on 31-05-2017.
 */

public class Round1ViewModel {

    private BehaviorProcessor<String> behaviorProcessor = BehaviorProcessor.create();

    private String msg;

    private int i;

    Observable<String> getMsg() {
        return behaviorProcessor.map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String msg) throws Exception {
                return msg + "_" + (i++);
            }
        }).toObservable();
    }

    void setMsg(String msg) {
        this.msg = msg;
        behaviorProcessor.onNext(msg);
    }


}
