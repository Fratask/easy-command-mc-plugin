package ru.fratask.mc.plugin.easycommands.dao.warp;

import ru.fratask.mc.plugin.easycommands.entity.Warp;

import java.util.Set;

public interface IWarpDAO {
    public static final String SQL_FIND_ALL = "select * from " + Warp.TABLE_NAME;
    public static final String SQL_FIND_NAME_AND_OWNER = SQL_FIND_ALL + " where " + Warp.NAME_COLUMN + " = ? AND " + Warp.OWNER_COLUMN + " = ?";
    public static final String SQL_INSERT = "insert into " + Warp.TABLE_NAME + " (" +
            Warp.NAME_COLUMN + ", " +
            Warp.XCOORD_COLUMN + ", " +
            Warp.YCOORD_COLUMN + ", " +
            Warp.ZCOORD_COLUMN + ", " +
            Warp.OWNER_COLUMN +
            ") values (?, ?, ?, ?, ?)";
    public static final String SQL_UPDATE = "update " + Warp.TABLE_NAME + " set " +
            Warp.NAME_COLUMN + " = ?, " +
            Warp.XCOORD_COLUMN + " = ?, " +
            Warp.YCOORD_COLUMN + " = ?, " +
            Warp.ZCOORD_COLUMN + " = ?, " +
            Warp.OWNER_COLUMN + " = ?" +
            " where " + Warp.ID_COLUMN + " = ?";
    public static final String SQL_DELETE = "delete from " + Warp.TABLE_NAME + " where " + Warp.ID_COLUMN + " = ?";
    public static final String SQL_DELETE_ALL = "delete from " + Warp.TABLE_NAME;

    public Set<Warp> findAll();
    public void deleteALL();
    public void insert(Warp warp);
    public void update(Warp warp);
    public void delete(Warp warp);
}
