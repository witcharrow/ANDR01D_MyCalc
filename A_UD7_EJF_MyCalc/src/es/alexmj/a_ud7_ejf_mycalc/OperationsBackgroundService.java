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
	//##Binder given to clients
	private OperationsBinder mOperationsBackgroundService = new OperationsBinder();
	// Random number generator
    private final Random mGenerator = new Random();
    
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