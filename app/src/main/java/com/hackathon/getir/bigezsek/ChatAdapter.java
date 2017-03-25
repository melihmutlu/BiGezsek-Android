package com.hackathon.getir.bigezsek;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by taha on 25/03/17.
 */

public class ChatAdapter extends ArrayAdapter<Message>{
    List<Message> m;
    private static LayoutInflater inflater = null;
    public ChatAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
        m = messages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = m.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.chat_item, null);

        TextView msg = (TextView) vi.findViewById(R.id.message_text);
        msg.setText(message.message);
        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);

        // if message is mine then align to right
        if (message.from.equals("1")) {
            parent_layout.setGravity(Gravity.RIGHT);
        }
        // If not mine then align to left
        else {
            parent_layout.setGravity(Gravity.LEFT);
        }
        msg.setTextColor(Color.BLACK);
        return vi;
    }

    final class ViewHolder {
        public TextView body;
    }


}
