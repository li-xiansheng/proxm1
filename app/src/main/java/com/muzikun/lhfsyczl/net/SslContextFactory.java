package com.muzikun.lhfsyczl.net;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.muzikun.lhfsyczl.MApplication;

/**
 * 功能描述：ssl安全验证，用于https访问
 */

public class SslContextFactory {
    private static final String CLIENT_TRUST_PASSWORD = "";//信任证书密码
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_TRUST_MANAGER = "X509";
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";
    SSLContext sslContext = null;

    public SSLContext getSslSocket() {
        /*try {
            //取得SSL的SSLContext实例
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            //取得TrustManagerFactory的X509密钥管理器实例
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
            //取得BKS密库实例
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);

            InputStream is = MApplication.getInstance().getResources().openRawResource(R.raw.dachebang);
            try {
                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
            } finally {
                is.close();
            }
            //初始化密钥管理器
            trustManager.init(tks);
            //初始化SSLContext
//            sslContext.init(null, trustManager.getTrustManagers(), null);
            sslContext.init(null, trustManager.getTrustManagers(), new SecureRandom());
        } catch (Exception e) {
            LogUtils.e("SslContextFactory", e.getMessage());
        }*/

        //取得TrustManagerFactory的X509密钥管理器实例
        final char[] JKS_PASSWORD = CLIENT_TRUST_PASSWORD.toCharArray();
        final char[] KEY_PASSWORD = CLIENT_TRUST_PASSWORD.toCharArray();

        try {
        /* Get the JKS contents */
            final KeyStore keyStore = KeyStore.getInstance("BKS");
            InputStream is = MApplication.getInstance().getResources().openRawResource(0);//R.raw.key
            keyStore.load(is, JKS_PASSWORD);

            final KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(keyStore, KEY_PASSWORD);
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init(keyStore);

		/*
         * Creates a socket factory for HttpsURLConnection using JKS
		 * contents
		 */
            //取得SSL的SSLContext实例
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext;
    }
}
