package message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.util.DBUtil;

/**
 * Servlet implementation class Message
 */
@WebServlet("/Message")
public class Message extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Message() {
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
		String content = request.getParameter("content");
		String to = request.getParameter("to");
		
		response.setContentType("text/html;charset=UTF-8");		
		
		String msg="";		
		
		HttpSession session=request.getSession(true);
		
		String from= (String)session.getAttribute("email");
		
		try {
			
			Connection conn=DBUtil.getConnection();
			
			Statement statement = conn.createStatement();
			
			String sql="INSERT INTO `message_v1`(`messagefrom`, `messageto`, `content`, `updateTime`) \r\n"
					+ "VALUES ('"+from+"', '"+to+"', '"+content+"', CURRENT_TIMESTAMP())";
			
			
			int num = statement.executeUpdate(sql);		

			conn.close();
			
			msg="メッセージが正常に送信されました。";
			
		}catch(Exception e) {
			System.out.println(e);
			msg="メッセージの送信に失敗しました。";
		}		
     
		response.getWriter().append(msg);
	}

}
