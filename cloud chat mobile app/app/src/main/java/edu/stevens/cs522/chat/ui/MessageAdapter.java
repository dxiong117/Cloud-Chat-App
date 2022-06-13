package edu.stevens.cs522.chat.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs522.chat.R;
import edu.stevens.cs522.chat.entities.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final String TAG = MessageAdapter.class.getCanonicalName();

    private List<Message> messages;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView senderView;
        private final TextView messageView;

        public ViewHolder(View view) {
            super(view);

            senderView = (TextView) view.findViewById(R.id.sender);

            messageView = (TextView) view.findViewById(R.id.message);

        }

        public void setSender(String sender) {
            senderView.setText(sender);
        }

        public void setMessage(String message) {
            messageView.setText(message);
        }
    }

    /**
     * Initialize the dataset of the Adapter
     */
    public MessageAdapter() {
        this.messages = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // TODO set the fields of the view for the message
        Message data = messages.get(position);
        viewHolder.setMessage(data.messageText);
        viewHolder.setSender(data.sender + ":");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /*
     * Invoked by live data observer.
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
