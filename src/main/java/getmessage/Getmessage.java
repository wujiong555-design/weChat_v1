package getmessage;

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
 * Servlet implementation class Getmessage
 */
@WebServlet("/Getmessage")
public class Getmessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Getmessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/login.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String to = request.getParameter("to");
		
		response.setContentType("text/html;charset=UTF-8");	
		
		String msg="";
				
		HttpSession session=request.getSession(true);
		
		String from= (String)session.getAttribute("email");
		
		try {
			
			Connection conn=DBUtil.getConnection();
			
			Statement statement = conn.createStatement();
			
			String sql="SELECT `messagefrom`,`content` FROM `message_v1` WHERE (`messagefrom` = '"+from+"' and `messageto`='"+to+"') OR (`messagefrom` = '"+to+"' and `messageto`='"+from+"')";
			
			ResultSet resultSet = statement.executeQuery(sql);
			//int num = statement.executeUpdate(sql);			
			
			
			while(resultSet.next()) {
				String sender = resultSet.getString("messagefrom");
				
				String content =resultSet.getString("content");
				
				msg +="["+sender+"]"+content+"<br>";
				
//				msg+=resultSet.getString("content")+"<br>";
			}
			resultSet.close();
			conn.close();				
			
		}catch(Exception e) {
			System.out.println(e);
			msg="JDBCのロードに失敗した";
		}

		response.getWriter().append(msg);

	}

}
