# KtStraightLine

A straight line program interpreter written in Kotlin

## Getting Started
### Prerequisites

- Kotlin Command Line Tool
    ```bash
    # Install Kotlin Command Line Tool through homebrew
    brew install kotlin
    ```

### Compilation

Navigate into `src` folder and run:
```bash
kotlinc * -d slp.jar
```
You should see that a jar file called slp.jar is generated inside the folder

### Running

To execute the interpreter, run the following command under the same directory:

```bash
kotlin -cp slp.jar StraightLineProgramKt
```

Example program:

```
program => a := 5+3; b := (print(a, a-1), 10*a); print(b)
```

Output:

```
8 7
80
```

## Author

[Harry Liu]() - **Initial works**

## License

This repo is maintained under MIT license