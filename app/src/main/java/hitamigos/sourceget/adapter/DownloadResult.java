/*
 * Copyright (c) 2016.
 * 个人版权所有
 * kuangmeng.net
 */

package hitamigos.sourceget.adapter;

/**
 * Created by kuangmeng on 2016/12/26.
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import hitamigos.sourceget.data.Activity;
import hitamigos.sourceget.Download;
import hitamigos.sourceget.R;
import hitamigos.sourceget.data.DownloadUtil;

public class DownloadResult extends ArrayAdapter<Download> {
    private int resourceId;
    private static final String TAG = Activity.class.getSimpleName();
    private ProgressBar mProgressBar;
    private TextView total;
    private Button start;
    private Button pause;
    private int max;
    private DownloadUtil mDownloadUtil;
    public DownloadResult(Context context, int textViewResourceId, List<Download> objects) {
        super(context, textViewResourceId, objects);
// TODO Auto-generated constructor stub
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Download d = getItem(position);
        //初始话ListView的子项布局
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        start = (Button) view.findViewById(R.id.button_start);
        pause = (Button) view.findViewById(R.id.button_pause);
        TextView dtitle = (TextView) view.findViewById(R.id.downloadingtitle);
        dtitle.setText(d.getTitle());
        mProgressBar = (ProgressBar)view.findViewById(R.id.progressBar1);
        total = (TextView)view.findViewById(R.id.textView_total);
        String urlString = "http://bbra.cn/Uploadfiles/imgs/20110303/fengjin/013.jpg";
        String localPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/local";
        mDownloadUtil = new DownloadUtil(2, localPath, d.getTitle()+".jpg",urlString,this.getContext());
        mDownloadUtil.setOnDownloadListener(new DownloadUtil.OnDownloadListener(){
            @Override
            public void downloadStart(int fileSize){
                // TODO Auto-generated method stub
                Log.w(TAG, "fileSize::" + fileSize);
                max = fileSize;
                mProgressBar.setMax(fileSize);
            }
            @Override
            public void downloadProgress(int downloadedSize){
                // TODO Auto-generated method stub
                Log.w(TAG, "Compelete::" + downloadedSize);
                mProgressBar.setProgress(downloadedSize);
                total.setText((int) downloadedSize * 100 / max + "%");
            }
            @Override
            public void downloadEnd(){
                // TODO Auto-generated method stub
                Log.w(TAG, "ENd");
            }
        });
        mDownloadUtil.start();
        start.setOnClickListener(new AdapterView.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDownloadUtil.start();
			}
		});
		pause.setOnClickListener(new AdapterView.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDownloadUtil.pause();
			}
		});
        return view;
    }
}
