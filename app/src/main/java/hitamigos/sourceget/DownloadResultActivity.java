/*
 * Copyright (c) 2016.
 * 个人版权所有
 * kuangmeng.net
 */

package hitamigos.sourceget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hitamigos.sourceget.adapter.DownloadAdapter;
import hitamigos.sourceget.adapter.DownloadResult;


public class DownloadResultActivity extends AppCompatActivity {
    List<View> views=new ArrayList<>();
    private ListView listView=null;
    private List<Download> Downloadlist=new ArrayList<Download>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadresult);

            listView = new ListView(this);
//            List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
//            for (int j = 0; j < 10; j++){
//                Map<String, Object> map=new HashMap<String, Object>();
//                map.put("image", R.drawable.midi);
//                map.put("title", j);
//                list.add(map);
//
//            }
//            //生成SimpleAdapter适配器对象
//            SimpleAdapter mySimpleAdapter=new SimpleAdapter(this, list,//数据源
//                    R.layout.downloadinglist,//ListView内部数据展示形式的布局文件
//                    new String[]{"image","title"},//HashMap中的两个key值
//                    new int[]{R.id.downloadingimage,R.id.downloadingtitle}
//            );/*布局文件listitem.xml中组件的id
//                                                          布局文件的各组件分别映射到HashMap的各元素上，完成适配*/
//            listView.setAdapter(mySimpleAdapter);
//            //添加点击事件
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
//                    //获得选中项的HashMap对象
//                    Map<String,Object>  map=(Map<String,Object>)listView.getItemAtPosition(arg2);
//                    String title=String.valueOf(map.get("title"));
//                    Toast.makeText(getApplicationContext(),
//                            "取消下载"+title+"！",
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
           initDownloadlist();
        DownloadResult dr = new DownloadResult(DownloadResultActivity.this,R.layout.downloadinglist, Downloadlist);
        listView.setAdapter(dr);
        views.add(listView);
        /*
        已下载页面
         */
        listView = new ListView(this);
        List<Map<String, Object>> lists=new ArrayList<Map<String,Object>>();
        for (int j = 0; j < 100; j++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.midi);
            map.put("title", j);
            lists.add(map);
        }
        //生成SimpleAdapter适配器对象
        SimpleAdapter myimpleAdapter=new SimpleAdapter(this, lists,//数据源
                R.layout.downloadlist,//ListView内部数据展示形式的布局文件listitem.xml
                new String[]{"image","title"},//HashMap中的两个key值 itemTitle和itemContent
                new int[]{R.id.downloadimage,R.id.downloadtitle});/*布局文件listitem.xml中组件的id
                                                          布局文件的各组件分别映射到HashMap的各元素上，完成适配*/
        listView.setAdapter(myimpleAdapter);
        //添加点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
                //获得选中项的HashMap对象
                Map<String,Object>  map=(Map<String,Object>)listView.getItemAtPosition(arg2);
                String title=String.valueOf(map.get("title"));
                Toast.makeText(getApplicationContext(),
                        "正在打开"+title+"……",
                        Toast.LENGTH_SHORT).show();
            }
        });
        views.add(listView);
        ViewPager viewPager= (ViewPager) findViewById(R.id.id_vpp);
        DownloadAdapter downloadAdapter=new DownloadAdapter(views);
        viewPager.setAdapter(downloadAdapter);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.id_tll);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }
    public void initDownloadlist(){
        for(int i =0;i<10;i++){
            Download d =new Download("标题"+i,"http://bbra.cn/Uploadfiles/imgs/20110303/fengjin/013.jpg");
            Downloadlist.add(d);
        }
    }
}