package hust.wzb.service;

import hust.wzb.reflex.GameConf;
import hust.wzb.view.Piece;

import java.util.ArrayList;
import java.util.List;

public class GameService {
	private GameConf config;
	private List<String> row;
	private List<String> column;
	Piece coordinate;

	public GameService(GameConf config) {
		this.config = config;
		// GameInit init = new GameInit(config);
		initRowAndColumn();
		// coordinate = init.getCoordinate();
	}

	public void initRowAndColumn() {
		row = new ArrayList<String>();
		column = new ArrayList<String>();

		for (int i = 0; i < this.config.getRow(); i++) {
			row.add("-1");
		}

		for (int i = 0; i < this.config.getColumn(); i++) {
			column.add("-1");
		}
	}

	public void start() {
		updateRowAndColumn();
	}

	// 更新显示坐标轴与待选择的坐标
	private void updateRowAndColumn() {
		GameInit init = new GameInit(config);
		row = init.getRow();
		column = init.getColumn();
		coordinate = init.getCoordinate();
	}

	public boolean getPiece(float touchX, float touchY, Piece p) {
		int relativeX = (int) touchX - config.getBeginX();
		int relativeY = (int) touchY - config.getBeginY();

		int currentRow = (relativeY + config.getBoardWidth() / 2)
				/ config.getBoardWidth();
		int currentColumn = (relativeX + config.getBoardWidth() / 2)
				/ config.getBoardWidth();

		if (currentRow < 1 || currentRow > config.getRow() || currentColumn < 1
				|| currentColumn > config.getColumn()) {
			p = null;
			return false;
		} else {
			int x = currentColumn * config.getBoardWidth() + config.getBeginX();
			int y = currentRow * config.getBoardWidth() + config.getBeginY();
			p.setX(x);
			p.setY(y);

			if (row.get(currentRow - 1) == coordinate.getRow()
					&& column.get(currentColumn - 1) == coordinate.getColumn())
				return true;
			else
				return false;
		}
	}

	public List<String> getRow() {
		return row;
	}

	public List<String> getColumn() {
		return column;
	}

	public Piece getCoordinate() {
		return this.coordinate;
	}
}
