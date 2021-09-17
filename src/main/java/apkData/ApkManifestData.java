package apkData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApkManifestData {
	
	private static String packageName;
	private static List<?> permissionsList=new ArrayList<>();
	private static String compileSdkVersionName;
	private static String compileSdkVersion;
	private static List<?> activityList=new ArrayList<>();
	private static Object apkInfo;
	private static Object sdkInfo;
	private static boolean Debuggable;
	private static Map<String,String> SupportedScreens=new HashMap<>();
	private static String versionName;
	private static String versionCode;
	private static List<?> Services=new ArrayList<>();
	private static List<?> ContentProvider=new ArrayList<>();
	private static List<?> BroadCastReceiverList=new ArrayList<>();

	private static List<?> featuresList=new ArrayList<>();


	public static List<?> getFeaturesList() {
		return featuresList;
	}
	public static void setFeaturesList(List<?> featuresList) {
		ApkManifestData.featuresList = featuresList;
	}
	public static List<?> getBroadCastReceiverList() {
		return BroadCastReceiverList;
	}

	public static void setBroadCastReceiverList(List<?> broadCastReceiverList) {
		BroadCastReceiverList = broadCastReceiverList;
	}

	public static Object getSdkInfo() {
		return sdkInfo;
	}

	public static void setSdkInfo(Object sdkInfo) {
		ApkManifestData.sdkInfo = sdkInfo;
	}

	public static Object getApkInfo() {
		return apkInfo;
	}

	public static void setApkInfo(Object object) {
		ApkManifestData.apkInfo = object;
	}
	
	public static List<?> getContentProvider() {
		return ContentProvider;
	}

	public void setContentProvider(List<?> contentProvider) {
		ApkManifestData.ContentProvider = contentProvider;
	}

	public static List<?> getServices() {
		return Services;
	}

	public void setServices(List<?> services) {
		ApkManifestData.Services = services;
	}

	public static String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String string) {
		ApkManifestData.versionCode = string;
	}

	public static String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		ApkManifestData.versionName = versionName;
	}

	public static boolean isDebuggable() {
		return Debuggable;
	}

	public void setDebuggable(boolean debuggable) {
		ApkManifestData.Debuggable = debuggable;
	}

	public static List<?> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<?> activityList) {
		ApkManifestData.activityList = activityList;
	}

	public static String getCompileSdkVersionName() {
		return compileSdkVersionName;
	}

	public void setCompileSdkVersionName(String compileSdkVersionName) {
		ApkManifestData.compileSdkVersionName = compileSdkVersionName;
	}

	public static String getPackageName() {
		return packageName;
	}

	public static void setPackageName(String packageName) {
		ApkManifestData.packageName = packageName;
	}

	public static List<?> getPermissions() {
		return permissionsList;
	}

	public static Map<String, String> getSupportedScreens() {
		return SupportedScreens;
	}

	public void setSupportedScreens(Map<String, String> supportsScreens) {
		ApkManifestData.SupportedScreens = supportsScreens;
	}

	public static void setPermissions(List<?> permissionsList) {
		ApkManifestData.permissionsList = permissionsList;
	}

	public static String getCompileSdkVersion() {
		return compileSdkVersion;
	}

	public void setCompileSdkVersion(String compileSdkVersion) {
		ApkManifestData.compileSdkVersion = compileSdkVersion;
	}

	@Override
	public String toString() {
		return  "\nPackageName= " + packageName
			+"\n" + "Debuggable= " + Debuggable
			+"\n" +    apkInfo 
			+"\n" + sdkInfo
			+"\n" + "CompiledSdkVersion= " + compileSdkVersion
			+"\n" + "CompileSdkVersionName= "+ compileSdkVersionName
			+"\n" + "SupportedScreenSize= " + SupportedScreens
			+"\n" + "Permissions"+"["+permissionsList.size()+"]="  + permissionsList
			+"\n" + "Features"+"["+featuresList.size()+"]="  + featuresList
			+"\n" + "Activities"+"["+activityList.size()+"]=" + activityList
			+"\n" + "Services"+"["+Services.size()+"]=" + Services
			+"\n" + "BroadCastReceivers"+"["+BroadCastReceiverList.size()+"]=" + BroadCastReceiverList
			+"\n" + "ContentProviders"+"["+ContentProvider.size()+"]=" + ContentProvider;
			
	}
	
	

}
