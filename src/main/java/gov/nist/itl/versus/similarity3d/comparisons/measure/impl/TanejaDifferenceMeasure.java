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
 * name          TanejaDifference 
 * description   
 * @author       B. Long
 * @version      1.0
 * 
 */
package gov.nist.itl.versus.similarity3d.comparisons.measure.impl;

import java.io.InputStream;
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
import gov.nist.itl.versus.similarity3d.comparisons.MathOpsE;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelHistogramDescriptor;
import gov.nist.itl.versus.similarity3d.comparisons.exception.ImageCompatibilityException;
import gov.nist.itl.versus.similarity3d.comparisons.exception.MathCompatibilityException;
import gov.nist.itl.versus.similarity3d.comparisons.exception.SWIndependenceException;
import gov.nist.itl.versus.similarity3d.comparisons.exception.SingularityTreatmentException;


public class TanejaDifferenceMeasure implements Measure, HasCategory, HasHelp
{
	private MathOpsE ops = new MathOpsE();

		@Override
		public SimilarityPercentage normalize(Similarity similarity) {
			return null;
		}	

        /**
         * Compares two VoxelHistogramDescriptors.
         *
         * @param feature1 VoxelHistogramDescriptor
         * @param feature2 VoxelHistogramDescriptor
         * @return SimilarityNumber
         * @throws Exception
         */	
		public SimilarityNumber compare(VoxelHistogramDescriptor feature1, VoxelHistogramDescriptor feature2) throws Exception 
		{
					// Check feature lengths, they must be equal
					if( feature1.getLength() != feature2.getLength() ) {
							throw new ImageCompatibilityException("Features must have the same length");
					}
					
					Double[] normHist1 = ops.normalizeHistogram( feature1.getHistogram() );
					
						if ( normHist1 == null ) 
							throw new MathCompatibilityException("Histogram normalization failed for VoxelToArrayFeature histogram1");					
					
					Double[] normHist2 = ops.normalizeHistogram( feature1.getHistogram() );
					
						if ( normHist2 == null ) 
							throw new MathCompatibilityException("Histogram normalization failed for VoxelToArrayFeature histogram2");

					Double measurement  = ops.histogram_measure_taneja_difference( normHist1, normHist2 );
									
						if ( measurement == null ) 
							throw new SingularityTreatmentException("Received null measurement value");	
							
					SimilarityNumber result = new SimilarityNumber(measurement.doubleValue());
				
						if ( result == null )
							throw new SingularityTreatmentException("Received null SimilarityNumber comparison result");							
						
					return result;
		}
	
		@Override
		public SimilarityNumber compare(Descriptor feature1, Descriptor feature2)	throws Exception {
				if (feature1 instanceof VoxelHistogramDescriptor && feature2 instanceof VoxelHistogramDescriptor) {

						VoxelHistogramDescriptor histogramFeature1 = (VoxelHistogramDescriptor) feature1;
						
							if ( histogramFeature1 == null )
								throw new SWIndependenceException("Feature extraction failed for VoxelHistogramDescriptor feature1");
						
						VoxelHistogramDescriptor histogramFeature2 = (VoxelHistogramDescriptor) feature2;
						
							if ( histogramFeature2 == null )
								throw new SWIndependenceException("Feature extraction failed for VoxelHistogramDescriptor feature2");
								
						SimilarityNumber result = compare(histogramFeature1, histogramFeature2);		
						
							if ( result == null )
								throw new SingularityTreatmentException("Received null VoxelHistogramDescriptor comparison value");
						
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
			features.add( VoxelHistogramDescriptor.class );
			return features;
		}

		@Override
		public String getName() {
			return "Taneja";
		}

		@Override
		public Class getType() {
			return TanejaDifferenceMeasure.class;
		}	
		
		@Override
		public String getCategory() {
			return "3D Combinations";
		}
		
		@Override
		public InputStream getHelpZipped() {
			return HelpProvider.getHelpZipped(TanejaDifferenceMeasure.class);
		}

		@Override
		public String getHelpSHA1() {
			return HelpProvider.getHelpSHA1(TanejaDifferenceMeasure.class);
		}	
}