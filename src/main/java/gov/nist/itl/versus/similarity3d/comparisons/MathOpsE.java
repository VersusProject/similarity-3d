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
 *
 *  @author  B. Long
 *  @version 1.3
 *  
 *  Measure calculation implementations
 *  
 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
 *
 *	description:	Each assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
 *  
 *  @author 		B. Long
 *  version:		1.0
 */	

package gov.nist.itl.versus.similarity3d.comparisons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import edu.illinois.ncsa.versus.descriptor.impl.GrayscaleHistogramDescriptor;
import edu.illinois.ncsa.versus.descriptor.impl.RGBHistogramDescriptor;
import gov.nist.itl.versus.similarity3d.comparisons.exception.HWIndependenceException;
import gov.nist.itl.versus.similarity3d.comparisons.exception.SingularityTreatmentException;

public class MathOpsE 
{
	
	public final static double EPSILON = 0.0000001;
	
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
 * Histogram-based measures.
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
 */
		
		/*
		 *  metric family: 	Minkowski Family Metric
		 *  name:			Euclidean_L2
		 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
		 *
		 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
		 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
		 *					
		 *  
		 *  @author 		B. Long
		 *  version:		1.0
		 */		 	
		 
	// eqn #1
	public Double histogram_measure_euclidean(Double[] P, Double[] Q) throws Exception
	{
		chkargs("histogram_measure_euclidean",P,Q);
		Double[] D3 = sub(P,Q);
		Double[] D4 = abs(D3);
		Double[] D5 = square(D4);
		Double 	 d6 = sum(D5);
		Double   d7 = sqrt(d6);
		chkresult("histogram_measure_euclidean",d7);
		return d7;
	}

	
	/*
	 *  metric family: 	Minkowski Family Metric
	 *  name:			City block L1
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *					
	 *  @author 		B. Long
	 *  version:		1.0
	 */		
	
	// eqn #2
	public Double histogram_measure_city_block(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_city_block",P,Q);
		  Double[] D1 = sub(P,Q);
		  Double[] D2 = abs(D1);
		  Double d3 = sum(D2);
		  chkresult("histogram_measure_city_block",d3);
		  return d3;		
	}
	
	/*
	 *  metric family: 	Minkowski Family Metric
	 *  name:			Minkowski_LP
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .				
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 *  
	 */		
	
	// eqn #3
	public Double histogram_measure_minkowski(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_minkowski",P,Q);		
	      Double[] D1 = sub(P,Q);
	  	  Double[] D2 = abs(D1);
	  	  Double[] D3 = cube(D2);
	  	  Double d4 = sum(D3);
	  	  Double d5 = cbrt(d4);
	  	  chkresult("histogram_measure_minkowski",d5);
	  	  return d5;
	}
	
/*
 *  metric family: 	Minkowski Family Metric
 *  name:			Chebyshev L_INF
 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
 *
 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
 *  
 *  @author 		B. Long
 *  version:		1.0
 */	
	
	// eqn #4
	public Double histogram_measure_chebyshev(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_chebyshev",P,Q);		
	      Double[] D1 = sub(P,Q);
	      Double[] D2 = abs(D1);
	      Double d3 = max(D2);
	  	  chkresult("histogram_measure_chebyshev",d3);
	  	  return d3;		
	}
	
	/*
	 *  metric family: 	L1 Family Metric
	 *  name:			Sorensen
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */		
	
	// eqn #5
	public Double histogram_measure_sorensen(Double[] P, Double[] Q) throws Exception
	{
		chkargs("histogram_measure_sorensen",P,Q);
		// top
		Double[] D1 = sub(P,Q);
		Double[] D2 = abs(D1);
		Double d3 = sum(D2);  	  
		// bottom
		Double[] D4 = add(P,Q);
		Double d5 = sum(D4);  	  
		// combine
		Double d6 = div(d3,d5);
		chkresult("histogram_measure_sorensen",d6);
		return d6;		
	}
	
	/*
	 *  metric family: 	L1 Family Metric
	 *  name:			Gower
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */		
	
	// eqn #7
	public Double histogram_measure_gower(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_gower",P,Q);		
		  // reciprocal term
	      Double d1 = (double)P.length;
	      Double d2 = reciprocal1(d1);	      
		  
		  // sum term
	      Double[] D3 = sub(P,Q);
	      Double[] D4 = abs(D3);
	      Double d5 = sum(D4);	      
		  
		  // combine
	      Double d6 = mult(d2,d5);	      
	  	  chkresult("histogram_measure_gower",d6);
	  	  return d6;		
	}
	
	// eqn #6
	public Double histogram_measure_gower2(Double[] P, Double[] Q, Double[] R) throws Exception
	{
		  chkargs("histogram_measure_gower2",P,Q);
		  chkargs("histogram_measure_gower2",Q,R);
		  
		  // right-most term
		  Double[] D1 = sub(P,Q);
		  Double[] D2 = abs(D1);
		  Double[] D3 = div(D2,R);
		  Double   d4 = sum(D3);
		  
		  // reciprocal term
	      Double d5 = (double)P.length;
	      Double d6 = reciprocal1(d5);
		  
		  // combine
		  Double d7 = mult(d6, d4);
		  
		  chkresult("histogram_measure_gower2",d7);
	  	  return d7;
	}	
	
	/*
	 *  metric family: 	L1 Family Metric
	 *  name:			Soergel
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .				
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */		
	
	// eqn #8
	public Double histogram_measure_soergel(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_soergel",P,Q);	
		  // top
	      Double[] D1 = sub(P,Q);
	      Double[] D2 = abs(D1);
	      Double d3 = sum(D2);	
		  // bottom
	      Double[] D4 = max(P,Q);
	      Double d5 = sum(D4);	      
		  // combine
	      Double d6 = div(d3,d5);	      
	  	  chkresult("histogram_measure_soergel",d6);
	  	  return d6;
	}
	
	/*
	 *  metric family: 	L1 Family Metric
	 *  name:			Kulczynski d
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #9
	public Double histogram_measure_kulczynski(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_kulczynski",P,Q);	
		  // top
	      Double[] D1 = sub(P,Q);
	      Double[] D2 = abs(D1);
	      Double d3 = sum(D2);	      
		  // bottom
	      Double[] D4 = min(P,Q);
	      Double d5 = sum(D4);	      
		  // combine
	      Double d6 = div(d3,d5);	      
	  	  chkresult("histogram_measure_kulczynski",d6);
	  	  return d6;		
	}
	

	
	/*
	 *  metric family: 	L1 Family Metric
	 *  name:			Canberra
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */		
	
	// eqn #10
	public Double histogram_measure_canberra(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_canberra",P,Q);	
		  // top
	      Double[] D1 = sub(P,Q);
	      Double[] D2 = abs(D1);	      
		  // bottom
	      Double[] D3 = add(P,Q);	      
		  // combine
	      Double[] D4 = div(D2,D3);	    
	      Double d5 = sum(D4);	
		  
	  	  chkresult("histogram_measure_canberra",d5);
	  	  return d5;		
	}
	
  	/*
	 *  metric family: 	L1 Family Metric
	 *  name:			Lorentzian
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #11
	public Double histogram_measure_lorentzian(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_lorentzian",P,Q);	
		  // inner-term
	      Double[] D1 = sub(P,Q);
	      Double[] D2 = abs(D1);				  
		  Double[] D3 = mkConstArray(P.length, 1d);		  
		  Double[] D4 = add(D3, D2);		  		  
		  Double[] D5 = ln(D4);		  
		  // combine
		  Double d6 = sum(D5);      				
	  	  chkresult("histogram_measure_lorentzian",d6);
		  return d6;		
	}
	
  	/*
	 *  metric family: 	Intersection Family Metric
	 *  name:			Intersection
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #12
	public Double histogram_measure_intersection_IS(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_intersection_IS",P,Q);		
	      Double[] D1 = min(P,Q);
	      Double d2 = sum(D1);      				
	  	  chkresult("histogram_measure_intersection_IS",d2);
	      return d2;		
	}
	
	// eqn #13.1
	public Double histogram_measure_intersection_dNonIS(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_intersection_dNonIS",P,Q);		
	      Double d1 = histogram_measure_intersection_IS(P,Q);
	   // Case of normalized histograms
		  if (d1 > 1 && Math.abs(sum(P) - 1) < MathOpsE.EPSILON && Math.abs(sum(Q) - 1) < MathOpsE.EPSILON) {  
			  return 0.0;
		  }
		  Double d2 = sub(1.0d,d1);      
	  	  chkresult("histogram_measure_intersection_dNonIS",d2);
	  	  return d2;		
	}	

	// eqn #13.2
	public Double histogram_measure_intersection_dNonIS2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_intersection_dNonIS2",P,Q);		
		  // reciprocal term
	      Double d1 = 2.0d;
	      Double d2 = reciprocal1(d1);	      
		  
		  // sum term
	      Double[] D3 = sub(P,Q);
	      Double[] D4 = abs(D3);
	      Double d5 = sum(D4);	      
		  
		  // combine
	      Double d6 = mult(d2,d5);	      
	  	  chkresult("histogram_measure_intersection_dNonIS2",d6);
	  	  return d6;		
	}	
	
	/*
	 *  metric family: 	Intersection Family Metric
	 *  name:			Wave Hedges
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */		
	
	// eqn #15
	public Double histogram_measure_wave_hedges(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_wave_hedges",P,Q);	
		  // top
	      Double[] D1 = sub(P,Q);		  
	      Double[] D2 = abs(D1);
		  // bottom
		  Double[] D3 = max(P,Q);		  
		  // combine
		  Double[] D4 = div(D2, D3);		  
		  Double d5 = sum(D4);        
	  	  chkresult("histogram_measure_wave_hedges",d5);
		  return d5;			
	}
	
	// eqn #14
	public Double histogram_measure_wave_hedges_dWH(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_wave_hedges_dWH",P,Q);	
		  // top
		  Double[] D1 = min(P,Q);
		  // bottom
		  Double[] D2 = max(P,Q);
		  // combine: term1
		  Double[] D3 = div(D1,D2);
		  // combine: term2
		  Double[] D4 = mkConstArray(P.length, 1.0d);	
		  Double[] D5 = sub(D4,D3);
		  // combine
		  Double   d6 = sum(D5);
	  	  chkresult("histogram_measure_wave_hedges_dWH",d6);
		  return d6;			
	}
	
	/*
	 *  metric family: 	Intersection Family Metric
	 *  name:			Czekanowski s
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 *
	 */	 	
	
	// eqn #16
	public Double histogram_measure_czekanowski(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_czekanowski",P,Q);		
		  // top
	      Double[] D1 = min(P,Q);
	      Double d2 = sum(D1);
	      Double d3	= mult(2.0d, d2);	      
		  // bottom
	      Double[] D4 = add(P,Q);
	      Double d5 = sum(D4);
		  // combine
		  Double d6 = div(d3, d5);	        
	  	  chkresult("histogram_measure_czekanowski",d6);
		  return d6;				
	}

	// eqn #17.1
	public Double histogram_measure_czekanowski_dCze(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_czekanowski_dCze",P,Q);
		   // Case of normalized histograms
		  Double d1 = histogram_measure_czekanowski(P,Q); 
			  if (d1 > 1 && Math.abs(sum(P) - 1) < MathOpsE.EPSILON && Math.abs(sum(Q) - 1) < MathOpsE.EPSILON) {  
				  return 0.0;
			  }
		  Double d2 = sub(1.0d,d1); 	        
	  	  chkresult("histogram_measure_czekanowski_dCze",d2);
		  return d2;				
	}	
	
	// eqn #17.2
	public Double histogram_measure_czekanowski_dCze2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_czekanowski_dCze2",P,Q);	
		  // top
		  Double[] D1 = sub(P,Q);
		  Double[] D2 = abs(D1);
		  Double   d3 = sum(D2);
		  // bottom
		  Double[] D4 = add(P,Q);
		  Double   d5 = sum(D4);
		  // combine
		  Double   d6 = div(d3,d5);
		  
