package pxgd.hyena.com.gallery;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主活动加载的片段（保留片段）
 */
public class GalleryFragment extends Fragment {

    private static final String TAG = "GalleryFragment";

    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();
    private PhotoDownloader<PhotoHolder> mThumbnailDownloader;



    /**
     * 返会当前片段实例（托管活动调用）
     * @return
     */
    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }
    /**
     * 必需的空构造函数
     */
    public GalleryFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为可保留的片段
        setRetainInstance(true);
        //设置片段接收菜单回调
        setHasOptionsMenu(true);
        //调用线程方法（执行AsyncTask）
        updateItems();



        //启动线程并保证线程就续（设置事件处理）
        Handler responseHandler = new Handler();
        mThumbnailDownloader = new PhotoDownloader<>(responseHandler);
        mThumbnailDownloader.setThumbnailDownloadListener(
                new PhotoDownloader.ThumbnailDownloadListener<PhotoHolder>() {
                    @Override
                    public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        photoHolder.bindDrawable(drawable);
                    }
                }
        );
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //清除Handler所持有的Message
        mThumbnailDownloader.clearQueue();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //退出线程
        mThumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //查找回收视图元素并设置布局
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        mPhotoRecyclerView = v.findViewById(R.id.fragment_photo_recycler);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        //绑定回收视图与适配器（视图创建或被重建(设备旋转)时调用）
        setupAdapter();
        return v;
    }
    private void setupAdapter() {
        //判断片段是否已与托管活动相关联
        if (isAdded()) {
            //绑定回收视图与适配器
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }
    private void updateItems() {
        //读取存储的查询字串值
        String query = QueryPreferences.getStoredQuery(getActivity());
        //调用线程方法（会启动AsyncTask）
        new FetchItemsTask(query).execute("ouyj");
    }

    /**
     * 后台线程（内部类）
     */
    private class FetchItemsTask extends AsyncTask<String,Void,List<GalleryItem>> {

        private String mQuery;
        //带搜索字串的构造
        public FetchItemsTask(String query) {
            mQuery = query;
        }
        //后台线程中执行
        @Override
        protected List<GalleryItem> doInBackground(String... strings) {
            //判断是否为搜索
            if (mQuery == null)
                return new FlickrFetchr().fetchPhotos();
            else
                return new FlickrFetchr().searchPhotos(mQuery);

        }
        //接收后台线程方法返回的数据
        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            //获取完数据就立即更新回收视图的显示
            mItems = items;
            setupAdapter();
        }
    }

    /**
     * 定义ViewHolder内部类（在适配器中实例化并使用）
     */
    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView mItemImageView;

        //构造（查找视图元素）
        public PhotoHolder(View itemView) {
            super(itemView);
            mItemImageView = itemView.findViewById(R.id.photo_image_view);
        }
        //视图值的设置方法（适配器调用）
        public void bindDrawable(Drawable drawable) {
            mItemImageView.setImageDrawable(drawable);
        }
    }

    /**
     * 定义Adapter内部类
     */
    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> mGalleryItems;
        //构造函数
        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }
        //根据布局文件创建ViewHolder实例
        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //加载布局文件，传参并构造ViewHolder
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gallery_item, parent , false);
            return new PhotoHolder(view);
        }
        //加载每一项视图（如在此内按需下载图片）
        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            Drawable placeholder = getResources().getDrawable(R.drawable.dragon);
            holder.bindDrawable(placeholder);

            //调用线程下载方法（下载结果显示视图,Url）
            mThumbnailDownloader.queueThumbnail(holder, galleryItem.getUrl());
        }
        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }



    /**
     * 加载菜单资源文件（使工具栏出现溢出按钮）
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //加载菜单资源，并查找搜索框视图
        inflater.inflate(R.menu.menus, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //用户提交查询
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "QueryTextSubmit: " + s);
                //保存新的查询字串值
                QueryPreferences.setStoredQuery(getActivity(), s);
                //立即更新查询结果
                updateItems();
                return true;
            }
            //文本框文字发生变化
            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "QueryTextChange: " + s);
                return false;
            }
        });
        //用户点击搜索按钮展开搜索栏
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取已保存的查询字串
                String query = QueryPreferences.getStoredQuery(getActivity());
                searchView.setQuery(query, false);
            }
        });
    }
    /**
     * 点击菜单项
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                //清除存储的查询字串
                QueryPreferences.setStoredQuery(getActivity(), null);
                updateItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
