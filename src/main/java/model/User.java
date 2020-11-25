package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {




    private static int idCount=1;
    private static int id;
    private String username;
    private String email;
    private String pwd;


    public User( String username, String email, String pwd) {
        this.id = idCount++;
        this.username = username;
        this.email = email;
        this.pwd = hashPassword(pwd);
    }

    public User() {
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
        this.pwd = hashPassword(pwd);
    }


    @Override
    public String toString() {
        return username ;
    }
    public String hashPassword(String password) {

        MessageDigest md = null;  //MD5
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password.getBytes());
        byte[] b =md.digest();
        StringBuffer sb = new StringBuffer();
        for(byte bt: b) {
            sb.append(Integer.toHexString(bt & 0xff).toString());
        }
        return sb.toString();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String computePwd(String pwd){
        return new User().hashPassword(pwd);
    }



}
