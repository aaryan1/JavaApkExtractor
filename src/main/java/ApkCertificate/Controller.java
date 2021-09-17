package ApkCertificate;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;


public class Controller
{
    public interface Callback {
        void getKeyHashesComplete(KeyHashes result);
    }
    
    public class KeyHashes {
        public Exception error = null;
        
        public String MD5 = null;
        public String SHA1 = null;
        public String SHA256 = null;
        public String FacebookHash = null;
        public String PublicKey=null;
        public String Signature=null;
        public String SignAlgName=null;
        public String SerialNumber=null;
        public String IssuerNameAndSubject =null;
        public String GetNotAfter=null;
        public String GetNotBefore=null;
        public int GetVersion;
    }
    
    public void getKeyHashes(String archiveSourcePath, Callback callback) {
        BackgroundTask task = new BackgroundTask(archiveSourcePath, callback);
        task.execute();
    }
    
    private class BackgroundTask extends SwingWorker<KeyHashes, Void> {
        private String filepath;
        private Callback callback;
        
        public BackgroundTask(String filepath, Callback callback) {
            this.filepath = filepath;
            this.callback = callback;
        }
        
        @Override
        public KeyHashes doInBackground() {
            return getKeyHashes(filepath);
        }
        
        @Override
        protected void done() {
            try {
                callback.getKeyHashesComplete(get());
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            } catch (ExecutionException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private KeyHashes getKeyHashes(String archiveSourcePath) {
        PackageParser parser = new PackageParser();
        PackageParser.Certificates certificates = parser.collectCertificates(archiveSourcePath);
        if (certificates.error != null) {
            KeyHashes result = new KeyHashes();
            result.error = certificates.error;
            return result;
        }
        
        KeyTool keytool = new KeyTool();
        KeyHashes result = new KeyHashes();
        Certificate cert = certificates.certs[0];
        try {
            X509Certificate x509certificate = (X509Certificate) cert;
            result.MD5 = keytool.getCertFingerPrint("MD5", cert);
            result.SHA1 = keytool.getCertFingerPrint("SHA1", cert);
            result.SHA256 = keytool.getCertFingerPrint("SHA-256", cert);


            result.PublicKey=keytool.toHexString(cert.getPublicKey().getEncoded());
            result.GetVersion=x509certificate.getVersion();
            result.SignAlgName=x509certificate.getSigAlgName();
            result.SerialNumber=x509certificate.getSerialNumber().toString();
            //result.IssuerNames=x509certificate.getIssuerAlternativeNames().toString();
            result.IssuerNameAndSubject =x509certificate.getIssuerDN().getName()+"\nSubjectName : "+x509certificate.getSubjectDN().getName();
            result.GetNotAfter=x509certificate.getNotAfter().toString();
            result.GetNotBefore=x509certificate.getNotBefore().toString();
            result.Signature=keytool.toHexString(x509certificate.getSignature());




        } catch(Exception e) {
            System.err.println(e.getMessage());
            result.error = e;
        }
        
        try {
            result.FacebookHash = getFacebookKeyHash(cert);
        } catch(CertificateEncodingException e) {
            System.err.println(e.getMessage());
            result.error = e;
        } catch(NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
            result.error = e;
        }
        return result;
    }
    
    // based on https://developers.facebook.com/docs/getting-started/facebook-sdk-for-android/3.0/#sig
    private String getFacebookKeyHash(Certificate cert) throws NoSuchAlgorithmException, CertificateEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(cert.getEncoded());
        return Base64.encodeToString(md.digest(), Base64.DEFAULT);
    }
}
