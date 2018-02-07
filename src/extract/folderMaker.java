package extract;
import java.io.*;
public class folderMaker {
	public static void foldermake(String path, int num, String prefix, GUI gui) {
		try {
			File[] folders=new File[num];
			for (int i=0; i<num; i++) {
				if (i+1<10)
					folders[i]=new File(path+File.separator+(prefix!=null?prefix:"")+"00"+(i+1));
				else
					folders[i]=new File(path+File.separator+(prefix!=null?prefix:"")+"0"+(i+1));
			}
			for (int i=0; i<num; i++) {
				if (!folders[i].exists()) {
					folders[i].mkdir();
					gui.updatefmlog("Created folder "+folders[i].getName()+" at "+folders[i].getParent());
				}
			}
			gui.updatefmlog("\nDone\n\n");
		}
		catch (Exception e) {}
	}
}