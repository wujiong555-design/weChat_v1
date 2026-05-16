package friendgroup;

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
 * Servlet implementation class Friendgroup
 */
@WebServlet("/Friendgroup")
public class Friendgroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Friendgroup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/friendgroup.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String content = request.getParameter("content");
		
		HttpSession session=request.getSession(true);
		String from=(String)session.getAttribute("email");

		response.setContentType("text/html;charset=UTF-8");		
		
		String msg="";	
		
		
		try {
			
			Connection conn=DBUtil.getConnection();
			
			Statement statement = conn.createStatement();
			
			String sql="INSERT INTO `friendgroup_v1`(`pengfrom`, `pengcontent`) \r\n"
					+ "VALUES ('"+from+"', '"+content+"')";
			
			//ResultSet resultSet = statement.executeQuery(s);
			statement.executeUpdate(sql);
			
			conn.close();			
			
			
			msg="公開されました。" + "<a href='peng.html'>友達グループに一覧</a>";
			
		}catch(Exception e) {
			System.out.println(e);
			msg="JDBCのロードに失敗した";
		}     

		response.getWriter().append(msg);
	}

}
