package c.y.s.pangu.app.recyleview;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import c.y.s.pangu.app.R;
import c.y.s.pangu.app.api.entry.VideoItem;

/**
 * Created by Administrator on 2016/9/13.
 */
public class RecommendAdapter2 extends RecyclerView.Adapter<BaseHolder> {

    private List<VideoItem> mData;
    public RecommendAdapter2(List<VideoItem> mData){
        this.mData = mData;
    }
    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendedHolder(R.layout.item_recommended_video_item2,parent,viewType);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        ((RecommendedHolder)holder).refreshData(mData,position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class RecommendedHolder extends BaseHolder<List<VideoItem>>{

        private TextView tvName;
        public RecommendedHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            tvName = (TextView) itemView.findViewById(R.id.textView);
        }

        @Override
        public void refreshData(List<VideoItem> data, int position) {
            super.refreshData(data, position);
            tvName.setText(data.get(position).itemDesc);
        }
    }
}
