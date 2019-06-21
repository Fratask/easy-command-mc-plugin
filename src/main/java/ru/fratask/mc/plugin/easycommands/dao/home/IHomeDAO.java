package ru.fratask.mc.plugin.easycommands.dao.home;

import ru.fratask.mc.plugin.easycommands.entity.Home;

import java.util.List;

public interface IHomeDAO {
    public static final String SQL_FIND_ALL = "select * from " + Home.TABLE_NAME;
    public static final String SQL_FIND_NAME_AND_OWNER = SQL_FIND_ALL + " where " + Home.NAME_COLUMN + " = ? AND " + Home.OWNER_COLUMN + " = ?";
    public static final String SQL_INSERT = "insert into " + Home.TABLE_NAME + " (" +
            Home.NAME_COLUMN + ", " +
            Home.XCOORD_COLUMN + ", " +
            Home.YCOORD_COLUMN + ", " +
            Home.ZCOORD_COLUMN + ", " +
            Home.OWNER_COLUMN +
            ") values (?, ?, ?, ?, ?)";
    public static final String SQL_UPDATE = "update " + Home.TABLE_NAME + " set " +
            Home.NAME_COLUMN + " = ?, " +
            Home.XCOORD_COLUMN + " = ?, " +
            Home.YCOORD_COLUMN + " = ?, " +
            Home.ZCOORD_COLUMN + " = ?, " +
            Home.OWNER_COLUMN + " = ?" +
            " where " + Home.ID_COLUMN + " = ?";
    public static final String SQL_DELETE = "delete from " + Home.TABLE_NAME + " where " + Home.ID_COLUMN + " = ?";
    public static final String SQL_DELETE_ALL = "delete from " + Home.TABLE_NAME;


    public List<Home> findAll();
    public void deleteALL();
    public void insert(Home home);
    public void update(Home home);
    public void delete(Home home);

}