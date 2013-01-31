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
 *
 *  @author  Benjamin Long, blong@nist.gov
 *  @version 1.0
 *    Date: Thu Dec  13 10:31:41 EST 2012
 */

package gov.nist.itl.versus.similarity3d.comparisons.adapter.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasVoxels;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasHistogram;

import volume.VolumeFloat;
import edu.illinois.ncsa.versus.VersusException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.FileLoader;
import edu.illinois.ncsa.versus.adapter.StreamLoader;
import edu.illinois.ncsa.versus.utility.HasCategory;
import edu.ncsa.model.Mesh;
import edu.ncsa.model.MeshAuxiliary.Color;
import edu.ncsa.model.MeshAuxiliary.Point;
import edu.ncsa.model.loaders.MeshLoader_OBJ;

public class OBJImageObjectAdapter 
	implements 
		Adapter
		,HasVoxels
		,HasHistogram
		,HasCategory
		,FileLoader
		,StreamLoader
{	
	
	private MeshLoader_OBJ loader;
	private Mesh mesh;
	private int w, h, d;
	private Double[] histogram;

	private VolumeFloat v;
	
	public OBJImageObjectAdapter()
	{
		loader = new MeshLoader_OBJ();
	}
	
	// load obj file
	public void load( File file ) 
	{
		mesh 	= loader.load( file.getAbsolutePath() );
		if ( mesh == null ) System.out.println("mesh==null");
		init();
	}	
	
    @Override
    public void load(InputStream stream) throws IOException, VersusException {
        File file = File.createTempFile("OBJImageObjectAdapterInput", ".tmp");
        FileOutputStream fos = new FileOutputStream(file);
        try {
            IOUtils.copy(stream, fos);
        } finally {
            fos.close();
            stream.close();
        }
        load(file);
        try {
            file.delete();
        } catch (Exception e) {
            Logger.getLogger(OBJImageObjectAdapter.class.getName()).log(Level.WARNING, "Cannot delete temp file " + file, e);
        }
    }
		
	public void init() 
	{
		//System.out.println("DEBUG: beg init");
		double[][][] voxels 	= meshToVoxels(mesh);
		d = voxels.length;
		w = voxels[0].length;
		h = voxels[0][0].length;
		v = new VolumeFloat( d2f(voxels) );	
		histogram = createHistogram(voxels);
		//System.out.println("DEBUG: end init");
	}
	
	public Double[] createHistogram( double[][][] voxels ) 
	{	
		int max = max(voxels);
		int val = 0;
		Double[] histogram = new Double[max+1];	// add 1 so can accommodate actual max value directly
		for(int z=0; z < d; z++) {
			for (int x=0; x < w; x++) {
				for (int y=0; y < h; y++) {
					val = (int)voxels[z][x][y];
					if ( val>=0 && val<=max) { // include max value here.
						histogram[ val ]++;
					}
				}
			}
		}
		return histogram;
	}
	
	public int max( double[][][] voxels ) {
		double max = 0;
		double val = 0;
		for(int z=0; z < d; z++) {
			for (int x=0; x < w; x++) {
				for (int y=0; y < h; y++) {
					val = voxels[z][x][y];
					if ( max < val ) max = val;
				}
			}
		}
		return (int) max;
	}	
	
	public Double[] getHistogram(){ return histogram; }
	public int getLength() { return histogram.length; }
	
	public int getWidth() { return w; }
	public int getHeight(){ return h; }
	public int getDepth() { return d; }
	
	public Mesh getMesh(){ return mesh; }
	
	public double getValue(int x, int y, int z ) {
		if ( v==null )
			System.out.println("v==null");
		Float f = null;
		try {
		f = ((Float)v.get(x,y,z));
		}
		catch(Exception e){ System.out.println("DEBUG: problem in getValue = [" + e + "]" ); }
		if ( f == null ) 
			return 0.0d;
		else
			return f.doubleValue();
	}
	
	public double getRGBPixel(int row, int col, int slice) {
		return getValue(row,col,slice);
	}
	
	public double[][][] getRGBPixels() {
		return getValues();
	}
	
	public double[][][] getValues() 
	{
		int w = getWidth();
		int h = getHeight();
		int d = getDepth();
		double[][][] voxels = new double[d][w][h];
		for (int z=0; z < d; z++) {
			for (int y=0; y < h; y++) {
				for (int x=0; x < w; x++) {
					voxels[z][x][y] = getValue(x,y,z);
				}
			}
		}
		return voxels;
	}
	
///////////////////////////////////////////////////
	
	public Double[] short2Double( short[] d ) {
		int len = d.length;
		Double[] a = new Double[len];
		for (int i=0; i < len; i++ ) a[i] = new Double(d[i]);
		return a;
	}
	
	public Double[] int2Double( int[] d ) {
		int len = d.length;
		Double[] a = new Double[len];
		for (int i=0; i < len; i++ ) a[i] = new Double(d[i]);
		return a;		
	}
	
	public double[][][] to3dPixelArray() {
		float[][][] _3dpixels = v.getVolume();
		int _d=0, _w=0, _h=0;
		_d = _3dpixels.length;
		_w = _3dpixels[0].length;
		_h = _3dpixels[0][0].length;
		double[][][] data = new double[_d][_w][_h];
		return data;
	}
	
	public String getName() {
		return "OBJ Image Object Adapter";
	}
	
	public List<String> getSupportedMediaTypes() {
		List<String> mediaTypes = new ArrayList<String>();
		mediaTypes.add("image/*");
		mediaTypes.add("text/plain");
		return mediaTypes;
	}		
	
	
	//utility methods
	public float[][][] d2f( double[][][] dVoxels ) {
		int d = dVoxels.length;				// z
		int w = dVoxels[0].length;			// x
		int h = dVoxels[0][0].length;		// y
		float[][][] f = new float[d][w][h];
		for (int z=0; z < d; z++) {
			for (int x=0; x < w; x++) {
				for (int y=0; y < h; y++) {
					f[z][x][y] = (float) dVoxels[z][x][y];
				}
			}
		}
		return f;
	}
	
	public double[][][] meshToVoxels( Mesh mesh ) {	// for .obj
		System.out.println("DEBUG: beg meshToVoxels");
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
				//voxels[z][x][y] = (pixel % 256);
		}     
		System.out.println("DEBUG: end meshToVoxels");
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
	
	@Override
	public String getCategory() {
		return "3D";
	}

}
