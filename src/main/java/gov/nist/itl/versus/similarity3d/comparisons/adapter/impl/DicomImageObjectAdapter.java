
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

import ij.IJ;

import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.FolderOpener;
import ij.process.ImageProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import volume.VolumeFloat;
import edu.illinois.ncsa.versus.utility.HasCategory;
import edu.illinois.ncsa.versus.adapter.Adapter;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasVoxels;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasHistogram;


public class DicomImageObjectAdapter 
	implements 
		Adapter
		,HasVoxels
		,HasHistogram
		,HasCategory
{	
	private ImagePlus image;
	private short[] pixels;
	private int w, h, d;
	private Double[] histogram;
	private VolumeFloat v;
	
	public DicomImageObjectAdapter()
	{
		image = new ImagePlus();
	}
	
	// load dicom file
	public void load( File file ) 
	{
		IJ.open( file.getAbsolutePath() );
		image = IJ.getImage();
		init();
	}
	
	// load dicom dir (image-stack)
	public void loadDir(File dir ) 
	{
		image = FolderOpener.open( dir.getAbsolutePath() );		
		init();
	}
	
	public void init()
	{
		ImageProcessor p = imageProcessor();
		ImageStack stk   = imageStack();
		pixels = (short[]) p.getPixels();
		w = image.getWidth();
		h = image.getHeight();
		d = image.getStackSize();
		histogram =int2Double( p.getHistogram() );
		if ( d > 1 ) 
			v = new VolumeFloat(stk);
		else
			v = new VolumeFloat(p);
	}
	
	public Double[] getHistogram(){ return histogram; }
	public short[] getPixels() { return pixels; }
	public ImagePlus image(){ return image; }
	public ImageProcessor imageProcessor(){ return image.getProcessor(); }
	public ImageStack imageStack(){ return image.getImageStack(); }
	
///////////////////////////////////////////////////	
	public int getWidth() { return w; }
	public int getHeight(){ return h; }
	public int getDepth() { return d; }
	
///////////////////////////////////////////////////	
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
		double[][][] pixels = new double[d][w][h];
		for (int z=0; z < d; z++) {
			for (int y=0; y < h; y++) {
				for (int x=0; x < w; x++) {
					pixels[z][x][y] = getValue(x,y,z);
				}
			}
		}
		return pixels;
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
	
	public Double[] toVoxelArray() {
		return short2Double( getPixels() );
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
		return "Dicom Image Object Adapter";
	}

	public List<String> getSupportedMediaTypes() {
		List<String> mediaTypes = new ArrayList<String>();
		mediaTypes.add("image/*");
		mediaTypes.add("application/dicom");
		return mediaTypes;
	}

	@Override
	public String getCategory() {
		return "3D";
	}
	
}
