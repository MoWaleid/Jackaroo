package model.card;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import engine.GameManager;
import engine.board.BoardManager;
import model.card.standard.*;
import model.card.wild.*;

public class Deck {
	private final static String CARDS_FILE = "Cards.csv";
	private static ArrayList<Card> cardsPool;
	
	public static void loadCardPool(BoardManager boardManager, GameManager gameManager) throws IOException{
		cardsPool = new ArrayList<>(); 
		BufferedReader br = new BufferedReader(new FileReader(CARDS_FILE));
		while(br.ready()){
			String line = br.readLine();
			String[] data = line.split(",");
			if (data.length == 0) 
				throw new IOException(line);
			int code = Integer.parseInt(data[0]);
			int frequency = Integer.parseInt(data[1]);
			String name = data[2];
			String description = data[3];
			if(code <= 13){
				int rank = Integer.parseInt(data[4]);
				Suit suit = Suit.valueOf(data[5]);
				switch (code){
				
				case 0 : while(frequency-- > 0) cardsPool.add(new Standard(name, description, rank, suit, boardManager, gameManager)); break;
				case 1 : while(frequency-- > 0) cardsPool.add(new Ace(name, description, suit, boardManager, gameManager)); break;
				case 4 : while(frequency-- > 0) cardsPool.add(new Four(name, description, suit, boardManager, gameManager)); break;
				case 5 : while(frequency-- > 0) cardsPool.add(new Five(name, description, suit, boardManager, gameManager)); break;
				case 7 : while(frequency-- > 0) cardsPool.add(new Seven(name, description, suit, boardManager, gameManager)); break;
				case 10: while(frequency-- > 0) cardsPool.add(new Ten(name, description, suit, boardManager, gameManager)); break;
				case 11: while(frequency-- > 0) cardsPool.add(new Jack(name, description, suit, boardManager, gameManager)); break;
				case 12: while(frequency-- > 0) cardsPool.add(new Queen(name, description, suit, boardManager, gameManager)); break;
				case 13: while(frequency-- > 0) cardsPool.add(new King(name, description, suit, boardManager, gameManager)); break;
				default: throw new IOException(line);
				}
			}
			else{
				switch (code){
				case 14: while(frequency-- > 0) cardsPool.add(new Burner(name, description, boardManager, gameManager)); break;
				case 15: while(frequency-- > 0) cardsPool.add(new Saver(name, description, boardManager, gameManager)); break;
				default: throw new IOException(line);
				}
			}
		}
		br.close();
	}
	
	public static ArrayList<Card> drawCards(){
		ArrayList<Card> hand = new ArrayList<>();
		Collections.shuffle(cardsPool);
		for(int i = 0; i < 4; i++)
			hand.add(cardsPool.remove(0));
		return hand;
	}
	
//	public static ArrayList<Card> drawspecial1(){
//		ArrayList<Card> hand = new ArrayList<>();
//		boolean [] flags = new boolean [4];
//		for (int i = 0 ; i < cardsPool.size() ; i++){
//			if (cardsPool.get(i) instanceof Seven && !flags[0]) {hand.add(cardsPool.remove(i)); flags[0] = true; }
//			if (cardsPool.get(i) instanceof King  && !flags[1]) {hand.add(cardsPool.remove(i)); flags[1] = true; }
//			if (cardsPool.get(i) instanceof Queen && !flags[2]) {hand.add(cardsPool.remove(i)); flags[2] = true; }
//			if (cardsPool.get(i) instanceof Standard && !flags[3]) {hand.add(cardsPool.remove(i)); flags[3] = true; }
//		}
//		return hand;
//	}
//	
//	public static ArrayList<Card> drawspecial2(){
//		ArrayList<Card> hand = new ArrayList<>();
//		boolean [] flags = new boolean [4];
//		for (int i = 0 ; i < cardsPool.size() ; i++){
//			if (cardsPool.get(i) instanceof Saver && !flags[0]) {hand.add(cardsPool.remove(i)); flags[0] = true; }
//			if (cardsPool.get(i) instanceof Burner  && !flags[1]) {hand.add(cardsPool.remove(i)); flags[1] = true; }
//			if (cardsPool.get(i) instanceof Queen && !flags[2]) {hand.add(cardsPool.remove(i)); flags[2] = true; }
//			if (cardsPool.get(i) instanceof Standard && !flags[3]) {hand.add(cardsPool.remove(i)); flags[3] = true; }
//		}
//		return hand;
//	}
//	
	public static void refillPool(ArrayList<Card> cards){
		cardsPool.addAll(cards);
	}
	
	public static int getPoolSize(){
		return cardsPool.size();
	}
}