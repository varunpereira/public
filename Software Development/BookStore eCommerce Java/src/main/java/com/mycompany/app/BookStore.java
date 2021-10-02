//my earlier progress is evident in my lectorial workspace, I moved to make things easier for tutor

// 3 unit tests for main function? each function needs at least 2 test cases
// explain code with comments
// make code less messy
// make sure you do all requirements on all spec sheets inc maven, github, canvas etc

package com.mycompany.app;

import java.util.Scanner;

public class BookStore {

// 5 books in database and no more, but can be changed
int bookArrayLength = 5;
// all books that exist are stored in books array
Book[] books = new Book[bookArrayLength]; 
// cart book array has no limit 
int cartBookArrayIndex = -1;
int cartBookArrayLength = 0;
// all books that exist in cart are stored here
Book[] cartBooks = new Book[cartBookArrayLength];
// keeps track of prices of books in cart
double totalPriceCartBooks = 0.00;

// constructor hard codes all the required variables a book needs to have
// (title,author,physical books left, ebook exists)
BookStore(){ 
this.books[0] = new Book("Absolute Java","Savitch",5,"yes");
this.books[1] = new Book("JAVA: How to Program","Deitel and Deitel",0,"yes");
this.books[2] = new Book("Computing Concepts with JAVA 8 Essentials","Horstman",5,"no");
this.books[3] = new Book("Java Software Solutions","Lewis and Loftus",5,"no");
this.books[4] = new Book("Java Program Design","Cohoon and Davidson",1,"yes");
menu();
}

//menu method is constantly called throughout program:
// once a valid or invalid option is complete, or cancel option is chosen
// prints menu option, then based on user's input goes to a specific method
// there is a method for each option 
public void menu() {
    Scanner input = new Scanner(System.in);  
    System.out.println("\nWelcome to Daintree! Choose an option: \n1. Add a book to shopping cart \n2. View shopping cart \n3. Remove a book from shopping cart \n4. Checkout \n5. List all books \n0. Quit \nPlease make a selection:");   
    int chosenOption = input.nextInt();
    if (chosenOption == 1){
        option1();
    }
    else if (chosenOption == 2){
        option2();
    }
    else if (chosenOption == 3){
        option3();
    }
    else if (chosenOption == 4){
        option4();
    }
    else if (chosenOption == 5){
        option5();
    }
    else if (chosenOption == 0){
        option0();
    }
    // for invalid options
    else {
        optionOther();
    }
    // input.close();
}

// option1 method:
// Waits for user to search for a book they want to buy.
// User input is converted to lowercase, and compared with
// every title in databse (books array) which also in lowercase.
// Whatever book titles match with searched book titles, are added to 
// a matched books array, but if there are no matches, an error message is 
// printed and menu method is called. All the matched books are printed.
// Then from the matched books list, the user can choose which book to add to their cart,
// one at a time. Again invalid options give error messages. Canceling option calls menu.
// the user can only make one invalid option at this stage.
// Then user is asked for version of book they want to buy.
// If its an ebook version: If in database, the book is not available as ebook, an error message is
// printed. If it is available as an ebook, it is added to cart book array. 
// If its a physical book version the user wants: If there are none left then error message is printed,
// if there is at least one physical book then it is added to cart book array.
public void option1() {

Scanner input = new Scanner(System.in);
System.out.println("Enter title to search for:");
String searchedTitleCaseSensitive = input.nextLine();
String searchedTitleLowerCase = searchedTitleCaseSensitive.toLowerCase();

Book[] matchedBooks = new Book[bookArrayLength];
int matchedBooksIndex = -1;
boolean matchedBooksExist = false;

for (int i=0; i < bookArrayLength; i+=1 ){
String currentTitleLowerCase = books[i].getTitle().toLowerCase();
if(currentTitleLowerCase.startsWith(searchedTitleLowerCase)){
    matchedBooksExist = true;
    matchedBooksIndex += 1;  
    matchedBooks[matchedBooksIndex] = books[i];
}
// if multiple searched then, only latest will be for currently variable
}

if (matchedBooksExist == false) {
    System.out.println("There is no title starting with/ containing that");
    menu();
}

else if (matchedBooksExist == true) {
System.out.println("The following title/s is a match:"); 
for (int i=0; i < (matchedBooksIndex + 1) && matchedBooks[i].getTitle() != null ; i+=1){
    System.out.println((i+1) + ". " + matchedBooks[i].getTitle() + ", Author: " + matchedBooks[i].getAuthor());
}
System.out.println("0. Cancel");

System.out.println("What is your selection:");

int selection = input.nextInt();

if(selection == 0){
    menu();
}

else if (selection < 0 || selection > (matchedBooksIndex + 1)) {
    System.out.println("Sorry that's an invalid option. Try again");
    selection = input.nextInt();
}
// can't make more more than one invalid or program won't work properly

if(selection == 0){
    menu();
}
else if (selection > 0 && selection <= (matchedBooksIndex + 1)) {
System.out.println("Do you want to buy " + matchedBooks[selection-1].getTitle() + " as an ebook?");

input = new Scanner(System.in);
String eBookVersion = input.nextLine();

if (eBookVersion.equalsIgnoreCase("yes")){
    //want ebook
    if (matchedBooks[selection-1].getEBookExists().equalsIgnoreCase("no")){
        System.out.println("Sorry ebook not available");
    }  
    else if (matchedBooks[selection-1].getEBookExists().equalsIgnoreCase("yes")){
        System.out.println(matchedBooks[selection-1].getTitle() + " has been added to your Cart.");
        this.cartBookArrayLength += 1;
        
        Book[] temp = new Book [this.cartBookArrayLength];
        for (int i = 0; i < this.cartBooks.length; i+=1){
        temp[i] = this.cartBooks[i];
        }
        this.cartBooks = temp;

        this.cartBookArrayIndex += 1;
        this.cartBooks[this.cartBookArrayIndex] = matchedBooks[selection-1]; 

        this.cartBooks[this.cartBookArrayIndex].setPurchasedVersion("eBook");
        this.totalPriceCartBooks += 8.00;
    }
}

else if (eBookVersion.equalsIgnoreCase("no")){
    //want physical book
    if(matchedBooks[selection-1].getPBookLeft() <= 0){
         System.out.println("There are no physical copies of that book available!");
    }
    else if(matchedBooks[selection-1].getPBookLeft() > 0){
        System.out.println(matchedBooks[selection-1].getTitle() + " has been added to your Cart.");
        this.cartBookArrayLength += 1;
        
        Book[] temp = new Book [this.cartBookArrayLength];
        for (int i = 0; i < this.cartBooks.length; i+=1){
        temp[i] = this.cartBooks[i];
        }
        this.cartBooks = temp;      
        this.cartBookArrayIndex += 1;      
        this.cartBooks[this.cartBookArrayIndex] = matchedBooks[selection-1]; 
        this.cartBooks[this.cartBookArrayIndex].setPurchasedVersion("pBook");
        this.totalPriceCartBooks += 50.00; 
        this.cartBooks[this.cartBookArrayIndex].setPBookLeft(this.cartBooks[this.cartBookArrayIndex].getPBookLeft() - 1);     
    }
}
menu();
}
}
}

// option2 method:
// Prints cart message.
// Then prints all required info about a book, and prints all books in user's cart.
public void option2() {
    System.out.println("Your Shopping Cart contains the following:");
    for (int i=0; i < (this.cartBookArrayIndex + 1) && this.cartBooks[i].getTitle() != null; i+=1){
        System.out.println((i+1) + ". " + this.cartBooks[i].getTitle() + ", Author: " + this.cartBooks[i].getAuthor());
    }
    menu();
}


// option3 method:
// prints cart message.
// prints all book's required info, for every book in cart.
// also prints cancel option.
// prints remove message, then user inputs chosen option.
// based on what user inputs, if cancel option chosen: menu method is called,
// if invalid option chosen, error message printed, another user input is required,
// otherwise if valid option or cancel option chosen then:
// if its an ebook $8 is removed from total price of books in cart,
// else if physical book is removed then total price of books is reduced by $50, 
// and the number of physical books left reduces by one.
// then the book needs to be removed from book cart array, so all books are replaced by
// books that have an index higher than their by 1, except of index 0 which nothing happens,
// since index -1 can't be accessed.
// then removed book from cart, is printed.

public void option3() {
    System.out.println("Your Shopping Cart contains the following:");
    for (int i=0; i < (this.cartBookArrayIndex + 1) && this.cartBooks[i].getTitle() != null; i+=1){
        System.out.println((i+1) + ". " + this.cartBooks[i].getTitle() + ", Author: " + this.cartBooks[i].getAuthor());
    }
    System.out.println("0. Cancel");
    System.out.println("What do you want to remove?");
    Scanner input = new Scanner(System.in);
    int selection = input.nextInt();   
    if(selection == 0){
        menu();
    }
    else if (selection < 0 || selection > (this.cartBookArrayIndex + 1)) {
        System.out.println("Sorry that's an invalid option. Try again");
        selection = input.nextInt();
    }
    // again only one inavlid works, not more
    // there should be a separte method for option 0 and option invalid
    if(selection == 0){
        menu();
    }
    else if (this.cartBooks[selection-1].getPurchasedVersion() == "eBook"){
        this.totalPriceCartBooks -= 8.00;        
    }
    else if (this.cartBooks[selection-1].getPurchasedVersion() == "pBook"){
        this.totalPriceCartBooks -= 50.00; 
        // this.cartBooks[selection-1].pBookLeft += 1;
        this.cartBooks[selection-1].setPBookLeft(this.cartBooks[selection-1].getPBookLeft() + 1); 
        // cant remove a book if it doesnt exist in cart list
    }
    
    this.cartBookArrayIndex -= 1;
    // so when adding... 

    // second half of array, but
    Book index0Book = this.cartBooks[0];
    for (int i=selection-1; i < (this.cartBookArrayIndex + 2) && this.cartBooks[i].getTitle() != null; i+=1){
        if (i==0){          
            //do nothing, sicne index of -1 cant be accessed
        }
        else {
            this.cartBooks[i-1] = this.cartBooks[i]; 
        }      
    }

    if (selection==1){
            System.out.println(index0Book.getTitle() + " removed from Cart.");
    }
    else {
           System.out.println(this.cartBooks[selection-2].getTitle() + " removed from Cart.");
    }   
    menu();
}

// option4 method:
// prints total bill for all books purchased/ added to cart.
// prints thank you message.
public void option4() {
    System.out.println("You have purchased total value of: $" + this.totalPriceCartBooks);
    System.out.println("Thank You for shopping with Daintree!");
}

// option5 method:
// shows all the books available in the database, including after some are purchased.
public void option5() {
    System.out.println("All Books List:");
    for (int i=0; i < bookArrayLength; i+=1){
    System.out.println(books[i].getTitle() + ", author:" + books[i].getAuthor()+ ", " + books[i].getPBookLeft() + " physical books left, ebook exists: " + books[i].getEBookExists());
    }
    menu();
}

// option0 method:
// prints a goodbye method.
public void option0() {
    System.out.println("Goodbye!");
}

// optionOther method:
// prints invalid message.
// then calls the menu method.
public void optionOther() {
    System.out.println("Sorry, that is an invalid option!");
    menu();
}

}
