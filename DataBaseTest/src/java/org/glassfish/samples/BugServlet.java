
import org.glassfish.samples.model.BugsEJB;
import java.io.IOException;
import javax.ejb.EJB;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.samples.model.Bugs;
import org.glassfish.samples.model.Users;
import org.glassfish.samples.model.UsersEJB;

@WebServlet(name = "BugServlet", urlPatterns = {"/BugServlet"})
public class BugServlet extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;

    @EJB
    BugsEJB ejb;

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                
        String mail = request.getParameter("mail");
        String message = request.getParameter("expl");
        ejb.create(mail, message);
        System.out.println(mail+message);
        RequestDispatcher view = request.getRequestDispatcher("HomePageServlet");
        view.forward(request, response);

    }
}
