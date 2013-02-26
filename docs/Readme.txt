=================================================
Using Metric Implementations in Eclipse
=================================================
1. Ensure Eclipse is installed
2. Create new project in Eclipse (File->New->Java Project)
3. Copy in lib/ dir (with jars)
4. Add in jars (R-click projectName -> Properties -> Java Build Path -> Libraries tab; foreach jar, Add External JARs -> select jar in lib/ )
5. Copy in (and/or extract from zip) code inside src/ directory (should create comparisons/ directory tree)
6. Should be ready to edit or use with Eclipse

=================================================
Running Various Metric Tests
=================================================
1. Select test.java.gov.nist.itl.versus.similarity3d.comparisons.measure.impl directory in eclipse browser (usually in left panel in "Java Browsing Perspective"). 
2. Right-click tests.comparisons.measure.impl directory -> Right-click "Run As" -> Right-click "JUnit Test"
3. All the unit tests should run and display output results in the console window.

   The tests are in test/java/gov/nist/itl/versus/similarity3d/comparisons/measure/impl/ subdirectory. 
   The corresponding data is in the data/ subdirectory.

=================================================
Release History / Release Notes
=================================================

01/24/2013	 similarity3d v 1.0
	Benjamin Long	Initial upload of similarity3d metric code
		- Created adapter(s)/loader(s) for Dicom file format
		- Adapted original similarity (2d) histogram and pixel measures to be able to handle 3d volumetric data.
		- Current comparisons are "whole-image" comparisons.
	
		- NOTES/ISSUES:
			- Can currently load dicom on a per-file basis or per-directory basis (in versus, this zipping directories of dicom files).
			- ARI/RI should only be used on binary data.
