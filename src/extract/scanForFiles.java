package extract;
import java.io.*;
import java.util.*;
public class scanForFiles {
	public static ArrayList<File> scan(File parentdir, String extension) {
		ArrayList<File> searchresults=new ArrayList<File>();
		File[] content=parentdir.listFiles();
		for (int i=0; i<content.length; i++) {
			if (content[i].isDirectory())
				searchresults.addAll(loop(content[i].listFiles(), searchresults, extension));
			if (content[i].isFile()) {
				if (content[i].getName().substring(content[i].getName().lastIndexOf(".")).equals(extension))
					searchresults.add(content[i]);
			}
		}
		for (int i=0; i<searchresults.size(); i++) {
			for (int w=0; w<searchresults.size(); w++) {
				if (searchresults.get(i).getAbsolutePath().equals(searchresults.get(w).getAbsolutePath())&&w!=i)
					searchresults.remove(w);
			}
		}
		return searchresults;
	}
	public static ArrayList<File> loop(File[] content, ArrayList<File> searchresults, String extension) {
		for (int i=0; i<content.length; i++) {
			if (content[i].isDirectory())
				loop(content[i].listFiles(), searchresults, extension);
			if (content[i].isFile()&&content[i].getName().contains(".")) {
				if (content[i].getName().substring(content[i].getName().lastIndexOf(".")).equals(extension)) {
					searchresults.add(content[i]);
				}
			}
		}
		return searchresults;
	}
}