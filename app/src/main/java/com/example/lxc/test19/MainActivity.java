package com.example.lxc.test19;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lxc.test19.adapter.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<DataBean> dataBeanList;
    private DataBean dataBean;
    private RecyclerAdapter mAdapter;
    StringBuffer stringBuffer;
    String[] content_text,place_name,comment_num,like_num,owner_id,notes_id,place_id,create_time,pic;
    int[] circle_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        OkhttpHelper okhttpHelper = new OkhttpHelper();
        stringBuffer = new StringBuffer();
        //网络连接要在子线程
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        dataBeanList = new ArrayList<>();






        try {

            //获取按照顺序的place_id
            String places_id = okhttpHelper.Okhttp_Get("http://172.16.243.163:9000/get_journey/1");
            //分割成数组存储
            String[] place_id = places_id.substring(1,places_id.length()-2).split(",");

            //循环获取二级列表对应的数据
            for(int i=0;i<place_id.length;i++){
                //输出测试
                System.out.println("sb_csx_placeid"+place_id[i]);
                //获取对应place_id的二级列表数据
                String result = okhttpHelper.Okhttp_Get("http://172.16.243.163:9000/journey_once/"+place_id[i]+"/1");
                //输出测试
                System.out.println("sb_csx_result"+result);
                if(!result.equals("0")) {

                    try {

                        //单独解析create_time
                        JSONObject jsonObject = new JSONObject(result);
                        //将/n替换
                        String a = jsonObject.getString("create_time").replaceAll("\n","");
                        //去除[]，并生成数组
                        create_time = a.substring(1,a.length()-1).split(",");
                        //将json数据保存到数组
                        comment_num = okhttpHelper.JsonArrays(result,"comment_num");
                        content_text = okhttpHelper.JsonArrays(result,"content_text");
                        like_num = okhttpHelper.JsonArrays(result,"like_num");
                        pic = okhttpHelper.JsonArrays(result,"pic");
                        place_name = okhttpHelper.JsonArrays(result,"place_name");
                        circle_type = okhttpHelper.JsonArrays_int(result,"type");

                        //测试各组数据
                        for (int j=0;j<comment_num.length;j++){
                            System.out.println("sb_csx_comment_num" + comment_num[j]+"length"+comment_num.length);
                            System.out.println("sb_csx_content_text" + content_text[j]+"length"+content_text.length);
                            System.out.println("sb_csx_create_time" + create_time[j]+"length"+create_time.length);
                            System.out.println("sb_csx_like_num" + like_num[j]+"length"+like_num.length);
                            System.out.println("sb_csx_pic" + pic[j]+"length"+pic.length);
                            System.out.println("sb_csx_place_name" + place_name[j]+"length"+place_name.length);
                            System.out.println("sb_csx_type" + circle_type[j]+"length"+circle_type.length);

                            dataBean = new DataBean();
                            dataBean.setID(i+"");
                            dataBean.setType(0);
                            dataBean.setParentLeftTxt("父--"+i);
                            dataBean.setParentRightTxt("父内容--"+i);
                            dataBean.setPlace_id(place_id[i]);

                            dataBean.setChildLeftTxt("子--"+j);
                            dataBean.setChildRightTxt("子内容--"+j);
                            dataBean.setComment_num(comment_num[j]);
                            dataBean.setContent_text(content_text[j]);
                            dataBean.setCreate_time(create_time[j]);
                            dataBean.setLike_num(like_num[j]);
                            dataBean.setPlace_name(place_name[j]);
                            dataBean.setCircle_type(circle_type[j]);
                            dataBean.setChildBean(dataBean);
                            dataBeanList.add(dataBean);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //拼接有数据的place_id
                    stringBuffer.append(place_id[i]+",");

                }




            }
            //测试拼接的字符串
            System.out.println("sb_csx"+stringBuffer.toString());
            String true_places_id = stringBuffer.toString();
            //将place_id数据添加到列表
            String[] true_place_id = true_places_id.substring(0,true_places_id.length()-1).split(",");
            for(int z = 0;z<true_place_id.length;z++){
                System.out.println("sb_csx"+true_place_id[z]);

            }




        } catch (IOException e) {
            e.printStackTrace();
        }












        setData();







    }

    private void setData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this,dataBeanList);
        mRecyclerView.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
    }

//    private TextView textView;
//    List<SortPlace> aData;
//    List<SortPlace> aData2;
//    SortPlaceAdapter sortPlaceAdapter;
//    SortPlaceAdapter2 sortPlaceAdapter2;
//    ListView list_place,list_data;
//    String[] content_text,place_name,comment_num,like_num,owner_id,notes_id,place_id,create_time,pic;
//    int[] type;
//    StringBuffer stringBuffer;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //初始化
//        textView = (TextView)findViewById(R.id.text);
//        OkhttpHelper okhttpHelper = new OkhttpHelper();
//        list_place = (ListView)findViewById(R.id.list_place);
//        list_data = (ListView)findViewById(R.id.list_data);
//        aData = new LinkedList<SortPlace>();
//        aData2 = new LinkedList<SortPlace>();
//        stringBuffer = new StringBuffer();
//        //网络连接要在子线程
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
//
//
//
//        try {
//
//            //获取按照顺序的place_id
//            String places_id = okhttpHelper.Okhttp_Get("http://172.16.243.163:9000/get_journey/1");
//            //分割成数组存储
//            String[] place_id = places_id.substring(1,places_id.length()-2).split(",");
//
//            //循环获取二级列表对应的数据
//            for(int i=0;i<place_id.length;i++){
//                //输出测试
//                System.out.println("sb_csx_placeid"+place_id[i]);
//                //获取对应place_id的二级列表数据
//                String result = okhttpHelper.Okhttp_Get("http://172.16.243.163:9000/journey_once/"+place_id[i]+"/1");
//                //输出测试
//                System.out.println("sb_csx_result"+result);
//                if(!result.equals("0")) {
//
//                    //拼接有数据的place_id
//                    stringBuffer.append(place_id[i]+",");
//
//                }
//
//
//
//
//            }
//            //测试拼接的字符串
//            System.out.println("sb_csx"+stringBuffer.toString());
//            String true_places_id = stringBuffer.toString();
//            //将place_id数据添加到列表
//            String[] true_place_id = true_places_id.substring(0,true_places_id.length()-1).split(",");
//            for(int z = 0;z<true_place_id.length;z++){
//                System.out.println("sb_csx"+true_place_id[z]);
//                aData.add(new SortPlace(true_place_id[z]));
//            }
//
//            //将数据放入适配器
//            sortPlaceAdapter = new SortPlaceAdapter((LinkedList<SortPlace>) aData,MainActivity.this);
//            //列表加入适配器
//            list_place.setAdapter(sortPlaceAdapter);
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//    }
}
