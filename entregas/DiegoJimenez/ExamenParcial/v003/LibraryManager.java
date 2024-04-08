package v003;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManager {

    private List<Book> books;
    private List<Author> authors;
    private List<BookAuthor> authorrelations;
    private List<KeyWord> keywords;
    private List<BookKeyWord> keywordrelations;
    private Scanner scanner;

    public LibraryManager() {
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.authorrelations = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.keywordrelations = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void startLibraryManager() {
        System.out.println("Bienvenido al Gestor de Biblioteca");
        boolean isWorking = true;
        while (isWorking) {
            System.out.println("Elige una opción:");
            System.out.println("1. Agregar un nuevo libro");
            System.out.println("2. Listar libros");
            System.out.println("3. Listar autores");
            System.out.println("4. Listar palabras clave");
            System.out.println("6. Editar un libro");
            System.out.println("9. Salir");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    listBooks();
                    break;
                case "3":
                    listAuthors();
                    break;
                case "4":
                    listKeyWords();
                    break;
                case "6":
                    editBook();
                    break;
                case "9":
                    System.out.println("Saliendo del gestor de biblioteca...");
                    isWorking = !isWorking;
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
                    break;
            }
        }
    }

    private void listBooks() {
        System.out.println("Libros en la biblioteca:");
        if (books.isEmpty()) {
            System.out.println("> No hay libros en la biblioteca.");
        } else {
            for (Book book : books) {
                System.out.println(book);
                System.out.println(getAuthorsByBookId(book.getId()));
                System.out.println(getKeyWordsByBookId(book.getId()));
            }
        }
    }

    private void addBook() {
        System.out.println("Introduce el título del libro:");
        String title = scanner.nextLine();
        System.out.println("Introduce el año de publicación:");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Introduce el tipo de libro:");
        String type = scanner.nextLine();

        Book newBook = new Book(books.size() + 1, title, year, type);
        addBook(newBook);
    }

    private void addBook(Book book) {
        books.add(book);
        System.out.println("Libro añadido. ¿Deseas añadir autores a este libro? (s/n)");
        String response = scanner.nextLine();
        if ("s".equalsIgnoreCase(response)) {
            addAuthor(book);
        }
        System.out.println("¿Deseas añadir palabras clave a este libro? (s/n)");
        response = scanner.nextLine();
        if ("s".equalsIgnoreCase(response)) {
            addKeyWord(book);
        }
    }

    private void addAuthor(Book book) {
        boolean addingAuthors = true;
        while (addingAuthors) {
            System.out.println(
                    "Selecciona el ID del autor para asociar con el libro, o introduce 'nuevo' para añadir un nuevo autor:");
            listAuthors();
            String input = scanner.nextLine();
            if ("nuevo".equalsIgnoreCase(input)) {
                System.out.println("Introduce el nombre del nuevo autor:");
                String name = scanner.nextLine();
                Author newAuthor = new Author(authors.size() + 1, name);
                addAuthor(newAuthor);
                addAuthorRelation(book.getId(), newAuthor.getId());
                System.out.println("Autor nuevo añadido y asociado al libro.");
            } else {
                try {
                    int authorId = Integer.parseInt(input);
                    addAuthorRelation(book.getId(), authorId);
                    System.out.println("Autor asociado al libro.");
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no válida.");
                }
            }
            System.out.println("¿Deseas añadir otro autor a este libro? (s/n)");
            if (!"s".equalsIgnoreCase(scanner.nextLine())) {
                addingAuthors = !addingAuthors;
            }
        }
    }

    private void addAuthor(Author author) {
        authors.add(author);
    }

    private void addAuthorRelation(int bookId, int authorId) {
        authorrelations.add(new BookAuthor(bookId, authorId));
    }

    private List<Author> getAuthorsByBookId(int bookId) {
        List<Author> result = new ArrayList<>();
        for (BookAuthor relation : authorrelations) {
            if (relation.getBookId() == bookId) {
                result.add(findAuthorById(relation.getAuthorId()));
            }
        }
        return result;
    }

    private void addKeyWord(Book book) {
        boolean addingKeywords = true;
        while (addingKeywords) {
            System.out.println(
                    "Selecciona el ID de la palabra clave para asociar con el libro, o introduce 'nuevo' para añadir una nueva palabra clave:");
            listKeyWords();
            String input = scanner.nextLine();
            if ("nuevo".equalsIgnoreCase(input)) {
                System.out.println("Introduce el nombre de la nueva palabra clave:");
                String name = scanner.nextLine();
                KeyWord newKeyWord = new KeyWord(keywords.size() + 1, name);
                addKeyWord(newKeyWord);
                addKeyWordRelation(book.getId(), newKeyWord.getId());
                System.out.println("Palabra clave nueva añadida y asociada al libro.");
            } else {
                try {
                    int keywordId = Integer.parseInt(input);
                    addKeyWordRelation(book.getId(), keywordId);
                    System.out.println("Palabra clave asociada al libro.");
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no válida.");
                }
            }
            System.out.println("¿Deseas añadir otra palabra clave a este libro? (s/n)");
            if (!"s".equalsIgnoreCase(scanner.nextLine())) {
                addingKeywords = !addingKeywords;
            }
        }
    }

    public void addKeyWord(KeyWord keyword) {
        keywords.add(keyword);
    }

    private void addKeyWordRelation(int bookId, int keywordId) {
        keywordrelations.add(new BookKeyWord(bookId, keywordId));
    }
    private List<KeyWord> getKeyWordsByBookId(int bookId) {
        List<KeyWord> result = new ArrayList<>();
        for (BookKeyWord relation : keywordrelations) {
            if (relation.getBookId() == bookId) {
                result.add(findKeyWordById(relation.getKeyWordId()));
            }
        }
        return result;
    }

    private List<Book> getBooksByAuthorId(int authorId) {
        List<Book> result = new ArrayList<>();
        for (BookAuthor relation : authorrelations) {
            if (relation.getAuthorId() == authorId) {
                result.add(findBookById(relation.getBookId()));
            }
        }
        return result;
    }

    private Book findBookById(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    private Author findAuthorById(int authorId) {
        for (Author author : authors) {
            if (author.getId() == authorId) {
                return author;
            }
        }
        return null;
    }

    private void listAuthors() {
        if (authors.isEmpty()) {
            System.out.println("> No hay autores disponibles.");
        } else {
            for (Author author : authors) {
                System.out.println("ID: " + author.getId() + ", Autor: " + author.getName());
            }
        }
    }
    private void listKeyWords() {
        if (keywords.isEmpty()) {
            System.out.println("> No hay palabras clave disponibles.");
        } else {
            for (KeyWord keyword : keywords) {
                System.out.println("ID: " + keyword.getId() + ", Palabra clave: " + keyword.getName());
            }
        }
    }

    private KeyWord findKeyWordById(int keyword) {
        for (KeyWord keyWord : keywords) {
            if (keyWord.getId() == keyword) {
                return keyWord;
            }
        }
        return null;
    }


   private void editBook() {
    System.out.println("Editar un libro");
    listBooks();

    System.out.println("Selecciona el ID del libro que deseas editar:");
    int bookId = scanner.nextInt();
    scanner.nextLine(); 

    Book bookToEdit = findBookById(bookId);
    if (bookToEdit == null) {
        System.out.println("El libro no existe.");
        return;
    }

    System.out.println("¿Qué deseas editar?");
    System.out.println("1. Título");
    System.out.println("2. Año de publicación");
    System.out.println("3. Tipo de libro");
    System.out.println("4. Autores");
    System.out.println("5. Palabras clave");

    int choice = scanner.nextInt();
    scanner.nextLine(); 

    switch (choice) {
        case 1:
            editTitle(bookToEdit);
            break;
        case 2:
            editYear(bookToEdit);
            break;
        case 3:
            editType(bookToEdit);
            break;
        case 4:
            editAuthorsForBook(bookToEdit);
            break;
        case 5:
            editKeywordsForBook(bookToEdit);
            break;
        default:
            System.out.println("Opción no válida.");
            break;
    }

    System.out.println("Libro editado correctamente.");
}

private void editTitle(Book book) {
    System.out.println("Introduce el nuevo título del libro:");
    String newTitle = scanner.nextLine();
    book.setTitle(newTitle);
}

private void editYear(Book book) {
    System.out.println("Introduce el nuevo año de publicación:");
    int newYear = scanner.nextInt();
    scanner.nextLine(); 
    book.setPublicationYear(newYear);
}



private void editType(Book book) {
    System.out.println("Introduce el nuevo tipo de libro:");
    String newType = scanner.nextLine();
    book.setType(newType);
}

private void editAuthorsForBook(Book book) {
    System.out.println("Editar autores para el libro: " + book.getTitle());
    listAuthors();

    System.out.println("Introduce el ID del autor que deseas editar o 'nuevo' para crear un nuevo autor:");
    String input = scanner.nextLine();

    if ("nuevo".equalsIgnoreCase(input)) {
        System.out.println("Introduce el nombre del nuevo autor:");
        String authorName = scanner.nextLine();
        Author newAuthor = new Author(authors.size() + 1, authorName);
        addAuthor(newAuthor);
        addAuthorRelation(book.getId(), newAuthor.getId());
        System.out.println("Autor nuevo añadido y asociado al libro.");
    } else {
        try {
            int authorId = Integer.parseInt(input);
            Author authorToEdit = findAuthorById(authorId);
            if (authorToEdit == null) {
                System.out.println("El autor con ID " + authorId + " no existe.");
                return;
            }
            System.out.println("Introduce el nuevo nombre del autor:");
            String newAuthorName = scanner.nextLine();
            authorToEdit.setName(newAuthorName);
            System.out.println("Autor editado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
        }
    }
}
private void editKeywordsForBook(Book book) {
    System.out.println("Editar palabras clave para el libro: " + book.getTitle());
    listKeyWords();

    System.out.println("Introduce el ID de la palabra clave que deseas añadir o editar:");
    int keywordId = scanner.nextInt();
    scanner.nextLine(); 

    KeyWord keyword = findKeyWordById(keywordId);
    if (keyword == null) {
        System.out.println("La palabra clave no existe. Se creará una nueva.");
        System.out.println("Introduce el nombre de la nueva palabra clave:");
        String keywordName = scanner.nextLine();
        keyword = new KeyWord(keywords.size() + 1, keywordName);
        addKeyWord(keyword);
    }

    addKeyWordRelation(book.getId(), keyword.getId());
    System.out.println("Palabra clave asociada al libro.");
}



}