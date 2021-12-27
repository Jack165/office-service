package com.feng.officeservice.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class GetSourceFile {

	public String getPath(String sourceFloder){
		try {
			File fontfile=new File(System.getProperty("user.dir")+"/font");
			if(fontfile.exists()){
				 return System.getProperty("user.dir")+sourceFloder;
			}else{
				fontfile.mkdir();
			}
			InputStream is=this.getClass().getResourceAsStream(sourceFloder); 
		     
	        FileOutputStream  fs  =  new  FileOutputStream(System.getProperty("user.dir")+sourceFloder);  
	        byte[]  buffer  =  new  byte[1024];    
	        int byteread=0;
	        while  ((byteread  =  is.read(buffer))  !=  -1)  {    

	            fs.write(buffer,  0,  byteread);    
	        }  
	        fs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	  
		return System.getProperty("user.dir")+sourceFloder;
	}
	public static void main(String[] args) {
		GetSourceFile df=new GetSourceFile();
	 System.out.println(df.getPath("/font/SIMSUN.TTC"));
	}
}
