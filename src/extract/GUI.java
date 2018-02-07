package extract;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class GUI {
	JTabbedPane tabs=new JTabbedPane();
	JFrame frame=new JFrame("PDFUtil");
	ActionListener zipaction=new zipInputAction();
	ActionListener mergeaction=new mergeInputAction();
	ActionListener filemakeaction=new filemakeInputAction();
	ActionListener searchaction=new searchInputAction();
	FileNameExtensionFilter zipfilter=new FileNameExtensionFilter(".zip files", "zip");
	FileNameExtensionFilter pdffilter=new FileNameExtensionFilter(".pdf files", "pdf");
	
	//Extract objects
	JRadioButton ziponly=new JRadioButton("Extract a single zip file");
	JRadioButton zipdir=new JRadioButton("Extract .zip files in directory", true);
	JCheckBox nodeleteafterextract=new JCheckBox("Don't delete extracted directories", false);
	JCheckBox nomakepdf=new JCheckBox("Don't add extracted files to a .pdf file", false);
	//JCheckBox extractonepdf=new JCheckBox("Add all extracted files to a single .pdf", false);
	ButtonGroup zipgroup=new ButtonGroup();
	JTextField inputfolder=new JTextField(30);
	JTextField outputfolder=new JTextField(30);
	JPanel tab1=new JPanel(new BorderLayout());
	JPanel east=new JPanel(new BorderLayout());
	JPanel west=new JPanel(new BorderLayout());
	JPanel inout=new JPanel(new BorderLayout());
	JPanel in=new JPanel(new BorderLayout());
	JPanel out=new JPanel(new BorderLayout());
	JPanel stats=new JPanel(new BorderLayout());
	JPanel buttons=new JPanel(new GridLayout(0,1));
	JLabel outputfolderlabel=new JLabel("Output folder");
	JLabel inputfolderlabel=new JLabel("Input folder");
	int numtotfiles=0, numfilesremaining=0, numfilescompleted=0;
	Label numfiles=new Label("Number of files: "+numtotfiles);
	Label numfilesleft=new Label("Number of files remaining: "+numfilesremaining);
	Label numfilesdone=new Label("Number of files completed: "+numfilescompleted);
	JFileChooser fc=new JFileChooser();
	TextArea log=new TextArea(20,40);
	JButton startbutton=new JButton("Start Extract");
	JButton chooseinputbutton=new JButton("Change input");
	JButton chooseoutputbutton=new JButton("Change output");
	int returnVal;
	String ifolder, ofolder, oldifolder, oldofolder;
	File data=new File("bin\\extract\\data.txt");
	
	//Merge objects
	JFrame mergefilenameoptionsframe=new JFrame("Filename format chooser");
	JRadioButton mergedir=new JRadioButton("Merge all files in selected folder");
	JRadioButton mergeselected=new JRadioButton("Merge only selected files(doesn't work as of now)");
	JRadioButton inDprefixDparent=new JRadioButton("Input_folder - Prefix - Parent_folder_of_.pdf_files");
	JRadioButton inDprefix=new JRadioButton("Input_folder - Prefix");
	JRadioButton prefixDparent=new JRadioButton("Prefix - Parent_folder_of_.pdf_files");
	JRadioButton inDparent=new JRadioButton("Input_folder - Parent_folder_of_.pdf_files");
	JRadioButton inprefix=new JRadioButton("Input_folder Prefix");
	JRadioButton inDprefixparent=new JRadioButton("Input_folder - Prefix Parent_folder_of_.pdf_files", true);
	JRadioButton inprefixparent=new JRadioButton("Input_folder Prefix Parent_folder_of_.pdf_files");
	ButtonGroup mergefilenameoptions=new ButtonGroup();
	JButton mergeselectpdf=new JButton("Select .pdf files");
	JCheckBox mergedirsubdir=new JCheckBox("Merge .pdf files in subdirectories of input folder", true);
	ArrayList<File> userselectedpdf=new ArrayList<File>();
	ButtonGroup mergegroup=new ButtonGroup();
	JTextField mergeinputfolder=new JTextField(30);
	JTextField mergeoutputfolder=new JTextField(30);
	JTextField mergeprefix=new JTextField(10);
	JPanel mergenewfilenameoptions=new JPanel(new GridLayout(0,1));
	JPanel selectedpdflist=new JPanel(new GridLayout(0,1));
	JPanel tab2=new JPanel(new BorderLayout());
	JPanel mergeeast=new JPanel(new BorderLayout());
	JPanel mergewest=new JPanel(new BorderLayout());
	JPanel mergeinout=new JPanel(new BorderLayout());
	JPanel mergein=new JPanel(new BorderLayout());
	JPanel mergeout=new JPanel(new BorderLayout());
	JPanel mergestats=new JPanel(new BorderLayout());
	JPanel mergestatssub=new JPanel(new BorderLayout());
	JPanel mergebuttons=new JPanel(new GridLayout(0,1));
	JPanel mergeselectedfilespanel=new JPanel(new GridLayout(0,1));
	JPanel mergedirsubdirpanel=new JPanel(new GridLayout(0,1));
	JPanel mergeprefixpanel=new JPanel(new GridLayout(0,1));
	int numtotfiles2=0, numfilesremaining2=0, numfilescompleted2=0, numtotpages=0;
	JLabel mergeoutputfolderlabel=new JLabel("Output folder");
	JLabel mergeinputfolderlabel=new JLabel("Input folder");
	JLabel mergedirsubdirlabel=new JLabel(" (Used for merging multiple sets of .pdf files at once)");
	JLabel mergeprefixlabeltop=new JLabel("Prefix for filename");
	JLabel mergeprefixlabelbottom1=new JLabel("Note that this will come after parent folder of .pdf");
	JLabel mergeprefixlabelbottom2=new JLabel("If left blank, the standard option will be used");
	Label numfiles2=new Label("Number of files: "+numtotfiles2);
	Label numfilesleft2=new Label("Number of files remaining: "+numfilesremaining2);
	Label numfilesdone2=new Label("Number of files merged: "+numfilescompleted2);
	Label numofpages=new Label("Number of pages: "+numtotpages);
	JFileChooser mergefc=new JFileChooser();
	TextArea mergelog=new TextArea(20,40);
	JButton mergesetfilenameformat=new JButton("Set filename format");
	JButton mergeopenfileformatselection=new JButton("Edit output filename");
	JButton mergestartbutton=new JButton("Start Merge");
	JButton chooseinputbutton2=new JButton("Change input");
	JButton chooseoutputbutton2=new JButton("Change output");
	int returnVal2;
	String mergeifolder, mergeofolder, mergeoldifolder, mergeoldofolder, mergeparentdirmulti, mergemultifn, mergeprefixs;
	File data2=new File("bin\\extract\\data2.txt");
	
	//Folder Make objects
	int returnVal3, numfolders=0;
	String fmoldofolder, fmofolder, fmoldfprefix, fmfolderprefix;
	JPanel tab3=new JPanel(new BorderLayout());
	JTextField fmoutputfolder=new JTextField(30);
	JTextField folderstomake=new JTextField(30);
	JTextField fmprefix=new JTextField(10);
	JButton chooseoutputbutton3=new JButton("Change output");
	JButton fmstartbutton=new JButton("Start");
	TextArea log3=new TextArea(20,40);
	JLabel enterfoldernumber=new JLabel("Number of folders to create");
	JLabel outputfolderlabel3=new JLabel("Folder where folders will be created");
	JFileChooser fmfc=new JFileChooser();
	JPanel west3=new JPanel(new BorderLayout());
	JPanel outnum=new JPanel(new BorderLayout());
	JPanel fmout=new JPanel(new BorderLayout());
	JPanel east3=new JPanel();
	JPanel num=new JPanel(new BorderLayout());
	File data3=new File("bin\\extract\\data3.txt");
	
	//Search objects
	int returnVal4;
	JPanel tab4=new JPanel(new BorderLayout());
	JPanel searchwest=new JPanel(new BorderLayout());
	JPanel searcheast=new JPanel(new BorderLayout());
	JPanel searchbuttons=new JPanel(new BorderLayout());
	JPanel searchfolderpanel=new JPanel(new BorderLayout());
	JPanel searchoptions=new JPanel(new GridLayout(0,1));
	JPanel searchstats=new JPanel(new GridLayout(0,1));
	JLabel searchinputlabel=new JLabel("Folder to search in");
	Label searchfilesfound=new Label("Number of files found: 0");
	Label searchfilessearched=new Label("Number of files searched: 0");
	Label searchfolderssearched=new Label("Number of folders searched through: 0");
	JTextField searchfolder=new JTextField(30);
	JComboBox filetypes=new JComboBox(new String[] {"Select a format", ".zip", ".pdf", ".jpg", ".png"});
	TextArea searchlog=new TextArea(20,40);
	JButton searchchoosefolder=new JButton("Change search folder");
	JButton searchstartbutton=new JButton("Start Search");
	JFileChooser fcsearch=new JFileChooser();
	JRadioButton searchdironly=new JRadioButton("Search only this folder");
	JRadioButton searchall=new JRadioButton("Search this folder and all subfolders", true);
	ButtonGroup searchgroup=new ButtonGroup();
	JCheckBox searchfoldersnewwindow=new JCheckBox("Show files in seperate window", true);
	
	
	GUI() {
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		
		//Extract set
		this.fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.zipgroup.add(ziponly);
		this.zipgroup.add(zipdir);
		this.zipdir.addActionListener(zipaction);
		this.ziponly.addActionListener(zipaction);
		this.nodeleteafterextract.addActionListener(zipaction);
		this.nomakepdf.addActionListener(zipaction);
		this.startbutton.addActionListener(zipaction);
		this.chooseinputbutton.addActionListener(zipaction);
		this.chooseoutputbutton.addActionListener(zipaction);
		this.stats.add(numfiles, BorderLayout.NORTH);
		this.stats.add(numfilesleft, BorderLayout.CENTER);
		this.stats.add(numfilesdone, BorderLayout.SOUTH);
		this.in.add(inputfolder, BorderLayout.WEST);
		this.in.add(chooseinputbutton, BorderLayout.EAST);
		this.in.add(inputfolderlabel, BorderLayout.NORTH);
		this.out.add(outputfolder, BorderLayout.WEST);
		this.out.add(chooseoutputbutton, BorderLayout.EAST);
		this.out.add(outputfolderlabel, BorderLayout.NORTH);
		this.inout.add(in, BorderLayout.NORTH);
		this.inout.add(out, BorderLayout.SOUTH);
		this.buttons.add(zipdir);
		this.buttons.add(ziponly);
		this.buttons.add(nomakepdf);
		this.buttons.add(nodeleteafterextract);
		this.buttons.setBorder(BorderFactory.createTitledBorder("Options"));
		this.west.add(buttons, BorderLayout.WEST);
		this.west.add(startbutton, BorderLayout.CENTER);
		this.west.add(stats, BorderLayout.SOUTH);
		this.west.add(inout, BorderLayout.NORTH);
		this.tab1.add(west, BorderLayout.WEST);
		this.inputfolder.setEditable(true);
		this.outputfolder.setEditable(true);
		this.log.setEditable(false);
		this.stats.setBorder(BorderFactory.createTitledBorder("Progress"));
		this.east.add(log, BorderLayout.CENTER);
		this.east.setBorder(BorderFactory.createTitledBorder("Log"));
		this.tab1.add(east, BorderLayout.EAST);
		this.tabs.addTab("Extract to PDF", tab1);
		
		//Merge set
		this.mergefc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.mergestartbutton.addActionListener(mergeaction);
		this.chooseinputbutton2.addActionListener(mergeaction);
		this.chooseoutputbutton2.addActionListener(mergeaction);
		this.mergestats.add(numfiles2, BorderLayout.NORTH);
		this.mergestats.add(numfilesleft2, BorderLayout.CENTER);
		this.mergestatssub.add(numfilesdone2, BorderLayout.NORTH);
		this.mergestatssub.add(numofpages, BorderLayout.SOUTH);
		this.mergestats.add(mergestatssub, BorderLayout.SOUTH);
		this.mergein.add(mergeinputfolder, BorderLayout.WEST);
		this.mergein.add(chooseinputbutton2, BorderLayout.EAST);
		this.mergein.add(mergeinputfolderlabel, BorderLayout.NORTH);
		this.mergeout.add(mergeoutputfolder, BorderLayout.WEST);
		this.mergeout.add(chooseoutputbutton2, BorderLayout.EAST);
		this.mergeout.add(mergeoutputfolderlabel, BorderLayout.NORTH);
		this.mergeinout.add(mergein, BorderLayout.NORTH);
		this.mergeinout.add(mergeout, BorderLayout.SOUTH);
		this.mergegroup.add(mergedir);
		this.mergegroup.add(mergeselected);
		this.mergeselected.addActionListener(mergeaction);
		this.mergeselectpdf.addActionListener(mergeaction);
		this.mergedir.addActionListener(mergeaction);
		this.mergeselectpdf.setEnabled(false);
		this.mergeprefixpanel.add(mergeprefixlabeltop);
		this.mergeprefixpanel.add(mergeprefix);
		this.mergeprefixpanel.add(mergeprefixlabelbottom1);
		this.mergeprefixpanel.add(mergeprefixlabelbottom2);
		this.mergeprefixlabelbottom1.setEnabled(false);
		this.mergeprefixlabelbottom2.setEnabled(false);
		this.mergefilenameoptions.add(inDparent);
		this.mergefilenameoptions.add(inprefix);
		this.mergefilenameoptions.add(inDprefix);
		this.mergefilenameoptions.add(inDprefixparent);
		this.mergefilenameoptions.add(inDprefixDparent);
		this.mergefilenameoptions.add(inprefixparent);
		this.mergefilenameoptions.add(prefixDparent);
		this.mergesetfilenameformat.addActionListener(mergeaction);
		this.mergeopenfileformatselection.addActionListener(mergeaction);
		this.mergenewfilenameoptions.add(mergeprefixpanel);
		this.mergenewfilenameoptions.add(inDparent);
		this.mergenewfilenameoptions.add(inprefix);
		this.mergenewfilenameoptions.add(inDprefix);
		this.mergenewfilenameoptions.add(inDprefixparent);
		this.mergenewfilenameoptions.add(inDprefixDparent);
		this.mergenewfilenameoptions.add(inprefixparent);
		this.mergenewfilenameoptions.add(prefixDparent);
		this.mergenewfilenameoptions.add(mergesetfilenameformat);
		this.mergefilenameoptionsframe.add(mergenewfilenameoptions);
		this.mergefilenameoptionsframe.pack();
		this.mergefilenameoptionsframe.setVisible(false);
		this.mergeprefixpanel.setBorder(BorderFactory.createLoweredBevelBorder());
		this.mergedirsubdir.addActionListener(mergeaction);
		this.mergebuttons.add(mergeopenfileformatselection);
		this.mergebuttons.add(mergedir);
		this.mergeselectedfilespanel.add(mergeselected);
		this.mergeselectedfilespanel.add(mergeselectpdf);
		this.mergedirsubdirpanel.setBorder(BorderFactory.createLoweredBevelBorder());
		this.mergebuttons.add(mergeselectedfilespanel);
		this.mergedirsubdirlabel.setEnabled(false);
		this.mergedirsubdirpanel.add(mergedirsubdir);
		this.mergedirsubdirpanel.add(mergedirsubdirlabel);
		this.mergebuttons.add(mergedirsubdirpanel);
		this.mergedir.setSelected(true);
		this.mergebuttons.setBorder(BorderFactory.createTitledBorder("Options"));
		this.mergewest.add(mergebuttons, BorderLayout.WEST);
		this.mergewest.add(mergestartbutton, BorderLayout.CENTER);
		this.mergewest.add(mergestats, BorderLayout.SOUTH);
		this.mergewest.add(mergeinout, BorderLayout.NORTH);
		this.mergeinputfolder.setEditable(true);
		this.mergeoutputfolder.setEditable(true);
		this.mergelog.setEditable(false);
		this.mergestats.setBorder(BorderFactory.createTitledBorder("Progress"));
		this.mergeeast.add(mergelog, BorderLayout.CENTER);
		this.mergeeast.setBorder(BorderFactory.createTitledBorder("Log"));
		this.tab2.add(mergeeast, BorderLayout.EAST);
		this.tab2.add(mergewest, BorderLayout.WEST);
		this.tabs.addTab("Merge PDF", tab2);
		
		//Folder Make set
		this.log3.setEditable(false);
		this.fmoutputfolder.setEditable(true);
		this.fmfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.fmstartbutton.addActionListener(filemakeaction);
		this.chooseoutputbutton3.addActionListener(filemakeaction);
		this.east3.add(log3);
		this.east3.setBorder(BorderFactory.createTitledBorder("Log"));
		this.tab3.add(east3, BorderLayout.EAST);
		this.fmout.add(fmoutputfolder, BorderLayout.WEST);
		this.fmout.add(chooseoutputbutton3, BorderLayout.EAST);
		this.fmout.add(outputfolderlabel3, BorderLayout.NORTH);
		this.num.add(folderstomake, BorderLayout.SOUTH);
		this.num.add(enterfoldernumber, BorderLayout.NORTH);
		this.outnum.add(fmout, BorderLayout.NORTH);
		this.outnum.add(num, BorderLayout.SOUTH);
		this.west3.add(outnum, BorderLayout.NORTH);
		this.west3.add(fmstartbutton, BorderLayout.CENTER);
		this.tab3.add(west3, BorderLayout.WEST);
		this.tabs.addTab("Create Folders(GUI not implemented)", tab3);
		
		//Search set
		this.fcsearch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.searchbuttons.add(searchall);
		this.searchbuttons.add(searchdironly);
		this.searchall.addActionListener(searchaction);
		this.searchchoosefolder.addActionListener(searchaction);
		this.searchdironly.addActionListener(searchaction);
		this.searchstartbutton.addActionListener(searchaction);
		this.searchfoldersnewwindow.addActionListener(searchaction);
		this.searchoptions.add(searchall);
		this.searchoptions.add(searchdironly);
		this.searchoptions.add(searchfoldersnewwindow);
		this.searchoptions.add(filetypes);
		this.searchstats.add(searchfilesfound);
		this.searchstats.add(searchfilessearched);
		this.searchstats.add(searchfolderssearched);
		this.searchstats.setBorder(BorderFactory.createTitledBorder("Progress"));
		this.filetypes.addActionListener(searchaction);
		this.searchfolderpanel.add(searchinputlabel, BorderLayout.NORTH);
		this.searchfolderpanel.add(searchfolder, BorderLayout.WEST);
		this.searchfolderpanel.add(searchchoosefolder, BorderLayout.EAST);
		this.searchwest.add(searchoptions, BorderLayout.WEST);
		this.searchwest.add(searchfolderpanel, BorderLayout.NORTH);
		this.searchwest.add(searchstartbutton, BorderLayout.CENTER);
		this.searchwest.add(searchstats, BorderLayout.SOUTH);
		this.searcheast.add(searchlog);
		this.searchlog.setEditable(false);
		this.searchoptions.setBorder(BorderFactory.createTitledBorder("Options"));
		this.searcheast.setBorder(BorderFactory.createTitledBorder("Log"));
		this.tab4.add(searcheast, BorderLayout.EAST);
		this.tab4.add(searchwest, BorderLayout.WEST);
		this.tabs.addTab("Search for files(GUI not implemented)", tab4);
		
		
		this.frame.add(tabs);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	public void updateInput(String newInput) {
		this.inputfolder.setText(newInput);
		this.ifolder=newInput;
		if (this.ziponly.isSelected()) {
			File iftemp=new File(newInput);
			this.updateOutput(iftemp.getParent());
		}
		if (this.zipdir.isSelected())
			this.updateOutput(newInput);
	}
	public void updateOutput(String newOutput) {
		this.outputfolder.setText(newOutput);
		this.ofolder=newOutput;
	}
	public void updateLog(String nextLog) {this.log.append(nextLog+"\n");}
	public void updateNumfiles(int n) {
		this.numfiles.setText("Number of files: "+n);
		this.numtotfiles=n;
		this.numfilesremaining=n;
		this.numfilesleft.setText("Number of files remaining: "+numfilesremaining);
	}
	public void updateStats() {
		this.numfilescompleted++;
		this.numfilesremaining--;
		this.updateNumfilesLeft();
		this.updateNumfilesDone();
	}
	public void resetStats() {this.updateNumfiles(0);}
	public void updateNumfilesLeft() {this.numfilesleft.setText("Number of files remaining: "+numfilesremaining);}
	public void updateNumfilesDone() {this.numfilesdone.setText("Number of files completed: "+numfilescompleted);}
	
	
	
	
	public void updatemergeinput(String newInput) {
		this.mergeinputfolder.setText(newInput);
		this.mergeifolder=newInput;
		this.updateOutput(newInput);
	}
	public void updatemergeoutput(String newOutput) {
		this.mergeoutputfolder.setText(newOutput);
		this.mergeofolder=newOutput;
	}
	public void updatemergelog(String nextLog) {this.mergelog.append(nextLog+"\n");}
	public void updateNumfiles2(int n) {
		this.numfiles2.setText("Number of files: "+n);
		this.numtotfiles2=n;
		this.numfilesremaining2=n;
		this.numfilesleft2.setText("Number of files remaining: "+numfilesremaining2);
	}
	public void updatemergestats() {
		this.numfilescompleted2++;
		this.numfilesremaining2--;
		this.numofpages.setText("Number of pages: "+this.numtotpages);
		this.updateNumfilesLeft2();
		this.updateNumfilesDone2();
	}
	public void changeMergeInput(File[] files) {
		String newInput="";
		for(int i=0; i<files.length; i++)
			newInput+="\""+files[i].getName()+"\" ";
		this.mergeinputfolder.setText(newInput);
		this.mergeifolder=newInput;
	}
	public void updateNumfilesLeft2() {this.numfilesleft2.setText("Number of files remaining: "+numfilesremaining2);}
	public void updateNumfilesDone2() {this.numfilesdone2.setText("Number of files merged: "+numfilescompleted2);}
	
	
	
	
	public void updatefmoutput(String newInput) {
		this.fmoutputfolder.setText(newInput);
		this.fmofolder=newInput;
	}
	public void updatefmprefix(String newPrefix) {
		this.fmprefix.setText(newPrefix);
		this.fmfolderprefix=newPrefix;
	}
	public void updatefmlog(String append) {this.log3.append(append+"\n");}
	
	
	
	private class zipInputAction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			GUI gui=base.returnGUI();
			if (e.getSource()==ziponly) {
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.addChoosableFileFilter(zipfilter);
			}
			if (e.getSource()==zipdir) {
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.removeChoosableFileFilter(zipfilter);
				if (new File(inputfolder.getText()).isFile())
					inputfolder.setText(new File(inputfolder.getText()).getParent());
			}
			if (e.getSource()==startbutton) {
				ifolder=inputfolder.getText();
				ofolder=outputfolder.getText();
				if (ifolder!=null&&ofolder!=null&&((gui.ziponly.isSelected()&&new File(gui.inputfolder.getText()).isFile()&&new File(gui.inputfolder.getText()).exists())||(gui.zipdir.isSelected()&&new File(gui.inputfolder.getText()).isDirectory()&&new File(gui.inputfolder.getText()).exists()))) {
					try {
						PrintWriter writer=new PrintWriter(data);
						writer.print("");
						writer.print(inputfolder.getText());
						writer.println();
						writer.print(outputfolder.getText());
						writer.close();
					}
					catch (IOException ioe) {}
					extract.unzip(ifolder, ofolder, gui);
				}
				else {
					if (gui.ziponly.isSelected())
						JOptionPane.showMessageDialog(null, "Please make sure you have a valid input (.zip file!) and an output folder selected.");
					else if (gui.zipdir.isSelected())
						JOptionPane.showMessageDialog(null, "Please make sure you have a valid input (folder!) and an output folder.");
					else 
						JOptionPane.showMessageDialog(null, "Please select an input and an output folder!");	
				}
			}
			if (e.getSource()==chooseinputbutton) {
				if (oldifolder!=null)
					fc.setCurrentDirectory(new File(oldifolder));
				if (zipdir.isSelected())
					returnVal=fc.showDialog(null, "Select Folder");
				if (ziponly.isSelected()) {
					fc.setFileFilter(zipfilter);
					returnVal=fc.showDialog(null, "Select .zip File");
				}
				if (returnVal==JFileChooser.APPROVE_OPTION) {
					ifolder=fc.getSelectedFile().getAbsolutePath();
					updateInput(ifolder);
				}
			}
			if (e.getSource()==chooseoutputbutton) {
				if (oldofolder!=null)
					fc.setCurrentDirectory(new File(oldofolder));
				returnVal=fc.showDialog(null, "Select Folder");
				if (returnVal==JFileChooser.APPROVE_OPTION) {
					ofolder=fc.getSelectedFile().getAbsolutePath();
					updateOutput(ofolder);
				}
			}
		}
	}
	private class mergeInputAction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			GUI gui=base.returnGUI();
			if (e.getSource()==mergedir) {
				mergefc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				mergefc.setMultiSelectionEnabled(false);
				mergefc.removeChoosableFileFilter(pdffilter);
				mergeselectpdf.setEnabled(false);
				mergeinputfolder.setText("");
				mergeoutputfolder.setText("");
				chooseinputbutton2.setEnabled(true);
				mergedirsubdir.setEnabled(true);
			}
			if (e.getSource()==mergeselected) {
				mergefc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				mergefc.setMultiSelectionEnabled(true);
				mergefc.addChoosableFileFilter(pdffilter);
				mergefc.setFileFilter(pdffilter);
				mergeselectpdf.setEnabled(true);
				chooseinputbutton2.setEnabled(false);
				mergedirsubdir.setEnabled(false);
				mergedirsubdir.setSelected(false);
			}
			if (e.getSource()==mergeselectpdf) {
				returnVal2=mergefc.showDialog(null, "Select .pdf files");
				if (returnVal2==JFileChooser.APPROVE_OPTION) {
					mergeparentdirmulti=mergefc.getCurrentDirectory().getAbsolutePath();
					File[] temp=mergefc.getSelectedFiles();
					mergemultifn="";
					for (int i=0; i<temp.length; i++)
						userselectedpdf.add(temp[i]);
					for (int i=0; i<userselectedpdf.size(); i++)
						mergemultifn+=" \""+userselectedpdf.get(i).getName()+"\"";
					mergeinputfolder.setText(mergemultifn);
					mergeoutputfolder.setText(mergeparentdirmulti);
				}
			}
			if (e.getSource()==mergesetfilenameformat) {mergefilenameoptionsframe.setVisible(false);}
			if (e.getSource()==mergeopenfileformatselection) {mergefilenameoptionsframe.setVisible(true);}
			if (e.getSource()==mergestartbutton) {
				mergeifolder=mergeinputfolder.getText();
				mergeofolder=mergeoutputfolder.getText();
				mergeprefixs=mergeprefix.getText();
				if (mergeifolder!=null&&mergeofolder!=null&&!gui.mergeselected.isSelected()) {
					try {
						PrintWriter writer=new PrintWriter(data2);
						writer.print("");
						writer.print(mergeinputfolder.getText());
						writer.println();
						writer.print(mergeoutputfolder.getText());
						writer.close();
					}
					catch (IOException ioe) {}
					mergePDF.merge(mergeifolder, mergeofolder, gui, null, mergedirsubdir.isSelected()?false:true, mergeprefixs);
				}
				else if (mergeifolder!=null&&mergeofolder!=null&&gui.mergeselected.isSelected()) {
					try {
						PrintWriter writer=new PrintWriter(data2);
						writer.print("");
						writer.print(mergeparentdirmulti);
						writer.println();
						writer.print(mergeoutputfolder.getText());
						writer.close();
					}
					catch (IOException ioe) {}
					mergePDF.merge(mergeifolder, mergeofolder, gui, userselectedpdf, false, mergeprefixs);
				}
				else
					JOptionPane.showMessageDialog(null, "Please select an input and an output folder!");
			}
			if (e.getSource()==chooseinputbutton2) {
				if (mergeoldifolder!=null)
					mergefc.setCurrentDirectory(new File(mergeoldifolder));
				if (mergedir.isSelected()) {
					returnVal2=mergefc.showDialog(null, "Select Folder");
					if (returnVal2==JFileChooser.APPROVE_OPTION) {
						mergeifolder=mergefc.getSelectedFile().getAbsolutePath();
						updatemergeinput(mergeifolder);
					}
				}
			}
			if (e.getSource()==chooseoutputbutton2) {
				if (mergeoldofolder!=null)
					mergefc.setCurrentDirectory(new File(mergeoldofolder));
				returnVal2=mergefc.showDialog(null, "Select Folder");
				if (returnVal2==JFileChooser.APPROVE_OPTION) {
					mergeofolder=mergefc.getSelectedFile().getAbsolutePath();
					updatemergeoutput(mergeofolder);
				}
			}
		}
	}
	private class filemakeInputAction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			GUI gui=base.returnGUI();
			if (e.getSource()==fmstartbutton) {
				fmofolder=fmoutputfolder.getText();
				try {
					numfolders=Integer.parseInt(enterfoldernumber.getText());
				}
				catch (Exception ie) {ie.printStackTrace();}
				fmfolderprefix=fmprefix.getText();
				if (fmofolder!=null&&numfolders>0) {
					try {
						PrintWriter writer=new PrintWriter(data3);
						writer.print("");
						writer.print(fmoutputfolder.getText());
						writer.println();
						writer.print(fmprefix.getText());
						writer.println();
						writer.print(enterfoldernumber.getText());
						writer.close();
					}
					catch (IOException ioe) {}
					folderMaker.foldermake(fmofolder, numfolders, fmfolderprefix, gui);
				}
				else
					JOptionPane.showMessageDialog(null, "Please select an output folder and the number of folders to make!");
			}
			if (e.getSource()==chooseoutputbutton3) {
				if (fmofolder!=null)
					fmfc.setCurrentDirectory(new File(fmofolder));
				returnVal3=fmfc.showDialog(null, "Select Folder");
				if (returnVal3==JFileChooser.APPROVE_OPTION) {
					fmofolder=fmfc.getSelectedFile().getAbsolutePath();
					updatefmoutput(fmofolder);
				}
			}
		}
	}
	private class searchInputAction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			//GUI gui=base.returnGUI();
		}
	}
}