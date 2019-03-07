package eBookStore;

import java.sql.*;
import java.util.Scanner;
public class eBookStore {

	public static void main(String[] args) {
		//creating table connection and statement
		try (
				
				Connection conn = DriverManager . getConnection (
						"jdbc:mysql://localhost:3306/ebookstore?useSSL=false&allowPublicKeyRetrieval = true" , "Dewald" , "Willemiscool123" );
						
					Statement stmt = conn . createStatement ();
				){
			
			int selection = 0;
			Scanner input = new Scanner(System.in);
			//menu for user input and selection
			while (selection != 5) {
				
				System.out.println("Please enter the number of the action you would like to take:\n"
						+ "1. Enter a new book\n"
						+ "2. Update book information\n"
						+ "3. Delete books \n"
						+ "4. Search for a book\n"
						+ "5. Show all records in the store\n");
				selection = input.nextInt();
				
				// sorting the selection entered by the user using if and else statements.
				if (selection == 1) {
					// collecting the book information from the user 
					System.out.println("Please enter the Id of the book");
					int id = input.nextInt();
					System.out.println("Please enter the name of the book.");
					String title = input.nextLine();
					System.out.println("Please enter the Author of the book");
					String author = input.nextLine();
					System.out.println("Please enter the quantity of books in the store");
					int qty = input.nextInt();
					
					// sending the collected information to the addBooks method 
					addBooks(id, title , author , qty , stmt);
				}
				else if (selection == 2) {
					// getting the id or the name of the book to be updated
					System.out.println("Please enter the ID number of the book you would like to update.\n");
					int id = input.nextInt();
					
					
					//sending the collected information to the updateBooks method 
					updateBooks(id , stmt);
				}
				// if the user selected to delete books from the system a while loop would allow the user to delete more than 1 book, 
				else if (selection == 3) {
					System.out.println("How many books would you like to delete from the system?");
					int delete_book_count = input.nextInt();
					
					// sending the number of requested books to the method called deleteBooks
					deleteBooks(delete_book_count , stmt);
				}
				// an else if statement if the user choice was to search a book.
				else if (selection == 4) {
					System.out.println("Please enter the Id of the book to see all records from the system ");
					int IDofBook = input.nextInt();
					
					//sending the id number to the method searchBook to search a book depending on user choice
					searchBook(IDofBook , stmt);
				}
				
				//based upon selection all the books currently in the system will be displayed
				else if(selection == 5) {
					System.out.println("The following is all the records in the system");
					
					String strSelect = "select * from books" ;
					ResultSet rset = stmt . executeQuery ( strSelect );
					
					while ( rset . next ()) {
						System . out . println ( rset . getInt ( "id" ) + ", "+ rset . getString ( "Author" ) + ", "+ rset . getString ( "Title" ) + ", "+ rset . getInt ( "Qty" ));
					}
				}
				input.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}



	// creating a method to add books into the database based on user input from the main menu 
	private static void addBooks(int id, String title, String author, int qty, Statement stmt) {
		
		try {
			String sqlInsert = "insert into books "+ "values ("+id+","+ title+ ","+ author+ ","+ qty+")" ;
			
			int countInserted;
			countInserted = stmt . executeUpdate ( sqlInsert );
			System . out . println ( countInserted + " records inserted.\nYour records have successfully been added to the system" );
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	//creating a method to update books of the database based on user input from the main menu 
	private static void updateBooks(int id, Statement stmt) {
		//creating a new scanner in the method 
		Scanner input = new Scanner(System.in);
		try {
			
			//showing the results of the id selected by the user 
			String strSelect = "select * from books where id = "+id ;
			ResultSet rset = stmt . executeQuery ( strSelect );
			while ( rset . next ()) { // Move the cursor to the next row
				System . out . println ( rset . getInt ( "id" ) + ", "+ rset . getString ( "Author" ) + ", "+ rset . getString ( "Title" ) + ", "+ rset . getInt ( "Qty" ));
			}
			
			//displaying a menu asking the user what he/she would like to update from the id chosen
			System.out.println("Enter the number from the menu that you want to update from the book ?\n"
					+ "1. Id\n"
					+ "2. Title\n"
					+ "3. Author\n"
					+ "4. Quantity");
			int selection = input.nextInt();
			
			// sorting the selection based on user input from the menu what books to be updated
			if (selection == 1) {
				System.out.println("What would you like the new id number to be?");
				int newID = input.nextInt();
				String strUpdate = "update books set id = "+newID+" where id ="+ id ;
				int countUpdated = stmt . executeUpdate ( strUpdate );
				System . out . println ( countUpdated + " records affected.\nThe new ID is: "+newID );
			}
			else if(selection == 2) {
				System.out.println("What would you like the new Title of the book to be?");
				String newTitle = input.nextLine();
				String strUpdate = "update books set Title = "+newTitle+"where id="+id;
				int countUpdated = stmt .executeUpdate(strUpdate);
				System.out.println(countUpdated +" records affected\nThe new Title is: "+newTitle);	
			}
			else if(selection == 3) {
				System.out.println("What would you like the new Author of the book to be?");
				String newAuthor = input.nextLine();
				String strUpdate = "update books set Author = "+newAuthor+" where id ="+ id ;
				int countUpdated = stmt . executeUpdate ( strUpdate );
				System . out . println ( countUpdated + " records affected.\nThe new Author is: "+newAuthor );
			}
			else if (selection == 4) {
				System.out.println("What would you like the new quantity to be?");
				int newQuantity = input.nextInt();
				String strUpdate = "update books set Qty = "+newQuantity+" where id ="+ id ;
				int countUpdated = stmt . executeUpdate ( strUpdate );
				System . out . println ( countUpdated + " records affected.\nThe new Quantity is: "+newQuantity );
			}
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		input.close();
	}
	//method to delete more than one book depending on user choice (amount of books to be deleted)
	public static void deleteBooks(int delete_book_count, Statement stmt) {
		Scanner input = new Scanner(System.in);
		try {
			int count = 0;
			while(count < delete_book_count) {
				System.out.println("Enter the Title of the book you want to delete");
				String userChoice = input.nextLine();
				String sqlDelete = "delete from books where Title = "+userChoice ;
				System . out . println ( "The SQL query is: " + sqlDelete );
				int countDeleted;
				countDeleted = stmt . executeUpdate ( sqlDelete );
				System . out . println ( countDeleted + " records deleted.\n"+userChoice+" was successfully deleted from the system" );
				count ++;
			}
		}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		input.close();
		}
	
	// a method to display the searched for book by the user 
	public static void searchBook( int IDofBook, Statement stmt) {
		
		try {
			String strSelect = "select * from books where id ="+IDofBook ;
			System . out . println ("The following are all the records in the system based on the ID number :"+IDofBook );
			ResultSet rset;
			rset = stmt . executeQuery ( strSelect );
			while ( rset . next ()) {
				System.out.println ( rset . getInt ( "id" ) + ", "+ rset . getString ( "Author" ) + ", "+ rset . getString ( "Title" ) + ", "+ rset . getInt ( "Qty" ));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
}

