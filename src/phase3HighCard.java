



/**
 * Assignment 5 - Phase 3
 *  
 * Adding CardGameFramework and Creating High Card Game
 * 
 * @author Cody Dill
 * @author Jared Hubbard
 * @author Trevor Osborne
 * @author Joseph Appleton
 * @version 0
 * @since 2018-04-02
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class phase3HighCard
{
   
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static int [] scores = new int[NUM_PLAYERS];
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];
   static JLabel[] scoreLabels = new JLabel[NUM_PLAYERS];
   static JLabel playResult = new JLabel("playResult");
   static JLabel computerCard = new JLabel();
   static JLabel humanCard = new JLabel();

   
   public static void main(String[] args)
   {
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card [] unusedCardsPerPack = null;
      Card computerPlay, humanPlay;
      
      CardGameFramework highCardGame = new CardGameFramework(numPacksPerDeck,
            numJokersPerPack, numUnusedCardsPerPack, unusedCardsPerPack,
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
      int k;
      int i;
      Deck myDeck = new Deck();
      myDeck.shuffle();
      
      
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);

      // CREATE LABELS ----------------------------------------------------
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerLabels[k] = new JLabel(GUICards.getBackCardIcon());
         humanLabels[k] = new JLabel(GUICards.getIcon(myDeck.dealCard()));
      }
      
      // labels for two random cards in the play region (simulating a computer/human play)
      playedCardLabels[0] = new JLabel(GUICards.getIcon(myDeck.dealCard()));
      for (i = 1; i < NUM_PLAYERS; i++)
         playedCardLabels[i] = new JLabel(GUICards.getIcon(myDeck.dealCard()));
      
      //labels for text for the two cards played by human and computer
      JLabel humanPlayedTextLabel = new JLabel("You", JLabel.CENTER );
      JLabel computerPlayedTextLabel = new JLabel("Computer", JLabel.CENTER );
      
      
      // ADD LABELS TO PANELS -----------------------------------------
      for (int q = 0; q < NUM_CARDS_PER_HAND; q++)
      {
         myCardTable.pnlComputerHand.add(computerLabels[q]); 
         myCardTable.pnlHumanHand.add(humanLabels[q]); 
      }
      
      //These are added in the correct order to fill a 2X2 Grid Layout
      myCardTable.pnlPlayArea.add(playedCardLabels[0]);
      for (i = 1; i < NUM_PLAYERS; i++)
      {   
         myCardTable.pnlPlayArea.add(playedCardLabels[i]);    
      }
      myCardTable.pnlPlayArea.add(computerPlayedTextLabel);
      myCardTable.pnlPlayArea.add(humanPlayedTextLabel);
      
      
      // show everything to the user
      myCardTable.setVisible(true);

   //End of main()
      for (k=0; k<NUM_PLAYERS; k++)
      {
         scores[k] = 0;
         scoreLabels[k] = new JLabel();
      }
      for(k=0; k<NUM_CARDS_PER_HAND; k++)
      {
         humanLabels[k] = new JLabel();
         computerLabels[k] = new JLabel();
      }
      while(highCardGame.getNumCardsRemainingInDeck() > 0)
   {
      highCardGame.deal();
      while(highCardGame.getHand(0).getNumCards() != -1)
      {
         for(k = 0; k< highCardGame.getHand(1).getNumCards(); k++)
         {
            humanLabels[k].setIcon(GUICards.getIcon(highCardGame.getHand(1).inspectCard(k)));
            computerLabels[k].setIcon(GUICards.getBackCardIcon());
            myCardTable.pnlComputerHand.add(computerLabels[k]);
            myCardTable.pnlHumanHand.add(humanLabels[k]);
         }
         myCardTable.setVisible(true);
         while(true)
         {
            if(!myCardTable.buttonNotPressed())
            {
               computerCard.setIcon(null);
               humanCard.setIcon(null);
               
              highCardGame.getHand(0).sort();
               highCardGame.getHand(1).sort();
               
              computerPlay = highCardGame.getHand(0).playCard();
               humanPlay = highCardGame.getHand(1).playCard();
               
              computerCard.setIcon(GUICards.getIcon(computerPlay));
               humanCard.setIcon(GUICards.getIcon(humanPlay));
               
              myCardTable.pnlPlayArea.add(humanCard);
               myCardTable.pnlPlayArea.add(computerCard);
               myCardTable.setVisible(true);
               
              if(GUICards.rankAsInt(computerPlay.getValue()) > GUICards.rankAsInt(humanPlay.getValue()))
                  {
                     scores[1] +=2;
                     scoreLabels[1].setText("Human: " + Integer.toString(scores[1]));
                     playResult.setText("Great Job, you win!");
                     playResult.setHorizontalAlignment(JLabel.CENTER);
                     playResult.setForeground(Color.white);
                     myCardTable.pnlControl.add(playResult,BorderLayout.CENTER);
                     myCardTable.pnlControl.add(scoreLabels[1],BorderLayout.LINE_START);
                     myCardTable.setVisible(true);
                     
                 }
              else if(GUICards.rankAsInt(computerPlay.getValue()) > GUICards.rankAsInt(humanPlay.getValue()))
                  {
                     scores[0] +=2;
                     scoreLabels[0].setText("Computer: " + Integer.toString(scores[0]));
                     playResult.setText("The AI has won again! Poor humans...");
                     playResult.setHorizontalAlignment(JLabel.CENTER);
                     playResult.setForeground(Color.white);
                     myCardTable.pnlControl.add(playResult,BorderLayout.CENTER);
                     myCardTable.pnlControl.add(scoreLabels[0],BorderLayout.LINE_END);
                     myCardTable.setVisible(true);
                  }
                  else
                  {
                     playResult.setText("Tie");
                     playResult.setHorizontalAlignment(JLabel.CENTER);
                     playResult.setForeground(Color.white);
                     myCardTable.pnlControl.add(playResult,BorderLayout.CENTER);
                     myCardTable.setVisible(true);
                  }
                  break;
            }
         }
         for(k=0; k<NUM_CARDS_PER_HAND; k++)
         {
            humanLabels[k].setIcon(null);
            computerLabels[k].setIcon(null);
         }
      }
   }//The while loop is over.
   computerCard.setIcon(null);
   humanCard.setIcon(null);
   playResult.setText("Game Over.");
   myCardTable.setVisible(true);

   } 
}
class BooleanResult
{
   private Boolean result;
   public BooleanResult(Boolean value) 
   {
      this.result = value;
   }
   public void setResult(boolean value)
   {
      result = value;
   }
   public Boolean getResult()
   {
      return result;
   }
}
//class CardGameFramework  ----------------------------------------------------
class CardGameFramework
{
 private static final int MAX_PLAYERS = 50;

 private int numPlayers;
 private int numPacks;            // # standard 52-card packs per deck
                                  // ignoring jokers or unused cards
 private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
 private int numUnusedCardsPerPack;  // # cards removed from each pack
 private int numCardsPerHand;        // # cards to deal each player
 private Deck deck;               // holds the initial full deck and gets
                                  // smaller (usually) during play
 private Hand[] hand;             // one Hand for each player
 private Card[] unusedCardsPerPack;   // an array holding the cards not used
                                      // in the game.  e.g. pinochle does not
                                      // use cards 2-8 of any suit

  CardGameFramework( int numPacks, int numJokersPerPack,
       int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
       int numPlayers, int numCardsPerHand)
 {
    int k;

    // filter bad values
    if (numPacks < 1 || numPacks > 6)
       numPacks = 1;
    if (numJokersPerPack < 0 || numJokersPerPack > 4)
       numJokersPerPack = 0;
    if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
       numUnusedCardsPerPack = 0;
    if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
       numPlayers = 4;
    // one of many ways to assure at least one full deal to all players
    if  (numCardsPerHand < 1 ||
          numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
          / numPlayers )
       numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

    // allocate
    this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
    this.hand = new Hand[numPlayers];
    for (k = 0; k < numPlayers; k++)
       this.hand[k] = new Hand();
    deck = new Deck(numPacks);

    // assign to members
    this.numPacks = numPacks;
    this.numJokersPerPack = numJokersPerPack;
    this.numUnusedCardsPerPack = numUnusedCardsPerPack;
    this.numPlayers = numPlayers;
    this.numCardsPerHand = numCardsPerHand;
    for (k = 0; k < numUnusedCardsPerPack; k++)
       this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

    // prepare deck and shuffle
    newGame();
 }

 // constructor overload/default for game like bridge
 public CardGameFramework()
 {
    this(1, 0, 0, null, 4, 13);
 }

 public Hand getHand(int k)
 {
    // hands start from 0 like arrays

    // on error return automatic empty hand
    if (k < 0 || k >= numPlayers)
       return new Hand();

    return hand[k];
 }

 public Card getCardFromDeck() 
 { 
    return deck.dealCard(); 
 
 }

 public int getNumCardsRemainingInDeck() 
 { 
    return deck.getNumCards(); 
    }

 public void newGame()
 {
    int k, j;

    // clear the hands
    for (k = 0; k < numPlayers; k++)
       hand[k].resetHand();

    // restock the deck
    deck.init(numPacks);

    // remove unused cards
    for (k = 0; k < numUnusedCardsPerPack; k++)
       deck.removeCard( unusedCardsPerPack[k] );

    // add jokers
    for (k = 0; k < numPacks; k++)
       for ( j = 0; j < numJokersPerPack; j++)
          deck.addCard( new Card('X', Card.Suit.values()[j]) );

    // shuffle the cards
    deck.shuffle();
 }

 public boolean deal()
 {
    // returns false if not enough cards, but deals what it can
    int k, j;
    boolean enoughCards;

    // clear all hands
    for (j = 0; j < numPlayers; j++)
       hand[j].resetHand();

    enoughCards = true;
    for (k = 0; k < numCardsPerHand && enoughCards ; k++)
    {
       for (j = 0; j < numPlayers; j++)
          if (deck.getNumCards() > 0)
             hand[j].takeCard( deck.dealCard() );
          else
          {
             enoughCards = false;
             break;
          }
    }

    return enoughCards;
 }

 void sortHands()
 {
    int k;

    for (k = 0; k < numPlayers; k++)
       hand[k].sort();
 }

 Card playCard(int playerIndex, int cardIndex)
 {
    // returns bad card if either argument is bad
    if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
        cardIndex < 0 || cardIndex > numCardsPerHand - 1)
    {
       //Creates a card that does not work
       return new Card('M', Card.Suit.SPADES);      
    }
 
    // return the card played
    return hand[playerIndex].playCard(cardIndex);
 
 }


 boolean takeCard(int playerIndex)
 {
    // returns false if either argument is bad
    if (playerIndex < 0 || playerIndex > numPlayers - 1)
       return false;
   
     // Are there enough Cards?
     if (deck.getNumCards() <= 0)
        return false;

     return hand[playerIndex].takeCard(deck.dealCard());
 }

}

/**
 * 
 * Controls the positioning of the panels and cards of the GUI.
 *
 */
