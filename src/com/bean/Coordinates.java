package com.bean;

public class Coordinates {
	
	int x;
	int y;
	
	Coordinates(int xVal, int yVal){
		x=xVal;
		y=yVal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public boolean lessOrEqualsNE(Coordinates upperDiagonalLimit) {
		return (upperDiagonalLimit.x>=x && upperDiagonalLimit.y>=y);
	}

	public boolean lessOrEqualsNW(Coordinates upperDiagonalLimit) {
		return (upperDiagonalLimit.x<=x && upperDiagonalLimit.y>=y);
	}
	
}
