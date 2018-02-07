package extract;
import java.io.*;
import java.util.*;
public class base {
	static GUI gui=new GUI();
	public static void main(String[] args) throws IOException {
		try {
			Scanner scnr=new Scanner(gui.data);
			if (gui.data!=null) {
				gui.oldifolder=scnr.nextLine();
				gui.updateInput(gui.oldifolder);
				gui.oldofolder=scnr.nextLine();
				gui.updateOutput(gui.oldofolder);
			}
			scnr.close();
			Scanner scnr2=new Scanner(gui.data2);
			if (gui.data2!=null) {
				gui.mergeoldifolder=scnr2.nextLine();
				gui.updatemergeinput(gui.mergeoldifolder);
				gui.mergeoldofolder=scnr2.nextLine();
				gui.updatemergeoutput(gui.mergeoldofolder);
			}
			scnr2.close();
			Scanner scnr3=new Scanner(gui.data3);
			if (gui.data2!=null) {
				gui.fmoldofolder=scnr3.nextLine();
				gui.updatefmoutput(gui.fmoldofolder);
				gui.fmoldfprefix=scnr3.nextLine();
				gui.updatefmprefix(gui.fmoldfprefix);
			}
			scnr3.close();
		}
		catch (IOException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
		
	}
	public static GUI returnGUI() {return gui;}
}