package es.alexmj.a_ud7_ejf_mycalc;

import java.util.Random;

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
	
	private Random mGenerator;
	private int num1;
	private int num2;
	private int suma;
	private long resta;
	private long multiplicacion;
	private float division;
	private double factorial;   
    
	/**
	 * Subclase para enlazar el servicio con la clase CalculartorActivity.
	 * @author Alejandro.Marijuan
	 *
	 */
	public class OperationsBinder extends Binder{
		
		/**
		 * Metodo encargado de devolver la referencia al servicio
		 * 	OperationsBackgroundService.
		 * @return referencia al servicio
		 */
		public OperationsBackgroundService getService(){
			Log.i(TAG, "getService");
			return OperationsBackgroundService.this;
		}// getService()
	}
	//##Binder given to clients
	private OperationsBinder mOperationsBackgroundService = new OperationsBinder();
	
	
	/**
	 * Thread que hace el trabajo en background
	 */
	private class CalculatorThread extends Thread{
		
		private boolean threadDone=false;

		@Override
		public void run() {
			while (!threadDone){
				num1 = getRandomNumber();
			    num2 = getRandomNumber();		    
			    
			    suma = getSuma(num1,num2);		    
			    resta = getResta(num1,num2);
			    multiplicacion = getMultiplicacion(num1,num2);
			    division = getDivision(num1,num2);
			    factorial = getFactorial(num1);
			    
			    Log.i(TAG, "Numeros generados: " + num1+" y "+num2);
			    Log.i(TAG, num1+"+"+num2+"="+suma);
			    Log.i(TAG, num1+"-"+num2+"="+resta);		            
			    Log.i(TAG, num1+"*"+num2+"="+multiplicacion);
			    Log.i(TAG, num1+"/"+num2+"="+division);		    
			    Log.i(TAG, num1+"!"+"="+factorial);  
			    
			    threadDone=true;
			}  
			stopSelf();
		}
	};
	
	private CalculatorThread mCalculatorThread;
	
	/**
	 * Maneja las acciones a realizar por el servicio una vez lanzado, generando
	 *  previamente un aleatorio.
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.i(TAG, "onStartCommand - Service");
		
		//##Random number generator
	    mGenerator = new Random();
		
		//##Thread que se encarga de las operaciones
		mCalculatorThread = new CalculatorThread();
		mCalculatorThread.start();
		
		//##El servicio no debe seguir funcionando una vez parado, por eso 
		//	devolvemos START_NOT_STICKY
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

	/**
	 * Elimina el hilo en ejecucion.
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();		
		mCalculatorThread = null;
	}

	/**
	 * Genera un numero aleatorio del 0 al 100.
	 * @return numero aleatorio entre el 0 y el 100
	 */
	public int getRandomNumber() {
		return mGenerator.nextInt(100);
	}


	/**
	 * Suma de dos valores.
	 * @param num1 valor 1
	 * @param num2 valor 2
	 * @return resultado de la suma de num1+num2
	 */
	public int getSuma(int num1, int num2) {		
		return num1+num2;
	}
	
	/**
	 * Resta de dos valores.
	 * @param num1 valor 1
	 * @param num2 valor 2
	 * @return resultado de la resta de num1+num2
	 */
	public long getResta(int num1, int num2) {		
		return num1-num2;
	}
	
	/**
	 * Multiplicacion de dos valores.
	 * @param num1 valor 1
	 * @param num2 valor 2
	 * @return resultado de la multiplicacion de num1*num2
	 */
	public long getMultiplicacion(int num1, int num2) {		
		return num1*num2;
	}
	
	/**
	 * Division de dos valores.
	 * @param num1 valor 1
	 * @param num2 valor 2
	 * @return resultado de la division de num1/num2
	 */
	public float getDivision(float num1, float num2) {		
		return num1/num2;
	}
	
	/**
	 * Factorial de un valor.
	 * @param num1 valor 1
	 * @return resultado del factorial de num1
	 */
	public double getFactorial(int numFactorial) {		
	  if (numFactorial==0)
		  return 1;
	  else
		  return numFactorial * getFactorial(numFactorial-1);
	}
	
}