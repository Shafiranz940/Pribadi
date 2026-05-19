interface Engine {
    void start();
}

class ModernEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Modern Engine started.");
    }
}

class Car {

    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void drive() {
        engine.start();
        System.out.println("Car is driving.");
    }
}

class Injector {

    public static Car inject() {

        Engine engine = new ModernEngine();

        return new Car(engine);
    }
}

public class Main {

    public static void main(String[] args) {

        Car car = Injector.inject();

        car.drive();
    }
}
