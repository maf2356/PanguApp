package c.y.s.pangu.app.api;

import java.util.List;

import c.y.s.pangu.app.api.entry.RecommendedEntity;
import c.y.s.pangu.app.utils.PanguImageViewLoader;
import c.y.s.pangu.app.utils.ParseUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/11.
 */
public class PanguApi {


    private static<T> Observable<T> creatObservable(Observable.OnSubscribe<T> onSubscribe,Subscriber<T> subscriber){
        Observable<T>  observable =  Observable.create(onSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(subscriber);
        return observable;

    }

    public static Observable getRecommended(Observable.OnSubscribe<RecommendedEntity> onSubscribe,Subscriber<RecommendedEntity> subscriber){
        return creatObservable(onSubscribe,subscriber);
    }


}
