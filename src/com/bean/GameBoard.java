package com.bean;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.springframework.stereotype.Component;

import com.attributes.COLOR;

@Component
public class GameBoard { 

	static final int ROW=6;
	static final int COLUMN =7;
	static final int ELEMENT_SIZE=ROW*COLUMN;
	private int[][] boardMap =new int [ROW][COLUMN];
	Coordinates lastPlaced;
	private JFrame mainFrame;
	private Map<COLOR,Integer> sidePiecesMap;
	
   /*  
    * Java is considered "row major", meaning that it does rows first. This is because a 2D array is an "array of arrays".
    * To initialize a 1D array to a particular value use java.util.Arrays.fill()
 	*/
	
	public void init (){
	 	sidePiecesMap = new EnumMap<>(COLOR.class);
		sidePiecesMap.put(COLOR.RED,21);
		sidePiecesMap.put(COLOR.BLACK,21);
	}
	 
    public boolean placePiece(Player player,Coordinates coordinates) {
    	boolean returnValue = false;
    	if (isValidLocation(coordinates)) {
    		lastPlaced = coordinates;
    		returnValue=true;
    		if (player.getColor()==COLOR.RED) {
    			boardMap[coordinates.y][coordinates.x]=1;
    		
    		} else {
    			boardMap[coordinates.y][coordinates.x]=2;
      		}
    	}
    	return returnValue;
    }

	private boolean isValidLocation(Coordinates coordinates) {
		return boardMap[coordinates.y][coordinates.x]==0;
	}

	public boolean isPlayerVictory(COLOR color) {
		int colorValue= color.getColorCode();
		//keep last put location in gameboard and examine the subset of array for connect 4
		return isVictoryUsingCoordinates(lastPlaced, colorValue);
	}

	private boolean isVictoryUsingCoordinates(Coordinates lastPlaced, int colorValue) {
		if (lastPlaced == null)
			return false;
		else {
			return (isConnectFourHoriziontically(lastPlaced, colorValue)
				|| isConnectFourVertically(lastPlaced, colorValue)
				|| isConnectFourDiagonallyNE(lastPlaced, colorValue)
				|| isConnectFourDiagonallyNW(lastPlaced, colorValue));
		}
	}

	private boolean isConnectFourHoriziontically(Coordinates lastPlaced, int colorValue) {
		boolean returnValue=false;
		int leftLimit=getLeftBoundry(lastPlaced.x);
	    int rightLimit=getRightBoundry(lastPlaced.x);
	    int count=0;
	    for (int i=leftLimit;i<=rightLimit;i++) {
	    	if (boardMap[lastPlaced.y][i]==colorValue) {
	    		count++;
	    		if (count==4) {
	    			returnValue=true;
	    			break;
	    		}
	    	} else {
	    		count=0;
	    	}
	    }
	    //System.out.println("leftLimitX :"+leftLimit+" Y :"+lastPlaced.y);
	    //System.out.println("rightLimitX :"+rightLimit+" Y :"+lastPlaced.y);
		return returnValue;
	}

	private boolean isConnectFourVertically(Coordinates lastPlaced, int colorValue) {
		boolean returnValue=false;
		int lowerLimit=getLowerBoundry(lastPlaced.y);
	    int upperLimit=getUpperBoundry(lastPlaced.y);
	    int count=0;
	    for (int j=lowerLimit;j<=upperLimit;j++) {	    	
	    	if (boardMap[j][lastPlaced.x]==colorValue) {
	    		count++;
	    		if (count==4) {
	    			returnValue=true;
	    			break;
	    		}
	    	} else {
	    		count=0;
    		}
	    }
	    //System.out.println("X :"+lastPlaced.x+" lowerLimitY :"+lowerLimit);
	    //System.out.println("X :"+lastPlaced.x+" upperLimitY :"+upperLimit);
	    return returnValue;
	}
	
	private boolean isConnectFourDiagonallyNE(Coordinates lastPlaced, int colorValue) {
		boolean returnValue=false;
		Coordinates lowerDiagonalLimit=getLowerLeftDiagonalBoundry(lastPlaced.x,lastPlaced.y);
		Coordinates upperDiagonalLimit=getUpperRightDiagonalBoundry(lastPlaced.x,lastPlaced.y);
		Coordinates current= lowerDiagonalLimit;
		int count=0;
	    while (current.lessOrEqualsNE(upperDiagonalLimit)) {	    	
	    	if (boardMap[current.y][current.x]==colorValue) {
	    		count++;
	    		if (count==4) {
	    			returnValue=true;
	    			break;
	    		}
	    	} else {
	    		count=0;
    		}
	    	current=new Coordinates(current.x+1,current.y+1);
	    }
	    //System.out.println("lowerDiagonalLimit X :"+lowerDiagonalLimit.x+" lowerDiagonalLimit Y :"+lowerDiagonalLimit.y);
	    //System.out.println("upperDiagonalLimit X :"+upperDiagonalLimit.x+" upperDiagonalLimit Y :"+upperDiagonalLimit.y);
	    return returnValue;
	}
	
