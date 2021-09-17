package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ApkAnalyzer.ApkInfo;
import apkData.ApkManifestData;

public class Database {

    private static Database db;

     String dbURL = "jdbc:mysql://localhost:3306/apkDb";
     String username = "root";
     String password = "admin";
    public static Database getInstance()
    {
        if(db==null)
        {
            db=new Database();
        }
        return db;
    }
    public  Connection getConnection()
    {
        Connection conn=null;
        try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             conn = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException throwables) {
            printException(throwables);
        } catch (ClassNotFoundException e) {
            printException(e);
        }
        return conn;
    }

    public  int insertRecord(ApkInfo info)
    {
        int rowsInserted =0 ;
        try {
            Connection conn = getConnection();

            if (conn != null && !conn.isClosed()) {
                String sql = "INSERT INTO apkDetails (" +
                        "packageName, " +
                        "apkName, " +
                        "apkSize," +
                        "apkDexSize," +
                        "apkResourcesSize,"+
                        "apkAssetsSize,"+
                        "apkIsDebuggable," +
                        "apkVersionInfo," +
                        "apkSdkInfo," +
                        "apkCompiledSdkVersion," +
                        "apkCompileSdkVersionName,"+
                        "apkSupportedScreenSize,"+
                        "akpActivityCount,"+
                        "apkActivitiesName,"+
                        "apkPermissionsCount,"+
                        "apkPermissionNames,"+
                        "apkServiceCount,"+
                        "apkServiceNames,"+
                        "apkFeatureCount,"+
                        "apkFeatureNames,"+
                        "apkBroadCastCount,"+
                        "apkBroadCastNames,"+
                        "apkContentProviderCount,"+
                        "apkContentProvideNames,"+
                        "apkSignInfoMD5,"+
                        "apkSignInfoSHA1,"+
                        "apkSignInfoSHA256,"+
                        "apkSignInfoFacebookHash,"+
                        "apkSignInfoPublicKey,"+
                        "apkSignInfoVersion,"+
                        "apkSignInfoSignature,"+
                        "apkSignInfoSignAlgName,"+
                        "apkSignInfoSerialNumber,"+
                        "apkSignInfoIssuerNameDn,"+
                        "apkSignInfoSubjectNameDn,"+
                        "apkSignInfoGetNotAfter,"+
                        "apkSignInfoGetNotBefore)"+
                        " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, info.getApkPackageName());
                statement.setString(2, info.getApkName());
                statement.setString(3, info.getApkSize());
                statement.setString(4,info.getApkDexSize());

                statement.setString(5,info.getApkResourcesSize());
                statement.setString(6,info.getApkAssetsSize());

                statement.setString(7,info.getApkIsDebuggable());
                statement.setString(8,info.getApkVersionInfo());
                statement.setString(9,info.getApkSdkInfo());
                statement.setString(10,info.getApkCompiledSdkVersion());
                statement.setString(11,info.getApkCompileSdkVersionName());
                statement.setString(12,info.getApkSupportedScreenSize());
                statement.setString(13,info.getAkpActivityCount());
                statement.setString(14,info.getApkActivitiesName());
                statement.setString(15,info.getApkPermissionsCount());
                statement.setString(16,info.getApkPermissionNames());
                statement.setString(17,info.getApkServiceCount());
                statement.setString(18,info.getApkServiceNames());
                statement.setString(19,info.getApkFeatureCount());
                statement.setString(20,info.getApkFeatureNames());
                statement.setString(21,info.getApkBroadCastCount());
                statement.setString(22,info.getApkBroadCastNames());
                statement.setString(23,info.getApkContentProviderCount());
                statement.setString(24,info.getApkContentProvideNames());


                statement.setString(25,info.getApkSignInfoMD5());
                statement.setString(26,info.getApkSignInfoSHA1());
                statement.setString(27,info.getApkSignInfoSHA256());
                statement.setString(28,info.getApkSignInfoFacebookHash());
                statement.setString(29,info.getApkSignInfoPublicKey());
                statement.setString(30,info.getApkSignInfoVersion());
                statement.setString(31,info.getApkSignInfoSignature());
                statement.setString(32,info.getApkSignInfoSignAlgName());
                statement.setString(33,info.getApkSignInfoSerialNumber());
                statement.setString(34,info.getApkSignInfoIssuerNameDn());
                statement.setString(35,info.getApkSignInfoSubjectNameDn());
                statement.setString(36,info.getApkSignInfoGetNotAfter());
                statement.setString(37,info.getApkSignInfoGetNotBefore());


                 rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Apk details saved successfully");
                }
                conn.close();
            }
        }catch (SQLException exception)
        {
           printException(exception);
        }

       return rowsInserted;
    }
    public ApkInfo getRecord(String packageName)
    {
        ApkInfo info=null;
        try
        {
            Connection conn=getConnection();
            String sql = "SELECT * FROM apkDetails where packageName='"+packageName+"'";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()){
                info=new ApkInfo();
                info.setApkPackageName(result.getString("packageName"));
                info.setApkName(result.getString("apkName"));
                info.setApkSize(result.getString("apkSize"));
                info.setApkDexSize(result.getString("apkDexSize"));
                info.setApkResourcesSize(result.getString("apkResourcesSize"));
                info.setApkAssetsSize(result.getString("apkAssetsSize"));
                info.setApkIsDebuggable(result.getString("apkIsDebuggable"));
                info.setApkVersionInfo(result.getString("apkVersionInfo"));
                info.setApkSdkInfo(result.getString("apkSdkInfo"));
                info.setApkCompiledSdkVersion(result.getString("apkCompiledSdkVersion"));
                info.setApkCompileSdkVersionName(result.getString("apkCompileSdkVersionName"));
                info.setApkSupportedScreenSize(result.getString("apkSupportedScreenSize"));

                info.setAkpActivityCount(result.getString("akpActivityCount"));
                info.setApkActivitiesName(result.getString("apkActivitiesName"));
                info.setApkPermissionsCount(result.getString("apkPermissionsCount"));
                info.setApkPermissionNames(result.getString("apkPermissionNames"));
                info.setApkServiceCount(result.getString("apkServiceCount"));
                info.setApkServiceNames(result.getString("apkServiceNames"));
                info.setApkFeatureCount(result.getString("apkFeatureCount"));
                info.setApkFeatureNames(result.getString("apkFeatureNames"));
                info.setApkBroadCastCount(result.getString("apkBroadCastCount"));
                info.setApkBroadCastNames(result.getString("apkBroadCastNames"));
                info.setApkContentProviderCount(result.getString("apkContentProviderCount"));
                info.setApkContentProvideNames(result.getString("apkContentProvideNames"));

                info.setApkSignInfoMD5(result.getString("apkSignInfoMD5"));
                info.setApkSignInfoSHA1(result.getString("apkSignInfoSHA1"));
                info.setApkSignInfoSHA256(result.getString("apkSignInfoSHA256"));
                info.setApkSignInfoFacebookHash(result.getString("apkSignInfoFacebookHash"));
                info.setApkSignInfoPublicKey(result.getString("apkSignInfoPublicKey"));
                info.setApkSignInfoVersion(result.getString("apkSignInfoVersion"));
                info.setApkSignInfoSignature(result.getString("apkSignInfoSignature"));
                info.setApkSignInfoSignAlgName(result.getString("apkSignInfoSignAlgName"));
                info.setApkSignInfoSerialNumber(result.getString("apkSignInfoSerialNumber"));
                info.setApkSignInfoIssuerNameDn(result.getString("apkSignInfoIssuerNameDn"));
                info.setApkSignInfoSubjectNameDn(result.getString("apkSignInfoSubjectNameDn"));
                info.setApkSignInfoGetNotAfter(result.getString("apkSignInfoGetNotAfter"));
                info.setApkSignInfoGetNotBefore(result.getString("apkSignInfoGetNotBefore"));

            }
        }
        catch (Exception e)
        {
            printException(e);
        }
        return info;
    }
    private void printException(Exception exception)
    {
        String str=exception.getMessage()+"   -->Localise<<--  "+exception.getLocalizedMessage();
        System.out.println("Takendra -->>> "+exception.getMessage());
    }
}
