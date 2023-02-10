package Project;

public class TrafficController2RightCars implements TrafficController {
	private TrafficRegistrar registrar;
	int rightCar = 1;
	boolean leftCar = false;
	
	public TrafficController2RightCars(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	public synchronized void enterRight(Vehicle v) { 
		try{
			while(rightCar > 2 || leftCar){
				wait();
			}
		rightCar++;
		registrar.registerRight(v);   
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}    
	}
	
	public synchronized void enterLeft(Vehicle v) {
		try{
			while(rightCar > 1 || leftCar){
				wait();
			}
			leftCar = true;
		registrar.registerLeft(v);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void leaveLeft(Vehicle v) {  
		rightCar--;
		registrar.deregisterLeft(v);      
		notifyAll();
	}
	
	public synchronized void leaveRight(Vehicle v) { 
		leftCar = false;
		registrar.deregisterRight(v); 
		notifyAll();
	}
}
