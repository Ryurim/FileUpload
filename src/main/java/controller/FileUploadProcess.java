package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(name="/FileUpload/FileUploadProcess", value="/file/upload")
@MultipartConfig(
		maxFileSize = 1024*1024*1, 
		maxRequestSize = 1024*1024*10	
		)
//파일 사이즈 1메가, 전송용량 10메가

public class FileUploadProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		try {
			
			String title = req.getParameter("title");
			
			//파일은 무조건 업로드 시점에 물리적 경로를 취득해야 함
			//String sDir = getServletContext.getRealPath("/Uploads"); //아래랑 똑같음! 필드에선 이거 사용함
			String saveDir = "D:\\java4\\JSP\\FileUpload\\src\\main\\webapp\\Uploads";
			
			Part part = req.getPart("in_file"); //파일 request
			
			String pHeader = part.getHeader("content-disposition");//첨부된 파일 정보 추출
			//파일 업로드를 하면 form-data; name="attachedfile"; filename="업로드한 파일명.확장자" 의 형태로 헤더에 데이터가 넘어옴.
			//content-disposition라는 헤더값에 위의 값들이 다 들어옴
			//파일명을 얻고 싶어서 하는거야! substring 해도 되구, filename= 기준으로 split 해서 앞뒤로 잘라도 돼
			System.out.println("content-disposition >> " + pHeader);
			
			
			
			String[] arrAttachedFile = pHeader.split("filename=");
			//[0] = filename=", [1] = 업로드한파일명.확장자"
			String orgFileName = arrAttachedFile[1].trim().replace("\"", ""); //업로드한파일명.확장자 추출
			System.out.println("orgFileName : " + orgFileName);
			
			
			
			//파일이 존재하면 saveDir에 저장
			if (!orgFileName.isEmpty()) { // 공백이 아니면 저장할게!
				part.write(saveDir + File.separator + orgFileName);
			}
			
			
			
			//파일 저장 후 원래 있던 파일명이 중복될 수 도 있어서 년월일시분초로 바꿔서 덮어씌우는 것
			//파일 확장자 가져올게
			String fileExt = orgFileName.substring(orgFileName.lastIndexOf("."));
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			//새로운 파일명 만들게
			String newFileName = now + fileExt;
			System.out.println("newFileName : " + newFileName);
			
			
			//원래 있던 파일의 이름을 시분초까지 있는 이름으로 바꿔치기
			File orgFile = new File(saveDir + File.separator + orgFileName);
			File newFile = new File(saveDir + File.separator + newFileName);
			orgFile.renameTo(newFile);
			
			
			//데이터베이스에 등록
			
			
			//성공하면
			res.sendRedirect("/");
			
			
		} catch (Exception e) {
			System.out.println("Upload error : " + e.getMessage());
			e.printStackTrace();

			req.setAttribute("uploadError", "파일 업로드 오류");
			req.getRequestDispatcher("/FileUpload/file.jsp").forward(req, res);
		}
	}
}
