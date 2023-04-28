package gui;

import analyzer.Lexer;
import analyzer.TokenType;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import managefiles.ManageFiles;

import java.io.*;

public class AnalyzerController {
    @FXML
    private ScrollPane lexicalAnalyzerInput;
    @FXML
    private JFXTextArea lexicalAnalyzerOutput;

    protected void analyzeLine(String line) {
        int count = 1;
        File file = new File("textForLexicalAnalysis.txt"); // Crea un archivo que se usara para analizar
        PrintWriter writer; // Crea un escritor para escribir en el archivo

        try {
            writer = new PrintWriter(file);
            writer.print(line); // Escribe el texto que se encuentra en el area de texto en el archivo
            writer.close(); // Termina de escribir en el archivo
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Reader readerForLexer = new BufferedReader(new FileReader("textForLexicalAnalysis.txt")); // Crea un lector para leer el archivo
            Lexer lexer = new Lexer(readerForLexer); // Crea un lexer para analizar el archivo
            String result = "Ecuacion: " + "\n"; // Para que al analizar nos muestre la linea 1 al inicio
            while (true) {
                TokenType token = lexer.yylex();
                if (token == null) {
                    result += "EOA"; // End of analysis (Fin del analisis)
                    lexicalAnalyzerOutput.setText(result);
                    return;
                }
                switch (token) { // Analiza el token y lo muestra en el area de texto
                    case Linea:
                        count++;
                        result += "Line:    " + count + "\n";
                        break;
                    case Number:
                        result += "Tk_Number:                   " + lexer.lexeme + "    ---> Es un Numero" + "\n";
                        break;
                    case Variable:
                        result += "Tk_Variable:                   " + lexer.lexeme + "    ---> Es una Variable" + "\n";
                        break;
                    case Operator:
                        result += "Tk_Operator:                 " + lexer.lexeme + "    ---> Es un Operador " + "\n";
                        break;
                    case Parenthesis_left:
                        result += "Tk_Parenthesis_left:       " + lexer.lexeme + "    ---> Es un Parentesis Izquierdo" + "\n";
                        break;
                    case Parenthesis_right:
                        result += "Tk_Parenthesis_right:     " + lexer.lexeme + "    ---> Es un Parentesis Derecho" + "\n";
                        break;

                    case ERROR:
                        result +=  "TK_"+token+":                   " +lexer.yytext()+"     ---> Simbolo no Reconocido"+"\n";
                        break;
                    case Exponent:
                        result += "Tk_Exponent:                 " + lexer.lexeme + "    ---> Es un Exponente" + "\n";
                        break;
                    case Corchete_right:
                        result += "TK_Corchete_ringt:         "+ lexer.lexeme + "    ---> Es un Corchete Derecho" + "\n";
                        break;
                    case Corchete_left:
                        result += "TK_Corchete_left:           "+ lexer.lexeme + "    ---> Es un Corchete Izquierdo"+"\n";
                        break;
                /*    default:
                        result += "  < " + lexer.lexeme + " >\n";

                        break;*/
                }

                lexicalAnalyzerOutput.setText(result);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void uploadFileAction() throws IOException { // Carga el archivo
        String filePath = ManageFiles.chooseFile();
        if (filePath != null) {
            String extractedText = ManageFiles.readFile(filePath);
            // Leer el texto extraido y crear botones en el panel de las ecuaciones
            String[] lines = extractedText.split("\n");

            VBox buttonContainer = new VBox();
            for (String line : lines) {
                Button button = new Button(line);
                // Establecer el estilo base
                button.setStyle("-fx-background-color: white; -fx-border-color: #198754; -fx-text-fill: black;");
                // Cambiar los estilos al colocar el cursor sobre el botón
                button.setOnMouseEntered(event -> {
                    button.setStyle("-fx-background-color: #198754; -fx-border-color: #198754; -fx-text-fill: white;");
                });
                // Cambiar los estilos al quitar el cursor del botón
                button.setOnMouseExited(event -> {
                    button.setStyle("-fx-background-color: white; -fx-border-color: #198754; -fx-text-fill: black;");
                });
                button.setOnAction(event -> {
                    // Analizar la linea
                    analyzeLine(line);
                });
                // Agregar un ancho minimo de 100px
                button.setMinWidth(100);
                // Agregar un margen inferior de 10px
                VBox.setMargin(button, new javafx.geometry.Insets(0, 0, 10, 0));
                buttonContainer.getChildren().add(button);
            }
            lexicalAnalyzerInput.setContent(buttonContainer);
        }
    }

    @FXML
    protected void cleanDataAction() {
        lexicalAnalyzerInput.setContent(null);
        lexicalAnalyzerOutput.setText("");
    } // Limpia el scroll pane y los datos del area de texto
}
