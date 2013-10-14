package hust.wzb.service;

import hust.wzb.reflex.GameConf;
import hust.wzb.view.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameInit {
	private GameConf config;
	private List<String> row;
	private List<String> column;
	
	public GameInit(GameConf config){
		this.config = config;
		row = new ArrayList<String>();
		for(int i = 0; i < config.getRow(); i++){
			row.add((i+1) + "");
		}
		if(!config.isOrder()){
			Collections.shuffle(row);
		}
		
		column = new ArrayList<String>();
		for(int i = 0; i < config.getColumn(); i++){
			column.add((i+1) + "");
		}
		if(!config.isOrder()){
			Collections.shuffle(column);
		}
	}
	
	public List<String> getRow(){
		return row;
	}
	
	public List<String> getColumn(){
		return column;
	}
	
	public Piece getCoordinate(){
		Piece p = new Piece();
		Random r = new Random();
		
		int indexColumn = r.nextInt(column.size());
		int indexRow = r.nextInt(row.size());
		
		int x = config.getBeginX() + (indexColumn + 1 ) * config.getBoardWidth();
		int y = config.getBeginY() + (indexRow + 1) * config.getBoardWidth();
		
		String currentColumn = this.column.get(indexColumn);
		String currentRow = this.row.get(indexRow);
		
		p.setX(x);
		p.setY(y);
		p.setColumn(currentColumn);
		p.setRow(currentRow);
		return p;
	}
}
