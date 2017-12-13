package demo.web;

import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControllerServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        process(request, response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        String view = perform_action(request);
        forwardRequest(request, response, view);
    }

    protected String perform_action(HttpServletRequest request)
        throws IOException, ServletException {
        
        String serv_path = request.getServletPath();
        HttpSession session = request.getSession();
        String user = request.getParameter("user");
        if (serv_path.equals("/login.do")) {
            
            String pass =request.getParameter("password");
            //Obtenemos el objeto remote_login definido como 
            //atributo al inicializar el contexto
            UserAccess current_user =getRemoteLogin().connect(user, pass);
            if(current_user != null){
                session.setAttribute("useraccess",current_user);
                return ("/view/wallview.jsp");
            }
            else{
                return "/error-no-user_access.html";
            }
        } 
        
        else if (serv_path.equals("/put.do")) {
            String msg = request.getParameter("msg");
            //Recuperamos de session el atributo useraccess
            UserAccess current_user = (UserAccess)session.getAttribute("useraccess");
            current_user.put(msg);
            return ("/view/wallview.jsp");
        } 
        
        else if (serv_path.equals("/refresh.do")) {
            UserAccess current_user = (UserAccess)session.getAttribute("useraccess");
            if(current_user != null){
                return ("/view/wallview.jsp");
            }
            return "/error-not-loggedin.html";
        } 
        
        else if (serv_path.equals("/logout.do")) {
            UserAccess current_user = (UserAccess)session.getAttribute("useraccess");
            if (current_user != null){
                session.removeAttribute("useraccess");
                return "/goodbye.html";
            }
            return "/error-not-loggedin.html";
        } 
        
        else if (serv_path.equals("/delete.do")) {
            UserAccess current_user = (UserAccess)session.getAttribute("useraccess");
            int position = Integer.parseInt(request.getParameter("position"));
            current_user.delete(position);
            return ("/view/wallview.jsp");
            
        }
        
        else {
            return "/error-bad-action.html";
        }
    }

    public RemoteLogin getRemoteLogin() {
        return (RemoteLogin) getServletContext().getAttribute("remoteLogin");
    }
    public void forwardRequest(HttpServletRequest request, HttpServletResponse response, String view) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(view);
        if (dispatcher == null) {
            throw new ServletException("No dispatcher for view path '"+view+"'");
        }
        dispatcher.forward(request,response);
    }
}


