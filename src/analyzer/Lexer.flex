package analyzer;
import static analyzer.TokenType.*;
%%
%public
%class Lexer
%type TokenType
// Expresiones regulares para ecuaciones de segundo grado
NUMBER =[0-9]+
VARIABLE = [a-zA-Z]
EXPONENT =[ª\²\³]
OPERATOR = [\+\-\*\/\^\=\±\ʃ]+
PARENTHESIS_LEFT = \(
PARENTHESIS_RIGHT = \)
CORCHETE_LEFT= \[
CORCHETE_RIGHT= \]
ESPACIO=[ ,\t,\r]+
%{
    public String lexeme;
%}
%%
// Reconocimiento de espacios en blanco
{ESPACIO} { /* ignore whitespace */ }

// Reconocimiento de salto de linea
( "\n" ) {return Linea;}

// Reconocimiento de números
{NUMBER} {lexeme=yytext(); return Number;}

// Reconocimiento de variables
{VARIABLE} {lexeme=yytext(); return Variable;}

//Reconocimiento de exponentes
{EXPONENT} {lexeme=yytext(); return Exponent;}

// Reconocimiento de operadores
{OPERATOR} {lexeme=yytext(); return Operator;}

// Reconocimiento de paréntesis izquierdo
{PARENTHESIS_LEFT} {lexeme=yytext(); return Parenthesis_left;}

// Reconocimiento de paréntesis derecho
{PARENTHESIS_RIGHT} {lexeme=yytext(); return Parenthesis_right;}

// Reconocimiento de un Corchete izquierdo
{CORCHETE_LEFT} {lexeme=yytext(); return Corchete_left;}

//Reconocimiento de un Corchete derecho
{CORCHETE_RIGHT} {lexeme=yytext(); return Corchete_right;}

// Error de analisis
 . {return ERROR;}