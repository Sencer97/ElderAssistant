package com.example.shengchun.elderassistant.util.address;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shengchun.elderassistant.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Sencer
 * @create 2017/4/10
 * @vertion 1.0
 * @description
 */

public class ContactsActivity extends Activity{
    private ListView listView;
    private ArrayList<Contacts> list;
    private SideBar sideBar;

    SideBar.ISideBarSelectCallBack is = new SideBar.ISideBarSelectCallBack() {
        @Override
        public void onSelectStr(int index, String selectStr) {
            for (int i = 0; i < list.size(); i++) {
                if (selectStr.equalsIgnoreCase(list.get(i).getFirstLetter())) {
                    listView.setSelection(i);     // 选择到首字母出现的位置
                    return;
                }
            }
        }
    };
    ListView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ContactsActivity.this,ChatActivity.class);
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_fragment);
        initView();
        initData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_contacts);
        sideBar = (SideBar) findViewById(R.id.side_bar);
        sideBar.setOnStrSelectCallBack(is);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                AlertDialog dialog = new AlertDialog.Builder(ContactsActivity.this).setTitle("删除")
                        .setMessage("是否要删除该联系人？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);         //删除后更新数据
                                SortAdapter adapter = (SortAdapter) listView.getAdapter();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("否",null).show();
                return true;
            }
        });
        listView.setOnItemClickListener(itemClickListener);
    }

      private void initData(){
        list = new ArrayList<Contacts>();
        list.add(new Contacts("亳州")); // 亳[bó]属于不常见的二级汉字
        list.add(new Contacts("大娃"));
        list.add(new Contacts("二娃"));
        list.add(new Contacts("三娃"));
        list.add(new Contacts("四娃"));
        list.add(new Contacts("五娃"));
        list.add(new Contacts("六娃"));
        list.add(new Contacts("七娃"));
        list.add(new Contacts("喜羊羊"));
        list.add(new Contacts("美羊羊"));
        list.add(new Contacts("懒羊羊"));
        list.add(new Contacts("沸羊羊"));
        list.add(new Contacts("暖羊羊"));
        list.add(new Contacts("慢羊羊"));
        list.add(new Contacts("灰太狼"));
        list.add(new Contacts("红太狼"));
        list.add(new Contacts("孙悟空"));
        list.add(new Contacts("黑猫警长"));
        list.add(new Contacts("舒克"));
        list.add(new Contacts("贝塔"));
        list.add(new Contacts("海尔"));
        list.add(new Contacts("阿凡提"));
        list.add(new Contacts("邋遢大王"));
        list.add(new Contacts("哪吒"));
        list.add(new Contacts("没头脑"));
        list.add(new Contacts("不高兴"));
        list.add(new Contacts("蓝皮鼠"));
        list.add(new Contacts("大脸猫"));
        list.add(new Contacts("大头儿子"));
        list.add(new Contacts("小头爸爸"));
        list.add(new Contacts("蓝猫"));
        list.add(new Contacts("淘气"));
        list.add(new Contacts("叶峰"));
        list.add(new Contacts("楚天歌"));
        list.add(new Contacts("江流儿"));
        list.add(new Contacts("Tom"));
        list.add(new Contacts("Jerry"));
        list.add(new Contacts("12345"));
        list.add(new Contacts("54321"));
        list.add(new Contacts("_(:з」∠)_"));
        list.add(new Contacts("……%￥#￥%#"));
        Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
        SortAdapter adapter = new SortAdapter(this, list);
        listView.setAdapter(adapter);
        Log.d("TAG",list.size()+"个人");
    }

    /**
     * 返回按钮
     * */
    public void onback(View view) {
        finish();
    }
}
