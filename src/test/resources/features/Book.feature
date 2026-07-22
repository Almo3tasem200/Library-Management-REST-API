Feature: Book Management

  Scenario: Create a new book successfully to the library
    Given the library is open
    And the library does not contain a book with title "The Cairo Trilogy"
    When the user adds a book with title "The Cairo Trilogy", author "Nagib Mahfouz", category "Historical Fiction", and id 4
    Then the book with title "The Cairo Trilogy" should be created and saved
    And the book with title "The Cairo Trilogy" should be available for borrowing

  Scenario: Fail to create an existing book
    Given the library is open
    And the book with title "Utopia" exists
    When the user adds a book with title "Utopia", author "Ahmed Khaled Tawfik", category "Dystopian", and id 1
    Then the system should return an error
    And the error message should be "This book with title 'Utopia' already exists"

  Scenario: Get all books successfully
    Given the library is open
    And the library contains the books with title "The Blue Elephant", "Utopia", and "The Days"
    When the user requests all books
    Then the system should return the books with titles "The Blue Elephant", "Utopia", and "The Days"

  Scenario: Get book by title successfully
    Given the library is open
    And the book with title "Utopia" exists
    When the user requests the book by title "Utopia"
    Then the system should return the details of the book "Utopia"

  Scenario: Fail to get a non-existing book by title
    Given the library is open
    And the library does not contain a book with title "Azazel"
    When the user requests the book by title "Azazel"
    Then the system should return an error
    And the error message should be "This book with title 'Azazel' does not exist"

  Scenario: Update book successfully
    Given the library is open
    And the book with title "Utopia" exists with category "Dystopian"
    When the user updates the category of the book with title "Utopia" to "Futuristic Sci-Fi"
    Then the category of the book with title "Utopia" should be updated to "Futuristic Sci-Fi"

  Scenario: Fail to update a non-existing book
    Given the library is open
    And the library does not contain a book with title "Azazel"
    When the user updates the category of the book with title "Azazel" to "Demon"
    Then the system should return an error
    And the error message should be "This book with title 'Azazel' does not exist"

  Scenario: Delete a book successfully
    Given the library is open
    And the book with title "Utopia" exists
    And the book with title "Utopia" is not currently borrowed
    When the user deletes the book with title "Utopia"
    Then the book with title "Utopia" should be deleted

  Scenario: Fail to delete a non-existing book
    Given the library is open
    And the library does not contain a book with title "Azazel"
    When the user deletes the book with title "Azazel"
    Then the system should return an error
    And the error message should be "This book with title 'Azazel' does not exist"

  Scenario: Fail to delete a borrowed book
    Given the library is open
    And the book with title "The Blue Elephant" exists
    And the book with title "The Blue Elephant" is currently borrowed
    When the user deletes the book with title "The Blue Elephant"
    Then the system should return an error
    And the error message should be "This book with title 'The Blue Elephant' is currently borrowed"




