package com.bean;

import java.util.LinkedList;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.attributes.COLOR;
import com.services.IGameHelperService;

@Component
public class Game {
	
	@Autowired 
	private IGameHelperService service;

	@Autowired 
	private GameBoard gameBoard;
	
	private LinkedList <Player> playerList=new LinkedList<>();
	Scanner scanInput;
	
	@PostConstruct
	public void init(){
		Player p1= new Player(COLOR.RED);
		Player p2 =new Player(COLOR.BLACK);
		playerList.add(p1);
		playerList.add(p2);
		gameBoard.init();
		scanInput = new Scanner(System.in);
	}
	
	public void start() {
		gameBoard.prepareGUI(this);
		boolean gameOver=false;
		while(service.piecesNotEmpty(gameBoard.getSidePiecesMap()) && !gameOver) {
			Player playerTurn = playerList.peekFirst();
			gameOver=takeTurn(playerTurn);
		}	
	}

	public boolean takeTurn(Player player) {
		boolean isGameOver =false;
		System.out.println("Player "+player.getColor()+" Enter column value");
		int x = scanInput.nextInt()%GameBoard.COLUMN;
		System.out.println("Player "+player.getColor()+" Enter row value");
		int y = scanInput.nextInt()%GameBoard.ROW;
		Coordinates input=new Coordinates(x,y);
		
		if (gameBoard.placePiece(player, input)) {
			substractPiece(player);
			if (service.isGameWon(gameBoard,player)) {
				System.out.println(player.getColor()+ " won.!");
				isGameOver=true; 
			} else if (gameBoard.getSidePiecesMap().get(COLOR.BLACK)==0 && gameBoard.getSidePiecesMap().get(COLOR.RED)==0) {
				System.out.println("No more pieces left!");
				isGameOver=true; 
			}
			endTurn();
		} else {
			System.out.println("Not a Valid location");
		}
		return isGameOver;	
	}
	
	public void endTurn() {
		Player p1=playerList.remove();
		playerList.add(p1);
		System.out.println("End Turn");
	}

	void substractPiece(Player player) {
		Integer value = gameBoard.getSidePiecesMap().get(player.getColor());
		gameBoard.getSidePiecesMap().put(player.getColor(),value-1);
	}

	public LinkedList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(LinkedList<Player> playerList) {
		this.playerList = playerList;
	}

	public IGameHelperService getService() {
		return service;
	}

	public void setService(IGameHelperService service) {
		this.service = service;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	
}
