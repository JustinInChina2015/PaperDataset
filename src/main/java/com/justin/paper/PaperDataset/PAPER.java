package com.justin.paper.PaperDataset;

import com.hp.hpl.jena.rdf.model.*;

/** VCARD vocabulary class for namespace http://www.w3.org/2001/vcard-rdf/3.0#
 */
public class PAPER {

    protected static final String uri ="http://www.w3.org/2001/vcard-rdf/3.0#";

    /** returns the URI for this schema
     * @return the URI for this schema
     */
    public static String getURI() {
          return uri;
    }

    private static Model m = ModelFactory.createDefaultModel();
    
    public static final Property Paper = m.createProperty(uri, "Paper" );
    public static final Property Title = m.createProperty(uri, "Title" );
    public static final Property Author1 = m.createProperty(uri, "Author1" );
    public static final Property Author2 = m.createProperty(uri, "Author2" );
    public static final Property Author3 = m.createProperty(uri, "Author3" );
    public static final Property Authors = m.createProperty(uri, "Authors" );
}
