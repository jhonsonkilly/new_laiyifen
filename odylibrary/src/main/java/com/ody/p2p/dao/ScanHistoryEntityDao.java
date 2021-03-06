package com.ody.p2p.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.ody.p2p.entity.ScanHistoryEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SCAN_HISTORY_ENTITY".
*/
public class ScanHistoryEntityDao extends AbstractDao<ScanHistoryEntity, Long> {

    public static final String TABLENAME = "SCAN_HISTORY_ENTITY";

    /**
     * Properties of entity ScanHistoryEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property SpId = new Property(1, String.class, "spId", false, "SP_ID");
        public final static Property Cost = new Property(2, String.class, "cost", false, "COST");
        public final static Property Url = new Property(3, String.class, "url", false, "URL");
        public final static Property SpName = new Property(4, String.class, "spName", false, "SP_NAME");
    }


    public ScanHistoryEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ScanHistoryEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SCAN_HISTORY_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"SP_ID\" TEXT," + // 1: spId
                "\"COST\" TEXT," + // 2: cost
                "\"URL\" TEXT," + // 3: url
                "\"SP_NAME\" TEXT);"); // 4: spName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SCAN_HISTORY_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ScanHistoryEntity entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String spId = entity.getSpId();
        if (spId != null) {
            stmt.bindString(2, spId);
        }
 
        String cost = entity.getCost();
        if (cost != null) {
            stmt.bindString(3, cost);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }
 
        String spName = entity.getSpName();
        if (spName != null) {
            stmt.bindString(5, spName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ScanHistoryEntity entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String spId = entity.getSpId();
        if (spId != null) {
            stmt.bindString(2, spId);
        }
 
        String cost = entity.getCost();
        if (cost != null) {
            stmt.bindString(3, cost);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }
 
        String spName = entity.getSpName();
        if (spName != null) {
            stmt.bindString(5, spName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ScanHistoryEntity readEntity(Cursor cursor, int offset) {
        ScanHistoryEntity entity = new ScanHistoryEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // spId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // cost
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // url
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // spName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ScanHistoryEntity entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSpId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCost(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSpName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ScanHistoryEntity entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ScanHistoryEntity entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ScanHistoryEntity entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
