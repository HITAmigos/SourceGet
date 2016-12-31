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
import android.os.StrictMode;
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
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.aspsine.multithreaddownload.demo.ui.activity.AppListActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import booksearch.activities.BookDetailActivity;
import booksearch.adapters.BookAdapter;
import booksearch.models.Book;
import booksearch.net.BookClient;
import hitamigos.alonepicture;
import hitamigos.picure.util.ImageLoader;
import hitamigos.picure.util.Search;
import hitamigos.sourceget.adapter.MyPagerAdapter;

import static hitamigos.sourceget.R.layout.result;


public class ResultActivity extends AppCompatActivity{
    private Intent intent;
    List<View> views=new ArrayList<>();
    private ListView listView=null;
    public static String message;
    public static final String BOOK_DETAIL_KEY = "book";
    public static final String EXTRA_PIC = "picture";
    private BookAdapter bookAdapter;
    private BookClient client;
    private ArrayList<String> list = new ArrayList<String>();
    public String liststr = new String();
    private GridView gridView;
    private String[] urlArray;
    private ImageLoader mImageLoader;
    private Handler UIHandler = new Handler();
    /*
    远程连接
     */
    private static  String processURL="http://192.168.134.1:8080/AndroidStruts2JSON/login.action?";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        liststr = "video,视频1,link1,视频2,link2,music,音乐1,link3,音乐2,link4";
        ///在Android2.2以后必须添加以下代码
        //本应用采用的Android4.0
        //设置线程的策略
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        //设置虚拟机的策略
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                //.detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        super.onCreate(savedInstanceState);
        setContentView(result);
        mImageLoader = ImageLoader.getInstance(3, ImageLoader.Type.LIFO);
        intent = getIntent();
        String mess = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        if(mess != null){
            message = mess;
        }
    //    GetData(message);
        String[] str = liststr.split("\\,");
        int boundary = 0;
        for(int i =0;i<str.length;i++){
            if(str[i].equals("music")) boundary = i;
        }
        /*
        视频
         */
            listView=new ListView(this);
            List<Map<String, Object>> list=new ArrayList<>();
            for (int j = 1; j < boundary; j++){
                if(str[j].equals("video")) j++;
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("image", R.drawable.midi);
                map.put("title", str[j]);
                map.put("info", str[++j]);
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
        /*
        音乐
         */
        listView=new ListView(this);
        List<Map<String, Object>> list1=new ArrayList<>();
        for (int j = boundary+1; j < str.length; j++) {
            if(str[j].equals("music")) j++;
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.midi);
            map.put("title", str[j]);
            map.put("info", str[++j]);
            list1.add(map);
        }
        //生成SimpleAdapter适配器对象
        SimpleAdapter myAdapter=new SimpleAdapter(this, list1,//数据源
                R.layout.list,//ListView内部数据展示形式的布局文件
                new String[]{"image","title","info"},//HashMap中的两个key值
                new int[]{R.id.image,R.id.title,R.id.info});/*布局文件
                                                            布局文件的各组件分别映射到HashMap的各元素上，完成适配*/
        listView.setAdapter(myAdapter);
        //添加点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
                //获得选中项的HashMap对象
                Map<String,Object>  map=(Map<String,Object>)listView.getItemAtPosition(arg2);
              //  String title =String.valueOf(map.get("title"));
               // System.out.println(title);
                Toast.makeText(getApplicationContext(),
                        "正在下载！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        views.add(listView);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.picture, null);
        gridView = (GridView) view.findViewById(R.id.id_gridview);
        Search search = new Search(message, UIHandler);
        urlArray = search.GetUrl().toString().split("\n");
        gridView.setAdapter(new ListImgItemAdapetr(ResultActivity.this, 0, urlArray));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(ResultActivity.this, alonepicture.class);
                intent.putExtra(EXTRA_PIC,urlArray[position]);
                startActivity(intent);
            }
        });
        views.add(view);
        listView=new ListView(this);
        ArrayList<Book> aBooks = new ArrayList<Book>();
        fetchBooks(message);
        // initialize the adapter
        bookAdapter = new BookAdapter(this, aBooks);
        // attach the adapter to the ListView
        listView.setAdapter(bookAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(ResultActivity.this, BookDetailActivity.class);
                intent.putExtra(BOOK_DETAIL_KEY, bookAdapter.getItem(position));
                startActivity(intent);
            }
        });
        views.add(listView);



        ViewPager viewPager= (ViewPager) findViewById(R.id.id_vp);
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(views);
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.id_tl);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }
//    public void GetData(String message){
//       // String result=null;
//        try {
//            //创建一个HttpClient对象
//            HttpClient httpclient = new DefaultHttpClient();
//            //远程登录URL
//            processURL=processURL+"message="+message;
//            Log.d("远程URL", processURL);
//            //创建HttpGet对象
//            HttpGet request=new HttpGet(processURL);
//            //请求信息类型MIME每种响应类型的输出（普通文本、html 和 XML，json）。允许的响应类型应当匹配资源类中生成的 MIME 类型
//            //资源类生成的 MIME 类型应当匹配一种可接受的 MIME 类型。如果生成的 MIME 类型和可接受的 MIME 类型不 匹配，那么将
//            //生成 com.sun.jersey.api.client.UniformInterfaceException。例如，将可接受的 MIME 类型设置为 text/xml，而将
//            //生成的 MIME 类型设置为 application/xml。将生成 UniformInterfaceException。
//            request.addHeader("Accept","text/json");
//            //获取响应的结果
//            HttpResponse response =httpclient.execute(request);
//            //获取HttpEntity
//            HttpEntity entity=response.getEntity();
//            //获取响应的结果信息
//            String json = EntityUtils.toString(entity,"UTF-8");
//            //JSON的解析过程
//            if(json!=null){
//                JSONObject jsonObject=new JSONObject(json);
//                liststr=jsonObject.get("message").toString();
//            }
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    // Executes an API call to the OpenLibrary search endpoint, parses the results
    // Converts them into an array of book objects and adds them to the adapter
    private void fetchBooks(String query) {
        // Show progress bar before making network request
        client = new BookClient();
        client.getBooks(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // hide progress bar
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("docs");
                        // Parse json array into array of model objects
                        final ArrayList<Book> books = Book.fromJson(docs);
                        // Remove all books from the adapter
                        bookAdapter.clear();
                        // Load model objects into the adapter
                        for (Book book : books) {
                            bookAdapter.add(book); // add book through the adapter
                        }
                        bookAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
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
            if (convertView == null){
                convertView = ResultActivity.this.getLayoutInflater().inflate(R.layout.item_fragment_list_imgs, parent, false);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.id_img);
            imageView.setImageResource(R.drawable.pictures_no);
            mImageLoader.loadImage(getItem(position), imageView, true);
            return convertView;
        }
    }
}
