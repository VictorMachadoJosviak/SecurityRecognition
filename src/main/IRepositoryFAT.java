package main;

public interface IRepositoryFAT {
	void createFile(String fileName, Integer size) throws Exception;
	void excludeFile(String fileName) throws Exception;
	String toList();
	
}