	  	  chkresult("histogram_measure_czekanowski_dCze2",d6);
		  return d6;				
	}	
	
	/*
	 *  metric family: 	Intersection Family Metric
	 *  name:			Motyka
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *					 
	 *  @author 		B. Long
	 *  version:		1.0
	 *
	 */		
	
	// eqn #18
	public Double histogram_measure_motyka(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_motyka",P,Q);
		  // top
	      Double[] D1 = min(P,Q);
	      Double d2 = sum(D1);
		  // bottom
	      Double[] D3 = add(P,Q);
	      Double d4 = sum(D3);	      	
		  // combine
		  Double d5 = div(d2, d4);	        
	  	  chkresult("histogram_measure_motyka",d5);
		  return d5;
	}
	
	// eqn #19.1
	public Double histogram_measure_motyka_dMot(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_motyka_dMot",P,Q);
		  Double d1 = histogram_measure_motyka(P,Q);
		   // Case of normalized histograms
		  if (d1 > 1 && Math.abs(sum(P) - 1) < MathOpsE.EPSILON && Math.abs(sum(Q) - 1) < MathOpsE.EPSILON) {  
			  return 0.0;
		  }
		  Double d2 = sub(1.0d, d1);        
	  	  chkresult("histogram_measure_motyka_dMot",d2);
		  return d2;
	}

	// eqn #19.2
	public Double histogram_measure_motyka_dMot2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_motyka_dMot2",P,Q);
		  // top
	      Double[] D1 = max(P,Q);
	      Double d2 = sum(D1);
		  // bottom
	      Double[] D3 = add(P,Q);
	      Double d4 = sum(D3);	      	
		  // combine
		  Double d5 = div(d2, d4);	        
	  	  chkresult("histogram_measure_motyka_dMot2",d5);
		  return d5;
	}	
	
	/*
	 *  metric family: 	Intersection Family Metric
	 *  name:			Kulczynski s
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 *
	 */	 	
	
	// eqn #20.1
	public Double histogram_measure_kulczynski_s(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_kulczynski_s",P,Q);
	      Double d1 = histogram_measure_kulczynski(P,Q);
	      Double d2 = reciprocal1(d1);        
	  	  chkresult("histogram_measure_kulczynski_s",d2);
	      return d2;		
	}
	
	// eqn #20.2
	public Double histogram_measure_kulczynski_s2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_kulczynski_s2",P,Q);
		  // top
	      Double[] D1 = min(P,Q);
		  Double   d2 = sum(D1);
		  // bottom
		  Double[] D3 = sub(P,Q);
		  Double[] D4 = abs(D3);
		  Double   d5 = sum(D4);
		  // combine
		  Double   d6 = div(d2,d5);
	  	  chkresult("histogram_measure_kulczynski_s2",d6);
	      return d6;		
	}	
	
	/*
	 *  metric family: 	Intersection Family Metric
	 *  name:			Ruzicka
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */		
	
	public Double histogram_measure_ruzicka(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_ruzicka",P,Q);
		  // top
	      Double[] D1 = min(P,Q);
	      Double d2 = sum(D1);
		  // bottom
	      Double[] D3 = max(P,Q);
	      Double d4 = sum(D3);	      	
		  // combine
		  Double d5 = div(d2, d4);	        
	  	  chkresult("histogram_measure_ruzicka",d5);
		  return d5;		
	}
	
	/*
	 *  metric family: 	Intersection Family Metric
	 *  name:			Tanimoto
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	 	
	
	// eqn #23
	public Double histogram_measure_tanimoto(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_tanimoto",P,Q);
		  
		  // bottom
	      Double[] D1 = max(P,Q);		  
	      Double d2   = sum(D1);
		  
		  // top
		  // term2: right-top
	      Double[] D3 = min(P,Q);
		  // term3: left-top
		  Double[] D4 = max(P,Q);
	      // combine top	      	
		  Double[] D5 = sub(D4, D3);		  		  
		  Double   d6 = sum(D5);		  
		  
		  // combine
		  Double d7 = div(d6, d2);	        
		  
	  	  chkresult("histogram_measure_tanimoto",d7);
		  return d7;
	}
	
	// eqn #22
	public Double histogram_measure_tanimoto2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_tanimoto2",P,Q);
		  
		  // bottom
	      Double[] D1 = min(P,Q);
		  Double   d2 = sum(D1);	
		  
		  Double   d3 = sum(Q);
		  Double   d4 = sum(P);
		  Double   d5 = add(d4,d3);		  
		  Double   d6 = sub(d5,d2);
		  
		  // top
		  Double[] D7 = min(P,Q);
		  Double   d8 = sum(D7);
		  Double   d9 = mult(2.0d, d8);
		  
		  Double   d10= sum(Q);
		  Double   d11= sum(P);
		  Double   d12= add(d11,d10);
		  Double   d13= sub(d12,d9);

		  // combine
		  Double   d14= div(d13, d6);
		  
	  	  chkresult("histogram_measure_tanimoto2",d14);
		  return d14;
	}	
	
	/*
	 *  metric family: 	Inner Product Family Metric
	 *  name:			Inner Product
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #24
	public Double histogram_measure_inner_product(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_inner_product",P,Q);
	      Double[] D1 = mult(P,Q);
	      Double d2 = sum(D1);
	  	  chkresult("histogram_measure_inner_product",d2);
	      return d2;
	}
	
	/*
	 *  metric family: 	Inner Product Family Metric
	 *  name:			Harmonic Mean
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #25
	public Double histogram_measure_harmonic_mean(Double[] P, Double[] Q) throws Exception
	{		
		  chkargs("histogram_measure_harmonic_mean",P,Q);
		  // top
	      Double[] D1 = mult(P,Q);
		  // bottom
	      Double[] D2 = add(P,Q);
		  // term1: combine
	      Double[] D3 = div(D1, D2);
		  // combine
	      Double d4	= sum(D3);
	      Double d5	= mult(2.0d, d4);        
	  	  chkresult("histogram_measure_harmonic_mean",d5);
	      return d5;		
	}

 	/*
	 *  metric family: 	Inner Product Family Metric
	 *  name:			Cosine
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *					
	 *  @author 		B. Long
	 *  version:		1.0
	 */		

	// eqn #26
	public Double histogram_measure_cosine(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_cosine",P,Q);
		  // bottom
	      Double[] D1 = square(P);
	      Double d2	  = sum(D1);
	      Double d3   = sqrt(d2);
	      
	      Double[] D3 = square(Q);
	      Double d4	  = sum(D3);
	      Double d5   = sqrt(d4);
	      // bottom terms: combine
	      Double d6	  = mult(d3, d5);
	      
	      // top
	      Double[] D7 = mult(P,Q);
	      Double d8   = sum(D7);	
	      // combine
	      Double d9	  = div(d8, d6);        
	  	  chkresult("histogram_measure_cosine",d9);
	      return d9;		
	}

	/*
	 *  metric family: 	Inner Product Family Metric
	 *  name:			Kumar-Hassebrook (PCE)
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 *
	 */		
	
	// eqn #27
	public Double histogram_measure_kumar_hassebrook_pce(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_kumar_hassebrook_pce",P,Q);
		  
	// NOTE: The implementation of this measure has been commented out and replaced with a constant NaN result
	//       due to the fact that the source paper (http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf)
	//       from which this was derived copied and pasted the formula for Jaccard in the description for Kumar-Hassebrook-PCE.
	//		 Thus, when a replacement formula is identified, the implementation will be updated accordingly.
	  
	/*
		  // bottom right-most term
	      Double[] D1 = mult(P,Q);
	      Double d2	= sum(D1);
	      
	      // bottom middle term
	      Double[] D3 = square(Q);
	      Double d4	= sum(D3);
		  
		  // bottom left term
	      Double[] D5 = square(P);
	      Double d6	= sum(D5);
		  // combine left and middle
	      Double d7 = add(d6,d4);
	      
	      // bottom: combine
	      Double d8	= sub(d7, d2);	   
	      
	      // top
	      Double[] D9 = mult(P,Q);
	      Double d10 = sum(D9);
	      
	      // combine
	      Double d11 = div(d10, d8);
	    */
		  Double d11 = Double.NaN;
		  
	  	  chkresult("histogram_measure_kumar_hassebrook_pce",d11);
          return d11;		
	}
	
	/*
	 *  metric family: 	Inner Product Family Metric
	 *  name:			Jaccard
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 *
	 */  	
	
	// eqn #28
	public Double histogram_measure_jaccard(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_jaccard",P,Q);
		  
		  // bottom right-most term
	      Double[] D1 = mult(P,Q);
	      Double d2	= sum(D1);
	      
	      // bottom middle term
	      Double[] D3 = square(Q);
	      Double d4	= sum(D3);
		  
		  // bottom left term
	      Double[] D5 = square(P);
	      Double d6	= sum(D5);
		  // combine left and middle
	      Double d7 = add(d6,d4);
	      
	      // bottom: combine
	      Double d8	= sub(d7, d2);	   
	      
	      // top
	      Double[] D9 = mult(P,Q);
	      Double d10 = sum(D9);
	      
	      // combine
	      Double d11 = div(d10, d8);		           
		             
	  	  chkresult("histogram_measure_jaccard",d11);
	      return d11;		
	}
	
	// eqn #39.1 (a.k.a., #29.1, was misnamed in paper)
	public Double histogram_measure_jaccard_dJac1(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_jaccard_dJac1",P,Q);
		  Double d1 = histogram_measure_jaccard(P,Q);
		  // Case of normalized histograms
		  if (d1 > 1 && Math.abs(sum(P) - 1) < MathOpsE.EPSILON && Math.abs(sum(Q) - 1) < MathOpsE.EPSILON) {  
			  return 0.0;
		  }
		  Double d2 = sub(1.0d,d1);
	  	  chkresult("histogram_measure_jaccard_dJac1",d2);
	      return d2;		
	}	
	
	// eqn #39.2 (a.k.a., #29.2, was misnamed in paper)
	public Double histogram_measure_jaccard_dJac2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_jaccard_dJac2",P,Q);
		  
		  // bottom right-most term
	      Double[] D1 = mult(P,Q);
	      Double d2	= sum(D1);
	      
	      // bottom middle term
	      Double[] D3 = square(Q);
	      Double d4	= sum(D3);
		  
		  // bottom left term
	      Double[] D5 = square(P);
	      Double d6	= sum(D5);
	      
		  // combine left and middle
	      Double d7 = add(d6,d4);
	      
	      // bottom: combine
	      Double d8	= sub(d7, d2);	   
	      
	      // top
		  Double[] D9 = sub(P,Q);
		  Double[] D10= square(D9);
		  Double   d11= sum(D10);
		  
		  // combine
		  Double   d12 = div(d11,d8);
		             
	  	  chkresult("histogram_measure_jaccard_dJac2",d12);
	      return d12;		
	}	
	
	/*
	 *  metric family: 	Inner Product Family Metric
	 *  name:			Dice
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #40 (a.k.a., #30, was misnamed in paper)
	public Double histogram_measure_dice(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_dice",P,Q);
		  // bottom
	      Double[] D1 = square(Q);
	      Double d2	  = sum(D1);
	      Double[] D3 = square(P);
	      Double d4	= sum(D3);	      
	      Double d5	= add(d4, d2);
	      
	      // top
	      Double[] D6 = mult(P,Q);
	      Double d7	= sum(D6);
	      Double d8	= mult(2.0d, d7);
	      
		  // combine
	      Double d9	= div(d8, d5);        
	  	  chkresult("histogram_measure_dice",d9);
	      return d9;		
	}

	// eqn #31.1 
	public Double histogram_measure_dice_dDice1(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_dice_dDice1",P,Q);
		  Double d1 = histogram_measure_dice(P,Q);
		  // Case of normalized histograms
		  if (d1 > 1 && Math.abs(sum(P) - 1) < MathOpsE.EPSILON && Math.abs(sum(Q) - 1) < MathOpsE.EPSILON) {  
			  return 0.0;
		 }
		
		  Double d2 = sub(1.0d, d1);       
	  	  chkresult("histogram_measure_dice_dDice1",d2);
	      return d2;		
	}	

	// eqn #31.2 
	public Double histogram_measure_dice_dDice2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_dice_dDice2",P,Q);
		  // bottom
	      Double[] D1 = square(Q);
	      Double d2	  = sum(D1);
	      Double[] D3 = square(P);
	      Double d4	= sum(D3);	      
	      Double d5	= add(d4, d2);
	      
	      // top
	      Double[] D6 = sub(P,Q);
		  Double[] D7 = square(D6);
		  Double   d8 = sum(D7);
	      
		  // combine
	      Double d9	= div(d8, d5);        
	  	  chkresult("histogram_measure_dice_dDice2",d9);
	      return d9;		
	}	
	
	
	/*
	 *  metric family: 	Fidelity/Chi-Squared Family Metric
	 *  name:			Fidelity
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */
	
	// eqn #32
	public Double histogram_measure_fidelity(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_fidelity",P,Q);
	      Double[] D1 = mult(P,Q);
		  Double[] D2 = sqrt(D1);
		  Double d3 = sum(D2);		               
	  	  chkresult("histogram_measure_fidelity",d3);
		  return d3;		
	}
	
	
	/*
	 *  metric family: 	Fidelity/Chi-Squared Family Metric
	 *  name:			Bhattacharyya
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #33
	public Double histogram_measure_bhattacharyya(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_bhattacharyya",P,Q);
	      Double[] D1 = mult(P,Q);
		  Double[] D2 = sqrt(D1);
		  Double d3 = sum(D2);
		  // Case of normalized histograms
		  if (d3 > 1 && Math.abs(sum(P) - 1) < MathOpsE.EPSILON && Math.abs(sum(Q) - 1) < MathOpsE.EPSILON) {  
			  return 0.0;
		  }
		  Double d4	= ln(d3);		  
		  Double d5	= mult(-1.0d, d4);		               
	  	  chkresult("histogram_measure_bhattacharyya",d5);
		  return d5;	
	}
	
	/*
	 *  metric family: 	Fidelity/Chi-Squared Family Metric
	 *  name:			Hellinger
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 *
	 */	
	
	// eqn #35
	public Double histogram_measure_hellinger(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_hellinger",P,Q);
		  // right-most inner term
	      Double[] D1 = mult(P,Q);
		  Double[] D2 = sqrt(D1);
		  Double d3 = sum(D2);
		  // Case of normalized histograms
		  if (d3 > 1 && Math.abs(sum(P) - 1) < MathOpsE.EPSILON && Math.abs(sum(Q) - 1) < MathOpsE.EPSILON) {  
			  return 0.0;
		  }
		  
		  // inside sqrt
		  Double d4 = sub(1d, d3);
		  
		  // combine
		  Double d5 = sqrt(d4);
		  Double d6 = mult(2.0d, d5);		                
		  
	  	  chkresult("histogram_measure_hellinger",d6);
		  return d6;		
	}
	
	// eqn #34
	public Double histogram_measure_hellinger_dH2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_hellinger_dH2",P,Q);
		  // inner sum
	      Double[] D1 = sqrt(Q);
		  Double[] D2 = sqrt(P);
		  Double[] D3 = sub(D2,D1);
		  Double[] D4 = square(D3);
		  Double   d5 = sum(D4);
		  // mult by 2
		  Double   d6 = mult(2.0d, d5);
		  // combine
		  Double   d7 = sqrt(d6);
	  	  chkresult("histogram_measure_hellinger_dH2",d7);
		  return d7;		
	}	
	
	/*
	 *  metric family: 	Fidelity/Chi-Squared Family Metric
	 *  name:			Matusita
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 *
	 */	
	
	// eqn #37
	public Double histogram_measure_matusita(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_matusita",P,Q);
	      Double[] D1 = mult(P,Q);
		  Double[] D2 = sqrt(D1);
		  Double d3 = sum(D2);
		  // Case of normalized histograms
		  if (d3 > 1 && Math.abs(sum(P) - 1) < MathOpsE.EPSILON && Math.abs(sum(Q) - 1) < MathOpsE.EPSILON) {  
			  return 0.0;
		  }
		  Double d4 = mult(2.0d, d3);		               
		  Double d5 = sub(2.0d, d4);
		  Double d6 = sqrt(d5);
	  	  chkresult("histogram_measure_matusita",d6);
		  return d6;		
	}
	
	// eqn #36
	public Double histogram_measure_matusita_dM2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_matusita_dM2",P,Q);
	      Double[] D1 = sqrt(Q);
		  Double[] D2 = sqrt(P);
		  Double[] D3 = sub(D2,D1);
		  Double[] D4 = square(D3);
		  Double   d5 = sum(D4);
		  Double   d6 = sqrt(d5);
	  	  chkresult("histogram_measure_matusita_dM2",d6);
		  return d6;		
	}	
	
	/*
	 *  metric family: 	Fidelity/Chi-Squared Family Metric
	 *  name:			Squared-chord
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #38
	public Double histogram_measure_squared_chord(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_squared_chord",P,Q);
	      Double[] D1 = sqrt(P);
		  Double[] D2 = sqrt(Q);
		  Double[] D3 = sub(D1,D2);		  			  
		  Double[] D4 = square(D3);		  
		  Double d5 = sum(D4);		                              
	  	  chkresult("histogram_measure_squared_chord",d5);
		  return d5;		
	}
	
	// eqn #39.1
	public Double histogram_measure_squared_chord_Ssqc1(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_squared_chord_Ssqc1",P,Q);
		  Double d1 = histogram_measure_squared_chord(P,Q);
		  // Case of normalized histograms
		  if (d1 > 1 && Math.abs(sum(P) - 1) < MathOpsE.EPSILON && Math.abs(sum(Q) - 1) < MathOpsE.EPSILON) {  
			  return 0.0;
		  }
	      Double d2 = sub(1.0d, d1);	                              
	  	  chkresult("histogram_measure_squared_chord_Ssqc1",d2);
		  return d2;		
	}	
	
	// eqn #39.2
	public Double histogram_measure_squared_chord_Ssqc2(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_squared_chord_Ssqc2",P,Q);
	      Double[] D1 = mkConstArray(P.length,1.0d);
		  
		  Double[] D2 = mult(P,Q);
		  Double[] D3 = sqrt(D2);
		  
		  Double[] D4 = sub(D3,D1);
		  
		  Double   d5 = sum(D4);
		  
		  Double   d6 = mult(2.0d, d5);
		  
	  	  chkresult("histogram_measure_squared_chord_Ssqc2",d6);
		  return d6;		
	}		
	
	/*
	 *  metric family: 	Squared_L2/Chi-Squared Family Metric
	 *  name:			Squared Euclidean
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */		
	
	// eqn #40
	public Double histogram_measure_squared_euclidean(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_squared_euclidean",P,Q);
	      Double[] D1 = sub(P,Q);
		  Double[] D2 = square(D1);
		  Double d3 = sum(D2);		  			 
	  	  chkresult("histogram_measure_squared_euclidean",d3);
		  return d3;
	}
	
	/*
	 *  metric family: 	Squared_L2/Chi-Squared Family Metric
	 *  name:			Pearson Chi-Squared
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */			
	
	// eqn #41
	public Double histogram_measure_pearson_chiSquared(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_pearson_chiSquared",P,Q);
		  // top
		  Double[] D1 = sub(P,Q);
		  Double[] D2 = square(D1);
		  // combine
		  Double[] D3 = div(D2,Q);			  
		  Double d4 = sum(D3);			  			  
	  	  chkresult("histogram_measure_pearson_chiSquared",d4);
		  return d4;		
	}
	
	/*
	 *  metric family: 	Squared_L2/Chi-Squared Family Metric
	 *  name:			Neyman Chi-Squared
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #42
	public Double histogram_measure_neyman_chiSquared(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_neyman_chiSquared",P,Q);
		  // top
	      Double[] D1 = sub(P,Q);
		  Double[] D2 = square(D1);
		  // combine
		  Double[] D3 = div(D2,P);
		  Double d4 = sum(D3);		  			 
	  	  chkresult("histogram_measure_neyman_chiSquared",d4);
		  return d4;
	}
	
	/*
	 *  metric family: 	Squared_L2/Chi-Squared Family Metric
	 *  name:			Squared Chi-Squared
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #43
	public Double histogram_measure_squared_chiSquared(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_squared_chiSquared",P,Q);
		  // bottom
	      Double[] D1 = add(P,Q);	      
		  // top
	      Double[] D2 = sub(P,Q);
		  Double[] D3 = square(D2);		  
		  // combine
		  Double[] D4 = div(D3,D1);		  
		  Double d5 = sum(D4);		  			  
	  	  chkresult("histogram_measure_squared_chiSquared",d5);
		  return d5;		
	}
	
	/*
	 *  metric family: 	Squared_L2/Chi-Squared Family Metric
	 *  name:			Probabilistic Symmetric Chi-Squared
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #44
	public Double histogram_measure_probabilistic_symmetric_chiSquared(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_probabilistic_symmetric_chiSquared",P,Q);
		  // bottom
	      Double[] D1 = add(P,Q);	      
		  // top
	      Double[] D2 = sub(P,Q);
		  Double[] D3 = square(D2);		  
		  // combine
		  Double[] D4 = div(D3,D1);		  
		  Double d5 = sum(D4);		  
		  Double d6 = mult(2.0d, d5);		  			  
	  	  chkresult("histogram_measure_probabilistic_symmetric_chiSquared",d6);
		  return d6;		
	}
	
	/*
	 *  metric family: 	Squared_L2/Chi-Squared Family Metric
	 *  name:			Divergence
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #45
	public Double histogram_measure_divergence(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_divergence",P,Q);
		  // bottom
	      Double[] D1 = add(P,Q);
	      Double[] D2 = square(D1);	      
		  // top
	      Double[] D3 = sub(P,Q);
		  Double[] D4 = square(D3);		  
		  // combine
		  Double[] D5 = div(D4,D2);		  
		  Double d6 = sum(D5);		  
		  Double d7 = mult(2.0d, d6);		  			  
	  	  chkresult("histogram_measure_divergence",d7);
		  return d7;	
	}
	
	/*
	 *  metric family: 	Squared_L2/Chi-Squared Family Metric
	 *  name:			Clark
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */
	
	// eqn #46
	public Double histogram_measure_clark(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_clark",P,Q);
		  // top
	      Double[] D1 = add(P,Q);	      
	      Double[] D2 = sub(P,Q);
		  Double[] D3 = abs(D2);		  
		  // bottom
		  Double[] D5 = div(D3,D1);		  
		  // combine
		  Double[] D6 = square(D5);		  		  
		  Double d7 = sum(D6);		  
		  Double d8 = sqrt(d7);		  			  
	  	  chkresult("histogram_measure_clark",d8);
		  return d8;
	}
	
	/*
	 *  metric family: 	Squared_L2/Chi-Squared Family Metric
	 *  name:			Additive Symmetric Chi-Squared
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 *
	 */
	
	// eqn #47
	public Double histogram_measure_additive_symmetric_chiSquared(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_additive_symmetric_chiSquared",P,Q);
		  // bottom
	      Double[] D1 = mult(P,Q);		      
		  // top right term
	      Double[] D2 = add(P,Q);
		  // top left term
	      Double[] D3 = sub(P,Q);
	      Double[] D4 = square(D3);	        
		  // top combine
	      Double[] D5 = mult(D4,D2);	      
		  // combine
		  Double[] D6 = div(D5,D1);		  
		  Double d7 = sum(D6);		  			  
	  	  chkresult("histogram_measure_additive_symmetric_chiSquared",d7);
		  return d7;		
	}
	
	/*
	 *  metric family: 	Shannon's Entropy Family Metric
	 *  name:			Kullback-Liebler
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #48
	public Double histogram_measure_kullback_leibler(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_kullback_leibler",P,Q);
			  // right-most term
		      Double[] D1 = div(P,Q);      		      
			  // combine
		      Double[] D3 = a_Ln_b(P,D1);		      
			  Double d4 = sum(D3);			  			  
		  	  chkresult("histogram_measure_kullback_leibler",d4);
			  return d4;		
	}
	
	/*
	 *  metric family: 	Shannon's Entropy Family Metric
	 *  name:			Jeffreys
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #49
	public Double histogram_measure_jeffreys(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_jeffreys",P,Q);
		  // right-most term
	      Double[] D1 = div(P,Q);      		      
		  // combine
	      Double[] D3 = sub(P,Q);	            		        
	      Double[] D4 = a_Ln_b(D3,D1);
		  Double d5 = sum(D4);
	  	  chkresult("histogram_measure_jeffreys",d5);
		  return d5;
	}
	
	/*
	 *  metric family: 	Shannon's Entropy Family Metric
	 *  name:			K divergence
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	 
	// eqn #50
	public Double histogram_measure_k_divergence(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_k_divergence",P,Q);
		  // bottom
	      Double[] D1 = add(P,Q);      		 
		  // top
	      Double[] D2 = mkConstArray(P.length,2.0d);	      
	      Double[] D3 = mult(D2,P);	            		      
		  // right-most term: combine
	      Double[] D4 = div(D3,D1);	 
		  // combine
		  Double[] D6 = a_Ln_b(P,D4);		  
		  Double d7 = sum(D6);		  			  
	  	  chkresult("histogram_measure_k_divergence",d7);
		  return d7;		
	}
	
	/*
	 *  metric family: 	Shannon's Entropy Family Metric
	 *  name:			Topsoe
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #51
	public Double histogram_measure_topsoe(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_topsoe",P,Q);
		  // right-most term
		  // bottom
	      Double[] D1 = add(P,Q);      		      
		  // top
	      Double[] D2 = mkConstArray(Q.length,2.0d);      		      
	      Double[] D3 = mult(D2,Q);      		            		        
		  // combine
	      Double[] D4 = div(D3,D1);      		      
		  // finish right-most term
		  Double[] D6 = a_Ln_b(Q,D4);
		  
		  // left-most term
		  // bottom
		  Double[] D7 = add(P,Q);      		      
		  // top
	      Double[] D8 = mkConstArray(P.length,2.0d);      		      
	      Double[] D9 = mult(D8,P);      		            		        
		  // combine
	      Double[] D10 = div(D9,D7);      		      
		  // finish left-most term
		  Double[] D12 = a_Ln_b(P,D10);		  
		  
		  // combine left and right terms
		  Double[] D13 = add(D12, D6);   
		  // combine
		  Double d14 = sum(D13);		  			 
	  	  chkresult("histogram_measure_topsoe",d14);
		  return d14;
	}
	
	/*
	 *  metric family: 	Shannon's Entropy Family Metric
	 *  name:			Jensen-Shannon
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #52
	public Double histogram_measure_jensen_shannon(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_jensen_shannon",P,Q);
		  // right-most term: sum Q * ln( 2Q / P+Q )
		  // bottom
	      Double[] D1 = add(P,Q);      		      
		  // top
	      Double[] D2 = mkConstArray(Q.length,2.0d);      		      
	      Double[] D3 = mult(D2,Q);      		            		        
		  // combine
	      Double[] D4 = div(D3,D1);      		      
		  Double[] D6 = a_Ln_b(Q,D4);
		  Double d7	= sum(D6);
		  
		  // left-most term: sum P * ln( 2P / P+Q )
		  // bottom
		  Double[] D7 = add(P,Q);      		      
		  // top
	      Double[] D8 = mkConstArray(P.length,2.0d);      		      
	      Double[] D9 = mult(D8,P);      		   
		  // combine
	      Double[] D10 = div(D9,D7);      		      
		  Double[] D12 = a_Ln_b(P,D10);
		  Double d12 = sum(D12);		
		  
		  // add them
		  Double d13 = add(d12, d7);
		  
		  // combine
		  Double d14 = div(1.0d, 2.0d);
		  
		  Double d15 = mult(d14, d13);
		  
	  	  chkresult("histogram_measure_jensen_shannon",d15);
		  return d15;
	}
	
	/*
	 *  metric family: 	Shannon's Entropy Family Metric
	 *  name:			Jensen difference
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
     *	 
	 */
	
	// eqn #53
	public Double histogram_measure_jensen_difference(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_jensen_difference",P,Q);
		  // right-most term: ln*((P+Q)/2))
	      Double[] D1 = mkConstArray(Q.length,2.0d);      		      
	      Double[] D2 = add(P,Q);      		      
	      Double[] D3 = div(D2,D1);      		      
	      Double[] D4 = a_Ln_b(D3, D3);
	      

		  // left-most term
		  Double[] D9 = mkConstArray(Q.length,2.0d);
		  
		  // Q*ln Q
		  Double[] D11 = a_Ln_b(Q, Q);// mult(Q, D10);
		  
		  // P*ln P
		  Double[] D13 = a_Ln_b(P,P); //mult(P, D12);
		  
		  // add them
		  Double[] D14 = add(D13, D11);
	
		  // full first term: (P*ln P + Q*ln Q)/2	  
		  Double[] D15 = div(D14, D9);
		  
		  // first term - last combined term
		  Double[] D16 = sub(D15, D4);  

		  // final term: summation of last
		  Double d17 = sum(D16);		
		  
	  	  chkresult("histogram_measure_jensen_difference",d17);
		  return d17;		
	}
	
	
  	/*
	 *  metric family: 	Combinations Family Metric
	 *  name:			Taneja difference
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */
	
	// eqn #54
	public Double histogram_measure_taneja_difference(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_taneja_difference",P,Q);
		  // right-most term: 2 * sqrt(P*Q)
		  // bottom
	      Double[] D1 = mult(P,Q);
	      Double[] D2 = sqrt(D1);
	      Double[] D3 = mkConstArray(P.length,2.0d);
	      Double[] D4 = mult(D3,D2);
	      // top
	      Double[] D5 = add(P,Q);
		  // combine
	      Double[] D6 = div(D5,D4);
	      
		  // left-most term
		  // top
	      Double[] D8 = add(P,Q);
	      Double[] D9 = mkConstArray(Q.length, 2.0d);
		  // combine
	      Double[] D10= div(D8,D9);
	      Double[] D11= a_Ln_b(D10,D6);
	      Double d12 = sum(D11);
	      
	  	  chkresult("histogram_measure_taneja_difference",d12);
	     return d12;		
	}
	
	/*
	 *  metric family: 	Combinations Family Metric
	 *  name:			Kumar-Johnson
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */
	
	// eqn #55
	public Double histogram_measure_kumar_johnson_difference(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_kumar_johnson_difference",P,Q);
		// bottom: 2 * ((P*Q)^(3/2))
	      Double[] D1 = mult(P,Q);
	      Double d2 = div(3.0d, 2.0d);
	      Double[] D2 = mkConstArray(P.length, d2);
	      Double[] D3 = pow(D1,D2);      		      
	      Double[] D4 = mkConstArray(P.length,2.0d);      		      
	      Double[] D5 = mult(D4,D3);
	      
	   // top: (P^2 - Q^2)^2
	   	  Double[] D6 = square(Q);
	   	  Double[] D7 = square(P);
	   	  Double[] D8 = sub(D7, D6);
	   	  Double[] D9 = square(D8);
	   	  
	   	  // divide: top / bottom
	   	  Double[] D10 = div(D9, D5);
	   	  
	   	  // final sum
	   	  Double d11 = sum(D10);		        			  			  
	  	  chkresult("histogram_measure_kumar_johnson_difference",d11);
	   	  return d11;		
	}
	
	/*
	 *  metric family: 	Combinations Family Metric
	 *  name:			Avg(L_1, L_INF)
	 *  @see 			<a href="http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf">http://www.wseas.us/e-library/conferences/2008/harvard/math/49-577-887.pdf</a>
	 *
	 *	description:	Assumes 2 probability distribution functions (1D vector PDFs) as input and generates a single numerical output.
	 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
	 *  
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
	
	// eqn #56
	public Double histogram_measure_avg_difference(Double[] P, Double[] Q) throws Exception
	{
		  chkargs("histogram_measure_avg_difference",P,Q);
		// bottom term: 	2
	      Double d1	= 2.0d;
	      
		  // top term:  		sum(|P-Q| + max(|P-Q|) 
	      // max(|P-Q|)
	   	  Double[] D2 = sub(P, Q);
	   	  Double[] D3 = abs(D2);
	   	  Double d4 = max(D3);
	   	  Double[] D4 =  mkConstArray(P.length, d4);
	   	  
	   	  // |P-Q|
	   	  Double[] D5 = sub(P, Q);
	   	  Double[] D6 = abs(D5);
	   	  
	   	  // |P-Q| + max(|P-Q|)
	   	  Double[] D7 = add(D6, D4);
	   	  
	   	  // top term
	   	  Double d8 = sum(D7);
	   	  
	   	  // final term = top/bottom
	   	  Double d9 = div(d8, d1); 		        			  			  
	  	  chkresult("histogram_measure_avg_difference",d9);
      	  return d9;		
	}
			  
	
	//////////////////////////////////////////////////////////////////////////////////
	// PRIMITIVE OPERATIONS
	//////////////////////////////////////////////////////////////////////////////////
	
	public Double add(Double a, Double b) throws Exception {
      	chkargs("add",a,b);
		Double c = a + b;
      	chkresult("add",c);
		return c;
	}
	
	public Double[] add(Double[] a, Double[] b) throws Exception {
      	chkargs("add",a,b);
		int len = a.length;
		Double[] c = new Double[len];
		for (int i=0; i < len; i++) c[i] = add(a[i], b[i]);
      	chkresult("add",c);
		return c;
	}
	
	public Double sum(Double[] a) throws Exception {
//      	chkargs("sum",a);
		int len = a.length;
		Double b = 0d;
		
		for (int i=0; i < len; i++) {
			if (a[i] >= Double.MAX_VALUE)			// DN/PNB updates to deal with data that could overflow computationn
				return Double.MAX_VALUE;
			b = add(a[i], b); 
		}
      	chkresult("sum",b);
		return b;
	}	
	
	public Double sub(Double a, Double b) throws Exception {
      	chkargs("sub",a,b);
		Double c = a - b;		
      	chkresult("sub",c);
		return c;
	}

	public Double[] sub(Double[] a, Double[] b) throws Exception {
      	chkargs("sub",a,b);
		int len = a.length;
		Double[] c = new Double[len];
		for (int i=0; i < len; i++) c[i] = sub(a[i], b[i]);
      	chkresult("sub",c);
		return c;
	}	
	
	public Double mult(Double a, Double b) throws Exception {
      	chkargs("mult",a,b);
		Double c = a * b;
      	chkresult("mult",c);
		return c;
	}
	
	public Double[] mult(Double[] a, Double[] b) throws Exception {
      	chkargs("mult",a,b);
		int len = a.length;
		Double[] c = new Double[len];
		for (int i=0; i < len; i++)	c[i] = mult(a[i], b[i]);
      	chkresult("mult",c);
		return c;
	}
	
	public Double mult(Double[] a) throws Exception {
      	chkargs("mult",a);
		int len = a.length;
		Double b = 1d;
		for (int i=0; i < len; i++) b = mult(a[i], b);	

      	chkresult("mult",b);
		return b;
	}			
	
	public Double div(Double a, Double b) throws Exception 
	{
      	chkargs("div",a,b);
		Double c = 0.0d;
		
		// Per guidance from the survey paper, if we receive 0/0, return 0. If x/0, a small value.
		if ( Math.abs(a) <= Double.MIN_VALUE && Math.abs(b) <= Double.MIN_VALUE)	 {  // 0/0
			return 0d;	
		}
		else if ( Math.abs(a) > Double.MIN_VALUE  && Math.abs(b) <= Double.MIN_VALUE  ) { // x/0
			return Double.MAX_VALUE; // dealing with divisions of/near zero => mathematically yield INFINITY. => DN/PNB updated for MAX instead.
		}
		
		c =  a / b;		
      	chkresult("div",c);
		return c;
	}
	
	public Double[] div(Double[] a, Double[] b) throws Exception {
      	chkargs("div",a,b);
		int len = a.length;
		Double[] c = new Double[len];
		for (int i=0; i < len; i++) c[i] = div(a[i], b[i]);
      	chkresult("div",c);
		return c;
	}			
	
	public Double pow(Double a, Double b) throws Exception {
      	chkargs("pow",a,b);
		Double c = Math.pow(a, b);
      	chkresult("pow",c);
		return c;
	}
	
	public Double[] pow(Double[] a, Double[] b) throws Exception {
      	chkargs("pow",a,b);
		int len = a.length;
		Double[] c = new Double[len];
		for (int i=0; i < len; i++) c[i] = pow(a[i], b[i]);
      	chkresult("pow",c);
		return c;
	}									
	
	public Double max(Double a, Double b) throws Exception {
      	chkargs("max",a,b);
		Double c = (a > b) ? a : b;
      	chkresult("max",c);
		return c;
	}
	
	public Double[] max(Double[] a, Double[] b) throws Exception {
      	chkargs("max",a,b);
		int len = a.length;
		Double[] c = new Double[len];
		for (int i=0; i < len; i++) c[i] = max(a[i], b[i]);
      	chkresult("max",c);
		return c;
	}
	
	public Double max(Double[] a) throws Exception {
      	chkargs("max",a);
		int len = a.length;
		if ( len==0 ) return 0d;
		Double b = a[0];
		for (int i=0; i < len; i++) b = max(a[i], b);
      	chkresult("max",b);
		return b;
	}	
		
	public Double min(Double a, Double b) throws Exception {
      	chkargs("min",a,b);
		Double c = (a < b) ? a : b;
      	chkresult("min",c);
		return c;
	}
	
	public Double[] min(Double[] a, Double[] b) throws Exception {
      	chkargs("min",a,b);
		int len = a.length;
		Double[] c = new Double[len];
		for (int i=0; i < len; i++) c[i] = min(a[i], b[i]);
      	chkresult("min",c);
		return c;
	}
	
	public Double min(Double[] a) throws Exception {
      	chkargs("min",a);
		int len = a.length;
		if ( len==0 ) return 0d;
		Double b = a[0];
		for (int i=0; i < len; i++) b = min(a[i], b);
      	chkresult("min",b);
		return b;
	}							
		
	public Double ln(Double a) throws Exception 
	{
      	chkargs("ln",a);
		Double b = 0.0d;

			// NOTE: let case: log 0 be handled by exception-handler of chkresult
			
		b = Math.log(a); // compute log

      	chkresult("ln",b);
		return b;
	}
	
	public Double[] ln(Double[] a) throws Exception {
      	chkargs("ln",a);
		int len = a.length;
		Double[] b = new Double[len];
		for (int i=0; i < len; i++) b[i] = ln(a[i]);
      	chkresult("ln",b);
		return b;
	}							

	// DN/PNB - introduce method for case: (a log b) => also checks-for/handles case: (0 log 0)	
	public Double a_Ln_b(Double a1, Double a2) throws Exception 
	{
		if(Math.abs(a1) <= Double.MIN_VALUE && Math.abs(a2) <= Double.MIN_VALUE)
			return 0.0;
		if(Math.abs(a1) > Double.MIN_VALUE && Math.abs(a2) <= Double.MIN_VALUE)
			return (-Double.MAX_VALUE);
		
		if (a1 >= Double.MAX_VALUE || a2 >= Double.MAX_VALUE)
			return  Double.MAX_VALUE;
		
//      	chkargs("a_Ln_b",a2);
		Double b = 0.0d;
		b = a1 * ln(a2);
		if (b == Double.POSITIVE_INFINITY){
			//System.out.println("A1: " + a1 + " A2: " + a2);
		}
      	chkresult("a_Ln_b",b);
		return b;
	}
	
	public Double[] a_Ln_b(Double[] a1, Double[] a2 ) throws Exception {

		int len = a1.length;
		Double[] b = new Double[len];
		for (int i=0; i < len; i++) b[i] = a_Ln_b(a1[i], a2[i]);
      	chkresult("a_Ln_b",b);
		return b;
	}	
	
	
	public Double abs(Double a) throws Exception {
      	chkargs("abs",a);
		Double b = Math.abs(a);
      	chkresult("abs",b);
		return b;
	}
	
	public Double[] abs(Double[] a) throws Exception {
      	chkargs("abs",a);
		int len = a.length;
		Double[] b = new Double[len];
		for (int i=0; i < len; i++) b[i] = abs(a[i]);
      	chkresult("abs",b);
		return b;
	}								

	
	// NOTE1: unused methods commented for now
	// NOTE2: When/if DO need to use these, can use them for any root: sqrt, cubed-root (cbrt), etc.
	
		/*	public Double root(Double a, b) throws Exception {
			chkargs("root",a);
			chkargs("root",b);
				Double c = pow(a, reciprocal1(b));
			chkresult("root",c);
				return c;
			}
			
			public Double[] root(Double[] a, Double[] b) throws Exception {
			chkargs("root",a);
			chkargs("root",b);
				int len = a.length;
				Double[] c = new Double[len];
				for (int i=0; i < len; i++) c[i] = root(a[i], b[i]);
			chkresult("root",c);
				return c;
			}	
		*/							
			
	public Double square(Double a) throws Exception {
      	chkargs("square",a);
		Double b = a * a;	
      	chkresult("square",b);
		return b;
	}
	
	public Double[] square(Double[] a) throws Exception {
      	chkargs("square",a);
		int len = a.length;
		Double[] b = new Double[len];
		for (int i=0; i < len; i++) b[i] = square(a[i]);
      	chkresult("square",b);
		return b;
	}									
	
	public Double sqrt(Double a) throws Exception {
      	chkargs("sqrt",a);
		Double b = Math.sqrt(a);
      	chkresult("sqrt",b);
		return b;
	}
	
	public Double[] sqrt(Double[] a) throws Exception {
      	chkargs("sqrt",a);
		int len = a.length;
		Double[] b = new Double[len];
		for (int i=0; i < len; i++)	b[i] = sqrt(a[i]);
      	chkresult("sqrt",b);
		return b;
	}
	
	public Double cube(Double a) throws Exception {
      	chkargs("cube",a);
		Double b = a * a * a;	
      	chkresult("cube",b);
		return b;
	}
	
	public Double[] cube(Double[] a) throws Exception {
      	chkargs("cube",a);
		int len = a.length;
		Double[] b = new Double[len];
		for (int i=0; i < len; i++) b[i] = cube(a[i]);
      	chkresult("cube",b);
		return b;
	}
	
	public Double cbrt(Double a) throws Exception {
      	chkargs("cbrt",a);
		Double b = Math.cbrt(a);
      	chkresult("cbrt",b);
		return b;
	}
	
	public Double[] cbrt(Double[] a) throws Exception {
      	chkargs("cbrt",a);
		int len = a.length;
		Double[] b = new Double[len];
		for (int i=0; i < len; i++) b[i] = cbrt(a[i]);
      	chkresult("cbrt",b);
		return b;
	}
			
	public Double reciprocal1(Double a) throws Exception {
      	chkargs("reciprocal1",a);
		Double b = div(1d, a);
      	chkresult("reciprocal1",b);
		return b;
	}
	
	public Double[] reciprocal1(Double[] a) throws Exception {
      	chkargs("reciprocal1",a);
		int len = a.length;
		Double[] b = new Double[len];
		for (int i=0; i < len; i++) b[i] = reciprocal1(a[i]);
      	chkresult("reciprocal1",b);
		return b;
	}
	
