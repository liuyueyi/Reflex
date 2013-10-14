package hust.wzb.reflex;

import java.io.Serializable;

public class GameConf implements Serializable{
	private int screenHeigth, screenWidth;
	// 棋盘的类型
	private int type = 1;
	// 坐标是否按顺序排放，true是，false不是
	private boolean order = true;

	// 棋盘的行列
	private int column = 5;
	private int row = 6;
	// 间距
	private int boardWidth;

	private int beginX;
	private int beginY;
	
	private int pieceRadius;
	private int circleRadius;

	public GameConf( int type, boolean isOrder,
			int screenHeigth, int screenWidth) {
		this.type = type;
		this.order = isOrder;
		switch (type) {
		case 1:
			column = 5;
			row = 6;
			break;
		case 2:
			column = 6;
			row = 8;
			break;
		case 3:
			column = 8;
			row = 10;
			break;
		case 4:
			column = 14;
			row = 16;
			break;
		}

		this.boardWidth = setBoardWidth(screenHeigth, screenWidth);
		this.beginX = boardWidth / 2;
		this.beginY = 80;
		this.circleRadius = this.boardWidth / 3;
		this.pieceRadius = this.boardWidth / 10;
	}

	private int setBoardWidth(int screenHeigth, int screenWidth) {
		this.screenHeigth = screenHeigth;
		this.screenWidth = screenWidth;
		int temp = (screenHeigth - 80) / (row + 3);
		int temp2 = screenWidth / (column + 3);
		
		return (temp > temp2) ? temp2 : temp;
	}
	
	public boolean isOrder(){
		return this.order;
	}
	
	public void setOrder(boolean order){
		this.order = order;
	}
	
	public int getBeginX(){
		return this.beginX;
	}
	
	public int getBeginY(){
		return this.beginY;
	}
	
	public int getType(){
		return this.type;
	}
	
	public void setType(int type){
		this.type = type;
	}

	public int getColumn(){
		return this.column;
	}
	
	public void setColumn(int column){
		this.column = column;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public void setRow(int row){
		this.row = row;
	}
	
	public int getBoardWidth(){
		return this.boardWidth;
	}
	
	public int getPieceRadius(){
		return this.pieceRadius;
	}
	
	public int getCircleRadius(){
		return this.circleRadius;
	}
	
	public int getScreenHeight(){
		return this.screenHeigth;
	}
	
	public int getScreenWidth(){
		return this.screenWidth;
	}
	
}
