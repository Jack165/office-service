package com.feng.officeservice.utils;
import java.io.FileOutputStream;
import java.io.IOException;
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
 
public class TextToPdf {
  
    public static void main(String[] args) throws Exception {
    	textAddToPdf("D:\\ts\\pdf\\test.pdf","D://test.pdf","中文");
    }
    
    public static void textAddToPdf(String pdfFilePath,String newPdf,String text){
    	
         PdfReader pdfReader;
		try {
			pdfReader = new PdfReader(pdfFilePath);
			 // Get the PdfStamper object
	         PdfStamper pdfStamper = new PdfStamper(pdfReader
	             , new FileOutputStream(
	            		 newPdf));
	         addWatermark(pdfStamper, text);
	  
	         pdfStamper.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
    }
    
    
 
     private static void addWatermark(PdfStamper pdfStamper
          , String waterMarkName) {
            PdfContentByte content = null;
            BaseFont base = null;
            Rectangle pageRect = null;
            PdfGState gs = new PdfGState();
            try {
                // 设置字体
//            base = BaseFont.createFont("STSongStd-Light", 
//"UniGB-UCS2-H",
//        BaseFont.NOT_EMBEDDED);
            	
            	GetSourceFile fontFile=new GetSourceFile();
            	String fontF=fontFile.getPath("/font/SIMSUN.TTC");
             base = BaseFont.createFont( fontF+",1", "Identity-H", true);// 使用系统字体  
             //base=      BaseFont.createFont("/data/font/SIMSUN.TTC,1",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (base == null || pdfStamper == null) {
                    return;
                }
                // 设置透明度为0.4
                gs.setFillOpacity(0.4f);
                gs.setStrokeOpacity(0.4f);
                int toPage = pdfStamper.getReader().getNumberOfPages();
                for (int i = 1; i <= toPage; i++) {
                    pageRect = pdfStamper.getReader().
                       getPageSizeWithRotation(i);
                    // 计算水印X,Y坐标
                    float x = pageRect.getWidth() / 2;
                    float y = pageRect.getHeight() / 2;
                    //获得PDF最顶层
                    content = pdfStamper.getOverContent(i);
                    content.saveState();
                    // set Transparency
                    content.setGState(gs);
                    content.beginText();
                    content.setColorFill(BaseColor.GRAY);
                    content.setFontAndSize(base, 60);
                    // 水印文字成45度角倾斜
                    content.showTextAligned(Element.ALIGN_CENTER
                            , waterMarkName, x,
                            y, 45);
                    content.endText(); 
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                content = null;
                base = null;
                pageRect = null;
            }
        }
 } 