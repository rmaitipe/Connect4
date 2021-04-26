package com.bean;

import com.attributes.COLOR;

public class Player {
	
	public Player(COLOR colorValue) {
		setColor(colorValue);
	}

	private COLOR color;
	
	public COLOR getColor() {
		return color;
	}

	public void setColor(COLOR color) {
		this.color = color;
	}

}
