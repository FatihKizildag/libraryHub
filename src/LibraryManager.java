import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LibraryManager {
    private static LibraryManager instance;
    private List<Observer> observers;
    private Map<Integer, Book> availableBooks;
    private Map<Integer, Book> unavailableBooks;
    private Map<Integer, String> rentedBookInfo; // Book ID -> Rental Information
    private int nextBookId = 1;

    private LibraryManager() {
        this.observers = new ArrayList<>();
        this.availableBooks = new HashMap<>();
        this.unavailableBooks = new HashMap<>();
        this.rentedBookInfo = new HashMap<>();
        initializeBooks();  // Initialize Books
    }

    public static LibraryManager getInstance() {
        if (instance == null) {
            instance = new LibraryManager();
        }
        return instance;
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void checkoutResource(int bookId, User user, int daysToReturn) {
        if (availableBooks.containsKey(bookId)) {
            Book requestedBook = availableBooks.get(bookId);
            new NormalCheckoutStrategy().checkoutResource(requestedBook, user, daysToReturn);
            availableBooks.remove(bookId);
            requestedBook.setDueDays(daysToReturn);
            unavailableBooks.put(bookId, requestedBook);
            rentedBookInfo.put(bookId, user.getUsername() + " - " + getCurrentDate());
            String dueDate = calculateDueDate(getCurrentDate(), daysToReturn);
            notifyObservers(user , user.getUsername()+ " checked out " + requestedBook.getTitle() +
                    " for " + daysToReturn + " days. Due date is : " + dueDate);
        } else {
            Book requestedBook = unavailableBooks.get(bookId);
            notifyObservers(user, "Error: "+requestedBook.getTitleById(bookId)+" is not available or already checked.");
        }
    }

    public void extendDueDate(int bookId, User user, int additionalDays) {
        if (unavailableBooks.containsKey(bookId) && rentedBookInfo.containsKey(bookId)) {
            Book rentedBook = unavailableBooks.get(bookId);
            int currentDueDays = rentedBook.getDueDays();
            rentedBook.setDueDays(currentDueDays + additionalDays);
            new ExtendCheckoutStrategy().checkoutResource(rentedBook, user, rentedBook.getDueDays());
            // update the rentedBookInfo map
            String userInfo = rentedBookInfo.get(bookId);
            userInfo = user.getUsername() + " - " + getCurrentDate();
            rentedBookInfo.put(bookId, userInfo);

            String dueDate = calculateDueDate(getCurrentDate(), rentedBook.getDueDays());

            notifyObservers(user ,user.getUsername()+ " extended the due date for " + rentedBook.getTitle() +
                    " by " + rentedBook.getDueDays() + " days. New due date for : " + dueDate);
        } else {
            Book rentedBook = availableBooks.get(bookId);
            notifyObservers(user,"Error: " + rentedBook.getTitleById(bookId) + " is not checked out.");
        }
    }


    public void returnResource(int bookId, User user) {
        if (unavailableBooks.containsKey(bookId) && rentedBookInfo.containsKey(bookId)) {
            Book returnedBook = unavailableBooks.get(bookId);

            new ReturnCheckoutStrategy().checkoutResource(returnedBook, user, returnedBook.getDueDays());

            returnedBook.setDueDays(0);
            availableBooks.put(bookId, returnedBook);
            unavailableBooks.remove(bookId);
            rentedBookInfo.remove(bookId);
            String bookTitle = returnedBook.getTitleById(bookId);
            notifyObservers(user.getUsername() + " returned the " + returnedBook.getTitle() + ". This book is available to checkout now.");
        } else {
            Book returnedBook = availableBooks.get(bookId);
            notifyObservers(user,"Error: " + returnedBook.getTitleById(bookId) + " is not checked out.");
        }
    }

    public List<Book> getAvailableBooks() {
        return new ArrayList<>(availableBooks.values());
    }

    public List<Book> getUnavailableBooks() {
        return new ArrayList<>(unavailableBooks.values());
    }

    public Map<Integer, String> getRentedBookInfo() {
        return new HashMap<>(rentedBookInfo);
    }

    private void notifyObservers(Observer observer, String message) {
            observer.update(message);

    }
    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    // initialize Books list
    private void initializeBooks() {
        addBook("Design Patterns", "Gang of Four");
        addBook("Clean Code", "Robert C. Martin");
        addBook(" Mathematical Thinking", "Jordan Ellenberg");
        addBook("A Guided Tour of Math", "Steven Strogatz");
        addBook("Companion to Mathematics", "Timothy Gowers");
        addBook("Introduction to Algorithms", "Thomas H. Cormen");
    }

    // Method for adding books
    public void addBook(String title, String author) {
        Book newBook = new Book(nextBookId, title, author);
        availableBooks.put(nextBookId, newBook);
        nextBookId++;
        notifyObservers(title+author+" is added on list.");
    }

    private LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    private String calculateDueDate(LocalDate currentDate, int daysToReturn) {
        // Calculate the borrowing or extension date by adding days to the current date
        LocalDate dueDate = currentDate.plusDays(daysToReturn);
        return dueDate.toString();
    }
}