package murach.cart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import murach.business.Product;
import murach.data.ProductIO;

public class ProductsServlet extends HttpServlet {
  
    public void initIfNeeded() throws ServletException {
    //
    String path = this.getActualFile();
    if (!isFile(path)) {
      try {
        //add two Users 
        ProductIO.addRecord(new Product("8601",
          "86 (the band) - True Life Songs and Pictures",
          14.95), path);
        ProductIO.addRecord(new Product("pf01",
          "Paddlefoot - The first CD",
          12.95), path);
        ProductIO.addRecord(new Product("pf02",
          "Paddlefoot - The second CD",
          14.95), path);
        ProductIO.addRecord(new Product("jr01",
          "Joe Rut - Genuine Wood Grained Finish",
          14.95), path);

      } catch (IOException ex) {
        Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    HttpSession session = request.getSession();
    initIfNeeded();
    String path = this.getActualFile();
    ArrayList<Product> products = ProductIO.getProducts(path);
    session.setAttribute("products", products);

    String url = "/index.jsp";
    getServletContext()
      .getRequestDispatcher(url)
      .forward(request, response);
  }
  
    /**
   * Check to see if a file path exists on system
   * 
   * @param path String representing full file path
   * @return true if file exists otherwise false
   */
  
  private boolean isFile(String path) {
    File aFile = new File(path);
    return aFile.exists();
  }

  /**
   * Method returns the classic location under WEB-INF for the file product.txt
   * unless on OpenShift. If on OpenShfit then looks up data directory pointed
   * to by environment variable OPENSHIFT_DATA_DIR
   *
   * @return Full path name of product.txt
   */
  private String getActualFile() {
    String path = "";
    String filename = "products.txt";
    String env = System.getenv("OPENSHIFT_DATA_DIR");
    if ((env != null) && (env.length() > 1)) {
      path = env + filename;
    } else {
      ServletContext sc = getServletContext();
      path = sc.getRealPath("/WEB-INF/" + filename);
    }
    return path;
  }
}

