package curso.springboot.controller;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	//retorna o PDF em Byte para donwload no navegador
	public byte[] gerarRelatorio(List<T> listDados, 
			String relatorio, ServletContext servletContext) throws Exception{
		
		
		// cria a lista de dados para o relatório com a lista de objetos.
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listDados);
		
		//carrega o caminho do arquivo jasper compilado
        String caminhoJasper = servletContext.getRealPath("relatorios" 
		+ File.separator + relatorio + ".jasper");
        
        //carrega o arquivo Jasper passando os dados
        JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, 
        		new HashMap<>(), jrBeanCollectionDataSource);		
		
        //Exporta para Byte para fazer o download do PDF
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}
}
