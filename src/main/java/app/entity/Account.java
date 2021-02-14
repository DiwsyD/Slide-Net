package app.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Account extends Entity {

    private Long roleId;
    private String roleName;

    private Integer login;
    private String password;

    private String fName;
    private String lName;
    private String sName;

    private String address;
    private String phoneNumber;
    private String ipAddress;
    private Integer moneyBalance;

    private boolean accountStatus;

    private List<AccountService> activeServices;

    public AccountService isServiceLinked(Service service) {
        return activeServices.stream()
                .filter(serv -> serv.getServiceId().equals(service.getId()))
                .findFirst()
                .orElse(null);
    }

    //Getters
    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Integer getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getsName() {
        return sName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Integer getMoneyBalance() {
        return moneyBalance;
    }

    public boolean isAccountStatus() {
        return accountStatus;
    }

    public List<AccountService> getActiveServices() {
        return activeServices;
    }

    //Setters
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setMoneyBalance(Integer moneyBalance) {
        this.moneyBalance = moneyBalance;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setActiveServices(List<AccountService> activeServices) {
        this.activeServices = activeServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return login.equals(account.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + this.getId()  + '\'' +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", login=" + login +
                ", password='" + password + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", sName='" + sName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", moneyBalance=" + moneyBalance +
                ", accountStatus=" + accountStatus +
                '}';
    }
}
