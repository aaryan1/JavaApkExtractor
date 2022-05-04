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

		//apkPath=Paths.get(args[0]);
		apkPath= Path.of(new File("C:\\apk\\aha.apk").getAbsolutePath());
		//apkPath= Path.of(new File("C:\\apksigned\\aha.apk").getAbsolutePath());
		apkPath= Path.of(new File("C:\\apk\\Zee 5 Apk.apk").getAbsolutePath());
		location.setApkPath(apkPath);
		
		new analyzeApk(apkPath);

	}

}
