package com.example.shengchun.elderassistant.util.address;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.shengchun.elderassistant.R;

import java.util.List;

/**
 * @author Sencer
 * @create 2017/3/24
 * @vertion 1.0
 * @description
 */

public class SortAdapter extends BaseAdapter {

    private List<Contacts> list = null;
    private Context mContext;
    private ColorGenerator generator = ColorGenerator.MATERIAL;      //颜色生成器
    private String[] userInfo = {ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
  //  Cursor curContacts = getContentResolver().query(userInfo, null,null,null);

    public SortAdapter(Context mContext, List<Contacts> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;

        final Contacts user = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.contact_item, null);
            viewHolder.name = (TextView) view.findViewById(R.id.tv_user);
            viewHolder.catalog = (TextView) view.findViewById(R.id.tv_catalog);
            viewHolder.thumb = (ImageView) view.findViewById(R.id.iv_thumb);
            viewHolder.phoneNum = (TextView) view.findViewById(R.id.phoneNUm);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取首字母作为目录catalog
        String catalog = list.get(position).getFirstLetter();

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现  
        if(position == getPositionForSection(catalog)){
            viewHolder.catalog.setVisibility(View.VISIBLE);
            viewHolder.catalog.setText(user.getFirstLetter().toUpperCase());
        }else{
            viewHolder.catalog.setVisibility(View.GONE);
        }

        viewHolder.name.setText(this.list.get(position).getName());
        viewHolder.phoneNum.setText(this.list.get(position).getPhoneNum());
        int color = generator.getRandomColor();
        TextDrawable.IBuilder builder = TextDrawable.builder().beginConfig().endConfig().round();
        TextDrawable icon = builder.build(viewHolder.name.getText().toString().substring(0,1),color);
        viewHolder.thumb.setImageDrawable(icon);
        return view;

    }

    final static class ViewHolder {
        ImageView thumb;
        TextView catalog;
        TextView name;
        TextView phoneNum;
    }

    /**
     * 获取catalog首次出现位置 
     */
    public int getPositionForSection(String catalog) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getFirstLetter();
            if (catalog.equalsIgnoreCase(sortStr)) {
                return i;
            }
        }
        return -1;
    }
}

