package analyzer;

public enum TokenType {
   /*
    **Enumerado que define una lista de constantes que representan los diferentes
    **tipos de tokens que el analizador léxico puede reconocer en el archivo de entrada
    */
   Linea,
   Number,
   Variable,
   Operator,
   Parenthesis_left,
   Parenthesis_right,
   ERROR,
   Exponent,
   Corchete_right,
   Corchete_left,

}
