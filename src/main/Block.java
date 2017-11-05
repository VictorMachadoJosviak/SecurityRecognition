package main;

public class Block implements IBlock{

	@Override
	public int getFirstBlockFree(int[] blocksHD) throws Exception {
	
		try {
			
			for (int i = 0; i < blocksHD.length; i++) {
				if (blocksHD[i] == -1)
					return i;
			}
			throw new Exception("Não há blocos livres no disco");
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}

	@Override
	public int getNextFreeBlock(int search,int[] blocksHD) throws Exception {
		try {
			
			for (int i = search + 1; i < blocksHD.length; i++) {
				if (blocksHD[i] == -1)
					return i;
			}
			throw new Exception("Não há blocos livres no disco");
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

}
