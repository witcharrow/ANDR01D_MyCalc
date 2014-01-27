package es.alexmj.a_ud7_ejf_mycalc;

import java.util.Random;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import es.alexmj.a_ud7_ejf_mycalc.OperationsBackgroundService.OperationsBinder;

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

	
    /**
	 * Define llamadas para la conexion con el servicio (service binding),
	 *  pasado a traves de bindService().
	 */
	private ServiceConnection mServiceConnection = new ServiceConnection(){		
		
		/**
		 * LLamada cuando la conexion con el servicio se pierde de forma inesperada.	
		 * @see android.content.ServiceConnection#onServiceDisconnected(android.content.ComponentName)
		 */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e(TAG, "onServiceDisconnected");
			mService=null;
			mBound = false;		
		}// onServiceDisconnected()	
		
		/**
		 * LLamada cuando la conexion con el servicio esta establecida.
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
	};
	
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
		mServiceButton.setOnClickListener(new OnClickListener(){
			
			/** Called when a button is clicked (the button in the layout file attaches to
		      * this method with the android:onClick attribute) */
			public void onClick(View v){
				Log.i(TAG,"onClick + mBound=FALSE");
				//##Lanzamos el servicio

				Random r = new Random();
				final int colors []= {Color.RED, Color.BLACK, Color.BLUE,Color.GREEN};
				for (int i = 0; i < colors.length; i++) {
			    	mServiceButton.setBackgroundColor(colors[r.nextInt(4)]);
			    	mServiceButton.setTextColor(Color.WHITE);
			    }
				if (mBound) {
					Log.i(TAG,"onClick + mBound=TRUE");
					// Call a method from the LocalService.
		            // However, if this call were something that might hang, then this request should
		            // occur in a separate thread to avoid slowing down the activity performance.
		            Intent intent = new Intent(CalculatorBindingActivity.this, OperationsBackgroundService.class);
					startService(intent);					
		        }
			}
		});		
	}// onCreate()

    /**
     * Bind to OperationsBackgroundService (conexion con el servicio).
     * @see android.app.Activity#onStart()
     */
    @Override
    protected void onStart() {
    	Log.i(TAG,"onStart-Binding to service...");
        super.onStart();
        Intent intent = new Intent(CalculatorBindingActivity.this, OperationsBackgroundService.class);
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);	
    }// onStart()

    /**
     * Unbind from the service (desconexion del servicio).
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
}
