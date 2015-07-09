package eu.indevgroup.cn1.tests.oauth2;


import com.codename1.db.Row;
import com.codename1.io.Log;
import com.codename1.io.Oauth2;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
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
        hi.setLayout(new GridLayout(2, 1));
        Button fbButton = new Button("Facebook");
        Hashtable fbParams = new Hashtable();
        fbParams.put("display","touch");
        Button gpButton = new Button("Google Plus");
        Hashtable gpParams = new Hashtable();
        gpParams.put("v",       "5.34");
        gpParams.put("display", "mobile");
        
        fbButton.addActionListener(getSocialListener(
            "https://www.facebook.com/dialog/oauth",
            "123456789012345",
            "email,public_profile",
            fbParams
        ));
        gpButton.addActionListener(getSocialListener(
            "https://accounts.google.com/o/oauth2/auth",
            "123456789012-no03e50bn04n5j95054r8phduo0ibsub.apps.googleusercontent.com",
            "email",
            gpParams
        ));
        hi.addComponent(fbButton);
        hi.addComponent(gpButton);
        hi.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

    private ActionListener getSocialListener(final String URL, final String clientId, final String scope, final Hashtable params) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Oauth2 fbOauth2 = new Oauth2(
                    URL, 
                    clientId, 
                    "someurl.com/",
                    scope, null, null, params
                );
                final Dialog authDialog = new Dialog();
                authDialog.setLayout(new BorderLayout());
                authDialog.addComponent(BorderLayout.CENTER, 
                    fbOauth2.createAuthComponent(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            authDialog.dispose();
                        }
                    })
                );
                try {
                    authDialog.show(0,0,0,0);
                } catch (Exception e) {
                    
                }
            }            
        };
    }
    
}
