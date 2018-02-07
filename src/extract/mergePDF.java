package extract;
import java.io.*;
import java.util.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
public class mergePDF {
	public static void merge(String inputfolder, String outputfolder, GUI gui, ArrayList<File> selected, boolean onlyinputfolder, String prefix) {
		try {
			File folder=new File(inputfolder);
			File[] allfolders=folder.listFiles();
			File[] allfiles = null;
			for (int p=0; p<allfolders.length; p++) {
				if (onlyinputfolder)
					allfiles=allfolders;
				else if (gui.mergeselected.isSelected()) {
					selected.toArray(allfiles);
					allfolders=allfiles;
				}
				else
					allfiles=allfolders[p].listFiles();
				String outpath=outputfolder+File.separator;
				if (gui.inDparent.isSelected())
					outpath+=folder.getName()+" - "+(onlyinputfolder ? folder.getName() : allfolders[p].getName())+".pdf";
				else if (gui.inDprefix.isSelected())
					outpath+=folder.getName()+" - "+prefix+".pdf";
				else if (gui.inprefix.isSelected())
					outpath+=folder.getName()+" "+prefix+".pdf";
				else if (gui.inDprefixDparent.isSelected())
					outpath+=folder.getName()+" - "+prefix+" - "+(onlyinputfolder ? folder.getName() : allfolders[p].getName())+".pdf";
				else if (gui.inDprefixparent.isSelected())
					outpath+=folder.getName()+" - "+prefix+" "+(onlyinputfolder ? folder.getName() : allfolders[p].getName())+".pdf";
				else if (gui.prefixDparent.isSelected())
					outpath+=prefix+" - "+(onlyinputfolder ? folder.getName() : allfolders[p].getName())+".pdf";
				else  //inprefixparent
					outpath+=folder.getName()+" "+prefix+" "+(onlyinputfolder ? folder.getName() : allfolders[p].getName())+".pdf";
				File out=new File(outpath);
				int numpdf=0;
				for (int i=0; i<allfiles.length; i++) {
					if (allfiles[i].getName().contains(".pdf"))
						numpdf++;
				}
				File[] pdfs=new File[numpdf];
				int pdfsindex=0;
				for (int i=0; i<allfiles.length; i++) {
					if (allfiles[i].getName().contains(".pdf")) {
						pdfs[pdfsindex]=allfiles[i];
						pdfsindex++;
					}
				}
				gui.updateNumfiles2(numpdf);
				Document doc=new Document();
				PdfCopy copy=new PdfCopy(doc, new FileOutputStream(out));
				doc.open();
				PdfReader reader;
				int pages, addingpage=1;
				for (int i=0; i<pdfs.length; i++) {
					reader=new PdfReader(pdfs[i].getAbsolutePath());
					pages=reader.getNumberOfPages();
					gui.numtotpages+=reader.getNumberOfPages();
					for (int q=0; q<pages;) {
						copy.addPage(copy.getImportedPage(reader, ++q));
						gui.updatemergelog("Adding page: "+addingpage);
						addingpage++;
					}
					gui.updatemergelog("Added document "+(i+1)+" successfully!");
					gui.updatemergestats();
				}
				gui.updatemergelog("Done!");
				addingpage=1;
				gui.updatemergestats();
				doc.close();
				if (onlyinputfolder||gui.mergeselected.isSelected())
					break;
			}
		}
		catch (Exception i) {}
	}
}