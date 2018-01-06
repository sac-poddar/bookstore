
package samples.webapps.bookstore.listeners;

import javax.servlet.*;

import samples.webapps.bookstore.database.BookDB;
import samples.webapps.bookstore.util.Counter;

public final class ContextListener
    implements ServletContextListener {
    private ServletContext context = null;

    public void contextInitialized(ServletContextEvent event) {
         context = event.getServletContext();
         
         try {
            BookDB bookDB = new BookDB();
            context.setAttribute("bookDB", bookDB);
         } catch (Exception ex) {
            context.log("Couldn't create bookstore database bean: " + ex.getMessage());
         }

         Counter counter = new Counter();
         context.setAttribute("hitCounter", counter);
         counter = new Counter();
         context.setAttribute("orderCounter", counter);
    }
      
    public void contextDestroyed(ServletContextEvent event) {
         context = event.getServletContext();
         BookDB bookDB = (BookDB)context.getAttribute("bookDB");
         if (bookDB != null) {
           bookDB.remove();
         }
         if (context != null) {
           context.removeAttribute("bookDB");
           context.removeAttribute("hitCounter");
           context.removeAttribute("orderCounter");
         }
    }
}
