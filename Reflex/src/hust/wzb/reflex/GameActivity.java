package hust.wzb.reflex;

import hust.wzb.service.GameService;
import hust.wzb.view.GameView;
import hust.wzb.view.Piece;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
	private GameConf config;
	private TextView timeTextView;
	// ������ʾ������ı���
	private TextView coordinateTextView;
	private GameView gameView;
	private GameService gameService;
	// ��������ѡ�����ĵ�
	private Piece errorPiece;
	// ��ǰѡ�еĵ�
	private Piece currentPiece;
	// ��Ϸ״̬ 0:δ��ʼ 	1:׼����ʼ	2:������Ϸ
	private int playingType;
	// ��ʱ(ms)
	private long gameTime = 0;
	private Bitmap startBitmap;
	
	private DecimalFormat df = new DecimalFormat("000.00");
	final MyHandler handler = new MyHandler();
	public class MyHandler extends Handler{
		public volatile boolean isRun = true;
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0x123:
				if(isRun){
					timeTextView.setText(df.format((float)gameTime / 1000) + " s");
					gameTime += 10;
				}
				break;
			}
			super.handleMessage(msg);
		}
	}
	
	private MyThread thread;
	public class MyThread implements Runnable{
		public volatile boolean isRun = true;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isRun){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0x123);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		playingType = 0;
		// ��MainActivity���GameConf
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		config = (GameConf) data.getSerializable("GameConf");

		timeTextView = (TextView) findViewById(R.id.timeTextView);
		coordinateTextView = (TextView) findViewById(R.id.CoordinateTextView);
		gameService = new GameService(config);

		gameView = (GameView) findViewById(R.id.gameView);
		gameView.setGameConf(config);
		gameView.setGameService(gameService);
		gameView.setPlayingType(0);
		startBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.start);
		gameView.setStartBitmap(startBitmap);

		gameView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					gameViewTouchDown(arg1);
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					gameViewTouchUp(arg1);
				}
				return true;
			}

		});
	}

	private void init() {
		this.playingType = 0;
		this.gameTime = 0;
		this.currentPiece = null;
		this.errorPiece = null;
		//this.gameView.setCurrentPiece(currentPiece);
		this.gameView.setErrorPiece(errorPiece);
		this.gameView.setPlayingType(0);
		this.timeTextView.setText("0 ms");
		this.coordinateTextView.setText("");

		this.gameView.postInvalidate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	private void gameViewTouchDown(MotionEvent e) {
		switch(this.playingType){
		case 0:
			// ��Ϸ��û������ʱ��������ʼ������Ϸ
			// ���õ���ʱ����
			gameService.start();
			new CountDownTimer(4000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {
					timeTextView.setText("����ʱ��" + millisUntilFinished / 1000
							+ " s");
					playingType = 1;
				}

				@Override
				public void onFinish() {
					startGame(0);
					// ����ʱ��������ʼ��Ϸ
					playingType = 2;
					gameView.setPlayingType(playingType);
					gameView.postInvalidate();
				}
			}.start();
			this.gameView.setCurrentPiece(null);
			this.gameView.setPlayingType(1);
			this.gameView.postInvalidate();
			break;
		
		case 1:
			// ׼���׶δ�����Ч
			break;
			
		case 2:
			// ��Ϸ��ʼ�������ж�
			// ��ô���������
			float touchX = e.getX();
			float touchY = e.getY();
			if (currentPiece == null) {
				currentPiece = new Piece();
			}
			if (this.gameService.getPiece(touchX, touchY, currentPiece)) {
				// ���ѡ����ȷ, ֹͣhandler�߳���thread��run�����е�ѭ��
				handler.isRun = false;
				thread.isRun = false;
				stopTimer();
				this.gameView.setCurrentPiece(currentPiece);
				this.gameView.postInvalidate();
				timeTextView.setText(df.format((float)gameTime / 1000) + " s");
				// ��ʾ���
				showResult();
			} else {
				// ѡ�����
				if (errorPiece == null) {
					errorPiece = new Piece();
				}
				if(currentPiece != null)
					errorPiece = currentPiece;
				this.gameView.setErrorPiece(errorPiece);
				this.gameView.postInvalidate();
			}
			break;
		}
		
		/*
		if (this.playingType == 0) {
			// ��Ϸ��û������ʱ��������ʼ������Ϸ
			// ���õ���ʱ����
			gameService.start();
			new CountDownTimer(4000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {
					timeTextView.setText("����ʱ��" + millisUntilFinished / 1000
							+ " s");
					playingType = 1;
				}

				@Override
				public void onFinish() {
					startGame(0);
					// ����ʱ��������ʼ��Ϸ
					playingType = 2;
					gameView.setPlayingType(playingType);
					gameView.postInvalidate();
				}
			}.start();
			//this.gameService.updateRowAndColumn();
			this.gameView.setCurrentPiece(null);
			this.gameView.setPlayingType(1);
			this.gameView.postInvalidate();
			return;
		}
		if(this.playingType == 1){
			// ׼���׶δ�����Ч
			return;
		}
		// ��ô���������
		float touchX = e.getX();
		float touchY = e.getY();
		if (currentPiece == null) {
			currentPiece = new Piece();
		}
		if (this.gameService.getPiece(touchX, touchY, currentPiece)) {
			// ���ѡ����ȷ
			handler.isRun = false;
			stopTimer();
			this.gameView.setCurrentPiece(currentPiece);
			this.gameView.postInvalidate();
			showResult();
		} else {
			// ѡ�����
			if (errorPiece == null) {
				errorPiece = new Piece();
			}
			if(currentPiece != null)
				errorPiece = currentPiece;
			this.gameView.setErrorPiece(errorPiece);
			this.gameView.postInvalidate();
		} */
	}

	private void gameViewTouchUp(MotionEvent e) {
		// this.gameView.postInvalidate();
	}

	private void stopTimer() {
		this.thread = null;
	}

	private void startGame(int gameTime) {
		initParameters(gameTime);
		// ������ʱ��
		new Thread(this.thread).start();
		// ��ʾ�������
		coordinateTextView.setText("(" + gameService.getCoordinate().getRow()
				+ "," + gameService.getCoordinate().getColumn() + ")");
	}

	// �[���_ʼ����ʼ��һЩ����
	private void initParameters(int gameTime){
		if (this.thread != null) {
			stopTimer();
		}
		this.gameTime = gameTime;
		if (this.gameTime == 0) {
			if (this.errorPiece != null) {
				errorPiece = null;
				this.gameView.setErrorPiece(errorPiece);
			}
			if (this.currentPiece != null) {
				currentPiece = null;
				this.gameView.setCurrentPiece(currentPiece);
			}
			this.timeTextView.setText("0ms");
			//this.gameView.startGame();
		}

		this.thread = new MyThread();
		this.thread.isRun = true;
		this.handler.isRun = true;
	}
	
	// ��ʾ����ʱ��
	private void showResult() {
		Toast.makeText(this, "��Ϸʱ��: " + ((float)gameTime / 1000) + " s", Toast.LENGTH_SHORT)
				.show();
		init();
	}
}
