import java.util.ArrayList;
import java.util.Scanner;

// Base class for library items 
class Item {
    private String title;
    private int publicationYear;

    public Item(String title, int publicationYear) {
        this.title = title;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
}

// Interface for borrowable items
interface IBorrowable {
    void borrowItem(String borrower);
    void returnItem();
    boolean isBorrowed();
}

// Book class
class Book extends Item implements IBorrowable {
    private String author;
    private String ISBN;
    private String borrower;

    public Book(String title, int publicationYear, String author, String ISBN) {
        super(title, publicationYear);
        this.author = author;
        this.ISBN = ISBN;
        this.borrower = null;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void borrowItem(String borrower) {
        if (this.borrower == null) {
            this.borrower = borrower;
            System.out.println("Book borrowed by " + borrower);
        } else {
            System.out.println("Book is already borrowed.");
        }
    }

    public void returnItem() {
        if (this.borrower != null) {
            System.out.println("Book returned by " + borrower);
            this.borrower = null;
        } else {
            System.out.println("Book is not borrowed.");
        }
    }

    public boolean isBorrowed() {
        return borrower != null;
    }
}

// Magazine class
class Magazine extends Item {
    private int issueNumber;

    public Magazine(String title, int publicationYear, int issueNumber) {
        super(title, publicationYear);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }
}

// Singleton Library
class Library {
    private static Library instance = null;
    private ArrayList<Item> items;

    private Library() {
        items = new ArrayList<>();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void listAvailableItems() {
        System.out.println("\nAvailable Items:");
        for (Item item : items) {
            if (item instanceof Book) {
                Book b = (Book) item;
                if (!b.isBorrowed()) {
                    System.out.println("Book: " + b.getTitle() + " by " + b.getAuthor());
                }
            } else if (item instanceof Magazine) {
                Magazine m = (Magazine) item;
                System.out.println("Magazine: " + m.getTitle() + ", Issue #" + m.getIssueNumber());
            }
        }
    }

    public Item findItemByTitle(String title) {
        for (Item item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return item;
            }
        }
        return null;
    }
}

// Factory class
class LibraryItemFactory {
    public static Item createItem(String type, String title, int year, String extra) {
        if (type.equalsIgnoreCase("book")) {
            return new Book(title, year, extra, "ISBN123");
        } else if (type.equalsIgnoreCase("magazine")) {
            int issue = Integer.parseInt(extra);
            return new Magazine(title, year, issue);
        }
        return null;
    }
}

// Main application
public class LibraryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = Library.getInstance();

        // Books in library initially
        Item book1 = LibraryItemFactory.createItem("book", "Harry Potter", 1997, "J.K. Rowling");
        Item book2 = LibraryItemFactory.createItem("book", "1984", 1949, "George Orwell");
        Item magazine = LibraryItemFactory.createItem("magazine", "Christophorus (Porsche Magazine)", 2025, "7");

        library.addItem(book1);
        library.addItem(book2);
        library.addItem(magazine);

        boolean running = true;

        System.out.println("Welcome to the Library App!");

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. List available items");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. Search by title");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    library.listAvailableItems();
                    break;
                case "2":
                    System.out.print("Enter the title of the book to borrow: ");
                    String borrowTitle = scanner.nextLine();
                    Item itemToBorrow = library.findItemByTitle(borrowTitle);
                    if (itemToBorrow instanceof Book) {
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        ((Book) itemToBorrow).borrowItem(name);
                    } else {
                        System.out.println("Book not found or not borrowable.");
                    }
                    break;
                case "3":
                    System.out.print("Enter the title of the book to return: ");
                    String returnTitle = scanner.nextLine();
                    Item itemToReturn = library.findItemByTitle(returnTitle);
                    if (itemToReturn instanceof Book) {
                        ((Book) itemToReturn).returnItem();
                    } else {
                        System.out.println("Book not found or not borrowable.");
                    }
                    break;
                case "4":
                    System.out.print("Enter the title to search for: ");
                    String searchTitle = scanner.nextLine();
                    Item found = library.findItemByTitle(searchTitle);
                    if (found != null) {
                        System.out.println("Item found: " + found.getTitle() + " (" + found.getPublicationYear() + ")");
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case "5":
                    running = false;
                    System.out.println("Have a nice day!");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }

        scanner.close();
    }
}
