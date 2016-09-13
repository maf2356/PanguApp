package c.y.s.pangu.app;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import c.y.s.pangu.app.api.PanguApi;
import c.y.s.pangu.app.api.entry.RecommendedEntity;
import c.y.s.pangu.app.api.entry.VideoItem;
import c.y.s.pangu.app.api.retrofit.RetrofitImp;
import c.y.s.pangu.app.api.server.RecommendedServer;
import c.y.s.pangu.app.databinding.ActivityMainBinding;
import c.y.s.pangu.app.databinding.ItemRecommendedVideoItemBinding;
import c.y.s.pangu.app.model.VModel;
import c.y.s.pangu.app.recyleview.FullyLinearLayoutManager;
import c.y.s.pangu.app.recyleview.RecommendAdapter;
import c.y.s.pangu.app.recyleview.RecommendAdapter2;
import c.y.s.pangu.app.recyleview.WrappingLinearLayoutManager;
import c.y.s.pangu.app.utils.PanguImageViewLoader;
import c.y.s.pangu.app.utils.ParseUtils;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private ActivityMainBinding mBinding;

    private VModel mVModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.mainAppbar.addOnOffsetChangedListener(this);
        mBinding.mainToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(mBinding.mainTvTitle, 0, View.INVISIBLE);
        mVModel = new VModel("1","2","3","4");
        mBinding.setModel(mVModel);
        mBinding.mainToolbar.setAlpha(0f);
//        intRecommended();
        FullyLinearLayoutManager mFullyLinearLayoutManager = new FullyLinearLayoutManager(this, LinearLayout.VERTICAL,true);
        mFullyLinearLayoutManager.setSmoothScrollbarEnabled(true);
//        mBinding.recyclerView.setLayoutManager(new WrappingLinearLayoutManager(this));
//        mBinding.recyclerView.setNestedScrollingEnabled(false);
//        mBinding.recyclerView.setHasFixedSize(false);
        jsoup();
    }


    void jsoup(){
        PanguApi.getRecommended(new Observable.OnSubscribe<RecommendedEntity>() {
            @Override
            public void call(Subscriber<? super RecommendedEntity> subscriber) {
                List<RecommendedEntity> recommendedEntities = ParseUtils.parseYoukuRecommended();
                subscriber.onNext(recommendedEntities.get(0));
                subscriber.onCompleted();
            }
        }, new Subscriber<RecommendedEntity>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(RecommendedEntity recommendedEntity) {
                PanguImageViewLoader.getInstance().display(MainActivity.this,mBinding.mainPlaceholder,recommendedEntity.img);
//                mBinding.recyclerView.setAdapter(new RecommendAdapter(recommendedEntity.videoItems));
                mBinding.videosLayout.removeAllViews();
                for (VideoItem item:recommendedEntity.videoItems
                     ) {
                    ItemRecommendedVideoItemBinding binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_recommended_video_item,null,false);
                    binding.setItem(item);
                    mBinding.videosLayout.addView(binding.getRoot());
                }
                mVModel.setProfiles(recommendedEntity.profiles);
                mVModel.setPublisherName(recommendedEntity.publisherName);
            }
        });

    }

/*
    void intRecommended(){
        RecommendedServer server = RetrofitImp.getServer(RecommendedServer.class);
        server.getRecommended()
                .map(new Func1<String, RecommendedEntity>() {
                    @Override
                    public RecommendedEntity call(String s) {
                        return ParseUtils.parseRecommended(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecommendedEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("rx_android",e.getMessage());
                    }

                    @Override
                    public void onNext(RecommendedEntity recommendedEntity) {
                        PanguImageViewLoader.getInstance().display(MainActivity.this,mBinding.mainPlaceholder,recommendedEntity.img);

                    }
                })

                ;
    }*/


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        mBinding.mainToolbar.setAlpha(percentage);
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {

        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            mVModel.setShowLogo(true);
            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mBinding.mainTvTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mBinding.mainTvTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
            mVModel.setShowLogo(false);
        }
    }

    private void handleAlphaOnTitle(float percentage) {

        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mBinding.mainLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mBinding.mainLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);

    }

}
