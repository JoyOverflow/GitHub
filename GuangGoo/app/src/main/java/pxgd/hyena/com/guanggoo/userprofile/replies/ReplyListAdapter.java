package pxgd.hyena.com.guanggoo.userprofile.replies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pxgd.hyena.com.guanggoo.R;
import pxgd.hyena.com.guanggoo.base.FragmentCallBack;
import pxgd.hyena.com.guanggoo.data.entity.Reply;
import pxgd.hyena.com.guanggoo.util.MyHtmlHttpImageGetter;

/**
 * @author mazhuang
 */
public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.ViewHolder> {

    private List<Reply> mData;
    private final FragmentCallBack mListener;

    public ReplyListAdapter(FragmentCallBack listener) {
        mListener = listener;
    }

    public void setData(List<Reply> data) {
        if (data == mData) {
            return;
        }

        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<Reply> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reply_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mData.get(position);
        holder.mTitleTextView.setText(mData.get(position).getTopic().getTitle());
        MyHtmlHttpImageGetter imageGetter = new MyHtmlHttpImageGetter(holder.mContentTextView);
        imageGetter.enableCompressImage(true, 30);
        holder.mContentTextView.setHtml(holder.mItem.getContent(), imageGetter);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public boolean isFilled() {
        return (mData != null && mData.size() > 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.title) TextView mTitleTextView;
        @BindView(R.id.content) HtmlTextView mContentTextView;
        public Reply mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.title})
        public void onClick(View v) {
            if (mListener == null) {
                return;
            }

            switch (v.getId()) {
                case R.id.title:
                    mListener.openPage(mItem.getTopic().getUrl(), mItem.getTopic().getTitle());
                    break;

                default:
                    break;
            }
        }
    }
}
