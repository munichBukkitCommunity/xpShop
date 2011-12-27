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

	public static void autoDownload(String url, String path, String name) throws Exception
	{
		File dir = new File(path);
		if (!dir.exists())
		{
			dir.mkdir();
		}
		File file = new File(path + name); 
		
		if (file.exists() && name.equals("xpShop.jar"))
		{
			file.delete();
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
				System.out.println("[xpShop] New " + name + " downloaded, Look up under " + path);
			}
			finally
			{
			}
			return;
		}
		if(!file.exists())
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
				System.out.println("[xpShop] New " + name +" downloaded, Look up under " + path);
			}
			finally
			{
			}
		}
		return;
	}
}