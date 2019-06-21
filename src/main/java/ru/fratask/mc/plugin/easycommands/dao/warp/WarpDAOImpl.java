package ru.fratask.mc.plugin.easycommands.dao.warp;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import ru.fratask.mc.plugin.easycommands.dao.DataSource;
import ru.fratask.mc.plugin.easycommands.entity.Warp;

import java.sql.*;
import java.util.*;

public class WarpDAOImpl implements IWarpDAO {

    private DataSource dataSource = DataSource.getInstance();


    @Override
    public Set<Warp> findAll() {
        Set<Warp> result = new HashSet<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                UserStorageService userStorageService = Sponge.getServiceManager().provide(UserStorageService.class).get();
                Long id = rs.getLong(Warp.ID_COLUMN);
                String name = rs.getString(Warp.NAME_COLUMN);
                Location<World> loc = new Location<World>(Sponge.getServer().getWorld("world").get(), rs.getDouble(Warp.XCOORD_COLUMN), rs.getDouble(Warp.YCOORD_COLUMN), rs.getDouble(Warp.ZCOORD_COLUMN));
                UUID owner = userStorageService.get(UUID.fromString(rs.getString(Warp.OWNER_COLUMN))).get().getUniqueId();
                Warp warp = new Warp(id, name, loc, owner);
                result.add(warp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void deleteALL(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ALL);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insert(Warp warp) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, warp.getName());
            statement.setString(2, String.valueOf(warp.getLoc().getBlockX()));
            statement.setString(3, String.valueOf(warp.getLoc().getBlockY()));
            statement.setString(4, String.valueOf(warp.getLoc().getBlockZ()));
            statement.setString(5, String.valueOf(warp.getOwner()));
            statement.execute();

            ResultSet generatedkeys = statement.getGeneratedKeys();
            if (generatedkeys.next()) {
                warp.setId(generatedkeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void update(Warp warp) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, warp.getName());
            statement.setString(2, String.valueOf(warp.getLoc().getBlockX()));
            statement.setString(3, String.valueOf(warp.getLoc().getBlockY()));
            statement.setString(4, String.valueOf(warp.getLoc().getBlockZ()));
            statement.setString(5, String.valueOf(warp.getOwner()));
            statement.setLong(6, warp.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Warp warp) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, warp.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
