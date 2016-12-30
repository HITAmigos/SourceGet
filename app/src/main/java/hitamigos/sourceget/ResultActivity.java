/*
 * Copyright (c) 2016.
 * 个人版权所有
 * kuangmeng.net
 */

package hitamigos.sourceget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.aspsine.multithreaddownload.demo.ui.activity.AppListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import booksearch.adapters.BookAdapter;
import booksearch.net.BookClient;
import hitamigos.picure.util.ImageLoader;
import hitamigos.picure.util.Search;
import hitamigos.sourceget.adapter.MyPagerAdapter;


public class ResultActivity extends AppCompatActivity{
    private Intent intent;
    List<View> views=new ArrayList<>();
    private ListView listView=null;
    public static String message;
    public static final String BOOK_DETAIL_KEY = "book";
    private ListView lvBooks;
    private BookAdapter bookAdapter;
    private BookClient client;
    private ProgressBar progress;
    private ArrayList<String> list = new ArrayList<String>();

    private GridView gridView;
    private String[] urlArray;
    private ImageLoader mImageLoader;
    private Handler UIHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        mImageLoader = ImageLoader.getInstance(3, ImageLoader.Type.LIFO);
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
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.picture, null);
        gridView = (GridView) view.findViewById(R.id.id_gridview);
        Search search = new Search(message, UIHandler);
        urlArray = search.GetUrl().toString().split("\n");
        gridView.setAdapter(new ListImgItemAdapetr(ResultActivity.this, 0, urlArray));
        views.add(view);
        ViewPager viewPager= (ViewPager) findViewById(R.id.id_vp);
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(views);
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.id_tl);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void Jump(){
        Intent in = new Intent(this, AppListActivity.class);
        in.putExtra("EXTRA_TYPE", AppListActivity.TYPE.TYPE_LISTVIEW);
        startActivity(in);
        System.out.println("前往下载页");
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

/*
  图片
 */

    private class ListImgItemAdapetr extends ArrayAdapter<String> {
        public ListImgItemAdapetr(Context context, int resouce, String[] datas) {
            super(ResultActivity.this, 0, datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = ResultActivity.this.getLayoutInflater().inflate(R.layout.item_fragment_list_imgs, parent, false);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.id_img);
            imageView.setImageResource(R.drawable.pictures_no);
            mImageLoader.loadImage(getItem(position), imageView, true);
            return convertView;
        }
    }
}
