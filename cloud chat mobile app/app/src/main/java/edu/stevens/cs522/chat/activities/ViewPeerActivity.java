package edu.stevens.cs522.chat.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.stevens.cs522.chat.R;
import edu.stevens.cs522.chat.databases.ChatDatabase;
import edu.stevens.cs522.chat.entities.Message;
import edu.stevens.cs522.chat.entities.Peer;
import edu.stevens.cs522.chat.ui.TextAdapter;
import edu.stevens.cs522.chat.viewmodels.PeerViewModel;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends FragmentActivity {

    public static final String TAG = ViewPeerActivity.class.getCanonicalName();

    public static final String PEER_KEY = "peer";

    private PeerViewModel peerViewModel;

    private LiveData<List<Message>> messages;

    private TextAdapter<Message> messageAdapter;

    private RecyclerView messageList;

    private Peer peer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        peer = getIntent().getParcelableExtra(PEER_KEY);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer id as intent extra");
        }

        // TODO init the UI
        TextView tUser = (TextView)findViewById(R.id.view_user_name);
        tUser.setText(peer.name);
        TextView tTime = (TextView)findViewById(R.id.view_timestamp);
        tTime.setText(peer.timestamp.toString());
        TextView tAdder = (TextView)findViewById(R.id.view_address);
        tAdder.setText("LAT: "+peer.latitude.toString()+" LONG:"+peer.longitude.toString());

        // Initialize the recyclerview and adapter for messages
        messageList = findViewById(R.id.view_messages);
        messageList.setLayoutManager(new LinearLayoutManager(this));

        messageAdapter = new TextAdapter<>(messageList);
        messageList.setAdapter(messageAdapter);

        // TODO open the view model
        peerViewModel = new ViewModelProvider(this).get(PeerViewModel.class);
        messages = peerViewModel.fetchMessagesFromPeer(peer);

        // TODO query the database asynchronously, and use messagesAdapter to display the result
        messages.observe(this, messages -> {
            messageAdapter.setDataset(messages);
            messageList.setAdapter(messageAdapter);
        });
    }

}
