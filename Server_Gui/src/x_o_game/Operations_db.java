/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x_o_game;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author amr
 */
public class Operations_db {

    Pattern valid_mail;
    Pattern valid_mail2;

    public Operations_db() {
        this.valid_mail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        this.valid_mail2 = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    
    }

    public boolean check_mail_exist(String mail) {
        Crud c = new Crud();
        if (c.select1(mail)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean check_mail_regex(String mail) {

        Matcher matcher = valid_mail2.matcher(mail);
        return matcher.find();

    }

}
