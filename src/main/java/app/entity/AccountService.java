package app.entity;

import java.sql.Date;
import java.util.Objects;

public class AccountService extends Entity {

    private Long accountId;
    private Long serviceId;
    private Long tariffId;
    private Date activationTime;
    private boolean status;
    private Date nexPaymentDay;
    private boolean payed;
    private int paymentAmount;

    //Getters
    public Long getAccountId() {
        return accountId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Long getTariffId() {
        return tariffId;
    }

    public Date getActivationTime() {
        return activationTime;
    }

    public boolean isStatus() {
        return status;
    }

    public Date getNexPaymentDay() {
        return nexPaymentDay;
    }

    public boolean isPayed() {
        return payed;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    //Setters
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public void setTariffId(Long tariffId) {
        this.tariffId = tariffId;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setNexPaymentDay(Date nexPaymentDay) {
        this.nexPaymentDay = nexPaymentDay;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountService that = (AccountService) o;
        return accountId.equals(that.accountId) && serviceId.equals(that.serviceId) && tariffId.equals(that.tariffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, serviceId, tariffId);
    }

    @Override
    public String toString() {
        return "AccountService{" +
                "accountId=" + accountId +
                ", serviceId=" + serviceId +
                ", tariffId=" + tariffId +
                ", activationTime=" + activationTime +
                ", status=" + status +
                ", nexPaymentDay=" + nexPaymentDay +
                ", payed=" + payed +
                ", paymentAmount=" + paymentAmount +
                '}';
    }
}
