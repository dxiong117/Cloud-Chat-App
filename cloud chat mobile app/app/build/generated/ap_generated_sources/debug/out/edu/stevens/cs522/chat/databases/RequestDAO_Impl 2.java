package edu.stevens.cs522.chat.databases;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import edu.stevens.cs522.chat.entities.DateConverter;
import edu.stevens.cs522.chat.entities.Message;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RequestDAO_Impl extends RequestDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Message> __insertionAdapterOfMessage;

  private final EntityDeletionOrUpdateAdapter<Message> __updateAdapterOfMessage;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSeqNum;

  public RequestDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMessage = new EntityInsertionAdapter<Message>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `Message` (`id`,`seqNum`,`chatRoom`,`messageText`,`timestamp`,`latitude`,`longitude`,`sender`,`senderId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Message value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.seqNum);
        if (value.chatRoom == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.chatRoom);
        }
        if (value.messageText == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.messageText);
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.timestamp);
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        if (value.latitude == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindDouble(6, value.latitude);
        }
        if (value.longitude == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindDouble(7, value.longitude);
        }
        if (value.sender == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.sender);
        }
        stmt.bindLong(9, value.senderId);
      }
    };
    this.__updateAdapterOfMessage = new EntityDeletionOrUpdateAdapter<Message>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Message` SET `id` = ?,`seqNum` = ?,`chatRoom` = ?,`messageText` = ?,`timestamp` = ?,`latitude` = ?,`longitude` = ?,`sender` = ?,`senderId` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Message value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.seqNum);
        if (value.chatRoom == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.chatRoom);
        }
        if (value.messageText == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.messageText);
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.timestamp);
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        if (value.latitude == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindDouble(6, value.latitude);
        }
        if (value.longitude == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindDouble(7, value.longitude);
        }
        if (value.sender == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.sender);
        }
        stmt.bindLong(9, value.senderId);
        stmt.bindLong(10, value.id);
      }
    };
    this.__preparedStmtOfUpdateSeqNum = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Message SET seqNum = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Message message) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfMessage.insertAndReturnId(message);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  protected void update(final Message message) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMessage.handle(message);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateSeqNum(final long id, final long seqNum) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSeqNum.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, seqNum);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateSeqNum.release(_stmt);
    }
  }

  @Override
  public long getLastSequenceNumber() {
    final String _sql = "SELECT MAX(seqNum) AS max_seq_num FROM Message WHERE seqNum <> 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Message> getUnsentMessages() {
    final String _sql = "SELECT * FROM Message WHERE seqNum = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSeqNum = CursorUtil.getColumnIndexOrThrow(_cursor, "seqNum");
      final int _cursorIndexOfChatRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "chatRoom");
      final int _cursorIndexOfMessageText = CursorUtil.getColumnIndexOrThrow(_cursor, "messageText");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
      final int _cursorIndexOfSenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "senderId");
      final List<Message> _result = new ArrayList<Message>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Message _item;
        _item = new Message();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.seqNum = _cursor.getLong(_cursorIndexOfSeqNum);
        if (_cursor.isNull(_cursorIndexOfChatRoom)) {
          _item.chatRoom = null;
        } else {
          _item.chatRoom = _cursor.getString(_cursorIndexOfChatRoom);
        }
        if (_cursor.isNull(_cursorIndexOfMessageText)) {
          _item.messageText = null;
        } else {
          _item.messageText = _cursor.getString(_cursorIndexOfMessageText);
        }
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfTimestamp)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
        }
        _item.timestamp = DateConverter.fromTimestamp(_tmp);
        if (_cursor.isNull(_cursorIndexOfLatitude)) {
          _item.latitude = null;
        } else {
          _item.latitude = _cursor.getDouble(_cursorIndexOfLatitude);
        }
        if (_cursor.isNull(_cursorIndexOfLongitude)) {
          _item.longitude = null;
        } else {
          _item.longitude = _cursor.getDouble(_cursorIndexOfLongitude);
        }
        if (_cursor.isNull(_cursorIndexOfSender)) {
          _item.sender = null;
        } else {
          _item.sender = _cursor.getString(_cursorIndexOfSender);
        }
        _item.senderId = _cursor.getLong(_cursorIndexOfSenderId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
