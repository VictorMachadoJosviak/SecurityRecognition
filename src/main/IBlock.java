package main;

public interface IBlock {
	int getFirstBlockFree(int[] blocksHD) throws Exception;
	int getNextFreeBlock(int search,int[] blocksHD) throws Exception;
}
