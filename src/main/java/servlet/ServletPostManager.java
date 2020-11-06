package servlet;


import model.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet("/ServletPostManager")
public class ServletPostManager extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Post> allPost= new ArrayList<>();
        DBQuerry db = new DBQuerry();
        allPost= db.getAllPost();
        request.getSession().setAttribute("allPost",allPost);
        String method = request.getMethod();
        switch (method.toLowerCase()){
            case "post":
                doPost(request,response);
                break;
            case "get":
                doGet(request,response);
                break;
            default:
                RequestDispatcher redirect = request.getRequestDispatcher("display.jsp");
                redirect.forward(request,response);
                break;

        }
    }
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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        DBQuerry db = new DBQuerry();
        List<Post> allPost= new ArrayList<>();
        String userName ="user1";
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
            InputStream inputStream = db.getFileUpload(id);
            if(inputStream!=null){
                w2file(response, inputStream);
            }
            else{
                w2file(response, InputStream.nullInputStream());
            }
        }
        else if(edit !=null){
            message= request.getParameter("editMessage");
            Post p= new Post(userName,message);
            Part part = extractFile(request, "updateFile");
            if(part!=null){
                db.updatePost(id,p, part.getInputStream());
            }
            else{
                db.updatePost(id,p,null);
            }
        }
        else{
            Post post= new Post(userName,message);
            Part part = extractFile(request, "file");
            if(part!=null){
                db.insertPost(post , part.getInputStream());
            }
            else{
                db.insertPost(post , null);
            }
        }
        allPost= db.getAllPost();
        request.getSession().setAttribute("allPost",allPost);
        RequestDispatcher redirect = request.getRequestDispatcher("display.jsp");
        redirect.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<Post> searchPost = new ArrayList<>();
        HttpSession session = request.getSession();
        session.setAttribute("user","user1");
        DBQuerry db = new DBQuerry();
        if (search !=null){
            searchPost =db.search(request.getParameter("searchText"));
        }
        for(Post p : searchPost){
            System.out.println(p);
        }
        request.getSession().setAttribute("searchPost",searchPost);
        RequestDispatcher redirect = request.getRequestDispatcher("display.jsp");
        redirect.forward(request,response);
    }
}
