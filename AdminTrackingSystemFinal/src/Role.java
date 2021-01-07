import java.io.Serializable;

public abstract class Role implements Serializable {
    private String name;
    private String password;
    private String phone;
    private String status;

    public Role(String name, String password, String phone, String status) {
        setName(name);
        setPassword(password);
        setPhone(phone);
        setStatus(status);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    };
}
