# Library Management System

This library management system encompasses a Java application that manages the borrowing, returning, and extension of books. It also utilizes the observer design pattern to notify users about these operations.

## Application Structure

- `Book`: Class representing the book object.
- `CheckoutStrategy`: Interface for checkout strategies.
- `ExtendCheckoutStrategy`: Strategy class for extending the borrowing period.
- `LibraryGUI`: Java Swing application creating the user interface.
- `LibraryManager`: Main class managing library operations.
- `NormalCheckoutStrategy`: Strategy class for normal book checkout.
- `Observer`: Interface for the observer design pattern.
- `ReturnCheckoutStrategy`: Strategy class for book return.

## Usage

When you run the application, a simple user interface, the `LibraryGUI` window, opens. You can perform book checkout, return, and due date extension through this window. Additionally, users can receive notifications about these operations.

## How to Run

The project can be compiled and run using a Java IDE (such as Eclipse or IntelliJ IDEA) or through the terminal/command prompt.

## Contribution

If you'd like to contribute to this project, please fork it and send a pull request. You can contribute by adding new features or fixing bugs.

## License

This project is written by Fatih KIZILDAG
