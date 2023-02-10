package Project;

public class TrafficControllerSimple implements TrafficController {
	private TrafficRegistrar registrar;
	boolean bridgeEmpty = true;
	
	public TrafficControllerSimple(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	public synchronized void enterRight(Vehicle v) { 
		try{
			while(!bridgeEmpty){
				wait();
			}
		bridgeEmpty = false;
		registrar.registerRight(v);   
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}    
	}
	
	public synchronized void enterLeft(Vehicle v) {
		try{
			while(!bridgeEmpty){
				wait();
			}
		bridgeEmpty = false;
		registrar.registerLeft(v);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void leaveLeft(Vehicle v) {  
		bridgeEmpty = true;
		registrar.deregisterLeft(v);      
		notifyAll();
	}
	
	public synchronized void leaveRight(Vehicle v) { 
		bridgeEmpty = true;
		registrar.deregisterRight(v); 
		notifyAll();
	}
}