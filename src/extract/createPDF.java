package extract;
import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
public class createPDF {
	public static void createPdf (File f, GUI gui) {
		Document doc=new Document();
		try {
			String path=f.getAbsolutePath();
			String[] filenames=f.list();
			File[] files;
			gui.updateLog(path);
			int count=0;
			for (int i=0; i<filenames.length; i++) {
				if (filenames[i].contains(".jpg")||filenames[i].contains(".jpeg"))
					count++;
			}
			files=new File[count];
			int fs=0;
			for (int i=0; i<filenames.length; i++) {
				if (filenames[i].contains(".jpg")||filenames[i].contains(".jpeg")) {
					files[fs]=new File(path+File.separator+filenames[i]);
					fs++;
				}
			}
			File out=new File(path+".pdf");
			if (!out.exists())
				out.createNewFile();
			PdfWriter writer=PdfWriter.getInstance(doc, new FileOutputStream(out));
			for (int i=0; i<files.length; i++) {
				gui.updateLog("Adding File: "+files[i].getName());
				Image image=Image.getInstance(files[i].getAbsolutePath());
				doc.setMargins(0, 0, 0, 0);
				doc.setPageSize(new Rectangle(image.getWidth(), image.getHeight()));
				doc.open();
				doc.add(image);
				if (i+1!=files.length) {
					doc.newPage();
					writer.setPageEmpty(false);
				}
			}
			gui.updateLog("Success!\n");
			doc.close();
		}
		catch (DocumentException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
	}
}