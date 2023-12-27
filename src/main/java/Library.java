import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Library {
    static Map<String,Book> bookDB;
    static Map<String,Member>memberDB;
    static HashSet<String> availableBooks;
    static Map<String, HashSet<String>>booksTakenByMembers;
    static Map<String,Loan>loanAssWithBook;

    static Scanner input=new Scanner(System.in);

    Library(){
        bookDB=new HashMap<>();
        memberDB=new HashMap<>();
        availableBooks=new HashSet<>();
        booksTakenByMembers=new HashMap<>();
        loanAssWithBook=new HashMap<>();
        Book newBook=new Book("Bob in Wonderaland","Alice","Mans","Fiction",120);
        Book newBook1=new Book("Alice in wonderland","Bob","Manchos","Fiction",125);

        availableBooks.add("Bob in Wonderaland");
        bookDB.put("Bob in Wonderaland",newBook);

        availableBooks.add("Alice in Wonderaland");
        bookDB.put("Alice in Wonderaland",newBook1);

        Member newMember=new Member("Rohit","Aditpur","8102051085","wwsanj@gmail.com");
        Member newMember1=new Member("Sam","Adit","8102051083","w1sanj@gmail.com");
    }

    public static void addBook(){
        System.out.println("Enter the Book Details");
        System.out.println("Enter the title of Book :");
        String title=input.nextLine();
        System.out.println("Enter the Author of Book :");
        String author=input.nextLine();
        System.out.println("Enter the Publisher of Book :");
        String publisher=input.nextLine();
        System.out.println("Enter the Genre of Book :");
        String genre=input.nextLine();
        System.out.println("Enter the Number of Pages of Book :");
        int pages=input.nextInt();
        input.nextLine();
        Book newBook=new Book(title,author,publisher,genre,pages);
        availableBooks.add(title);
        bookDB.put(title,newBook);
    }

    public static void addMember(){
        System.out.println("Enter the details of Member");

        System.out.println("Enter your Name :");
        String name=input.nextLine();
        System.out.println("Enter your Address :");
        String address=input.nextLine();
        System.out.println("Enter your PhoneNumber :");
        String phoneNumber=input.nextLine();
        System.out.println("Enter your Email:");
        String email=input.nextLine();

        Member newMember=new Member(name,address,phoneNumber,email);
        memberDB.put(name,newMember);
    }

    public static void overDueForMember(String name){
        HashSet<String>loanDetails=booksTakenByMembers.get(name);
        LocalDate today = LocalDate.now();
        int price=0;
        for(String title:loanDetails){
            Loan loan=loanAssWithBook.get(title);
            if(loan.dueDate.compareTo(today)<0){
                long daysDifference= ChronoUnit.DAYS.between(today,loan.dueDate);
                price+=daysDifference*10;
            }
        }

        if(price>0)System.out.println(price + "needed to be paid by "+name);
        else System.out.println("No OverDues for "+name);
    }

    public static void checkOutBook(){
        System.out.println("Welcome to checkout Book Page");
        System.out.println("Enter your Name :");
        String name=input.nextLine();

        if(!memberDB.containsKey(name)){
            System.out.println("Please Complete the Register First");
            return ;
        }

        System.out.println("Enter the title of Book :");
        String title=input.nextLine();

        if(!availableBooks.contains(title)){
            System.out.println("Currently "+title+" is Not available");
            return ;
        }

        availableBooks.remove(title);
        LocalDate today = LocalDate.now();
        int days=7;
        LocalDate dueDate=today.plusDays(days);

        Loan newLoan=new Loan(bookDB.get(title),memberDB.get(name),dueDate);
        loanAssWithBook.put(title,newLoan );

        if(booksTakenByMembers.containsKey(name)){
            booksTakenByMembers.get(name).add(title);
        }else{
            HashSet<String>bookArr=new HashSet<>();
            bookArr.add(title);
            booksTakenByMembers.put(name,bookArr);
        }

        System.out.println(title+" Book Checkout Successfully to "+name);
    }

    public static void checkInBook(){
        System.out.println("welcome to check in book page");

        System.out.println("Enter your Name :");
        String name=input.nextLine();

        if(!memberDB.containsKey(name) || !booksTakenByMembers.containsKey(name)){
            System.out.println("Please Complete the Register First");
            return ;
        }

        System.out.println("Enter the title of Book you want to return :");
        String title=input.nextLine();

        if(booksTakenByMembers.get(name).contains(title)){
            LocalDate today = LocalDate.now();
            LocalDate dueDate=loanAssWithBook.get(title).dueDate;
            int price=0;
            if(dueDate.compareTo(today)<0){
                long daysDifference= ChronoUnit.DAYS.between(today,dueDate);
                price+=daysDifference*10;
            }

            booksTakenByMembers.get(name).remove(title);
            if(price>0)System.out.println("Pay an amount of+"+price+" as a due.");
            availableBooks.add(title);
            loanAssWithBook.remove(title);
        }else{
            System.out.println("You have'nt borrowed this book");
        }
    }

    public static void allBooks(){
        System.out.println("All Books Present in the library");

        for (String title:bookDB.keySet()){
            System.out.println(title);
        }
    }

    public static void allMembers(){
        System.out.println("All Members Present in the library");

        for (String name:memberDB.keySet()){
            System.out.println(name);
        }
    }

    public static void allCheckoutBooks(){
        System.out.println("All check out Books \n");

        for(String name:booksTakenByMembers.keySet()){
            HashSet<String>loanDetails=booksTakenByMembers.get(name);
            int price=0;
            for(String title:loanDetails){
                System.out.println(title);
            }
        }
    }

    public static void allOverDueBooks(){
        System.out.println("All overDues Books \n");

        for(String name:booksTakenByMembers.keySet()){
            overDueForMember(name);
        }
    }
}
