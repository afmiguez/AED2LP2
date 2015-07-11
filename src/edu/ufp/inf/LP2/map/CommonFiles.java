/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.lp2.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author rmoreira
 */
public class CommonFiles {

    private static File getFileToSave(JFrame jf, String filepath) {
        javax.swing.filechooser.FileFilter ff = fileChooser.getFileFilter(); 
        if (ff != null && !ff.getDescription().contains("txt")) {
            System.out.println("CommonFiles - saveAsFile(): set file filter... " + filter.getDescription());
            fileChooser.setFileFilter(filter);
        }

        if (filepath.length() > 0) { //caso de se querer mudar o diretorio inicial ???
            String dirpath = filepath.substring(0, filepath.lastIndexOf(""));
            System.out.println("CommonFiles - saveAsFile():  dirpath = " + dirpath);
            fileChooser.setCurrentDirectory(new File(dirpath));
        }

        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int option = fileChooser.showSaveDialog(jf);
        if (option == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            return f;
        }
        return null;
    }
    // File Chooser and Filter
    private static JFileChooser fileChooser = new JFileChooser("C:\\Users\\AF\\MEOCloud\\4º semestre\\NetBeansProjects\\NetBeansProjects\\LP2");
    private static String[] fileExtensions = {"txt", "bmp","xml"};
    private static ExtensionsFileFilter filter = new ExtensionsFileFilter(fileExtensions);

    public static PrintWriter saveTxt(File f) throws IOException{
	    FileWriter fw=new FileWriter(f);
	    PrintWriter pw=new PrintWriter(fw);
	    return pw;
    }
    
    public static BufferedReader readTxt(JFrame jf) throws IOException{
	File file=getFileToSave(jf,"");
	FileReader fr=new FileReader(file);
	BufferedReader br=new BufferedReader(fr);

	return br;
    }
    
    public static PrintWriter saveAsTxt(JFrame jf) throws IOException{
	File file=getFileToSave(jf,null);
	
	FileWriter fw=new FileWriter(file);
	PrintWriter pw=new PrintWriter(fw);
	return pw;
	
    }
}

// javax.swing.filechooser.FileFilter is an abstract class, used to restrict
// the files that are shown in a JFileChooser; We must extend and define
// methods accept(f) and getDescription().
class ExtensionsFileFilter extends javax.swing.filechooser.FileFilter {

    private LinkedList<String> listExtensions = new LinkedList<String>();

    public ExtensionsFileFilter(String[] extensions) {
        for (String fext : extensions) {
            addExtension(fext);
        }
    }

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        } else {
            String filename = f.getName();
            String fileext = filename.substring(filename.indexOf('.') + 1);
            for (String fext : listExtensions) {
                if (fext.compareTo(fileext) == 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public String getDescription() {
        return "List accepted file extensions: " + this.listExtensions.toString();
    }

    public void addExtension(String ext) {
        if (!this.listExtensions.contains(ext)) {
            this.listExtensions.addFirst(ext);
        }
    }
    
    
}
