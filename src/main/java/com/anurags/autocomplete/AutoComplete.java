package com.anurags.autocomplete;

import java.util.*;
import java.io.*;
import com.anurags.autocomplete.interfaces.AutoCompleteInterface;

class AutoComplete implements AutoCompleteInterface {
  TrieNode root;
  AutoComplete(){
    root = new TrieNode();
  }

  public void feedFile(String fileName) {
    InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(fileName);
    if(inputStream == null) {
      System.out.println("File not found");
      return;
    }
    try(BufferedReader br = new BufferedReader(new java.io.InputStreamReader(inputStream))) {
      String line;
      while((line = br.readLine()) != null) {
        insert(line);
      }
      System.out.println("File " + fileName + " has been read successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void printTrie() {
      Queue<TrieNode> q = new LinkedList<>();
      q.add(root);
      while (!q.isEmpty()) {
        TrieNode current = q.poll();
        System.out.println(current.c + " " + current.wordCount + " " + current.wordsAhead);
        for (TrieNode node : current.order) {
        q.add(node);
        }
      }
  }

  public void insert(String word) {
    TrieNode current = root;
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      if (!current.children.containsKey(c)) {
        TrieNode tmp = new TrieNode(c);
        current.children.put(c, tmp);
        current.order.offer(tmp);
      }
      current = current.children.get(c);
      current.wordsAhead++;
    }
    current.wordCount++;
  }

  public List<String> search(String prefix) {
    return search(prefix, 5);
  }
  public List<String> search(String prefix, int count) {
    // only return count number of words with maximum wordCount or wordsAhead

    TrieNode current = root;
    for (int i = 0; i < prefix.length(); i++) {
      char c = prefix.charAt(i);
      if (!current.children.containsKey(c)) {
      return new ArrayList<String>();
      }
      current = current.children.get(c);
    }
    List<String> result = new ArrayList<>();
    dfs(current, prefix, result, count);
    return result;
  }

  private void dfs(TrieNode current, String prefix, List<String> result, int count) {
    if (current.wordCount > 0) {
      result.add(prefix);
      count--;
    }
    if (count == 0) {
      return;
    }
    for (TrieNode node : current.order) {
      dfs(node, prefix + node.c, result, count);
    }
  }

  public void acceptSuggestion(String word) {
    insert(word);
  }
}
