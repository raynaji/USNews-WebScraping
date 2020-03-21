package ;// under your package

import java.io.IOException;
import java.text.BreakIterator;
import java.util.Locale;

// Jsoup cookbook: https://jsoup.org/cookbook/extracting-data/selector-syntax
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class usnews_websraping {



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            
	/*
	 * connects to the server and download its HTML page
	 */
            Document doc = Jsoup.connect("https://usnews.com")
                                .userAgent("Mozilla/5.0") // optional here, but may be required elsewhere
                                .get();
            
	/*
	 * Find the 2nd Top Story:
	 */
            Elements h3 = doc.select("h3"); // select <h3>
            Elements h3a = h3.select("a[href]"); // select the <a> under <h3> with href attribute. 
            Element h3a2 = h3a.get(1); //find the second element (but the index is 1) in the result set.
            System.out.println("URL: " + h3a2.attr("href")); // print the href attribute of that element.
        
	            /*
	             * Second way:
	             */
	            Elements h3a_2 = doc.select("h3 > *"); // select the direct children tag of <h3>
	            System.out.println("h3a_2: " + h3a_2.get(1).attr("href"));
	            
	            /*
	             * Third way with Regex
	             */
	            Elements h3_3 = doc.select("h3[class^=story-headline]"); // select the tag with class begins with "story-headline".
	            Element h3a2_3 = h3_3.select("a").get(1);
	            String link = h3a2_3.attr("href");
	            // System.out.println("Third way: " + h3a2_3.attr("href"));
	            System.out.print( "\n\n\n" ); // blank line
            
            
	/*
	 * Navigate to the 2nd Story
	 */
            Document second = Jsoup.connect(link)
                                .userAgent("Mozilla/5.0") // optional here, but may be required elsewhere
                                .get();
            
    /*
     * Find the header of the new page
     */
            Elements h1 = second.select("h1");
            System.out.println("Header: " + h1.text());
            System.out.print( "\n\n\n" ); // blank line
            
            
    /*
     * Find the first three sentences of the body text
     */
            
        // First of all, find the body paragraphs. 
            
            Elements body = second.select("div#ad-in-text-target"); // find the <div> with id = "ad-in-text-target"
            Elements p = body.select("div[class$=AXWJq]"); // find the first three nested <div> with class = "". Elements p3 = body.select("div[class$=AXWJq]:lt(4)");
            	/* You can not do the lt() on the p level, because the :lt(int) is the sibling index based on the parent level
            	 * Elements p3 = p.select("p:lt(3)"); 
	             * System.out.print( "\n\n\n" ); 
	             * System.out.println("Paragraphs: " + p3);
            	 */
            String text= p.text();
            //System.out.println("Body Paragraphs: " + text);
            
        // Second of all, do the sentence split with BreakIterator.
            
            BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
            String source = text;
            iterator.setText(source);
            
            // Do a for loop for three times to split the first three sentences.
            System.out.println("First three sentences:");
            int start = iterator.first();
            for(int i =1; i <4; i = i +1) 
            {
            	int end = iterator.next();
            	System.out.println("[" + i + "] " + source.substring(start,end));
            	start = end;
            }
            
            
        } catch ( IOException ex ) {
            
            System.out.println("Problem with the connection...");
            
        }
	}
	
}
