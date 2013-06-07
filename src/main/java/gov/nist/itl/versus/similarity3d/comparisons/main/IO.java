package gov.nist.itl.versus.similarity3d.comparisons.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

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
 * A generic class for encapsulating all IO functionality to/from the application.
 */

public class IO 
{	
	
  // ----------------------------------------------
  // I/O utility functions
  // ----------------------------------------------
	
	/*
	 * Open file.
	 */
	public PrintWriter open( String fileName )
	{
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(fileName, false));	// where true=do append; false=do not append
		} catch (Exception e) {
			System.out.println( "Error: " + e.getMessage() );
		}
		return pw;
	}
	
	public PrintWriter open( String fileName, boolean append )
	{
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(fileName, append));	// where true=do append; false=do not append
		} catch (Exception e) {
			System.out.println( "Error: " + e.getMessage() );
		}
		return pw;
	}	
	
	public BufferedReader openR( String fileName ) throws Exception {
		BufferedReader rdr = null;
		try {
			rdr = new BufferedReader(new FileReader(fileName));
		}
		catch ( Exception e ) {
			throw e;
		}
		return rdr;
	}
	
	public void close( BufferedReader rdr ) throws Exception {
		try {
			rdr.close();
		}
		catch (Exception e ) {
			throw e;
		}
	}
	
	
	public int in( BufferedReader rdr ) throws Exception {
		int c = -1;
		try {
			c = rdr.read();
		}
		catch( Exception e ) {
			throw e;
		}
		return c;
	}
	
	public String inln( BufferedReader rdr ) throws Exception {
		String s = "";
		try {
			s = rdr.readLine();
		}
		catch( Exception e ) {
			throw e;
		}
		return s;		
	}

	public String[] readAllLines( BufferedReader rdr ) throws Exception {
		ArrayList<String> lines = new ArrayList<String>();
		String line = "";
		try {
			line = inln(rdr);
			while (line != null) {
				lines.add( line );
				line = inln(rdr);
			}
			close(rdr);
		}
		catch( Exception e ) {
			throw e;
		}
		String[] str = new String[lines.size()];
		for (int i=0; i < lines.size(); i++) str[i] = lines.get(i);
		return str;
	}

	public String readAllToString( BufferedReader rdr ) throws Exception {
		String s = "";
		String line = "";
		try {
			line = inln(rdr);
			while (line != null) {
				s += line + "\n";
				line = inln(rdr);
			}
			close(rdr);
		}
		catch( Exception e ) {
			throw e;
		}
		return s;
	}
	
	/*
	 *  Equivalent to println, routed via output stream.
	 */
	public void outln( final PrintWriter pw, final String msg ) {
		try {
			if ( pw != null ) {
				pw.println( msg );
			}
			else {
				System.out.println( msg );
			}
		} catch (Exception e) {
			System.out.println( "Error: " + e.getMessage() );
		}			
	}
	
	/*
	 *  Equivalent to print, routed via output stream.
	 */
	public void out( final PrintWriter pw, final String msg ) {
		try {
			if ( pw != null ) {
				pw.print( msg );
			}
			else {
				System.out.print( msg );
			}
		} catch (Exception e) {
			System.out.println( "Error: " + e.getMessage() );
		}			
	}
	
	/*
	 * Close file.
	 */
	public void close( final PrintWriter pw ) {
		try {
			if ( pw!=null )
				pw.close();
		} catch (Exception e) {
			System.out.println( "Error: " + e.getMessage() );
		}				
	}
	
}
