package com.anurags.autocomplete;

import java.util.*;


public class Main {
  private static String prompt() {
    Scanner sc = new Scanner(System.in);
    System.out.printf("Choose an option:\n  1. Search\n  2. Exit\nEnter your choice: ");
    int key = sc.nextInt();
    switch (key) {
      case 1:
        System.out.print("Enter the prefix to search: ");
        String prefix = sc.next();
        return prefix;
      case 2:
        System.out.println("Exiting...");
        return null;
      default:
        System.out.println("Invalid choice. Exiting...");
        return null;
    }
  }
  public static void main(String[] args) {
    AutoComplete ac = new AutoComplete();
    String fileName = "rockyou.txt";
    ac.feedFile(fileName);

    Scanner sc = new Scanner(System.in);
    while (true) {
      String prefix = prompt();
      if (prefix == null || prefix.isEmpty()){
        break;
      }
      List<String> result = ac.search(prefix);
      for (int i = 0; i < result.size(); i++) {
        System.out.println((i + 1) + ". " + result.get(i));
      }
      System.out.println("Total words found: " + result.size() + "\n ");
      System.out.print("Enter the accepted word number: ");
      int acceptedWordNumber = sc.nextInt();
      if (acceptedWordNumber >= 1 && acceptedWordNumber <= result.size()) {
        String acceptedWord = result.get(acceptedWordNumber - 1);
        System.out.println("Accepted word: " + acceptedWord);
        ac.acceptSuggestion(acceptedWord);
      } else {
        System.out.println("Invalid word number. Exiting...");
        break;
      }
    }
    sc.close();
  }
}

