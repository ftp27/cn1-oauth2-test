package eu.indevgroup.cn1.tests.oauth2;


import com.codename1.db.Row;
import com.codename1.io.Log;
import com.codename1.io.Oauth2;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.social.FacebookConnect;
import com.codename1.social.GoogleConnect;
import com.codename1.social.VkontakteConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Hashtable;

public class main {

    private Form current;

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        
        Form hi = new Form("oAuth2-test");
        hi.setLayout(new GridLayout(4, 1));
        Label status = new Label("");
        Button fbButton = new Button("Facebook");
        fbButton.addActionListener(getSocialListener(
            status,    
            FacebookConnect.getInstance(), 
            "rest-auth/facebook/", 
            "5c0e954dbcbdd805e920527b98bdec78",
            "417297645125254"
        ));
        
        Button gpButton = new Button("Google Plus");
        gpButton.addActionListener(getSocialListener(
            status,
            GoogleConnect.getInstance(), 
            "rest-auth/google/", 
            "cSW__XE1CphdSWUSt0d3OjkE",
            "679089336538-no03e50bn04n5j95054r8phduo0ibsub.apps.googleusercontent.com"
        ));
        
        Button vkButton = new Button("Vkontakte");
        vkButton.addActionListener(getSocialListener(
            status,
            VkontakteConnect.getInstance(), 
            "rest-auth/vk/", 
            "Auo437BTJlLLcxcFg8fG",
            "4973969"
        ));
        
        hi.addComponent(fbButton);
        hi.addComponent(gpButton);
        hi.addComponent(vkButton);
        hi.addComponent(status);
        
        hi.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

    private ActionListener getSocialListener(final Label status, final Login socialLogin, String redirect, String secret, String id) {
        socialLogin.setClientId(id);
        socialLogin.setRedirectURI("http://lk2.beta.indev-group.eu/" + redirect);
        socialLogin.setClientSecret(secret);
        socialLogin.setCallback(new LoginCallback() {

            @Override
            public void loginSuccessful() {
                status.setText("Successful: "+socialLogin.getAccessToken().getToken());
            }

            @Override
            public void loginFailed(String errorMessage) {
                status.setText("Failed: "+errorMessage);
            }
            
            
            
        });
        return new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(!socialLogin.isUserLoggedIn()){
                    socialLogin.doLogin();
                }
            }            
        };
        
    }
    
}
