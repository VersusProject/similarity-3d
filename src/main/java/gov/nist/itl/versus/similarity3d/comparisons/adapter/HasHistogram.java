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
 */

package gov.nist.itl.versus.similarity3d.comparisons.adapter;

import edu.illinois.ncsa.versus.adapter.Adapter;

public interface HasHistogram  extends Adapter {
	public Double[] getHistogram();
	public int getLength();
}