class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   // for now, we only allow 2 person games
   static int MAX_PLAYERS = 2;  
   
   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea,pnlControl;
   
   BooleanResult buttonNotPressed;
   
   
   /**
    * Constructor filters input, adds any panels, and establishes layouts 
    * @param title
    * @param numCardsPerHand
    * @param numPlayers
    */
   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      super();
      
      //Validates input
      if (numCardsPerHand > MAX_CARDS_PER_HAND || numCardsPerHand <= 0)
         System.exit(0);
      if (numPlayers > MAX_PLAYERS || numPlayers <= 0)
         System.exit(0);
         
         this.numCardsPerHand = numCardsPerHand;
         this.numPlayers = numPlayers;
      TitledBorder controlTitle;   
      controlTitle = BorderFactory.createTitledBorder("Game Info");
      //Default Table settings   
      setSize(800, 600);
      setTitle(title);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      getContentPane().setBackground(Color.GREEN);
      setLayout(new GridLayout(3, 0));
      
      //Panels
      add(pnlComputerHand = new JPanel(new GridLayout(0, numCardsPerHand))); 
      pnlComputerHand.setSize(800, 200);
      pnlComputerHand.setBackground(Color.GREEN);
      
      add(pnlPlayArea = new JPanel(new GridLayout(numPlayers, numCardsPerHand))); 
      pnlPlayArea.setSize(800, 200);
      pnlPlayArea.setBackground(Color.GREEN);
      
      add(pnlHumanHand = new JPanel(new GridLayout(0, numCardsPerHand))); 
      pnlHumanHand.setSize(800, 200);
      pnlHumanHand.setBackground(Color.GREEN);
      
      pnlControl = new JPanel();
      pnlControl.setLayout(new GridBagLayout());
      pnlControl.setBackground(Color.GRAY);
      pnlControl.setBorder(controlTitle);
      
      this.buttonNotPressed = new BooleanResult(true);
      
      JButton playButton = new JButton("Click to play");
      PlayListener plisten = new PlayListener(buttonNotPressed);
      playButton.addActionListener(plisten);
      pnlControl.add(BorderLayout.PAGE_START,playButton);
      
   }
   
   
   public boolean buttonNotPressed()
   {
      if(!buttonNotPressed.getResult())
      {
         buttonNotPressed.setResult(true);
         return false;
      }
      return true;
   }

   /**
    * Accessor for numCardsPerHand
    * @return - max Cards per hand for this CardTable
    */
   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }
   
   
   /**
    * Accessor for numPlayers
    * @return - max players including computer for this CardTable
    */
   public int getNumPlayers()
   {
      return numPlayers;
      
   }
   //End of CardTable Class
   
}


