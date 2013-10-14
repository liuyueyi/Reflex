package hust.wzb.view;

public class Piece {
	// 坐标
	private int x;
	private int y;
	// 在网格中的行列
	private String row;
	private String column;
	
	public Piece(){
		
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return this.x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setRow(String row){
		this.row = row;
	}
	
	public String getRow(){
		return this.row;
	}
	
	public void setColumn(String column){
		this.column = column;
	}
	
	public String getColumn(){
		return this.column;
	}
}
