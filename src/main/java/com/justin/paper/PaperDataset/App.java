package com.justin.paper.PaperDataset;

import java.io.*;
import java.util.Stack;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.*;

import com.hp.hpl.jena.rdf.model.*;

public class App {

	static String personURI = "http://somewhere/person";
	static String infile = "C:\\Users\\Justin\\workspace\\PaperDataset\\Paper_dataset.xls";
	static String outfile = "C:\\Users\\Justin\\workspace\\PaperDataset\\PaperRDF.rdf";
	
	@SuppressWarnings("resource")
	public static void main (String args[]) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(infile));
			PrintWriter pw = new PrintWriter(outfile, "UTF-8");
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
		    
		    // create an empty model
			Model model = ModelFactory.createDefaultModel();
			model.createResource(personURI);
			
		    for(int i = 0; i < rows-1; i++) {
				model.getResource(personURI)
				    .addProperty(PAPER.Paper, model.createResource()
						.addProperty(PAPER.Title, titles.pop())
						.addProperty(PAPER.Authors, model.createResource()
							.addProperty(PAPER.Author1, authors1.pop())
							.addProperty(PAPER.Author2, authors2.pop())
							.addProperty(PAPER.Author3, authors3.pop())));
		    }
		    
		    /*for(int i = 0; i < rows-1; i++) {
		    	model.createResource(personURI)
		    		.addProperty(PAPER.Title, model.createResource(titles.pop())
				    	.addProperty(PAPER.Author1, authors1.pop())
						.addProperty(PAPER.Author2, authors2.pop())
						.addProperty(PAPER.Author3, authors3.pop()));
		    }*/
		    
		    model.write(System.out);
			model.write(pw);
			pw.close();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
    }
}