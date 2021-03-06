package servlet;


import Helper.UserManager;
import Helper.UserManagerInterface;
import model.User;
import model.UserManagerJson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/ServletExtractUser")
public class ServletExtractUser extends HttpServlet {
    /**
     * list all the user name from the json file
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserManagerInterface userManager = new UserManager().getUserManager("","","");
        List<User> allUser = userManager.getAllUser();
        req.getSession().setAttribute("allUser",allUser);
        RequestDispatcher redirect = req.getRequestDispatcher("load.jsp");
        redirect.forward(req,resp);
    }


}
