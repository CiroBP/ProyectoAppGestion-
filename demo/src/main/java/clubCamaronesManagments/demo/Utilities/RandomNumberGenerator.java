package clubCamaronesManagments.demo.Utilities;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RandomNumberGenerator {
    private Random random;
    private Object repository;
    public RandomNumberGenerator(Random random, Object repository){
        this.random = random;
        this.repository = repository;
    }

    private Integer generateRandomNumber() {
        return random.nextInt(9000) + 1000;
    }
    public Integer asignarId(){
        Integer id;
        do {
            id = generateRandomNumber();
        } while(existInRepository(id));
        return id;
    }
    private boolean existInRepository(Integer id){
        if(repository != null){
            try{
                return (boolean) repository.getClass().getMethod("existsById", Integer.class).invoke(repository, id);
            } catch (NoSuchMethodException e) {
                System.out.println("Error: El método existById no fue encontrado en el repositorio.");
                e.printStackTrace();
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Error al invocar el método existById en el repositorio.");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Error desconocido: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

}

