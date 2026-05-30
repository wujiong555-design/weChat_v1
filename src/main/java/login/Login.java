package login;

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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/login.html").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		System.out.println(email+"|"+password+"|");
		
		String url="jdbc:mysql://localhost/wechat_v1";
		String user="root";
		String passwordConnect="123456";
		
		String msg="";
		 
				try {
//					Class.forName("com.mysql.cj.jdbc.Driver");
//					
//					Connection conn = DriverManager.getConnection(url,user,passwordConnect);
					Connection conn=DBUtil.getConnection();
					
					String sql="SELECT `email`,`password` FROM `user_v1` WHERE email='"+email+"'"
							+ " AND password='"+password+"'";
					
					Statement creatStatement = conn.createStatement();
					
					ResultSet resultSet = creatStatement.executeQuery(sql);
					
					int count =0;
					
					while(resultSet.next()){
						count= count+1;
						
					}
					
					conn.close();
					
					if(count==0) {
						
						msg="メールかパスワードが正しくない。";
					}else {
						
						msg="ログインに成功しました。"+"<a href='my.html'>マイページへ進む</a>";
				
					HttpSession session = request.getSession(true);
					
					session.setAttribute("email", email); 
					
					}
					
					
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				
				response.getWriter().append(msg);
	
	}
	

}
