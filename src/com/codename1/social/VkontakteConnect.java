package com.codename1.social;

import com.codename1.io.Oauth2;
import com.codename1.social.Login;
import java.util.Hashtable;

public class VkontakteConnect extends Login{
    
    private static String scope = "email";
    private static String tokenURL = "https://oauth.vk.com/access_token";
    
    private static VkontakteConnect instance;
    static Class implClass;
    
    VkontakteConnect() {
        setOauth2URL("https://oauth.vk.com/authorize");
    }
    
    /**
     * Gets the VkontakteConnect singleton instance
     * .
     * @return the VkontakteConnect instance
     */ 
    public static VkontakteConnect getInstance() {
        if (instance == null) {
            if (implClass != null) {
                try {
                    instance = (VkontakteConnect) implClass.newInstance();
                } catch (Throwable t) {
                    instance = new VkontakteConnect();
                }
            } else {
                instance = new VkontakteConnect();
            }
        }
        return instance;
    }

    @Override
    public boolean isNativeLoginSupported() {
        return false;
    }

    @Override
    protected Oauth2 createOauth2() {
        Hashtable params = new Hashtable();
        params.put("v",       "5.34");
        params.put("display", "mobile");
        Oauth2 auth = new Oauth2(oauth2URL, clientId, redirectURI, scope, tokenURL, clientSecret, params);
        return auth;
    }
}