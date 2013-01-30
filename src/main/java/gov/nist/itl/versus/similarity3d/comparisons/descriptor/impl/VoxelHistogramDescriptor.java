/**
 * 
 */
package gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;

import java.util.Vector;

import gov.nist.itl.versus.similarity3d.comparisons.adapter.impl.DicomImageObjectAdapter;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasHistogram;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.utility.HasCategory;
import edu.ncsa.model.Mesh;
import edu.ncsa.model.MeshAuxiliary.Color;
import edu.ncsa.model.MeshAuxiliary.Point;
import  edu.illinois.ncsa.versus.adapter.HasMesh;


/**
 * A feature presented to users as a 1-dimensional array of doubles
 * extracted from a set of input voxels.
 *
 * @author Benjamin Long
 * 
 */
public class VoxelHistogramDescriptor 
	implements 
		Descriptor
	  , HasCategory
{
	public final String type = this.getClass().toString();

	private double[][][] values;
	private Double[] histogram;
	private double min_voxel_value = 0.0d;
	private double max_voxel_value;

	public VoxelHistogramDescriptor() {
	}

	public VoxelHistogramDescriptor(double[][][] values) {
		this.values = values;
	}
	
	public VoxelHistogramDescriptor(double[][][] values, HasHistogram a) {
		this.values = values;
		this.histogram =  a.getHistogram() ;
	}	
	

	public VoxelHistogramDescriptor(HasMesh hm) {
		this.values = voxelsToArray(hm.getMesh());
	}
	
	public VoxelHistogramDescriptor(DicomImageObjectAdapter a) {
		this.values = voxelsToArray( a.image() );
	}
	
	public Double[] getHistogram(){ return histogram; }	// returns "raw" (unnormalized) histogram

	@Override
	public String getType() {
		return type;
	}

	public Double[] getValues() {
		return voxelsToArray();
	}
	
	public int getLength(){ return getValues().length; }
	public double getValue(int i) {
		if ( i >=0 && i < getLength() ) {
			Double[] vals = getValues();
			return vals[i];
		}
		return -1.0d;
	}
			
	public double getValue(int x, int y, int z) {
		return values[z][x][y];
	}

	public int getDepth() {
		return values.length;
	}

	public int getHeight() {
		return values[0][0].length;
	}

	public int getWidth() {
		return values[0].length;
	}

	@Override
	public String getName() {
		return "Voxel Histogram to Array";
	}
	
	///////////////////////////////
	// Utility methods
	
	public Double[] voxelsToArray() {
		int w = getWidth();
		int h = getHeight();
		int d = getDepth();
		Double[] pixelArray = new Double[d*w*h];
		int i = 0;
		for (int z=0; z < d; z++) {
			for (int x=0; x < w; x++) {
				for (int y=0; y < h; y++, i++) {
					pixelArray[i] = new Double(values[z][x][y]);
				}
			}
		}
		return pixelArray;
	}
	
	/*
	 * Walk pixels of image and save them in 1D pixel-array of Double values.
	 */
	public Double[] vectorizeVoxels( ImagePlus im ) 
	{    
		ImageProcessor ip;
        int w 			= im.getWidth();
        int h 			= im.getHeight();
        int slices		= im.getStackSize();        
        ImageStack stk 	= im.getStack();
        
        double pixel 	= 0;
        Double[] pixels = new Double[slices * w * h];
        int pIdx 		= 0;		// pixel-index into pixels array. 
        int x,y,z;					// coordinate-indices
        
        for (z=1; z<=slices; z++) 
        {
            ip = stk.getProcessor(z);
            for ( x=0; x < w; x++ ) 
            {
                for ( y=0; y < h; y++, pIdx++ ) 
                {
                    pixel = ip.getPixel(x, y);
                    pixels[pIdx] = pixel ;
                }
            }
        }
        return pixels;
	}
	
	public double[][][] voxelsToArray( ImagePlus im ) 
	{
		ImageProcessor ip;
        int w 			= im.getWidth();
        int h 			= im.getHeight();
        int slices		= im.getStackSize();        
        ImageStack stk 	= im.getStack();
        
        double pixel 	= 0;
        double[][][] pixels = new double[slices][w][h];
        int pIdx 		= 0;		// pixel-index into pixels array. 
        int x,y,z;					// coordinate-indices
        
        for (z=1; z<=slices; z++) 
        {
            ip = stk.getProcessor(z);
            for ( x=0; x < w; x++ ) 
            {
                for ( y=0; y < h; y++ ) 
                {
                    pixel = ip.getPixel(x, y);
                    pixels[(z-1)][w][h] = pixel ;
                }
            }
        }
        return pixels;
	}

	
   /*
	* Utility method
	*	- converts a given Mesh object to Double array.
	*/
	public Double[] vectorizeVoxels( Mesh mesh ) {	// for .obj
		Vector<Point> points = mesh.getVertices();
		int len = points.size();
		double pixel 	= 0;
		Double[] pixels = new Double[len];
		int pIdx 		= 0;		// pixel-index into pixels array.         
		
		for (int i=0; i < len; i++) 
		{
				pixel = points.get(i).magnitude();
				pixels[pIdx] = pixel ;
				pIdx++;
		}     
		return pixels;
	}
	
	public double[][][] voxelsToArray( Mesh mesh ) 
	{
		Vector<Point> points = mesh.getVertices();
		Vector<Color> values = mesh.getVertexColors();
		int len = points.size();
		double voxel 	= 0;
		int w = maxX(points);
		int h = maxY(points);
		int d = maxZ(points);
		double[][][] voxels = new double[d+1][w+1][h+1];		// add 1 to allow for the actual max values.
		//double[][][] voxels = new double[len][len][len];
		int pIdx 		= 0;		// pixel-index into pixels array.         
		int x, y, z;
		System.out.println("voxels = (d,w,h)=(" + d + "," + w + "," + h + ")" );
		System.out.println("total-vertices=" + points.size() );
		
		for (int i=0; i < len; i++, pIdx++) 
		{		
				voxel = values.get(i).getInt();
				z = (int)points.get(i).z;
				x = (int)points.get(i).x;
				y = (int)points.get(i).y;
				System.out.println("voxels = (z,x,y,voxel-value)=(" + z + "," + x + "," + y + "," + voxel + ")" );
				voxels[z][x][y] = voxel ;
		}     
		return voxels;
	}
	
	public int maxX(Vector<Point> points) {
		int max = 0;
		int val = 0;
		int len = points.size();
		for (int i=0; i < len; i++) {
			val = (int)points.get(i).x;
			if ( val > max ) max = val;
		}
		return max;
	}
	public int maxY(Vector<Point> points) {
		int max = 0;
		int val = 0;
		int len = points.size();
		for (int i=0; i < len; i++) {
			val = (int)points.get(i).y;
			if ( val > max ) max = val;
		}
		return max;
	}
	public int maxZ(Vector<Point> points) {
		int max = 0;
		int val = 0;
		int len = points.size();
		for (int i=0; i < len; i++) {
			val = (int)points.get(i).z;
			if ( val > max ) max = val;
		}
		return max;
	}
	
	/*
	 * 3D-to-1D voxels to 1D-array transformation.
	 */
	public Double[] vectorizeVoxels( double[][][] voxels ) 
	{    
        int slices 		= voxels.length;
        int h 			= voxels[0].length;
        int w			= voxels[0][0].length;        

        Double[] pixels = new Double[slices * w * h];
        int pIdx 		= 0;		// pixel-index into pixels array. 
        int x,y,z;					// coordinate-indices
        
        for (z=0; z < slices; z++) 
        {
            for ( x=0; x < w; x++ ) 
            {
                for ( y=0; y < h; y++, pIdx++ ) 
                {           
                    pixels[pIdx] = voxels[z][y][x] ;
                }
            }
        }
        return pixels;
	}	
	
	@Override
	public String getCategory() {
		return "3D";
	}	
		
}
