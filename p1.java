import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* Purpose: The purpose of this program is to: 
  * a) Read a hand of cards from a given file
  * b) Sort the cards by suit into different arrays
  * c) Calculate the number of points each suit has and the grand total of points of hand
  * d) Ignores hands with: Too many/few cards, fake cards, or duplicate cards
 * Programmer Info:
 * Name: Jared McDonald
 * Instructor: Dr.Dunn
 * Class: CSC 241
 * Files Needed/Used: p1.dat
 * Dictionary of variables:
 * input- A BufferedReader used to read a file
 * line- A string used to hold what BufferedReader has read
 * clubs- An array to hold the club Strings/cards
 * diamonds- An array to hold the diamond Strings/cards
 * hearts- An array to hold the heart Strings/cards
 * spades- An array to hold the spade Strings/cards
 * totalpoints- Total number of points of the hand
 * nextindex- Index of the next available spot in a suit array
 * rank- The rank the card holds
 * cards- The array passed from the MakeSuit method
 * card- A String object used to put into the array
 * i- Int variable used for loops
 * j- Int variable used for loops
 * count- Int variable representing the number of cards
 * points- The number of points the suit has
 * item- String used to compare cards for duplicates
 * otheritem- String used to compare cards for duplicates
 * ADTs Used: None
 * Sample input: 
 * 
 * Sample Output:
 * 
*/
public class p1 {

	public static void main(String[] args) throws IOException {
		new p1().newmain();//Call nonstatic method so the other methods wont have to be static
	}

	private void newmain() throws IOException {//Called to use as main method instead
		BufferedReader input = new BufferedReader(new FileReader("p1.dat"));//Where the input file is located
		String line;//Declare a String variable
		while((line = input.readLine()) != null){//While the predefined string is not null after reading from the FileReader
			System.out.println("Hand: "+line); //Print what is read for confirmation and detail
			if(TotalCards(line)!= 13){//If the total cards in hand isnt 13 (a valid hand)
				System.err.println("\t This hand has an incorrect amount of cards("+TotalCards(line)+" cards instead of 13)!"); //Print error message and ignore hand
			}
			else{
				String[] clubs = MakeSuit(line,'C');//Make club cards array
				String[] diamonds = MakeSuit(line,'D');//Make diamond cards array
				String[] hearts = MakeSuit(line,'H');//Make heart cards array
				String[] spades = MakeSuit(line,'S');//Make spade cards array
				System.out.println("\t CLUBS: ");
				PrintSuit(clubs);//List the club cards
				System.out.println("\t DIAMONDS: ");
				PrintSuit(diamonds);//List the diamonds cards
				System.out.println("\t HEARTS: ");
				PrintSuit(hearts);//List the hearts cards
				System.out.println("\t SPADES: ");
				PrintSuit(spades);//List the spade cards
				System.out.println("Clubs points: "+Calculate(clubs)+", Diamonds points; "+ Calculate(diamonds)+", Hearts points: "+Calculate(hearts)+", Spades points: "+Calculate(spades));
				int totalpoints = Calculate(MakeSuit(line,'H'))+Calculate(MakeSuit(line,'C'))+Calculate(MakeSuit(line,'D'))+Calculate(MakeSuit(line,'S'));//Add up all the points
				System.out.println("\t Total Points = "+totalpoints); //Print the total points
			}
		}
		input.close();//close the filereader
	}
	private String[] MakeSuit(String line,char suit){//Called to create a suit from the read line
		String[] cards = new String[CountCards(line,suit)];//Create a new array with just enough space for every card in a suit
		int nextindex = 0;//Index of next available space
		for(int i = 0;i<=line.length()-1;i++){
			if(line.charAt(i) == suit){//If the card in the hand matches the suit
				char rank = line.charAt(i-1);//Grab its rank
				String card = Character.toString(rank); //Make a card string
				cards[nextindex] = card;//Put card in array/hand?
				nextindex++;//Go to next available space
				i++;
			}
		}
		return cards;//Return that array we just made
	}
	
	private int CountCards(String line,char suit) {//Pass the hand and the suit you want to count
		int count = 0;//No cards have been counted
		for(int i = 0;i<line.length();i++){
			if(line.charAt(i) == suit){
				count++;//Counted a card!
			}
		}
		return count; //Return how many cards were counted
	}
	private int TotalCards(String line) {//Called to count the total number of cards in the hand
		int count = 0;
		for(int i = 0;i<line.length();i++){
			if(line.charAt(i) == ' '){
				count++;
			}
		}
		return count+1;//Since we started count at 0
	}//Returns the total number of cards in the hand that was passed
	int Calculate(String[] suit){//Called to calculate points of a suit/passed array
		int points = 0;//Havent calculated any points
		for(int i = 0;i < suit.length;i++){
			if(suit[i].equalsIgnoreCase("A")){
				points += 4;//4 points
			}
			else if(suit[i].equalsIgnoreCase("K")){
				points+= 3;//3 points
			}
			else if(suit[i].equalsIgnoreCase("Q")){
				points += 2;//2 points
			}
			else if(suit[i].equalsIgnoreCase("J")){
				points += 1;//1 point
			}
			else{
				//Do nothing if a fake card slipped through the cracks
			}
		}
		if(suit.length == 0){//If the suit has no cards
			points += 3;//3 points for a void
		}
		
		if(suit.length == 1){//If the suit only has 1 card
			points += 2;//2 points for a singleton
		}
		if(suit.length == 2){//If the suit has 2 cards
			points += 1;//1 point for a doubleton
		}
		if(suit.length >5){//If the suit has more than 5 cards
			points += ((suit.length - 5)*2);//Count how many extra cards we have and get 1 point for each
		}
		
		return points;//Return the points we calculated
	}
	
	void EvaluateHand(String[] suit){//Checks for valid hands and cards
		for(int i = 0;i < suit.length-1;){//Every card in the suit is considered
			if(Character.isDigit(suit[i].charAt(0))== false||suit[i].charAt(0) != 'K'||suit[i].charAt(0) != 'J'||suit[i].charAt(0) != 'Q'||suit[i].charAt(0) != 'T'||suit[i].charAt(0) != 'A'){//If the card has a fake rank
				System.err.println("This hand contains an invalid card!");//Error message and ignore the suit	
				}	
			}
		if(Duplicates(suit) == 1){//This if doesn't work unfortunately
			System.err.println("This hand contains an duplicate card!");//If it did, this error message would occur
		}
		else{//If the suit is valid
			//Do nothing to it
		}
	}
	int Duplicates(String[] suit){//Passed a suit to check for duplicates
		for(int i = 0;i <suit.length-2;){
			String item = suit[i];//Item in suit[i] to compare to...
			String otheritem = suit[i+1];//its neighbor 
			if(item.equalsIgnoreCase(otheritem) == true){//if they are the same rank/card
				return 1;//Return 1, as in its not zero, or 1= true
			}
			else{
				return 0;//Return 0 because the suit is valid
			}
		}
		return 0;//Added to get rid of error messages from IDE
	}
	void PrintSuit(String[] suit){//Prints each card in the passed suit
		for(int i = 0;i < suit.length;i++){//For each card in the suit
				System.out.print(suit[i]+" ");	
		}
	}//Prints each card in the suit seperated by a space
}
