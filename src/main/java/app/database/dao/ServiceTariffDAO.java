package app.database.dao;

import app.database.conf.ConnectionPool;
import app.database.conf.ConstantQuery;
import app.entity.AccountService;
import app.entity.Service;
import app.entity.Tariff;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTariffDAO extends AbstractDAO {
    private static final Logger LOG = Logger.getLogger(ServiceTariffDAO.class);

    private static final ServiceTariffDAO serviceTariffDAO = new ServiceTariffDAO();


    private ServiceTariffDAO() {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.getInstance();
        }
    }

    public static ServiceTariffDAO getInstance() {
        return serviceTariffDAO;
    }


    public Service getServiceById(long id) {
        Service service = new Service();
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_SERVICE_BY_ID);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                service.setId(rs.getLong(ConstantQuery.ID));
                service.setName(rs.getString(ConstantQuery.SERVICE_NAME));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }

    public List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<>();
        try (Connection con = connectionPool.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(ConstantQuery.GET_ALL_SERVICES);
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getLong(ConstantQuery.ID));
                service.setName(rs.getString(ConstantQuery.SERVICE_NAME));
                service.setTariffList(getTariffsByServiceId(service.getId()));
                serviceList.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Service> getAllServicesWithoutTariffs() {
        List<Service> serviceList = new ArrayList<>();
        try (Connection con = connectionPool.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(ConstantQuery.GET_ALL_SERVICES);
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getLong(ConstantQuery.ID));
                service.setName(rs.getString(ConstantQuery.SERVICE_NAME));
                serviceList.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<AccountService> getAccountServicesByAccountId(long accountId) {
        List<AccountService> accountServices = new ArrayList<>();
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_ALL_ACCOUNT_SERVICES);
            pst.setLong(1, accountId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                AccountService accServ = new AccountService();
                accServ.setAccountId(accountId);
                accServ.setServiceId(rs.getLong(ConstantQuery.SERVICE_ID));
                accServ.setTariffId(rs.getLong(ConstantQuery.TARIFF_ID));
                accServ.setActivationTime(rs.getDate(ConstantQuery.ACTIVATION_DATE));
                accServ.setStatus(rs.getBoolean(ConstantQuery.ENABLE_STATUS));
                accServ.setNexPaymentDay(rs.getDate(ConstantQuery.NEXT_PAYMENT_DAY));
                accServ.setPayed(rs.getBoolean(ConstantQuery.PAYED));
                accountServices.add(accServ);
            }
        } catch (SQLException e) {
            LOG.debug("ID: " + accountId);
            e.printStackTrace();
        }
        return accountServices;
    }

    public AccountService getAccountServiceByAccountId(long accountId, long serviceId) {
        AccountService accountService = null;
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_ACCOUNT_SERVICE);
            pst.setLong(1, accountId);
            pst.setLong(2, serviceId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                accountService = new AccountService();
                accountService.setAccountId(accountId);
                accountService.setServiceId(rs.getLong(ConstantQuery.SERVICE_ID));
                accountService.setTariffId(rs.getLong(ConstantQuery.TARIFF_ID));
                accountService.setActivationTime(rs.getDate(ConstantQuery.ACTIVATION_DATE));
                accountService.setStatus(rs.getBoolean(ConstantQuery.ENABLE_STATUS));
                accountService.setNexPaymentDay(rs.getDate(ConstantQuery.NEXT_PAYMENT_DAY));
                accountService.setPayed(rs.getBoolean(ConstantQuery.PAYED));
            }
        } catch (SQLException e) {
            LOG.debug("ID: " + accountId);
            e.printStackTrace();
        }
        return accountService;
    }

    public List<Tariff> getTariffsByServiceId(long id) {
        List<Tariff> tariffList = new ArrayList<>();
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_ALL_TARIFF_BY_SERVICEID);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Tariff tariff = new Tariff();
                tariff.setId(rs.getLong(ConstantQuery.ID));
                tariff.setServiceId(id);
                tariff.setName(rs.getString(ConstantQuery.TARIFF_NAME));
                tariff.setDescription(rs.getString(ConstantQuery.TARIFF_DESCRIPTION));
                tariff.setPrice(rs.getInt(ConstantQuery.TARIFF_PRICE));
                tariffList.add(tariff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tariffList;
    }

    public List<Tariff> getPartTariffsByServiceId(long service_id, int start, int end, String orderBy, String desc) {
        List<Tariff> tariffs = new ArrayList<>();
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst;
            pst = con.prepareStatement(ConstantQuery.GET_PART_TARIFFS_BY_SERVICEID.replace("myTable", orderBy).replace("myDesc", desc));
            pst.setLong(1, service_id);
            pst.setInt(2, start);
            pst.setInt(3, end);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Tariff tariff = new Tariff();
                tariff.setId(rs.getLong(ConstantQuery.ID));
                tariff.setServiceId(service_id);
                tariff.setName(rs.getString(ConstantQuery.TARIFF_NAME));
                tariff.setDescription(rs.getString(ConstantQuery.TARIFF_DESCRIPTION));
                tariff.setPrice(rs.getInt(ConstantQuery.TARIFF_PRICE));
                tariffs.add(tariff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tariffs;
    }

    public int getServiceTariffCount(long id) {
        int count = 0;
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_SERVICE_TARIFF_COUNT);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("tariffs");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public long checkTariffIsExist(Tariff tariff) {
        long result = -1;
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.CHECK_EXIST_TARIFF);
            pst.setString(1, tariff.getName());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getLong(ConstantQuery.ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Tariff getTariffById(long tariffId) {
        Tariff tariff = new Tariff();
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_TARIFF_BY_ID);
            pst.setLong(1, tariffId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tariff.setId(tariffId);
                tariff.setServiceId(rs.getLong(ConstantQuery.SERVICE_ID));
                tariff.setName(rs.getString(ConstantQuery.TARIFF_NAME));
                tariff.setDescription(rs.getString(ConstantQuery.TARIFF_DESCRIPTION));
                tariff.setPrice(rs.getInt(ConstantQuery.TARIFF_PRICE));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tariff;
    }

    public void addNewTariff(Tariff tariff) {
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.ADD_NEW_TARIFF);
            pst.setLong(1, tariff.getServiceId());
            pst.setString(2, tariff.getName());
            pst.setString(3, tariff.getDescription());
            pst.setInt(4, tariff.getPrice());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTariff(String tariff) {
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.REMOVE_TARIFF);
            pst.setString(1, tariff);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editTariff(Tariff tariff) {
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.EDIT_TARIFF);
            pst.setString(1, tariff.getName());
            pst.setString(2, tariff.getDescription());
            pst.setInt(3, tariff.getPrice());
            pst.setLong(4, tariff.getId());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
