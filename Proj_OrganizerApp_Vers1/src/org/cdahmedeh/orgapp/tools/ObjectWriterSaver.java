package org.cdahmedeh.orgapp.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectWriterSaver {
	public static Object readData(String defaultFile) {
		Object coreLoad = null;
		try {
			FileInputStream file 	= new FileInputStream(defaultFile);
			ObjectInputStream in 	= new ObjectInputStream(file);
			coreLoad = in.readObject();
			in.close();
			file.close();
		} catch (IOException e) 			{e.printStackTrace();
		} catch (ClassNotFoundException e) 	{e.printStackTrace();}
		return coreLoad;
	}

	public static void writeData(String defaultFile, Object core) {
		try {
			FileOutputStream file  	= new FileOutputStream(defaultFile);
			ObjectOutputStream out 	= new ObjectOutputStream(file);
			out.writeObject(core);
			out.close();
			file.close();
		} catch (IOException e) 			{e.printStackTrace();}
	}
}
