package c.y.s.pangu.app.recyleview;

/**
 * Created by Administrator on 2016/9/13.
 */

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
public class BaseHolder<T> extends RecyclerView.ViewHolder {


    public BaseHolder(int viewId, ViewGroup parent, int viewType) {
        super(((LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE)).inflate(viewId, parent,false));
    }

    public void refreshData(T data, int position) {

    }
}