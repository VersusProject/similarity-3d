package gov.nist.itl.versus.similarity3d.comparisons.extract.impl;

import java.util.HashSet;
import java.util.Set;

import edu.illinois.ncsa.versus.UnsupportedTypeException;
import edu.illinois.ncsa.versus.adapter.Adapter;
import edu.illinois.ncsa.versus.adapter.HasPixels;
import edu.illinois.ncsa.versus.adapter.impl.ImageObjectAdapter;
import edu.illinois.ncsa.versus.descriptor.Descriptor;
import edu.illinois.ncsa.versus.extract.Extractor;

import gov.nist.itl.versus.similarity3d.comparisons.descriptor.impl.VoxelToArrayFeature;
import gov.nist.itl.versus.similarity3d.comparisons.adapter.impl.DicomImageObjectAdapter;

public class VoxelToArrayFeatureExtractor implements Extractor 
{
	private VoxelToArrayFeature extract(HasPixels adapter) {
		return new VoxelToArrayFeature(adapter.getRGBPixels());
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
	public Descriptor extract(Adapter adapter) throws UnsupportedTypeException {
		if (adapter instanceof HasPixels) {
			HasPixels hasPixels = (HasPixels) adapter;
			return extract(hasPixels);
		} else {
			throw new UnsupportedTypeException();
		}
	}

	@Override
	public Set<Class<? extends Adapter>> supportedAdapters() {
		Set<Class<? extends Adapter>> adapters = new HashSet<Class<? extends Adapter>>();
		adapters.add(HasPixels.class);
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
}
