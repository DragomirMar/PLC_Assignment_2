package Project;

import java.util.concurrent.locks.*;

public class TrafficControllerFair implements TrafficController {
	private TrafficRegistrar registrar;
	Lock lock = new ReentrantLock(true);
	Condition bridge = lock.newCondition();
	boolean bridgeEmpty = true;
	
	public TrafficControllerFair(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	
	
	public void enterRight(Vehicle v) { 
		lock.lock();
		
		try {
			while(!bridgeEmpty) {
				bridge.await();
			
		}
		bridgeEmpty = false;
		registrar.registerRight(v);
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}    
	}
	
	public void enterLeft(Vehicle v) {
		lock.lock();
		
		try {
			while(!bridgeEmpty) {
				bridge.await();
			
		}
		bridgeEmpty = false;
		registrar.registerLeft(v); 
		
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}  finally {
			lock.unlock();
		}
		  
	}
	
	public void leaveLeft(Vehicle v) {  
		lock.lock();
		bridgeEmpty = true;
		registrar.deregisterLeft(v);
		bridge.signalAll();
		lock.unlock();
	}
	
	public void leaveRight(Vehicle v) { 
		lock.lock();
		bridgeEmpty = true;
		registrar.deregisterRight(v); 
		bridge.signalAll();
		lock.unlock();
	}
}