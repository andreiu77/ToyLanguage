# 🖥️ Toy Language Interpreter

A robust, multi-threaded interpreter for a custom programming language, implemented in Java. This project demonstrates the core principles of interpreter design, including lexical analysis, parsing, type checking, state management, and memory allocation (heap).

---

## ✨ Features

### 🔧 Language Constructs
- **Basic Types**: `int`, `bool`, `string`, and `Ref` (pointers).
- **Expressions**: 
  - Arithmetical (`+`, `-`, `*`, `/`)
  - Logical (`and`, `or`)
  - Relational (`<`, `<=`, `==`, `!=`, `>`, `>=`)
  - Heap Access (`readHeap`)
- **Statements**:
  - `Assign`: Variable assignment.
  - `Print`: Console output.
  - `If`: Conditional execution.
  - `While`: Looping construct.
  - `Comp`: Compound statements (sequence).
  - `Fork`: Spawns a new execution thread (concurrency).
  - `File Operations`: `openFile`, `readFile`, `closeFile`.
  - `Heap Management`: `new` (allocation), `writeHeap`.

### 🚀 Advanced Functionalities
- **Static Type Checker**: Validates program types before execution to prevent runtime errors.
- **Safe Garbage Collector**: Automatically cleans up unreferenced heap memory, including support for indirect references.
- **Multithreading**: True concurrent execution using Java's `ExecutorService` and `Callable` interface.
- **Execution Logging**: Records each step of the program's state (Stack, SymTable, Heap, Output, FileTable) into dedicated log files.

---

## 🏗️ Project Architecture

The project follows a clean, layered architecture:

- **Model**: Defines the internal state of the interpreter.
  - `adt`: Custom data structures (Stack, Dictionary, List, Heap).
  - `expressions`: Logic for evaluating various expression types.
  - `statements`: Logic for executing language commands.
  - `state`: The `PrgState` class, encapsulating the entire environment of a thread.
- **Repository**: Manages the list of active program states and handles file logging.
- **Controller**: Orchestrates the execution flow, garbage collection, and thread management.
- **View/GUI**:
  - **Console View**: A text-based menu for selecting and running programs.
  - **JavaFX GUI**: A rich graphical interface to visualize the execution step-by-step.

---

## 📦 Prerequisites

- **Java JDK 17** or newer.
- **JavaFX SDK** (required for the GUI mode).
- An IDE (IntelliJ IDEA recommended) or Build Tool (Maven/Gradle).

---

## 🚀 How to Run

To run the application via command line (CLI) rather than an IDE like IntelliJ, follow the instructions below. 

### 1. Console Mode
A simple text-based menu to run programs and view results in the console.

**From the root of the repository:**
```bash
# Compile the Java files
javac -d out -sourcepath src src/Interpreter.java

# Run the Interpreter
java -cp out Interpreter
```

**Steps**:
1. Run the commands above.
2. Enter the number corresponding to the program you want to execute.
3. Enter `0` to exit.

### 2. Graphical User Interface (GUI)
The GUI provides a step-by-step visualization of the execution structures (Heap, Symbol Table, Execution Stack, etc.). Since this uses JavaFX, you must provide the path to your JavaFX SDK.

**From the root of the repository:**
```bash
# Set your JavaFX SDK path (example for Windows)
set PATH_TO_FX="C:\path\to\javafx-sdk\lib"

# Compile the Java files (including JavaFX modules)
javac -d out --module-path %PATH_TO_FX% --add-modules javafx.controls -sourcepath src src/gui/GUI.java

# Run the GUI
java -cp out --module-path %PATH_TO_FX% --add-modules javafx.controls gui.GUI
```

**Steps**:
1. Launch the `GUI` class using the commands above.
2. Select one of the pre-defined programs from the list.
3. Click **Start** to open the main execution window.
4. Use the **One Step** button to advance execution across all threads.

---

## 🧪 Example Programs

The project comes with several pre-configured test cases in `src/importprgs/Programs.java`, covering:
- Complex arithmetic and logical operations.
- File handling (reading from `test.in`).
- Heap allocation and pointer manipulation.
- **Multithreading**: A `fork` example that demonstrates variables being shared or shadowed across threads.

---

## 📂 Repository Structure

```
ToyLanguage/
├── src/
│   ├── controller/      # Execution logic & Thread management
│   ├── gui/             # JavaFX controllers and views
│   ├── model/           # AST nodes, ADTs, and State
│   ├── repository/      # Data persistence and logging
│   ├── view/            # Console menu implementation
│   ├── exceptions/      # Custom error types
│   ├── importprgs/      # Pre-defined test programs
│   └── Interpreter.java # Console entry point
├── test.in              # Sample input file for file operations
└── README.md
```
