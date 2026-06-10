# JVM Report

## Class Loader

The Class Loader subsystem is responsible for loading class files into the JVM. It performs three main functions:

- **Loading**: Finds and loads the binary data of a class from various sources (filesystem, network, etc.).
- **Linking**: Verifies the bytecode, allocates memory for class variables, and resolves symbolic references.
- **Initialization**: Executes static initializers and assigns initial values to static variables.

There are three types of class loaders:

- **Bootstrap Class Loader**: Loads core Java classes (e.g., from rt.jar).
- **Extension Class Loader**: Loads classes from the extension directory.
- **Application Class Loader**: Loads classes from the application's classpath.

## Runtime Data Areas

The JVM divides memory into several runtime data areas:

- **Heap**: Shared among all threads, stores objects and arrays. Managed by the Garbage Collector.
- **Stack**: Thread-specific, stores method call frames, local variables, and partial results.
- **Method Area**: Shared, stores class-level data like static variables, method bytecode, and runtime constant pool.
- **PC Register**: Thread-specific, holds the address of the currently executing JVM instruction.
- **Native Method Stack**: Thread-specific, supports native method execution.

## Execution Engine

The Execution Engine executes the bytecode. It includes:

- **Interpreter**: Executes bytecode line-by-line.
- **JIT Compiler**: Compiles frequently executed bytecode to native machine code for faster execution.
- **Garbage Collector**: Reclaims memory from unused objects.

## JIT Compiler vs Interpreter

- **Interpreter**: Executes bytecode instruction by instruction. Slower but starts execution immediately. Suitable for code that runs infrequently.
- **JIT Compiler**: Monitors code execution and compiles "hot spots" (frequently executed code) to native code. Improves performance for long-running applications but has a compilation overhead.

The JVM uses a mixed mode: starts with interpretation and switches to JIT for optimized performance.

## Write Once, Run Anywhere

Java's "Write Once, Run Anywhere" (WORA) principle means that Java bytecode compiled on one platform can run on any platform with a JVM installed, without recompilation. This is achieved through:

- Platform-independent bytecode.
- JVM abstraction layer that handles OS-specific operations.
- Ensures portability across different operating systems and architectures.
