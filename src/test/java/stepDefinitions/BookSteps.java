package stepDefinitions;


import com.example.librarymanagementrestapi.Repository.BookRepository;
import com.example.librarymanagementrestapi.model.BookModel;
import com.example.librarymanagementrestapi.service.BookService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;
import org.junit.jupiter.api.Assertions;


import java.util.ArrayList;
import java.util.List;


public class BookSteps {

     Exception exception;
     BookService bookService;
     BookModel book;
     List<BookModel> books;

    @Before
    public void setup() {
        books = new ArrayList<>();
        BookRepository bookRepository = new BookRepository(books);
        bookService = new BookService(bookRepository);
        exception = null;
    }

    @Given("the library is open")
    public void theLibraryIsOpen() {
        // No action required. The library is always open.
    }

    @Given("the library does not contain a book with title {string}")
    public void libraryDoesNotContainBook(String title){
        book = bookService.findBookByTitle(title);
        Assertions.assertNull(book);
    }

    @When("the user adds a book with title {string}, author {string}, category {string}, and id {int}")
    public void createsBook(String title, String author, String category, int id){
       book = new BookModel();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setAvailable(true);

        try{
            bookService.addBook(book);
        }
        catch (Exception e){
            exception = e;
        }
    }

    @Then("the book with title {string} should be created and saved")
    public void bookShouldBeCreated(String title) {

        book = bookService.findBookByTitle(title);

        Assertions.assertNotNull(book); // Check creation
        Assertions.assertEquals(title, book.getTitle()); // check it is saved
    }

    @Then("the book with title {string} should be available for borrowing")
    public void bookBorrowingAvailability(String title){
        book = bookService.findBookByTitle(title);
        Assertions.assertTrue(book.isAvailable());
    }

    @Given("the book with title {string} exists")
    public void bookExists(String title){
        book = new BookModel();
        book.setId(1);
        book.setTitle(title);
        book.setAuthor("Ahmed Khaled Tawfik");
        book.setCategory("Dystopian");
        book.setAvailable(true);

        bookService.addBook(book);
    }

    @Then("the system should return an error")
    public void systemShouldReturnError() {
        Assertions.assertNotNull(exception);
    }

    @Then("the error message should be {string}")
    public void errorMessageShouldBe(String message) {
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Given("the library contains the books with title {string}, {string}, and {string}")
    public void libraryContainsBooks(String title1, String title2, String title3) {

        bookService.addBook(
                new BookModel(1,title1,"Ahmed Khaled Tawfik", "Dystopian")
        );

        bookService.addBook(
                new BookModel(2,title2,"Ahmed Mourad", "Psychological Thriller")
        );

        bookService.addBook(
                new BookModel(3,title3,"Taha Hussein", "Autobiography")
        );
    }

    @When("the user requests all books")
    public void userRequestsAllBooks() {
        books = bookService.getAllBooks();
    }

    @Then("the system should return the books with titles {string}, {string}, and {string}")
    public void returnsAllBooks(String title1, String title2, String title3){
        Assertions.assertNotNull(books);
        Assertions.assertEquals(3, books.size());
        Assertions.assertTrue(
                books.stream().anyMatch(book -> book.getTitle().equals(title1))
        );

        Assertions.assertTrue(
                books.stream().anyMatch(book -> book.getTitle().equals(title2))
        );

        Assertions.assertTrue(
                books.stream().anyMatch(book -> book.getTitle().equals(title3))
        );
    }

    @When("the user requests the book by title {string}")
    public void userRequestsBookByTitle(String title){
        book = bookService.findBookByTitle(title);
        if (book == null) {
            exception = new IllegalArgumentException("This book with title '" + title + "' does not exist");
        }
    }

    @Then("the system should return the details of the book {string}")
    public void systemReturnsBookDetails(String title) {
        Assertions.assertNotNull(book);
        Assertions.assertEquals(title, book.getTitle());
    }

    @Given("the book with title {string} exists with category {string}")
    public void bookExistWithSameCategory(String title, String category) {
        book = new BookModel();
        book.setId(1);
        book.setTitle(title);
        book.setCategory(category);
        book.setAuthor("Ahmed Khaled Tawfik");
        book.setAvailable(true);

        bookService.addBook(book);
    }


    @When("the user updates the category of the book with title {string} to {string}")
    public void updateBookCategory(String title, String category) {
        book = bookService.findBookByTitle(title);
        if (book == null) {
            exception = new IllegalArgumentException("This book with title '" + title + "' does not exist");
            return;
        }
        book.setCategory(category);
         book = bookService.updateBook(
                book.getId(),
                book
        );
    }


    @Then("the category of the book with title {string} should be updated to {string}")
    public void categoryUpdated(String title, String category) {
        book = bookService.findBookByTitle(title);
        Assertions.assertEquals(
                category,
                book.getCategory()
        );
    }

    @Given("the book with title {string} is not currently borrowed")
    public void nonBorrowedBook(String title){
        book = bookService.findBookByTitle(title);
        Assertions.assertNotNull(book);
        Assertions.assertTrue(book.isAvailable());
    }

    @When("the user deletes the book with title {string}")
    public void deleteBook(String title) {
        book = bookService.findBookByTitle(title);
        if (book == null) {
            exception = new IllegalArgumentException("This book with title '" + title + "' does not exist");
            return;
        }
        Assertions.assertNotNull(book);
        try {
            bookService.deleteBookById(book.getId());
        } catch (Exception e) {
            exception = e;
        }
    }


    @Then("the book with title {string} should be deleted")
    public void bookDeleted(String title) {

        Assertions.assertNull(
                bookService.findBookByTitle(title)
        );
    }


    @Given("the book with title {string} is currently borrowed")
    public void bookIsBorrowed(String title) {
        book = bookService.findBookByTitle(title);
        Assertions.assertNotNull(book);
        book.setAvailable(false);
        bookService.updateBook(book.getId(), book);
    }

}

