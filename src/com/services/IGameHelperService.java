package com.services;

import java.util.Map;

import com.attributes.COLOR;
import com.bean.GameBoard;
import com.bean.Player;

public interface IGameHelperService {
	
	public boolean isGameWon(GameBoard board, Player player);

	public boolean piecesNotEmpty(Map<COLOR, Integer> sidePiecesMap);

}
