package main;

import java.util.Scanner;

public class HardDisk {

	private static HardDisk disk;
	private Scanner scan = new Scanner(System.in);
	private FATTable fat;

	public static HardDisk getInstance() {
		if (disk == null) {
			disk = new HardDisk();
		}
		return disk;
	}

	public void init() {
		try {
			System.out.println("Digite o tamanho do disco");
			int diskSize = scan.nextInt();
			System.out.println("Digite o tamanho do bloco");
			int blockSize = scan.nextInt();
			fat = new FATTable(blockSize, diskSize);
			int opt;
			do {
				oncreateOptionsMenu();
				System.out.print("Digite uma opção: ");
				opt = scan.nextInt();
				switch (opt) {
				case 0:
					System.out.println("Fechando...");
					break;
				case 1:
					create();
					break;
				case 2:
					remove();
					break;
				case 3:
					show();
					break;
				default:
					System.out.println("Opção inválida tente novamente");
				}

			} while (opt != 0);		

		} catch (Exception ex) {
			System.out.println("Erro: "+ex.getMessage());
		}

	}

	private void remove() {
		scan.nextLine();
		System.out.print("Digite o nome do arquivo a ser removido: ");
		String fileName = scan.nextLine();
		try {
			String format = String.format("Arquivo %s removido com sucesso", fileName);
			System.out.println(format);
			fat.excludeFile(fileName);
		} catch (Exception ex) {
			System.out.println("Erro: "+ex.getMessage());
		}
	}
	
	private void show(){
		System.out.println(fat.toList());
		System.out.println();
	}

	private void create() {
		try {
			scan.nextLine();
			System.out.print("Digite o nome do arquivo: ");
			String fileName = scan.nextLine();
			System.out.print("Digite o tamanho do arquivo:  ");
			int size = scan.nextInt();
			fat.createFile(fileName,size);
		} catch (Exception ex) {
			System.out.println("Erro "+ex.getMessage());
		}
	}

	private void oncreateOptionsMenu() {
		System.out.println("----------------------");
		System.out.println("1 - Criar Arquivo");
		System.out.println("2 - Remover Arquivo");
		System.out.println("3 - Imprimir FAT");
		System.out.println("0 - Sair");
		System.out.println("----------------------");
	}

}