//////////////////////////////////////////////////////////////////////////////////
// SUPPORT METHODS
//////////////////////////////////////////////////////////////////////////////////
	
  	/*
	 *  Support method for metric implementations  
	 *  description: 	creates an array of a specific size, all having the same value (used in calculations).
	 *  @author 		B. Long
	 *  version:		1.0
	 */		      	   
  public Double[] mkConstArray(Integer len, Double value) throws Exception 
  {
	  
	  chkargs("mkConstArray", len);
	  chkargs("mkConstArray", value);
	  
	  Double[] a = new Double[len];
	  for (int i=0; i < len; i++) a[i] = value;
    
      chkresult("mkConstArray", a);
      
    return a;
  }
  
  
	/*
	 *  Support method for metric implementations
	 *  description: 	Converts the content of an RGB histogram to the form used by the metric algorithms.
	 *  @author 		B. Long
	 *  version:		1.0
	 */	
  
  public Double[] rgbHistogram2Double(final RGBHistogramDescriptor d) throws Exception { 
	  
      	  chkargs("rgbHistogram2Double",d);
      	  
	      int len   	= d.getNumBins();
	      int bands  	= d.getNumBands();
	      Double[] r 	= new Double[len*bands];
	      int hist[][] 	= d.getHistogram();
	      
	      // convert
	      for ( int i=0; i < len; i++ ) {
	        for (int j=0; j < bands; j++){
	        	r[ (i*bands) + j ] = ( new Double(hist[i][j]) );
	        }      
	      }	      
	      
      	  chkresult("rgbHistogram2Double",r);
	      return r;
  } 
   
	/*
	 *  Support method for metric implementations  
	 *  description: 	Converts the content of a gray-scale histogram to the form used by the metric algorithms.
	 *  @author 		B. Long
	 *  version:		1.0
	 */	 

  public Double[] grayScaleHistogram2Double(final GrayscaleHistogramDescriptor d) throws Exception { 
	  
      chkargs("grayscaleHistogram2Double",d);
      
      int len    = d.getNumBins();
      int hist[] = d.getHistogram();
      Double[] r 	= new Double[len];

      for ( int i=0; i < len; i++ ) {
    	  r[i] = ( new Double(hist[i]) );
      }
      
      chkresult("grayscaleHistogram2Double",r);
      
      return r;
  } 

	/*
	 *  Support method for metric implementations  
	 *  description: 	Normalizes a raw histogram to relative frequencies. (Where the histogram was received from an RGB or gray-scale histogram feature.)
	 *  @author 		B. Long
	 *  version:		1.0
	 */		 


  public Double[] normalizeHistogram(final Double[] h) throws Exception { 
	  
	chkargs("normalizeHistogram",h);
        double total = 0;
        int len = h.length;
        for (int i=0; i < len; i++) {
        	if ( h[i] != 0 ) {
        		total += h[i];
        	}
        }

      Double[] r = new Double[len];
      for (int i=0; i < len; i++) {
          r[i] = new Double( div(h[i], total)  );
      }
      
      chkresult("normalizeHistogram",r);
      return r;
 } 

	/*
	 *  Support method for metric implementations  
	 *  description: 	Normalizes an RGB histogram to relative frequencies.
	 *  @author 		B. Long
	 *  version:		1.0
	 */		      


	  public Double[] normalizeRgbHistogram(final RGBHistogramDescriptor d) throws Exception { 
		  
	      chkargs("normalizeRgbHistogram",d);
	      Double[] h = rgbHistogram2Double(d);
	      h = normalizeHistogram(h);
	      chkresult("normalizeRgbHistogram",h);
	      return h;         
	  }       

	/*
	 *  Support method for metric implementations  
	 *  description: 	Normalizes a gray-scale histogram to relative frequencies.
	 *  @author 		B. Long
	 *  version:		1.0
	 */		      	 

	  public Double[] normalizeGrayscaleHistogram(final GrayscaleHistogramDescriptor d) throws Exception { 
		  
	    	chkargs("normalizeGrayscaleHistogram",d);
	        Double[] h = grayScaleHistogram2Double(d);
	        h = normalizeHistogram(h);
	        chkresult("normalizeGrayscaleHistogram",h);
	        return h;         
	  } 

	
