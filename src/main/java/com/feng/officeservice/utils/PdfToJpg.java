package com.feng.officeservice.utils;

import java.awt.Image;  
import java.awt.Rectangle;  
import java.awt.image.BufferedImage;  
 
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.RandomAccessFile;  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import javax.swing.SwingUtilities;  
import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGImageEncoder;  
import com.sun.pdfview.PDFFile;  
import com.sun.pdfview.PDFPage;  
 
/**
 *pdf格式转jpg格式
 *@author 丰硕 
 * 
 */

public class PdfToJpg {  
    public static void setup(String pdfPath,String jpgPath) throws IOException {  
 
        // load a pdf from a byte buffer  
        File file = new File(pdfPath);  
        
        RandomAccessFile raf = new RandomAccessFile(file, "r");  
        FileChannel channel = raf.getChannel();  
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel  
                .size());  
        PDFFile pdffile = new PDFFile(buf);  
 
        System.out.println("页数" + pdffile.getNumPages());  
 
        String getPdfFilePath = jpgPath;
        //判断是否存在文件夹如果不存在就创建
        File jpgfile= new File(jpgPath); 
        if  (!jpgfile .exists()  && !file .isDirectory())      
        {       
            System.out.println("文件夹不存在");
            jpgfile .mkdir();  
            System.out.println("创建文件夹："+jpgPath); 
        }
        //String getPdfFilePath = System.getProperty("user.dir")+"\\pdfPicFile";
        
        System.out.println("getPdfFilePath is  :"+getPdfFilePath);
       
        for (int i = 1; i <= pdffile.getNumPages(); i++) {  
            // draw the first page to an image  
            PDFPage page = pdffile.getPage(i);  
 
            // get the width and height for the doc at the default zoom  
            Rectangle rect = new Rectangle(0, 0, (int) page.getBBox()  
                    .getWidth(), (int) page.getBBox().getHeight());  
 
            // generate the image  
            Image img = page.getImage(rect.width, rect.height, // width &  
                                                                // height  
                    rect, // clip rect  
                    null, // null for the ImageObserver  
                    true, // fill background with white  
                    true // block until drawing is done  
                    );  
 
            BufferedImage tag = new BufferedImage(rect.width, rect.height,  
                    BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage(img, 0, 0, rect.width, rect.height,  
                    null); 
           
          
           
            FileOutputStream out = new FileOutputStream( getPdfFilePath+"/" + i + ".jpg"); // 输出到文件流
            System.out.println("成功保存图片" +getPdfFilePath+"/" + i + ".jpg");
           
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            encoder.encode(tag); // JPEG编码  
 
            out.close();  
        }  
 
        // show the image in a frame  
        // JFrame frame = new JFrame("PDF Test");  
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        // frame.add(new JLabel(new ImageIcon(img)));  
        // frame.pack();  
        // frame.setVisible(true);  
    }  
 
    public static void main(final String[] args) {  
        SwingUtilities.invokeLater(new Runnable() {  
            public void run() {  
                try {  
                    PdfToJpg.setup("D:\\ts\\pdf\\test.pdf","D:\\ts\\jpg\\");  
                } catch (IOException ex) {  
                    ex.printStackTrace();  
                }  
            }  
        });  
    }  
 
} 
 
