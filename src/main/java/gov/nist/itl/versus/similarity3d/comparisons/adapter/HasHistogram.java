package gov.nist.itl.versus.similarity3d.comparisons.adapter;

import edu.illinois.ncsa.versus.adapter.Adapter;

public interface HasHistogram  extends Adapter {
	public Double[] getHistogram();
}
