package gov.nist.itl.versus.similarity3d.comparisons.extract.impl;

import java.util.HashSet;
import java.util.Set;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;

import edu.illinois.ncsa.versus.adapter.impl.ImageObjectAdapter;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;

import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasVoxels;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasHistogram;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelHistogramDescriptor;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelToArrayFeature;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.impl.DicomImageObjectAdapter;

public class VoxelHistogramExtractor 
	implements 
		Extractor
		
{
	public VoxelHistogramExtractor(){ }
	
	public VoxelHistogramDescriptor extract(HasVoxels adapter) {
		VoxelHistogramDescriptor v = null;
		if ( (adapter instanceof HasVoxels)==true && (adapter instanceof HasHistogram)==true ) {
			v = new VoxelHistogramDescriptor(adapter.getRGBPixels(), (HasHistogram)adapter);
		}

		return v; 
	}

	@Override
	public DicomImageObjectAdapter newAdapter() {
		return new DicomImageObjectAdapter();
	}

	@Override
	public String getName() {
		return "Voxels Histogram";
	}

	@Override
	public Descriptor extract(Adapter adapter) throws UnsupportedTypeException {
		if (adapter instanceof HasVoxels) {
			HasVoxels vx = (HasVoxels) adapter;
			Descriptor d = extract(vx);
			return d;
		} else {
			throw new UnsupportedTypeException();
		}
	}

	@Override
	public Set<Class<? extends Adapter>> supportedAdapters() {
		Set<Class<? extends Adapter>> adapters = new HashSet<Class<? extends Adapter>>();
		adapters.add(HasVoxels.class);
		adapters.add(HasHistogram.class);
		return adapters;
	}

	@Override
	public Class<? extends Descriptor> getFeatureType() {
		return VoxelHistogramDescriptor.class;
	}

	@Override
	public boolean hasPreview() {
		return true;
	}

	@Override
	public String previewName() {
		return "Voxel Histogram";
	}	
}
