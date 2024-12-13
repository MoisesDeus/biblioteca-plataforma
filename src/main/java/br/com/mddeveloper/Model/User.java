package br.com.mddeveloper.Model;

import java.sql.Date;
import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private LocalDate birthDate;

    public User(int id, String name, String email, String address, String phone, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public User() {

    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User: " +
                "id: " + id +
                ", name: " + name +
                ", email: " + email +
                ", address: " + address +
                ", phone: " + phone +
                ", birthDate: " +  birthDate;
    }
}
