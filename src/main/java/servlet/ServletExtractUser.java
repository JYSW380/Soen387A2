package servlet;


import model.User;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;




@WebServlet("/ServletExtractUser")
public class ServletExtractUser extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        JSONArray obj1 = null;
        try {
            obj1 = (JSONArray) new JSONParser().parse(new FileReader("C:\\Users\\Owner\\IdeaProjects\\Soen387A2\\users.json"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<String > allName= new ArrayList<>();
        for(Object o : obj1){
            Map<String, String > u = (Map) o;
            allName.add(u.get("name"));
        }

        req.getSession().setAttribute("allU",allName);
        RequestDispatcher redirect = req.getRequestDispatcher("load.jsp");
        redirect.forward(req,resp);
    }
}
