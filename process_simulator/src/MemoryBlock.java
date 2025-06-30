/**************************************************************************
 *
 * File : MemoryBlock.java
 * Author : Stepan Nikonov
 * Created on : 2025-06-13
 *
 * Description: Memory block allows me to explain the contigious section of RAM allocated for specific process.
 * Memory block is utilised in the MemoryManager that decides how to allocate memory for the processes.
 **************************************************************************/

public class MemoryBlock {
    int blockId;              // block identifier
    int size;                 // block size in kilobytes
    int startAddress;         // starting address
    boolean isAllocated;      // if the block is occupied
    int processId;            // id of process occupying the block

    /* initialising a memory block */
    public MemoryBlock(int blockId, int size, int startAddress) {
        this.blockId = blockId;
        this.size = size;
        this.startAddress = startAddress;
        this.isAllocated = false;           // initially empty
        this.processId = -1;                                
    }
}