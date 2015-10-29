/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package murach.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import static javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED;
import javax.servlet.jsp.tagext.BodyTagSupport;
import static javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import murach.business.Cart;
import murach.business.LineItem;
import murach.business.Product;

/**
 *
 * @author Mitch
 */
public class ProductsTag extends BodyTagSupport{
  private ArrayList<Product> products;
  private Iterator iterator;
  private Product product;

  @Override
  public int doStartTag() {
    products = (ArrayList<Product>) pageContext.findAttribute("products");
    if (products.size() <= 0) {
      return SKIP_BODY;
    } else {
      return EVAL_BODY_BUFFERED;
    }
  }

  @Override
  public void doInitBody() throws JspException {
    products = (ArrayList<Product>) pageContext.findAttribute("products");
    iterator = products.iterator();
    if (iterator.hasNext()) {
      product = (Product) iterator.next();
      this.setProductAttributes(product);
    }
  }

  private void setProductAttributes(Product p) {
    pageContext.setAttribute(
      "productCode", p.getCode());
    pageContext.setAttribute(
      "productDescription", p.getDescription());
    pageContext.setAttribute(
      "productPrice", p.getPriceCurrencyFormat());
  }

  @Override
  public int doAfterBody() throws JspException {
    try {
      if (iterator.hasNext()) {
        product = (Product) iterator.next();
        this.setProductAttributes(product);
        return EVAL_BODY_AGAIN;
      } else {
        JspWriter out = bodyContent.getEnclosingWriter();
        bodyContent.writeOut(out);
        return SKIP_BODY;
      }
    } catch (IOException ioe) {
      System.err.println("error in doAfterBody " + ioe.getMessage());
      return SKIP_BODY;
    }
  }
}
