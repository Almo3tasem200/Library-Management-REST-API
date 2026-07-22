Feature: Borrow Management

  Scenario: Borrow an available book successfully
    Given a book with title "Utopia" is available for borrowing
    And the user with id 1 has borrowed less than five books
    And the user with id 1 has less than three overdue books
    When the user with id 1 borrows a book with title "Utopia"
    Then the borrowing should succeed
    And the book with title "Utopia" should become unavailable for borrowing

  Scenario: Fail to borrow an unavailable book
    Given a book with title "The Blue Elephant" is already borrowed
    And the book with title "The Blue Elephant" is unavailable for borrowing
    When the user with id 1 borrows a book with title "The Blue Elephant"
    Then the system should return error
    And error message should be "This book is currently unavailable"


  Scenario: Return a borrowed book successfully
    Given the user with id 1 has borrowed the book with title "Utopia"
    When the user with id 1 returns the book with title "Utopia"
    Then the borrow status of the book with title "Utopia" should become "Returned"
    And the book with title "Utopia" should become available for borrowing

  Scenario: Mark borrowed books as overdue
    Given the user with id 2 has borrowed the book with title "The Days"
    And the due date of the book with title "The Days" has passed
    And the user with id 2 has not returned the book
    When the system checks overdue books
    Then the borrow status of the book with title "The Days" should become "Overdue"


  Scenario: Fail borrowing after reaching maximum borrowed books limit
    Given the user with id 2 has borrowed five books
    And a book with title "Azazel" is available for borrowing
    When the user with id 2 borrows a book with title "Azazel"
    Then the system should return error
    And error message should be "The user cannot borrow more than five books"


  Scenario: Fail borrowing when user has three overdue books
    Given the user with id 3 has three overdue books
    And a book with title "Utopia" is available for borrowing
    When the user with id 3 borrows a book with title "Utopia"
    Then the system should return error
    And error message should be "The user cannot borrow books while having three overdue books"
    And the user with id 3 should become blocked


