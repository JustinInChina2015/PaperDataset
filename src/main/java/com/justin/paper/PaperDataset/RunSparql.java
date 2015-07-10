package com.justin.paper.PaperDataset;

import java.io.*;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;

public class RunSparql {
	
	static String infile = "C:\\Users\\Justin\\workspace\\PaperDataset\\PaperRDF.rdf";
	static String outfile = "C:\\Users\\Justin\\workspace\\PaperDataset\\PaperSPARQL.txt";
	
	public static void main (String[] args) throws IOException {
		try {
			InputStream in = new FileInputStream(new File(infile));
			OutputStream os = new FileOutputStream(new File(outfile));
			
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createMemModelMaker().createDefaultModel();
			model.read(in,null); // null base URI, since model URIs are absolute
			in.close();
			
			// Create a new query
			String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> " + 
				"SELECT ?title ?author1 ?author2 ?author3 " +
				"WHERE {" +
				"	   ?x vcard:Title ?title ; " +
				"         vcard:Authors ?y . " + 
				"      ?y vcard:Author1 ?author1 . " +
				"	   ?y vcard:Author2 ?author2 . " +
				"	   ?y vcard:Author3 ?author3 . " +
				"      }";
			
			Query query = QueryFactory.create(queryString);
			
			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			
			// Output query results	
			ResultSetFormatter.out(System.out, results, query);
			ResultSetFormatter.out(os, results, query);
			
			// Important - free up resources used running the query
			qe.close();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
}