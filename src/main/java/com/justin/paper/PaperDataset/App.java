package com.justin.paper.PaperDataset;

import java.io.*;
import java.util.Stack;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.VCARD;

public class App {
	// some definitions
	static String personURI = "http://somewhere/person";
	static String infile = "C:\\Users\\Justin\\workspace\\Papers\\Paper_dataset.xls";
	
	public static void main (String args[]) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(infile));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
		    HSSFSheet sheet = wb.getSheetAt(0);
		    HSSFRow row;
		    HSSFCell cell;
		    
		    int rows = sheet.getPhysicalNumberOfRows(); // Number of Rows
		    int cols = 0;								// Number of Columns
		    int tmp = 0;
		    
		    Stack<String> titles = new Stack<String>();
		    Stack<String> authors1 = new Stack<String>();
		    Stack<String> authors2 = new Stack<String>();
		    Stack<String> authors3 = new Stack<String>();

		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) {
		            	cols = tmp;
		            }
		        }
		    }

		    // Skip the first row
		    for(int r = 1; r < rows+1; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		            for(int c = 0; c < cols; c++) {
		                cell = row.getCell(c);
		                if(cell != null && c % cols == 0) {
		                    titles.push(cell.toString());
		                } else if (cell != null && c % cols == 1) { 
		                	authors1.push(cell.toString());
		                } else if (cell != null && c % cols == 2) { 
		                	authors2.push(cell.toString());
		                } else if (cell != null && c % cols == 3) { 
		                	authors3.push(cell.toString());
		                }
		            }
		        }
		    }
		    
		    for(int i = 0; i < rows-1; i++) {
				// create an empty model
				Model model = ModelFactory.createDefaultModel();
				// create the resource and add properties cascading style
				Resource paper = 
						model.createResource(personURI)
							.addProperty(PAPER.Title, titles.pop())
							.addProperty(PAPER.Authors, model.createResource()
								.addProperty(PAPER.Author1,  authors1.pop())
								.addProperty(PAPER.Author2,  authors2.pop())
								.addProperty(PAPER.Author3,  authors3.pop()));
				model.write(System.out);
		    }
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
    }
}