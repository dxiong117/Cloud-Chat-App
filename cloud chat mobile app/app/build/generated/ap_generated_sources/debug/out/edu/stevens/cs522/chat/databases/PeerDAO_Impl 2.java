package edu.stevens.cs522.chat.databases;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.guava.GuavaRoom;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stevens.cs522.chat.entities.DateConverter;
import edu.stevens.cs522.chat.entities.Peer;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PeerDAO_Impl extends PeerDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Peer> __insertionAdapterOfPeer;

  private final EntityDeletionOrUpdateAdapter<Peer> __updateAdapterOfPeer;

  public PeerDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPeer = new EntityInsertionAdapter<Peer>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `Peer` (`id`,`name`,`timestamp`,`latitude`,`longitude`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Peer value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.timestamp);
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        if (value.latitude == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.latitude);
        }
        if (value.longitude == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindDouble(5, value.longitude);
        }
      }
    };
    this.__updateAdapterOfPeer = new EntityDeletionOrUpdateAdapter<Peer>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Peer` SET `id` = ?,`name` = ?,`timestamp` = ?,`latitude` = ?,`longitude` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Peer value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        final Long _tmp;
        _tmp = DateConverter.dateToTimestamp(value.timestamp);
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        if (value.latitude == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.latitude);
        }
        if (value.longitude == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindDouble(5, value.longitude);
        }
        stmt.bindLong(6, value.id);
      }
    };
  }

  @Override
  public long insert(final Peer peer) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPeer.insertAndReturnId(peer);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  protected void update(final Peer peer) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPeer.handle(peer);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long upsert(final Peer peer) {
    __db.beginTransaction();
    try {
      long _result = PeerDAO_Impl.super.upsert(peer);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Peer>> fetchAllPeers() {
    final String _sql = "SELECT * FROM Peer";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Peer"}, false, new Callable<List<Peer>>() {
      @Override
      public List<Peer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final List<Peer> _result = new ArrayList<Peer>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Peer _item;
            _item = new Peer();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfName)) {
              _item.name = null;
            } else {
              _item.name = _cursor.getString(_cursorIndexOfName);
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
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public ListenableFuture<Peer> fetchPeer(final long peerId) {
    final String _sql = "SELECT * FROM Peer WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, peerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return GuavaRoom.createListenableFuture(__db, false, new Callable<Peer>() {
      @Override
      public Peer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, _cancellationSignal);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final Peer _result;
          if(_cursor.moveToFirst()) {
            _result = new Peer();
            _result.id = _cursor.getLong(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfName)) {
              _result.name = null;
            } else {
              _result.name = _cursor.getString(_cursorIndexOfName);
            }
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            _result.timestamp = DateConverter.fromTimestamp(_tmp);
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _result.latitude = null;
            } else {
              _result.latitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _result.longitude = null;
            } else {
              _result.longitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }
    }, _statement, true, _cancellationSignal);
  }

  @Override
  protected long getPeerId(final String name) {
    final String _sql = "SELECT id FROM Peer WHERE name = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
