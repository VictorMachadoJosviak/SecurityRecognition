package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FATTable implements IRepositoryFAT {
	private Map<String, Integer> dic = new HashMap<String, Integer>();
	private int blockSize;
	private int totalBlocksFree;
	private int[] blocksHD;
	private IBlock block = new Block();
	
	public FATTable(int sizeBlock,
					int sizeDisk
			) throws Exception  {
		try {
			if (sizeDisk % sizeBlock != 0){throw new Exception("O tamanho do bloco deve ser um múltiplo do disco");}
			if (sizeBlock > sizeDisk) throw new Exception("O tamanho do bloco não deve ser maior que o disco");
			this.blockSize = sizeBlock;
			blocksHD = new int[sizeDisk / sizeBlock];
			dic.clear();
			totalBlocksFree = blocksHD.length;
			for (int i = 0; i < blocksHD.length; i++) {
				blocksHD[i] = -1;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public void createFile(String fileName, Integer size) throws Exception{
		try {
			int blockFile = size.intValue() / blockSize;
			if (size.intValue() % blockSize != 0) {	blockFile++;}
			if (totalBlocksFree < blockFile) {throw new Exception("Espaço insuficiente");}
			int blockFree = block.getFirstBlockFree(blocksHD);
			if (dic.containsKey(fileName)) {throw new Exception(String.format("Já existe um arquivo com nome %s" , fileName));}
			dic.put(fileName, Integer.valueOf(blockFree));
			totalBlocksFree -= blockFile;
			int previous = blockFree;
			while (blockFile > 1) {
				blockFree = block.getNextFreeBlock(previous, blocksHD);
				blocksHD[previous] = blockFree;
				previous = blockFree;
				blockFile--;
			}
			blocksHD[blockFree] = -2;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public void excludeFile(String fileName) throws Exception {
		try {
			Integer next = (Integer) dic.get(fileName);
			if (next == null) {throw new Exception(String.format("não foi encontrado arquivo com nome %s" , fileName));}
			while (blocksHD[next.intValue()] != -2) {
				Integer previous = next;
				next = Integer.valueOf(blocksHD[next.intValue()]);
				blocksHD[previous.intValue()] = -1;
				totalBlocksFree += 1;
			}
			blocksHD[next.intValue()] = -1;
			totalBlocksFree += 1;
			dic.remove(fileName);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public String toList() {
		try {
			StringBuilder builder = new StringBuilder();
			int busyMem = (blocksHD.length - totalBlocksFree) * blockSize;
			int freeMem = totalBlocksFree * blockSize;
			int totalblocksDisk = blocksHD.length ;
			int busyBlock = blocksHD.length - totalBlocksFree;
			builder.append(String.format("\nMemória Ocupada  %d \n", busyMem));
			builder.append(String.format("Memória Livre    %d \n", freeMem));
			builder.append(String.format("Total de Blocos no Disco %d \n", totalblocksDisk));
			builder.append(String.format("Blocos Ocupados %d \n", busyBlock));
			builder.append(String.format("Blocos Livres %d \n\n", totalBlocksFree));
			builder.append("Mapeamento de Blocos com FAT \n");
			builder.append("Endereço \t Encadeamento \n");
			for (int i = 0; i < blocksHD.length; i++) {
				builder.append(i + "\t");
				if (blocksHD[i] == -1) {builder.append("\t x\n");} 
				else if (blocksHD[i] == -2) {builder.append("\t f\n");}
				else {builder.append("\t "+blocksHD[i] + "\n");}
			}builder.append("\nEndereço inicial dos aquivos\n\n");
			builder.append("Nome\t Primeiro Bloco\n");
			for (Entry<String, Integer> item : dic.entrySet()) {
				builder.append((String) item.getKey() + "\t" + item.getValue() + "\n");
			}
			return builder.toString();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
}
