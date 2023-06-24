package com.example.hatchatmobile1.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.R;

import java.util.List;

/**
 * Custom ArrayAdapter for displaying a list of contacts in a ListView.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater inflater;
    private int resourceId;

    // Set the desired diameter for circular images
    private int desiredDiameter = 180;

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
            // Convert the base64 string to a bitmap
            byte[] decodedBytes = Base64.decode(contact.getProfilePic(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            Bitmap circularBitmap = getCircleBitmap(decodedBitmap);
            // Set the bitmap to the ImageView
            viewHolder.contactImage.setImageBitmap(circularBitmap);
            viewHolder.username.setText(contact.getUsername());

            // Set the bio text
            viewHolder.bio.setText(contact.getBio());

            // Hide the bio TextView if the bio is empty or null
            if (TextUtils.isEmpty(contact.getBio())) {
                viewHolder.bio.setVisibility(View.GONE);
            } else {
                viewHolder.bio.setVisibility(View.VISIBLE);
            }
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

    /**
     * Converting an image to be rounded in and in to a fixed size.
     *
     * @param bitmap The image's bitmap.
     * @return The image rounded.
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        int diameter = desiredDiameter;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, diameter, diameter, false);
        Bitmap circularBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(circularBitmap);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, diameter, diameter);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(diameter / 2.0f, diameter / 2.0f, diameter / 2.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        int left = (diameter - scaledBitmap.getWidth()) / 2;
        int top = (diameter - scaledBitmap.getHeight()) / 2;
        canvas.drawBitmap(scaledBitmap, left, top, paint);

        return circularBitmap;
    }
}
