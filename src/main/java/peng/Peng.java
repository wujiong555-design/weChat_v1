package peng;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.util.DBUtil;

/**
 * Servlet implementation class Peng
 */
@WebServlet("/Peng")
public class Peng extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Peng() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/peng.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");		
		
		String msg="";		
		
		HttpSession session=request.getSession(true);
		
		String from= (String)session.getAttribute("email");
		
		try {
			
			Connection conn=DBUtil.getConnection();
			
			Statement statement = conn.createStatement();	
	
				
			String sql1="SELECT `accepto` FROM `friend_v1` WHERE addfrom='"+from+"'";			
			
			ResultSet resultList1 = statement.executeQuery(sql1);
			
			//int num = statement.executeUpdate(sql);
			
			List<String> list=new ArrayList<>();		
			
			while(resultList1.next()) {
				
				list.add(resultList1.getString("accepto"));
				
			}
			
			String sql2="SELECT `addfrom` FROM `friend_v1` WHERE accepto='"+from+"'";
			
			ResultSet resultList2 = statement.executeQuery(sql2);
			
			while(resultList2.next()) {
				
				list.add(resultList2.getString("addfrom"));
				
			}
			String pengfrom="";
			String pengcontent="";
			
			for(String s:list) {
				String sqlall="SELECT `pengcontent`,`pengfrom` FROM `friendgroup_v1` WHERE `pengfrom`='"+s+"'";
				
				ResultSet result = statement.executeQuery(sqlall);						
				
				while(result.next()) {
					
					pengfrom=result.getString("pengfrom");
					
					pengcontent=result.getString("pengcontent");
					
					msg+=pengcontent+"<br>"+pengfrom+"<br><hr><br>";
					
				}	
			}
			
			conn.close();
			
						
		}catch(Exception e) {
			System.out.println(e);
			msg="JDBCのアクセスに失敗した";
		}

		response.getWriter().append(msg);
	}

}
