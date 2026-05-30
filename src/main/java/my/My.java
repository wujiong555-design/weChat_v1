package my;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.util.DBUtil;

/**
 * Servlet implementation class My
 */
@WebServlet("/My")
public class My extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public My() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/my.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);

		String email = (String) session.getAttribute("email");
		

//		System.out.println(email + "|" + password + "|");

		String url = "jdbc:mysql://localhost/wechat_v1";
		String user = "root";
		String passwordConnect = "123456";

		String msg = "";

		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//
//			Connection conn = DriverManager.getConnection(url, user, passwordConnect);
			Connection conn=DBUtil.getConnection();

			String sql = "SELECT `email`,`password` FROM `user_v1` WHERE email='" + email + "'";

			Statement creatStatement = conn.createStatement();

			ResultSet resultSet = creatStatement.executeQuery(sql);

			int count = 0;
			
			String email1 = "";
			String password1 = "";

			while (resultSet.next()) {
				email1=resultSet.getString("email");
				password1=resultSet.getString("password");
			}

			conn.close();

			if (email1 == null || email1=="") {

				msg = "このメールアドレスが登録されない";
				
			} 
			response.getWriter().append(email1+"/"+password1);
			
			

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		response.getWriter().append(msg);

	}

}
