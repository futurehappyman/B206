
/**************************************************************************
 *
 *  File       : MemoryManager.java
 *  Author     : Stepan Nikonov
 *  Created on : 2025-06-13
 *
 *  Description: MemoryManager Controls access to RAM(abstractly saying), just 4 static memory blocks.
**************************************************************************/

import java.util.ArrayList;

public class MemoryManager {
   private ArrayList<MemoryBlock> memory; // ArrayList of objects that represent availible memory space 
   private int totalMemory;               // total memory
   
   /* initialisation of MemoryManager that controls memory allocation and deallocation */
   public MemoryManager(int totalMemory) { 
      this.totalMemory = totalMemory;     
      this.memory = new ArrayList<>();   
      initializeMemory();                 // initialise memory blocks, 4 by default, i have them static, not pages(sorry). 
   }

   private void initializeMemory() {
      int blockSize = totalMemory / 4;
      memory.add(new MemoryBlock(1, blockSize, 0));
      memory.add(new MemoryBlock(2, blockSize, blockSize));     
      memory.add(new MemoryBlock(3, blockSize, blockSize * 2)); 
      memory.add(new MemoryBlock(4, blockSize, blockSize * 3));  
   }
   /* utilises blocks to allocate memory segments */
   public boolean allocateMemory(Process process) {
      /* check if the memory block is taken */
      for (MemoryBlock block : memory) {  
        if (block.processId == process.pid) {
            process.memoryAddress = block.startAddress;
            return true;
        }
      }

      for (MemoryBlock block : memory) {                             // utilises 4 blocks of memory
         if (!block.isAllocated && block.size >= process.fileSize) { // if filesize is fit for the block
            block.isAllocated = true;                                // then memory is allocated 
            block.processId = process.pid;                           // assign block to process
            process.memoryAddress = block.startAddress;              // process adreess is memorys block start address 
            return true;
         }
      }
      return false;
   }

   /* deallocates memory for the process when dropped from cpui */
   public void deallocateMemory(int pid) {
      for (MemoryBlock block : memory) {
         if (block.processId == pid) {
            block.isAllocated = false;
            block.processId = -1;
         }
      }
   }
   
   /* dispalys memory */ 
   public void displayMemorySummary() {
      System.out.println("\nFinal memory usage:");
      int usedMemory = 0;
      for (MemoryBlock block : memory) {
         System.out.printf("Block %d (Size %d KB, Address %d): %s\n",
               block.blockId, block.size, block.startAddress,
               block.isAllocated ? "Occupied by P" + block.processId : "Free");
         if (block.isAllocated)
            usedMemory += block.size;
      }
      System.out.printf("Total memory used: %d KB (%.2f%%)\n",
   }
            usedMemory, (double) usedMemory / totalMemory * 100);
}
