package hust.wzb.view;

import hust.wzb.reflex.GameConf;
import hust.wzb.service.GameService;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
	private Paint paintLine, paintText, paintCircle;
	private GameConf config;
	private GameService gameService;
	private Piece errorPiece;
	private Piece currentPiece;
	private int playingType;

	private Bitmap startBitmap;

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paintLine = new Paint();
		paintLine.setColor(Color.GRAY);
		paintLine.setStrokeWidth(3);

		paintText = new Paint();
		paintText.setColor(Color.BLUE);
		paintText.setTextSize(23);

		paintCircle = new Paint();
		paintCircle.setColor(Color.BLACK);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (this.gameService == null) {
			return;
		}

		List<String> row = gameService.getRow();
		List<String> column = gameService.getColumn();

		int add = config.getBoardWidth();
		for (int i = 0; i < config.getRow(); i++) {
			canvas.drawLine(config.getBeginX() + add / 2, config.getBeginY()
					+ add * (i + 1),
					config.getBeginX() + add * config.getColumn() + add / 2,
					config.getBeginY() + add * (i + 1), paintLine);

			canvas.drawText(row.get(i), config.getBeginX(), config.getBeginY()
					+ add * (i + 1) - 3, paintText);

			for (int j = 0; j < config.getColumn(); j++) {
				canvas.drawCircle(config.getBeginX() + add * (j + 1),
						config.getBeginY() + add * (i + 1),
						config.getPieceRadius(), paintLine);
			}
		}

		for (int j = 0; j < config.getColumn(); j++) {
			canvas.drawLine(config.getBeginX() + add * (j + 1),
					config.getBeginY() + add / 2, config.getBeginX() + add
							* (j + 1),
					config.getBeginY() + add * config.getRow() + add / 2,
					paintLine);

			canvas.drawText(column.get(j), config.getBeginX() + add * (j + 1)
					- 3, config.getBeginY(), paintText);
		}

		switch (this.playingType) {
		case 0: // 还没开始游戏
			canvas.drawBitmap(
					startBitmap,
					config.getScreenWidth() /2  - startBitmap.getWidth(),
					config.getBoardWidth() * 3 + 70,
					paintLine);
			break;
		case 1: // 3s 准备期间
			
		case 2: // 正在运行
			break;
		}

		if (this.errorPiece != null) {
			paintCircle.setColor(Color.BLACK);
			canvas.drawCircle(errorPiece.getX(), errorPiece.getY(),
					config.getCircleRadius(), paintCircle);
		}
		if (currentPiece != null) {
			paintCircle.setColor(Color.RED);
			canvas.drawCircle(currentPiece.getX(), currentPiece.getY(),
					config.getCircleRadius(), paintCircle);
		}
	}

	public void setGameConf(GameConf config) {
		this.config = config;
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	public void startGame() {
		gameService.start();
	}

	public void setCurrentPiece(Piece piece) {
		this.currentPiece = piece;
	}

	public void setErrorPiece(Piece errorPiece) {
		this.errorPiece = errorPiece;
	}

	public void setStartBitmap(Bitmap startBitmap) {
		this.startBitmap = startBitmap;
	}

	public void setPlayingType(int playingType) {
		this.playingType = playingType;
	}
}
