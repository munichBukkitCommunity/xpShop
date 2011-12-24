package me.ibhh.xpShop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;

class Update implements Serializable
{ /**
 * 
 */
	private static final long serialVersionUID = 1L;
	String s; // stored
	transient int i; // transient: not stored

    public static String readAll(Reader in) throws IOException {
        if(in == null) 
        	throw new NullPointerException("in == null");
        try {
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024];
            int charsRead;
            while((charsRead = in.read(buf)) != -1) {
                sb.append(buf, 0, charsRead);
            }
            return sb.toString();
        }
        finally {
            in.close();
        }
    }
    
	public static void autoDownload(String url, String datafolder) throws Exception
	{
			String path = datafolder + "/Update/";
			File dir = new File(path);
			dir.mkdir();
			File file = new File(path + "xpShop.jar"); 
			if (file.exists())
			{
				file.delete();
				autoDownload(url, datafolder);
				return;
			}
			else
			{
			try
			{
					URL newurl = new URL(url);
			        //Eingehender Stream wird "erzeugt"
			        BufferedInputStream buffin = new BufferedInputStream(newurl.openStream());
	                BufferedOutputStream buffout = new BufferedOutputStream(new FileOutputStream(file));
	                byte[] buffer = new byte[200000];
	                int len; 
	                //Ausgelesene Daten in die Datei schreiben
	                while ((len = buffin.read(buffer)) != -1) {
	                    buffout.write(buffer, 0, len);
	                }      
	                buffout.flush();
	                buffout.close();
	                buffin.close();
	                System.out.println("[xpShop] New xpShop.jar downloaded, Look up under plugins/Update/xpShop");
				}
				finally
				{
	
				}
	      }
	}
}