package gov.nist.itl.versus.similarity3d.comparisons.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import core.io.IO;
import gov.nist.itl.versus.similarity3d.comparisons.main.Sys;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;

/**
 * This software was developed at the National Institute of Standards and
 * Technology by employees of the Federal Government in the course of
 * their official duties. Pursuant to title 17 Section 105 of the United
 * States Code this software is not subject to copyright protection and is
 * in the public domain. This software is an experimental system. NIST assumes
 * no responsibility whatsoever for its use by other parties, and makes no
 * guarantees, expressed or implied, about its quality, reliability, or
 * any other characteristic. We would appreciate acknowledgment if the
 * software is used.
 * 
 *  @author  B. Long
 *  @version 1.0
 *    
 */

/*
 * This is intended to be a simple shell through which to extract measurements.
 * 
 */

public class Main 
{
	protected String resultsFileName 	= "results.txt";
	protected String configFileName  	= "config.txt";  // lines: adapter, extractor, descriptor, mode=(inline|allpairs)
	protected String filesFileName   	= "files.txt";
	protected String measuresFileName	= "measures.txt"; 
	protected String[] fileNames;
	protected String[] measureNames;
	protected String adapterName;
	protected String extractorName;
	protected String descriptorName;
	protected String measureName;
	
	protected String operatingMode="inline";	// inline | allpairs
	
	protected Adapter 		a = null;
	protected Extractor 	x = null;
	protected Descriptor 	d1 = null, d2=null; 
	protected File 			f1 = null, f2=null;
	protected Measure[] 	measures;
	protected IO 			io = new IO();
	protected String 		SEP = "\t";
	protected String 		EOL = "\n";
	
	// utilities
	protected Sys 			sys = new Sys();
	
	//////////////////
	
	public boolean isInline(){ return (operatingMode.compareTo("inline")==0); }
	
	public static void main(String[] args) {
		Main m = new Main();
		o("init.");
		m.init();
		o("compare.");
			if (m.isInline() )
				m.inline();
			else
				m.allPairs();
		o("done.");
	}
	
	public static void o(String s) { System.out.println("- status: " + s); }
	
	
//////////////////////////////////////////////////////////////////
// Loading
//////////////////////////////////////////////////////////////////	
	
	public void init()
	{
		try {
			loadConfig(configFileName);
			loadFiles(filesFileName);
			loadMeasures(measuresFileName);
		}
		catch(Exception e) {
			o("Error: " + e.getMessage() );
		}
	}
	
		public void loadConfig(String configFile) throws Exception {
			BufferedReader br = io.openR(configFile);
			String[] cfg = io.readAllLines(br);
			io.close(br);
			if ( cfg.length != 4 ) return;
			adapterName 	= cfg[0];
			extractorName	= cfg[1];
			descriptorName	= cfg[2];
			operatingMode  	= cfg[3];			 
		}
		
		public void loadFiles(String fileListing) throws Exception {
			BufferedReader br 	= io.openR(fileListing);
			fileNames 			= io.readAllLines(br);
			io.close(br);
		}
		
		public void loadMeasures(String measureListing) throws Exception {
			BufferedReader br 	= io.openR(measureListing);
			measureNames 		= io.readAllLines(br);
			io.close(br);
			if ( measureNames==null || measureNames.length==0) throw new Exception("Could not load measures.");		
			
			int len = measureNames.length;
			measures = new Measure[len];
			for (int i=0; i < len; i++) {
				measures[i] = (Measure)Class.forName(measureNames[i]).newInstance();
			}
		}	
	
//////////////////////////////////////////////////////////////////	
// Comparisons
//////////////////////////////////////////////////////////////////
		
	public void inline()
	{
		if ( fileNames == null || fileNames.length==0 ) return;		
		ArrayList<Pair<String,Exception>> results = null;
		
		String file1=fileNames[0];
		for( String file2: fileNames ) {
			results = cmp(file1, file2);
			output(results, file1, file2);
		}
	}
	
