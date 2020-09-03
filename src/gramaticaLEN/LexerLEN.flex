package gramaticaLEN;
import java_cup.runtime.Symbol;

%%
%public
%class AnalizadorLexico
%cup
%cupdebug
%line
%column

/*Identificadores*/
Letra = [a-zA-Z]
LetraMin = [a-z]
LetraMay = [A-Z]
GuionBajo = [_]
Numero = [0123456789]
Salto = \r|\n|\r\n
Espacio1 = [ \t\f]
Espacio = {Salto} | {Espacio1}

%{
    
    private Symbol symbol(int tipo){
        return new Symbol(tipo, yyline+1, yycolumn+1);
    }

    private Symbol symbol(int tipo, Object value){
        return new Symbol(tipo, yyline+1, yycolumn+1, value);
    }

%}
%%
<YYINITIAL>{
    ","                                                             {return symbol(sym.coma, new String(yytext()));}    
    "nombre"                                                        {return symbol(sym.nombre);}                                                                   
    "version"                                                       {return symbol(sym.version);}
    "autor"                                                         {return symbol(sym.autor);}
    "lanzamiento"                                                   {return symbol(sym.lanzamiento);}
    "_"                                                             {return symbol(sym.guion_bajo, new String(yytext()));}
    "extension"                                                     {return symbol(sym.extension);}
    "RESULT"                                                        {return symbol(sym.result);}
    ":"                                                             {return symbol(sym.dos_puntos, new String(yytext()));}
    "."                                                             {return symbol(sym.punto, new String(yytext()));}
    ";"                                                             {return symbol(sym.punto_coma, new String(yytext()));}
    "%%"                                                            {return symbol(sym.separador);}
    "["                                                             {return symbol(sym.agrupacion_a, new String(yytext()));}
    "]"                                                             {return symbol(sym.agrupacion_b, new String(yytext()));}
    "?"                                                             {return symbol(sym.interrogacion, new String(yytext()));}
    "*"                                                             {return symbol(sym.por, new String(yytext()));}
    "+"                                                             {return symbol(sym.mas, new String(yytext()));}
    "\""                                                            {return symbol(sym.comillas, new String(yytext()));}
    "("                                                             {return symbol(sym.parentesis_a, new String(yytext()));}
    ")"                                                             {return symbol(sym.parentesis_b, new String(yytext()));}
    "{"                                                             {return symbol(sym.llave_a, new String(yytext()));}
    "}"                                                             {return symbol(sym.llave_b, new String(yytext()));}
    "-"                                                             {return symbol(sym.guion, new String(yytext()));}
    "\\n"                                                           {return symbol(sym.enter, new String(yytext()));}
    "\\t"                                                           {return symbol(sym.tab, new String(yytext()));}
    "="                                                             {return symbol(sym.igual, new String(yytext()));}
    "&"                                                             {return symbol(sym.ampersand, new String(yytext()));}
    "\\b"                                                           {return symbol(sym.espacio, new String(yytext()));}
    "/"                                                             {return symbol(sym.diagonal, new String(yytext()));}
    "//"                                                            {return symbol(sym.comentario);}
    "/*"                                                            {return symbol(sym.comentario_a);}
    "|"                                                             {return symbol(sym.o, new String(yytext()));}
    "*/"                                                            {return symbol(sym.comentario_b);}
    "terminal"                                                      {return symbol(sym.terminal1);}
    "no terminal"                                                   {return symbol(sym.noTerminal);}
    "entero"                                                        {return symbol(sym.tipoEntero, new String(yytext()));}
    "real"                                                          {return symbol(sym.tipoReal, new String(yytext()));}
    "cadena"                                                        {return symbol(sym.tipoCadena, new String(yytext()));}
    "id"                                                                {return symbol(sym.id);}
    {Numero}+                                                       {return symbol(sym.numero, new Integer(yytext()));}
    ({LetraMin}|{GuionBajo})({LetraMin}|{Numero}}|{GuionBajo})*                              {return symbol(sym.idMin, new String(yytext()));}
    ({LetraMay}|{GuionBajo})({LetraMay}|{Numero}|{GuionBajo})*                              {return symbol(sym.idMay, new String(yytext()));} 
    ({Letra}|{GuionBajo})({Letra}|{Numero}|{GuionBajo})*            {return symbol(sym.soloLetra, new String(yytext()));}
    {Espacio}+                                                      {}
}   
    [^]                                                               {return symbol(sym.resto, new String(yytext()));}
