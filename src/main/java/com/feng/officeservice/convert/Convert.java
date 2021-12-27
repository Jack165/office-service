package com.feng.officeservice.convert;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.SwingUtilities;

import com.feng.officeservice.utils.HtmlToPdf;
import com.feng.officeservice.utils.PdfToJpg;
import com.feng.officeservice.utils.TextToPdf;
import com.feng.officeservice.utils.Word2Html;

public class Convert {
	public static boolean docToHtml(String docPath, String htmlPath) {
		try {
			Word2Html.doc2Html(docPath, htmlPath);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean docxTohtml(String docxPath, String htmlPath) {
		try {
			Word2Html.docx2Html(docxPath, htmlPath);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean htmlToPdf(String htmlPath, String pdfPath,
			String newPdf, String text) {
		try {
			HtmlToPdf.htmlCodeComeFromFile(htmlPath, pdfPath);
			System.out.println("pdfparh:"+pdfPath);
			TextToPdf.textAddToPdf(pdfPath, newPdf, text);
		
			System.out.println("newpath:"+newPdf);
			File f = new File(pdfPath);
			File f2 = new File(newPdf);
			f.deleteOnExit();
			boolean b=moveFile(newPdf,pdfPath);
			return b;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean pdfToJpg(String pdfPath, String jpgPath, String text,
			String icPath) {
		try {

			PdfToJpg.setup(pdfPath, jpgPath);
			// File simSunFontFile = new File("/data/font/simsun.ttf");
			// Font font = Font.createFont(Font.TRUETYPE_FONT, simSunFontFile);

			//textTojpg.createImage(text, new Font("宋体", Font.BOLD, 48),//添加字体
				//	new File(icPath));
			// OutJpg.FilesToic(jpgPath,icPath);

			// OutJpg.pressImage(jpgPath,icPath, 0, 0,0.2f);

			// wm.pressImage("e:/a.jpg", "D:\\ts\\jpg\\1.jpg", 0, 0,0.2f);
			// ImageUtils.addWaterMark(jpgPath, text);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String readTxtFile(String filePath) {
		try {
			System.out.println("读取文件内容：" + filePath);
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				String txt = "";
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println("text content is：" + lineTxt);
					txt = lineTxt;
				}
				System.out.println("读取到：" + txt);
				read.close();
				return txt;
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return "";

	}

	public static void main(String[] args) {
		System.out.println("start up");
		
		createFile("docx");
		createFile("html");
		createFile("pdf");
		createFile("ic");
		createFile("jpg");
		File s=new File(System.getProperty("user.dir"));
		String[] ls=s.list();
		for(int i=0;i<ls.length;i++){
			System.out.println(ls[i]);
			String[] str=ls[i].split("\\.");
			if(str.length<2){
				continue;
			}
			if(str[1].equals("doc")){
				docToHtml(ls[i],"html/"+str[0]+".html");
			}else if(str[1].equals("docx")){
				docxTohtml(ls[i],"html"+File.separator+str[0]+".html");
			}
		}
		File htmls=new File(System.getProperty("user.dir")+File.separator+"html");
		String[] html=htmls.list();
		for(int i=0;i<html.length;i++){
			String[] str=html[i].split("\\.");
			 if(htmlToPdf(System.getProperty("user.dir")+File.separator+"html"+File.separator+html[i],System.getProperty("user.dir")+File.separator+"pdf"+File.separator+str[0]+".pdf",System.getProperty("user.dir")+File.separator+"ic"+File.separator+str[0]+".pdf","大神水印")){
				 System.out.println("水印添加成功");
			 }
		}
		File pdfs=new File(System.getProperty("user.dir")+File.separator+"pdf");
		String[] pdf=pdfs.list();
		for(int i=0;i<pdf.length;i++){
			String[] str=pdf[i].split("\\.");
			pdfToJpg(System.getProperty("user.dir")+File.separator+"pdf"+File.separator+str[0]+".pdf",System.getProperty("user.dir")+File.separator+"jpg","xx","xx");
		}
		
		//docxTohtml("D:\\ts\\docx\\正益无线_上海医药推送接口文档V0.1.docx",
			//	"D:\\ts\\html\\正益无线_上海医药推送接口文档V0.1.html");
		//htmlToPdf("D:\\ts\\html\\正益无线_上海医药推送接口文档V0.1.html",
			//	"D:\\ts\\pdf\\正益无线_上海医药推送接口文档V0.1.pdf", "D:/q.pdf", "hahahah");
		//pdfToJpg("D:\\ts\\pdf\\正益无线_上海医药推送接口文档V0.1.pdf", "D:\\ts\\jpg",
			//	"appcan", "D:\\ts\\ic\\a.jpg");
	}
	
	public static boolean createFile(String path){
		File file =new File(path);    
		//如果文件夹不存在则创建    
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    System.out.println("文件夹不存在，创建文件："+path);  
		    file .mkdir();    
		}
		
		return true;
	}
	
	
	public static boolean moveFile(String oldPath,String newPath){
		try {
			InputStream  inStream  =  new  FileInputStream(oldPath);  //读入原文件   
	        FileOutputStream  fs  =  new  FileOutputStream(newPath);    
	        byte[]  buffer  =  new  byte[1024];    
            int byteread=0;
	        while  ((byteread  =  inStream.read(buffer))  !=  -1)  {    
 
	            fs.write(buffer,  0,  byteread);    
	        }    
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return false;
		}
		
	}
	
}
