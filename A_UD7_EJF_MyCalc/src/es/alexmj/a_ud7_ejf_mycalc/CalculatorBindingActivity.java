package es.alexmj.a_ud7_ejf_mycalc;

import java.util.Random;

import es.alexmj.a_ud7_ejf_mycalc.OperationsBackgroundService.OperationsBinder;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Calculadora que ejecuta un Servicio en otro hilo de la aplicacion para evitar bloquear
 *  la interfaz al usuario. Dicho servicio ejecuta operaciones en segundo plano al hacer
 *  click sobre el botón CALCULADORA.
 *  
 * @author Alejandro.Marijuan
 *
 */
public class CalculatorBindingActivity extends Activity {
	
	private static final String TAG="CalculatorActivity--> ";
	private OperationsBackgroundService mService;
	boolean mBound = false;
	private Button mServiceButton;
	private int num1;
	private int num2;
	private int suma;
	private long resta;
	private long multiplicacion;
	private float division;
	private double factorial;
	
	/**
	 * Inicio de la activity para la Calculadora.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG,"onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);		
		
		mServiceButton=(Button) findViewById(R.id.service);
		final int colors []= {Color.RED, Color.BLACK, Color.BLUE,Color.GREEN};
		
		mServiceButton.setOnClickListener(new OnClickListener(){
			
			/** Called when a button is clicked (the button in the layout file attaches to
		      * this method with the android:onClick attribute) */
			public void onClick(View v){
				Log.i(TAG,"onClick + mBound=FALSE");
				//##Lanzamos el servicio
				onStart();
			    
				if (mBound) {
					Log.i(TAG,"onClick + mBound=TRUE");
		            // Call a method from the LocalService.
		            // However, if this call were something that might hang, then this request should
		            // occur in a separate thread to avoid slowing down the activity performance.
		            num1 = mService.getRandomNumber();
		            num2 = mService.getRandomNumber();
		            Random r = new Random();
		            
		            Toast.makeText(CalculatorBindingActivity.this, "Numeros generados: " + num1+" y "+num2, Toast.LENGTH_SHORT).show();
		            suma = mService.getSuma(num1,num2);
		            Toast.makeText(CalculatorBindingActivity.this, num1+" + "+num2+" = "+suma, Toast.LENGTH_SHORT).show();
		            resta = mService.getResta(num1,num2);
		            Toast.makeText(CalculatorBindingActivity.this, num1+" - "+num2+" = "+resta, Toast.LENGTH_SHORT).show();		            
		            multiplicacion = mService.getMultiplicacion(num1,num2);
		            Toast.makeText(CalculatorBindingActivity.this, num1+" * "+num2+" = "+multiplicacion, Toast.LENGTH_SHORT).show();
		            division = mService.getDivision(num1,num2);
		            Toast.makeText(CalculatorBindingActivity.this, num1+" / "+num2+" = "+division, Toast.LENGTH_SHORT).show();
		            factorial = mService.getFactorial(num1);
		            Toast.makeText(CalculatorBindingActivity.this, num1+"!"+" = "+factorial, Toast.LENGTH_SHORT).show();
		            
		            for (int i = 0; i < colors.length; i++) {
		            	mServiceButton.setBackgroundColor(colors[r.nextInt(3)]);
		            	mServiceButton.setTextColor(Color.WHITE);
		            } 
		        }
				
			}
		});		
		
	}// onCreate()

	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG,"onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.calculator, menu);		
		return true;
	}// onCreateOptionsMenu()
	
    /**
     * Bind to LocalService.
     * @see android.app.Activity#onStart()
     */
    @Override
    protected void onStart() {
    	Log.i(TAG,"onStart");
        super.onStart();
        Intent intent = new Intent(CalculatorBindingActivity.this, OperationsBackgroundService.class);
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);	
    }// onStart()

    /**
     * Unbind from the service.
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
    	Log.i(TAG,"onStop");
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }// onStop()

    /**
	 * Defines callbacks for service binding, passed to bindService().
	 */
	private ServiceConnection mServiceConnection = new ServiceConnection(){		
		
		/**
		 * Called when the connection with the service is established.
		 * @see android.content.ServiceConnection#onServiceConnected(android.content.ComponentName, android.os.IBinder)
		 */
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			Log.i(TAG, "onServiceConnected");
			//## We've bound to LocalService, cast the IBinder and get LocalService
			//    instance
			OperationsBinder binder = (OperationsBinder) service;
			mService = binder.getService();
			mBound = true;
		}// onServiceConnected()

		/**
		 * Called when the connection with the service disconnects unexpectedly.	
		 * @see android.content.ServiceConnection#onServiceDisconnected(android.content.ComponentName)
		 */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e(TAG, "onServiceDisconnected");
			mService=null;//¿?¿?¿?
			mBound = false;		
		}// onServiceDisconnected()		
	};

}
