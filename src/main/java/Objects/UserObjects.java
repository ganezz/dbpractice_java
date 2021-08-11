package Objects;

public class UserObjects {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String otp;

    public UserObjects( String id, String name,String phone,String email,String otp){
        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
        setOtp(otp);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }



}
