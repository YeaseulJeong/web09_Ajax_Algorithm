package com.encore;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/main.do")
public class MainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		System.out.println(action);
		if (action.equals("wordCount")) {
			wordCount(request, response);
		} else if (action.equals("kickCount")) {
			kickCount(request, response);
		}
	}// process()

	private void wordCount(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		    
		//구현하세요..
		
		String words = request.getParameter("words");
		
		Count c = new Count();
		String result = c.execute(words);
		
//		PrintWriter out = response.getWriter();
//		out.print(word);                         // 이렇게 out.print 해서 결과를 보여주는건 Servlet 의 역할이 아니다  REsult.jsp 로 forwarding해라
		
		request.setAttribute("result", result);
		request.getRequestDispatcher("Result.jsp").forward(request, response);	 // 어디로 forwarding 되어서 가는건지 
	}
	
	private void kickCount(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String fname=request.getParameter("fname");
		
		String path=request.getServletContext().getRealPath("/res");   // 중요합니다..
		// web기반에서이기 때문에  이 file을 넣게되면 서버에서 돌아야 한다 (서버 path를 잡아줘야 한다) 따라서 서버 주소까지 (WAS tomcat에 이 파일이 있어서 돌아가야한다)를 요청해야 (웹에서는 서버에 파일이 있어야 인식을 한다 )
		// request.getServletContext().getRealPath까지만 쓰면  서버에 올라가는 ContextPath 프로젝트명까지만 주소로 만듦. tomcat8.5\webapps\web09_Ajax_Algorithm 
		// eclipse 는 서버에 배포까지 해주는데 file을 WebContent 밑에 res 폴더안에 넣으면 tomcat 에 res 폴더가 만들어지고 그 안에서 들어간다 (즉 WAS 안에 배포됨). 따라서 res 까지 path를 잡아줘야 한다
		
		System.out.println(path);
		File f=new File(path, fname);  // 이 경로에 fname 을 넣어서 file을 만든 것 
		
		int result=Kickboard.execute(f);  // static 이니까 바로 썼다 객체를 만들지 않고도 한번에 할 수 있으니까
	
		request.setAttribute("result", result);
	    request.getRequestDispatcher("Result.jsp").forward(request, response);	   // out.print 하지 않고  Result.jsp 계속 활용성 높임
	}
}
