package gov.nist.itl.versus.similarity3d.comparisons.extract.impl;

import java.util.HashSet;
import java.util.Set;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.HasMesh;
import edu.illinois.ncsa.versus.adapter.HasPixels;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;
import edu.illinois.ncsa.versus.utility.HasCategory;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasHistogram;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.HasVoxels;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.impl.DicomImageObjectAdapter;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelHistogramDescriptor;
import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelToArrayFeature;

public class VoxelToArrayFeatureExtractor 
	implements 
		Extractor
	  , HasCategory
{
	
	@Override
	public Descriptor extract(Adapter adapter) throws UnsupportedTypeException {
		
		if (adapter instanceof HasVoxels) {
			HasVoxels vx = (HasVoxels) adapter;
			Descriptor d = extract(vx);
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
	
	public VoxelToArrayFeature extract(HasVoxels adapter) {
		VoxelToArrayFeature v = null;
			v = new VoxelToArrayFeature(adapter.getRGBPixels());
		return v; 
	}
	
	public VoxelToArrayFeature extract(HasMesh adapter) {
		VoxelToArrayFeature v = null;
			v = new VoxelToArrayFeature();
			v.voxelsToArray(adapter.getMesh());
		return v; 
	}


	@Override
	public DicomImageObjectAdapter newAdapter() {
		return new DicomImageObjectAdapter();
	}

	@Override
	public String getName() {
		return "Voxels to Array";
	}

	@Override
	public Set<Class<? extends Adapter>> supportedAdapters() {
		Set<Class<? extends Adapter>> adapters = new HashSet<Class<? extends Adapter>>();
		adapters.add(HasVoxels.class);
		adapters.add(HasMesh.class);
		return adapters;
	}

	@Override
	public Class<? extends Descriptor> getFeatureType() {
		return VoxelToArrayFeature.class;
	}

	@Override
	public boolean hasPreview() {
		return true;
	}

	@Override
	public String previewName() {
		return "Voxels to Array";
	}

	@Override
	public String getCategory() {
		return "3D";
	}	
}
