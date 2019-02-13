package x_o_game;

import java.util.Scanner;

/**
 *
 * @author amr
 */
public class Batch1_2 {

    public static void main(String[] args) {
        Crud c = new Crud();
        SignUp s = new SignUp();
        Operations_db op = new Operations_db();
        boolean check_mail, check_login;

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("Enter your mail :");
            String mail = sc.nextLine();

            if (op.check_mail_regex(mail)) {
                // check_mail = op.check_mail_exist(mail);
                System.out.println(op.check_mail_exist(mail) + " this is check ");
                check_mail = c.select1(mail);
                if (check_mail) {
                    System.out.println("This e-mail exist in DB please Enter another mail : ");
                } else {
                    s.setMail(mail);
                    break;
                }

            } else {
                System.out.println("Please write email right ");
            }

        }
        System.out.println("Enter your password ");
        String pw = sc.nextLine();
        s.setPassword(pw);
        c.insert(s);
        
        while (true) {
            System.out.println("Enter your mail To login ");
            String login_str = sc.nextLine();
            if (op.check_mail_regex(login_str)) {
                System.out.println("Enter your password ");
                String pass_login = sc.nextLine();
                boolean check_both = c.select2(login_str, pass_login);
                if (check_both) {

                    System.out.println("You logged in , Welcom  " + login_str);
                    break;
                } else {
                    System.out.println("The mail you Entered is not exist or passwd wrong  ");
                }

            } else {
                System.out.println("Please write email right ");
            }

        }
    }

}
