<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
  </head>
  <body>
      <%@ taglib uri="/WEB-INF/murach.tld" prefix="mma" %>
      
    <h1>CD list</h1>
    <table>
      <tr>
        <th>Description</th>
        <th class="right">Price</th>
        <th>&nbsp;</th>
      </tr>
      <mma:products>
      <tr>
        <td>${productDescription}</td>
        <td class="right">${productPrice}</td>
        <td><form action="cart" method="post">
            <input type="hidden" name="productCode" value="8601">
            <input type="submit" value="Add To Cart">
          </form></td>
      </tr>
      </mma:products>
    </table>
    

  </body>
</html>