package woorigym.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import woorigym.user.model.service.UserService;
import woorigym.user.model.vo.UserTable;

/**
 * Servlet implementation class UserJoinServlet
 */
@WebServlet("/join")
public class UserJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/userJoin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user_id = request.getParameter("user_id");
		String user_pwd = request.getParameter("user_pwd");
		String user_pwdtest = request.getParameter("user_pwdtest");
		String user_name = request.getParameter("user_name");
		String phone = request.getParameter("phone");
		String identity_number = request.getParameter("identity_number");
		int mileage = 0;
		String genderStr = request.getParameter("genderStr");
		int gender = Integer.parseInt(genderStr);
		String birthday_yy = request.getParameter("birthday_yy");
		String birthday_mm = request.getParameter("birthday_mm");
		String birthday_dd = request.getParameter("birthday_dd");
		String birthday = birthday_yy + "/" + birthday_mm + "/" + birthday_dd;
		String email_1 = request.getParameter("email_1");
		String email_2 = request.getParameter("email_2");
		String email = email_1 + "@" + email_2;
		String email_ynStr = request.getParameter("email_ynStr");
		int email_yn = Integer.parseInt(email_ynStr);

		UserTable user = new UserTable();
		if (user_pwd.equals(user_pwdtest)) {
			user.setUser_id(user_id);
			user.setUser_pwd(user_pwd);
			user.setUser_name(user_name);
			user.setEmail(email);
			user.setEmail_yn(email_yn);
			user.setPhone(phone);
			user.setMileage(mileage);
			user.setBirthday(birthday);
			user.setIdentity_number(identity_number);
			user.setGender(gender);
		} else {

		}
		int result = new UserService().userInsert(user);
		if (result == 1) {
			response.sendRedirect("login");
		} else {
			response.sendRedirect("join");
		}
		
	}

}
