package ApkAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import ApkCertificate.Controller;
import ApkLocation.Locations;
import Database.Database;
import apkData.ApkManifestData;
import apkData.ApkSizeData;

public class analyzeApk  implements Controller.Callback {

    private static Locations locations = new Locations();
    private static ApkSizeData apkdata = new ApkSizeData();
    private static ApkManifestData manifestdata = new ApkManifestData();
    StringBuilder strBuilder;
    long totalResourcesSize=0;
    private Controller controller;
    private Controller.Callback callback = this;
    Controller.KeyHashes certificateResult;
    public analyzeApk(Path apkPath) {
        Controller controller = new Controller();
        this.controller=controller;
        System.out.println("Apk Location: " + locations.getApkPath());

        controller.getKeyHashes(apkPath.toString(), callback);

        try {
            File file = new File(System.getProperty("user.dir") + "/result.txt");
            FileWriter output = new FileWriter(file, true);
            System.out.println("OutputFile location:   " + System.getProperty("user.dir"));

            //new Size(apkPath);
            new Manifest();
           // new Debuggable();
            new ReadYml();
            System.out.println("Apk Size:  " + "\n" +Size.getSize(new File(apkPath.toString()).length()));
            System.out.println("Manifest Data: " + "\n" + manifestdata.toString());

            //output.write(apkdata.toString());
            output.write("\nDexSize="+Size.getSize(getDexSize()));
            output.write("\nResourceSize="+Size.getSize(getResourcesSize()));
            output.write("\nAssetsSize="+Size.getSize(getAssetsSize()));
            output.write(manifestdata.toString() + "%n");


            /** resource files*/
            output.write("\n ---->>> Resources Files:<<<----\n");
            output.write(getAllResources().toString());


            /** Assets files*/
            output.write("\n ---->>> Assets Files:<<<----\n");
            output.write(getAllAssetsFiles().toString());

            output.close();

            ApkInfo info=new ApkInfo();
            info.setApkName(apkPath.getFileName().toString());
            info.setApkSize(Size.getSize(new File(apkPath.toString()).length()));
            info.setApkPackageName(ApkManifestData.getPackageName());
            info.setApkDexSize(Size.getSize(getDexSize()));
            info.setApkResourcesSize(Size.getSize(getResourcesSize()));
            info.setApkAssetsSize(Size.getSize(getAssetsSize()));
            info.setApkIsDebuggable(String.valueOf(ApkManifestData.isDebuggable()));
            info.setApkSdkInfo(ApkManifestData.getSdkInfo().toString());
            info.setApkVersionInfo(ApkManifestData.getApkInfo().toString());
            info.setApkCompiledSdkVersion(ApkManifestData.getCompileSdkVersion());
            info.setApkCompileSdkVersionName(ApkManifestData.getCompileSdkVersionName());
            info.setApkSupportedScreenSize(ApkManifestData.getSupportedScreens().toString());
            info.setAkpActivityCount(String.valueOf(ApkManifestData.getActivityList().size()));
            info.setApkActivitiesName(ApkManifestData.getActivityList().toString());
            info.setApkPermissionsCount(String.valueOf(ApkManifestData.getPermissions().size()));
            info.setApkPermissionNames(ApkManifestData.getPermissions().toString());
            info.setApkServiceCount(String.valueOf(ApkManifestData.getServices().size()));
            info.setApkServiceNames(ApkManifestData.getServices().toString());
            info.setApkFeatureCount(String.valueOf(ApkManifestData.getFeaturesList().size()));
            info.setApkFeatureNames(ApkManifestData.getFeaturesList().toString());
            info.setApkBroadCastCount(String.valueOf(ApkManifestData.getBroadCastReceiverList().size()));
            info.setApkBroadCastNames(ApkManifestData.getBroadCastReceiverList().toString());
            info.setApkContentProviderCount(String.valueOf(ApkManifestData.getContentProvider().size()));
            info.setApkContentProvideNames(ApkManifestData.getContentProvider().toString());
            if(certificateResult!=null)
            {
                info.setApkSignInfoMD5(certificateResult.MD5);
                info.setApkSignInfoSHA1(certificateResult.SHA1);
                info.setApkSignInfoSHA256(certificateResult.SHA256);
                info.setApkSignInfoFacebookHash(certificateResult.FacebookHash);
                info.setApkSignInfoPublicKey(certificateResult.PublicKey);
                info.setApkSignInfoVersion(String.valueOf(certificateResult.GetVersion));
                info.setApkSignInfoSignature(certificateResult.Signature);
                info.setApkSignInfoSignAlgName(certificateResult.SignAlgName);
                info.setApkSignInfoSerialNumber(certificateResult.SerialNumber);
                info.setApkSignInfoIssuerNameDn(certificateResult.IssuerNameAndSubject);
                info.setApkSignInfoSubjectNameDn(certificateResult.IssuerNameAndSubject);
                info.setApkSignInfoGetNotAfter(certificateResult.GetNotAfter);
                info.setApkSignInfoGetNotBefore(certificateResult.GetNotBefore);


            }

            Database db =Database.getInstance();

           ApkInfo oldApkInfo=db.getRecord(ApkManifestData.getPackageName());
             if(oldApkInfo!=null && oldApkInfo.getApkPackageName().equalsIgnoreCase(ApkManifestData.getPackageName()))
            {
               System.out.println("Record of "+oldApkInfo.getApkPackageName()+" already exist \n Preparing comparison file ");
               prepareComparisonFile(oldApkInfo,info);
            }
            else {
                 db.insertRecord(info);
             }


        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }

    private void prepareComparisonFile(ApkInfo oldApkInfo, ApkInfo info) {
        File file = new File(System.getProperty("user.dir") + "/OutputCompare.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(file.exists())
        {
            try {
                FileWriter output = new FileWriter(file, false);

                output.write("--------------Apk comparison Result----------------------");
                output.write("\nOld apk name="+oldApkInfo.getApkName());
                output.write("\nNew apk name="+info.getApkName());

                output.write("\n------------------------------------");

                output.write("\nNew apk package name="+oldApkInfo.getApkPackageName());
                output.write("\nOld apk package name="+info.getApkPackageName());

                output.write("\n------------------------------------");

                output.write("\nOld apk size="+oldApkInfo.getApkSize());
                output.write("\nNew apk size="+info.getApkSize());

                output.write("\n------------------------------------");

                output.write("\nOld apk apkDexSize="+oldApkInfo.getApkDexSize());
                output.write("\nNew apk apkDexSize="+info.getApkDexSize());

                output.write("\n------------------------------------");

                output.write("\nOld apk apkResourcesSize="+oldApkInfo.getApkResourcesSize());
                output.write("\nNew apk apkResourcesSize="+info.getApkResourcesSize());

                output.write("\n------------------------------------");

                output.write("\nOld apk apkAssetsSize="+oldApkInfo.getApkAssetsSize());
                output.write("\nNew apk apkAssetsSize="+info.getApkAssetsSize());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkIsDebuggable="+oldApkInfo.getApkIsDebuggable());
                output.write("\nNew apk apkIsDebuggable="+info.getApkIsDebuggable());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkVersionInfo="+oldApkInfo.getApkVersionInfo());
                output.write("\nNew apk apkVersionInfo="+info.getApkVersionInfo());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSdkInfo="+oldApkInfo.getApkSdkInfo());
                output.write("\nNew apk apkSdkInfo="+info.getApkSdkInfo());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkCompiledSdkVersion="+oldApkInfo.getApkCompiledSdkVersion());
                output.write("\nNew apk apkCompiledSdkVersion="+info.getApkCompiledSdkVersion());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkCompileSdkVersionName="+oldApkInfo.getApkCompileSdkVersionName());
                output.write("\nNew apk apkCompileSdkVersionName="+info.getApkCompileSdkVersionName());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSupportedScreenSize="+oldApkInfo.getApkSupportedScreenSize());
                output.write("\nNew apk apkSupportedScreenSize="+info.getApkSupportedScreenSize());

                output.write("\n------------------------------------");


                output.write("\nOld apk ActivityCount="+oldApkInfo.getAkpActivityCount());
                output.write("\nNew apk ActivityCount="+info.getAkpActivityCount());

                output.write("\n------------------------------------");

                output.write("\nOld apk ActivityNames="+oldApkInfo.getApkActivitiesName());
                output.write("\nNew apk ActivityNames="+info.getApkActivitiesName());

                output.write("\n------------------------------------");


                output.write("\nOld apk PermissionCount="+oldApkInfo.getApkPermissionsCount());
                output.write("\nNew apk PermissionCount="+info.getApkPermissionsCount());

                output.write("\n------------------------------------");


                output.write("\nOld apk PermissionName="+oldApkInfo.getApkPermissionNames());
                output.write("\nNew apk PermissionName="+info.getApkPermissionNames());

                output.write("\n------------------------------------");


                output.write("\nOld apk ServiceCount="+oldApkInfo.getApkServiceCount());
                output.write("\nNew apk ServiceCount="+info.getApkServiceCount());

                output.write("\n------------------------------------");


                output.write("\nOld apk ServiceNames="+oldApkInfo.getApkServiceNames());
                output.write("\nNew apk ServiceNames="+info.getApkServiceNames());

                output.write("\n------------------------------------");


                output.write("\nOld apk BroadCastCount="+oldApkInfo.getApkBroadCastCount());
                output.write("\nNew apk BroadCastCount="+info.getApkBroadCastCount());

                output.write("\n------------------------------------");


                output.write("\nOld apk BroadCastNames="+oldApkInfo.getApkBroadCastNames());
                output.write("\nNew apk BroadCastNames="+info.getApkBroadCastNames());

                output.write("\n------------------------------------");

                output.write("\nOld apk ContentProvidersCount="+oldApkInfo.getApkContentProviderCount());
                output.write("\nNew apk ContentProvidersCount="+info.getApkContentProviderCount());

                output.write("\n------------------------------------");

                output.write("\nOld apk ContentProvidersNames="+oldApkInfo.getApkContentProvideNames());
                output.write("\nNew apk ContentProvidersNames="+info.getApkContentProvideNames());

                output.write("\n------------------------------------");

                output.write("\nOld apk FeaturePermissionCount="+oldApkInfo.getApkFeatureCount());
                output.write("\nNew apk FeaturePermissionCount="+info.getApkFeatureCount());

                output.write("\n------------------------------------");


                output.write("\nOld apk FeaturePermissionName="+oldApkInfo.getApkFeatureNames());
                output.write("\nNew apk FeaturePermissionName="+info.getApkFeatureNames());

                output.write("\n------------------------------------");



                output.write("\nOld apk apkSignInfoMD5="+oldApkInfo.getApkSignInfoMD5());
                output.write("\nNew apk apkSignInfoMD5="+info.getApkSignInfoMD5());

                output.write("\n------------------------------------");

                output.write("\nOld apk apkSignInfoSHA1="+oldApkInfo.getApkSignInfoSHA1());
                output.write("\nNew apk apkSignInfoSHA1="+info.getApkSignInfoSHA1());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoSHA256="+oldApkInfo.getApkSignInfoSHA256());
                output.write("\nNew apk apkSignInfoSHA256="+info.getApkSignInfoSHA256());

                output.write("\n------------------------------------");

                output.write("\nOld apk apkSignInfoFacebookHash="+oldApkInfo.getApkSignInfoFacebookHash());
                output.write("\nNew apk apkSignInfoFacebookHash="+info.getApkSignInfoFacebookHash());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoPublicKey="+oldApkInfo.getApkSignInfoPublicKey());
                output.write("\nNew apk apkSignInfoPublicKey="+info.getApkSignInfoPublicKey());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoVersion="+oldApkInfo.getApkSignInfoVersion());
                output.write("\nNew apk apkSignInfoVersion="+info.getApkSignInfoVersion());

                output.write("\n------------------------------------");

                output.write("\nOld apk apkSignInfoSignature="+oldApkInfo.getApkSignInfoSignature());
                output.write("\nNew apk apkSignInfoSignature="+info.getApkSignInfoSignature());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoSignAlgName="+oldApkInfo.getApkSignInfoSignAlgName());
                output.write("\nNew apk apkSignInfoSignAlgName="+info.getApkSignInfoSignAlgName());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoSerialNumber="+oldApkInfo.getApkSignInfoSerialNumber());
                output.write("\nNew apk apkSignInfoSerialNumber="+info.getApkSignInfoSerialNumber());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoIssuerNameDn="+oldApkInfo.getApkSignInfoIssuerNameDn());
                output.write("\nNew apk apkSignInfoIssuerNameDn="+info.getApkSignInfoIssuerNameDn());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoSubjectNameDn="+oldApkInfo.getApkSignInfoSubjectNameDn());
                output.write("\nNew apk apkSignInfoSubjectNameDn="+info.getApkSignInfoSubjectNameDn());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoGetNotAfter="+oldApkInfo.getApkSignInfoSubjectNameDn());
                output.write("\nNew apk apkSignInfoGetNotAfter="+info.getApkSignInfoSubjectNameDn());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoGetNotAfter="+oldApkInfo.getApkSignInfoGetNotAfter());
                output.write("\nNew apk apkSignInfoGetNotAfter="+info.getApkSignInfoGetNotAfter());

                output.write("\n------------------------------------");


                output.write("\nOld apk apkSignInfoGetNotBefore="+oldApkInfo.getApkSignInfoGetNotBefore());
                output.write("\nNew apk apkSignInfoGetNotBefore="+info.getApkSignInfoGetNotBefore());

                output.write("\n------------------------------------");





                output.close();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public long getResourcesSize()
{
    totalResourcesSize=0;
    String folderPath = System.getProperty("user.dir") + "/Decompiled/res";
    final File folder = new File(folderPath);
    long totalSize =listFilesForFolderForSize(folder);
    return totalSize;
}
public long getAssetsSize()
{
    totalResourcesSize=0;
    String folderPath = System.getProperty("user.dir") + "/Decompiled/assets";
    final File folder = new File(folderPath);
    long totalSize =listFilesForFolderForSize(folder);
    return totalSize;
}
    public StringBuilder getAllResources()
    {
        strBuilder=new StringBuilder();
        String folderPath = System.getProperty("user.dir") + "/Decompiled/res";
        final File folder = new File(folderPath);
        StringBuilder str=listFilesForFolder(folder);
        return str;
    }
    public StringBuilder getAllAssetsFiles()
    {
        strBuilder=new StringBuilder();
        String folderPath = System.getProperty("user.dir") + "/Decompiled/assets";
        final File folder = new File(folderPath);
        StringBuilder str=listFilesForFolder(folder);
        return str;
    }
    public StringBuilder listFilesForFolder(final File folder) {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                strBuilder.append(fileEntry.getName()+"\n");
            }
        }
        return strBuilder;
    }
    public long listFilesForFolderForSize(final File folder) {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolderForSize(fileEntry);
            } else {
                totalResourcesSize+=fileEntry.length();
            }
        }
        return totalResourcesSize;
    }
    public Long getDexSize()
    {
        String folderPath = System.getProperty("user.dir") + "/Decompiled";
        File dexFile = new File(folderPath, "classes.dex");
      if(dexFile.exists()) {
          Long length = dexFile.length();
          if (length != null && length != 0) {
              return length;
          }

      }
        return 0l;
    }

    @Override
    public void getKeyHashesComplete(Controller.KeyHashes result) {

        if (result.error != null) {
            return ;
        }
        certificateResult=result;

        String message = "";
        message += "MD5: " + result.MD5 + "\n";
        message += "SHA1: " + result.SHA1 + "\n";
        message += "SHA256: " + result.SHA256 + "\n";
        message += "Facebook hash: " + result.FacebookHash;
        message+="PublicKey: "+result.PublicKey+"\n";
        message+="Version: "+result.GetVersion+"\n";
        message+="Signature: "+result.Signature+"\n";
        message+="SignAlgName: "+result.SignAlgName+"\n";
        message+="SerialNumber: "+result.SerialNumber+"\n";
        message+="IssuerNames: "+result.IssuerNameAndSubject +"\n";
        message+="GetNotAfter: "+result.GetNotAfter+"\n";
        message+="GetNotBefore: "+result.GetNotBefore+"\n";

        System.out.println("ApkSignInfo  "+message);


    }
}
