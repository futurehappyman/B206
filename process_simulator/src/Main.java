
/**************************************************************************
 *
 *  File       : Main.java
 *  Author     : Stepan Nikonov
 *  Created on : 2025-06-13
 *
 *  Description: Main.java is used to initialise java files that will act as processes.
 *               Then the step of execution: Scheduler and MemoryManager take place.
**************************************************************************/

import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      System.out.print("Enter total memory (KB): ");  
      int totalMemory = scanner.nextInt();               // total memory utilised in simulation
      System.out.print("Enter quantum of time for single process: ");
      int quantum = scanner.nextInt();                   // quantum of time that we can give to a single process on cpu 
      scanner.nextLine();

      JavaFileExecutor executor = new JavaFileExecutor(totalMemory, quantum); // executer has the MemoryManger and scheduler that executes files along with memory manager that provides acces to ram

      System.out.print("Enter number of files: "); 
      int n = scanner.nextInt();
      scanner.nextLine();

      for (int i = 0; i < n; i++) { 
         System.out.printf("Enter file details %d (Name, Size (KB), Arrival Time, Execution Time): ", i + 1);
         /* details of a process */
         String fileName = scanner.nextLine();  
         int fileSize = scanner.nextInt();
         int arrivalTime = scanner.nextInt();
         int executionTime = scanner.nextInt();
         scanner.nextLine();
         executor.addProcess(i + 1, fileName, fileSize, arrivalTime, executionTime); // add files as NEW processes to the proceses ArrayList in the scheduler.  
      }
      /* 4 processes initialised, proceed to execution */
      executor.executeFiles(); 
      scanner.close();
   }
}