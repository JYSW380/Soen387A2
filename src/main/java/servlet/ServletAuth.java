package servlet;

import Helper.UserManager;
import Helper.UserManagerInterface;
import model.UserManagerJson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebServlet("/ServletAuth")
public class ServletAuth extends HttpServlet {
    /**
     * to set the user authetication to the one that is selected , save only the string
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManagerInterface userManager = new UserManager().getUserManager("","","");
        String logUser = request.getParameter("user");
        Set<String> userGroup =  userManager.getGroup(logUser);
        HttpSession session = request.getSession();
        session.setAttribute("user",logUser);
        session.setAttribute("userGroup",userGroup);
//        System.out.println(request.getParameter("user"));
        RequestDispatcher redirect = request.getRequestDispatcher("ServletPostManager");
        redirect.forward(request,response);
    }
}
