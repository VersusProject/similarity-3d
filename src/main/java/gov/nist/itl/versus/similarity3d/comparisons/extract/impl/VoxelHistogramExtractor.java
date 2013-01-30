package gov.nist.itl.versus.similarity3d.comparisons.extract.impl;

import java.util.HashSet;
import java.util.Set;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;

import edu.illinois.ncsa.versus.adapter.impl.ImageObjectAdapter;

import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.utility.HasCategory;

import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasVoxels;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasHistogram;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelHistogramDescriptor;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelToArrayFeature;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.impl.DicomImageObjectAdapter;

import  edu.illinois.ncsa.versus.adapter.HasMesh;

public class VoxelHistogramExtractor 
	implements 
		Extractor
		, HasCategory
		
{
	public VoxelHistogramExtractor(){ }
	
	@Override
	public Descriptor extract(Adapter adapter) throws UnsupportedTypeException {
		if (adapter instanceof HasHistogram) {
			HasHistogram hg = (HasHistogram)adapter;
			Descriptor d = extract(hg);
			return d;
		}		
		else if (adapter instanceof HasVoxels) {
			HasMesh hm = (HasMesh) adapter;
			Descriptor d = extract(hm);
			return d;
		}		
		else {
			throw new UnsupportedTypeException();
		}
	}	
	
	public VoxelHistogramDescriptor extract(HasHistogram adapter) {
		VoxelHistogramDescriptor v = null;
		if ( (adapter instanceof HasVoxels)==true && (adapter instanceof HasHistogram)==true ) {
			v = new VoxelHistogramDescriptor( ((HasVoxels)adapter).getRGBPixels(), adapter);
		}

		return v; 
	}
	
	public VoxelHistogramDescriptor extract(HasMesh adapter) {
		VoxelHistogramDescriptor v = null;
			v = new VoxelHistogramDescriptor();
			v.voxelsToArray(adapter.getMesh());
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
	public Set<Class<? extends Adapter>> supportedAdapters() {
		Set<Class<? extends Adapter>> adapters = new HashSet<Class<? extends Adapter>>();
		adapters.add(HasHistogram.class);
		adapters.add(HasMesh.class);
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
	
	@Override
	public String getCategory() {
		return "3D";
	}
}
