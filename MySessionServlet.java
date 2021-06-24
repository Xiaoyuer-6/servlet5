import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: HuYu
 * Date: 2021-06-23
 * Time: 16:15
 */
public class MySessionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        //1.验权
        String name  =request.getParameter("name");
        String pwd = request.getParameter("pwd");
        PrintWriter writer = response.getWriter();
        if(name!=null&&pwd!=null&&
            name.equals("root")&&pwd.equals("root")){
          //2.用户创建成功，创建会话信息
            HttpSession session = request.getSession(true);
            String sessionId = session.getId();
            writer.println("<h1>欢迎访问~</h1>");
            writer.println(String.format("<h3>SessionID:%s</h3>",sessionId));
            //session存储访问数量的key
            String countKey = "countkey";//全局，存储的key
            if(session.isNew()|| session.getAttribute(countKey)==null){//是否是首次访问，得到的访问次数未null
                //存储当前用户的访问数据
                session.setAttribute(countKey,1);//给session中存储1
                writer.println("<h4>访问次数：1</h4>");//第一次访问
            }else {//不是第一次访问
                //拿到当前用户的访问数据
                int count  = (int)session.getAttribute(countKey);//获取次数。强转
                count++;
                //改变后，重新将访问数据变更到Session
                session.setAttribute(countKey,count);
                writer.println(String.format("<h4>访问次数：%d</h4>",count));
            }





        }else {
            writer.println("<h1>用户名或密码错误！</h1>");

        }
    }
}
