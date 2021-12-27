package com.feng.officeservice.utils;

import java.io.FileOutputStream;  
import java.io.FileReader;  
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
public class HtmlToPdf{  
    public static void main(String[] args) {  
        HtmlToPdf ih = new HtmlToPdf();
        HtmlToPdf.htmlCodeComeFromFile("D:\\ts.html","D:\\index.pdf");
       // ih.htmlCodeComeString("Hello中文", "D:\\index.pdf");  
    }  
      
    public static void htmlCodeComeFromFile(String filePath, String pdfPath) {  
        Document document = new Document();
        try {  
        	
            StyleSheet st = new StyleSheet();
            st.loadTagStyle("body", "leading", "16,0");  
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();  
            ArrayList p = (ArrayList) HTMLWorker.parseToList(new FileReader(filePath), st);
            GetSourceFile fontFile=new GetSourceFile();
            String fontF=fontFile.getPath("/font/SIMSUN.TTC");
           BaseFont bfChinese = BaseFont.createFont(fontF+",1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //BaseFont bfChinese = BaseFont.createFont("/data/font/SIMSUN.TTC,1",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            
          // BaseFont bfChinese = BaseFont.createFont(BaseFont.HELVETICA,
                    //BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            // BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
             Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
            // Font FontChinese = new Font(bfChinese, 12, Font.NORMAL); 
            for(int k = 0; k < p.size(); ++k) {  
            	if(k==0){
            		continue;
            	}
               // document.add((Element)p.get(k)); 
               // System.out.println(p.get(k));
                Paragraph t = new Paragraph(p.get(k).toString().replaceAll("\\[", "").replaceAll("\\]", ""), FontChinese);
                document.add(t);
            }  
            document.close();  
            System.out.println("文档创建成功");  
        }catch(Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void htmlCodeComeString(String htmlCode, String pdfPath) {  
        Document doc = new Document(PageSize.A4);
        try {  
            PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));  
            doc.open();  
            // 解决中文问题  
          //  BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            GetSourceFile fontFile=new GetSourceFile();
            String fontF=fontFile.getPath("/font/SIMSUN.TTC");
           BaseFont bfChinese = BaseFont.createFont(fontF+",1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
          
           // BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);  
            Paragraph t = new Paragraph(htmlCode, FontChinese);  
            doc.add(t);  
            doc.close();  
            System.out.println("文档创建成功");  
        }catch(Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  