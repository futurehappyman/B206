/***************************************************************
 *
 *  File       : ProcessScheduler.java
 *  Author     : Stepan Nikonov
 *  Created on : 2025-06-13
 *
 *  Description: Explains the life cycle of processes, from new State, to executed.
 *               Shows how the queue works, memory is allocated through the MemoryManager. 
***************************************************************/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessScheduler {
   private ArrayList<Process> processes;  // Processes in the NEW state 
   private Queue<Process> readyQueue;     // Processes in the READY state
   private MemoryManager memoryManager;   // memory manager object 
   private int quantum;                   // quantum of time for a single process
   private int currentTime;               // currentTime on the timeline of processes

   public ProcessScheduler(int quantum, MemoryManager memoryManager) { // 4, {block, block, block, block}
      this.processes = new ArrayList<>();    // processes in a NEW state
      this.readyQueue = new LinkedList<>();  // processes in a READY state
      this.quantum = quantum;                // quantum of time allocated for 1 process
      this.memoryManager = memoryManager;    // ArrayList with memory blocks
      this.currentTime = 0;               
   }

   public void addProcess(Process process) {
      processes.add(process);
   }

   /* Execution with Round Robin */
   public void executeFiles() { 
      System.out.println("\nSimulating execution with Round Robin...");
      /* if we have NEW or READY processes */
      while (!processes.isEmpty() || !readyQueue.isEmpty()) {
         for (Process p : new ArrayList<>(processes)) { 
            if (p.arrivalTime <= currentTime) {
               readyQueue.add(p); 
               processes.remove(p); 
            }
         }
         /* if we have process => we allocate memory, if can => calculate, take next in queue 
            if cant alloocate memory(to bad, its static)
         */
         if (!readyQueue.isEmpty()) {
            Process p = readyQueue.poll();
            if (memoryManager.allocateMemory(p)) {
               int timeSlice = Math.min(quantum, p.remainingTime); 
               /* some time consuming operations */ 
               System.out.printf("Process %d: Loading file '%s' (Size: %d KB) at address %d\n",
                     p.pid, p.fileName, p.fileSize, p.memoryAddress);
               System.out.printf("Process %d: Compiling file to bytecode (%d units)...\n", 
                     p.pid, timeSlice / 2);
               System.out.printf("Process %d: Interpreting bytecode in JVM (%d units)...\n", 
                     p.pid, timeSlice - timeSlice / 2);
               System.out.println("--- Current Memory Usage ---");
               memoryManager.displayMemorySummary();      
               /* updating remaining time for process 
                  updating currentTime for scheduler */
               currentTime += timeSlice;  
               p.remainingTime -= timeSlice;  

               /* adjusting wait time for other process in Queue */
               for (Process other : readyQueue) {
                  other.waitTime += timeSlice; 
               }
               /* incase some process comes in the middle of other process */
               for (Process pr : new ArrayList<>(processes)) {
                  if (pr.arrivalTime <= currentTime) {
                     readyQueue.add(pr);
                     processes.remove(pr);
                  }
               }     
               if (p.remainingTime > 0) { 
                  readyQueue.add(p); // if process not finished, add it back to the queue
               } else {              // if process finished 
                  p.turnaroundTime = currentTime - p.arrivalTime;                  // document turnaround time 
                  p.waitTime = p.turnaroundTime - (p.turnaroundTime - p.waitTime); // document waitTime 
                  displayProcessMetrics(p);                                         
                  memoryManager.deallocateMemory(p.pid);                           // deallocating memory for that process 
               }
            } else {
               /* if we could not allocate memory */
               System.out.printf("Process %d: Waiting for memory for file '%s' (Size: %d KB)\n",
                     p.pid, p.fileName, p.fileSize);
               readyQueue.add(p); 
            }
         } else {
            currentTime++; 
         }
         
      }
      memoryManager.displayMemorySummary();
   }

   private void displayProcessMetrics(Process p) {
      System.out.printf("Process %d: File '%s', Wait Time = %d, Turnaround Time = %d\n",
            p.pid, p.fileName, p.waitTime, p.turnaroundTime);
   }
}
