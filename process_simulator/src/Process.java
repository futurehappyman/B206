/**************************************************************************
 *
 * File : Process.java
 * Author : Stepan Nikonov
 * Created on : 2025-06-13
 *
 * Description: File contains a representation of a process. (In my case its a java files)
 * When creating java files(giving them: names, size, e.t.c) in the Main and adding them to Processes(in Processes they are in a ready state) 
 **************************************************************************/

public class Process {
   int pid;             // process id
   String fileName;     // file name
   int fileSize;        // file size in kilobytes
   int arrivalTime;     // time when process is in ready state
   int remainingTime;   // remaining execution time
   int memoryAddress;   // memory address (-1 if not allocated)
   int waitTime;        // time spent waiting in queue (waiting)
   int turnaroundTime;  // total time from arrival(ready state) to completion

   /* initialising an instance of the process */
   public Process(int pid, String fileName, int fileSize, int arrivalTime, int executionTime) {
      this.pid = pid;
      this.fileName = fileName;
      this.fileSize = fileSize;
      this.arrivalTime = arrivalTime;
      this.remainingTime = executionTime;  // estiamted time left   
      this.memoryAddress = -1;             // abstraction to represent memory(assuming initial -1)
      this.waitTime = 0;                   // (assuming time to wait is 0, if only one process, which is immideitly goese to execution) 
      this.turnaroundTime = 0;             // (actually turnaround time cant be 0), bits its just an initialisation
   }
}