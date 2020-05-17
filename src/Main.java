public class Main {

    public static void main(String[] args) {
        game game = new game();

        game.innit(); //initialize the game 
        while (!game.quite) {
            game.turn(); //method for making turns
            game.printWinner(game.checkWinner()); //checking and printing the winner 

        }
    }

}
