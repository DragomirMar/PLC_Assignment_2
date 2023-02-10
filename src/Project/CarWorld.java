package Project;

import java.awt.*;
import java.util.*;
import javax.swing.*;

class CarWorld extends JPanel {
	private static final long serialVersionUID = 1L;
	Image bridge;
    Image redCar;
    Image blueCar;

    TrafficController controller;

    ArrayList<Car> blueCars = new ArrayList<Car>();
    ArrayList<Car> redCars = new ArrayList<Car>();

    public CarWorld() {
    	    //controller = new Project.TrafficControllerEmpty(new Project.TrafficRegistrarEmpty());
    	    //controller = new Project.TrafficControllerSimple(new Project.TrafficRegistrarEmpty());
       //	controller = new Project.TrafficControllerFair(new Project.TrafficRegistrarEmpty());
       	controller = new TrafficController2RightCars(new TrafficRegistrarEmpty());
        	
        MediaTracker mt = new MediaTracker(this);
	    Toolkit toolkit = Toolkit.getDefaultToolkit();

        redCar = toolkit.getImage("/Users/drago/UNI/5.Semester/Programming Languages and Concepts/Assignment2PLC22WS/image/redcar.gif");
        mt.addImage(redCar, 0);
        blueCar = toolkit.getImage("/Users/drago/UNI/5.Semester/Programming Languages and Concepts/Assignment2PLC22WS/image/bluecar.gif");
        mt.addImage(blueCar, 1);
        bridge = toolkit.getImage("/Users/drago/UNI/5.Semester/Programming Languages and Concepts/Assignment2PLC22WS/image/bridge.gif");
        mt.addImage(bridge, 2);

        try {
            mt.waitForID(0);
            mt.waitForID(1);
            mt.waitForID(2);
        } catch (java.lang.InterruptedException e) {
            System.out.println("Couldn't load one of the images");
        }

	    redCars.add(new Car(Car.REDCAR,null,redCar,null));
	    blueCars.add(new Car(Car.BLUECAR,null,blueCar,null));
        setPreferredSize(new Dimension(bridge.getWidth(null),bridge.getHeight(null)));
    }


    public void paintComponent(Graphics g) {
	g.drawImage(bridge,0,0,this);
        for (Car c : redCars) c.draw(g);
        for (Car c : blueCars) c.draw(g);
    }

    public void addCar(final int cartype) {
	SwingUtilities.invokeLater(new Runnable () {
		public void run() {
		    Car c;
		    if (cartype==Car.REDCAR) {
			    c = new Car(cartype,redCars.get(redCars.size()-1),redCar,controller);
			    redCars.add(c);
		    } else {
			    c = new Car(cartype,blueCars.get(blueCars.size()-1),blueCar,controller);
			    blueCars.add(c);
		    }
		    new Thread(c).start();
	        }
	    });
    }

}
