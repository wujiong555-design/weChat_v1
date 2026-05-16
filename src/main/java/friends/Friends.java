package friends;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.util.DBUtil;

/**
 * Servlet implementation class Friends
 */
@WebServlet("/Friends")
public class Friends extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Friends() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/friends.html").forward(request, response);
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
		    Connection conn = DBUtil.getConnection();
		    Statement statement = conn.createStatement();
		   // List<String> list = new ArrayList<>();  
		   Set<String> set = new LinkedHashSet<>(); 
		    
		    
		    // 【步骤 1】先完整地找出所有你主动加的人（外层不嵌套任何东西）
		    String sql1 = "SELECT `accepto` FROM `friend_v1` WHERE addfrom='" + from + "'";   
		    ResultSet resultList1 = statement.executeQuery(sql1);
		    while(resultList1.next()) {
		        set.add(resultList1.getString("accepto"));
		    }
		    resultList1.close(); // 跑完立刻关闭第一个结果集
		    
		    // 【步骤 2】第一个查完了，现在并列地去查所有主动加你的人
		    String sql2 = "SELECT `addfrom` FROM `friend_v1` WHERE accepto='" + from + "'";
		    ResultSet resultList2 = statement.executeQuery(sql2); // 此时可以安全复用同一个 statement
		    while(resultList2.next()) {
		        String friend = resultList2.getString("addfrom");
		        // 【小技巧】为了防止“互加好友”导致的重复，加个判断：如果 list 里已经有了，就不重复加
		        if (!set.contains(friend)) {
		            set.add(friend);
		        }
		    }
		    resultList2.close(); // 跑完立刻关闭第二个结果集
		    
		    // 【步骤 3】两个查询全部干净地结束了，再统一去拼 HTML 渲染页面
		    String html = "";
		    for(String s : set) {
		        html += "<a href='message.html?email=" + s + "'>" + s + "</a><br>";
		    }
		    response.getWriter().append(html);
		    
		    // 最后关闭大资源
		    statement.close();
		    conn.close(); 
		    
		} catch(Exception e) {
		    e.printStackTrace(); 
		    msg = "JDBCのアクセスに失敗した";
		
		}
     		response.getWriter().append(msg);
	
	
	}

}
