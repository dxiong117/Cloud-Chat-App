package edu.stevens.cs522.chat.databases;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatDatabase_Impl extends ChatDatabase {
  private volatile PeerDAO _peerDAO;

  private volatile MessageDAO _messageDAO;

  private volatile RequestDAO _requestDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Peer` (`id` INTEGER NOT NULL, `name` TEXT, `timestamp` INTEGER, `latitude` REAL, `longitude` REAL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_Peer_name` ON `Peer` (`name`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Message` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `seqNum` INTEGER NOT NULL, `chatRoom` TEXT, `messageText` TEXT, `timestamp` INTEGER, `latitude` REAL, `longitude` REAL, `sender` TEXT, `senderId` INTEGER NOT NULL, FOREIGN KEY(`senderId`) REFERENCES `Peer`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_Message_senderId` ON `Message` (`senderId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5ac9468abe7bbc311086881dda9c8064')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Peer`");
        _db.execSQL("DROP TABLE IF EXISTS `Message`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPeer = new HashMap<String, TableInfo.Column>(5);
        _columnsPeer.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeer.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeer.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeer.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeer.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPeer = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPeer = new HashSet<TableInfo.Index>(1);
        _indicesPeer.add(new TableInfo.Index("index_Peer_name", true, Arrays.asList("name")));
        final TableInfo _infoPeer = new TableInfo("Peer", _columnsPeer, _foreignKeysPeer, _indicesPeer);
        final TableInfo _existingPeer = TableInfo.read(_db, "Peer");
        if (! _infoPeer.equals(_existingPeer)) {
          return new RoomOpenHelper.ValidationResult(false, "Peer(edu.stevens.cs522.chat.entities.Peer).\n"
                  + " Expected:\n" + _infoPeer + "\n"
                  + " Found:\n" + _existingPeer);
        }
        final HashMap<String, TableInfo.Column> _columnsMessage = new HashMap<String, TableInfo.Column>(9);
        _columnsMessage.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("seqNum", new TableInfo.Column("seqNum", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("chatRoom", new TableInfo.Column("chatRoom", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("messageText", new TableInfo.Column("messageText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("sender", new TableInfo.Column("sender", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("senderId", new TableInfo.Column("senderId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMessage = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMessage.add(new TableInfo.ForeignKey("Peer", "CASCADE", "NO ACTION",Arrays.asList("senderId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMessage = new HashSet<TableInfo.Index>(1);
        _indicesMessage.add(new TableInfo.Index("index_Message_senderId", false, Arrays.asList("senderId")));
        final TableInfo _infoMessage = new TableInfo("Message", _columnsMessage, _foreignKeysMessage, _indicesMessage);
        final TableInfo _existingMessage = TableInfo.read(_db, "Message");
        if (! _infoMessage.equals(_existingMessage)) {
          return new RoomOpenHelper.ValidationResult(false, "Message(edu.stevens.cs522.chat.entities.Message).\n"
                  + " Expected:\n" + _infoMessage + "\n"
                  + " Found:\n" + _existingMessage);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5ac9468abe7bbc311086881dda9c8064", "7caa2d6cd71bd4acd96b63e49efa82b5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Peer","Message");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `Peer`");
      _db.execSQL("DELETE FROM `Message`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PeerDAO.class, PeerDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(MessageDAO.class, MessageDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(RequestDAO.class, RequestDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public PeerDAO peerDao() {
    if (_peerDAO != null) {
      return _peerDAO;
    } else {
      synchronized(this) {
        if(_peerDAO == null) {
          _peerDAO = new PeerDAO_Impl(this);
        }
        return _peerDAO;
      }
    }
  }

  @Override
  public MessageDAO messageDao() {
    if (_messageDAO != null) {
      return _messageDAO;
    } else {
      synchronized(this) {
        if(_messageDAO == null) {
          _messageDAO = new MessageDAO_Impl(this);
        }
        return _messageDAO;
      }
    }
  }

  @Override
  public RequestDAO requestDao() {
    if (_requestDAO != null) {
      return _requestDAO;
    } else {
      synchronized(this) {
        if(_requestDAO == null) {
          _requestDAO = new RequestDAO_Impl(this);
        }
        return _requestDAO;
      }
    }
  }
}
