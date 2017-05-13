package com.example.shengchun.elderassistant.util.address;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.shengchun.elderassistant.R;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collections;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author Sencer
 * @create 2017/4/10
 * @vertion 1.0
 * @description
 */

public class ContactsActivity extends Activity {
    private ListView listView;
    private ArrayList<Contacts> list;
    private SideBar sideBar;
    private FloatingActionButton addBtn;
    private EditText name;
    private EditText phone;
    private SortAdapter adapter;
    private LinearLayout layout;

    //数据库操作
    private ContactsDataHelper helper;
    private SQLiteDatabase db;

    private final String CALL_ACTION = "android.intent.action.CALL";

    private GoogleApiClient client;

    private void initView() {
        //初始化控件
        listView = (ListView) findViewById(R.id.lv_contacts);
        sideBar = (SideBar) findViewById(R.id.side_bar);
        addBtn = (FloatingActionButton) findViewById(R.id.add_contact);
        sideBar.setOnStrSelectCallBack(is);

        /**
         *  长按联系人删除
         **/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                Contacts person = list.get(position);
                final String name = person.getName();
                SweetAlertDialog dialog = new SweetAlertDialog(ContactsActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText("删除")
                        .setContentText("确定要删除联系人： " + name + " ？")
                        .setConfirmText("是的")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.setContentText("删除成功!")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(null)
                                        .setCancelClickListener(null)
                                        .showCancelButton(false)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                /**
                                 * //从数据库中删除该联系人，同时更新listView
                                 */
                                db.delete(ContactsDataHelper.TABLENAME, "name=?", new String[]{name});
                                list.remove(position);
                                SortAdapter adapter = (SortAdapter) listView.getAdapter();
                                adapter.notifyDataSetChanged();
                            }
                        });
                dialog.show();
                return true;
            }
        });
        listView.setOnItemClickListener(itemClickListener);
    }

        //回调侧边导航栏导航
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

    /**
     * 单击联系人 弹出选择对话框  打电话还是发短信
     */
    ListView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //获取被点击联系人的信息
            Contacts person = list.get(position);
            final String name = person.getName();
            final String phone = person.getPhoneNum();
            // 聊天
            // Intent intent = new Intent(ContactsActivity.this,ChatActivity.class);
            // startActivity(intent);
            final int p = position;
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.popup_dialog, null);
            final PopupWindow popWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popWindow.setBackgroundDrawable(new ColorDrawable(0));
            //设置动画
            popWindow.setAnimationStyle(R.style.popwin_anim_style);
            //设置popupwindow的位置
            popWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            //设置背景半透明
            backgroundAlpha(0.6f);
            //点击空白位置，popupwindow消失的事件监听，这时候让背景恢复正常
            popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f);
                }
            });
            TextView title = (TextView) layout.findViewById(R.id.pop_title);
            title.setText("给联系人："+name);
            ((Button) layout.findViewById(R.id.pop_confirm)).setVisibility(View.GONE);
            ((Button) layout.findViewById(R.id.pop_cancel)).setVisibility(View.GONE);
            ((View)layout.findViewById(R.id.line)).setVisibility(View.VISIBLE);
            Button call = (Button) layout.findViewById(R.id.pop_call);
            Button sendmsg = (Button) layout.findViewById(R.id.pop_send_msg);

            call.setVisibility(View.VISIBLE);
            sendmsg.setVisibility(View.VISIBLE);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.pop_call:
                            Intent i = new Intent(CALL_ACTION, Uri.parse("tel:" +phone));
                            startActivity(i);
                            popWindow.dismiss();
                            break;
                        case R.id.pop_send_msg:
                            //Toast.makeText(getBaseContext(),"发短信给"+name,Toast.LENGTH_SHORT).show();
                            //系统默认的action，用来打开默认的短信界面
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SENDTO);
                            //需要发短息的号码,电话号码之间用“;”隔开
                            intent.setData(Uri.parse("smsto:"+phone));
                            intent.putExtra("sms_body", "这是短信内容。");
                            startActivity(intent);
                            popWindow.dismiss();
                            break;
                    }
                }
            };
            call.setOnClickListener(listener);
            sendmsg.setOnClickListener(listener);

        }

        /**
         * 设置屏幕的背景透明度 1表示不透明，0表示全透明
         * @param bgAlpha
         */
        public void backgroundAlpha(float bgAlpha) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = bgAlpha; // 0.0-1.0
            getWindow().setAttributes(lp);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_fragment);
        initView();
        initData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initData() {
        list = new ArrayList<Contacts>();

        //数据库操作
        helper = new ContactsDataHelper(getBaseContext());
        db = helper.getWritableDatabase();   //插入数据用write
        Cursor cursor = db.rawQuery("select *from " + ContactsDataHelper.TABLENAME, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsDataHelper.NAME));
            String num = cursor.getString(cursor.getColumnIndex(ContactsDataHelper.PHONE_NUM));
            list.add(new Contacts(name, num));
        }
        adapter = new SortAdapter(this, list);
        Collections.sort(list);
        listView.setAdapter(adapter);
        /**
         *  添加联系人
         */
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout = (LinearLayout) getLayoutInflater().inflate(R.layout.add_person, null);
                name = (EditText) layout.findViewById(R.id.name);
                phone = (EditText) layout.findViewById(R.id.phone);
                AlertDialog.Builder addDialog = new AlertDialog.Builder(ContactsActivity.this).setTitle("添加联系人")
                        .setView(layout)
                        .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String user = name.getText().toString();
                                String phoneNum = phone.getText().toString();

                                Log.i("sencer", "onClick: " + user);
                                Log.i("sencer", "onClick: " + phoneNum);
                                if (!user.equals("") && !phoneNum.equals("")) {
                                    list.add(new Contacts(user, phoneNum));
                                    adapter.notifyDataSetChanged();
                                    Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
                                    //将联系人插入数据库中
                                    ContentValues values = new ContentValues();
                                    values.put(ContactsDataHelper.NAME, user);
                                    values.put(ContactsDataHelper.PHONE_NUM, phoneNum);
                                    db.insert(ContactsDataHelper.TABLENAME, null, values);
                                }

                            }
                        });
                addDialog.show();
            }
        });
    }

    /**
     * 返回按钮
     */
    public void onback(View view) {
        finish();
    }

}
