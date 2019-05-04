package pxgd.hyena.com.beatbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeatBoxFragment extends Fragment {

    private BeatBox mBeatBox;

    //由托管活动调用（加载本片段）
    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }





    public BeatBoxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //保留片段状态
        setRetainInstance(true);
        mBeatBox = new BeatBox(getActivity());
    }






    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //以网格的样式布局其内组件
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        //绑定适配器（声音资源文件的集合）
        recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        return view;
    }



    private class SoundHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        private Button mButton;
        private Sound mSound;

        public SoundHolder(LayoutInflater inflater, ViewGroup parent) {
            //声音按钮的布局（每一项的布局）
            super(inflater.inflate(R.layout.item_sound, parent, false));
            mButton =itemView.findViewById(R.id.button);
            mButton.setOnClickListener(this);
        }
        public void bindSound(Sound sound) {
            mSound = sound;
            mButton.setText(mSound.getName());
        }
        //实现按钮的点击事件处理
        @Override
        public void onClick(View v) {
            //播放当前声音
            mBeatBox.play(mSound);
        }
    }
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {

        private List<Sound> mSounds;
        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }
        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater, parent);
        }
        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bindSound(sound);
        }
        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }


    /**
     * 音频播放完毕，释放SoundPool
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

}



