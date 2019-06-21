package ru.fratask.mc.plugin.easycommands.dao.home;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import ru.fratask.mc.plugin.easycommands.dao.DataSource;
import ru.fratask.mc.plugin.easycommands.entity.Home;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeDAOImpl implements IHomeDAO{

    private DataSource dataSource = DataSource.getInstance();


    @Override
    public List<Home> findAll() {
        List<Home> result = new ArrayList<Home>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                UserStorageService userStorageService = Sponge.getServiceManager().provide(UserStorageService.class).get();
                Long id = rs.getLong(Home.ID_COLUMN);
                String name = rs.getString(Home.NAME_COLUMN);
                Location<World> loc = new Location<World>(Sponge.getServer().getWorld("world").get(), rs.getDouble(Home.XCOORD_COLUMN), rs.getDouble(Home.YCOORD_COLUMN), rs.getDouble(Home.ZCOORD_COLUMN));
                UUID owner = userStorageService.get(UUID.fromString(rs.getString(Home.OWNER_COLUMN))).get().getUniqueId();
                Home home = new Home(id, name, loc, owner);
                result.add(home);
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
    public void insert(Home home) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, home.getName());
            statement.setString(2, String.valueOf(home.getLoc().getBlockX()));
            statement.setString(3, String.valueOf(home.getLoc().getBlockY()));
            statement.setString(4, String.valueOf(home.getLoc().getBlockZ()));
            statement.setString(5, String.valueOf(home.getOwner()));
            statement.execute();

            ResultSet generatedkeys = statement.getGeneratedKeys();
            if (generatedkeys.next()) {
                home.setId(generatedkeys.getLong(1));
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
    public void update(Home home) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, home.getName());
            statement.setString(2, String.valueOf(home.getLoc().getBlockX()));
            statement.setString(3, String.valueOf(home.getLoc().getBlockY()));
            statement.setString(4, String.valueOf(home.getLoc().getBlockZ()));
            statement.setString(5, String.valueOf(home.getOwner()));
            statement.setLong(6, home.getId());
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
    public void delete(Home home) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, home.getId());
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
