// Interface Engine
interface Engine {
    void start();
}

// Concrete Class
class ModernEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Modern Engine started.");
    }
}

// Car class
class Car {

    // Dependency
    private Engine engine;

    // Constructor Injection
    public Car(Engine engine) {
        this.engine = engine;
    }

    public void drive() {
        engine.start();
        System.out.println("Car is driving.");
    }
}

// Injector class
class Injector {

    public static Car inject() {

        // Membuat object ModernEngine
        Engine engine = new ModernEngine();

        // Menyuntikkan engine ke Car
        return new Car(engine);
    }
}

// Main class
public class Main {

    public static void main(String[] args) {

        // Injector membuat dan menghubungkan object
        Car car = Injector.inject();

        // Menjalankan mobil
        car.drive();
    }
}
