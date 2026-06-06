package miu.rahul.model;

public class Surgery {
    private Long id;
    private String name;
    private String locationAddress;
    private String telephoneNumber;

    public Surgery(Long id, String name, String locationAddress, String telephoneNumber) {
        this.id = id;
        this.name = name;
        this.locationAddress = locationAddress;
        this.telephoneNumber = telephoneNumber;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLocationAddress() { return locationAddress; }
    public String getTelephoneNumber() { return telephoneNumber; }

    @Override
    public String toString() {
        return "Surgery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
