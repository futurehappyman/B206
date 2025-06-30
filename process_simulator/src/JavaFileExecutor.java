/**************************************************************************
 *
 * File : JavaFileExecutor.java
 * Author : Stepan Nikonov
 * Created on : 2025-06-13
 *
 * Description: File connects Main, MemoryManager, ProcessScheduler. 
 *               scheduler.executeFiles() doese the scheduling for processes along with all MemoryManagers methods.
 **************************************************************************/

public class JavaFileExecutor {
   private ProcessScheduler scheduler;    
   private MemoryManager memoryManager;   

   public JavaFileExecutor(int totalMemory, int quantum) {            // simulator that gives us the quantum of time for each process on cpu, and totalMemoy on the system.
      this.memoryManager = new MemoryManager(totalMemory);            // memoryManager initialised and intialises memoryBlocks
      this.scheduler = new ProcessScheduler(quantum, memoryManager);  // scheduler initialised and initialises queues for ready processes, list for NEW process, memory Blocks
   }

   /* adds processes as NEW to processes ArrayList */
   public void addProcess(int pid, String fileName, int fileSize, int arrivalTime, int executionTime) {
      scheduler.addProcess(new Process(pid, fileName, fileSize, arrivalTime, executionTime));
   }

   /* scheduler takes files added to processes and executes them */
   public void executeFiles() { 
      scheduler.executeFiles(); 
   }
}