	public void allPairs()
	{
		if ( fileNames == null || fileNames.length==0 ) return;		
		ArrayList<Pair<String,Exception>> results = null;
		
		for( String file1: fileNames ) {
		for( String file2: fileNames ) {
			results = cmp(file1, file2);
			output(results, file1, file2);
		}}		
	}
	
//////////////////////////////////////////////////////////////////
// Output
//////////////////////////////////////////////////////////////////
	
	public void output(ArrayList<Pair<String,Exception>> results, String f1, String f2) {
		String s = "";
			for (Pair<String,Exception> p: results) {
				s += outputResult(p, f1, f2);
			}
			output(s);
	}
	
	public void output(String s) {
		try {
			PrintWriter pw = io.open(resultsFileName, true);
			io.out(pw,  s);
			io.close(pw);
		}
		catch(Exception e) {
			o("Error: problem outputting results " + e.getMessage() );
		}		
	}
	
	public String outputResult(Pair<String,Exception> result, String f1, String f2 ) {
		String s = "";
		
		  s += f1 + SEP;
		  s += f2 + SEP;
		  if ( result.a != null && result.b == null ) { 
			  s += result.a + SEP;
			  s += "None"   + EOL;
		  }
		  else
		  if ( result.a != null && result.b != null ) { 
			  s += "None" + SEP;
			  s += result.b.getMessage()   + EOL;
		  }
		  else {
			  s += "Unknown" + SEP;
			  s += "Unknown" + EOL;
		  }
		return s;
	}
	
	public String outputCfg() {
		String s = "";
		
			s += adapterName + SEP;
			s += extractorName + SEP;
			s += descriptorName + EOL;
			
		return s;
	}
	
//////////////////////////////////////////////////////////////////
// Per file-pair, per measure
//////////////////////////////////////////////////////////////////
	
	public ArrayList<Pair<String,Exception>> cmp( String fileName1, String fileName2 ) 
	{
		Pair<String, Exception> result = null;
		ArrayList<Pair<String,Exception>> results = new ArrayList<Pair<String,Exception>>();
		Similarity r = null;
		
		try {
			a = (Adapter)	Class.forName(adapterName).newInstance();
			x = (Extractor)	Class.forName(extractorName).newInstance();	
			f1 = new File(fileName1);
			if ( a instanceof FileLoader ) 
				((FileLoader)a).load(f1);
			else 
				throw new Exception("(a1): Unexpected adapter type");			
			d1 = (Descriptor)x.extract(a);
			f2 = new File(fileName2);
			if ( a instanceof FileLoader ) 
				((FileLoader)a).load(f2);
			else 
				throw new Exception("(a2): Unexpected adapter type");			
			d2 = (Descriptor)x.extract(a);
			
			a = null;
			x = null;
			f1= null;
			f2= null;
			String timeStamp = "";
			
			for (Measure m: measures) {				
				try {
					measureName = m.getName();
					
					sys.beginTiming();

						r = m.compare(d1, d2);
					
					timeStamp = sys.endTiming();
					
					o("comparison: (measure,f1,f2,result)=(" + measureName + "," + fileName1 + "," + fileName2 + "," + ((r!=null)?r.getValue():"null") + ")" );
					result = new Pair<String,Exception>(measureName + SEP + r.getValue() + SEP + timeStamp, null );
					
				}
				catch(Exception e) {
					o("Error: problem during measurement of : " + measureName + SEP + "error=" + e.getMessage() + ")" );
					result = new Pair<String,Exception>(measureName, e);
				}				
				results.add( result );
			}			
		}
		catch(Exception e) {
			o("Error: booted out of entire comparison loop for given file-pair(" + fileName1 + "," + fileName2 + ", error=" + e.getMessage() + ")" );
			result = new Pair<String,Exception>(null,e);
		}
		return results;
	}

	
	class Pair<A,B> {
		public final A a;
		public final B b;
		public Pair(A a, B b){this.a=a; this.b=b;}
	}
}
