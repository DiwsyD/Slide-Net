package app.entity;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private static final Logger LOG = Logger.getLogger(EntityManager.class);

    private static List<Account> accountList;

    private static List<Service> serviceList;
    private static List<Tariff> tariffList;
    private static List<Role> roleList;

    private static final EntityManager entityManager = new EntityManager();

    private EntityManager() {
        accountList = new ArrayList<>();
        serviceList = new ArrayList<>();
        tariffList = new ArrayList<>();
        roleList = new ArrayList<>();
    }

    public static EntityManager getInstance() {
        return entityManager;
    }


    //Adders
    public void addAccount(Account account) {
        if (account == null || !accountList.contains(account)) {
            accountList.add(account);
        }
    }
    public void addService(Service service) {
        if (service != null && !serviceList.contains(service)) {
            serviceList.add(service);
        }
    }

    public void addTariff(Tariff tariff) {
        if (tariff != null && !tariffList.contains(tariff)) {
            tariffList.add(tariff);
        }
    }

    public void addRole(Role role) {
        if (role != null && !roleList.contains(role)) {
            roleList.add(role);
        }
    }


    //Removers
    public void removeTariff(Tariff tariff) {
        tariffList.remove(tariff);
        for(Service s : serviceList) {
            s.getTariffList().remove(tariff);
        }
    }

    //Updaters
    public void updateAccount(Account account) {
        Account acc = getAccountById(account.getId());
        accountList.set(accountList.indexOf(acc), account);
    }

    public void updateService(Service service) {
        Service serv = getServiceById(service.getId());
        serviceList.set(serviceList.indexOf(serv), service);
    }


    //Getters

    //Account
    public List<Account> getAccountList() {
        return accountList;
    }

    public Account getAccountById(Long id) {
        return accountList.stream()
                .filter(acc -> id.equals(acc.getId()))
                .findFirst()
                .orElse(null);
    }

    public Account getAccountByLogin(Integer login) {
        return accountList.stream()
                .filter(acc -> login.equals(acc.getLogin()))
                .findFirst()
                .orElse(null);
    }

    //Service
    public Service getServiceById(Long id) {
        return serviceList.stream()
                .filter(ser -> id.equals(ser.getId()))
                .findFirst()
                .orElse(null);
    }

    public Tariff getTariffByName(String name) {
        return tariffList.stream()
                .filter(tar -> name.equals(tar.getName()))
                .findFirst()
                .orElse(null);
    }

    public Tariff getTariffById(Long id) {
        return tariffList.stream()
                .filter(tar -> id.equals(tar.getId()))
                .findFirst()
                .orElse(null);
    }

    public Role getRoleById(Long id) {
        return roleList.stream()
                .filter(rol -> id.equals(rol.getId()))
                .findFirst()
                .orElse(null);
    }

    public Role getRoleByName(String name) {
        return roleList.stream()
                .filter(tar -> name.equals(tar.getName()))
                .findFirst()
                .orElse(null);
    }
}
