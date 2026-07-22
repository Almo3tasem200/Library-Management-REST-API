package stepDefinitions;

import com.example.librarymanagementrestapi.Repository.BookRepository;
import com.example.librarymanagementrestapi.Repository.BorrowRepository;
import com.example.librarymanagementrestapi.model.BookModel;
import com.example.librarymanagementrestapi.model.BorrowRecord;
import com.example.librarymanagementrestapi.service.BookService;
import com.example.librarymanagementrestapi.service.BorrowService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowSteps {

    Exception exception;
    BookService bookService;
    BorrowService borrowService;
    BookModel book;
    BorrowRecord borrowRecord;
    List<BookModel> books;
    List<BorrowRecord> borrowedBooks;
    BorrowRepository borrowRepository;
    BookRepository bookRepository;

    @Before
    public void setup() {
        books = new ArrayList<>();
        borrowedBooks = new ArrayList<>();
        bookRepository = new BookRepository(books);
        borrowRepository = new BorrowRepository(borrowedBooks);
        bookService = new BookService(bookRepository);
        borrowService = new BorrowService(bookRepository, borrowRepository);
        bookRepository.getAllBooks().clear();
        borrowRepository.getAllBorrowedRecords().clear();
        exception = null;
    }

    @Given("a book with title {string} is available for borrowing")
    public void availableBook(String title) {
        book = bookService.findBookByTitle(title);
        if (book == null) {
            book = new BookModel();
            book.setId(books.size() + 1);
            book.setTitle(title);
            book.setAuthor("Ahmed Khaled Tawfik");
            book.setCategory("Dystopian");
            book.setAvailable(true);
            bookService.addBook(book);
        } else {
            book.setAvailable(true);
        }
    }

    @Given("the user with id {int} has borrowed less than five books")
    public void userHasLessThanFiveBorrowed(int userId) {
        for (int i = 1; i <= 2; i++) {
            BorrowRecord record = new BorrowRecord();

            record.setBookId(i);
            record.setUserId(userId);
            record.setBorrowDate(LocalDate.now());
            record.setDueDate(LocalDate.now().plusDays(14));
            record.setStatus("Borrowed");
            borrowRepository.addBorrowedBook(record);
        }
    }

    @Given("the user with id {int} has less than three overdue books")
    public void userHasLessThanThreeOverdue(int userId) {
        for (int i = 3; i <= 4; i++) {
            BorrowRecord record = new BorrowRecord();

            record.setBookId(i);
            record.setUserId(userId);
            record.setBorrowDate(LocalDate.now().minusDays(30));
            record.setDueDate(LocalDate.now().minusDays(15));
            record.setReturnDate(null);
            record.setStatus("Overdue");

            borrowRepository.addBorrowedBook(record);
        }
    }

    @When("the user with id {int} borrows a book with title {string}")
    public void userBorrowsBook(int userId, String title) {

        book = bookService.findBookByTitle(title);
        try {
            if (book == null) {
                exception = new IllegalArgumentException("This book with title '" + title + "' does not exist");
                return;
            }
            borrowService.borrowBook(book.getId(), userId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the borrowing should succeed")
    public void borrowingShouldSucceed() {
        Assertions.assertNull(exception);
    }

    @Then("the book with title {string} should become unavailable for borrowing")
    public void bookShouldBeUnavailable(String title) {
        book = bookService.findBookByTitle(title);
        Assertions.assertNotNull(book);
        Assertions.assertFalse(book.isAvailable());
    }

    @Given("a book with title {string} is already borrowed")
    public void bookAlreadyBorrowed(String title) {
        book = new BookModel(2, title, "Ahmed Mourad", "Psychological Thriller");
        book.setAvailable(false);
        bookService.addBook(book);
        borrowRecord = new BorrowRecord();
        borrowRecord.setBookId(book.getId());
        borrowRecord.setUserId(1);
        borrowRecord.setBorrowDate(LocalDate.now());
        borrowRecord.setDueDate(LocalDate.now().plusDays(14));
        borrowRecord.setStatus("Borrowed");
        borrowRepository.addBorrowedBook(borrowRecord);
    }

    @Given("the book with title {string} is unavailable for borrowing")
    public void bookIsUnavailable(String title) {
        book = bookService.findBookByTitle(title);
        if (book == null) {
            throw new RuntimeException("Book not found in test");
        }

        book.setAvailable(false);
    }

    @Then("the system should return error")
    public void systemShouldReturnError() {
        Assertions.assertNotNull(exception);
    }

    @Then("error message should be {string}")
    public void errorMessageShouldBe(String message) {
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Given("the user with id {int} has borrowed the book with title {string}")
    public void userHasBorrowedBook(int userId, String title) {
        book = new BookModel(3, title, "Taha Hussein", "Autobiography");
        book.setAvailable(false);
        bookService.addBook(book);
        borrowRecord = new BorrowRecord();
        borrowRecord.setBookId(book.getId());
        borrowRecord.setUserId(userId);
        borrowRecord.setBorrowDate(LocalDate.now());
        borrowRecord.setDueDate(LocalDate.now().plusDays(14));
        borrowRecord.setStatus("Borrowed");
        borrowRepository.addBorrowedBook(borrowRecord);
    }

    @When("the user with id {int} returns the book with title {string}")
    public void userReturnsBook(int userId, String title) {
        book = bookService.findBookByTitle(title);
        try {
            if (book == null) {
                exception = new IllegalArgumentException("the book with title '" + title + "' does not exist");
                return;
            }
            borrowService.returnBook(book.getId(), userId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the borrow status of the book with title {string} should become {string}")
    public void borrowStatusShouldBecome(String title, String status) {
        book = bookService.findBookByTitle(title);
        BorrowRecord record =
                borrowRepository.getAllBorrowedRecords()
                        .stream()
                        .filter(r -> r.getBookId() == book.getId())
                        .findFirst()
                        .orElse(null);

        Assertions.assertNotNull(record);
        Assertions.assertEquals(status, record.getStatus());
    }

    @Then("the book with title {string} should become available for borrowing")
    public void bookShouldBeAvailable(String title) {
        book = bookService.findBookByTitle(title);
        Assertions.assertNotNull(book);
        Assertions.assertTrue(book.isAvailable());
    }

    @Given("the due date of the book with title {string} has passed")
    public void borrowedBookExceededDueDate(String title) {
        book = bookService.findBookByTitle(title);

        borrowRecord = borrowRepository.getAllBorrowedRecords()
                .stream()
                .filter(r -> r.getBookId() == book.getId())
                .findFirst()
                .orElse(null);

        assert borrowRecord != null;
        borrowRecord.setDueDate(LocalDate.now().minusDays(15));
    }

    @Given("the user with id {int} has not returned the book")
    public void userHasNotReturnedBook(int id) {
        borrowRecord.setUserId(id);
        borrowRecord.setReturnDate(null);
    }

    @When("the system checks overdue books")
    public void systemChecksOverdueBooks() {
        borrowService.bookOverdue();
    }

    @Given("the user with id {int} has borrowed five books")
    public void userHasBorrowedFiveBooksById(int userId) {
        List<BookModel> borrowedFiveBooks = List.of(new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Dystopian"
        ), new BookModel(
                2,
                "The Blue Elephant",
                "Ahmed Mourad",
                "Psychological Thriller"
        ), new BookModel(
                3,
                "The Days",
                "Taha Hussein",
                "Autobiography"
        ), new BookModel(
                4,
                "Season of Migration to the North",
                "Taleb Salih",
                "Novel"
        ), new BookModel(
                5,
                "The Cairo Trilogy",
                "Nagib Mahfouz",
                "Historical Fiction"
        ));
        for (BookModel book : borrowedFiveBooks) {
            book.setAvailable(false);
            bookService.addBook(book);
            borrowRecord = new BorrowRecord();
            borrowRecord.setBookId(book.getId());
            borrowRecord.setUserId(userId);
            borrowRecord.setBorrowDate(LocalDate.now());
            borrowRecord.setDueDate(LocalDate.now().plusDays(14));
            borrowRecord.setStatus("Borrowed");
            borrowRecord.setReturnDate(null);
            borrowRepository.addBorrowedBook(borrowRecord);
        }
    }


    @Given("the user with id {int} has three overdue books")
    public void userHasThreeOverdue(int userId) {

        List<BookModel> books = List.of(new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Dystopian"
        ), new BookModel(
                2,
                "The Blue Elephant",
                "Ahmed Mourad",
                "Psychological Thriller"
        ), new BookModel(
                3,
                "The Days",
                "Taha Hussein",
                "Autobiography"
        ));
        for (BookModel book : books) {

            book.setAvailable(false);
            bookService.addBook(book);

            borrowRecord = new BorrowRecord();
            borrowRecord.setBookId(book.getId());
            borrowRecord.setUserId(userId);
            borrowRecord.setBorrowDate(LocalDate.now().minusDays(30));
            borrowRecord.setDueDate(LocalDate.now().minusDays(15));
            borrowRecord.setReturnDate(null);
            borrowRecord.setStatus("Overdue");
            borrowRepository.addBorrowedBook(borrowRecord);
        }

    }

    @Then("the user with id {int} should become blocked")
    public void userBecomeBlocked(int userId) {
        Assertions.assertTrue(borrowService.isUserBlocked(userId));
    }
}
