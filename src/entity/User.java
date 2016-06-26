package entity;

import java.util.Date;

public class User extends AbstractEntity {

    protected String username;
    private String password;
    private String email;
    private String userAlias;
    private Date lastLoginDate;
    private Date registerDate;

    public User() {
    }

    public User( int userId, String username, String hashPass, String email,
            String userAlias, Date lastLoginDate, Date registerDate ) {
        this.id = userId;
        this.username = username;
        this.password = hashPass;
        this.email = email;
        this.userAlias = userAlias;
        this.lastLoginDate = lastLoginDate;
        this.registerDate = registerDate;
    }

    public void setLastLoginDate( Date lastLoginDate ) {
        this.lastLoginDate = lastLoginDate;
    }

    public void setUserAlias( String userAlias ) {
        this.userAlias = userAlias;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getUserAlias() {
        return userAlias;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> User{" + "username=" + username + ", password="
                + password + ", email=" + email + ", userAlias=" + userAlias
                + ", lastLoginDate=" + lastLoginDate + ", registerDate=" + registerDate + '}';
    }

}
