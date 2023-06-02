import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Menu {
    private final   int FIX_SIZE_STRING=20;
    private int last_pos;
    RandomAccessFile last_pos_pfile;
     RandomAccessFile pfile;
    Flight flight = new Flight();
    public Menu() throws FileNotFoundException {
    }
    public void MENUE_OPTIONS() {

        Scanner input = new Scanner(System.in);
        try {
            System.out.println("MENUE OPTIONS \n <1>sign in \n <2>sign up");
            switch (input.nextInt()) {
                case 1:
                    sign_in();
                    break;
                case 2:
                    sign_up();
                    break;
                default:
                    System.out.println("Your select invalid!");
                    MENUE_OPTIONS();
            }
        }
        catch (Exception b)
        {
            System.err.println(b);
            MENUE_OPTIONS();
        }
    }
    public  void sign_in() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter username: ");
            String user = input.nextLine();
            System.out.print("Enter password: ");
            int pass = input.nextInt();
            if (user.equals("Admin") && pass == 1)
            {
               admin_MENUE();
            }

            pfile=new RandomAccessFile("passengerfile.dat","rw");
            last_pos_pfile=new RandomAccessFile("lastpos-pfile.dat","rw");
            last_pos_pfile.seek(0);
            last_pos=last_pos_pfile.read();
            last_pos_pfile.close();
            int i = 0;
            while (i<=last_pos){
                //System.out.println(ary[i]);
                pfile.seek(i);
               // System.out.println("ok");
                if (user.equals(readfixstring(pfile)))
                {
                    //System.out.println(pfile.getFilePointer());
                   // System.out.println("b");
                    pfile.close();
                    System.out.println("Menu passenger");
                }
                i+=48;
            }
            pfile.close();
            System.out.println("this username not founded");
            MENUE_OPTIONS();
        } catch (Exception a) {
            System.err.println(a);
            MENUE_OPTIONS();
        }
    }


    public  void sign_up() throws IOException {
        Scanner input = new Scanner(System.in);
        //System.out.println(last_pos_file.length());
       // System.out.println(ary.length);
        System.out.print("Enter username: ");
        String user = input.nextLine();
        System.out.print("Enter password: ");
        int pass = input.nextInt();
        //System.out.println(aryfile.getFilePointer());
        int i=0;
        last_pos_pfile = new RandomAccessFile("lastpos-pfile.dat","rw");
        pfile=new RandomAccessFile("passengerfile.dat","rw");
        last_pos_pfile.seek(0);
        last_pos=last_pos_pfile.read();
       // System.out.println(last_pos);
       // System.out.println(last_pos_file.length());

            while (i <= last_pos) {
               // System.out.println(last_pos);
                //System.out.println("ok");
                pfile.seek(i);
                if (readfixstring(pfile).equals(user)) {
                    System.out.println("There is a user with this username");
                    sign_up();
                }
                i += 48;
            }

        pfile.seek(i);
        pfile.writeChars(fixstringtowrite(user));
        pfile.write(pass);
        pfile.write(0);
        last_pos_pfile.seek(0);
        last_pos_pfile.write(i);
       // System.out.println(last_pos_file.getFilePointer());
        System.out.println("user added!");
        pfile.close();
        last_pos_pfile.close();
        MENUE_OPTIONS();
        }
    public String fixstringtowrite(String str)
    {
        while (str.length()<FIX_SIZE_STRING)
        {
            str+=" ";
        }
        return str.substring(0,FIX_SIZE_STRING);
    }
    public String readfixstring(RandomAccessFile pfile)throws IOException
    {
        String temp = "";
        for (int i = 0; i < FIX_SIZE_STRING; i++) {
            temp+=pfile.readChar();

        }
        return temp.trim();
    }
    public void admin_MENUE() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("ADMIN MENUE OPTIONS");
        System.out.println("<1>Add \n<2>Update \n<3>Remove \n<4>Flight schedules \n<0>sign out");
        switch (input.nextInt()) {
            case 1: {
                flight.add_flight(input);
                break;
            }
            case 2: {
                flight.update_flight(input);
                break;
            }
            case 3: {
                flight.remove_flight(input);
                break;
            }
            case 4: {
                flight.print_flights();
                admin_MENUE();
                break;
            }
            case 0: {
                MENUE_OPTIONS();
                break;
            }
            default: {
                System.out.println("This number is not available");
                admin_MENUE();
            }
        }
    }
}