/**
 * 
 * This Class produces an image icon when client needs one
 *
 */
class GUICards
{   
   // 52 + 4 jokers + 1 back-of-card image  = 57
   private static final int NUM_CARD_IMAGES = 57;
   //14 = A thru K + joker
   private static Icon[][] iconCards = new ImageIcon[4][14]; 
   private static Icon iconBack;
   static boolean iconsLoaded = false;
   
   
   /**
    * Reads image files and stores them in a static Icon array
    */
   private static void loadCardIcons() 
   {   
      //Checks if array has already been loaded
      if (iconsLoaded == true)
         return;
      
      String folder = "images/"; 
      String extension = ".gif";
      String cardBack = "BK";
      int numOfSuits = 4;
      int numOfCardsPerSuit = 14;
      String[] fileNames = new String[NUM_CARD_IMAGES];
      int cardNumber = 0;
      char rank = 'e';
      char suit = 'e';
      
      // build the file names ("AC.gif" etc.)
      for (int j = 0; j < numOfSuits; j++)
      {
         for (int k = 0; k < numOfCardsPerSuit; k++)
         {
            rank = turnIntIntoCardRank(k);
            suit = turnIntIntoCardSuit(j);
            fileNames[cardNumber] = folder + rank + suit + extension;
            cardNumber++;
         }
      }
      fileNames[cardNumber] = folder + cardBack + extension;
      
      // instantiate each of the 56 Icons in the icon[][] array.
      cardNumber = 0;
      for (int p = 0; p < numOfSuits; p++)
      {
         for (int q = 0; q < numOfCardsPerSuit; q++)
         {
            iconCards[p][q] = new ImageIcon(fileNames[cardNumber]);
            cardNumber++;
         }
      }
      iconBack = new ImageIcon(fileNames[NUM_CARD_IMAGES - 1]);
            
      iconsLoaded = true;
   }
   
   
   
