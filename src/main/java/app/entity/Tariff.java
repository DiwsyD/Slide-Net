package app.entity;

import java.util.Objects;

public class Tariff extends Entity{

    private long serviceId;
    private String name;
    private String description;
    private int price;

    /*Getters*/
    public long getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    /*Setters*/
    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return serviceId == tariff.serviceId && Objects.equals(name, tariff.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, name);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id='" + this.getId()  + '\'' +
                "serviceId=" + serviceId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
