package picture;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/upload")
public class upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public upload() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		Part part=request.getPart("photo");
		String filename=getFilename(part);
		writeTo(filename,part);
	}

	private String getFilename(Part part) {
		String header=part.getHeader("Content-Disposition");
		String filename=header.substring(header.indexOf("filename=\"")+10,header.lastIndexOf("\""));
		return filename;
	}
	private void writeTo(String filename, Part part) {
		
		try {
			InputStream in=part.getInputStream();
			String sname=change(filename);
			String path="E:/COURSE/"+sname;
			System.out.println(path);
			OutputStream out=new FileOutputStream(path);
			byte[] buffer=new byte[1024];
			int length=-1;
			while((length=in.read(buffer))!=-1) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String change(String str) {
		 String[] fileRoot = str.split(";");
	     String[] fileName = null;
	     String name = null;
	     for(int i = 0;i < fileRoot.length;i++){
		      if(fileRoot[i] != null){
		       fileName = fileRoot[i].split("\\\\");
		       //得到最终需要的文件名
		       name=fileName[fileName.length-1];
	      }
	     }
		return name;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
