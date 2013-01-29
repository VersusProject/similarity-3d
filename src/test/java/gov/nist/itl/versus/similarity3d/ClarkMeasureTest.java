/*
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
 * @author       Benjamin Long
 * @version      1.0
 * date          
 */
package gov.nist.itl.versus.similarity3d;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.adapter.impl.ImageObjectAdapter;
import edu.illinois.ncsa.versus.extract.impl.GrayscaleHistogramExtractor;
import edu.illinois.ncsa.versus.descriptor.impl.GrayscaleHistogramDescriptor;
import edu.illinois.ncsa.versus.extract.impl.RGBHistogramExtractor;
import edu.illinois.ncsa.versus.descriptor.impl.RGBHistogramDescriptor;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;

import gov.nist.itl.versus.similarity3d.comparisons.measure.impl.*;
import gov.nist.itl.versus.similarity3d.comparisons.MathOpsE;


import gov.nist.itl.versus.similarity3d.comparisons.exception.*;

// new for 3D

// dicom scenario
import gov.nist.itl.versus.similarity3d.comparisons.adapter.impl.DicomImageObjectAdapter;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelHistogramDescriptor;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelToArrayFeature;
import gov.nist.itl.versus.similarity3d.comparisons.extract.impl.VoxelHistogramExtractor;
import gov.nist.itl.versus.similarity3d.comparisons.extract.impl.VoxelToArrayFeatureExtractor;

// mesho scenario
import edu.ncsa.model.Mesh;
import edu.ncsa.model.MeshAuxiliary.Point;
import edu.ncsa.model.loaders.MeshLoader_OBJ;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.impl.OBJImageObjectAdapter;

/**
 * Clark Test
 */
public class ClarkMeasureTest extends junit.framework.TestCase
{
	// default settings if no external file pairs are specified
	private static String fileName1 = "data/small_ct_set/1.3.6.1.4.1.9590.100.1.1.342414932842009174.342907622617267.dcm";
	private static String fileName2 = "data/small_ct_set/1.3.6.1.4.1.9590.100.1.1.342414932842009174.342907622798281.dcm";
	
	private static String fileDir1	= "data/small_ct_set/";
	private static String fileDir2	= "data/small_ct_set2/";
	
	private static String fileName3 = "data/obj/cube.obj";
	private static String fileName4 = "data/obj/tetrahedron.obj";
	
	private static String carlSimonFileName1="data/carl_simon/";
	private static String carlSimonFileName2="data/carl_simon/";
	
	private static String qibaFileName1="data/qiba/qiba1/00020001";
	private static String qibaFileName2="data/qiba/qiba2/00020009";
	
	private static String qibaDir1="data/qiba/qiba1/";
	private static String qibaDir2="data/qiba/qiba2/";	
	
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
			//throw new ImageCompatibilityException( "failed to load file via adapter into memory" );		
			String rString = "";
			if ( result != null ) 
				rString = fileName1 + SEP + fileName2 + SEP + "DicomImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + result.getValue() ;
			else 
				rString = fileName1 + SEP + fileName2 + SEP + "DicomImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + "null" ;
			throw new SingularityTreatmentException( "" + rString + ": Error [" + e.getMessage() + "]");			
		} 
		
		return result;
	}

////////////////

	// dicom-dir-scenario
	public static SimilarityNumber dicomDir( String fileDir1, String fileDir2 ) throws Exception
	{
		DicomImageObjectAdapter a1 = new DicomImageObjectAdapter();
			if ( a1 == null )
				throw new SWIndependenceException("failed to create DicomImageObjectAdapter adapter for dir1");
				
		DicomImageObjectAdapter a2 = new DicomImageObjectAdapter();
			if ( a2 == null )
				throw new SWIndependenceException("failed to create DicomImageObjectAdapter adapter for dir2");
				
		SimilarityNumber result = null;
		
		try {
			File f1 = new File(fileDir1);			
				if ( f1 == null )
					throw new ImageCompatibilityException("failed to create file object for dir1");
			
			File f2 = new File(fileDir2);
				if ( f2 == null )
					throw new ImageCompatibilityException("failed to create file object for dir2");

			a1.loadDir( f1 );
				if ( a1 == null )
					throw new SWIndependenceException("failed to load file1 into adapter1");
			a2.loadDir( f2 );		
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
			
			
			String rString = fileDir1 + SEP + fileDir2 + SEP + "DicomImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + result.getValue() ;
			System.out.println ( rString );
	
		} catch (Exception e) {
			//throw new ImageCompatibilityException( "failed to load file via adapter into memory" );		
			String rString = "";
			if ( result != null ) 
				rString = fileDir1 + SEP + fileDir2 + SEP + "DicomImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + result.getValue() ;
			else 
				rString = fileDir1 + SEP + fileDir2 + SEP + "DicomImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + "null" ;
			throw new SingularityTreatmentException( "" + rString + ": Error [" + e.getMessage() + "]");			
		} 
		
		return result;
	}

////////////////


	// mesh-scenario
	public static SimilarityNumber mesh( String fileName1, String fileName2 ) throws Exception
	{
		OBJImageObjectAdapter a1 = new OBJImageObjectAdapter();
			if ( a1 == null )
				throw new SWIndependenceException("failed to create OBJImageObjectAdapter adapter for file1");
				
		OBJImageObjectAdapter a2 = new OBJImageObjectAdapter();
			if ( a2 == null )
				throw new SWIndependenceException("failed to create OBJImageObjectAdapter adapter for file2");
				
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
			
			
			String rString = fileName1 + SEP + fileName2 + SEP + "OBJImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + result.getValue() ;
			System.out.println ( rString );
	
		} catch (Exception e) {
			//throw new ImageCompatibilityException( "failed to load file via adapter into memory" );	
			String rString = "";
			if ( result != null ) 
				rString = fileName1 + SEP + fileName2 + SEP + "OBJImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + result.getValue() ;
			else 
				rString = fileName1 + SEP + fileName2 + SEP + "OBJImageObjectAdapter" + SEP + "VoxelHistogramDescriptor" + SEP + "ClarkMeasure" + SEP + "null" ;
			throw new SingularityTreatmentException( "" + rString + ": Error [" + e.getMessage() + "]");			
		} 
		
		return result;
	}

////////////////

	
	public static String o( String category, String message, String details ) {
		String str = "[Histogram metric]\t[" + category + "]\t[" + message + "]\t[" + details + "]";
		//System.out.println( str );
		return str; 
	}	
	
	@Test
	public void test() {
		try {
				System.out.println("scenario-1.dicom.histogram-measure.file-comparison");
				dicom(fileName1, fileName2);
				dicom(qibaFileName1, qibaFileName2);
				
				System.out.println("scenario-2.dicom.histogram-measure.dir-comparison");
				dicomDir(fileDir1, fileDir2);
				dicomDir(qibaDir1, qibaDir2);
				
				//System.out.println("scenario-3.obj/mesh.histogram-measure.file-comparison");
				//mesh(fileName3, fileName4);
		}
		catch( Exception e ) {
			System.out.println("Error:" + e.getMessage() );
		}		
	}	

	public static void main( String[] args )
	{
		org.junit.runner.JUnitCore.runClasses( ClarkMeasureTest.class );
	}
}