   /**
    * turnIntIntoCardRank - turns 0 .. 13 
    *    into "A", "2", "3", ... "Q", "K", "X"
    * @param k - int to convert
    * @return  char - char representation
    */
   private static char turnIntIntoCardRank(int k)
   {
      switch(k)
      {
         case 0:
            return 'A';
         case 9:
            return 'T';
         case 10:
            return 'J';
         case 11:
            return 'Q';
         case 12:
            return 'K';
         case 13:
            return 'X';
         default:
            // return the char values for 2..9
            return (char) (k + 49);
      }
      
   }
   
   
   /**
    * tunrIntIntoCardSuit - turns 0 .. 3 
    *    into "C", "D", "H", "S"
    * @param j - int to convert
    * @return char - char representation
    */
   private static char turnIntIntoCardSuit(int j)
   {
      switch(j)
      {
         case 0:
            return 'C';
         case 1:
            return 'D';
         case 2:
            return 'H';
         case 3:
            return 'S';
         default:
            return 'e';
      }
   }

   
   /**
    * Gets the card/back icon 
    * @return - the card/back image icon
    */
   public static Icon getBackCardIcon()  
   {    
      loadCardIcons();
      return iconBack;
   }
   
   
   /**
    * Gets a Card's corresponding image icon
    * @param card
    * @return - the Card's icon
    */
   public static Icon getIcon(Card card) 
   {
      loadCardIcons();     
      return iconCards[suitAsInt(card.getSuit())][rankAsInt(card.getValue())];
   }
   
   
   /**
    * Helper to getIcon: gets iconCards[#][] index 
    * @return integer representation of card suit
    */
   public static int suitAsInt(Card.Suit suit)
   {
      //Suit input already validated by Card.getSuit()
      if (suit == Card.Suit.CLUBS)
         return 0; 
      else if (suit == Card.Suit.DIAMONDS)
         return 1;
      else if (suit == Card.Suit.HEARTS)
         return 2;
      else 
         return 3;    
   }
   
   
   /**
    * Helper to getIcon: gets iconCards[][#] index
    * @return integer representation of card rank
    */
   public static int rankAsInt(char rank)
   {
      //Rank/value input already validated by Card.getValue()
      if (rank == 'A')
         return 0; 
      else if (rank == '2')
         return 1;
      else if (rank == '3')
         return 2;
      else if (rank == '4')
         return 3;
      else if (rank == '5')
         return 4;
      else if (rank == '6')
         return 5;
      else if (rank == '7')
         return 6;
      else if (rank == '8')
         return 7;
      else if (rank == '9')
         return 8;
      else if (rank == 'T')
         return 9;
      else if (rank == 'J')
         return 10;
      else if (rank == 'Q')
         return 11;
      else if (rank == 'K')
         return 12;
      //Joker
      else
         return 13; 
   }
   //End of GUICards class
  
}


