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

/**
 * Custom ArrayAdapter for displaying a list of contacts in a ListView.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater inflater;
    private int resourceId;

    /**
     * Constructor for ContactListAdapter.
     *
     * @param context    The current context.
     * @param resourceId The resource ID for the layout file to be used.
     * @param contacts   The list of contacts to be displayed.
     */
    public ContactListAdapter(Context context, int resourceId, List<Contact> contacts) {
        super(context, resourceId, contacts);
        this.inflater = LayoutInflater.from(context);
        this.resourceId = resourceId;
    }

    /**
     * Overrides the getView method of ArrayAdapter to provide a custom view for each item in the list.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            // Inflate the layout for each item in the list
            view = inflater.inflate(resourceId, parent, false);

            // Create a ViewHolder to store references to the views
            viewHolder = new ViewHolder();
            viewHolder.contactImage = view.findViewById(R.id.contact_image);
            viewHolder.username = view.findViewById(R.id.usernameInList);
            viewHolder.bio = view.findViewById(R.id.bio);

            // Set the ViewHolder as a tag for the view
            view.setTag(viewHolder);
        } else {
            // If the view is already available, retrieve the ViewHolder from the tag
            viewHolder = (ViewHolder) view.getTag();
        }

        // Get the Contact object for the current position
        Contact contact = getItem(position);

        if (contact != null) {
            // Set the data from the Contact object to the views
            viewHolder.contactImage.setImageResource(contact.getProfilePic());
            viewHolder.username.setText(contact.getUsername());
            viewHolder.bio.setText(contact.getDisplayName());
        }

        return view;
    }

    /**
     * ViewHolder class for holding references to the views of each item in the list.
     */
    static class ViewHolder {
        ImageView contactImage;
        TextView username;
        TextView bio;
    }
}
