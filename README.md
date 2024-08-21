# PITest-Concolic-Testing
This repository contains the solutions to the second computer assignment for the Software Testing course at the University of Tehran. The assignment involves writing and evaluating tests using mutation testing with the PITest tool, as well as applying concolic testing to explore computation paths in a given codebase.

## Introduction

This project focuses on advanced software testing techniques, including mutation testing using PITest and concolic testing. These methods are applied to a cloned Agile methodology project and a provided codebase to evaluate test effectiveness and explore computation paths.

## Assignment Overview

### Part 1: Mutation Testing with PITest

- **Task:** Clone the [Agile Methodology Project](https://github.com/rima1993/Agile-methodology-Project), write JUnit tests, and evaluate them using the PITest mutation testing tool.
- **Objective:** Assess the quality of the tests by introducing mutations and analyzing their ability to detect faults.

### Part 2: Concolic Testing

- **Task:** Apply concolic testing to a provided codebase to explore all possible computation paths and visualize the computation tree graph.
- **Objective:** Use concolic testing to ensure comprehensive test coverage by exploring different execution paths within the code.

## Prerequisites

Before running the tests, ensure you have the following installed:

- Java Development Kit (JDK) 8 or higher
- Gradle
- PITest tool
- An IDE with JUnit support (e.g., IntelliJ IDEA, Eclipse)

## Installation

To set up the project locally, follow these steps:

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/PITest-Concolic-Testing.git
    cd PITest-Concolic-Testing
    ```

2. Ensure that Gradle and PITest are installed and set up on your system.

3. Open the project in your IDE and allow it to download the necessary dependencies.

## Usage

### Running Mutation Tests with PITest

1. Navigate to the project directory.
2. Execute the PITest tool to run mutation tests:
    ```sh
    ./gradlew pitest
    ```
3. Review the mutation testing report generated in the `build/reports/pitest` directory.

## Results and Analysis

- **Mutation Testing:** Developed and evaluated tests for the Agile Methodology Project using PITest. The mutation testing results were analyzed to assess the effectiveness of the tests in detecting faults.
- **Concolic Testing:** Applied concolic testing to explore all possible computation paths in the provided codebase, ensuring thorough test coverage.

## Report

A detailed report documenting the research, methodology, implementation, and analysis for each part of the assignment is available [here](Report/HW2_REPORT.pdf).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
