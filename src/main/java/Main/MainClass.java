package Main;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import ApkAnalyzer.analyzeApk;
import ApkLocation.Locations;

public class MainClass {
	private static Path apkPath;
	private static Locations location = new Locations();

	public static void main(String[] args) {
			// /home/vaibhav/Documents/apkFile.apk
		//apkPath=Paths.get(args[0]);
		apkPath= Path.of(new File("C:\\apk\\aha.apk").getAbsolutePath());
		//apkPath= Path.of(new File("C:\\apksigned\\aha.apk").getAbsolutePath());
		apkPath= Path.of(new File("C:\\apk\\amazon-prime-video.apk").getAbsolutePath());
		location.setApkPath(apkPath);
		
		new analyzeApk(apkPath);
		
		// /home/vaibhav/Documents/Apks/old.apk
		/*if(args.length>=2) {
			//System.out.println("Second Apk ");
			
			apkPath=Paths.get(args[1]);
			location.setApkPath(apkPath);
			
			new analyzeApk(apkPath);
		}	*/
	}

}
