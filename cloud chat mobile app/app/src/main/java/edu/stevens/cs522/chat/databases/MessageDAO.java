package edu.stevens.cs522.chat.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import edu.stevens.cs522.chat.entities.Message;
import edu.stevens.cs522.chat.entities.Peer;

// TODO add annotations for Repository pattern
@Dao
public abstract class MessageDAO {
    @Query("SELECT * FROM message")
    public abstract LiveData<List<Message>> fetchAllMessages();

    @Query("SELECT * FROM message WHERE senderId = :peerId")
    public abstract LiveData<List<Message>> fetchMessagesFromPeer(long peerId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void persist(Message message);

}
