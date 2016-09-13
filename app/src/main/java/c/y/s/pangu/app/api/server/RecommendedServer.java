package c.y.s.pangu.app.api.server;

import c.y.s.pangu.app.api.entry.RecommendedEntity;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/11.
 */
public interface RecommendedServer {

    @GET("/")
    Observable<String> getRecommended();

}
