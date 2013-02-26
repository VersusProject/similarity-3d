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
 * name          Clark 
 * description   
 * @author       B. Long
 * @version      1.0
 * 
 */
package test.gov.nist.itl.versus.similarity3d.comparisons.measure.impl;

import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import gov.nist.itl.versus.similarity3d.comparisons.MathOpsE;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.impl.DicomImageObjectAdapter;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelHistogramDescriptor;
import gov.nist.itl.versus.similarity3d.comparisons.exception.ImageCompatibilityException;
import gov.nist.itl.versus.similarity3d.comparisons.exception.SWIndependenceException;
import gov.nist.itl.versus.similarity3d.comparisons.exception.SingularityTreatmentException;
import gov.nist.itl.versus.similarity3d.comparisons.extract.impl.VoxelHistogramExtractor;
import gov.nist.itl.versus.similarity3d.comparisons.measure.impl.*;
import java.io.File;
import org.junit.Test;

/**
 * Clark Test
 */
public class ClarkMeasureTest extends junit.framework.TestCase
{
	// default settings if no external file pairs are specified
	private static String fileName1 = "data/small_ct_set_bin/1.3.6.1.4.1.9590.100.1.1.342414932842009174.342907622617267.dcm";
	private static String fileName2 = "data/small_ct_set_bin/1.3.6.1.4.1.9590.100.1.1.342414932842009174.342907622798281.dcm";
	
	private static String SEP = ",";	
	private static MathOpsE mopsE = new MathOpsE();

	public ClarkMeasureTest(){}
	
	// dicom-scenario
	public static SimilarityNumber dicom( String fileName1, String fileName2 ) throws Exception
	{
		DicomImageObjectAdapter a1 = new DicomImageObjectAdapter();
			if ( a1 == null )
				throw new SWIndependenceException("failed to create DicomImageObjectAdapter adapter for file1");
				
		DicomImageObjectAdapter a2 = new DicomImageObjectAdapter();
			if ( a2 == null )
				throw new SWIndependenceException("failed to create DicomImageObjectAdapter adapter for file2");
				
		SimilarityNumber result = null;
		
		try {
			File f1 = new File(fileName1);			
				if ( f1 == null )
					throw new ImageCompatibilityException("failed to create file object for file1");
			
			File f2 = new File(fileName2);
				if ( f2 == null )
					throw new ImageCompatibilityException("failed to create file object for file2");

			a1.load( f1 );
				if ( a1 == null )
					throw new SWIndependenceException("failed to load file1 into adapter1");
			a2.load( f2 );		
				if ( a2 == null )
					throw new SWIndependenceException("failed to load file2 into adapter2");			
					
			VoxelHistogramExtractor x1 = new VoxelHistogramExtractor();
				if ( x1 == null )
					throw new SWIndependenceException("failed to create VoxelHistogramExtractor object for extractor1");			
					
			VoxelHistogramExtractor x2 = new VoxelHistogramExtractor();
				if ( x2 == null )
					throw new SWIndependenceException("failed to create VoxelHistogramExtractor object for extractor2");						
			
			VoxelHistogramDescriptor desc1 = (VoxelHistogramDescriptor) x1.extract(a1);
				if ( desc1 == null )
					throw new SWIndependenceException("failed to extract VoxelHistogramDescriptor feature1 via extractor1");			
					
			VoxelHistogramDescriptor desc2 = (VoxelHistogramDescriptor) x2.extract(a2);
				if ( desc2 == null )
					throw new SWIndependenceException("failed to extract VoxelHistogramDescriptor feature2 via extractor2");
			
			ClarkMeasure m 		= new ClarkMeasure();
			
				if ( m == null )
					throw new SWIndependenceException("failed to create ClarkMeasure object for measure");
					
			result 	= m.compare(desc1, desc2);
			
				if ( result == null )
					throw new SingularityTreatmentException("received null comparison result");			
			
			
			String rString = fileName1 + SEP + fileName2 + SEP + "DicomImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + result.getValue() ;
			System.out.println ( rString );
	
		} catch (Exception e) {	
			String rString = "";
			if ( result != null ) 
				rString = fileName1 + SEP + fileName2 + SEP + "DicomImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + result.getValue() ;
			else 
				rString = fileName1 + SEP + fileName2 + SEP + "DicomImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + "null" ;
			throw new SingularityTreatmentException( "" + rString + ": Error [" + e.getMessage() + "]");			
		} 
		
		return result;
	}
	
	public static String o( String category, String message, String details ) {
		String str = "[Histogram metric]\t[" + category + "]\t[" + message + "]\t[" + details + "]";
		return str; 
	}	
	
	@Test
	public void test() {
		try {
			dicom(fileName1, fileName2);
		}
		catch( Exception e ) {}		
	}	

	public static void main( String[] args )
	{
		org.junit.runner.JUnitCore.runClasses( ClarkMeasureTest.class );
	}
}