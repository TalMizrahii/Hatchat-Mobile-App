package com.example.hatchatmobile1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hatchatmobile1.DaoRelated.Message;
import com.example.hatchatmobile1.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;
    private String contactUsername;

    public MessageAdapter(List<Message> messages, String contactUsername) {
        this.messages = messages;
        this.contactUsername = contactUsername;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            // Inflate the layout for sending messages.
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_message, parent, false);
        } else {
            // Inflate the layout for receiving messages.
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_message, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageTextView.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        // Check if the message sender is the contact or the current user.
        return message.getSender().equals(contactUsername) ? 1 : 0;
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.text_message_content);
        }
    }
}
