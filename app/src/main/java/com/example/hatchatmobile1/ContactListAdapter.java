package com.example.hatchatmobile1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.hatchatmobile1.DaoRelated.Contact;

import java.util.List;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater inflater;
    private int resourceId;

    public ContactListAdapter(Context context, int resourceId, List<Contact> contacts) {
        super(context, resourceId, contacts);
        this.inflater = LayoutInflater.from(context);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contactImage = view.findViewById(R.id.contact_image);
            viewHolder.username = view.findViewById(R.id.usernameInList);
            viewHolder.bio = view.findViewById(R.id.bio);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Contact contact = getItem(position);

        if (contact != null) {
            viewHolder.contactImage.setImageResource(contact.getProfilePic());
            viewHolder.username.setText(contact.getUsername());
            viewHolder.bio.setText(contact.getDisplayName());
        }

        return view;
    }

    static class ViewHolder {
        ImageView contactImage;
        TextView username;
        TextView bio;
    }
}