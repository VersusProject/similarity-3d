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
 * name          TotalErrorRateTest 
 * description   Adapted 2D metrics to 3D dicom and mesh scenarios.
 * @author       Benjamin Long
 * @version      1.0
 * date          
 */
package gov.nist.itl.versus.similarity3d.comparisons.measure.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.measure.Measure;
import edu.illinois.ncsa.versus.measure.Similarity;
import edu.illinois.ncsa.versus.measure.SimilarityNumber;
import edu.illinois.ncsa.versus.measure.SimilarityPercentage;

import edu.illinois.ncsa.versus.utility.HasCategory;
import edu.illinois.ncsa.versus.utility.HasHelp;
import edu.illinois.ncsa.versus.utility.HelpProvider;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

/**
 * New for 3D support
 */

import gov.nist.itl.versus.similarity3d.comparisons.MathOpsE;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelToArrayFeature;
import gov.nist.itl.versus.similarity3d.comparisons.exception.*;

public class TotalErrorRateTestMeasure implements Measure, HasCategory //, HasHelp
{
	private MathOpsE ops = new MathOpsE();

		@Override
		public SimilarityPercentage normalize(Similarity similarity) {
			return null;
		}	

        /**
         * Compares two images based on their voxels.
         *
         * @param feature1 VoxelToArrayFeature
         * @param feature2 VoxelToArrayFeature
         * @return SimilarityNumber
         * @throws Exception
         */	
		public SimilarityNumber compare(VoxelToArrayFeature feature1, VoxelToArrayFeature feature2) throws Exception 
		{
			// check for same height
			if (feature1.getHeight() != feature2.getHeight()) {
				throw new ImageCompatibilityException("Features must have the same height");
			}
			// check for same width
			if (feature1.getWidth() != feature2.getWidth()) {
				throw new ImageCompatibilityException("Features must have the same width");
			}
			// check for same depth
			if (feature1.getDepth() != feature2.getDepth()) {
				throw new ImageCompatibilityException("Features must have the same depth");
			}

			Double[] va1 = feature1.voxelsToArray();
				
				if ( va1 == null ) 
						throw new SWIndependenceException("failed to create object for VoxelToArrayFeature voxel array1");
			
			Double[] va2 = feature2.voxelsToArray();
			
				if ( va2 == null ) 
						throw new SWIndependenceException("failed to create object for VoxelToArrayFeature voxel array2");
			
			Double measurement = ops.pixel_measure_tet_nD(va1, va2 );	
				if ( measurement == null ) 
					throw new SingularityTreatmentException("Received null measurement value");	
					
			SimilarityNumber result = new SimilarityNumber(measurement.doubleValue());
					
				if ( result == null )
					throw new SingularityTreatmentException("Received null SimilarityNumber comparison value");							
							
			return result;		
		}

	
		@Override
		public SimilarityNumber compare(Descriptor feature1, Descriptor feature2)	throws Exception {

			if (feature1 instanceof VoxelToArrayFeature && feature2 instanceof VoxelToArrayFeature) {
				
				VoxelToArrayFeature desc1 = (VoxelToArrayFeature) feature1;
					if ( desc1 == null ) 
						throw new SWIndependenceException("failed to create object for VoxelToArrayFeature array1");
						
					VoxelToArrayFeature desc2 = (VoxelToArrayFeature) feature2;
					if ( desc2 == null ) 
						throw new SWIndependenceException("failed to create object for VoxelToArrayFeature array2");
					
					SimilarityNumber result = compare(desc1, desc2);
						if ( result == null ) 
							throw new SingularityTreatmentException("Received null SimilarityNumber comparison result");					
				return result;
			} 
			else {
				throw new SWIndependenceException(
							"Similarity measure expects features of type " + supportedTypesString() );
			}
		}	

		private String supportedTypesString() {
			String str="";
			Set f   = supportedFeaturesTypes();
			Iterator it = f.iterator();
			while (it.hasNext()) {
				str += ((Class)it.next()).getName() + " ";
			}
			return str;
		}

		@Override
		public Set<Class<? extends Descriptor>> supportedFeaturesTypes()
		{
			Set features = new HashSet();
			features.add( VoxelToArrayFeature.class );
			return features;
		}

		@Override
		public String getName() {
			return "TotErrRateTestMeasure";
		}

		@Override
		public Class getType() {
			return TotalErrorRateTestMeasure.class;
		}	
		
		@Override
		public String getCategory() {
			return "3D Voxel-Based Family";
		}
		
	/*
		@Override
		public InputStream getHelpZipped() {
			return HelpProvider.getHelpZipped(TotalErrorRateTestMeasure.class);
		}

		@Override
		public String getHelpSHA1() {
			return HelpProvider.getHelpSHA1(TotalErrorRateTestMeasure.class);
		}
	*/
}