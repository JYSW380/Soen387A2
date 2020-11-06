package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {

    private String username;
    private String pwd;

    public User() {
    }

    public User(String username,  String pwd) {

        this.username = username;
        try {
            this.pwd = hashPassword(pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }
    public String getPwd(String pwd) {
        return pwd;
    }

    public void setPwd(String pwd) {
        try {
            this.pwd = hashPassword(pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
    public String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA");  //MD5
        md.update(password.getBytes());
        byte[] b =md.digest();
        StringBuffer sb = new StringBuffer();
        for(byte bt: b) {
            sb.append(Integer.toHexString(bt & 0xff).toString());
        }
        return sb.toString();
    }


}
