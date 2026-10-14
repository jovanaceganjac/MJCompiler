# MicroJava Compiler

A complete compiler for **MicroJava**, a Java subset used in the
*Programming Translators 1* course at ETF Belgrade. It compiles `.mj` source
into bytecode for the MicroJava virtual machine.

## Phases

1. **Lexical analysis** — JFlex specification (`spec/mjlexer.lex`)
2. **Syntax analysis** — LALR(1) grammar in CUP with AST generation and error recovery
   (`spec/mjparser_astbuild.cup`)
3. **Semantic analysis** — symbol table, type checking, scopes (`SemAnalyzer.java`)
4. **Code generation** — MicroJava VM bytecode (`CodeGenerator.java`)

Supported language features include constants, global and local variables, arrays,
enums, `if`/`else`, `for`, `switch`/`case`, `break`/`continue`, `read`/`print`
and methods.

## Tech

Java, JFlex, CUP, Ant, log4j

## Build and run

```bash
ant compile             # generate lexer and parser, then compile
java -cp ... rs.ac.bg.etf.pp1.Compiler test/program.mj test/program.obj
```

Libraries in `lib/` (JFlex, CUP, symbol table, MJ runtime) are provided by the course.
