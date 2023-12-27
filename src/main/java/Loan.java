import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Loan {
    Book bookName;
    Member memberName;
    LocalDate dueDate;

    Loan(Book book,Member member,LocalDate dueDate){
        this.bookName=book;
        this.memberName=member;
        this.dueDate=dueDate;
    }

}
