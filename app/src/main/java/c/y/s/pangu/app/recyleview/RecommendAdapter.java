package c.y.s.pangu.app.recyleview;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import c.y.s.pangu.app.R;
import c.y.s.pangu.app.api.entry.VideoItem;
import c.y.s.pangu.app.databinding.ItemRecommendedVideoItemBinding;

/**
 * Created by Administrator on 2016/9/13.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendedHolder> {

    private List<VideoItem> mData;
    public RecommendAdapter(List<VideoItem> mData){
        this.mData = mData;
    }
    @Override
    public RecommendedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecommendedVideoItemBinding inflate = DataBindingUtil.inflate(inflater, R.layout.item_recommended_video_item, parent, false);
        return new RecommendedHolder(inflate.getRoot(),inflate);
    }

    @Override
    public void onBindViewHolder(RecommendedHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

     static class RecommendedHolder extends RecyclerView.ViewHolder{
         private ItemRecommendedVideoItemBinding mBind;
        public RecommendedHolder(View itemView,ItemRecommendedVideoItemBinding bind) {
            super(itemView);
            mBind = bind;
        }

         @UiThread
         public void bindData(VideoItem item){
            mBind.setItem(item);
         }
    }

}
