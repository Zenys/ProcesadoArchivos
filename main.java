package ProcesadoArchivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Alberto
 */
public class main {

    public static void main(String[] args) throws IOException {
        Scanner Scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del archivo");
        String fichero = Scanner.nextLine();
        readFile(fichero);
        generateHistogram(fichero);
    }

    public static void readFile(String fichero) {

        try {
            File myFile = new File(fichero + ".txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void generateHistogram(String fichero) throws IOException {
        try {
            String text = "";
            HashMap<String, Integer> histogram = new HashMap<String, Integer>();
            BufferedReader infile = new BufferedReader(new FileReader(fichero + ".txt"));
            while (infile.ready()) {
                String linea = infile.readLine();
                String[] palabras = linea.split("\\W+");
                for (String palabra : palabras) {
                    if (palabra.length() > 2) {
                        if (!histogram.containsKey(palabra)) {
                            histogram.put(palabra, 1);
                        } else {
                            histogram.put(palabra, histogram.get(palabra) + 1);
                        }
                    }
                }
            }
            ArrayList<String> palabras = new ArrayList<String>(histogram.keySet());
            for (String palabra : palabras) {
                System.out.println(palabra + "\t" + histogram.get(palabra));
                if (palabra.length() > 2) {
                    text = text + palabra + "\t" + histogram.get(palabra) + "\n";
                }
            }

            createFile(fichero);
            generateCSV(fichero, text);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void createFile(String fichero) {
        try {
            File myObj = new File(fichero + "_histograma.csv");
            if (myObj.createNewFile()) {
                System.out.println("Archivo creado: " + myObj.getName());
            } else {
                System.out.println("El archivo ya existe.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void generateCSV(String fichero, String text) {
        try {
            FileWriter myWriter = new FileWriter(fichero + "_histograma.csv");
            myWriter.write(text);
            myWriter.close();
            System.out.println("Se ha escrito en el archivo correctamente.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
