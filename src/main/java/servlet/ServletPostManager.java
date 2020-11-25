package servlet;


import model.DBQuerry;
import model.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet("/ServletPostManager")
public class ServletPostManager extends HttpServlet {


    /**
     * extract the part from file upload from client
     * @param request
     * @param name
     * @return
     */
    public Part extractFile(HttpServletRequest request,String name){
        Part part =null;
        try {
            part = request.getPart(name);
        } catch (ServletException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return part;
    }

    /**
     * write the file from the database to the client
     * @param response
     * @param inputStream
     * @throws IOException
     */
    private void w2file(HttpServletResponse response, InputStream inputStream) throws IOException {
        String type = ".txt";
        response.setContentType("text/plain");
        String repName =  "fileDownload"+type;
        response.setHeader("Content-disposition", "attachment; " +
                "filename=" + repName);
        ServletOutputStream res = response.getOutputStream();
        response.setContentLength(inputStream.available());
        byte[] buffer = new byte[1024];
        while (inputStream.read(buffer) > 0) {
            res.write(buffer);
        }
        inputStream.close();
        res.flush();
        res.close();
    }

    private void w2file(HttpServletResponse response,String type, Post post) throws IOException {
        response.setContentType(!type.equals("xml")? "text/plain ": "text/xml" + ";charset=UTF-8" );

        String repName =  "post."+type;
        response.setHeader("Content-disposition", "attachment; " +
                "filename=" + repName);

        ServletOutputStream res = response.getOutputStream();
        if(type.equals("xml")){
            res.print("<?xml version='1.0'?>");
            res.print("<root>");
//            for(Object [] el: chatmessage){
                try {
                    res.print("<post>");
                    res.print("<message> Message: "+post.getMessage()+"</message>");
                    res.print("<hashTag> #: "+post.getHashTag()+"</hashTag>");
                    res.print("<userName> owner: "+post.getUserName()+"</userName>");
                    res.print("<group> in: "+post.getGroup()+"</group>");
                    res.print("</post>");

                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
            res.print("</root>");
        }
        else    {
//            for(Object [] el: chatmessage){
                try {
                    res.print("Message: "+post.getMessage());
                    res.print("#: "+post.getHashTag());
                    res.print("owner: "+post.getUserName());
                    res.print("in: "+post.getGroup());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
        }
        res.flush();
        res.close();
    }

    /**
     * handle create a post ,  edit delete update file of the post
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        DBQuerry db = new DBQuerry();
        Map<String, ArrayList<Post>> allPost= new HashMap<>();
        String userName = (String)session.getAttribute("user");
        String message = request.getParameter("message");
        String edit = request.getParameter("edit");
        String delete = request.getParameter("delete");
        String idValue = request.getParameter("id");
        String download = request.getParameter("download");
        int id =0;
        if(idValue !=null){
            id = Integer.parseInt(idValue);
        }
        if(delete!=null){
            db.deletePost(id);
        }
        else if(download!=null){
            String isxml = request.getParameter("xml");
            if(isxml ==null){
                InputStream inputStream = db.getFileUpload(id);
                if(inputStream!=null){
                    w2file(response, inputStream);
                }
                else{
                    w2file(response, InputStream.nullInputStream());
                }
            }
            else{
                Post post = db.getPostById(id);
                if(post!=null){
                    w2file(response,isxml,post);
                }

            }

        }
        else if(edit !=null){
            String group = request.getParameter("editGroup");
            message= request.getParameter("editMessage");
//            message =message.replace("<br/>", "");
            Post p= new Post(userName,message, group);
            Part part = extractFile(request, "updateFile");
            if(part!=null){
                db.updatePost(id,p, part.getInputStream());
            }
            else{
                db.updatePost(id,p,null);
            }
        }
        else{
            String group = request.getParameter("group");
            Post post= new Post(userName,message, group);
            Part part = extractFile(request, "file");
            if(part!=null){
                db.insertPost(post , part.getInputStream());
            }
            else{
                db.insertPost(post , null);
            }
        }
        Set<String> userGroup = (Set<String>) session.getAttribute("userGroup");
        allPost= db.getAllPost(userGroup);
        request.getSession().setAttribute("allPost",allPost);
        RequestDispatcher redirect = request.getRequestDispatcher("display.jsp");
        redirect.forward(request,response);
    }

    /**
     * handle log out and search of the username or hashtag
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, ArrayList<Post>> allPost= new HashMap<>();
        DBQuerry db = new DBQuerry();
        String logOut = request.getParameter("LogOut");
        String search = request.getParameter("search");
        HttpSession session = request.getSession();
        if(logOut !=null){
            System.out.println("inside logout");
            session.removeAttribute("user");
            session.removeAttribute("allPost");
            session.removeAttribute("searchPost");
            System.out.println("after remove");
            RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
            redirect.forward(request,response);
        }
        else if(search !=null){
            List<Post> searchPost = new ArrayList<>();
            if (search !=null){
                Set<String> userGroup = (Set<String>) session.getAttribute("userGroup");
                searchPost =db.search(request.getParameter("searchText"), userGroup);
            }
            request.getSession().setAttribute("searchPost",searchPost);
            RequestDispatcher redirect = request.getRequestDispatcher("display.jsp");
            redirect.forward(request,response);
        }
        else{
            Set<String> userGroup = (Set<String>) session.getAttribute("userGroup");
            allPost= db.getAllPost(userGroup);
            request.getSession().setAttribute("allPost",allPost);
            RequestDispatcher redirect = request.getRequestDispatcher("display.jsp");
            redirect.forward(request,response);
        }
    }
}
