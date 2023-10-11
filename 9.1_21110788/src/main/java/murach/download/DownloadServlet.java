package murach.download;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import murach.business.Product;
import murach.business.User;
import murach.data.ProductIO;
import murach.data.UserIO;
import murach.util.CookieUtil;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		// get current action
		String action = request.getParameter("action");
		if (action == null) {
			action = "viewAlbums"; // default action
		}

		// perform action and set URL to appropriate page
		String url = "/index.jsp";

		if (action.equals("viewAlbums")) {
			url = "/index.jsp";
		} else if (action.equals("checkUser")) {
			url = checkUser(request, response);
		} else if (action.equals("viewCookies")) {
			url = "/view_cookies.jsp";
		} else if (action.equals("deleteCookies")) {
			url = deleteCookies(request, response);
			HttpSession session = request.getSession();
			session.invalidate();
		}

		// forward to the view
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		// perform action and set URL to appropriate page
		String url = "/index.jsp";
		if (action.equals("registerUser")) {
			url = registerUser(request, response);
		}

		// forward to the view
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	private String checkUser(HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();

		String productCode = request.getParameter("productCode");

		ServletContext sc1 = getServletContext();
		String productPath = sc1.getRealPath("/WEB-INF/products.txt");
		HttpSession session = request.getSession();
		session.setAttribute("productCode", productCode);

		Product product = (Product) session.getAttribute("product");

		product = ProductIO.getProduct(productCode, productPath);
		session.setAttribute("product", product);
		String description = product.getDescription();
		session.setAttribute("description", description);

		User user = (User) session.getAttribute("user");

		String url;
		// if User object doesn't exist, check email cookie
		if (user == null) {
			String emailAddress = CookieUtil.getCookieValue(cookies, "userEmail");

			// if cookie doesn't exist, go to Registration page
			if (emailAddress == null || emailAddress.equals("")) {

				url = "/register.jsp";
			}
			// if cookie exists, create User object and go to Downloads page
			else {
				request.setAttribute("emailAddress", emailAddress);
				ServletContext sc = getServletContext();
				String path = sc.getRealPath("/EmailList.txt");

				user = UserIO.getUser(emailAddress, path);
				session.setAttribute("user", user);
				url = "/" + product.getCode() + "_download.jsp";
			}
		}
		// if User object exists, go to Downloads page
		else {
			url = "/" + productCode + "_download.jsp";
		}
		return url;
	}

	private String registerUser(HttpServletRequest request, HttpServletResponse response) {
		String url = "/register.jsp";

		// get the user data
		String email = request.getParameter("email").replaceAll("\\s", "");
		String firstName = request.getParameter("firstName").replaceAll("\\s", "");
		String lastName = request.getParameter("lastName").replaceAll("\\s", "");

		if (firstName == null || lastName == null || email == null || firstName.isEmpty() || lastName.isEmpty()
				|| email.isEmpty()) {
			String message = "You haven't fill all your information";
			request.setAttribute("message", message);
			return url;
		} else {
			// store the data in a User object
			User user = new User();
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);

			// write the User object to a file
			ServletContext sc = getServletContext();
			String path = sc.getRealPath("/WEB-INF/EmailList.txt");
			System.out.print(path);
			System.out.print(email);
			UserIO.add(user, path);

			// store the User object as a session attribute
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			// add a cookie that stores the user's email to browser
			Cookie c = new Cookie("userEmail", email);
			c.setMaxAge(60 * 60 * 24 * 365 * 3); // set age to 3 years
			c.setPath("/"); // allow entire app to access it
			response.addCookie(c);

			// add a cookie that stores the user's as a cookie
			Cookie c2 = new Cookie("firstNameCookie", firstName);
			c2.setMaxAge(60 * 60 * 24 * 365 * 3); // set age to 3 years
			c2.setPath("/"); // allow entire app to access it
			response.addCookie(c2);

			// create and return a URL for the appropriate Download page
			Product product = (Product) session.getAttribute("product");
			url = "/" + product.getCode() + "_download.jsp";
		}
		return url;
	}

	private String deleteCookies(HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0); // delete the cookie
			cookie.setPath("/"); // allow the download application to access it
			response.addCookie(cookie);
		}
		String url = "/delete_cookies.jsp";
		return url;
	}
}