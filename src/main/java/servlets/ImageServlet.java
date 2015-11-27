package servlets;

import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dal.PropertyImageFacade;

@WebServlet("/images")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		InitialContext ic;
		byte[] imgData = null;
		try {
			ic = new InitialContext();
			PropertyImageFacade propertyImageFacade = (PropertyImageFacade) ic.lookup("java:global/PropertyFinder/PropertyImageFacade");
			imgData = propertyImageFacade.getFirstByPropertyId(Long.valueOf((String)req.getParameter("id")));
		} catch (NamingException e) {
			e.printStackTrace();
		}	
	    
		response.setHeader("Content-Type", "JPG");
		response.setHeader("Content-Length", String.valueOf(imgData.length));

		BufferedOutputStream output = null;

		try {
		    output = new BufferedOutputStream(response.getOutputStream());
		    output.write(imgData);
		} finally {
		    if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
		}
	}

}
