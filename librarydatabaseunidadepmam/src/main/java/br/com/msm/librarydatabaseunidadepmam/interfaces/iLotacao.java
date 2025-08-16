package br.com.msm.librarydatabaseunidadepmam.interfaces;

import java.util.List;

import br.com.msm.librarydatabaseunidadepmam.classes_vo.lotacoesVO;

public interface iLotacao {

	public void setLotacoes(List<lotacoesVO> result, String erro);
}
