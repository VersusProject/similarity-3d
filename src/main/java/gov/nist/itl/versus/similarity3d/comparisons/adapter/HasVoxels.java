package gov.nist.itl.versus.similarity3d.comparisons.adapter;

import edu.illinois.ncsa.versus.adapter.Adapter;

public interface HasVoxels extends Adapter
{
	public int getWidth();
	public int getHeight();
	public int getDepth();
	public double[][][] getValues();
	public double getValue(int x, int y, int z );
	public double getRGBPixel(int row, int col, int slice);	
	public double[][][] getRGBPixels();
}
