package c.y.s.pangu.app.api.retrofit;

import c.y.s.pangu.app.Constant;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/11.
 */
public class RetrofitImp {


    public static <T> T getServer(Class<T> tClass){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YOUKU_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(tClass);

    }
}