/**
 * Objects represent a single playing card with suit and value
 *
 */
class Card
{

   // Public enum Members:
   public enum Suit
   {
      CLUBS, DIAMONDS, HEARTS, SPADES
   };

   // Public Static Data Members:
   public static final char[] LEGAL_VALUES =
   { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };

   // Private Data Members:
   private char value;
   private Suit cardSuit = null;
   private boolean errorFlag = false;

   
   /**
    * Public default constructor initializes 
    * value to 'A' and Suit to SPADES
    */
   public Card()
   {
      set('A', Suit.SPADES);
   }

   
   /**
    * Public Constructor
    * 
    * @param value
    *           - card value to be stored
    * @param suit
    *           - card suit to be stored
    */
   public Card(char value, final Suit suit)
   {
      set(value, suit);
   }

   
   /**
    * Public Constructor (Copy)
    * 
    * @param originalCard
    *           - values to be copied into new 
    *             separate Card object
    */
   public Card(final Card originalCard)
   {
      value = originalCard.value;
      cardSuit = originalCard.cardSuit;
      errorFlag = originalCard.errorFlag;
   }

   
   /**
    * Public Accessor Method toString (OVERRIDE)
    *
    * @return String - concatenated value and 
    *                  suit of Card object 
    */
   @Override
   public String toString()
   {
      String cardValue = String.format("%s of %s", value, cardSuit);
      return errorFlag ? "** illegal **" : cardValue;
   }

   
   /**
    * Public Mutator Method for value and 
    * cardSuit for legal parameters, sets
    * errorFlag to true, false otherwise.
    * 
    * @param newValue
    *           - the new value for value
    * @param newSuit
    *           - the new value for cardSuit
    * @return boolean - indicating the success
    *                   of the value assignment
    */
   public boolean set(char newValue, final Suit newSuit)
   {
      // check if it is valid, save the error flag
      errorFlag  = !isValid(newValue, newSuit);
      if (!errorFlag) 
      {
         // if valid we are saving the variables
         cardSuit = newSuit;
         value = newValue;
      }
      // returning whether or not the values are valid.
      return !errorFlag;
   }

   
   /**
    * Public Accessor Method
    * 
    * @param card
    *           - card to be compared
    * @return boolean - true if all suit, value
    *                   and errorFlag fields 
    *                   equal card fields, 
    *                   false otherwise
    */
   public boolean equals(final Card card)
   {
      Card.Suit testSuit = card.getSuit();
      char testValue = card.getValue();
      boolean testFlag = card.getErrorFlag();
      if (testSuit == cardSuit)
      {
         if (testValue == value)
         {
            if (testFlag == errorFlag)
            {
               return true;
            }
         }
      }
      return false;
   }

   
   /**
    * Public Accessor Method for value
    * 
    * @return the value of value
    */
   public char getValue()
   {
      return value;
   }

   
   /**
    * Public Accessor Method for cardSuit
    * 
    * @return the value of cardSuit
    */
   public Suit getSuit()
   {
      return cardSuit;
   }

   
   /**
    * Public Accessor Method for errorFlag
    * 
    * @return the value of errorFlag
    */
   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   
   /**
    * Private helper method to determine 
    * validity of checkValue argument. Suit is
    * currently an enum and not checked by this
    * method.
    * 
    * @param value
    *           - value of value to be validated
    * @param suit
    *           - enum parameter included for future use
    * @return true if all arguments are legal, false otherwise
    */
   private boolean isValid(char value, final Suit suit)
   {
      boolean isValidValue = false;

      for (char legalValue : LEGAL_VALUES)
      {
         if (value == legalValue)
         {
            isValidValue = true;
         }
      }

      return isValidValue;
   }

   
   /**
    * Orders card values with the smallest first,
    */
   public static char[] valueRanks()
   {
      char[] rankOrder = new char[LEGAL_VALUES.length];
      for (int i = 0; i < LEGAL_VALUES.length; i++)
         rankOrder[i] = LEGAL_VALUES[i];
           
      return rankOrder;
      
   }
   
   
   /**
    * Sorts the incoming array of cards
    */
   public static void arraySort(Card[] cards, int arraySize)
   {  
      Card tempCard;

      for(int i = 0; i < arraySize; i++)
      {
         for(int j = 1; j < arraySize - i; j++)
         {
            if(valueIndex(cards[j-1]) > valueIndex(cards[j]))
            {
               tempCard = cards[j-1];
                cards[j-1] = cards[j];
                cards[j] = tempCard;
            }
         }
      }
      
   }
   
   
   /**
    * This method returns the card's value based on the value ranks
    */
   private static int cardValue(final Card card)
   {
      int value = new String(Card.valueRanks()).indexOf(card.getValue());
      return Card.valueRanks().length - value;
   }
   //End of Card Class
     
}