	private boolean isConnectFourDiagonallyNW(Coordinates lastPlaced, int colorValue) {
		boolean returnValue=false;
		Coordinates lowerDiagonalLimit=getLowerRightDiagonalBoundry(lastPlaced.x,lastPlaced.y);
		Coordinates upperDiagonalLimit=getUpperLeftDiagonalBoundry(lastPlaced.x,lastPlaced.y);
		Coordinates current= lowerDiagonalLimit;
		int count=0;
	    while (current.lessOrEqualsNW(upperDiagonalLimit)) {	    	
	    	if (boardMap[current.y][current.x]==colorValue) {
	    		count++;
	    		if (count==4) {
	    			returnValue=true;
	    			break;
	    		}
	    	} else {
	    		count=0;
    		}
	    	current=new Coordinates(current.x-1,current.y+1);
	    }
	    //System.out.println("lowerDiagonalLimit X :"+lowerDiagonalLimit.x+" lowerDiagonalLimit Y :"+lowerDiagonalLimit.y);
	    //System.out.println("upperDiagonalLimit X :"+upperDiagonalLimit.x+" upperDiagonalLimit Y :"+upperDiagonalLimit.y);
	    return returnValue;
	}	


	private Coordinates getUpperRightDiagonalBoundry(int x, int y) {
		int xLim=x;
		int yLim=y;
		int count= 0;
		while(xLim<GameBoard.COLUMN-1 && yLim<GameBoard.ROW-1  && count<3 ) {
			xLim=xLim+1;
			yLim=yLim+1;
			count++;
		}
		return new Coordinates(xLim,yLim);
	}

	private Coordinates getLowerLeftDiagonalBoundry(int x, int y) {
		int xLim=x;
		int yLim=y;
		int count= 0;
		while(xLim>0 && yLim>0 && count<3 ) {
			xLim=xLim-1;
			yLim=yLim-1;
			count++;
		}
		return new Coordinates(xLim,yLim);
	}

	private Coordinates getUpperLeftDiagonalBoundry(int x, int y) {
		int xLim=x;
		int yLim=y;
		int count= 0;
		while(xLim>0 && yLim<GameBoard.ROW-1  && count<3 ) {
			xLim=xLim-1;
			yLim=yLim+1;
			count++;
		}
		return new Coordinates(xLim,yLim);
	}

	private Coordinates getLowerRightDiagonalBoundry(int x, int y) {
		int xLim=x;
		int yLim=y;
		int count= 0;
		while(xLim<GameBoard.COLUMN-1 && yLim>0 && count<3 ) {
			xLim=xLim+1;
			yLim=yLim-1;
			count++;
		}
		return new Coordinates(xLim,yLim);
	}

	private int getLowerBoundry(int y) {
		if (y-3>=0) {
			return y-3;
		} else {
			return 0;
		}
	}
	
	private int getUpperBoundry(int y) {
		if (y+3<GameBoard.ROW-1) {
			return y+3;
		} else {
			return GameBoard.ROW-1;
		}
	}
	
	private int getLeftBoundry(int x) {
		if (x-3>=0) {
			return x-3;
		} else {
			return 0;
		}
	}
	
	private int getRightBoundry(int x) {
		if (x+3<=GameBoard.COLUMN-1) {
			return x+3;
		} else {
			return GameBoard.COLUMN-1;
		}
	}
	
	void prepareGUI(Game game){
		System.setProperty("java.awt.headless", "false"); 
		mainFrame = new JFrame("Connect Four");
		mainFrame.setSize(700,600);
      
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});

		JPanel panel = new JPanel(new GridLayout(ROW, COLUMN, 0, 0));
		for (int i = 1; i <= ELEMENT_SIZE; i++) {
			JButton l = new JButton(String.valueOf(i));
			l.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			if (i%2==0) {
				l.setBackground(Color.white);
			} else {
				l.setBackground(Color.gray);
			}
			panel.add(l);
			final Integer innerI = i;
			l.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
 	        	 //overlay with game piece
					Coordinates input=new Coordinates((COLUMN-1-(ELEMENT_SIZE-innerI)%COLUMN),(ELEMENT_SIZE-innerI)/COLUMN);
					Player player = game.getPlayerList().peekFirst();
					if (placePiece(player, input)) {
						try {
							if (COLOR.RED==player.getColor()) {
								GamePieceRed buttonR = new GamePieceRed();
								l.setIcon(new ImageIcon(buttonR.getBufferedImage()));
							}else {
								GamePieceBlack buttonB = new GamePieceBlack();
								l.setIcon(new ImageIcon(buttonB.getBufferedImage()));
							}
							l.setEnabled(false);//+disable	
						}catch (IOException e1) {
							e1.printStackTrace();
						}
						game.substractPiece(player);
						if (game.getService().isGameWon(game.getGameBoard(),player)) {
							System.out.println("We have an winner!"); 
							final MessageWindow window = new MessageWindow (mainFrame, "We Have a Winner!: "+player.getColor());
							window.show();
						}
						game.endTurn();
					} else {
						System.out.println("Not a Valid location");
					}
				}
			});
		}      
		mainFrame.add(panel);
		mainFrame.setVisible(true);  
   }

	public Map<COLOR, Integer> getSidePiecesMap() {
		return sidePiecesMap;
	}

	public void setSidePiecesMap(Map<COLOR, Integer> sidePiecesMap) {
		this.sidePiecesMap = sidePiecesMap;
	}
	
	public void reset(){
	 	sidePiecesMap = new EnumMap<>(COLOR.class);
		sidePiecesMap.put(COLOR.RED,21);
		sidePiecesMap.put(COLOR.BLACK,21);
	}
	
}
