package generatefiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateFiles {
    public static void main(String[] args) throws Exception {
        /* Obtener la ruta del directorio actual de trabajo */
        String absolutePath = System.getProperty("user.dir");
        /* Ruta hacia el directorio o package en los que se encuentran nuestros .flex y .cup */
        String srcPath = "/src/analyzer/";
        /* Armado de las rutas */
        String path = absolutePath + srcPath + "MathLexer.flex";
        String path2 = absolutePath + srcPath + "Lexer.flex";
        String[] paths = {"-parser", "Sintax", absolutePath + srcPath + "MathParser.cup"};
        generate(path, path2, paths, absolutePath, srcPath);
    }

    public static void generate(String path, String path2, String[] paths, String absolutePath, String srcPath) throws IOException, Exception{
        /* Rutas sym.java y Sintax.java */
        Path pathSym = Paths.get(absolutePath + "/sym.java");
        Path pathSin = Paths.get(absolutePath + "/Sintax.java");

        /* Eliminar Sintax y sym si ya existen dentro de analyzer */
        File fileSym = new File(absolutePath + srcPath + "sym.java");
        File fileSin = new File(absolutePath + srcPath + "Sintax.java");
        if (fileSym.exists()) {
            fileSym.delete();
        }
        if (fileSin.exists()) {
            fileSin.delete();
        }

        /* Generar archivos */
        File file;
        file = new File(path);
        JFlex.Main.generate(file);
        file = new File(path2);
        JFlex.Main.generate(file);
        java_cup.Main.main(paths);

        /* Mover archivos */
        Files.move(
                pathSym,
                Paths.get(absolutePath + srcPath + "sym.java")
        );

        Files.move(
                pathSin,
                Paths.get(absolutePath + srcPath + "Sintax.java")
        );
    }
}
