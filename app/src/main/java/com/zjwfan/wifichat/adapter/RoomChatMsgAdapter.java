package com.zjwfan.wifichat.adapter;

/**
 * Created by zjw on 2017-11-13.
 */

import android.widget.BaseAdapter;

import com.zjwfan.wifichat.R;
import com.zjwfan.wifichat.WifiChatApplication;
import com.zjwfan.wifichat.model.UDPMessage;
import com.zjwfan.wifichat.model.ViewHolder;
import com.zjwfan.wifichat.ui.SettingsActivity;
import com.zjwfan.wifichat.util.LocalMemoryCache;
import com.zjwfan.wifichat.util.Util;
import com.zjwfan.wifichat.util.WinChatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class RoomChatMsgAdapter extends BaseAdapter {

    private List<UDPMessage> myMessages;
    private Context context;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.CHINA);

    public RoomChatMsgAdapter(Context context, List<UDPMessage> myMessages) {
        this.context = context;
        this.myMessages = myMessages;
    }

    @Override
    public int getCount() {
        if (myMessages != null)
            return myMessages.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                    R.layout.chat_other_listview_item, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.chat_other_listview_item_img);
            holder.txt = (TextView) convertView.findViewById(R.id.chat_other_listview_item_txt);
            holder.time = (TextView) convertView.findViewById(R.id.chat_other_listview_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            UDPMessage message = myMessages.get(position);
            Bitmap bitmap;
            if (message.getDeviceCode().equals(WifiChatApplication.mainInstance.getDeviceCode()))
                bitmap = LocalMemoryCache.getInstance().get(SettingsActivity.iconName);// 获取自己的头像
            else
                bitmap = LocalMemoryCache.getInstance().get(message.getDeviceCode());
            if (bitmap != null)
                holder.icon.setImageBitmap(Util.getRoundedCornerBitmap(bitmap));
            else
                holder.icon.setImageResource(R.drawable.ic_launcher);
            String zhengze = "f0[0-9]{2}|f10[0-7]"; // 正则表达式，用来判断消息内是否有表情
            SpannableString spannableString = WinChatUtil.getExpressionString(context, message.getMsg(), zhengze);
            holder.txt.setText(spannableString);
            holder.time.setText(format.format(new Date(Long.valueOf(message.getSendTime()))));
        } catch (Exception e) {
            e.printStackTrace();
            holder.txt.setText(myMessages.get(position).getMsg());
        }
        return convertView;
    }
}


