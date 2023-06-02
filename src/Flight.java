import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Flight {
    private final int FIX_SIZE_STRING=20;
    Menu menu;
    public RandomAccessFile ffile;
    public RandomAccessFile last_pos_ffile;
    private int last_pos;

    public Flight() throws FileNotFoundException {
    }

    public void add_flight(Scanner input) throws IOException {
        System.out.print("Enter Id of flight: ");
        String Id = input.nextLine();

        int i=0;
        last_pos_ffile = new RandomAccessFile("last_pos_ffile.dat","rw");
        ffile=new RandomAccessFile("flightfile.dat","rw");
        last_pos_ffile.seek(0);
        last_pos= last_pos_ffile.readInt();
        // System.out.println(last_pos);
        // System.out.println(last_pos_file.length());

        while (i <= last_pos) {
            ffile.seek(i);
            if (readfixstring(ffile).equals(Id)) {
                System.out.println("There is a flight with this Id");
                ffile.close();
                last_pos_ffile.close();
                add_flight(input);
            }
            i += 256;
        }
        ffile.seek(i);
        ffile.writeChars(fixstringtowrite(Id));
        System.out.println("Enter origin of flight: ");
        ffile.writeChars(fixstringtowrite(input.nextLine()));
        System.out.println("Enter Destination of flight: ");
        ffile.writeChars(fixstringtowrite(input.nextLine()));
        System.out.println("Date of flight");
        System.out.println("Enter year of flight: ");
        ffile.writeChars(fixstringtowrite(input.nextLine()));
        System.out.println("Enter month of flight: ");
        ffile.write(input.nextInt());
        System.out.println("Enter day of flight: ");
        ffile.write(input.nextInt());
        System.out.println("Enter hour of flight: ");
        ffile.write(input.nextInt());
        System.out.println("Enter minute of flight: ");
        ffile.write(input.nextInt());
        System.out.println("Enter price of flight: ");
        input.nextLine();
        ffile.writeChars(fixstringtowrite(input.nextLine()));
        System.out.println("Enter seats of flight: ");
        ffile.writeChars(fixstringtowrite(input.nextLine()));
        last_pos_ffile.seek(0);
        last_pos_ffile.writeInt(i);
        last_pos_ffile.seek(0);
        System.out.println(last_pos_ffile.readInt());
        // System.out.println(last_pos_file.getFilePointer());
        System.out.println("flight added!");
        ffile.close();
        last_pos_ffile.close();
        menu.admin_MENUE();
    }
    public void remove_flight(Scanner input) throws IOException {

        System.out.println("Enter flight Id: ");
        String Id = input.nextLine();
        last_pos_ffile = new RandomAccessFile("last_pos_ffile.dat","rw");
        ffile = new RandomAccessFile("flightfile.dat","rw");
        last_pos_ffile.seek(0);
        last_pos = last_pos_ffile.readInt();
        remove_one_flight_and_shift_flights(Id);
        if (remove_one_flight_and_shift_flights(Id)==false)
        {
            System.out.println("This ID is not valid");
            ffile.close();
            last_pos_ffile.close();
            remove_flight(input);
        }
        ffile.close();
        last_pos_ffile.close();
        System.out.println("The flight was removed!");
        menu = new Menu();
        menu.admin_MENUE();
    }
    public boolean remove_one_flight_and_shift_flights(String Id) throws IOException {
        last_pos_ffile = new RandomAccessFile("last_pos_ffile.dat","rw");
        ffile = new RandomAccessFile("flightfile.dat","rw");
        last_pos_ffile.seek(0);
        last_pos = last_pos_ffile.read();
        int i=0,c=0,temp;
        String stemp;
        while (i<=last_pos)
        {
            ffile.seek(i);
            if (readfixstring(ffile).equals(Id))
            {
                while (i<=last_pos) {
                    c = i;
                    i += 256;
                    ffile.seek(i);
                    stemp = readfixstring(ffile);
                    ffile.seek(c);
                    ffile.writeChars(fixstringtowrite(stemp));
                    ffile.seek(i + 40);
                    stemp = readfixstring(ffile);
                    ffile.seek(c + 40);
                    ffile.writeChars(fixstringtowrite(stemp));
                    ffile.seek(i + 80);
                    stemp = readfixstring(ffile);
                    ffile.seek(c + 80);
                    ffile.writeChars(fixstringtowrite(stemp));
                    ffile.seek(i + 120);
                    stemp = readfixstring(ffile);
                    ffile.seek(c + 120);
                    ffile.writeChars(fixstringtowrite(stemp));
                    ffile.seek(i + 124);
                    temp = ffile.read();
                    ffile.seek(c + 124);
                    ffile.write(temp);
                    ffile.seek(i + 128);
                    temp = ffile.read();
                    ffile.seek(c + 128);
                    ffile.write(temp);
                    ffile.seek(i + 132);
                    temp = ffile.read();
                    ffile.seek(c + 132);
                    ffile.write(temp);
                    ffile.seek(i + 136);
                    temp = ffile.read();
                    ffile.seek(c + 136);
                    ffile.write(temp);
                    ffile.seek(i + 176);
                    stemp = readfixstring(ffile);
                    ffile.seek(c + 176);
                    ffile.writeChars(fixstringtowrite(stemp));
                    ffile.seek(i + 216);
                    stemp = readfixstring(ffile);
                    ffile.seek(c + 216);
                    ffile.writeChars(fixstringtowrite(stemp));
                }
                last_pos_ffile.seek(0);
                last_pos_ffile.writeInt(c);
                ffile.close();
                last_pos_ffile.close();
                return true;
            }
            i+=256;
        }
        ffile.close();
        last_pos_ffile.close();
        return false;
    }
    public void update_flight(Scanner input) throws IOException {
        last_pos_ffile = new RandomAccessFile("last_pos_ffile.dat","rw");
        ffile = new RandomAccessFile("flightfile.dat","rw");
        last_pos_ffile.seek(0);
        last_pos = last_pos_ffile.read();
        last_pos_ffile.close();
        System.out.println("Enter ID of flight: ");
        String Id = input.nextLine();
        int i=0;
        while (i<=last_pos)
        {
            ffile.seek(i);
            if (readfixstring(ffile).equals(Id))
            {
                update_fight2(i,input);
                menu.admin_MENUE();
            }
            i+=256;
        }
        System.out.println("This ID invalid");
        ffile.close();
        update_flight(input);
    }
    public void update_fight2(int i,Scanner input) throws IOException {
        System.out.println("which part of flight?\n1.Origin\n2.Destination\n3.Year\n4.Month\n5.Day\n6.Hour\n7.Minute\n8.Price\n9.Seats");
        switch (input.nextInt())
        {
            case 1:
            {
                System.out.println("Enter new origin:");
                ffile.seek(i);
                ffile.writeChars(fixstringtowrite(input.nextLine()));
                System.out.println("filght updated!");
                ffile.close();
                menu = new Menu();
                menu.admin_MENUE();
            }
            case 2:{
                System.out.println("Enter new destination:");
                ffile.seek(i+40);
                ffile.writeChars(fixstringtowrite(input.nextLine()));
                System.out.println("filght updated!");
                ffile.close();
                menu = new Menu();
                menu.admin_MENUE();
            }
            case 3:{
                System.out.println("Enter new year:");
                ffile.seek(i+80);
                ffile.writeChars(fixstringtowrite(input.nextLine()));
                System.out.println("filght updated!");
                ffile.close();
                menu = new Menu();
                menu.admin_MENUE();
            }
            case 4:{
                System.out.println("Enter new month:");
                ffile.seek(i+120);
                ffile.write(input.nextInt());
                System.out.println("filght updated!");
                ffile.close();
                menu = new Menu();
                menu.admin_MENUE();
            }
            case 5:{
                System.out.println("Enter new day:");
                ffile.seek(i+124);
                ffile.write(input.nextInt());
                System.out.println("filght updated!");
                ffile.close();
                menu = new Menu();
                menu.admin_MENUE();
            }
            case 6:{
                System.out.println("Enter new hour:");
                ffile.seek(i+128);
                ffile.write(input.nextInt());
                System.out.println("filght updated!");
                ffile.close();
                menu = new Menu();
                menu.admin_MENUE();
            }
            case 7:{
                System.out.println("Enter new minute:");
                ffile.seek(i+132);
                ffile.write(input.nextInt());
                System.out.println("filght updated!");
                ffile.close();
                menu = new Menu();
                menu.admin_MENUE();
            }
            case 8:{
                System.out.println("Enter new destination:");
                ffile.seek(i+172);
                ffile.writeChars(fixstringtowrite(input.nextLine()));
                System.out.println("filght updated!");
                ffile.close();
                menu = new Menu();
                menu.admin_MENUE();
            }
            case 9:{
                System.out.println("Enter new destination:");
                ffile.seek(i+212);
                ffile.writeChars(fixstringtowrite(input.nextLine()));
                System.out.println("filght updated!");
                ffile.close();
                menu = new Menu();
                menu.admin_MENUE();
            }
            default:
            {
                System.out.println("This number is not available");
                update_fight2(i,input);
            }
        }



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
    public void print_flights() throws IOException {
        int i=0;
        last_pos_ffile = new RandomAccessFile("last_pos_ffile.dat","rw");
        last_pos_ffile.seek(0);
        last_pos=last_pos_ffile.read();
        System.out.println(last_pos);
        ffile = new RandomAccessFile("flightfile.dat","rw");
        while (i<=last_pos)
        {
            ffile.seek(i);
            System.out.println("|FlightId" + "\t" + "|" + "origin" + "\t" + "\t" + "|" + "Destination" + "\t" + "|" + "Date" + "\t" + "\t" + "\t" + "|" + "Time" + "\t" + "\t" + "|" +
                    "Price" + "\t" + "\t" + "\t" + "|" + "Seats");
            System.out.println("|"+readfixstring(ffile) + "\t" + "\t" + "|" + readfixstring(ffile) + "\t" + "\t" + "|" + readfixstring(ffile) + "\t" + "\t" + "\t" + "|" + readfixstring(ffile) + "-" +
                    ffile.read() + "-" + ffile.read() + "\t" + "\t" + "|" + ffile.read() + ":" + ffile.read() + "\t" + "\t" + "|" + readfixstring(ffile) + "\t" + "\t" + "|" + readfixstring(ffile));
            i+=256;
        }
        ffile.close();
        last_pos_ffile.close();
    }

}
