import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LibraryManagementMain {
    public static void main(String[] args) {
        System.out.println("Welcome to Library Managemnet System");
        Library library1=new Library();
        
        Library.addBook();
        Library.addMember();
        Library.checkOutBook();
        Library.checkInBook();

        Library.allBooks();
        Library.allMembers();
        Library.allCheckoutBooks();
        Library.allOverDueBooks();
    }
}