class Hand
{
   public static int MAX_CARDS = 56;
   private Card[] myCards;
   private int numCards;

   
   // Constructor
   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   
   public Card playCard(int cardIndex)
   {
     
      return null;
   }


   /* 
    * Resets the array by developing a new one. 
    * The previous array will eventually
    * be removed.
    */ 
   public void resetHand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   
   /* 
    * We add a card as input and put it to the 
    * front of the array. If the card is not 
    * added it returns false otherwise it is true.
    */
   public boolean takeCard(Card card)
   {
      if (numCards != MAX_CARDS)
      {
         myCards[numCards] = new Card(card);
         numCards++;
         return true;
      }
      return false;
   }

   // The first card in the array gets removed.
   public Card playCard()
   {
      //Check if there are still Cards in Hand.
      if (numCards <= 0)
      {
         Card badCard = new Card('B', Card.Suit.SPADES);
         return badCard;
      }
      
      Card tempCard = myCards[numCards - 1];
      myCards[numCards - 1] = null;
      numCards--;
      return tempCard;
   }

   
   // Moves all cards to a string to be able to be displayed.
   public String toString()
   {
      String[] tempString = new String[numCards];
      for (int i = 0; i < numCards; i++)
      {
         tempString[i] = (myCards[i].toString() + " ");

      }
      return Arrays.toString(tempString);
   }

   
   // Returns the number of initilized Cards that are in a Hand.
   public int getNumCards()
   {
      return numCards;
   }

   
   /* 
    * Returns the card at k in the array MyCard. 
    * If k happens to be invalid then a invalid
    * card will be created and returned.
    */
   public Card inspectCard(int k)
   {
      if ((k >= 0) && (k < numCards))
      {
         return new Card(myCards[k]);
      } 
      else
      {
         Card tempCard = new Card('0', Card.Suit.CLUBS);
         return tempCard;
      }
   }
   
   
   /*
    * Sorts hand by calling arraySort() method in
    * Card Class.
    */
   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }  
   //End of Hand Class  

}


