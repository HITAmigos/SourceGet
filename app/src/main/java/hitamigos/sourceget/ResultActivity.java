/*
 * Copyright (c) 2016.
 * 个人版权所有
 * kuangmeng.net
 */

package hitamigos.sourceget;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hitamigos.sourceget.adapter.MyPagerAdapter;


public class ResultActivity extends AppCompatActivity{
    private Intent intent;
    List<View> views=new ArrayList<>();
    private ListView listView=null;
    public static final String PAR_KEY = "par_key";
    public static final String PAR_TYPE = "par_type";
    public static String message;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        intent = getIntent();
        String mess = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        if(mess != null){
            message = mess;
        }
        for (int i=0;i<2;i++){
            listView=new ListView(this);
            List<Map<String, Object>> list=new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("image", R.drawable.midi);
                map.put("title", message);
                map.put("info", "这是一个详细信息"+j);
                list.add(map);
            }
            //生成SimpleAdapter适配器对象
            SimpleAdapter mySimpleAdapter=new SimpleAdapter(this, list,//数据源
                    R.layout.list,//ListView内部数据展示形式的布局文件
                    new String[]{"image","title","info"},//HashMap中的两个key值
                    new int[]{R.id.image,R.id.title,R.id.info});/*布局文件
                                                            布局文件的各组件分别映射到HashMap的各元素上，完成适配*/
            listView.setAdapter(mySimpleAdapter);
            //添加点击事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
                    //获得选中项的HashMap对象
                    Map<String,Object>  map=(Map<String,Object>)listView.getItemAtPosition(arg2);
                    String title =String.valueOf(map.get("title"));
                    System.out.println(title);
                    Toast.makeText(getApplicationContext(),
                            "正在下载"+title+"！",
                            Toast.LENGTH_SHORT).show();
                }
            });
             views.add(listView);
        }

        ViewPager viewPager= (ViewPager) findViewById(R.id.id_vp);
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(views);
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.id_tl);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void Jump(){
        Intent intent = new Intent(this, DownloadResultActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.result_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // TODO Auto-generated method stub
        switch(item.getItemId()){
            case R.id.download:
                Toast.makeText(ResultActivity.this, ""+"正在前往下载页！", Toast.LENGTH_SHORT).show();
                Jump();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
