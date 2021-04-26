package com.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.attributes.COLOR;
import com.bean.GameBoard;
import com.bean.Player;

@Service
public class GameHelperService implements IGameHelperService {
	
	public boolean isGameWon(GameBoard board, Player player){
		return board.isPlayerVictory(player.getColor()); 
	}

	@Override
	public boolean piecesNotEmpty(Map<COLOR, Integer> sidePiecesMap) {
		List<Integer> list = new ArrayList<> (sidePiecesMap.values());
		return !list.contains(0);
	}

}
