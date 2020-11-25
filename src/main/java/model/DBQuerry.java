package model;

import Helper.DBQuerryInterface;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalTime;
import java.util.*;


public class DBQuerry implements DBQuerryInterface {
    /**
     * get all the post
     * @return
     */
    @Override
    public Map<String, ArrayList<Post>> getAllPost(Set<String> userGroup){
        // get post by their group and public too
        Connection con = DBconnection.getConnection();
        Map<String, ArrayList<Post>> postByGroup = new HashMap<>();
        List<Post> allPost = new ArrayList();
        try{
            String query = "SELECT * FROM post where group_name=? order by timeStamp ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs=null;
            for(String s: userGroup){
                postByGroup.put(s, new ArrayList<>());
                ps.setString(1,s);
                rs= ps.executeQuery();
                while(rs.next()){
                    Post post = extractPostFromRS(rs);
                    if(post!=null){
                        postByGroup.get(s).add(post);
                    }
                }
            }
            postByGroup.put("public", new ArrayList<>());
            ps.setString(1,"public");
            rs= ps. executeQuery();
            while(rs.next()){
                Post post = extractPostFromRS(rs);
                if(post!=null){
                    postByGroup.get("public").add(post);
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
        return postByGroup;
    }

    /**
     * get File from the post
     * @param id
     * @return
     */
    @Override
    public InputStream getFileUpload(int id){
        Connection con = DBconnection.getConnection();
        InputStream inputStream=null;
        Blob fileUploadBlob= null;
        try{
            String query = "SELECT fileUpload from post where id="+id;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
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

    /**
     * insert post into the database
     * @param post
     * @param inputStream
     * @return
     */
    @Override
    public boolean insertPost(Post post, InputStream inputStream){
        Connection con = DBconnection.getConnection();
        try{
            String query = "INSERT INTO post VALUES (default, ?,?,?,?,?,default,";
            if(inputStream!=null){
                query += "?)";
            }
            else{
                query +="default)";
            }
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,post.getUserName()!=""? post.getUserName().trim():"");
            ps.setString(2, post.getMessage()!=""? post.getMessage().trim():"");
            ps.setTime(3, Time.valueOf(post.getTimeStamp()));
            ps.setString(4,post.getHashTag()!=""? post.getHashTag().trim():"");
            ps.setString(5,post.getGroup());
            if(inputStream!=null){
                ps.setBlob(6,inputStream);
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

    /**
     * search by username and hashtag
     * @param search
     * @return
     */
    @Override
    public List<Post> search(String search , Set<String> userGroup){
        // search all the group that he has
        List<Post> result = new ArrayList<>();
        Connection con = DBconnection.getConnection();
        try{
            String query="SELECT * FROM post where ";
            String order= " order by timeStamp DESC";
            String searchQuery;
            String groupQuery;
            String [] toSearch = {"user=","hashTag="};
            for(int i =0;i <2; i++){
                searchQuery=query;
                searchQuery += toSearch[i]+"?";
                for(String s: userGroup){
                    groupQuery=searchQuery;
                    groupQuery += " AND group_name=?" +order;
//                    System.out.println(groupQuery);
                    PreparedStatement ps = con.prepareStatement(groupQuery);
                    ps.setString(1, search);
                    ps.setString(2, s);
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
                groupQuery=searchQuery;
                groupQuery += " AND where group_name=?" +order;
                PreparedStatement ps = con.prepareStatement(groupQuery);
                ps.setString(1, search);
                ps.setString(2, "public");
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

    /**
     * delete the post from database
     * @param id
     * @return
     */
    @Override
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
    @Override
    public Post getPostById(int id){
        Connection con = DBconnection.getConnection();
        Post post=null;
        try {
            String query = "SELECT * from post where id =?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs =ps.executeQuery();
            rs.next();
            post = extractPostFromRS(rs);
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
        return post;
    }

    /**
     * update the post in the database
     * @param id
     * @param post
     * @param inputStream
     * @return
     */
    @Override
    public boolean updatePost(int id, Post post, InputStream inputStream){
        Connection con = DBconnection.getConnection();
        try{
            String query="UPDATE post SET message=?, hashTag=?, updateTime=?, group_name=?";
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
            ps.setString(4, post.getGroup()) ;
            if(inputStream!=null){
                ps.setBlob(5, inputStream);
                ps.setInt(6, id);
            }
            else{
                ps.setInt(5, id);
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
