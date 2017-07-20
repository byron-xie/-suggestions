package suggestions.example.com.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import suggestions.example.com.R;
import suggestions.example.com.bean.CountItemViewHolder;
import suggestions.example.com.bean.Items;

/**
 * Created by root on 17-7-13.
 */

public class CountSectionAdapter extends SimpleSectionedAdapter<CountItemViewHolder> {

    private LayoutInflater layoutInflater;
    private Items mAlldata;
    private View.OnClickListener mOnclickListener;
    @Override
    protected int getItemCountForSection(int section) {
        return mAlldata.getItems().get(section).getContent().size();
    }

    @Override
    protected int getSectionCount() {
        return mAlldata.getItems().size();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    protected LayoutInflater getLayoutInflater(){
        return layoutInflater;
    }

    public CountSectionAdapter(LayoutInflater layoutInflater , Items alldata  ,View.OnClickListener mOnclickListener) {
        this.layoutInflater = layoutInflater;
        this.mAlldata = alldata;
        this.mOnclickListener = mOnclickListener;
    }





    @Override
    protected CountItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_count_item, parent, false);
        CountItemViewHolder countItemViewHolder = new CountItemViewHolder(view);
        view.setOnClickListener(mOnclickListener);
        return countItemViewHolder;
    }



    @Override
    protected void onBindItemViewHolder(CountItemViewHolder holder, int section, int position) {
        holder.render(mAlldata.getItems().get(section).getContent().get(position).getName(), R.drawable.framedraw);
    }


    @Override
    protected String getSectionHeaderTitle(int section) {
        return  mAlldata.getItems().get(section).getName();
    }


}
