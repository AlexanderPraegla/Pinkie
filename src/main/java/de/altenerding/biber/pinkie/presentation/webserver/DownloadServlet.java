package de.altenerding.biber.pinkie.presentation.webserver;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(name = "DownloadServlet", urlPatterns = {"/files/*"})
public class DownloadServlet extends HttpServlet {

	private Logger logger;
	private FileService fileService;

	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Read parameter from form that contains the filename to download
		try {
			String fileId = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");

			Path path = fileService.downloadById(fileId, response.getOutputStream());

			response.setHeader("Content-Type", getServletContext().getMimeType(fileId));
			response.setHeader("Content-Length", String.valueOf(Files.size(path)));
			response.setHeader("Content-Disposition", "inline; filename=\"" + path.getFileName().toString() + "\"");
		} catch (Exception e) {
			logger.error("Problems during file download. ", e);
			response.setContentType("text/html;charset=UTF-8");
			response.setStatus(400);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
}
