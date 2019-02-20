package facebookapi;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author amr
 */
public class FacebookApi {

    public static void main(String[] args) throws FileNotFoundException {
        String accessToken = "EAAIBZAAlGdn4BACWG9nhlbYTAEW7gSlOspEeX26d2tPUGlRlZCvD0t1NTDTy0XQFPWQZC6ak7mGnn1QOgTHqgVThOILAHuF88IDsKkgVOARUz0wgSgirFeuas0jZAhJltb7VDpEHnoX4As8ZAiHAF2wDFQTlIagLurT3vmDqvKkFCgVDZCuv9cex9Pb4NrKQVEKTkvUCH4ZCwZDZD";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
        FileInputStream fis = new FileInputStream(new File("/home/amr/NetBeansProjects/Patch2/out.jpg"));

        FacebookType response = fbClient.publish("me/feed", FacebookType.class, BinaryAttachment.with("out.jpg", fis), Parameter.with("message ", "RadixCode Logo image"));

        System.out.println("fb.com/" + response.getId());

        User me = fbClient.fetchObject("me", User.class);
        System.out.println(me.getName());
        System.out.println(me.getBirthday());

    }

}
