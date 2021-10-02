//my earlier progress is evident in my lectorial workspace, I moved to make things easier for tutor

package com.mycompany.app;

//Book class: contains all info that a book should have 
public class Book {

// 5 member variables
private String title;
private String author;
private int pBookLeft;
private String eBookExists;
private String purchasedVersion;

// Constructor which initialises member variables to local variables created 
// when an object of class Book is created    
public Book(String title,String author,int pBookLeft,String eBookExists) {
this.title = title;
this.author = author;
this.pBookLeft = pBookLeft;
this.eBookExists = eBookExists;
this.purchasedVersion = "";
}

// returns Book's title
public String getTitle() {
    return title;
}

// returns Book's author
public String getAuthor() {
    return author;
}

// returns number Physical Books Left of a Book
public int getPBookLeft() {
    return pBookLeft;
}

// returns if the Book has an EBook version or not
public String getEBookExists() {
    return eBookExists;
}

// returns what version of book (physical or electronic) the user 
// purchased to cart
public String getPurchasedVersion() {
    return purchasedVersion;
}

// changes Book's title
public void setTitle(String title) {
    this.title = title;
}

// changes Book's author
public void setAuthor(String author) {
    this.author = author;
}

// changes how many physical books are left 
public void setPBookLeft(int pBookLeft) {
   this.pBookLeft = pBookLeft;
}

// changes if ebook exists or not
public void setEBookExists(String eBookExists) {
    this.eBookExists = eBookExists;
}

// changes what version of book (physical or electronic) user purcharsed to their cart
public void setPurchasedVersion(String purchasedVersion) {
    this.purchasedVersion = purchasedVersion;
}

// method which when printed only prints variables as a string, however its not used
public String toString(){
    return "";
}
 
}