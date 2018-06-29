package multithread.process;

import java.util.concurrent.Callable;

public class Process implements Callable<Boolean> {

	private  DataToProcess dataToProcess;
	
	public Process(final DataToProcess dataToProcess) {
		this.dataToProcess = dataToProcess;
	}

	public Boolean call() throws Exception {
		Boolean success = Boolean.TRUE;
		try {
			System.out.println("Executando Rotina");
			System.out.println(this.dataToProcess.getMessage());
		} catch (Exception e) {
			success = false;
		}
		
		return success;
	}
	
	

}