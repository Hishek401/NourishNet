package com.NourishNet.model;

/**
 * Recipient Model Class
 * 
 * Represents an organization or individual who receives donated food.
 * Examples: food banks, shelters, community kitchens.
 */
public class Recipient {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String organizationType;
    private String createdAt;

    // No-arg constructor (required for JavaBean)
    public Recipient() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getOrganizationType() { return organizationType; }
    public void setOrganizationType(String organizationType) { this.organizationType = organizationType; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
