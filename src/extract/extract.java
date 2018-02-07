package extract;
import java.io.*;
import java.util.zip.*;
public class extract {
	public static void unzip(String zipfilefolder, String outputfolder, GUI gui) {
		byte[] buffer=new byte[1024];
		try {
			File zipfile=new File(zipfilefolder);
			File[] af=null, zips=null;
			String spdir=null;
			ZipInputStream zis=null;
			int n=1, zs;
			if (gui.zipdir.isSelected()) {
				af=zipfile.listFiles();
				n=0;
				zs=0;
				for (int i=0; i<af.length; i++) {
					if (af[i].getName().contains(".zip"))
						n++;
				}
				zips=new File[n];
				for (int i=0; i<af.length; i++) {
					if (af[i].getName().contains(".zip")) {
						zips[zs]=af[i];
						zs++;
					}
				}
			}
			gui.updateNumfiles(n);
			int numrun=gui.ziponly.isSelected() ? 1 : zips.length;
			for (int i=0; i<numrun; i++) {
				if (gui.zipdir.isSelected())
					spdir=zips[i].getName();
				if (gui.ziponly.isSelected())
					spdir=zipfile.getName();
				int len1=spdir.length();
				spdir=spdir.substring(0, len1-4);
				File oFolder=new File(outputfolder+File.separator+spdir);
				if (!oFolder.exists())
					oFolder.mkdir();
				if (gui.zipdir.isSelected())
					zis=new ZipInputStream(new FileInputStream(zips[i]));
				if (gui.ziponly.isSelected())
					zis=new ZipInputStream(new FileInputStream(zipfile));
				ZipEntry ze=zis.getNextEntry();
				while(ze!=null) {
					String filename=ze.getName();
					File newFile=new File(oFolder+File.separator+filename);
					gui.updateLog("file unzip : "+ newFile.getAbsoluteFile());
					new File(newFile.getParent()).mkdirs();
					FileOutputStream fos=new FileOutputStream(newFile);
					int len;
					while ((len=zis.read(buffer))>0)
						fos.write(buffer,0,len);
					fos.close();
					ze=zis.getNextEntry();
				}
				zis.closeEntry();
				zis.close();
				if (!gui.nomakepdf.isSelected()) {
					gui.updateLog("Done extracting.\nNow adding files to PDF document");
					createPDF.createPdf(oFolder, gui);
				}
				if (gui.nomakepdf.isSelected())
					gui.updateLog("Done extracting.");
				gui.updateStats();
				if (!gui.nodeleteafterextract.isSelected()) {
					gui.updateLog("Now deleting unzipped folders created in process of adding files to the PDF file...");
					File[] del=oFolder.listFiles();
					for (int w=0; w<del.length; w++)
						del[w].delete();
					oFolder.delete();
					gui.updateLog("Done!");
				}
				if (gui.ziponly.isSelected())
					break;
			}
			gui.resetStats();
		}
		catch (IOException e) {e.printStackTrace();}
	}
}