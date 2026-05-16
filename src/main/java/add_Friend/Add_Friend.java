package add_Friend;

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
 * Servlet implementation class Add_Frend
 */
@WebServlet("/Add_Frend")
    public class Add_Friend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add_Friend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/sh.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String to = request.getParameter("to");
		
		HttpSession session=request.getSession(true);
		String from=(String)session.getAttribute("email");

		response.setContentType("text/html;charset=UTF-8");		
		
		String msg="";			
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DBUtil.getConnection();
			Statement statement = conn.createStatement();
			
			String sql="INSERT INTO `friend_v1` (`addfrom`, `accepto`, `status`, `updateTime`) "
										+ "SELECT'"+from+"', '"+to+"',1,CURRENT_TIMESTAMP() "
					               		+ "WHERE NOT EXISTS(SELECT 1 FROM `friend_v1` "
					               		+ "WHERE (`addfrom`='"+from+"'AND `accepto`='"+to+"')"
					               		+"OR (`addfrom`='"+to+"'AND `accepto`='"+from+"'))";
			System.out.println(sql);
			int row=statement.executeUpdate(sql);
			if(row>0) {
				msg="友達が正常に追加されました。" + "<a href=\"friends.html\">友達一覧を見る</a>";
				
			}else {
				
				msg="二人はすでに友達です。";
				
			}
			
			statement.close();
			
			conn.close();			
			
			// 【关键改动 1】：把成功提示和写入直接放进 try 的大括号内
			
			response.getWriter().append(msg);
			// 【关键改动 2】：成功后直接结束方法，绝不往下走
		}
			
		 catch(Exception e) {
			System.out.println(e);
			msg="JDBCのロードに失敗した";
			response.getWriter().append(msg); // 【关键改动 3】：失败时在这里写入
		}     

		// 【关键改动 4】：删掉原本在最底部的 response.getWriter().append(msg);
		
		
		
	}

}
