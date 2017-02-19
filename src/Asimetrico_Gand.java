import java.io.*;
import java.util.Scanner;

/**
 * Created by gand on 15/02/17.
 */
public class Asimetrico_Gand {

    static Scanner teclado;

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        teclado = new Scanner(System.in);

        System.out.println("-------------CIFRADO ASIMETRICO---------------");
        System.out.println("Selecciona una opcion:");
        System.out.println("\t1.- Cifrar frase y guardar en archivo.");
        System.out.println("\t2.- Descifrar frase desde archivo.");
        char e = teclado.nextLine().charAt(0);

        switch (e){
            case '1': cifrar();
                break;
            case '2': descifrar();
                break;
            default:
                System.out.println("Debe selecionar una opcion 1 o 2");
                menu();
        }
    }

    private static void cifrar() {
        teclado = new Scanner(System.in);

        System.out.println("Introduce los datos para cifrar:");
        System.out.println("Ruta del almacen de claves:");
        String almacen = teclado.nextLine().trim();
        System.out.println("Contraseña del almacen de claves:");
        String passAlm = teclado.nextLine().trim();
        System.out.println("Alias de la clave publica del receptor:");
        String alias = teclado.nextLine().trim();
        System.out.println("Frase a cifrar con la clave publica");
        String fraseAC = teclado.nextLine().trim();

        Cifrado cifrado = new Cifrado(almacen, passAlm,"",alias,fraseAC, "");

        String fraseCifrada = cifrado.cifrarPublicK();
        System.out.println("Frase cifrada en Base64: \n\"" + fraseCifrada + "\"");

        System.out.println("Introduce la ruta y el nombre del fichero para guardar la frase cifrada.");
        String ruta = teclado.nextLine();

        File fichero = new File(ruta);
        try ( FileWriter fw = new FileWriter(fichero)){
            fw.write(fraseCifrada);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Fichero cifrado y guardado correctamente.");
    }

    private static void descifrar() {
        teclado = new Scanner(System.in);

        System.out.println("Introduce los datos para descifrar:");
        System.out.println("Ruta del fichero con frase a descifrar:");
        String ruta = teclado.nextLine().trim();

        System.out.println("Ruta del almacen de claves:");
        String almacen = teclado.nextLine().trim();
        System.out.println("Contraseña del almacen de claves:");
        String passAlm = teclado.nextLine().trim();
        System.out.println("Alias de tu clave privada:");
        String alias = teclado.nextLine().trim();
        System.out.println("Contraseña de tu clave privada:");
        String passPrivK = teclado.nextLine().trim();

        String fraseC = null;
        try (FileReader fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr)){

            fraseC = br.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cifrado descifrado = new Cifrado(almacen, passAlm,passPrivK,alias,"", fraseC);

        String fraseDesc = descifrado.descifrarPrivateK();

        System.out.println("La frase descifrada es: \n\"" + fraseDesc + "\"");

    }
}//Fin class
