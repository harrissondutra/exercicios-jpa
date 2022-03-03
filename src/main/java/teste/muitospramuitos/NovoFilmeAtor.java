package teste.muitospramuitos;

import infra.DAO;
import modelo.muitospramuitos.Ator;
import modelo.muitospramuitos.Filme;

public class NovoFilmeAtor {

	public static void main(String[] args) {

		Filme filmeA = new Filme("Star Wars Ep4", 8.9);
		Filme filmeB = new Filme("O Fugitivo", 8.1);
		
		Ator atorA = new Ator("Harrisson Dutra");
		Ator atrizB = new Ator("Carrie Fischer");
		
		filmeA.adicionarAtor(atorA);
		filmeA.adicionarAtor(atrizB);
		
		filmeB.adicionarAtor(atorA);
		
		DAO<Filme> dao =new DAO<Filme>();
		dao.incluirAtomico(filmeA);

	}

}
