package multithread.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class ThreadsTest {

	/* Log details */
	private static final String LOGFOLDERPATH = "./log";
	private static final String ERRORLOGPATH = "./log/error.log";
	private static final Logger LOGGER = Logger.getLogger("DownloadErrorLog");

	public static void main(String[] args) {
		ExecutorService threadsPool = null;
		final DateTime dtHoraInicio = new DateTime();
		threadsPool = Executors.newFixedThreadPool(10);
		final List<Future<Boolean>> listaProcessosEmExecucao = new ArrayList<Future<Boolean>>();
		final List<Future<Boolean>> listaProcessosFinalizados = new ArrayList<Future<Boolean>>();

		final List<DataToProcess> listaDTOsEmExecucao = new ArrayList<DataToProcess>();
		final List<DataToProcess> listaDTOsFinalizados = new ArrayList<DataToProcess>();
		try {
			Future<Boolean> processoEmExecucao = null;
			List<DataToProcess> listData = new ArrayList<DataToProcess>();
			listData.add(new DataToProcess("Good Luck!"));
			listData.add(new DataToProcess("Go man!"));
			listData.add(new DataToProcess("Beauty!"));
			Iterator<DataToProcess> it = listData.iterator();

			while (it.hasNext()) {
				DataToProcess data = it.next();
				Process call = new Process(data);
				processoEmExecucao = threadsPool.submit(call);
				listaProcessosEmExecucao.add(processoEmExecucao);
				listaDTOsEmExecucao.add(data);
				it.remove();
			}

			boolean finished = false;
			int numTarefasErro = 0;
			int numTarefasSucesso = 0;
			while (!finished) {
				LOGGER.info("Checking tasks return...");
				boolean retorno = false;
				int index = 0;
				finished = true;
				// percorre a lista de futures em busca dos retornos das tarefas escalonadas
				for (Future<Boolean> processoDaListaEmExecucao : listaProcessosEmExecucao) {
					final DataToProcess dtoEmExecucao = listaDTOsEmExecucao.get(index);
					if (!processoDaListaEmExecucao.isDone()) {
						finished = false;
						Thread.sleep(10000);
						break;
					} else {
						// se terminou, computa o resultado
						index++;
						try {
							LOGGER.info("Task["+numTarefasSucesso+"] Finished: getting Task return...");
							retorno = processoDaListaEmExecucao.get(1, TimeUnit.SECONDS);
							listaProcessosFinalizados.add(processoDaListaEmExecucao);
							listaDTOsFinalizados.add(dtoEmExecucao);
						} catch (Exception e) {
							LOGGER.severe("Erro ao obter o retorno da tarefa!" + e.toString());
							retorno = false;
						}
						
						if (!retorno) {
							numTarefasErro++;
							LOGGER.severe("Erro! Retorno = false!");
						} else {
							numTarefasSucesso++;
							LOGGER.info(" Task ["+numTarefasSucesso+"] Executed with success!");

						}
					}
				}
				listaProcessosEmExecucao.removeAll(listaProcessosFinalizados);
				listaDTOsEmExecucao.removeAll(listaDTOsFinalizados);
				listaProcessosFinalizados.clear();
				listaDTOsFinalizados.clear();
				listData.clear();
			}
			LOGGER.info("Shuting down the threads pool...");
			threadsPool.shutdown();
			LOGGER.info("Shutdown OK!");
			LOGGER.info("Tasks with errors=[" + numTarefasErro + "] Success Tasks=["
					+ numTarefasSucesso + "]");
			threadsPool.awaitTermination(1, TimeUnit.MINUTES);
			final DateTime dtHoraFim = new DateTime();
			LOGGER.info(" Begin Date/Time:    "
					+ DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").print(dtHoraInicio) + " End Date/Time:       "
					+ DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").print(dtHoraFim));
		} catch (Exception e) {
			if (threadsPool != null) {
				LOGGER.severe(
						"Shutting down the threads pool... " + e.toString());
				threadsPool.shutdown();
				LOGGER.info("Shutdown OK!");
			} else {
				LOGGER.severe(" Error: " + e.toString());
			}
		} finally {
			if (threadsPool != null) {
				if (threadsPool.isTerminated()) {
					threadsPool = null;
				} else {
					if (threadsPool.isShutdown() && !threadsPool.isTerminated()) {
						LOGGER.info("Shuting down the threads pool...");
						threadsPool.shutdownNow();
						LOGGER.info("Shutdown OK!");
					} else {
						LOGGER.info("Shuting down the threads pool...");
						threadsPool.shutdown();
						LOGGER.info("Shutdown OK!");
					}
				}
			}
		}
		System.exit(0);
	}
}