//////////////////////////////////////////////////////////////////////////////////
// Error checks
//////////////////////////////////////////////////////////////////////////////////
	  
  public boolean chkargs( String methodName, RGBHistogramDescriptor a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  return true;	// if we get here, we've had no exceptions.
  }	  

  public boolean chkargs( String methodName, GrayscaleHistogramDescriptor a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  return true;	// if we get here, we've had no exceptions.
  }	    
  
  public boolean chkargs( String methodName, Double a, Double b ) throws Exception {
	  if ( 	a.equals(Double.NaN) ) throw new HWIndependenceException(methodName + ": first argument NAN indeterminate value");
	  if ( 	a.equals(Double.POSITIVE_INFINITY) ) throw new SingularityTreatmentException(methodName  + ": first argument POSITIVE_INFINITY value");
	  if ( 	a.equals(Double.NEGATIVE_INFINITY) ) throw new SingularityTreatmentException(methodName  + ": first argument NEGATIVE_INFINITY value");
	  if ( 	b.equals(Double.NaN) ) throw new HWIndependenceException(methodName + ": second argument NAN indeterminate value");
	  if ( 	b.equals(Double.POSITIVE_INFINITY) ) throw new SingularityTreatmentException(methodName  + ": second argument POSITIVE_INFINITY value");
	  if ( 	b.equals(Double.NEGATIVE_INFINITY) ) throw new SingularityTreatmentException(methodName  + ": second argument NEGATIVE_INFINITY value");
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  if ( 	b == null ) throw new SingularityTreatmentException(methodName  + ": second argument null value");
	  return true;	// if we get here, we've had no exceptions.
  }
  
  // NOTE: This check for an int arg assumes a non-negative integer
  public boolean chkargs( String methodName, int a ) throws Exception {
	  if ( 	a < 0 ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  return true;	// if we get here, we've had no exceptions.
  }  
  
  public boolean chkargs( String methodName, Integer a ) throws Exception {
	  if ( 	a < 0 ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  return true;	// if we get here, we've had no exceptions.
  }  
      
  public boolean chkargs( String methodName, Double a ) throws Exception {
	  if ( 	a.equals(Double.NaN) ) throw new HWIndependenceException(methodName  + ": first argument NAN indeterminate value");
	  if ( 	a.equals(Double.POSITIVE_INFINITY) ) throw new SingularityTreatmentException(methodName  + ": first argument POSITIVE_INFINITY value");
	  if ( 	a.equals(Double.NEGATIVE_INFINITY) ) throw new SingularityTreatmentException(methodName  + ": first argument NEGATIVE_INFINITY value");
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  return true;	// if we get here, we've had no exceptions.
  }
  
  public boolean chkargs( String methodName, Double[] a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  
	  int len = a.length;
	  for (int i=0; i < len; i++) {
		  chkargs(methodName, a[i]);
	  }	  
	  return true;	// if we get here, we've had no exceptions.
  }
  
  public boolean chkargs( String methodName, Integer[] a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  
	  int len = a.length;
	  for (int i=0; i < len; i++) {
		  chkargs(methodName, a[i]);
	  }	  
	  return true;	// if we get here, we've had no exceptions.
  }
  
  public boolean chkargs( String methodName, Double[][] a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  
	  int len = a.length;
	  for (int i=0; i < len; i++) {
		  chkargs(methodName, a[i]);
	  }	  
	  return true;	// if we get here, we've had no exceptions.
  }
  
  public boolean chkargs( String methodName, Integer[][] a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  
	  int len = a.length;
	  for (int i=0; i < len; i++) {
		  chkargs(methodName, a[i]);
	  }	  
	  return true;	// if we get here, we've had no exceptions.
  }
  
  public boolean chkargs( String methodName, Double[][][] a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  
	  int len = a.length;
	  for (int i=0; i < len; i++) {
		  chkargs(methodName, a[i]);
	  }	  
	  return true;	// if we get here, we've had no exceptions.
  } 
  
  public boolean chkargs( String methodName, Integer[][][] a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  
	  int len = a.length;
	  for (int i=0; i < len; i++) {
		  chkargs(methodName, a[i]);
	  }	  
	  return true;	// if we get here, we've had no exceptions.
  }  
    
  public boolean chkargs( String methodName, Double[] a, Double[] b ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  if ( 	b == null ) throw new SingularityTreatmentException(methodName  + ": second argument null value");
	  chkargs(methodName,a);
	  chkargs(methodName,b);
	  return true;	// if we get here, we've had no exceptions.
  }

  public boolean chkargs( String methodName, Integer[] a, Integer[] b ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  if ( 	b == null ) throw new SingularityTreatmentException(methodName  + ": second argument null value");
	  chkargs(methodName,a);
	  chkargs(methodName,b);
	  return true;	// if we get here, we've had no exceptions.
  }  
  
  public boolean chkargs( String methodName, Double[][] a, Double[][] b ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  if ( 	b == null ) throw new SingularityTreatmentException(methodName  + ": second argument null value");
	  chkargs(methodName,a);
	  chkargs(methodName,b);
	  return true;	// if we get here, we've had no exceptions.
  }
  
  public boolean chkargs( String methodName, Integer[][] a, Integer[][] b ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  if ( 	b == null ) throw new SingularityTreatmentException(methodName  + ": second argument null value");
	  chkargs(methodName,a);
	  chkargs(methodName,b);
	  return true;	// if we get here, we've had no exceptions.
  }  
 
  public boolean chkargs( String methodName, Double[][][] a, Double[][][] b ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  if ( 	b == null ) throw new SingularityTreatmentException(methodName  + ": second argument null value");
	  chkargs(methodName,a);
	  chkargs(methodName,b);
	  return true;	// if we get here, we've had no exceptions.
  }  
  
  public boolean chkargs( String methodName, Integer[][][] a, Integer[][][] b ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  if ( 	b == null ) throw new SingularityTreatmentException(methodName  + ": second argument null value");
	  chkargs(methodName,a);
	  chkargs(methodName,b);
	  return true;	// if we get here, we've had no exceptions.
  }    
       
   
  public boolean chkresult( String methodName, Double a ) throws Exception {
	return chkargs(methodName,a);  
  }  
	
  public boolean chkresult( String methodName, Double[] a ) throws Exception {
	return chkargs(methodName,a);  
  }
  
  public boolean chkresult( String methodName, Double[][] a ) throws Exception {
	return chkargs(methodName,a);  
  }  

  public boolean chkresult( String methodName, Double[][][] a ) throws Exception {
	return chkargs(methodName,a);  
  }   
  
  public boolean chkresult( String methodName, Integer a ) throws Exception {
	return chkargs(methodName,a);  
  }  
	
  public boolean chkresult( String methodName, Integer[] a ) throws Exception {
	return chkargs(methodName,a);  
  }
  
  public boolean chkresult( String methodName, Integer[][] a ) throws Exception {
	return chkargs(methodName,a);  
  }  

  public boolean chkresult( String methodName, Integer[][][] a ) throws Exception {
	return chkargs(methodName,a);  
  }   
 	
	
/////////////////////////////////////////////////////
// - - - - - - - - - - - - - - - - - - - - - - - - -
/////////////////////////////////////////////////////
	
	  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
	  // -----------------------------------------
	  // Labeled/Geometric/Structural Metrics
	  // -----------------------------------------

		/*
		 *  name:			Adjusted Rand Index : (ARI)
		 *  @see 			TBD.
		 *
		 *	description:	Takes 2 representations of image pixel data as input and generates a single numerical output.
		 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
		 *  
		 *  @author 		B. Long
		 *  version:		1.0
		 */	

	  // assumes we have converted from voxels to 1D arrays here.
	  public Double pixel_measure_ari_nD(Double[] im1, Double[] im2) throws Exception {
		  
			chkargs("pixel_measure_ari",im1,im2);	
	        Double ari     = 0.0d;
	        Double ri     = 0.0d;

		     // We've assumed that images are identical in size at the application level.
		     // Ergo,
	        int imgSize = im1.length;
     
	        Double[] im1PVS = uniqueValues(im1);	// unique pixelValueSpace for each image, respectively
	        Double[] im2PVS = uniqueValues(im2);
	        int im1LargestPV = im1PVS[ im1PVS.length - 1 ].intValue();	// get largest pixelValue from each pixelValueSpace
	        int im2LargestPV = im2PVS[ im2PVS.length - 1 ].intValue();	
	        
	        im1PVS = null;
	        im2PVS = null;
     
	        double[][] commonPixels = new double[im1LargestPV+1][im2LargestPV+1];	// for counting total pixel overlaps in the two images
	        	// NOTE: We add 1 to each pixelValue dimension b/c when a given pixelValue, like 255, is indexed, it goes to
	        	//	     index-location 255, which works if we allocated 256 pixelLocations in which to index/count.
	        
	        int im1Idx = 0;	// respective indices in commonPixel tracking matrix
	        int im2Idx = 0;
   
	        for (int i=0; i < imgSize; i++) {
	        	im1Idx = im1[i].intValue();		// get pixel index values into the commonPixels pixelValueCoordinateSpace
	        	im2Idx = im2[i].intValue();	        	
	        	commonPixels[im1Idx][im2Idx]++;	// increment relative pixel values at (im1Px, im2Px) location
	        }
	        im1 = null;
	        im2 = null;
     
	        double[] A = getRowCounts( commonPixels );	// get row counts
	        double[] B = getColCounts( commonPixels ); // get column counts
      
	        // linearize commonPixels space and get its sum
	        double[] linearized_commonPixels = vectorize(commonPixels);	 
	        
	        commonPixels = null;
	        
	        Double N = sum( linearized_commonPixels );	        
	        // perform (N*(N-1)/2) over entire commonPixels space and other counts
	        Double Nij = gaussSum( linearized_commonPixels );
	        
	        linearized_commonPixels = null;
	        
	        Double A2  = gaussSum( A );
	        Double B2  = gaussSum( B );
		    Double N2  = gaussSum( N );		    
		    Double ARI = ( Nij - ((A2*B2)/N2) ) / ( (0.5 * (A2+B2)) - ((A2*B2)/N2) );
		    Double  RI = 1 + (((2*Nij) - A2 - B2) / N2) ;	 
		    
		    chkresult("pixel_measure_ari",ARI);
		   
		    _ARI=ARI;
		    _RI = RI;
		    
		  return ARI;
	  }
	  
	  /*
	   * Perform equivalent of Gauss's Sum (N*(N-1))/2 for a vector of integer values.
	   */
	  public Integer gaussSum( Integer[] V ) throws Exception {
		  
		  chkargs("gaussSum",V);
		  int len1 = V.length;
		  Integer[] V2sub1 = sub(V, 1);					// subtract 1 from vector elements
		  Integer[] mulResult = dotProd(V, V2sub1);		// multiply them
		  Integer sum = sum(mulResult);
		  Integer div = div( sum, 2);
		  chkresult("gaussSum",div);
		  
		  return div;
	  }

	  public Double gaussSum( Double [] V ) throws Exception {
		  chkargs("gaussSum",V);
		  int len1 = V.length;
		  Double[] V2sub1 = sub(V, 1.0d);					// subtract 1 from vector elements
		  Double[] mulResult = dotProd(V, V2sub1);		// multiply them
		  Double sum = sum(mulResult);
		  Double result = div( sum, 2.0d);
		  chkresult("gaussSum",result);
		  return result;
	  }	  
	  
	  public double gaussSum( double [] V ) throws Exception {
		  chkargs("gaussSum",V);
		  int len1 = V.length;
		  double[] V2sub1 = sub(V, 1.0d);					// subtract 1 from vector elements
		  double[] mulResult = dotProd(V, V2sub1);		// multiply them
		  double sum = sum(mulResult);
		  double result = div( sum, 2.0d);
		  chkresult("gaussSum",result);
		  return result;
	  }		  
	  
	  public int gaussSum( Integer N ) throws Exception {
		  chkargs("gaussSum",N);
		  Integer result = (N*(N-1)) / 2; // BJL: BUGFIX - should be n(n+1)/2 for true gaussSum; however, only RI/ARI use this, and the correct computation for RI/ARI is n(n-1)/2. So, despite this misnomer, RI/ARI were being calculated correctly.
		  chkresult("gaussSum", result);
		  return result;
	  }
	  
	  public Double gaussSum( Double N ) throws Exception {
		  chkargs("gaussSum",N);
		  Double result = (N*(N-1)) / 2;  // BJL: BUGFIX - should be n(n+1)/2 for true gaussSum; however, only RI/ARI use this, and the correct computation for RI/ARI is n(n-1)/2. So, despite this misnomer, RI/ARI were being calculated correctly.
		  chkresult("gaussSum", result);
		  return result;		  
	  }	  
	  
	  
	  public Integer[] sub( Integer[] V, Integer num ) throws Exception {
		  chkargs("sub", V);
		  chkargs("sub", num);
		  int len = V.length;
		  Integer[] V2 = new Integer[len];
		  for (int i=0; i < len; i++) {
			  V2[i] = V[i] - num;
		  }
		  chkresult("sub", V2);
		  return V2;
	  }
	  
	  public Double[] sub( Double[] V, Double num ) throws Exception {
		  chkargs("sub", V);
		  chkargs("sub", num);
		  int len = V.length;
		  Double[] V2 = new Double[len];
		  for (int i=0; i < len; i++) {
			  V2[i] = V[i] - num;
		  }
		  chkresult("sub", V2);
		  return V2;
	  }	  
	  
	  public double[] sub( double[] V, double num ) throws Exception {
		  chkargs("sub", V);
		  chkargs("sub", num);
		  int len = V.length;
		  double[] V2 = new double[len];
		  for (int i=0; i < len; i++) {
			  V2[i] = V[i] - num;
		  }
		  chkresult("sub", V2);
		  return V2;
	  }	  	  
	  
	  public Integer[] dotProd( Integer[] V1, Integer[] V2 ) throws Exception {
		  chkargs("dotProd", V1);
		  chkargs("dotProd", V2);
		  int len1 = V1.length;
		  int len2 = V2.length;
		  Integer[] result = null;
		  
		  // NOTE: These should be the same length		  
		  if ( len1 != len2 ) throw new Exception("vectors are not the same length");
		  
		  // if get here, assume they're the same length
		  result = new Integer[len1];		  
		  for (int i=0; i < len1; i++) {
			  result[i] = V1[i] * V2[i];
		  }
		  
		  chkresult("dotProd", result);
		  
		  return result;
	  }
	  
	  public Double[] dotProd( Double[] V1, Double[] V2 ) throws Exception {
		  chkargs("dotProd", V1);
		  chkargs("dotProd", V2);
		  int len1 = V1.length;
		  int len2 = V2.length;
		  Double[] result = null;
		  
		  // NOTE: These should be the same length		  
		  if ( len1 != len2 ) throw new Exception("vectors are not the same length");
		  
		  // if get here, assume they're the same length
		  result = new Double[len1];		  
		  for (int i=0; i < len1; i++) {
			  result[i] = V1[i] * V2[i];
		  }		  
		  
		  chkresult("dotProd", result);
		  
		  return result;
	  }
	  
	  public double[] dotProd( double[] V1, double[] V2 ) throws Exception {
		  chkargs("dotProd", V1);
		  chkargs("dotProd", V2);
		  int len1 = V1.length;
		  int len2 = V2.length;
		  double[] result = null;
		  
		  // NOTE: These should be the same length		  
		  if ( len1 != len2 ) throw new Exception("vectors are not the same length");
		  
		  // if get here, assume they're the same length
		  result = new double[len1];		  
		  for (int i=0; i < len1; i++) {
			  result[i] = V1[i] * V2[i];
		  }		  
		  
		  chkresult("dotProd", result);
		  
		  return result;
	  }	  
	  
	  
	  public Integer div( Integer a, Integer b ) throws Exception {
		  chkargs("div", a);
		  chkargs("div", b);
		  
		  if ( b == 0 )
			  throw new SingularityTreatmentException("divide by zero during vector divide operation");
		  Integer result = a / b;
		  
		  chkresult("div", result);
		  
		  return result;		  
	  }
	  
		public Integer[] vectorize( Integer[][] A ) throws Exception {
			
			chkargs("vectorize",A);
			
			int len1 = A.length;
			int len2 = A[0].length;
			int len3 = len1*len2;
			Integer[] C = new Integer[len1*len2];
			for (int i=0; i < len1; i++) {
			  for (int j=0; j < len2; j++) {
				C[(i*len2)+j] = A[i][j];
			  }
			}
			
			chkresult("vectorize",C);
			
			return C;
		}	  
	  
	  public Integer[] getRowCounts( Integer[][] M ) throws Exception {
		  
		  chkargs("getRowCounts", M);
		  
		  int rows = M.length;
		  Integer[] rowCounts = new Integer[rows];		  
		  for (int i=0; i < rows; i++) {
			  rowCounts[i] = sum( getRow(M,i) );
		  }
		  
		  chkresult("getRowCounts", rowCounts );
		  
		  return rowCounts;
	  }
	  
	  public double[] getRowCounts( double[][] M ) throws Exception {
		  
		  chkargs("getRowCounts", M);
		  
		  int rows = M.length;
		  double[] rowCounts = new double[rows];		  
		  for (int i=0; i < rows; i++) {
			  rowCounts[i] = sum( getRow(M,i) );
		  }		  
		  
		  chkresult("getRowCounts", rowCounts);
		  
		  return rowCounts;
	  }		  
	  
	  public Double[] getRowCounts( Double[][] M ) throws Exception {
		  
		  chkargs("getRowCounts", M);
		  
		  int rows = M.length;
		  Double[] rowCounts = new Double[rows];		  
		  for (int i=0; i < rows; i++) {
			  rowCounts[i] = sum( getRow(M,i) );
		  }		  
		  
		  chkresult("getRowCounts", rowCounts);
		  
		  return rowCounts;
	  }	  
	  
	  public Integer[] getRow( Integer[][] M, Integer row ) throws Exception {
		  
		  chkargs("getRow", M);
		  chkargs("getRow", row);
		  
		  Integer[] result = null;
		  
		  if ( row >=0 && row < M.length )
			  result = M[row];
		  
		  chkresult("getRow", result);
		  
		  return result;
	  }
	  
	  public double[] getRow( double[][] M, int row ) throws Exception {
		  
		  chkargs("getRow", M);
		  chkargs("getRow", row);
		  double[] result = null;
		  if ( row >=0 && row < M.length )
			  result = M[row];

		  chkresult("getRow", result);
		  
		  return result;
	  }		  
	  
	  public Double[] getRow( Double[][] M, Integer row ) throws Exception {
		  
		  chkargs("getRow", M);
		  chkargs("getRow", row);
		  Double[] result = null;
		  if ( row >=0 && row < M.length )
			  result = M[row];

		  chkresult("getRow", result);
		  
		  return result;
	  }	  
	  
	  
	  public Integer[] getColCounts( Integer[][] M ) throws Exception {
		  
		  chkargs("getColCounts", M);
		  
		  int cols = M[0].length;
		  Integer[] colCounts = new Integer[cols];		  
		  for (int i=0; i < cols; i++) {			  
			  colCounts[i] = sum( getCol(M,i) );
		  }		  
		  
		  chkresult("getColCounts", colCounts);
		  
		  return colCounts;
	  }
	  
	  public double[] getColCounts( double[][] M ) throws Exception {
		  
		  chkargs("getColCounts", M);
		  
		  int cols = M[0].length;
		  double[] colCounts = new double[cols];		  
		  for (int i=0; i < cols; i++) {			  
			  colCounts[i] = sum( getCol(M,i) );
		  }
		  
		  chkresult("getColCounts", colCounts);
		  
		  return colCounts;
	  }
	  
	  public Double[] getColCounts( Double[][] M ) throws Exception {
		  
		  chkargs("getColCounts", M);
		  
		  int cols = M[0].length;
		  Double[] colCounts = new Double[cols];		  
		  for (int i=0; i < cols; i++) {			  
			  colCounts[i] = sum( getCol(M,i) );
		  }
		  
		  chkresult("getColCounts", colCounts);
		  
		  return colCounts;
	  }

	  public Integer[] getCol( Integer[][] M, Integer col ) throws Exception {
		  
		  chkargs("getCol", M);
		  chkargs("getCol", col);
		  
		  Integer[] result = null;
		  
		  if ( col >=0 && col < M[0].length ) {
			  
			  int rows = M.length;
			  Integer[] column = new Integer[rows];
			  for (int i=0; i < rows; i++) {
				  column[i] = M[i][col];
			  }			  
			  result = column;
		  }
		  
		  chkresult("getCol", result);
		  
		  return result;

	  }	  
	  
	  public double[] getCol( double[][] M, int col ) throws Exception {
		  
		  chkargs("getCol", M);
		  chkargs("getCol", col);

		  double[] result = null;
		  
		  if ( col >=0 && col < M[0].length ) {
			  
			  int rows = M.length;
			  double[] column = new double[rows];
			  for (int i=0; i < rows; i++) {
				  column[i] = M[i][col];
			  }			  
			  result = column;
		  }
		 
		  chkresult("getCol", result);
		  
		  return result;
	  }		  

	  public Double[] getCol( Double[][] M, Integer col ) throws Exception {
		  
		  chkargs("getCol", M);
		  chkargs("getCol", col);

		  Double[] result = null;
		  
		  if ( col >=0 && col < M[0].length ) {
			  
			  int rows = M.length;
			  Double[] column = new Double[rows];
			  for (int i=0; i < rows; i++) {
				  column[i] = M[i][col];
			  }			  
			  result = column;
		  }
		 
		  chkresult("getCol", result);
		  
		  return result;
	  }	  
	  
	  
	  public Integer sum( Integer[] V ) throws Exception {
		  
		  chkargs("sum", V);
		  
		  int len = V.length;
		  Integer sum = 0;
		  for (int i=0; i < len; i++) {
			  sum += V[i];
		  }
		  
		  chkresult("sum", sum);
		  return sum;
	  }
	  
	  public Double[] uniqueValues( Double[] imgPixels ) throws Exception {
		  
		  chkargs("uniqueValues", imgPixels);
		  
		  Double[] unique = null;
		  LinkedHashMap map = new LinkedHashMap();
		  
		  int len = imgPixels.length;
		  double pixelValue = 0.0d; 
		  for (int i=0; i < len; i++) {
			  pixelValue = imgPixels[i];
			  if ( !map.containsKey( pixelValue ) ) {
				  map.put(pixelValue, pixelValue);
			  }
		  }
		  
		  // sort unique map here
		  Object[] sorted = new TreeSet( map.values() ).toArray();
		  
		  int len2 = sorted.length;
		  unique = new Double[len2];
		  
		  // convert back to doubles
		  for (int i=0; i < len2; i++) {
			  unique[i] = (Double) sorted[i];
		  }
	
		  chkresult("uniqueValues", unique);
		  
		  return unique;
	  }


		/*
		 *  name:			Rand Index : (RI)
		 *  @see 			TBD.
		 *
		 *	description:	Takes 2 representations of image pixel data as input and generates a single numerical output.
		 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
		 *  
		 *  @author 		B. Long
		 *  version:		1.0
		 */	
	  
	  
	  //assumes we're getting our data via voxels->1D-array
	  public Double pixel_measure_ri_nD(Double[] im1, Double[] im2) throws Exception {
			chkargs("pixel_measure_ri",im1,im2);	
	        Double ari     = 0.0d;
	        Double ri     = 0.0d;

		     // We've assumed that images are identical in size at the application level.
		     // Ergo,
	        int imgSize = im1.length;
     
	        Double[] im1PVS = uniqueValues(im1);	// unique pixelValueSpace for each image, respectively
	        Double[] im2PVS = uniqueValues(im2);
	        int im1LargestPV = im1PVS[ im1PVS.length - 1 ].intValue();	// get largest pixelValue from each pixelValueSpace
	        int im2LargestPV = im2PVS[ im2PVS.length - 1 ].intValue();	
     
	        im1PVS = null;
	        im2PVS = null;
	        
	        double[][] commonPixels = new double[im1LargestPV+1][im2LargestPV+1];	// for counting total pixel overlaps in the two images
	        	// NOTE: We add 1 to each pixelValue dimension b/c when a given pixelValue, like 255, is indexed, it goes to
	        	//	     index-location 255, which works if we allocated 256 pixelLocations in which to index/count.
	        
	        int im1Idx = 0;	// respective indices in commonPixel tracking matrix
	        int im2Idx = 0;
   
	        for (int i=0; i < imgSize; i++) {
	        	im1Idx = im1[i].intValue();		// get pixel index values into the commonPixels pixelValueCoordinateSpace
	        	im2Idx = im2[i].intValue();	        	
	        	commonPixels[im1Idx][im2Idx]++;	// increment relative pixel values at (im1Px, im2Px) location
	        }
     
	        im1 = null;
	        im2 = null;
	        
	        double[] A = getRowCounts( commonPixels );	// get row counts
	        double[] B = getColCounts( commonPixels ); // get column counts
      
	        // linearize commonPixels space and get its sum
	        double[] linearized_commonPixels = vectorize(commonPixels);	 
	        
	        commonPixels = null;
	        
	        Double N = sum( linearized_commonPixels );	        
	        // perform (N*(N-1)/2) over entire commonPixels space and other counts
	        Double Nij = gaussSum( linearized_commonPixels );
	        
	        linearized_commonPixels = null;
	        
	        Double A2  = gaussSum( A );
	        Double B2  = gaussSum( B );
		    Double N2  = gaussSum( N );		    
		    Double ARI = ( Nij - ((A2*B2)/N2) ) / ( (0.5 * (A2+B2)) - ((A2*B2)/N2) );
		    Double  RI = 1 + (((2*Nij) - A2 - B2) / N2) ;	 
		    
		    chkresult("pixel_measure_ri",ARI);
		    
		    _ARI=ARI;
		    _RI = RI;
		    
		  return RI;	  
	}		  
	  
		/*
		 *  name:			Dice
		 *  @see 			TBD.
		 *
		 *	description:	Takes 2 representations of image pixel data as input and generates a single numerical output.
		 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
		 *  
		 *  @author 		B. Long
		 *  version:		1.0
		 */	
	  
	  
	  public Double pixel_measure_dice_nD(Double[] im1, Double[] im2) throws Exception {	
		  
			chkargs("pixel_measure_dice",im1,im2);	

	        im1 = pvrel_greaterThan(im1, 0.0d);			// transform all input images to binary via pixel-value-relation: pixelValue>0
	        im2 = pvrel_greaterThan(im2, 0.0d);

	        Double[] I = add(im1,im2);					// add their values together, creating a composite image of their corresponding values
	        
	        im1 = null;
	        im2 = null;
	        
	        Double[] overlap = pvrel_equalTo(I, 2.0d);	// transform combined image into binary via pixel-value-relation; pixelValue==2 (i.e., where images had pixelValue==1)\
	        
	        // compute common color area bet/w both images
	        I = pvrel_greaterThan(I, 0.0d);	// transforms back to binary  
	        Double sum_overlap = sum(overlap);
	        
	        overlap = null;
	        
	        Double sum_I 	   = sum(I); 
	        
	        // use this information to compute dice
	        Double dice = div( 
	        				mult(2.0d,sum_overlap), 
	        			    add(sum_I,sum_overlap) 
	        			  );
	        
	        
	        chkresult("pixel_measure_dice", dice);
	        
	        return dice;
	  }	 
	  
	  public void show( String name, Double[] V ) throws Exception
	  {
		  int len = V.length;
		  String s = name + ": ";
		  for (int i=0; i < len; i++ ) {
			  if ( i!=0 ) s+= ", ";
			  s += V[i];
		  }
		  System.out.println(s);
	  }
	  
	  public void show( String name, Double d ) throws Exception
	  {
		  String s = name + ": " + d;
		  System.out.println(s);
	  }	  
	  
	  public Double[] mult( Double num, Double[] V ) throws Exception {
		  
		  chkargs("mult", V);
		  
		  int len = V.length;
		  Double[] product = new Double[len];
		  for (int i=0; i < len; i++) {
			  product[i] = mult(V[i],num);
		  }
		  
		  chkresult("mult", product);
		  
		  return product;
	  }
	  
		/*
		 *  name:			Jaccard
		 *  @see 			TBD.
		 *
		 *	description:	Takes 2 representations of image pixel data as input and generates a single numerical output.
		 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
		 *  
		 *  @author 		B. Long
		 *  version:		1.0
		 */	
	  
	  public Double pixel_measure_jaccard_nD(Double[] im1, Double[] im2) throws Exception {
		  
			chkargs("pixel_measure_jaccard",im1,im2);	   
	        
	        im1 = pvrel_greaterThan(im1, 0.0d);	// transform all input images to binary via pixel-value-relation: pixelValue>0
	        im2 = pvrel_greaterThan(im2, 0.0d);

	        Double[] I = add(im1,im2);		// add their values together, creating a composite image of their corresponding values
	        
	        im1 = null;
	        im2 = null;
	        
	        Double[] overlap = pvrel_equalTo(I, 2.0d);	// transform combined image into binary via pixel-value-relation; pixelValue==2 (i.e., where images had pixelValue==1)\
	        // compute common color area bet/w both images
	        I = pvrel_greaterThan(I, 0.0d);	// transforms back to binary  
	        Double sum_overlap = sum(overlap);
	        
	        overlap = null;
	        
	        Double sum_I 	   = sum(I); 
	        
	        // use this information to compute jaccard	        
	        Double jaccard = div( sum_overlap,  sum_I );
	        
	        chkresult("pixel_measure_jaccard", jaccard );
	        
	        return jaccard;
	  }	 	  	  
	  
	  /*
	   * PixelValueRelation (pvrel): greater than (GT) given pixel value.
	   */
	  public Double[] pvrel_greaterThan( Double[] img, Double pixelValue ) throws Exception {
		  
		  chkargs("pvrel_greaterThan", img);
		  chkargs("pvrel_greaterThan", pixelValue);
		  
		  int len = img.length;
		  Double[] result = new Double[len];
		  for (int i=0; i < len; i++) {
			  if ( img[i] > pixelValue ) {
				  result[i] = 1.0d;
			  }
			  else {
				  result[i] = 0.0d;
			  }
		  }
		  
		  chkresult("pvrel_greaterThan", result);
		  
		  return result;
	  }
	  
	  /*
	   * PixelValueRelation (pvrel): equalTo given pixel value.
	   */
	  public Double[] pvrel_equalTo( Double[] img, Double pixelValue ) throws Exception {
		  
		  
		  chkargs("pvrel_equalTo", img);
		  chkargs("pvrel_equalTo", pixelValue);
		  
		  int len = img.length;
		  Double[] result = new Double[len];
		  for (int i=0; i < len; i++) {
			  if ( img[i].equals( pixelValue ) ) {
				  result[i] = 1.0d;
			  }
			  else {
				  result[i] = 0.0d;
			  }
		  }
		  
		  chkresult("pvrel_equalTo", result);
		  
		  return result;
	  }	  
	  
		/*
		 *  name:			Total Error Rate Evaluation Measure (TEE)
		 *  @see 			TBD.
		 *
		 *	description:	Takes 2 representations of image pixel data as input and generates a single numerical output.
		 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
		 *  
		 *  @author 		B. Long
		 *  version:		1.0
		 */		
		
	public static Double _TEE = 0.0;
	public static Double _TET = 0.0;
	public static Double _ARI = 0.0;
	public static Double _RI  = 0.0;
	
	public Double pixel_measure_tee_nD( Double[] im1, Double[] im2 ) throws Exception
	{
 	 chkargs("pixel_measure_tee",im1,im2);

      Double si     = 0.0d;
      Double fp     = 0.0d;
      Double fn     = 0.0d;
      Double in_TE   = 0.0d;
      Double tet     = 0.0d;
      Double tee     = 0.0d;

		  Double[] T       = null;
		  Double[] E       = null;
        Double[] Tc      = null;
        Double[] Ec      = null;
        Double[] N_TE    = null;
        Double[] N_TcE   = null;
        Double[] N_TEc   = null;
        Double[] U_TE    = null;



     	T= logical(im1);
     	E= logical(im2);

			Tc    = not(T);	

			Ec    = not(E);
         
			N_TE    = and(T,E);
			N_TcE   = and(Tc,E);
			N_TEc   = and(T,Ec);
			U_TE    = or(T,E);

       Double c_T      = sum(T);
       Double c_E      = sum(E);
       Double cN_TE    = sum(N_TE);
       Double cN_TcE  = sum(N_TcE);
       Double cN_TEc  = sum(N_TEc);
       Double cU_TE    = sum(U_TE);
       
       si     =  div(cN_TE,cU_TE);
       fp     = div(cN_TcE,cU_TE);
       fn     = div(cN_TEc,cU_TE);
       in_TE   = cN_TE;
       tet     = div(cN_TE,c_T);

       if ( c_E.equals( 0.0d ) ) {
         tee = 1.0d;
       }
       else {
         tee = div(cN_TE,c_E);	
       }
       
       _TEE=tee;
       _TET=tet;
       
	        Double r = new Double(tee);
	        
	        chkresult("pixel_measure_tee",r);
	        
	        return r;		
	}

		/*
		 *  name:			Total Error Rate Test Measure (TET)
		 *  @see 			TBD.
		 *
		 *	description:	Takes 2 representations of image pixel data as input and generates a single numerical output.
		 *					Is called by appropriate related class in gov.nist.itl.versus.similarity.comparisons.measure.impl.* .
		 *  
		 *  @author 		B. Long
		 *  version:		1.0
		 */	    
		 			  
	public Double pixel_measure_tet_nD(  Double[] im1, Double[] im2 ) throws Exception
	{
 	 chkargs("pixel_measure_tet",im1,im2);

      Double si     = 0.0d;
      Double fp     = 0.0d;
      Double fn     = 0.0d;
      Double in_TE   = 0.0d;
      Double tet     = 0.0d;
      Double tee     = 0.0d;

		  Double[] T       = null;
		  Double[] E       = null;
        Double[] Tc      = null;
        Double[] Ec      = null;
        Double[] N_TE    = null;
        Double[] N_TcE   = null;
        Double[] N_TEc   = null;
        Double[] U_TE    = null;

     	T= logical(im1);
     	E= logical(im2);

			Tc    = not(T);	
			Ec    = not(E);
			N_TE    = and(T,E);
			N_TcE   = and(Tc,E);
			N_TEc   = and(T,Ec);
			U_TE    = or(T,E);

       Double c_T      = sum(T);
       Double c_E      = sum(E);
       Double cN_TE    = sum(N_TE);
       Double cN_TcE  = sum(N_TcE);
       Double cN_TEc  = sum(N_TEc);
       Double cU_TE    = sum(U_TE);
       si     =  div(cN_TE,cU_TE);
       fp     = div(cN_TcE,cU_TE);
       fn     = div(cN_TEc,cU_TE);
       in_TE   = cN_TE;
       tet     = div(cN_TE,c_T);
       if ( c_E.equals( 0.0d ) ) {
         tee = 1.0d;
       }
       else {
         tee = div(cN_TE,c_E);	
       }
       
       _TEE=tee;
       _TET=tet;
       
	        Double r = new Double(tet);
	        
	        chkresult("pixel_measure_tet",r);
	        
	        return r;		
	}  			  
	
	//////////////////////////////////////////////////////////////////////////////////
	// PRIMITIVE OPERATIONS
	//////////////////////////////////////////////////////////////////////////////////
	
	
	public Double sum(double[] a) throws Exception {
    	chkargs("sum",a);
		int len = a.length;
		Double b = 0d;
		for (int i=0; i < len; i++) b = add(a[i], b);
    	chkresult("sum",b);
		return b;
	}		
	
	
//////////////////////////////////////////////////////////////////////////////////
//SUPPORT METHODS
//////////////////////////////////////////////////////////////////////////////////
	    

/////////////////  
//BEG
/////////////////

public boolean chkargs( String methodName, double[] a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  
	  int len = a.length;
	  for (int i=0; i < len; i++) {
		  chkargs(methodName, a[i]);
	  }	  
	  return true;	// if we get here, we've had no exceptions.
}


public boolean chkargs( String methodName, double[][] a ) throws Exception {
	  if ( 	a == null ) throw new SingularityTreatmentException(methodName  + ": first argument null value");
	  
	  int len = a.length;
	  for (int i=0; i < len; i++) {
		  chkargs(methodName, a[i]);
	  }	  
	  return true;	// if we get here, we've had no exceptions.
}  


public boolean chkresult( String methodName, double a ) throws Exception {
	return chkargs(methodName,a);  
}    

public boolean chkresult( String methodName, double[] a ) throws Exception {
	return chkargs(methodName,a);  
}

  

//END


/////////////////////////////////
//ImageOps auxilliary methods
/////////////////////////////////

	public Double and( Double a, Double b ) throws Exception {
		chkargs("and",a,b);
		Double c = 0.0d;
		if ( a.equals(0.0d) && b.equals(0.0d) ) c = 0.0d;
		else if ( a.equals(0.0d) && !b.equals(0.0d) ) c = 0.0d;
		else if ( !a.equals(0.0d) && b.equals(0.0d) ) c = 0.0d;
		else if ( a >= 0.0d && b >= 0.0d ) c = 1.0d;
		chkresult("and",c);
		return c;
	}

	public Double[] and( Double[] A, Double[] B ) throws Exception {
		chkargs("and",A,B);
		int len = A.length;
		Double[] c = new Double[len];
		for (int i=0; i < len; i++) {
			c[i] = and(A[i],B[i]);	
		}
		chkresult("and",c);
		return c;
	}

	public Double[][] and( Double[][] A, Double[][] B ) throws Exception {
		chkargs("and",A,B);
		int len1 = A.length;
		int len2 = A[0].length;
		Double[][] C = new Double[len1][len2];
		for (int i=0; i < len1; i++) {
			C[i] = and(A[i],B[i]);	
		}
		chkresult("and",C);
		return C;
	}
	
	public Double[][][] and( Double[][][] A, Double[][][] B ) throws Exception {
		chkargs("and",A,B);
		int len1 = A.length;
		int len2 = A[0].length;
		int len3 = A[0][0].length;
		Double[][][] C = new Double[len1][len2][len3];
		for (int i=0; i < len1; i++) {
			C[i] = and(A[i],B[i]);	
		}
		chkresult("and",C);
		return C;
	}
	

	public Double or( Double a, Double b ) throws Exception {
		chkargs("or",a,b);
		Double c = 0.0d;
		if ( a.equals(0.0d) && b.equals(0.0d) ) c = 0.0d;
		else if ( a.equals(0.0d) && !b.equals(0.0d) ) c = 1.0d;
		else if ( !a.equals(0.0d) && b.equals(0.0d) ) c = 1.0d;
		else if ( a >= 0.0d && b >= 0.0d ) c = 1.0d;
		chkresult("or",c);
		return c;
	}

	public Double[] or( Double[] A, Double[] B ) throws Exception {
		chkargs("or",A,B);
		int len = A.length;
		Double[] C = new Double[len];
		for (int i=0; i < len; i++) {
			C[i] = or(A[i],B[i]);
		}
		chkresult("or",C);
		return C;
	}

	public Double[][] or( Double[][] A, Double[][] B ) throws Exception {
		chkargs("or",A,B);
		int len1 = A.length;
		int len2 = A[0].length;
		Double[][] C = new Double[len1][len2];
		for (int i=0; i < len1; i++) {
			C[i] = or(A[i],B[i]);
		}
		chkresult("or",C);
		return C;
	}

	public Double[][][] or( Double[][][] A, Double[][][] B )  throws Exception {
		chkargs("or",A,B);
		int len1 = A.length;
		int len2 = A[0].length;
		int len3 = A[0][0].length;
		Double[][][] C = new Double[len1][len2][len3];
		for (int i=0; i < len1; i++) {
			C[i] = or(A[i],B[i]);
		}
		chkresult("or",C);
		return C;
	}	

	public Double not( Double a ) throws Exception {
		chkargs("not",a);
		Double c = 0.0d;
		if ( a > 0.0d ) 
			c = 0.0d;
		else if ( c.equals(0.0d) )
			c = 1.0d;
		chkresult("not",c);
		return c;
	}

	public Double[] not( Double[] A ) throws Exception {
		chkargs("not",A);
		int len = A.length;
		Double[] B = new Double[len];
		for (int i=0; i < len; i++) {
			B[i] = not(A[i]);
		}
		chkresult("not",B);
		return B;
	}

	public Double[][] not( Double[][] A ) throws Exception {
		chkargs("not",A);
		int len1 = A.length;
		int len2 = A[0].length;
		Double[][] B = new Double[len1][len2];
		for (int i=0; i < len1; i++) {
			B[i] = not(A[i]);
		}
		chkresult("not",B);
		return B;
	}

	public Double[][][] not( Double[][][] A ) throws Exception {
		chkargs("not",A);
		int len1 = A.length;
		int len2 = A[0].length;
		int len3 = A[0][0].length;
		Double[][][] B = new Double[len1][len2][len3];
		for (int i=0; i < len1; i++) {
			B[i] = not(A[i]);
		}
		chkresult("not",B);
		return B;
	}	

	public Double logical( Double a ) throws Exception {
		chkargs("logical",a);
		Double c = 0.0d;
		if ( a > 0 ) 
			c = 1.0d;
		else if ( a.equals( 0.0d ) )
			c = 0.0d;
		chkresult("logical",c);
		return c;
	}

	public Double[] logical( Double[] A  ) throws Exception {
		chkargs("logical",A);
		int len = A.length;
		Double[] C = new Double[len];
		for (int i=0; i < len; i++) {
			C[i] = logical(A[i]);
		}
		chkresult("logical",C);
		return C;
	}

	public Double[][] logical( Double[][] A) throws Exception {
		chkargs("logical",A);
		int len1 = A.length;
		int len2 = A[0].length;
		Double[][] C = new Double[len1][len2];
		for (int i=0; i < len1; i++) {
			C[i] = logical(A[i]);
		}
		chkresult("logical",C);
		return C;
	}

	public Double[][][] logical( Double[][][] A ) throws Exception {
		chkargs("logical",A);
		int len1 = A.length;
		int len2 = A[0].length;
		int len3 = A[0][0].length;
		Double[][][] C = new Double[len1][len2][len3];
		for (int i=0; i < len1; i++) {
			C[i] = logical(A[i]);
		}
		chkresult("logical",C);
		return C;
	}	

	public Double[] vectorize( Double[][] A ) throws Exception {
		chkargs("vectorize",A);
		int len1 = A.length;
		int len2 = A[0].length;
		int len3 = len1*len2;
		Double[] C = new Double[len1*len2];
		for (int i=0; i < len1; i++) {
		  for (int j=0; j < len2; j++) {
			C[(i*len2)+j] = A[i][j];
		  }
		}
		chkresult("vectorize",C);
		return C;
	}
	
	public double[] vectorize( double[][] A ) throws Exception {
		chkargs("vectorize",A);
		int len1 = A.length;
		int len2 = A[0].length;
		int len3 = len1*len2;
		double[] C = new double[len1*len2];
		for (int i=0; i < len1; i++) {
		  for (int j=0; j < len2; j++) {
			C[(i*len2)+j] = A[i][j];
		  }
		}
		chkresult("vectorize",C);
		return C;
	}	
	
	public Double[] vectorize( Double[][][] A ) throws Exception {
		chkargs("vectorize",A);
		int len1 = A.length;
		int len2 = A[0].length;
		int len3 = A[0][0].length;
		int len4 = len1*len2*len3;
		Double[] C = new Double[len1*len2*len3];
		for (int i=0; i < len1; i++) {
		  for (int j=0; j < len2; j++) {
			  for (int k=0; k < len3; k++) {
				  C[(i*len2)+(j*len3)+k] = A[i][j][k];
			  }
		  }
		}
		chkresult("vectorize",C);
		return C;
	}	
	
	public Double sum( Double[][] A ) throws Exception {
		chkargs("sum",A);
		int len1 = A.length;
		int len2 = A[0].length;
		Double result = 0.0d;
		for (int i=0; i < len1; i++ ) {
			for (int j=0; i < len2; j++) {
				result += A[i][j];
			}
		}
		chkresult("sum",result);
		return result;
	}
	
	public Double sum( Double[][][] A ) throws Exception {
		chkargs("sum",A);
		int len1 = A.length;
		int len2 = A[0].length;
		int len3 = A[0][0].length;
		Double result = 0.0d;
		for (int i=0; i < len1; i++ ) {
			for (int j=0; i < len2; j++) {
				for (int k=0; k < len3; k++) {
					result += A[i][j][k];
				}
			}
		}
		chkresult("sum",result);
		return result;
	}
	
	public Double[] mask( Double[] img, Double[] mask ) throws Exception {
		chkargs("mask",img,mask);
		int len = img.length;
		ArrayList B = new ArrayList();	
		for (int i=0; i < len; i++) {
			if ( mask[i].equals( 1.0d ) ) {
				B.add( img[i] );
			}	
		}

		int len2 = B.size();
		Double[] C = new Double[len2];
		for (int i=0; i < len2; i++) {
			C[i] = (Double)B.get(i);
		}
		chkresult("mask",C);
		return C;
	}

	/*
       * Given an input list of items, sort it and remove duplicates.
       */

	public Double[] uniqueSort( Double[] list ) throws Exception {
		chkargs("uniqueSort",list);
		int len = list.length;
		ArrayList A = new ArrayList();
		ArrayList uniqueList = new ArrayList();

		for (int i=0; i < len; i++) {
			A.add( list[i] );
		}
		Collections.sort( A );
		for ( Object o: A ) {
			if ( !uniqueList.contains(o) ) {
				uniqueList.add( o );
			}
		}
		int len2 = uniqueList.size();
		Double[] B = new Double[len2];
		for (int i=0; i < len2; i++) {
			B[i] = (Double) uniqueList.get(i);
		}
		chkresult("uniqueSort",B);
		return B;
	}

	/* Given an unordered list of non-zero pixel values in an image,
	 * provide a sorted unique lilst of pixel-values occurring there.
	 *
	 * example:
	 *  im_nonZeroEntries = mask(origImg, imgMask);
	 *  im_unsortedPixelValueList = uniquePixelValues(img);
	 */

	public Double[] uniquePixelValues( Double[] img ) throws Exception {
		chkargs("uniquePixelValues",img);
		int totalPixelValues = 256;
		int pixelValues[] = new int[ totalPixelValues ];
		ArrayList list= new ArrayList();

		// count pixel values	
		int len = img.length;	
		for (int i=0; i < len; i++) {
			pixelValues[ ((Double)img[i]).intValue() ]++;

		}

		// add unique/non-zero pixel values to the list
		len = totalPixelValues;
		for (int i=0; i < len; i++) {
			if ( pixelValues[i] != 0.0d ) {
				list.add( new Double(i) );
			}
		}

		int len2 = list.size();

		Double[] B = new Double[len2];
		for (int i=0; i < len2; i++) {
			B[i] = (Double)list.get(i);
		}
		chkresult("uniquePixelValues",B);
		return B;
	}

	/* Get a list of values (often a vectorized sequance of non-zero entries from an image matrix)
	 * return a binary list and 1-entries where the given value is found in the list and
	 * 0-entries are found everywhere else.
       */

	public Double[] getMatches( Double[] originalContent, Double value ) throws Exception {
		chkargs("getMatches",originalContent);
		chkargs("getMatches",value);
		Double k   = 0.0;
		int len = originalContent.length;
		Double[] B = new Double[len];

		for (int i=0; i < len; i++) {
			k = originalContent[i];	// BJL FIX - missing stmt caused getMatches to not show matches as expected.
			if ( k.equals( value ) )			
			//if ( k == value  )
				B[i] = 1.0d;			// NOTE: If match, then 1, else 0.
			else
				B[i] = 0.0d;
		}

		chkresult("getMatches",B);
		return B;
	}

	/* 
       * Returns the content of a given matrix's column
       *
       */

	public Double[] getColumn(Double[][] M, Integer rows, Integer col ) throws Exception {
		chkargs("getColumn",M);
		chkargs("getColumn",rows);
		chkargs("getColumn",col);
		int len = rows;
		Double[] C = new Double[len];

		for (int i=0; i < len; i++ ) {
			C[i] = new Double(M[i][col]);
		}
		chkresult("getColumn",C);
		return C;
	}

	/* 
       * Returns content of a given matrix's row.
	 */

	public Double[] getRow(Double M [][], Integer cols, Integer row ) throws Exception {
		chkargs("getRow",M);
		chkargs("getRow",cols);
		chkargs("getRow",row);
		int len = cols;
		Double[] C = new Double[len];
		
		for (int i=0; i < len; i++ ) {
			C[i] = new Double(M[row][i]);
		}
		chkresult("getRow",C);
		return C;
	}

	/*
	 *  Computes n-choose-2.
	 */

	public Double nChoose2( Double n ) throws Exception {
		chkargs("nChoose2",n);
		Double result = ((n*n) - n)/2;
		chkresult("nChoose2",result);
		return result;
	}

	public Double nChoose2( int n ) throws Exception {
		chkargs("nChoose2",n);
		Double result = (double)((n*n) - n)/2;
		chkresult("nChoose2",result);
		return result;
	}
	
	/* 
   * Create a 2D zero matrix.
	 */

	public Double[][] makeZeroMatrix2D( Integer rows, Integer cols, Double value )  throws Exception {
		chkargs("makeZeroMatrix2D",rows);
		chkargs("makeZeroMatrix2D",cols);
		chkargs("makeZeroMatrix2D",value);
		Double m[][] = new Double[rows][cols];
		for (int i=0; i < rows; i++) {
			for (int j=0; j < cols; j++) {
				m[i][j] = value;
			}
		}
		chkresult("makeZeroMatrix2D",m);
		return m;
	}

}
