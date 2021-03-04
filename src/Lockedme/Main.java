package Lockedme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    final static String Root = "C:\\Users\\mohamed\\Desktop\\App LockedMe\\Root" ;


    public static void main(String[] args) {
        welcome();
        Menu();
    }

    // Application name and developer details
    public static void welcome() {
        System.out.println("Welcome to LockedMe");
        System.out.println("Developer: Mohamed Zagre");
        System.out.println("Contact zagremohamedcheik@gmail.com");
        System.out.println("------------------");
    }

    // Displaying App menu

    public static void Menu() {
        System.out.println("What task would you like to execute?\n");
        System.out.println("1 Listing files name in an ascending order");
        System.out.println("2 Business-level operations");
        System.out.println("3 Or Exit App");
        System.out.println("\nSelect the appropriate number relate to your Choice:");

        String Choice = scanner.nextLine();

        switch (Choice) {
            case "1":
                listFiles();
                break;
            case "2":
                operations();
                break;
            case "3":
                System.out.println("See you soon!");
                System.exit(0);
            default:
                System.out.println("Oops Please try again!");
                Menu();
                break;
        }

    }

    //  Method displaying user interface

    public static void operations() {
        System.out.println("Choose one of the option below\n");
        System.out.println("1 Add File");
        System.out.println("2 Delete a File");
        System.out.println("3 Search for a file or directory");
        System.out.println("4 Return to main menu");
        String operationsSelection = scanner.nextLine();
        switch (operationsSelection) {
            case "1":
                System.out.println("Let's get the file path");
                String addFilePath = scanner.nextLine();
                addFile(addFilePath);
                break;
            case "2":
                System.out.println("Provide a file name");
                String deleteFileName = scanner.nextLine();
                deleteFile(deleteFileName);
                break;
            case "3":
                System.out.println("Please enter file name");
                String searchFileName = scanner.nextLine();
                searchFiles(searchFileName);
                break;
            case "4":
                Menu();
                break;
            default:
                System.out.println("Please try again");
                operations();
                break;
        }

    }


    // Sorting algorithm for returning current file names in ascending order.

    public static Set<String> getFiles() {
        File[] f = new File(Root).listFiles();

        Set<String> sorted = new TreeSet<>();


        for (File file : f) {
            sorted.add(file.getName());
        }
        return (sorted);
    }


    // Listing files

    public static void listFiles()  {
        System.out.println(" files in ascending order:\n");
        getFiles().forEach(System.out::println);
        System.out.println("------------------");
        Menu();
    }

    // Adding a file to the existing directory list
    //ignoring the case sensitivity of the file names

    public static void addFile(String filePath) {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println("Sorry we couldn't find the file");
        }

        String newFilePath = Root + "/" + path.getFileName();
        int inc = 0;
        while (Files.exists(Paths.get(newFilePath))) {
            inc++;
            newFilePath = Root + "/" + inc + "_" + path.getFileName();
        } try {
            Files.copy(path, Paths.get(newFilePath));
            System.out.println("Copied to: " + newFilePath);
        } catch(IOException e) {
            System.out.println("copying file to " + newFilePath + " was unsuccessful");
        }
        System.out.println("------------------");
        operations();
    }

    //  Search Logic for matching existing files

    public static boolean[] search(String fileName) {
        final boolean[] exists = {false};

        getFiles().forEach((x) -> {
            if (x.toLowerCase(Locale.ROOT).equals(fileName.toLowerCase(Locale.ROOT))) {
                exists[0] = true;
            }
        });
        return (exists);
    }
    // Delete a user specified file from the existing directory list

    public static void deleteFile(String fileName) {
        final boolean[] exists = search(fileName);

        File file = new File(Root + fileName );

        if (exists[0]) {
            file.delete();
            System.out.println("File Deleted " +fileName);
        } else {
            System.out.println(fileName + " doesn't exist");
        }
        operations();
    }
    // Search a user specified file from the main directorySearch
    // a user specified file from the main directory

    public static void searchFiles(String fileName) {
        final boolean[] exists = search(fileName);
        if (exists[0]) {
            System.out.println(fileName + " exists");
        } else {
            System.out.println(fileName + " doesn't exist");
        }
        operations();
    }

}
