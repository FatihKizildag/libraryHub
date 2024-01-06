import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

class LibraryGUI extends JFrame {
    private JButton checkoutButton;
    private JButton extendButton;
    private JButton returnButton;
    private JTextField usernameField;
    private JTextField bookIdField;
    private JTextField daysToReturnField;
    private JTextArea availableBooksArea;
    private JTextArea unavailableBooksArea;
    private JTextArea rentedBooksArea;

    public LibraryGUI() {
        super("Library Management System");

        checkoutButton = new JButton("Checkout Book");
        extendButton = new JButton("Extend Due Date");
        returnButton = new JButton("Return Book");
        usernameField = new JTextField(10);
        bookIdField = new JTextField(10);
        daysToReturnField = new JTextField(10);

        availableBooksArea = new JTextArea(5, 10);
        unavailableBooksArea = new JTextArea(5, 10);
        rentedBooksArea = new JTextArea(5, 10);
        availableBooksArea.setEditable(false);
        unavailableBooksArea.setEditable(false);
        rentedBooksArea.setEditable(false);

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkoutBook();
            }
        });

        extendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extendDueDate();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        updateBookLists();

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username: "));
        panel.add(usernameField);
        panel.add(new JLabel("Book ID: "));
        panel.add(bookIdField);
        panel.add(new JLabel("Days to Return: "));
        panel.add(daysToReturnField);
        panel.add(checkoutButton);
        panel.add(extendButton);
        panel.add(returnButton);

        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new GridLayout(2, 3));

        bookPanel.add(new JLabel("Available Books:"));
        bookPanel.add(new JLabel("Unavailable Books:"));
        bookPanel.add(new JLabel("Rented Books Info:"));

        bookPanel.add(new JScrollPane(availableBooksArea));
        bookPanel.add(new JScrollPane(unavailableBooksArea));
        bookPanel.add(new JScrollPane(rentedBooksArea));

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);
        container.add(new JScrollPane(bookPanel), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void checkoutBook() {
        String username = usernameField.getText();
        int bookId = Integer.parseInt(bookIdField.getText());
        int daysToReturn = Integer.parseInt(daysToReturnField.getText());

        LibraryManager libraryManager = LibraryManager.getInstance();
        User user = new User(username);
        libraryManager.addObserver(user);


        libraryManager.checkoutResource(bookId, user, daysToReturn);

        // Show updated book list and rented book information
        updateBookLists();
        updateRentedBooksInfo();

    }

    private void extendDueDate() {
        String username = usernameField.getText();
        int bookId = Integer.parseInt(bookIdField.getText());
        int additionalDays = Integer.parseInt(daysToReturnField.getText());

        LibraryManager libraryManager = LibraryManager.getInstance();
        User user = new User(username);

        libraryManager.extendDueDate(bookId, user, additionalDays);

        // Show updated rented book information
        updateBookLists();
        updateRentedBooksInfo();
    }

    private void returnBook() {
        String username = usernameField.getText();
        int bookId = Integer.parseInt(bookIdField.getText());

        LibraryManager libraryManager = LibraryManager.getInstance();
        User user = new User(username);
        libraryManager.removeObserver(user);

        libraryManager.returnResource(bookId, user);

        // Show updated book list and rented book information
        updateBookLists();
        updateRentedBooksInfo();
    }

    private void updateBookLists() {
        LibraryManager libraryManager = LibraryManager.getInstance();
        java.util.List<Book> availableBooks = libraryManager.getAvailableBooks();
        List<Book> unavailableBooks = libraryManager.getUnavailableBooks();

        StringBuilder availableBookList = new StringBuilder();
        for (Book book : availableBooks) {
            availableBookList.append("ID: ").append(book.getId()).append(", Title: ").append(book.getTitle()).append(", Author: ").append(book.getAuthor()).append("\n");
        }
        availableBooksArea.setText(availableBookList.toString());

        StringBuilder unavailableBookList = new StringBuilder();
        for (Book book : unavailableBooks) {
            unavailableBookList.append("ID: ").append(book.getId()).append(", Title: ").append(book.getTitle()).append(", Author: ").append(book.getAuthor()).append(", Due Days: ").append(book.getDueDays()).append("\n");
        }
        unavailableBooksArea.setText(unavailableBookList.toString());
    }

    private void updateRentedBooksInfo() {
        LibraryManager libraryManager = LibraryManager.getInstance();
        Map<Integer, String> rentedBookInfo = libraryManager.getRentedBookInfo();

        StringBuilder rentedBooksInfo = new StringBuilder();
        for (Map.Entry<Integer, String> entry : rentedBookInfo.entrySet()) {
            rentedBooksInfo.append("ID: ").append(entry.getKey()).append(", Info : ").append(entry.getValue()).append("\n");
        }
        rentedBooksArea.setText(rentedBooksInfo.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryGUI::new);
    }
}