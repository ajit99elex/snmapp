package com.snm.app.dao;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.snm.app.FireBaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.util.Arrays;

@Component
public class FireBaseDatasource {

    Logger logger = LoggerFactory.getLogger(FireBaseDatasource.class);

    @Value("firebase.database.url")
    private String firebaseDBUrl;

    @Autowired
    private FireBaseConfig fireBaseConfig;

    private String authToken;

    public String getAuthToken() {
        try {
            logger.info("Fetching AccessToken for firebase");
            FileInputStream serviceAccount = new FileInputStream(getClass().getClassLoader().getResource(fireBaseConfig.getServiceKeyFile()).getFile());
            GoogleCredential googleCred = null;
            googleCred = GoogleCredential.fromStream(serviceAccount);
            GoogleCredential scoped = googleCred.createScoped(
                    Arrays.asList(
                            "https://www.googleapis.com/auth/firebase.database",
                            "https://www.googleapis.com/auth/userinfo.email"
                    )
            );
            scoped.refreshToken();
            authToken = scoped.getAccessToken();
            return authToken;
        } catch (Exception e) {
            logger.error("Excpetion while fetching AccessToken for firebase", e);
        }
        return null;
    }

    public String getFirebaseDBUrl() {
        return firebaseDBUrl;
    }

    public void setFirebaseDBUrl(String firebaseDBUrl) {
        this.firebaseDBUrl = firebaseDBUrl;
    }
}