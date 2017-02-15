import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

/**
 * Created by gand on 15/02/17.
 */
public class Cifrado {
    String almacen = "/home/gand/.keystore";
    char[] passAlmacen = "usuario".toCharArray();
    char[] passClaveP = "keygand".toCharArray();
    String alias = "KEYGAND";
    String fraseACifrar = "Frase de prueba";
    String fraseCifrada;
    String fraseDescifrada;

    Cifrado (){}

    //------------------------------------------DESCIFRAR --------------------------------------
    String descifrarPrivateK () {
        try (
                FileInputStream fis = new FileInputStream(almacen);
        ) {
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(fis, passAlmacen);
            System.out.println("Almacen abierto, extrayendo clave privada...");
            System.out.println("Contraseña de clave privada: " + String.valueOf(passClaveP));
            PrivateKey pk = (PrivateKey) ks.getKey(alias, passClaveP);
            Cipher cifrado = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cifrado.init(Cipher.DECRYPT_MODE, pk);
//            System.out.println("En Base64 la clave es: " + fraseCifrada + "\n\n");
            byte[] textBytes = Base64.getDecoder().decode(fraseCifrada);
            fraseDescifrada = new String(cifrado.doFinal(textBytes));

//            System.out.println(fraseDescifrada);

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return fraseDescifrada;
    }//Fin descifrar
        //------------------------------------------CIFRAR--------------------------------
    String cifrarPublicK (){
        try (
                FileInputStream fis = new FileInputStream(almacen);
        )

        {
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(fis, passAlmacen);
            PublicKey pubKeyPrueba = ks.getCertificate(alias).getPublicKey();
            //Cipher cifrado = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            Cipher cifrado = Cipher.getInstance(pubKeyPrueba.getAlgorithm());
            cifrado.init(Cipher.ENCRYPT_MODE,pubKeyPrueba);
            //System.out.println("ESTO BORRALO: "+pubKeyPrueba.getAlgorithm());
            byte [] datos = fraseACifrar.getBytes("UTF8");
            byte [] datos1 = cifrado.doFinal(datos);

            System.out.println(""+datos1);

            //pasamos datos1 a base64 y lo guardamos en passC;
            fraseCifrada =Base64.getEncoder().encodeToString(datos1);

        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return fraseCifrada;

    }//Fin cifrar
}//Fin class
