"# ProfileLibrary" 

To use the this library in your android project

Step 1. Add the JitPack repository to your root build.gradle at the end of repositories

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Sejo9:ProfileLibrary:build-version'
	}
  
  Available build versions:
  
  v0.1
  
  1.Single view containing fields for a profile
  
  2.Fields : Profile Picture, Username ,Email, Phone Number
  
  3.Fields can be set via XML or programmatically
  
  4.To set fields programmatically, call the setter methods for the various fields
  
  5.Getter methods available for retrieving field data
