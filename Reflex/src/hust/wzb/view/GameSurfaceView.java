package hust.wzb.view;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	private SurfaceHolder holder;
	private MyThread myThread;
	
	public GameSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		holder = this.getHolder();
		holder.addCallback(this);
		myThread = new MyThread(holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		myThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public class MyThread extends Thread{
		private SurfaceHolder holder;
		public MyThread(SurfaceHolder holder){
			this.holder = holder;
		}
		
		@Override
		public void run(){
			
		}
	}

}
