package es.alexmj.a_ud7_ejf_mycalc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


/**
 * Clase que implementa un BoundService con operaciones de:
 * 	- Suma
 *  - Resta
 *  - Multiplicacion
 *  - Division
 *  - Factorial
 * @author Alejandro.Marijuan
 *
 */
public class OperationsBackgroundService extends Service{

	private static final String TAG = "OperationsBackgroundService--> ";
	private OperationsBinder mOperationsBackgroundService = new OperationsBinder();
	
	/**
	 * Subclase para enlazar el servicio con la clase CalculartorActivity.
	 * @author Alejandro.Marijuan
	 *
	 */
	private class OperationsBinder extends Binder{
		OperationsBackgroundService getService(){
			Log.i(TAG, "getService");
			return OperationsBackgroundService.this;
		}// getService()
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.i(TAG, "onStartCommand");
		//##Thread que se encarga de las operaciones
		Thread thread = new Thread(){
			public void run(){
				//TODO:operaciones...
			}// run()
		};
		thread.start();
		return START_NOT_STICKY;
	}// onStartCommand()
	
	/**
	 * Devuelve la referencia al servicio OperationsBackgroundService.
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {	
		Log.i(TAG, "onBind");
		return mOperationsBackgroundService;
	}// onBind()
	
}