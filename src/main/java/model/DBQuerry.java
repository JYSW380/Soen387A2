package model;



import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DBQuerry {
    // extract user
    // all the function fro message

    private User extractUserFromRS(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPwd(rs.getString("pwd"));
        return user;
    }
//    public User getUser(String userName) throws SQLException{
//        Connection con = DBconnection.getConnection();
//        try{
//            Statement stmt = con.createStatement();
//            String sql = "SELECT * FROM user_details WHERE username="+userName;
//            ResultSet rs= stmt.executeQuery(sql);
//            return extractUserFromRS(rs);
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                con.close();
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    public List<User> getAllUsers(){
        Connection con = DBconnection.getConnection();
        List<User> users = new ArrayList();
        try{
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM users_info";
            ResultSet rs= stmt.executeQuery(sql);
            while(rs.next()){
                // it would automatically increment the pointer of rs
                User user = extractUserFromRS(rs);
                users.add(user);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;

    }
    public List<Post> getAllPost(){
        Connection con = DBconnection.getConnection();
        List<Post> allPost = new ArrayList();
        try{
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM post";
            ResultSet rs= stmt.executeQuery(sql);
            while(rs.next()){
                Post post = extractPostFromRS(rs);
                if(post!=null){
                    allPost.add(post);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return allPost;
    }
    public InputStream getFileUpload(int id ){
        Connection con = DBconnection.getConnection();
        InputStream inputStream=null;
        Blob fileUploadBlob= null;
        try{
            String query = "SELECT fileUpload from post where id="+id;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
//            PreparedStatement ps = con.prepareStatement(query);
//            ps.setInt(1, id);
//            ResultSet rs= ps.executeQuery(query);
            while(rs.next()){
                // it would automatically increment the pointer of rs

                fileUploadBlob=rs.getBlob("fileUpload");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(fileUploadBlob!=null){
            try {
//                System.out.println(fileUploadBlob.getBinaryStream().available());
                return fileUploadBlob.getBinaryStream();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
    private Post extractPostFromRS(ResultSet rs) {
        try {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUser(rs.getString("user"));
            post.setMessage(rs.getString("message"));
            post.setTimeStamp( LocalTime.parse(String.valueOf(rs.getTime("timeStamp"))));
            post.setHashTag(rs.getString("hashTag"));
            if(rs.getTime("updateTime")==null){
                post.setUpdateTime(null);
            }
            else{
                post.setUpdateTime(LocalTime.parse(String.valueOf(rs.getTime("updateTime"))));
            }

             return post;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public boolean insertPost(Post post , InputStream inputStream){
        Connection con = DBconnection.getConnection();
        try{
            String query = "INSERT INTO post VALUES (default, ?,?,?,?,default,";
            if(inputStream!=null){
                query += "?)";
            }
            else{
                query +="default)";
            }
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, post.getUserName());
            ps.setString(2, post.getMessage());
            ps.setTime(3, Time.valueOf(post.getTimeStamp()));
            ps.setString(4, post.getHashTag());
            if(inputStream!=null){
                ps.setBlob(5,inputStream);
            }
            int i= ps.executeUpdate();
            return i==1;
        }
        catch (SQLException e){
            System.out.println("catch insert post ");
            e.printStackTrace();
        }
        finally {
            try{
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Post> search(String search){
        List<Post> result = new ArrayList<>();
        Connection con = DBconnection.getConnection();
        try{
//            LocalTime timeStamp;
//            if(search.contains(":")){
//                timeStamp = LocalTime.parse(search, DateTimeFormatter.ofPattern("HH:mm:ss"));
//            }
            String query="SELECT * FROM post where ";
            String [] toSearch = {"user=","hashTag="};
            for(int i =0;i <2; i++){
                query += toSearch[i]+"?";
                System.out.println(query);
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, search);
                ResultSet rs= ps.executeQuery();
                while(rs.next()){
                    Post post = extractPostFromRS(rs);
                    if(post!=null){
                        result.add(post);
                    }
                }
                if(!result.isEmpty()){
                    return result;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deletePost(int id){
        Connection con = DBconnection.getConnection();
        try {
            String query = "DELETE from post where id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int i =ps.executeUpdate();
            return i==1;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean updatePost(int id, Post post, InputStream inputStream){
        Connection con = DBconnection.getConnection();
        try{
            String query="UPDATE post SET message=?, hashTag=?, updateTime=?";
            if(inputStream !=null){
                query += ", fileUpload =? where id=?";
            }
            else{
                query += "where id=?";
            }

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, post.getMessage());
            ps.setString(2, post.getHashTag());
            ps.setTime(3, Time.valueOf(LocalTime.now()));
            if(inputStream!=null){
                ps.setBlob(4, inputStream);
                ps.setInt(5, id);
            }
            else{
                ps.setInt(4, id);
            }
            int i= ps.executeUpdate();
            return i==1;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
