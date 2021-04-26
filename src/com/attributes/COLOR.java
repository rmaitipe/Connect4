package com.attributes;

public enum COLOR {
	RED(1),
	BLACK(2);
	
	COLOR(Integer i) {
		setColorCode(i);
	}

	public Integer getColorCode() {
		return colorCode;
	}

	private void setColorCode(Integer colorCode) {
		this.colorCode = colorCode;
	}

	private Integer colorCode;	
	
}
