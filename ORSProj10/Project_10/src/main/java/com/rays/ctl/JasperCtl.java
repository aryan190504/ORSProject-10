package com.rays.ctl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.MarksheetDTO;
import com.rays.form.MarksheetForm;
import com.rays.service.MarksheetServiceInt;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Jasper functionality Controller. Generates PDF report for Marksheet Merit
 * List.
 * 
 * Author: Aryan Shrivastav
 */
@Transactional
@RestController
@RequestMapping(value = "/Jasper")
public class JasperCtl extends BaseCtl<MarksheetForm, MarksheetDTO, MarksheetServiceInt> {

	@Autowired
	private ServletContext context;

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping(value = "/report", produces = MediaType.APPLICATION_PDF_VALUE)
	public void display(HttpServletRequest request, HttpServletResponse response)
			throws JRException, SQLException, IOException {

		System.out.println("******** Jasper Report Generation Started ********");

		// Load jasper file path from application.properties
		ResourceBundle rb = ResourceBundle.getBundle("application");
		String jrxmlPath = context.getRealPath(rb.getString("jasper"));
		System.out.println("JRXML Path: " + jrxmlPath);

		// Compile JRXML file to JasperReport
		JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);

		// Parameters to pass in report
		Map<String, Object> parameters = new HashMap<>();

		// Get database connection from Hibernate SessionFactory
		SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
		Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry()
				.getService(ConnectionProvider.class).getConnection();

		try {
			// Fill report with data
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

			// Convert to PDF
			byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

			// Send PDF to browser
			response.setContentType("application/pdf");
			response.getOutputStream().write(pdfBytes);
			response.getOutputStream().flush();

			System.out.println("******** Jasper Report Generated Successfully ********");

		} finally {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}
}
