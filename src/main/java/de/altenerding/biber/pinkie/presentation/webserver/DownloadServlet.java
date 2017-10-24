package de.altenerding.biber.pinkie.presentation.webserver;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Files;

@SuppressWarnings("Duplicates")
@WebServlet(name = "DownloadServlet", urlPatterns = {"/file/*"})
public class DownloadServlet extends HttpServlet {

	@Inject
	private Logger logger;

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
			String resourceFolder = System.getProperty("resourceFolder");

			if (resourceFolder == null || resourceFolder.isEmpty()) {
				throw new ServletException("Please provide the VM Option \'-DresourceFolder=\'");
			}

			String fileName = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
			String filePath = resourceFolder + File.separator + fileName;
			logger.info("Download file with fileName={}", filePath);
			// Call the download method with the given file
			doDownload(request, response, filePath);
		} catch (Exception e) {
			PrintWriter writer = response.getWriter();
			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent "
					+ "location.");
			writer.println("ERROR: " + e.getMessage());

			logger.error("Problems during file upload. ", e);
			response.setContentType("text/html;charset=UTF-8");
			response.setStatus(400);
		}
	}


	/**
	 *  Sends a file to the output stream.
	 *
	 *  @param request The request
	 *  @param response The response
	 *  @param fileName The name the browser should receive.
	 */
	private void doDownload( HttpServletRequest request, HttpServletResponse response,
							 String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			throw new FileNotFoundException(String.format("File %s does not exist", fileName));
		}
		response.setHeader("Content-Type", getServletContext().getMimeType(fileName));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
		Files.copy(file.toPath(), response.getOutputStream());
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
}
