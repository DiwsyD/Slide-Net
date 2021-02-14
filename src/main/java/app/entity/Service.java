package app.entity;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service extends Entity {

    private String name;
    private List<Tariff> tariffList = new ArrayList<>();

    /*Getters*/
    public String getName() {
        return name;
    }

    public List<Tariff> getTariffList() {
        return tariffList;
    }

    /*Setters*/
    public void setName(String name) {
        this.name = name;
    }


    public void setTariffList(List<Tariff> tariffList) {
        this.tariffList = tariffList;
    }

    public void addTariff(Tariff tariff) {
        if (tariff != null && !tariffList.contains(tariff)) {
            tariffList.add(tariff);
        }
    }

    public void updateTariff(Tariff tariff) {
        tariffList.set(tariffList.indexOf(tariff), tariff);
    }

    public Tariff getTariffById(long id) {
        return tariffList.stream()
                .filter(tar -> tar.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(name, service.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id='" + this.getId()  + '\'' +
                "name='" + name + '\'' +
                ", tariffList=" + tariffList +
                '}';
    }
}