/**
 * A Deck object is the source of all cards. 
 * It's where the dealer gets cards to deal,
 * and if a player takes an individual card 
 * after the deal, he takes it from the Deck
 * object.
 */
 class Deck
{
   public static final int MAX_CARDS = 6;
   private static Card[] masterPack = new Card[56];
   private Card[] cards;
   private int topCard = 0; 
   private int numPacks = 0;

   
   /**
    * Default Constructor for Deck
    */
   public Deck()
   {
      numPacks = 1;
      allocateMasterPack();
      cards = new Card[56];
      init(numPacks);
   }
   
   
   /**
    * Constructor for Deck
    * if numPacks exceed max packs of 6, sets 
    * to 6 if True
    * 
    * @param numPacks 
    *        - total packs of cards to include 
    *          in Deck
    */
   public Deck(int numPacks)
   {
      if (numPacks > MAX_CARDS) 
         numPacks = MAX_CARDS;
      this.numPacks = numPacks;
      allocateMasterPack();
      cards = new Card[56 * numPacks];
      init(numPacks);
   }

   
   /**
    * Restocks the deck to the original full 
    * condition in preparation for a fresh
    * deal
    * 
    * @param numPacks
    *           - how many packs the original 
    *           created deck object contained
    */
   public void init(int numPacks)
   {
      int i = 0;
      topCard = 0;
      char cardValue = 'e';
      Card.Suit suitValue = Card.Suit.CLUBS;
      
      // for each pack
      for (int j = 1; j <= numPacks; j++)
      {
         // for each card
         for (i = 0; i < 56; i++)
         {
            // grab values from master pack and fill deck
            cardValue = Deck.masterPack[i].getValue();
            suitValue = Deck.masterPack[i].getSuit();
            cards[topCard++] = new Card(cardValue, suitValue);
         }
      }
   }

   
   /**
    * Mixes up the cards with the help of a 
    * standard random number generator
    */
   public void shuffle()
   {
      boolean check[] = new boolean[topCard];
      Random r = new Random();
      Card[] temp = new Card[topCard];

      for (int j = 0; j < topCard; j++)
      {
         temp[j] = new Card(cards[j]);
      }

      for (int i = 0; i < topCard; i++)
      {
         int rand = r.nextInt(topCard);
         while (check[rand])
         {
            rand = r.nextInt(topCard);
         }
         check[rand] = true;
         cards[i].set(temp[rand].getValue(), temp[rand].getSuit());
      }
   }

   
   /**
    * Deals cards to players
    * 
    * @return - Removes top Card from deck 
                  object and deals to player
    */
   public Card dealCard()
   {
    //Check if there are still Cards in the Deck.
      if (topCard <= 0)
      {
         Card badCard = new Card('B', Card.Suit.SPADES);
         return badCard;
      }
      
      Card temp = new Card(cards[--topCard]);
      cards[topCard] = null;
      return temp;
   }

   
   /*
    * Public accessor method
    *
    * @return - Returns the index to the card 
    *           on top of a deck object
    */
   public int getTopCard()
   {
      return topCard;
   }

   
   /*
    * Accessor for an individual card
    * returns a card with errorFlag set to true
    *  if k is bad
    *
    * @param k
    *           - checks if this value is out 
    *             of range for number of cards
    * @return - 
    */
   public Card inspectCard(int k)
   {
      if (k >= topCard || k < 0)
         return new Card('e', Card.Suit.DIAMONDS);
      else
         return new Card(cards[k]);
   }

   
   /*
    * Sets each card in masterDeck[] starting 
    * with aces and so on
    */
   private static void allocateMasterPack()
   {
      // check if masterPack[] has been initialized
      if (masterPack[0] != null)
      {
         return;
      }

      // top index position for masterPack[]
      int masterIndex = 0; 
      // total types of cards e.g. A, 2, J, 3, etc.
      int totalTypeCards = 14; 
      // creates array of Suits of enum type
      Card.Suit[] suits = Card.Suit.values();

      for (int cardValue = 1; cardValue < totalTypeCards + 1; cardValue++)
      {
         for (int cardSuit = 0; cardSuit < suits.length; cardSuit++)
         {
            if (cardValue >= 2 && cardValue <= 9)
            {
               masterPack[masterIndex++] = new 
                     Card((char) (cardValue + 48), suits[cardSuit]);
               continue;
            } 
            else if (cardValue == 1)
            {
               masterPack[masterIndex++] = new Card('A', suits[cardSuit]);
               continue;
            } 
            else if (cardValue == 10)
            {
               masterPack[masterIndex++] = new Card('T', suits[cardSuit]);
               continue;
            } 
            else if (cardValue == 11)
            {
               masterPack[masterIndex++] = new Card('J', suits[cardSuit]);
               continue;
            } 
            else if (cardValue == 12)
            {
               masterPack[masterIndex++] = new Card('Q', suits[cardSuit]);
               continue;
            } 
            else if (cardValue == 13)
            {
               masterPack[masterIndex++] = new Card('K', suits[cardSuit]);
               continue;
            }
            else
               masterPack[masterIndex++] = new Card('X', suits[cardSuit]);
         }
      }
   }
   
   
   /**
    * Checks if Card is missing from Deck before adding
    * @param card - the card to be added
    * @return - true if Card was missing and was added, false otherwise
    */
   public boolean addCard(Card card)
   {
      for (int i = 0; i < topCard; i++)
      {
         if (card.equals(cards[i]))
            return false;
      }
      cards[topCard++] = new Card(card);    
      return true;
      
   }
   
   
   /**
    * Removes specific card from the deck and puts the current 
    * top card into its place
    * @param card - the specific card you wish to remove
    * @return - false if card to remove is not in deck
    */
   public boolean removeCard(Card card)
   {
      for (int i = 0; i < topCard; i++)
      {
         if (card.equals(cards[i]))
         {
            cards[i] = new Card(cards[topCard - 1]);
            cards[topCard - 1] = null;
            return true;
         }
      }
      return false;
   }
   
   
   /**
    * Calls Card's sort() to put cards in deck into order according
    * to their values
    */
   public void sort()
   {
      Card.arraySort(cards, topCard);
   }
   
   
   /**
    * Gets number of cards
    * @return - the number of initilized Cards in a deck
    */
   public int getNumCards()
   {
      return topCard;
   }
   //End of Deck Class
 
}
   class PlayListener implements ActionListener
   {
      BooleanResult cardNotPressed;
      public PlayListener(BooleanResult result)
      {
         this.cardNotPressed = result;
      }
      public void actionPerformed (ActionEvent e)
      {
         this.cardNotPressed.setResult(false);
      }
   } 






